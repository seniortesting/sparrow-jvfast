import createPersistedState from 'vuex-persistedstate'
import SecureLS from 'secure-ls'
import config from '@/config'
import { persistPaths } from '@/store'

const ls = new SecureLS({
  encodingType: 'aes',
  isCompression: false,
  encryptionSecret: config.secretKey
})
/**
 * 持久化对应的vuex中的对象
 * @type {SecureLS}
 */
export default ({ store }) => {
  // window.onNuxtReady(() => {
  if (config.storeEnabled) {
    createPersistedState({
      key: config.storeKey,
      // 设置缓存哪些state数据
      paths: persistPaths,
      storage: {
        getItem: key => ls.get(key),
        setItem: (key, value) => ls.set(key, value),
        removeItem: key => ls.remove(key)
      }
    })(store)
  } else {
    createPersistedState({
      key: config.storeKey,
      // 设置缓存哪些state数据
      paths: persistPaths
    })(store)
  }
  // })
}
