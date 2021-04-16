import config from '@/config'
import request from '@/api/index'

const DEPT_ADD_URL = config.serverUrls.API_BASE_URL + '/sys/dept/add'
const DEPT_UPDATE_URL = config.serverUrls.API_BASE_URL + '/sys/dept/update'
const DEPT_REMOVE_URL = config.serverUrls.API_BASE_URL + '/sys/dept/del'
const DEPT_REMOVE_BATCH_URL = config.serverUrls.API_BASE_URL + '/sys/dept/delbatch'
const DEPT_ID_URL = config.serverUrls.API_BASE_URL + '/sys/dept/id'
const DEPT_LIST_URL = config.serverUrls.API_BASE_URL + '/sys/dept/list'
const DEPT_PAGE_URL = config.serverUrls.API_BASE_URL + '/sys/dept/page'
const DEPT_TREE_URL = config.serverUrls.API_BASE_URL + '/sys/dept/tree'

const dept = {
  addSysDept: (data) => {
    return request.$post(DEPT_ADD_URL, data)
  },
  updateSysDept: (data) => {
    return request.$post(DEPT_UPDATE_URL, data)
  },
  removeSysDept: (data) => {
    return request.$post(DEPT_REMOVE_URL, data)
  },
  removeSysDepts: (data) => {
    return request.$post(DEPT_REMOVE_BATCH_URL, data)
  },
  getSysDeptById: (data) => {
    return request.$post(DEPT_ID_URL, data)
  },
  getSysDeptList: (data) => {
    return request.$post(DEPT_LIST_URL, data)
  },
  getSysDeptPageList: (data) => {
    return request.$post(DEPT_PAGE_URL, data)
  },
  // 其它调用接口
  getSysDeptTreeList: (data) => {
    return request.$post(DEPT_TREE_URL, data)
  }
}

export default dept
