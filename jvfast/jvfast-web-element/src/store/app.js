import variables from '@/assets/css/element/element-variables.scss'
import config from '@/config'
export const state = () => ({
  sidebar: {
    opened: true,
    withoutAnimation: false
  },
  device: 'desktop',
  // 相关设置
  theme: variables.theme,
  tagsView: true,
  sidebarLogo: true,
  fixedHeader: true,
  // 锁屏
  isLock: false,
  lockPasswd: '',
  // tag配置
  visitedViews: [],
  cachedViews: [],
  // 网站设置
  website: config.website,
  // 个人资料激活tab
  userCenterActiveTab: 'basic'
})

export const mutations = {
  TOGGLE_SIDEBAR: (state) => {
    state.sidebar.opened = !state.sidebar.opened
    state.sidebar.withoutAnimation = false
  },
  CLOSE_SIDEBAR: (state, withoutAnimation) => {
    state.sidebar.opened = false
    state.sidebar.withoutAnimation = withoutAnimation
  },
  TOGGLE_DEVICE: (state, device) => {
    state.device = device
  },
  SET_SIZE: (state, size) => {
    state.size = size
  },
  SET_LOCK: (state) => {
    state.isLock = true
  },
  SET_LOCK_PASSWD: (state, lockPasswd) => {
    state.lockPasswd = lockPasswd
  },
  CLEAR_LOCK: (state) => {
    state.isLock = false
    state.lockPasswd = ''
  },
  // 设置
  CHANGE_SETTING: (state, { key, value }) => {
    if (key in state) {
      state[key] = value
    }
  },
  // tag相关
  ADD_VISITED_VIEW: (state, view) => {
    if (state.visitedViews.some(v => v.componentName === view.componentName)) { return }
    state.visitedViews.push(
      Object.assign({}, view, {
        title: view.title || view.menuName || 'no-name'
      })
    )
  },
  ADD_CACHED_VIEW: (state, view) => {
    if (state.cachedViews.includes(view.componentName)) { return }
    if (!view.noCache) {
      state.cachedViews.push(view.componentName)
    }
  },

  DEL_VISITED_VIEW: (state, view) => {
    for (const [i, v] of state.visitedViews.entries()) {
      if (v.componentName === view.componentName) {
        state.visitedViews.splice(i, 1)
        break
      }
    }
  },
  DEL_CACHED_VIEW: (state, view) => {
    const index = state.cachedViews.indexOf(view.componentName)
    index > -1 && state.cachedViews.splice(index, 1)
  },

  DEL_OTHERS_VISITED_VIEWS: (state, view) => {
    state.visitedViews = state.visitedViews.filter((v) => {
      return v.affix || v.componentName === view.componentName
    })
  },
  DEL_OTHERS_CACHED_VIEWS: (state, view) => {
    const index = state.cachedViews.indexOf(view.componentName)
    if (index > -1) {
      state.cachedViews = state.cachedViews.slice(index, index + 1)
    } else {
      // if index = -1, there is no cached tags
      state.cachedViews = []
    }
  },

  DEL_ALL_VISITED_VIEWS: (state) => {
    // keep affix tags
    const affixTags = state.visitedViews.filter(tag => tag.affix)
    state.visitedViews = affixTags
  },
  DEL_ALL_CACHED_VIEWS: (state) => {
    state.cachedViews = []
  },

  UPDATE_VISITED_VIEW: (state, view) => {
    for (let v of state.visitedViews) {
      if (v.componentName === view.componentName) {
        v = Object.assign(v, view)
        break
      }
    }
  },
  // 用户中心
  ACTIVE_USER_TAB: (state, tabName) => {
    state.userCenterActiveTab = tabName
  }
}
export const actions = {
  toggleSideBar ({ commit }) {
    commit('TOGGLE_SIDEBAR')
  },
  closeSideBar ({ commit }, { withoutAnimation }) {
    commit('CLOSE_SIDEBAR', withoutAnimation)
  },
  toggleDevice ({ commit }, device) {
    commit('TOGGLE_DEVICE', device)
  },
  setSize ({ commit }, size) {
    commit('SET_SIZE', size)
  },
  changeSetting ({ commit }, data) {
    commit('CHANGE_SETTING', data)
  },
  // tag相关配置
  addView ({ dispatch }, view) {
    dispatch('addVisitedView', view)
    dispatch('addCachedView', view)
  },
  addVisitedView ({ commit }, view) {
    commit('ADD_VISITED_VIEW', view)
  },
  addCachedView ({ commit }, view) {
    commit('ADD_CACHED_VIEW', view)
  },
  delView ({ dispatch, state }, view) {
    return new Promise((resolve) => {
      dispatch('delVisitedView', view)
      dispatch('delCachedView', view)
      resolve({
        visitedViews: [...state.visitedViews],
        cachedViews: [...state.cachedViews]
      })
    })
  },
  delVisitedView ({ commit, state }, view) {
    return new Promise((resolve) => {
      commit('DEL_VISITED_VIEW', view)
      resolve([...state.visitedViews])
    })
  },
  delCachedView ({ commit, state }, view) {
    return new Promise((resolve) => {
      commit('DEL_CACHED_VIEW', view)
      resolve([...state.cachedViews])
    })
  },

  delOthersViews ({ dispatch, state }, view) {
    return new Promise((resolve) => {
      dispatch('delOthersVisitedViews', view)
      dispatch('delOthersCachedViews', view)
      resolve({
        visitedViews: [...state.visitedViews],
        cachedViews: [...state.cachedViews]
      })
    })
  },
  delOthersVisitedViews ({ commit, state }, view) {
    return new Promise((resolve) => {
      commit('DEL_OTHERS_VISITED_VIEWS', view)
      resolve([...state.visitedViews])
    })
  },
  delOthersCachedViews ({ commit, state }, view) {
    return new Promise((resolve) => {
      commit('DEL_OTHERS_CACHED_VIEWS', view)
      resolve([...state.cachedViews])
    })
  },

  delAllViews ({ dispatch, state }, view) {
    return new Promise((resolve) => {
      dispatch('delAllVisitedViews', view)
      dispatch('delAllCachedViews', view)
      resolve({
        visitedViews: [...state.visitedViews],
        cachedViews: [...state.cachedViews]
      })
    })
  },
  delAllVisitedViews ({ commit, state }) {
    return new Promise((resolve) => {
      commit('DEL_ALL_VISITED_VIEWS')
      resolve([...state.visitedViews])
    })
  },
  delAllCachedViews ({ commit, state }) {
    return new Promise((resolve) => {
      commit('DEL_ALL_CACHED_VIEWS')
      resolve([...state.cachedViews])
    })
  },

  updateVisitedView ({ commit }, view) {
    commit('UPDATE_VISITED_VIEW', view)
  },
  switchUserCenterActiveTab ({ commit }, tabName) {
    commit('ACTIVE_USER_TAB', tabName)
  }
}
