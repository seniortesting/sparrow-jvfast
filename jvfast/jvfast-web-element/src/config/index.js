const pkg = require('../../package.json')
const isProduction = process.env.NODE_ENV === 'production'

const config = {
  // 网站通用设置
  website: {
    version: pkg.version,
    buildVersion: process.env.BUILD_VERSION,
    name: 'JVFast管理系统',
    shortName: 'JVF',
    desc: 'JVFast是最敏捷的前后端脚手架系统快速解决方案',
    url: 'https://jvfast.pingbook.top',
    helpUrl: 'https://jvfast.pingbook.top',
    beian: '沪ICP备19010939号-1',
    enableAds: isProduction,
    enableAPPDownload: true,
    appDownloadUrl: 'https://www.baidu.com'
  },
  // 服务请求配置
  serverUrls: {
    API_BASE_URL: !isProduction ? 'http://127.0.0.1:9999/jvfast-service' : 'https://open.pingbook.top/jvfast-service',
    STATIC_BASE_URL: !isProduction ? 'https://res.yitieyilu.com/upload/' : 'https://res.pingbook.top/jvfast-service',
    CDN_BASE_URL: !isProduction ? '/res/' : 'https://res.yitieyilu.com/assets/b2b36bebdb95946fa/'
  },
  // axios插件配置: 对应的header值,对应后台接口设置的header
  tokenHeader: 'X-Token',
  tokenRefreshHeader: 'X-Token',
  secretEnabled: false,
  secretKey: 'b2c17b46e2b13s5392aab5a82869856c',
  // vuex-persistedstate本地持久化： 持久化存放值键
  storeEnabled: isProduction,
  storeKey: pkg.name,
  storeExpiredSeconds: 259200, // 3天过期秒数
  // iconfont: 加载所有的iconfont资源
  iconfont: ['font_1529202_0ms2l5eu5dk'],
  defaultSettings: {
    permission: {
      superAdmin: 'admin',
      superPermission: '*:*:*'
    },
    page: {
      loginComponentName: 'login',
      changePwdComponentName: 'changepwd-code',
      error401ComponentName: 'error401',
      homeComponentName: 'index',
      lockComponentName: 'lock',
      // 直接访问页面
      ignoreComponentName: [
        'lock',
        'index-iframe',
        'index-help',
        'index-profile-basic',
        'index-profile-feedback',
        'index-profile-notification',
        'index-profile-security'
      ]
    },
    defaultPasswd: '123456',
    // 高德地图组件
    AMAP_JS_KEY: '9e84bb4624b3c37448d15a7672800563',
    defaultProjectSize: 100
  }
}
module.exports = config
