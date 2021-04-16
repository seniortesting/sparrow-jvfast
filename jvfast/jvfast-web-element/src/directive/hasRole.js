/**
 * 角色权限处理
 * 使用方法: v-has-role = "['user','vip']"
 * Copyright (c) 2020 walter
 */
import config from '@/config'
export default {
  inserted (el, binding, vnode) {
    const { value } = binding
    const superAdmin = config.defaultSettings.permission.superAdmin
    const roles = window.$nuxt.$store.getters && window.$nuxt.$store.getters.roles
    if (value && Array.isArray(value) && value.length > 0) {
      const roleFlag = value
      const hasRole = roles.some((role) => {
        return superAdmin === role || roleFlag.includes(role)
      })
      if (!hasRole) {
        el.parentNode && el.parentNode.removeChild(el)
      }
    } else {
      throw new Error('请设置角色权限标签值"')
    }
  }
}
