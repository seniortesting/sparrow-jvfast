import history from '@/api/monitor/history'

export const state = () => ({
  // 对应的持久化对象
})
export const mutations = {
  // 对应的持久化操作
}
export const actions = {
  // 添加
  addMonitorLoginHistory ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      history.addMonitorLoginHistory(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 更新
  updateMonitorLoginHistory ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      history.updateMonitorLoginHistory(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeMonitorLoginHistory ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      history.removeMonitorLoginHistory(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeMonitorLoginHistorys ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      history.removeMonitorLoginHistorys(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 通过Id单独查询列表
  getMonitorLoginHistoryById ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      history.getMonitorLoginHistoryById(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 查询全部结果列表
  getMonitorLoginHistoryList ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      history.getMonitorLoginHistoryList(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 分页查询结果
  getMonitorLoginHistoryPageList ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      history.getMonitorLoginHistoryPageList(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  }
  // 其它接口调用
}
