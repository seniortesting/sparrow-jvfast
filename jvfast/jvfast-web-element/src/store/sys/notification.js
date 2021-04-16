import notification from '@/api/sys/notification'

export const state = () => ({
  // 对应的持久化对象
})
export const mutations = {
  // 对应的持久化操作
}
export const actions = {
  // 添加
  addSysNotification ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      notification.addSysNotification(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 更新
  updateSysNotification ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      notification.updateSysNotification(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeSysNotification ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      notification.removeSysNotification(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeSysNotifications ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      notification.removeSysNotifications(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 通过Id单独查询列表
  getSysNotificationById ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      notification.getSysNotificationById(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 查询全部结果列表
  listSysNotification ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      notification.listSysNotification(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 分页查询结果
  pageSysNotification ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      notification.pageSysNotification(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // -------------------------------- 其它接口调用
  readNotification ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      notification.readNotification(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  }
}
