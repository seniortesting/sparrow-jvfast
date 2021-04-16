import config from '@/config'
import request from '@/api/index'

const FILE_UPLOAD_URL = config.serverUrls.API_BASE_URL + '/sys/file/upload'
const FILE_DELETE_URL = config.serverUrls.API_BASE_URL + '/sys/file/del'
const upload = {
  UPLOAD_URL: FILE_UPLOAD_URL,
  uploadFile: (data) => {
    return request.$post(FILE_UPLOAD_URL, data, { headers: { 'Content-Type': 'multipart/form-data' } })
  },
  delFile: (data) => {
    return request.$post(FILE_DELETE_URL, data)
  }
}

export default upload
