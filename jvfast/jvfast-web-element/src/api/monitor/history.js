import config from '@/config'
import request from '@/api/index'

const HISTORY_ADD_URL = config.serverUrls.API_BASE_URL + '/monitor/history/add'
const HISTORY_UPDATE_URL = config.serverUrls.API_BASE_URL + '/monitor/history/update'
const HISTORY_REMOVE_URL = config.serverUrls.API_BASE_URL + '/monitor/history/del'
const HISTORY_REMOVE_BATCH_URL = config.serverUrls.API_BASE_URL + '/monitor/history/delbatch'
const HISTORY_ID_URL = config.serverUrls.API_BASE_URL + '/monitor/history/id'
const HISTORY_LIST_URL = config.serverUrls.API_BASE_URL + '/monitor/history/list'
const HISTORY_PAGE_URL = config.serverUrls.API_BASE_URL + '/monitor/history/page'

const history = {
  addMonitorLoginHistory: (data) => {
    return request.$post(HISTORY_ADD_URL, data)
  },
  updateMonitorLoginHistory: (data) => {
    return request.$post(HISTORY_UPDATE_URL, data)
  },
  removeMonitorLoginHistory: (data) => {
    return request.$post(HISTORY_REMOVE_URL, data)
  },
  removeMonitorLoginHistorys: (data) => {
    return request.$post(HISTORY_REMOVE_BATCH_URL, data)
  },
  getMonitorLoginHistoryById: (data) => {
    return request.$post(HISTORY_ID_URL, data)
  },
  getMonitorLoginHistoryList: (data) => {
    return request.$post(HISTORY_LIST_URL, data)
  },
  getMonitorLoginHistoryPageList: (data) => {
    return request.$post(HISTORY_PAGE_URL, data)
  }
  // 其它调用接口
}

export default history
