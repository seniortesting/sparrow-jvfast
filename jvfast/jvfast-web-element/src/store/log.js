import log from '@/api/monitor/log'
import { formatDateTime } from '@/util/DateUtil'

export const state = () => ({
  // 对应的持久化对象
  logs: []
})
export const mutations = {
  // 对应的持久化操作
  // 存放页面的错误日志
  SET_LOG: (state, log) => {
    state.logs.push(log)
  },
  CLEAR_LOG: (state) => {
    state.logs = []
  }
}
export const actions = {
  // 添加
  sendLogs ({ state, commit }) {
    return new Promise((resolve, reject) => {
      log.addMonitorLogBatch(state.logs).then((res) => {
        if (res.code === 0) {
          commit('CLEAR_LOG')
        }
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  addMonitorLog ({ state, commit }, { businessType, operationType, businessTitle, requestErrorMsg, requestParam, requestResult }) {
    const logData = Object.assign({
      requestUrl: window.location.href,
      createTime: formatDateTime(new Date())
    }, {
      businessType, operationType, businessTitle, requestErrorMsg, requestParam, requestResult
    })
    commit('SET_LOG', logData)
  },
  // 更新
  updateMonitorLog ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      log.updateMonitorLog(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeMonitorLog ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      log.removeMonitorLog(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeMonitorLogs ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      log.removeMonitorLogs(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 通过Id单独查询列表
  getMonitorLogById ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      log.getMonitorLogById(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 查询全部结果列表
  getMonitorLogList ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      log.getMonitorLogList(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 分页查询结果
  getMonitorLogPageList ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      log.getMonitorLogPageList(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  clearLogs ({ state, commit }) {
    commit('CLEAR_LOG')
  }
  // 其它接口调用
}
