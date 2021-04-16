import config from '@/config'
import request from '@/api/index'

const NOTIFICATION_ADD_URL = config.serverUrls.API_BASE_URL + '/sys/notification/add'
const NOTIFICATION_UPDATE_URL = config.serverUrls.API_BASE_URL + '/sys/notification/update'
const NOTIFICATION_REMOVE_URL = config.serverUrls.API_BASE_URL + '/sys/notification/del'
const NOTIFICATION_REMOVE_BATCH_URL = config.serverUrls.API_BASE_URL + '/sys/notification/delbatch'
const NOTIFICATION_ID_URL = config.serverUrls.API_BASE_URL + '/sys/notification/id'
const NOTIFICATION_LIST_URL = config.serverUrls.API_BASE_URL + '/sys/notification/list'
const NOTIFICATION_PAGE_URL = config.serverUrls.API_BASE_URL + '/sys/notification/page'
const NOTIFICATION_READ_URL = config.serverUrls.API_BASE_URL + '/sys/notification/read'

const notification = {
  addSysNotification: (data) => {
    return request.$post(NOTIFICATION_ADD_URL, data)
  },
  updateSysNotification: (data) => {
    return request.$post(NOTIFICATION_UPDATE_URL, data)
  },
  removeSysNotification: (data) => {
    return request.$post(NOTIFICATION_REMOVE_URL, data)
  },
  removeSysNotifications: (data) => {
    return request.$post(NOTIFICATION_REMOVE_BATCH_URL, data)
  },
  getSysNotificationById: (data) => {
    return request.$post(NOTIFICATION_ID_URL, data)
  },
  listSysNotification: (data) => {
    return request.$post(NOTIFICATION_LIST_URL, data)
  },
  pageSysNotification: (data) => {
    return request.$post(NOTIFICATION_PAGE_URL, data)
  },
  // -------------------------------- 其它接口调用
  readNotification: (data) => {
    return request.$post(NOTIFICATION_READ_URL, data)
  }
}

export default notification
