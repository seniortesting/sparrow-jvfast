import wx from '@/api/lib/wx'

export const state = () => ({
  // 对应的持久化对象
})
export const mutations = {
  // 对应的持久化操作
}
export const actions = {

  // 分页查询结果
  getWxMpPageList ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      wx.getWxMpPageList(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 通过Id单独查询列表
  listWxMpMenu ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      wx.listWxMpMenu(data.appId).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 通过Id单独查询列表
  updateWxMpMenu ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      wx.updateWxMpMenu(data.appId, data.menu).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  listWxMpUser ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      wx.listWxMpUser(data.appId, data.query).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  }
  // 其它接口调用
}
