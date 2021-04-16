import Map from './amap.vue'

Map.install = function (Vue) {
  Vue.component(Map.name, Map)
}

export default Map
