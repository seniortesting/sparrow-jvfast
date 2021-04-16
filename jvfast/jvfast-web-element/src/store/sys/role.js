import role from '@/api/sys/role'

export const state = () => ({
  // 对应的持久化对象
})
export const mutations = {
  // 对应的持久化操作
}
export const actions = {
  // 添加
  addSysRole ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      role.addSysRole(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 更新
  updateSysRole ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      role.updateSysRole(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeSysRole ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      role.removeSysRole(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeSysRoles ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      role.removeSysRoles(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 通过Id单独查询列表
  getSysRoleById ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      role.getSysRoleById(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 查询全部结果列表
  getSysRoleList ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      role.getSysRoleList(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 分页查询结果
  getSysRolePageList ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      role.getSysRolePageList(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  }
  // 其它接口调用
}
