import config from '@/config'
import request from '@/api/index'

const ROLE_ADD_URL = config.serverUrls.API_BASE_URL + '/sys/role/add'
const ROLE_UPDATE_URL = config.serverUrls.API_BASE_URL + '/sys/role/update'
const ROLE_REMOVE_URL = config.serverUrls.API_BASE_URL + '/sys/role/del'
const ROLE_REMOVE_BATCH_URL = config.serverUrls.API_BASE_URL + '/sys/role/delbatch'
const ROLE_ID_URL = config.serverUrls.API_BASE_URL + '/sys/role/id'
const ROLE_LIST_URL = config.serverUrls.API_BASE_URL + '/sys/role/list'
const ROLE_PAGE_URL = config.serverUrls.API_BASE_URL + '/sys/role/page'

const role = {
  addSysRole: (data) => {
    return request.$post(ROLE_ADD_URL, data)
  },
  updateSysRole: (data) => {
    return request.$post(ROLE_UPDATE_URL, data)
  },
  removeSysRole: (data) => {
    return request.$post(ROLE_REMOVE_URL, data)
  },
  removeSysRoles: (data) => {
    return request.$post(ROLE_REMOVE_BATCH_URL, data)
  },
  getSysRoleById: (data) => {
    return request.$post(ROLE_ID_URL, data)
  },
  getSysRoleList: (data) => {
    return request.$post(ROLE_LIST_URL, data)
  },
  getSysRolePageList: (data) => {
    return request.$post(ROLE_PAGE_URL, data)
  }
  // 其它调用接口
}

export default role
