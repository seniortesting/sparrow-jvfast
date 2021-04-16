import dept from '@/api/sys/dept'

export const state = () => ({
  // 对应的持久化对象
})
export const mutations = {
  // 对应的持久化操作
}
export const actions = {
  // 添加
  addSysDept ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      dept.addSysDept(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 更新
  updateSysDept ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      dept.updateSysDept(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeSysDept ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      dept.removeSysDept(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeSysDepts ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      dept.removeSysDepts(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 通过Id单独查询列表
  getSysDeptById ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      dept.getSysDeptById(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 查询全部结果列表
  getSysDeptList ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      dept.getSysDeptList(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 分页查询结果
  getSysDeptPageList ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      dept.getSysDeptPageList(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  getSysDeptTreeList ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      dept.getSysDeptTreeList(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  }
  // 其它接口调用
}
