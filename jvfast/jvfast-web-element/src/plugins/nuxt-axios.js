import { setClient } from '@/api'
import cfg from '@/config'
import encrypt from '@/util/EncryptUtil'

const secretEnabled = cfg.secretEnabled
const secretKey = cfg.secretKey
const tokenHeader = cfg.tokenHeader
const tokenRefreshHeader = cfg.tokenRefreshHeader
const updateTokenAction = 'sys/user/updateToken'
const logoutAction = 'sys/user/logout'
export default ({ $axios, error, store, app }) => {
  $axios.defaults.timeout = 30000
  // 拦截请求
  $axios.onRequest((config) => {
    //  配置对应的请求token header
    const tokenInfo = store.getters.token
    if (tokenInfo) {
      config.headers[tokenHeader] = tokenInfo
    }
    // 是否进行加密数据请求包,只针对json put/post/delete格式的body数据进行加密,其他格式不进行加密
    if (secretEnabled) {
      // 如果对应的有data数据并且需要加解密
      const data = config.data
      if (data && data instanceof Object & config.headers.common.Accept.startsWith('application/json')) {
        config.headers['Content-Type'] = 'application/json;charset=UTF-8'
        const jsonStr = JSON.stringify(data)
        const encryptJsonData = encrypt.encode(secretKey, jsonStr)
        config.data = encryptJsonData
      }
    }
  })
  $axios.onResponse((response) => {
    const refreshToken = response.headers[tokenRefreshHeader]
    const data = response.data
    // 更新token
    if (refreshToken) {
      store.dispatch(updateTokenAction, refreshToken)
    }
    if (data) {
      const code = data.code
      // 进行json数据的解密
      if (secretEnabled && (typeof data === 'string')) {
        const decodeJsonStr = encrypt.decode(secretKey, data)
        response.data = JSON.parse(decodeJsonStr)
      }
      // 跳转页面
      if (code === 401) {
        store.dispatch(logoutAction).then(() => app.router.push({ name: cfg.defaultSettings.page.loginComponentName }))
      }
    }
  })
  $axios.onError((error) => {
    console.log('Request err: ', error)
  })
  // 暴露$axios对象,方便在外部文件访问对应的$axios实例
  setClient($axios)
  window.axios = $axios
}
