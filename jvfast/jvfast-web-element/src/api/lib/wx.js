import config from '@/config'
import request from '@/api/index'

const WX_MP_PAGE_URL = config.serverUrls.API_BASE_URL + '/wx/mp/cfg/page'

const wx = {
  getWxMpPageList: (data) => {
    return request.$post(WX_MP_PAGE_URL, data)
  },
  listWxMpMenu: (appId) => {
    const WX_MP_MENU_LIST_URL = config.serverUrls.API_BASE_URL + `/wx/mp/menu/${appId}/list`
    return request.$post(WX_MP_MENU_LIST_URL, {})
  },
  updateWxMpMenu: (appId, data) => {
    const WX_MENU_ADD_URL = config.serverUrls.API_BASE_URL + `/wx/mp/menu/${appId}/add`
    return request.$post(WX_MENU_ADD_URL, data)
  },
  // 其它调用接口
  listWxMpUser: (appId, data) => {
    const WX_MP_USER_LIST_URL = config.serverUrls.API_BASE_URL + `/wx/mp/user/${appId}/page`
    return request.$post(WX_MP_USER_LIST_URL, data)
  }
}

export default wx
