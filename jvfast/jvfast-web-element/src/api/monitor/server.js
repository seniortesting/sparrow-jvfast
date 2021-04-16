import config from '@/config'
import request from '@/api/index'

const SERVER_INFO_URL = config.serverUrls.API_BASE_URL + '/monitor/server/info'

const REDIS_INFO_URL = config.serverUrls.API_BASE_URL + '/monitor/server/redis/info'
const REDIS_KEYS_URL = config.serverUrls.API_BASE_URL + '/monitor/server/redis/keys'
const REDIS_MEMORY_URL = config.serverUrls.API_BASE_URL + '/monitor/server/redis/memory'

const server = {

  getServerInfo: () => {
    return request.$post(SERVER_INFO_URL)
  },
  getRedisInfo: () => {
    return request.$post(REDIS_INFO_URL)
  },
  getRedisKeys: () => {
    return request.$post(REDIS_KEYS_URL)
  },
  getRedisMemory: () => {
    return request.$post(REDIS_MEMORY_URL)
  }
}

export default server
