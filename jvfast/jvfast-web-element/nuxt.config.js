const config = require('./src/config')
const server = require('./server')
const serverConfig = require('./server/api/config')
const now = new Date()
module.exports = {
  // mode: 'spa',
  mode: 'universal',
  /**
   * 相关配置
   */
  buildDir: '.dist',
  srcDir: 'src/',
  env: {
    BUILD_VERSION: now.getFullYear() + (now.getMonth() + 1) + now.getDate()
  },
  head: {
    title: config.website.name + ',' + config.website.desc,
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { name: 'keywords', content: config.website.keyword || '' },
      { hid: 'description', name: 'description', content: config.website.desc || '' }
    ],
    link: [
      // favicon generator:
      { rel: 'apple-touch-icon', sizes: '180x180', href: '/apple-touch-icon.png' },
      { rel: 'icon', type: 'image/png', sizes: '32x32', href: '/favicon-32x32.png' },
      { rel: 'icon', type: 'image/png', sizes: '16x16', href: '/favicon-16x16.png' },
      { rel: 'manifest', href: '/site.webmanifest' }
      // { rel: 'stylesheet', href: 'https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.min.css' },
      // { rel: 'stylesheet', href: 'https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.7.2/animate.min.css' },
      // { rel: 'stylesheet', href: 'https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.min.css' }
    ],
    script: [
      // 高德地图
      { src: `https://webapi.amap.com/maps?v=1.4.11&key=${config.defaultSettings.AMAP_JS_KEY}&plugin=AMap.PlaceSearch`, defer: true },
      { src: 'https://webapi.amap.com/ui/1.0/main.js?v=1.0.11', defer: true }
      // excel导出功能，npm方式: { "xlsx": "^0.15.4", "file-saver": "^2.0.2"}
    ]
  },
  /*
  ** 自定义加载条
  */
  loading: { color: '#409eff', height: '5px' },
  // 单页时白屏加载显示
  loadingIndicator: {
    name: '~/static/loading.html',
    color: '#3B8070',
    background: 'white'
  },
  css: [
    'normalize.css',
    '~/assets/css/app.scss'
  ],
  plugins: [
    { src: '~/plugins/nuxt-error-handler', ssr: false },
    { src: '~/plugins/nuxt-axios', ssr: false },
    { src: '~/plugins/nuxt-persist', ssr: false },
    { src: '~/plugins/nuxt-commons', ssr: false },
    { src: '~/plugins/element-ui', ssr: false }
  ],
  router: {
    middleware: ['router-middleware'],
    extendRoutes (routes, resolve) {
      // const profileIndex = routes.findIndex(route => route.name === 'profile')
      // routes[profileIndex] = {
      //   ...routes[profileIndex],
      //   components: {
      //     default: routes[profileIndex].component,
      //     notification: resolve(__dirname, 'components/mainTop.vue')
      //   },
      //   chunkNames: {
      //     top: 'components/mainTop'
      //   }
      // }
    }
  },
  modules: [
    ['@nuxtjs/axios', {
      progress: true,
      retry: false,
      credentials: true
    }],
    ['@nuxtjs/device'],
    ['@nuxtjs/sitemap', {
      hostname: config.website.url,
      path: 'sitemap_index.xml',
      gzip: false,
      exclude: [
        '/admin/**'
      ]
    }],
    ['@nuxtjs/robots', [
      {
        UserAgent: '*'
        // Disallow: serverConfig.prefix
      }
    ]]
    /** 网站SEO相关配置 **/
    // ['@nuxtjs/google-adsense', {
    //   id: 'ca-pub-1893384651266286'
    // }],
    // ['@nuxtjs/google-tag-manager', { id: config.googleTagManagerId }],
  ],
  buildModules: [
    // Doc: https://github.com/nuxt-community/eslint-module
    '@nuxtjs/eslint-module',
    ['nuxt-purgecss', { enabled: false, mode: 'webpack' }]
  ],
  build: {
    // CDN配置，生产环境配置此处cdn,参考: https://nuxtjs.org/api/configuration-build/#publicpath
    publicPath: config.serverUrls.CDN_BASE_URL,
    extractCSS: true,
    // 页面打包体积分析
    analyze: {
      analyzerMode: process.env.NODE_ENV === 'development' ? 'static' : 'disabled'
    },
    // 减小vendor的打包体积
    optimization: {
      splitChunks: {
        chunks: 'all',
        automaticNameDelimiter: '.',
        name: 'dist',
        maxSize: 256000
      }
    },
    babel: {
      presets: ['@nuxt/babel-preset-app'],
      comments: true,
      plugins: [
        [
          'component',
          {
            libraryName: 'element-ui',
            styleLibraryName: 'theme-chalk'
          }
        ]
      ]
    },
    // 默认babel不会编译node-modules目录的文件,对于一些包采用源码形式打包的将不被转译
    transpile: [
      // element-ui引用
      /^element-ui/,
      // vue-echarts引用
      'vue-echarts', 'resize-detector'
    ],
    extend (config, ctx) {
      if (ctx.isDev) {
        config.devtool = 'source-map'
        /*
         ** Run ESLINT on save
        */
        if (ctx.isClient) {
          config.module.rules.push({
            enforce: 'pre',
            test: /\.(js|vue)$/,
            loader: 'eslint-loader',
            exclude: /(node_modules)/
          })
        }
      }
    }
  },
  // 服务端渲染组件
  serverMiddleware: [
    { path: serverConfig.prefix, handler: server }
  ]
}
