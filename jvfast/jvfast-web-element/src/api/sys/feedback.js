import config from '@/config'
import request from '@/api/index'

const FEEDBACK_ADD_URL = config.serverUrls.API_BASE_URL + '/sys/feedback/add'
const FEEDBACK_UPDATE_URL = config.serverUrls.API_BASE_URL + '/sys/feedback/update'
const FEEDBACK_REMOVE_URL = config.serverUrls.API_BASE_URL + '/sys/feedback/del'
const FEEDBACK_REMOVE_BATCH_URL = config.serverUrls.API_BASE_URL + '/sys/feedback/delbatch'
const FEEDBACK_ID_URL = config.serverUrls.API_BASE_URL + '/sys/feedback/id'
const FEEDBACK_LIST_URL = config.serverUrls.API_BASE_URL + '/sys/feedback/list'
const FEEDBACK_PAGE_URL = config.serverUrls.API_BASE_URL + '/sys/feedback/page'

const feedback = {
  addSysFeedback: (data) => {
    return request.$post(FEEDBACK_ADD_URL, data)
  },
  updateSysFeedback: (data) => {
    return request.$post(FEEDBACK_UPDATE_URL, data)
  },
  removeSysFeedback: (data) => {
    return request.$post(FEEDBACK_REMOVE_URL, data)
  },
  removeSysFeedbacks: (data) => {
    return request.$post(FEEDBACK_REMOVE_BATCH_URL, data)
  },
  getSysFeedbackById: (data) => {
    return request.$post(FEEDBACK_ID_URL, data)
  },
  listSysFeedback: (data) => {
    return request.$post(FEEDBACK_LIST_URL, data)
  },
  pageSysFeedback: (data) => {
    return request.$post(FEEDBACK_PAGE_URL, data)
  }
  // -------------------------------- 其它接口调用
}

export default feedback
