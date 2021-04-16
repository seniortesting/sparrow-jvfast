import config from '@/config'
import request from '@/api/index'

const TEST_ADD_URL = config.serverUrls.API_BASE_URL + '/demo/test/add'
const TEST_UPDATE_URL = config.serverUrls.API_BASE_URL + '/demo/test/update'
const TEST_REMOVE_URL = config.serverUrls.API_BASE_URL + '/demo/test/del'
const TEST_REMOVE_BATCH_URL = config.serverUrls.API_BASE_URL + '/demo/test/delbatch'
const TEST_ID_URL = config.serverUrls.API_BASE_URL + '/demo/test/id'
const TEST_LIST_URL = config.serverUrls.API_BASE_URL + '/demo/test/list'
const TEST_PAGE_URL = config.serverUrls.API_BASE_URL + '/demo/test/page'

const test = {
  addDemoTest: (data) => {
    return request.$post(TEST_ADD_URL, data)
  },
  updateDemoTest: (data) => {
    return request.$post(TEST_UPDATE_URL, data)
  },
  removeDemoTest: (data) => {
    return request.$post(TEST_REMOVE_URL, data)
  },
  removeDemoTests: (data) => {
    return request.$post(TEST_REMOVE_BATCH_URL, data)
  },
  getDemoTestById: (data) => {
    return request.$post(TEST_ID_URL, data)
  },
  listDemoTest: (data) => {
    return request.$post(TEST_LIST_URL, data)
  },
  pageDemoTest: (data) => {
    return request.$post(TEST_PAGE_URL, data)
  }
  // -------------------------------- 其它接口调用
}

export default test
