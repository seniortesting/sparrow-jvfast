import Vue from 'vue'
const addLogAction = 'log/addMonitorLog'
/**
 * 全局错误捕获
 * @param store
 */
export default ({ store }) => {
  Vue.config.errorHandler = function (err, vm, info) {
    const ip = ''
    Vue.nextTick(() => {
      store.dispatch(addLogAction, {
        userName: store.getters.userInfo.id ? store.getters.userInfo.id : '',
        requestIp: ip,
        businessType: 0,
        operationType: 2,
        businessTitle: err.message,
        requestErrorMsg: err.stack,
        requestParam: vm.name || '',
        requestResult: info.toString()
      })
    })
    if (process.env.NODE_ENV === 'development') {
      console.group('>>>>>> Vue信息info >>>>>>')
      console.error(info)
      console.groupEnd()
      console.group('>>>>>> Vue组件实例vm >>>>>>')
      console.log(vm)
      console.groupEnd()
      console.group('>>>>>> Vue错误err >>>>>>')
      console.log(err)
      console.groupEnd()
    }
  }
}
