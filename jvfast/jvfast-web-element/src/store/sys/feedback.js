import feedback from '@/api/sys/feedback'

export const state = () => ({
  // 对应的持久化对象
})
export const mutations = {
  // 对应的持久化操作
}
export const actions = {
  // 添加
  addSysFeedback ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      feedback.addSysFeedback(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 更新
  updateSysFeedback ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      feedback.updateSysFeedback(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeSysFeedback ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      feedback.removeSysFeedback(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeSysFeedbacks ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      feedback.removeSysFeedbacks(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 通过Id单独查询列表
  getSysFeedbackById ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      feedback.getSysFeedbackById(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 查询全部结果列表
  listSysFeedback ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      feedback.listSysFeedback(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 分页查询结果
  pageSysFeedback ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      feedback.pageSysFeedback(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  }
  // -------------------------------- 其它接口调用
}
