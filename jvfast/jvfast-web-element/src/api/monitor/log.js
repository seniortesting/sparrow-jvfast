import config from '@/config'
import request from '@/api/index'

const LOG_ADD_URL = config.serverUrls.API_BASE_URL + '/monitor/log/add'
const LOG_ADD_BATCH_URL = config.serverUrls.API_BASE_URL + '/monitor/log/add/batch'
const LOG_UPDATE_URL = config.serverUrls.API_BASE_URL + '/monitor/log/update'
const LOG_REMOVE_URL = config.serverUrls.API_BASE_URL + '/monitor/log/del'
const LOG_REMOVE_BATCH_URL = config.serverUrls.API_BASE_URL + '/monitor/log/delbatch'
const LOG_ID_URL = config.serverUrls.API_BASE_URL + '/monitor/log/id'
const LOG_LIST_URL = config.serverUrls.API_BASE_URL + '/monitor/log/list'
const LOG_PAGE_URL = config.serverUrls.API_BASE_URL + '/monitor/log/page'

const log = {
  addMonitorLog: (data) => {
    return request.$post(LOG_ADD_URL, data)
  },
  addMonitorLogBatch: (data) => {
    return request.$post(LOG_ADD_BATCH_URL, data)
  },
  updateMonitorLog: (data) => {
    return request.$post(LOG_UPDATE_URL, data)
  },
  removeMonitorLog: (data) => {
    return request.$post(LOG_REMOVE_URL, data)
  },
  removeMonitorLogs: (data) => {
    return request.$post(LOG_REMOVE_BATCH_URL, data)
  },
  getMonitorLogById: (data) => {
    return request.$post(LOG_ID_URL, data)
  },
  getMonitorLogList: (data) => {
    return request.$post(LOG_LIST_URL, data)
  },
  getMonitorLogPageList: (data) => {
    return request.$post(LOG_PAGE_URL, data)
  }
  // 其它调用接口
}

export default log
