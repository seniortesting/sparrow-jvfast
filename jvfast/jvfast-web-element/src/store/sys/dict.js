import dict from '@/api/sys/dict'

export const state = () => ({
  // 对应的持久化对象
})
export const mutations = {
  // 对应的持久化操作
}
export const actions = {
  // 添加
  addSysDictType ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      dict.addSysDictType(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  addSysDictData ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      dict.addSysDictData(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 更新
  updateSysDictType ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      dict.updateSysDictType(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  updateSysDictData ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      dict.updateSysDictData(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeSysDictType ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      dict.removeSysDictType(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  removeSysDictData ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      dict.removeSysDictData(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeSysDictTypes ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      dict.removeSysDictTypes(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  removeSysDictDatas ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      dict.removeSysDictDatas(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 通过Id单独查询列表
  getSysDictTypeById ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      dict.getSysDictTypeById(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  getSysDictDataById ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      dict.getSysDictDataById(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 查询全部结果列表
  getSysDictTypeList ({ state, commit }) {
    return new Promise((resolve, reject) => {
      dict.getSysDictTypeList().then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 分页查询结果
  getSysDictDataPageList ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      dict.getSysDictDataPageList(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  getSysDictTypePageList ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      dict.getSysDictTypePageList(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  }
  // 其它接口调用
}
