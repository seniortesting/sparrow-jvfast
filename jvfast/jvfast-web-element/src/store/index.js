import createLogger from 'vuex/dist/logger'

export const persistPaths = [
  'log.logs',
  'sys.user.userInfo', 'sys.user.token', 'sys.user.roles', 'sys.user.permissions', 'sys.user.routes', 'sys.user.routePermissionMap', 'sys.user.sidebarMenus', 'sys.user.sidebarMenuActive', 'sys.user.topMenus', 'sys.user.topMenuIndex',
  'app.sidebar', 'app.size', 'app.theme', 'app.tagsView',
  'app.isLock', 'app.lockPasswd',
  'app.visitedViews', 'app.cachedViews',
  'app.userCenterActiveTab'
]
export const getters = {
  // 错误日志
  logs: state => state.log.logs,
  // 用户对应相关数据
  userInfo: state => state.sys.user.userInfo,
  token: state => state.sys.user.token,
  roles: state => state.sys.user.roles,
  permissions: state => state.sys.user.permissions,
  website: state => state.app.website,
  // 后台菜单配置
  routes: state => state.sys.user.routes,
  routePermissionMap: state => state.sys.user.routePermissionMap,
  sidebarMenus: state => state.sys.user.sidebarMenus,
  sidebarMenuActive: state => state.sys.user.sidebarMenuActive,
  topMenus: state => state.sys.user.topMenus,
  topMenuIndex: state => state.sys.user.topMenuIndex,
  // 后台界面配置
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  visitedViews: state => state.app.visitedViews,
  cachedViews: state => state.app.cachedViews,
  isLock: state => state.app.isLock,
  lockPasswd: state => state.app.lockPasswd,
  userCenterActiveTab: state => state.app.userCenterActiveTab
}
export const plugins = process.env.NODE_ENV !== 'production' ? [createLogger()] : []
