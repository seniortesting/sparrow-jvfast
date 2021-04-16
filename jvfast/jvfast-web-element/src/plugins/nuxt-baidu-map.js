import Vue from 'vue'
import BaiduMap from 'vue-baidu-map'
export default ({ app }) => {
  Vue.use(BaiduMap, {
    /* Visit http://lbsyun.baidu.com/apiconsole/key for details about app key. */
    ak: 'QxFOYT4qgRS4bBPImK0Knrer'
  })
}
