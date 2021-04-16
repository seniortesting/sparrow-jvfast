import config from '@/config'
import request from '@/api/index'

const DICT_TYPE_ADD_URL = config.serverUrls.API_BASE_URL + '/sys/dict/add/type'
const DICT_DATA_ADD_URL = config.serverUrls.API_BASE_URL + '/sys/dict/add/data'

const DICT_TYPE_UPDATE_URL = config.serverUrls.API_BASE_URL + '/sys/dict/update/type'
const DICT_DATA_UPDATE_URL = config.serverUrls.API_BASE_URL + '/sys/dict/update/data'

const DICT_TYPE_REMOVE_URL = config.serverUrls.API_BASE_URL + '/sys/dict/del/type'
const DICT_DATA_REMOVE_URL = config.serverUrls.API_BASE_URL + '/sys/dict/del/data'

const DICT_TYPE_REMOVE_BATCH_URL = config.serverUrls.API_BASE_URL + '/sys/dict/delbatch/type'
const DICT_DATA_REMOVE_BATCH_URL = config.serverUrls.API_BASE_URL + '/sys/dict/delbatch/data'

const DICT_TYPE_ID_URL = config.serverUrls.API_BASE_URL + '/sys/dict/id/type'
const DICT_DATA_ID_URL = config.serverUrls.API_BASE_URL + '/sys/dict/id/data'

const DICT_TYPE_LIST_URL = config.serverUrls.API_BASE_URL + '/sys/dict/list'
// 分页结果
const DICT_TYPE_PAGE_URL = config.serverUrls.API_BASE_URL + '/sys/dict/page/type'
const DICT_DATA_PAGE_URL = config.serverUrls.API_BASE_URL + '/sys/dict/page/data'

const dict = {
  addSysDictType: (data) => {
    return request.$post(DICT_TYPE_ADD_URL, data)
  },
  addSysDictData: (data) => {
    return request.$post(DICT_DATA_ADD_URL, data)
  },
  updateSysDictType: (data) => {
    return request.$post(DICT_TYPE_UPDATE_URL, data)
  },
  updateSysDictData: (data) => {
    return request.$post(DICT_DATA_UPDATE_URL, data)
  },
  removeSysDictType: (data) => {
    return request.$post(DICT_TYPE_REMOVE_URL, data)
  },
  removeSysDictData: (data) => {
    return request.$post(DICT_DATA_REMOVE_URL, data)
  },
  removeSysDictTypes: (data) => {
    return request.$post(DICT_TYPE_REMOVE_BATCH_URL, data)
  },
  removeSysDictDatas: (data) => {
    return request.$post(DICT_DATA_REMOVE_BATCH_URL, data)
  },
  getSysDictTypeById: (data) => {
    return request.$post(DICT_TYPE_ID_URL, data)
  },
  getSysDictDataById: (data) => {
    return request.$post(DICT_DATA_ID_URL, data)
  },
  getSysDictTypeList: () => {
    return request.$post(DICT_TYPE_LIST_URL)
  },
  getSysDictDataPageList: (data) => {
    return request.$post(DICT_DATA_PAGE_URL, data)
  },
  getSysDictTypePageList: (data) => {
    return request.$post(DICT_TYPE_PAGE_URL, data)
  }
  // 其它调用接口
}

export default dict
