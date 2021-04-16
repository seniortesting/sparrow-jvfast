import config from '@/config'
import request from '@/api/index'

const ${cfg.functionName?upper_case}_ADD_URL = config.serverUrls.API_BASE_URL + '/${package.ModuleName}/${cfg.functionName}/add'
const ${cfg.functionName?upper_case}_UPDATE_URL = config.serverUrls.API_BASE_URL + '/${package.ModuleName}/${cfg.functionName}/update'
const ${cfg.functionName?upper_case}_REMOVE_URL = config.serverUrls.API_BASE_URL + '/${package.ModuleName}/${cfg.functionName}/del'
const ${cfg.functionName?upper_case}_REMOVE_BATCH_URL = config.serverUrls.API_BASE_URL + '/${package.ModuleName}/${cfg.functionName}/delbatch'
const ${cfg.functionName?upper_case}_ID_URL = config.serverUrls.API_BASE_URL + '/${package.ModuleName}/${cfg.functionName}/id'
const ${cfg.functionName?upper_case}_LIST_URL = config.serverUrls.API_BASE_URL + '/${package.ModuleName}/${cfg.functionName}/list'
const ${cfg.functionName?upper_case}_PAGE_URL = config.serverUrls.API_BASE_URL + '/${package.ModuleName}/${cfg.functionName}/page'

const ${cfg.functionName} = {
  add${entity}: (data) => {
    return request.$post(${cfg.functionName?upper_case}_ADD_URL, data)
  },
  update${entity}: (data) => {
    return request.$post(${cfg.functionName?upper_case}_UPDATE_URL, data)
  },
  remove${entity}: (data) => {
    return request.$post(${cfg.functionName?upper_case}_REMOVE_URL, data)
  },
  remove${entity}s: (data) => {
    return request.$post(${cfg.functionName?upper_case}_REMOVE_BATCH_URL, data)
  },
  get${entity}ById: (data) => {
    return request.$post(${cfg.functionName?upper_case}_ID_URL, data)
  },
  list${entity}: (data) => {
    return request.$post(${cfg.functionName?upper_case}_LIST_URL, data)
  },
  page${entity}: (data) => {
    return request.$post(${cfg.functionName?upper_case}_PAGE_URL, data)
  }
  // -------------------------------- 其它接口调用
}

export default ${cfg.functionName}
