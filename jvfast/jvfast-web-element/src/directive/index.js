import hasRole from './hasRole'
import hasPermission from './hasPermission'
import dialogdrag from './dialogdrag'
import clickout from './clickout'
const install = function (Vue) {
  Vue.directive('hasRole', hasRole)
  Vue.directive('hasPermission', hasPermission)
  Vue.use(dialogdrag)
  clickout(Vue)
}

// if (window.Vue) {
//   window.hasRole = hasRole
//   window.hasPermission = hasPermission
//   Vue.use(install); // eslint-disable-line
// }

export default install
