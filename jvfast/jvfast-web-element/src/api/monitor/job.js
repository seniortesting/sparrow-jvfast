import config from '@/config'
import request from '@/api/index'

const JOB_JOBS_URL = config.serverUrls.API_BASE_URL + '/monitor/task/jobs'
const JOB_LIST_URL = config.serverUrls.API_BASE_URL + '/monitor/task/page'
const JOB_ADD_URL = config.serverUrls.API_BASE_URL + '/monitor/task/add'
const JOB_UPDATE_URL = config.serverUrls.API_BASE_URL + '/monitor/task/update'
const JOB_START_URL = config.serverUrls.API_BASE_URL + '/monitor/task/start'
const JOB_STOP_URL = config.serverUrls.API_BASE_URL + '/monitor/task/stop'
const JOB_PAUSE_URL = config.serverUrls.API_BASE_URL + '/monitor/task/pause'
const JOB_RESUME_URL = config.serverUrls.API_BASE_URL + '/monitor/task/resume'
const JOB_DELETE_URL = config.serverUrls.API_BASE_URL + '/monitor/task/del'

const history = {
  listImplementJobs: () => {
    return request.$post(JOB_JOBS_URL)
  },
  pageJobTasks: (data) => {
    return request.$post(JOB_LIST_URL, data)
  },
  addJob: (data) => {
    return request.$post(JOB_ADD_URL, data)
  },
  updateJob: (data) => {
    return request.$post(JOB_UPDATE_URL, data)
  },
  startJob: (data) => {
    return request.$post(JOB_START_URL, data)
  },
  stopJob: (data) => {
    return request.$post(JOB_STOP_URL, data)
  },
  pauseJob: (data) => {
    return request.$post(JOB_PAUSE_URL, data)
  },
  resumeJob: (data) => {
    return request.$post(JOB_RESUME_URL, data)
  },
  deleteJob: (data) => {
    return request.$post(JOB_DELETE_URL, data)
  }
  // 其它调用接口
}

export default history
