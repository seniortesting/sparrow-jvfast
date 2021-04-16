import ${cfg.functionName} from '@/api/${package.ModuleName?lower_case}/${cfg.functionName}'

export const state = () => ({
  // 对应的持久化对象
})
export const mutations = {
  // 对应的持久化操作
}
export const actions = {
  // 添加
  add${entity} ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      ${cfg.functionName}.add${entity}(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 更新
  update${entity} ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      ${cfg.functionName}.update${entity}(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  remove${entity} ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      ${cfg.functionName}.remove${entity}(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 批量删除
  remove${entity}s ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      ${cfg.functionName}.remove${entity}s(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 通过Id查询对象结果
  get${entity}ById ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      ${cfg.functionName}.get${entity}ById(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 查询全部结果列表
  list${entity} ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      ${cfg.functionName}.list${entity}(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 分页查询结果
  page${entity} ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      ${cfg.functionName}.page${entity}(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  }
  // -------------------------------- 其它接口调用
}
