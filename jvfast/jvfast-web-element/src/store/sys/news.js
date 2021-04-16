import news from '@/api/sys/news'

export const state = () => ({
  // 对应的持久化对象
})
export const mutations = {
  // 对应的持久化操作
}
export const actions = {
  // 添加
  addSysNews ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      news.addSysNews(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 更新
  updateSysNews ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      news.updateSysNews(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeSysNews ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      news.removeSysNews(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeSysNewss ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      news.removeSysNewss(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 通过Id单独查询列表
  getSysNewsById ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      news.getSysNewsById(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 查询全部结果列表
  getSysNewsList ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      news.getSysNewsList(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 分页查询结果
  getSysNewsPageList ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      news.getSysNewsPageList(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  }
  // 其它接口调用
}
