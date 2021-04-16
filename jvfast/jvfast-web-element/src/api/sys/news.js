import config from '@/config'
import request from '@/api/index'

const NEWS_ADD_URL = config.serverUrls.API_BASE_URL + '/sys/news/add'
const NEWS_UPDATE_URL = config.serverUrls.API_BASE_URL + '/sys/news/update'
const NEWS_REMOVE_URL = config.serverUrls.API_BASE_URL + '/sys/news/del'
const NEWS_REMOVE_BATCH_URL = config.serverUrls.API_BASE_URL + '/sys/news/delbatch'
const NEWS_ID_URL = config.serverUrls.API_BASE_URL + '/sys/news/id'
const NEWS_LIST_URL = config.serverUrls.API_BASE_URL + '/sys/news/list'
const NEWS_PAGE_URL = config.serverUrls.API_BASE_URL + '/sys/news/page'

const news = {
  addSysNews: (data) => {
    return request.$post(NEWS_ADD_URL, data)
  },
  updateSysNews: (data) => {
    return request.$post(NEWS_UPDATE_URL, data)
  },
  removeSysNews: (data) => {
    return request.$post(NEWS_REMOVE_URL, data)
  },
  removeSysNewss: (data) => {
    return request.$post(NEWS_REMOVE_BATCH_URL, data)
  },
  getSysNewsById: (data) => {
    return request.$post(NEWS_ID_URL, data)
  },
  getSysNewsList: (data) => {
    return request.$post(NEWS_LIST_URL, data)
  },
  getSysNewsPageList: (data) => {
    return request.$post(NEWS_PAGE_URL, data)
  }
  // 其它调用接口
}

export default news
