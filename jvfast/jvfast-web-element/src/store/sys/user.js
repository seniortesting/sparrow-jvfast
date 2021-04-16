import user from '@/api/sys/user'

export const state = () => ({
  // ---------------------------start:路由菜单开始
  routes: [],
  routePermissionMap: {},
  sidebarMenus: [],
  sidebarMenuActive: {},
  // 顶部菜单
  topMenus: [],
  topMenuIndex: '0',
  // ---------------------------end: 路由菜单结束
  // 登录用户信息
  userInfo: {},
  token: '',
  roles: [],
  permissions: []
})

export const mutations = {
  // ---------------------------start: 路由菜单开始
  SET_ROUTES: (state, routes) => {
    state.routes = routes
  },
  SET_ROUTE_PERMISSION_MAP: (state, route) => {
    state.routePermissionMap[route.componentName] = route.permission
  },
  SET_SIDEBAR_MENUS: (state, sidebarMenus) => {
    state.sidebarMenus = sidebarMenus
  },
  SET_SIDEBAR_MENU_ACTIVE: (state, sidebarMenuActive) => {
    state.sidebarMenuActive = sidebarMenuActive
  },
  SET_TOP_MENUS: (state, topMenus) => {
    state.topMenus = topMenus
  },
  SET_TOP_MENU_INDEX: (state, topmenuIndex) => {
    state.topMenuIndex = topmenuIndex
  },
  // ---------------------------end: 路由菜单结束
  // 更新用户信息
  SET_USER_INFO: (state, userInfo) => {
    state.userInfo = Object.assign(state.userInfo, userInfo)
  },
  CLEAR_USER_INFO: (state) => {
    state.userInfo = {}
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  },
  SET_PERMISSIONS: (state, permissions) => {
    state.permissions = permissions
  },
  LOGOUT: (state) => {
    state.userInfo = {}
    state.token = ''
    state.roles = []
    state.permissions = []
    // 设置菜单相关设置数据
    state.routes = []
    state.routePermissionMap = {}
    state.sidebarMenus = []
    state.sidebarMenuActive = {}
    state.topMenus = []
    state.topMenuIndex = '0'
  }
}

