import ElementUI from 'element-ui'
import menu from '@/api/sys/menu'
import config from '@/config'
import { ajax } from '@/util/DomUtil'

export const state = () => ({
  // 对应的持久化对象
})
export const mutations = {
  // 对应的持久化操作
}
export const actions = {
  // 添加
  addSysMenu ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      menu.addSysMenu(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 更新
  updateSysMenu ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      menu.updateSysMenu(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeSysMenu ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      menu.removeSysMenu(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 删除
  removeSysMenus ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      menu.removeSysMenus(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 通过Id单独查询列表
  getSysMenuById ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      menu.getSysMenuById(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 查询全部结果列表
  getSysMenuList ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      menu.getSysMenuList(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 分页查询结果
  getSysMenuPageList ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      menu.getSysMenuPageList(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  // 其它接口调用
  listSidebarMenuByRoleId ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      menu.listSidebarMenuByRoleId(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  listSidebarMenuTreeByMenuId ({ state, commit }, data) {
    return new Promise((resolve, reject) => {
      menu.listSidebarMenuTreeByMenuId(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  },
  listElementIcons ({ state, commit }) {
    // \element-ui\lib\theme-chalk\icon.css
    return new Promise((resolve, reject) => {
      const iconList = []
      const eleVersion = ElementUI.version
      const elementIconUrl = `//unpkg.com/element-ui@${eleVersion}/lib/theme-chalk/icon.css`
      ajax({
        url: elementIconUrl,
        success: (res) => {
          res.replace(/\.([^:^ ]+):[\s\S]+?content:"\\([^"]+)"/gi, (...item) => {
            iconList.push({
              name: item[1],
              value: `&#${item[2]};`
            })
          })
          resolve(iconList)
        },
        fail: (status) => {
          reject(status)
        }
      })
    })
  },
  listIconFontIcons ({ state, commit }) {
    return new Promise((resolve, reject) => {
      const iconList = []
      const iconfontVersions = config.iconfont
      const iconfontUrl = '//at.alicdn.com/t/$t.css'
      iconfontVersions.forEach((v) => {
        const iconFontUrl = iconfontUrl.replace('$t', v)
        ajax({
          url: iconFontUrl,
          success: (res) => {
            res.replace(/\.([^:^ ]+):[\s\S]+?content: "\\([^"]+)";/gi, (...item) => {
              iconList.push({
                name: item[1],
                value: `&#${item[2]};`
              })
            })
            resolve(iconList)
          },
          fail: (status) => {
            reject(status)
          }
        })
      })
    })
  }
}
