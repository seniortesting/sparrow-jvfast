import post from '@/api/sys/post'

export const state = () => ({
  // 对应的持久化对象
})
export const mutations = {
  // 对应的持久化操作
}
export const actions = {
  // 添加
  addSysPost ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      post.addSysPost(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 更新
  updateSysPost ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      post.updateSysPost(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeSysPost ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      post.removeSysPost(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeSysPosts ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      post.removeSysPosts(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 通过Id单独查询列表
  getSysPostById ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      post.getSysPostById(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 查询全部结果列表
  getSysPostList ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      post.getSysPostList(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 分页查询结果
  getSysPostPageList ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      post.getSysPostPageList(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  }
  // 其它接口调用
}
