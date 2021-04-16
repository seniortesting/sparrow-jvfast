/**
 * 角色权限处理
 * 使用方法: v-has-permission = "['user:delete','vip:edit']"
 * Copyright (c) 2020 walter
 */
import config from '@/config'
export default {
  inserted (el, binding, vnode) {
    const { value } = binding
    const allPermission = config.defaultSettings.permission.superPermission
    const permissions = window.$nuxt.$store.getters && window.$nuxt.$store.getters.permissions
    if (value && Array.isArray(value) && value.length > 0) {
      const permissionValue = value
      const hasPermissions = permissions.some((permission) => {
        return allPermission === permission || permissionValue.includes(permission)
      })
      if (!hasPermissions) {
        el.parentNode && el.parentNode.removeChild(el)
      }
    } else {
      throw new Error('请设置操作权限标签值')
    }
  }
}