export const actions = {
  // ---------------------------------------------start: 菜单相关设置
  /**
   * 路由切换时获取所有的菜单列表
   * 调用地方： src\middleware\router-middleware.js
   * @param commit
   * @param state
   * @return {Promise<unknown>}
   */
  getRoutes ({ commit, state }) {
    // 设置对应的后面用于鉴权的routeMap
    function addRouteMap (routes) {
      routes.forEach((route) => {
        commit('SET_ROUTE_PERMISSION_MAP', route)
        if (route.children) {
          addRouteMap(route.children)
        }
      })
    }
    return new Promise((resolve, reject) => {
      user.getMenu().then((res) => {
        const resCode = res.code
        if (resCode === 0) {
          const routes = res.data || []
          const defaultSidebarMenus = routes[0]
          commit('SET_ROUTES', routes)
          if (state.topMenuIndex === '0') {
            commit('SET_SIDEBAR_MENUS', defaultSidebarMenus.children || [])
            addRouteMap(routes)
          }
          resolve(routes)
        } else {
          const message = res.message
          reject(message)
        }
      })
    })
  },
  /**
   * 调用地方： src\components\vue-element-ui\el-layout\Top\top-menu.vue
   * @param commit
   * @param state
   * @return {Promise<unknown>}
   */
  getTopMenus ({ commit, state }) {
    return new Promise((resolve, reject) => {
      // 后端控制,总是请求该数据
      user.getTopMenu().then((res) => {
        const resCode = res.code
        if (resCode === 0) {
          const topMenus = res.data || []
          commit('SET_TOP_MENUS', topMenus)
          // 如果顶部激活选项没有值则重新设置
          if (state.topMenuIndex === '0') {
            const defaultTopMenuActive = topMenus[0]
            const defaultSidebarMenus = state.routes[0]
            commit('SET_TOP_MENU_INDEX', defaultTopMenuActive.id || '0')
            commit('SET_SIDEBAR_MENUS', defaultSidebarMenus.children || [])
          }
          resolve(topMenus)
        } else {
          const message = res.message
          reject(message)
        }
      })
    })
  },
  setTopMenuIndex ({ commit }, topMenuIndex) {
    commit('SET_TOP_MENU_INDEX', topMenuIndex)
  },
  setSidebarMenus ({ commit }, sidebarMenus) {
    commit('SET_SIDEBAR_MENUS', sidebarMenus)
  },
  setSidebarMenuActive ({ commit }, sidebarMenuActive) {
    commit('SET_SIDEBAR_MENU_ACTIVE', sidebarMenuActive)
  },
  // ---------------------------------------------end: 菜单相关设置
  // 全局存放用户登录信息
  getCaptcha ({ commit }) {
    return new Promise((resolve, reject) => {
      user.getCaptcha().then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  getForgetSMS ({ commit }, phone) {
    return new Promise((resolve, reject) => {
      const data = {
        type: 'FORGET_PASSWD',
        phone
      }
      user.getSMS(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  loginByUserName ({ commit }, data) {
    return new Promise((resolve, reject) => {
      user.login(data).then((res) => {
        // 如果登录成功
        const resCode = res.code
        if (resCode === 0) {
          const resData = res.data
          commit('SET_TOKEN', resData.token)
          commit('SET_USER_INFO', resData.user)
          commit('SET_ROLES', resData.user.roles)
          commit('SET_PERMISSIONS', resData.user.permissions)
        }
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 根据手机号登录
  LoginByPhone ({ commit }, data) {
    return new Promise((resolve, reject) => {
      user.login(data).then((res) => {
        // 如果登录成功
        const resCode = res.code
        if (resCode === 0) {
          const resData = res.data
          commit('SET_TOKEN', resData.token)
          commit('SET_USER_INFO', resData.user)
          commit('SET_ROLES', resData.user.roles)
          commit('SET_PERMISSIONS', resData.user.permissions)
        }
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  clearUserInfo ({ commit }) {
    commit('CLEAR_USER_INFO')
  },
  updateUserInfo ({ commit }, userInfo) {
    return new Promise((resolve, reject) => {
      commit('SET_USER_INFO', userInfo)
      resolve(userInfo)
    })
  },
  // 获取用户信息
  getUserInfo ({ commit }) {
    return new Promise((resolve, reject) => {
      user.getUserInfo().then((res) => {
        const resCode = res.code
        if (resCode === 0) {
          const resData = res.data
          commit('SET_TOKEN', resData.token)
          commit('SET_USER_INFO', resData.user)
          commit('SET_ROLES', resData.user.roles)
          commit('SET_PERMISSIONS', resData.user.permissions)
        }
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 注册
  register ({ commit }, data) {
    return new Promise((resolve, reject) => {
      user.register(data).then((res) => {
        const resCode = res.code
        if (resCode === 0) {
          const resData = res.data
          commit('SET_TOKEN', resData.token)
          commit('SET_USER_INFO', resData.user)
          commit('SET_ROLES', resData.user.roles)
          commit('SET_PERMISSIONS', resData.user.permissions)
        }
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 检查用户是否存在
  userVerify ({ commit }, data) {
    return new Promise((resolve, reject) => {
      user.userVerify(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  forgetPwd ({ commit }, data) {
    return new Promise((resolve, reject) => {
      user.forgetPwd(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  forgetPwdVerify ({ commit }, data) {
    return new Promise((resolve, reject) => {
      user.forgetPwdVerify(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 登出
  logout ({ commit }) {
    return new Promise((resolve, reject) => {
      commit('LOGOUT')
      commit('app/CLEAR_LOCK', null, { root: true })
      commit('log/CLEAR_LOG', null, { root: true })
      commit('app/DEL_ALL_VISITED_VIEWS', null, { root: true })
      commit('app/DEL_ALL_CACHED_VIEWS', null, { root: true })
      user.logout().then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 添加
  addUser ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      user.addUser(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 更新
  updateUser ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      user.updateUser(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeUserById ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      user.removeUserById(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeUsersById ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      user.removeUsersById(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 通过Id单独查询列表
  getUserById ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      user.getUserById(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 查询全部结果列表
  getUserList ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      user.getUserList(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 分页查询结果
  getUserPageList ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      user.getUserPageList(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 重置用户密码
  resetUserPasswdBatch ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      user.resetUserPasswdBatch(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  getUserRoleByUserId ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      user.getUserRoleByUserId(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  lockUser ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      user.lockUser(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  }
}
