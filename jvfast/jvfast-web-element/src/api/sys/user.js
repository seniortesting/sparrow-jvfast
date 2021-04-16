import config from '@/config'
import request from '@/api/index'

// 获取接口的aouth bearer认证接口,不再采用
const CAPTCHA_URL = config.serverUrls.API_BASE_URL + '/sys/captcha/img'
const SMS_URL = config.serverUrls.API_BASE_URL + '/sys/sms/code'
// 用户相关请求地址
const USER_LOGIN_URL = config.serverUrls.API_BASE_URL + '/sys/user/login'
const USER_INFO_URL = config.serverUrls.API_BASE_URL + '/sys/user/info'
const USER_REG_URL = config.serverUrls.API_BASE_URL + '/sys/user/reg'
const USER_VERIFY_URL = config.serverUrls.API_BASE_URL + '/sys/user/verify'
const USER_FORGET_PASSWORD_URL = config.serverUrls.API_BASE_URL + '/sys/user/forgetpwd'
const USER_FORGET_PASSWORD_VERIFY_URL = config.serverUrls.API_BASE_URL + '/sys/user/forgetpwd/verify'
const USER_LOGOUT_URL = config.serverUrls.API_BASE_URL + '/sys/user/logout'
const USER_TOP_MENU_URL = config.serverUrls.API_BASE_URL + '/sys/user/menu'
const USER_MENU_URL = config.serverUrls.API_BASE_URL + '/sys/user/router'
const USER_CHILD_MENU_URL = config.serverUrls.API_BASE_URL + '/sys/menu/child'
// ---------------------------------------------
const USER_ADD_URL = config.serverUrls.API_BASE_URL + '/sys/user/add'
const USER_UPDATE_URL = config.serverUrls.API_BASE_URL + '/sys/user/update'
const USER_REMOVE_URL = config.serverUrls.API_BASE_URL + '/sys/user/del'
const USER_REMOVE_BATCH_URL = config.serverUrls.API_BASE_URL + '/sys/user/delbatch'
const USER_ID_URL = config.serverUrls.API_BASE_URL + '/sys/user/id'
const USER_LIST_URL = config.serverUrls.API_BASE_URL + '/sys/user/list'
const USER_PAGE_URL = config.serverUrls.API_BASE_URL + '/sys/user/page'

const USER_RESET_PASSWD_BATCH_URL = config.serverUrls.API_BASE_URL + '/sys/user/resetpwd'
const USER_ROLE_URL = config.serverUrls.API_BASE_URL + '/sys/user/role'
const USER_LOCK_URL = config.serverUrls.API_BASE_URL + '/sys/user/lock'

const user = {
  getCaptcha: () => {
    return request.$post(CAPTCHA_URL)
  },
  getSMS: (data) => {
    return request.$post(SMS_URL, data)
  },
  login: (data) => {
    return request.$post(USER_LOGIN_URL, data)
  },
  logout: () => {
    return request.$post(USER_LOGOUT_URL)
  },
  getUserInfo: () => {
    return request.$post(USER_INFO_URL)
  },
  register: (data) => {
    return request.$post(USER_REG_URL, data)
  },
  userVerify: (data) => {
    return request.$post(USER_VERIFY_URL, data)
  },
  forgetPwd: (data) => {
    return request.$post(USER_FORGET_PASSWORD_URL, data)
  },
  forgetPwdVerify: (data) => {
    return request.$post(USER_FORGET_PASSWORD_VERIFY_URL, data)
  },
  // 后台操作捷豹------------------------------
  getTopMenu: () => {
    return request.$post(USER_TOP_MENU_URL)
  },
  getMenu: () => {
    return request.$post(USER_MENU_URL)
  },
  getChildMenu: (data) => {
    return request.$post(USER_CHILD_MENU_URL, data)
  },
  addUser: (data) => {
    return request.$post(USER_ADD_URL, data)
  },
  updateUser: (data) => {
    return request.$post(USER_UPDATE_URL, data)
  },
  removeUserById: (data) => {
    return request.$post(USER_REMOVE_URL, data)
  },
  removeUsersById: (data) => {
    return request.$post(USER_REMOVE_BATCH_URL, data)
  },
  getUserById: (data) => {
    return request.$post(USER_ID_URL, data)
  },
  getUserList: (data) => {
    return request.$post(USER_LIST_URL, data)
  },
  getUserPageList: (data) => {
    return request.$post(USER_PAGE_URL, data)
  },
  resetUserPasswdBatch: (data) => {
    return request.$post(USER_RESET_PASSWD_BATCH_URL, data)
  },
  getUserRoleByUserId: (data) => {
    return request.$post(USER_ROLE_URL, data)
  },
  lockUser: (data) => {
    return request.$post(USER_LOCK_URL, data)
  }
}

export default user
