import config from '@/config'
import request from '@/api/index'

const CITY_ADD_URL = config.serverUrls.API_BASE_URL + '/sys/city/add'
const CITY_UPDATE_URL = config.serverUrls.API_BASE_URL + '/sys/city/update'
const CITY_REMOVE_URL = config.serverUrls.API_BASE_URL + '/sys/city/del'
const CITY_REMOVE_BATCH_URL = config.serverUrls.API_BASE_URL + '/sys/city/delbatch'
const CITY_ID_URL = config.serverUrls.API_BASE_URL + '/sys/city/id'
const CITY_LIST_URL = config.serverUrls.API_BASE_URL + '/sys/city/list'
const CITY_PAGE_URL = config.serverUrls.API_BASE_URL + '/sys/city/page'
const CITY_AREA_URL = config.serverUrls.API_BASE_URL + '/sys/city/area'

const city = {
  GET_AREA_DATA: CITY_AREA_URL,
  addSysCity: (data) => {
    return request.$post(CITY_ADD_URL, data)
  },
  updateSysCity: (data) => {
    return request.$post(CITY_UPDATE_URL, data)
  },
  removeSysCity: (data) => {
    return request.$post(CITY_REMOVE_URL, data)
  },
  removeSysCitys: (data) => {
    return request.$post(CITY_REMOVE_BATCH_URL, data)
  },
  getSysCityById: (data) => {
    return request.$post(CITY_ID_URL, data)
  },
  listSysCity: (data) => {
    return request.$post(CITY_LIST_URL, data)
  },
  pageSysCity: (data) => {
    return request.$post(CITY_PAGE_URL, data)
  }
  // -------------------------------- 其它接口调用
}

export default city
