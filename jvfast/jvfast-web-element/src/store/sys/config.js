import config from '@/api/sys/config'

export const state = () => ({
  // 对应的持久化对象
})
export const mutations = {
  // 对应的持久化操作
}
export const actions = {
  // 添加
  addSysConfig ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      config.addSysConfig(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 更新
  updateSysConfig ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      config.updateSysConfig(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeSysConfig ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      config.removeSysConfig(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeSysConfigs ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      config.removeSysConfigs(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 通过Id单独查询列表
  getSysConfigById ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      config.getSysConfigById(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 查询全部结果列表
  getSysConfigList ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      config.getSysConfigList(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 分页查询结果
  getSysConfigPageList ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      config.getSysConfigPageList(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  }
  // 其它接口调用
}
