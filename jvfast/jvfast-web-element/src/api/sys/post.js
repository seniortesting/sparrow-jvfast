import config from '@/config'
import request from '@/api/index'

const POST_ADD_URL = config.serverUrls.API_BASE_URL + '/sys/post/add'
const POST_UPDATE_URL = config.serverUrls.API_BASE_URL + '/sys/post/update'
const POST_REMOVE_URL = config.serverUrls.API_BASE_URL + '/sys/post/del'
const POST_REMOVE_BATCH_URL = config.serverUrls.API_BASE_URL + '/sys/post/delbatch'
const POST_ID_URL = config.serverUrls.API_BASE_URL + '/sys/post/id'
const POST_LIST_URL = config.serverUrls.API_BASE_URL + '/sys/post/list'
const POST_PAGE_URL = config.serverUrls.API_BASE_URL + '/sys/post/page'

const post = {
  addSysPost: (data) => {
    return request.$post(POST_ADD_URL, data)
  },
  updateSysPost: (data) => {
    return request.$post(POST_UPDATE_URL, data)
  },
  removeSysPost: (data) => {
    return request.$post(POST_REMOVE_URL, data)
  },
  removeSysPosts: (data) => {
    return request.$post(POST_REMOVE_BATCH_URL, data)
  },
  getSysPostById: (data) => {
    return request.$post(POST_ID_URL, data)
  },
  getSysPostList: (data) => {
    return request.$post(POST_LIST_URL, data)
  },
  getSysPostPageList: (data) => {
    return request.$post(POST_PAGE_URL, data)
  }
  // 其它调用接口
}

export default post
