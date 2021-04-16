import config from '@/config'
import request from '@/api/index'

const NOTICE_ADD_URL = config.serverUrls.API_BASE_URL + '/sys/notice/add'
const NOTICE_UPDATE_URL = config.serverUrls.API_BASE_URL + '/sys/notice/update'
const NOTICE_REMOVE_URL = config.serverUrls.API_BASE_URL + '/sys/notice/del'
const NOTICE_REMOVE_BATCH_URL = config.serverUrls.API_BASE_URL + '/sys/notice/delbatch'
const NOTICE_ID_URL = config.serverUrls.API_BASE_URL + '/sys/notice/id'
const NOTICE_LIST_URL = config.serverUrls.API_BASE_URL + '/sys/notice/list'
const NOTICE_PAGE_URL = config.serverUrls.API_BASE_URL + '/sys/notice/page'

const notice = {
  addSysNotice: (data) => {
    return request.$post(NOTICE_ADD_URL, data)
  },
  updateSysNotice: (data) => {
    return request.$post(NOTICE_UPDATE_URL, data)
  },
  removeSysNotice: (data) => {
    return request.$post(NOTICE_REMOVE_URL, data)
  },
  removeSysNotices: (data) => {
    return request.$post(NOTICE_REMOVE_BATCH_URL, data)
  },
  getSysNoticeById: (data) => {
    return request.$post(NOTICE_ID_URL, data)
  },
  getSysNoticeList: () => {
    return request.$post(NOTICE_LIST_URL)
  },
  getSysNoticePageList: (data) => {
    return request.$post(NOTICE_PAGE_URL, data)
  }
  // 其它调用接口
}

export default notice
