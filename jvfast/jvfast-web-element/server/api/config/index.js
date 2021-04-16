const isDevelopment = process.env.NODE_ENV === 'development'
const config = {
  // 接口地址前缀
  prefix: '/api',
  uploadFolder: 'upload',
  uploadUrlPrefix: 'static',
  db: {
    // 连接池设置
    acquireTimeout: 10 * 1000,
    waitForConnections: true,
    connectionLimit: 10,
    queueLimit: 0,
    // 连接设置
    host: isDevelopment ? 'localhost' : 'localhost',
    port: isDevelopment ? 3306 : 1876,
    user: isDevelopment ? 'syscorer' : 'syscorer',
    password: isDevelopment ? 's6s@#@!L0ngh' : 's6s@#@!L0ngh',
    database: isDevelopment ? 'jvfast' : 'sff',
    connectTimeout: 10 * 1000,
    supportBigNumbers: true,
    bigNumberStrings: true,
    timezone: '+08:00',
    stringifyObjects: false,
    multipleStatements: true,
    typeCast: true,
    dateStrings: true
  },
  snowflake: {
    // worker_id 是 0-31的机器ID（用来配置分布式的多机器，最多支持32个机器）
    worker_id: 1,
    // datacenter_id 是 0-31的数据ID（用来配置某个机器下面的某某服务，每台机器最多支持32个服务）
    datacenter_id: 30
  },
  // Tunnel to serve the app over HTTPS and be able to receive webhooks locally.
  // Optionally, if you have a paid ngrok account, you can specify your `subdomain`
  // and `authtoken` in your `.env` file to use it.
  ngrok: {
    enabled: isDevelopment,
    port: process.env.PORT || 3000,
    subdomain: process.env.NGROK_SUBDOMAIN,
    authtoken: process.env.NGROK_AUTHTOKEN
  }
}
module.exports = config
