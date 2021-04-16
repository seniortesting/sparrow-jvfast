import config from '@/config'
import request from '@/api/index'

const MENU_ADD_URL = config.serverUrls.API_BASE_URL + '/sys/menu/add'
const MENU_UPDATE_URL = config.serverUrls.API_BASE_URL + '/sys/menu/update'
const MENU_REMOVE_URL = config.serverUrls.API_BASE_URL + '/sys/menu/del'
const MENU_REMOVE_BATCH_URL = config.serverUrls.API_BASE_URL + '/sys/menu/delbatch'
const MENU_ID_URL = config.serverUrls.API_BASE_URL + '/sys/menu/id'
const MENU_LIST_URL = config.serverUrls.API_BASE_URL + '/sys/menu/list'
const MENU_PAGE_URL = config.serverUrls.API_BASE_URL + '/sys/menu/page'

const MENU_ROLE_URL = config.serverUrls.API_BASE_URL + '/sys/menu/role'
const MENU_CHILD_URL = config.serverUrls.API_BASE_URL + '/sys/menu/child'

// const MENU_ICONFONT_URL = 'https://www.iconfont.cn/api/project/detail.json?pid=1529202&t=1578482608292&ctoken=CydylT6lgkGSoECOO_25MgIx'

const menu = {
  addSysMenu: (data) => {
    return request.$post(MENU_ADD_URL, data)
  },
  updateSysMenu: (data) => {
    return request.$post(MENU_UPDATE_URL, data)
  },
  removeSysMenu: (data) => {
    return request.$post(MENU_REMOVE_URL, data)
  },
  removeSysMenus: (data) => {
    return request.$post(MENU_REMOVE_BATCH_URL, data)
  },
  getSysMenuById: (data) => {
    return request.$post(MENU_ID_URL, data)
  },
  getSysMenuList: (data) => {
    return request.$post(MENU_LIST_URL, data)
  },
  getSysMenuPageList: (data) => {
    return request.$post(MENU_PAGE_URL, data)
  },
  // 其它调用接口
  listSidebarMenuByRoleId: (data) => {
    return request.$post(MENU_ROLE_URL, data)
  },
  listSidebarMenuTreeByMenuId: (data) => {
    return request.$post(MENU_CHILD_URL, data)
  }
}

export default menu
