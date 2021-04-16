import config from '@/config'
import request from '@/api/index'

const CONFIG_ADD_URL = config.serverUrls.API_BASE_URL + '/sys/config/add'
const CONFIG_UPDATE_URL = config.serverUrls.API_BASE_URL + '/sys/config/update'
const CONFIG_REMOVE_URL = config.serverUrls.API_BASE_URL + '/sys/config/del'
const CONFIG_REMOVE_BATCH_URL = config.serverUrls.API_BASE_URL + '/sys/config/delbatch'
const CONFIG_ID_URL = config.serverUrls.API_BASE_URL + '/sys/config/id'
const CONFIG_LIST_URL = config.serverUrls.API_BASE_URL + '/sys/config/list'
const CONFIG_PAGE_URL = config.serverUrls.API_BASE_URL + '/sys/config/page'

const cfg = {
  addSysConfig: (data) => {
    return request.$post(CONFIG_ADD_URL, data)
  },
  updateSysConfig: (data) => {
    return request.$post(CONFIG_UPDATE_URL, data)
  },
  removeSysConfig: (data) => {
    return request.$post(CONFIG_REMOVE_URL, data)
  },
  removeSysConfigs: (data) => {
    return request.$post(CONFIG_REMOVE_BATCH_URL, data)
  },
  getSysConfigById: (data) => {
    return request.$post(CONFIG_ID_URL, data)
  },
  getSysConfigList: (data) => {
    return request.$post(CONFIG_LIST_URL, data)
  },
  getSysConfigPageList: (data) => {
    return request.$post(CONFIG_PAGE_URL, data)
  }
  // 其它调用接口
}

export default cfg
