import VueEditor from './Editor'

VueEditor.install = function (Vue) {
  Vue.component(VueEditor.name, VueEditor)
}

export default VueEditor
