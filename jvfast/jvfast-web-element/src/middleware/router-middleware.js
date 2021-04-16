import config from '@/config'

// const filterRouter = null
const globalWhiteList = [config.defaultSettings.page.loginComponentName, config.defaultSettings.page.changePwdComponentName, config.defaultSettings.page.error401ComponentName]
const getRoutesAction = 'sys/user/getRoutes'
const logoutAction = 'sys/user/logout'
/**
 * 路由拦截
 * 不推荐beforeEach方式: https://github.com/nuxt/nuxt.js/issues/3621
 * @param store
 * @param route
 * @param redirect
 */
export default ({ store, route, redirect }) => {
  const currentComponentName = route.name
  if (currentComponentName) {
    const token = store.getters.token
    if (!globalWhiteList.includes(currentComponentName)) {
      // 判断是否有token
      if (token) {
        if (store.getters.isLock && currentComponentName !== config.defaultSettings.page.lockComponentName) {
          redirect({ name: config.defaultSettings.page.lockComponentName })
        } else {
          try {
            fetchRoutesWithPermission(store, route, redirect)
          } catch (error) {
            console.log(error || 'Data fetch has Error')
            store.dispatch(logoutAction).then(() => {
              redirect({ name: config.defaultSettings.page.loginComponentName, query: { redirect: route.path } }) // 否则全部重定向到登录页
            })
          }
        }
      }
    } else if (token && currentComponentName === config.defaultSettings.page.loginComponentName) {
      redirect({ name: config.defaultSettings.page.homeComponentName })
    }
  }
}

/**
 * 获取所有的用户对应的路由列表, 路由路径权限控制
 * @param store
 * @param route
 * @param redirect
 */
const fetchRoutesWithPermission = function (store, route, redirect) {
  const permissions = store.getters.permissions
  const superPermission = config.defaultSettings.permission.superPermission
  const currentComponentName = route.name
  const routePermissionMap = store.getters.routePermissionMap

  const ignorePermissionCheck = (permissions && permissions.length > 0 && permissions[0] === superPermission) ||
    currentComponentName === config.defaultSettings.page.homeComponentName ||
    config.defaultSettings.page.ignoreComponentName.includes(currentComponentName)
    // 没有对应的路由信息
  store.dispatch(getRoutesAction).then((routes) => {
    // 当前路由对应的权限是否与用户的权限
    if (!ignorePermissionCheck) {
      const foundPermission = routePermissionMap[currentComponentName]
      // 如果没有找到当前路由的权限,直接放行访问
      const hasPermission = foundPermission ? permissions.includes(foundPermission) : true
      if (!hasPermission) {
        redirect({ name: config.defaultSettings.page.error401ComponentName, query: { redirect: route.path } }) // 否则全部重定向到401页
      }
    }
  })
}
/**
 * 过滤对应的路由的名称
 * @param routers
 * @param componentName
 */
// const filterRouterPermission = function (routers, componentName) {
//   for (let i = 0; i < routers.length; i++) {
//     if (routers[i].componentName === componentName) {
//       filterRouter = routers[i]
//       // filterRouter.children = []
//       break
//     }
//     if (routers[i].children) {
//       filterRouterPermission(routers[i].children, componentName)
//     }
//   }
//   return filterRouter
// }
