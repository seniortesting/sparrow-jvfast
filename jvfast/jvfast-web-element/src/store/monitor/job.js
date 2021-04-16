import job from '@/api/monitor/job'

export const state = () => ({
  // 对应的持久化对象
})
export const mutations = {
  // 对应的持久化操作
}
export const actions = {
  // 已经实现的定时任务
  listImplementJobs ({ state, commit }) {
    return new Promise((resolve, reject) => {
      job.listImplementJobs().then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 分页
  pageJobTasks ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      job.pageJobTasks(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 添加
  addJob ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      job.addJob(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 更新
  updateJob ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      job.updateJob(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 启动
  startJob ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      job.startJob(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 停止
  stopJob ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      job.stopJob(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 暂停
  pauseJob ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      job.pauseJob(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  resumeJob ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      job.resumeJob(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  deleteJob ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      job.deleteJob(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  }
  // 其它接口调用
}
