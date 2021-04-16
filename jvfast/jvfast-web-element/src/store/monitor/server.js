import server from '@/api/monitor/server'
export const state = () => ({
  // 对应的持久化对象
})
export const mutations = {
  // 对应的持久化操作
}
export const actions = {
  getServerInfo ({ state, commit }) {
    return new Promise((resolve, reject) => {
      server.getServerInfo().then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  getRedisInfo ({ state, commit }) {
    return new Promise((resolve, reject) => {
      server.getRedisInfo().then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  getRedisKeys ({ state, commit }) {
    return new Promise((resolve, reject) => {
      server.getRedisKeys().then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  getRedisMemory ({ state, commit }) {
    return new Promise((resolve, reject) => {
      server.getRedisMemory().then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  }
}
