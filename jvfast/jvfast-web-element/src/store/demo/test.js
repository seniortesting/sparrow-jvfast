import test from '@/api/demo/test'

export const state = () => ({
  // 对应的持久化对象
})
export const mutations = {
  // 对应的持久化操作
}
export const actions = {
  // 添加
  addDemoTest ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      test.addDemoTest(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 更新
  updateDemoTest ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      test.updateDemoTest(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeDemoTest ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      test.removeDemoTest(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 批量删除
  removeDemoTests ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      test.removeDemoTests(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 通过Id查询对象结果
  getDemoTestById ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      test.getDemoTestById(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 查询全部结果列表
  listDemoTest ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      test.listDemoTest(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 分页查询结果
  pageDemoTest ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      test.pageDemoTest(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  }
  // -------------------------------- 其它接口调用
}
