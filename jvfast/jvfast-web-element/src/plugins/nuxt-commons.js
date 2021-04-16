import Vue from 'vue'
// ## google广告
// import Ads from '@/components/vue-google-adsense/VueGoogleAdsense'
// import VueScript2 from '@/components/vue-google-adsense/vue-script2'

// ## 剪切板配置
import VueClipboard from 'vue-clipboard2'

// ## 图片懒加载配置
import VueLazyload from 'vue-lazyload'

// ## echart图表配置
import ECharts from 'vue-echarts'

// ## 滚动动画特效
// import AOS from 'aos'
// import 'aos/dist/aos.css'

// ## 加载模态框
// import Loading from '@/components/vue-loading-overlay'
// import '@/components/vue-loading-overlay/css/index.css'

// ## 权限控制
import directive from '@/directive'

import error from '@/assets/img/error.svg'
import loading from '@/assets/img/loading.svg'

// ## iconfont图标
import config from '@/config'
import { injectStyle } from '@/util/DomUtil'
const iconfontVersions = config.iconfont
const iconfontUrl = '//at.alicdn.com/t/$t.css'
export default ({ app }) => {
  // ## adsense广告，此处推荐使用@nuxtjs/google-adsense
  // Vue.use(VueScript2)
  // Vue.use(Ads.AutoAdsense, { adClient: 'ca-pub-1893384651266286' })
  // Vue.use(Ads.Adsense)
  // Vue.use(Ads.InArticleAdsense)
  // Vue.use(Ads.InFeedAdsense)

  // ## 剪切板配置
  VueClipboard.config.autoSetContainer = true // add this line
  Vue.use(VueClipboard)
  // ## vue-lazyload图片懒加载，参考使用 ： https://www.yasminzy.com/nuxt/vue-lazyload.html#steps
  Vue.use(VueLazyload, {
    preLoad: 1,
    error,
    loading,
    attempt: 3
  })
  // ## 图表组件
  Vue.component('v-chart', ECharts)
  // ## 加载模态框
  // Vue.use(Loading, {
  //   width: 128,
  //   height: 128,
  //   color: '#007bff',
  //   loader: 'spinner'
  // }, {
  // })
  // 使用方法
  // const loader = this.$loading.show({
  //   // Optional parameters
  //   container: this.fullPage ? null : this.$refs.formContainer,
  //   canCancel: true,
  //   onCancel: this.onCancel
  // })
  // // simulate AJAX
  // setTimeout(() => {
  //   loader.hide()
  // }, 5000)
  // ## 权限控制相关指令
  Vue.use(directive)
  // ## iconfont图标
  iconfontVersions.forEach((v) => {
    injectStyle(iconfontUrl.replace('$t', v))
  })
  // ## aos
  // app.AOS = new AOS.init({ disable: 'phone' }) // eslint-disable-line new-cap
}
