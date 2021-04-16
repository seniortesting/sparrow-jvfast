import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN'
import html2canvas from 'html2canvas'

// 其他组件
import ScreenShot from '@/components/vue-element-ui/el-screenshot'
import Print from '@/components/vue-element-ui/el-export/print'
import Excel from '@/components/vue-element-ui/el-export/excel'
import AMap from '@/components/vue-element-ui/el-amap'
import TinymceEditor from '@/components/vue-element-ui/el-tinymce-editor'
import ElCardContainer from '@/components/vue-element-ui/el-card-container'
import ElIframe from '@/components/vue-element-ui/el-iframe'
import ElEmpty from '@/components/vue-element-ui/el-empty'
import ElGroup from '@/components/vue-element-ui/el-group'
import { deepClone } from '@/util/ObjectUtil'

export default () => {
  Vue.use(ElementUI, { locale, size: 'medium', zIndex: 3000 })

  // 其他组件
  window.html2canvas = html2canvas
  Vue.prototype.$screenshot = ScreenShot
  Vue.prototype.$print = Print
  Vue.prototype.$deepclone = deepClone
  // 可以使用 this.$export.excel({title: 'test'})
  Vue.use(Excel)
  Vue.use(AMap)
  Vue.use(TinymceEditor)
  Vue.component(ElCardContainer.name, ElCardContainer)
  Vue.component(ElIframe.name, ElIframe)
  Vue.component(ElEmpty.name, ElEmpty)
  Vue.component(ElGroup.name, ElGroup)
}
