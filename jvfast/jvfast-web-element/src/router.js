import Vue from 'vue'
import Router from 'vue-router'
import { constRoutes } from '@/routes'
Vue.use(Router)

export function createRouter (ssrContext, createDefaultRouter, routerOptions) {
  const options = routerOptions || createDefaultRouter(ssrContext).options
  const router = new Router({
    ...options,
    routes: fixRoutes(options.routes)
  })
  // 设置路由
  return router
}
function fixRoutes (defaultRoutes) {
  // default routes that come from `pages/`
  return defaultRoutes.concat(constRoutes)
}
