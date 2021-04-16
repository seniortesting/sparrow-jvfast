const path = require('path')
const fs = require('fs')
const express = require('express')
// eslint-disable-next-line import/order
const config = require('./api/config')
const ngrok = config.ngrok.enabled ? require('ngrok') : null
// const consola = require('consola')
// const { Nuxt, Builder } = require('nuxt')
// start express app
const isDev = process.env.NODE_ENV !== 'production'
const rootPwd = process.cwd()
const app = express()
// ------cors: registering cors
const cors = require('cors')
app.use(cors())
// ------body-parser: configure body parser,grabbing the HTTP body, decoding the information, and appending it to the req.body
const bodyParser = require('body-parser')
app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json())
// ------morgan: configure morgan
const FileStreamRotator = require('file-stream-rotator')
const morgan = require('morgan')

const devModify = '[:date[iso]] ":method :url HTTP/:http-version" :status - :res[content-length] - :response-time ms ":referrer" ":user-agent"'
const combinedModify = ':remote-addr - :remote-user [:date[iso]] ":method :url HTTP/:http-version" :status :response-time ms ":referrer" ":user-agent"'
const morganFormat = isDev ? devModify : combinedModify
// ensure log directory exists
const logDirectory = path.join(rootPwd, 'logs')
fs.existsSync(logDirectory) || fs.mkdirSync(logDirectory)
// 1. create stream
// const accessLogStream = fs.createWriteStream(logPath, { flags: 'a' })
// 2. create a rotating write stream
const accessLogStream = FileStreamRotator.getStream({
  date_format: 'YYYYMMDD',
  filename: path.join(logDirectory, 'access-%DATE%.log'),
  frequency: 'daily',
  verbose: false
})
app.use(morgan(morganFormat, { stream: accessLogStream })) // configire morgan

// 方法一：Export the server middleware
const api = require('./api/controller')
app.use(api)
// 静态访问路径
const staticPath = path.join(process.cwd(), config.uploadFolder)
app.use('/' + config.uploadUrlPrefix, express.static(staticPath, { index: ['index.html', 'index.htm'] }))
// Turn on the ngrok tunnel in development, which provides both the mandatory HTTPS
// support for all card payments, and the ability to consume webhooks locally.
if (ngrok) {
  ngrok
    .connect({
      addr: config.ngrok.port,
      subdomain: config.ngrok.subdomain,
      authtoken: config.ngrok.authtoken
    })
    .then((url) => {
      console.log(`💳  App URL to see the demo in your browser: ${url}/`)
    })
    .catch((err) => {
      if (err.code === 'ECONNREFUSED') {
        console.log(`⚠️  Connection refused at ${err.address}:${err.port}`)
      } else {
        console.log(`⚠️ Ngrok error: ${JSON.stringify(err)}`)
      }
      process.exit(1)
    })
}
module.exports = app
// 方法二：Router: Import API Routes
// app.use(config.prefix, api)
// async function start () {
//   // Init Nuxt.js
//   const nuxtConfig = require('../nuxt.config.js')
//   // Import and Set Nuxt.js options
//   nuxtConfig.dev = isDev
//   const nuxt = new Nuxt(nuxtConfig)
//   const { host, port } = nuxt.options.server
//
//   await nuxt.ready()
//   // Build only in dev mode
//   if (nuxtConfig.dev) {
//     const builder = new Builder(nuxt)
//     await builder.build()
//   }
//
//   // Give nuxt middleware to express
//   app.use(nuxt.render)
//
//   // Listen the server
//   app.listen(port, host)
//   consola.ready({
//     message: `Server listening on http://${host}:${port}`,
//     badge: true
//   })
// }
// start()
