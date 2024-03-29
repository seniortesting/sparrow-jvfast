export const topMenu = [{
  id: '1',
  pid: '0',
  menuType: 1,
  menuIcon: 'iconfont icon-console',
  menuName: '工作台',
  menuOrder: 1,
  componentName: '',
  hidden: false,
  external: false,
  permission: '',
  remark: '',
  status: true,
  version: 0,
  updateTime: '2019-12-16 15:19:10'
}, {
  id: '2',
  pid: '0',
  menuType: 1,
  menuIcon: 'el-icon-setting',
  menuName: '系统管理',
  menuOrder: 2,
  componentName: '',
  hidden: false,
  external: false,
  permission: 'sys',
  remark: '',
  status: true,
  version: 0,
  updateTime: '2019-12-16 15:19:10'
}, {
  id: '3',
  pid: '0',
  menuType: 1,
  menuIcon: 'iconfont icon-monitor',
  menuName: '系统监控',
  menuOrder: 3,
  componentName: '',
  hidden: false,
  external: false,
  permission: 'monitor',
  remark: '',
  status: true,
  version: 0,
  updateTime: '2019-12-16 15:19:10'
}, {
  id: '4',
  pid: '0',
  menuType: 1,
  menuIcon: 'iconfont icon-third',
  menuName: '第三方平台',
  menuOrder: 4,
  componentName: '',
  hidden: false,
  external: false,
  permission: 'third',
  remark: '',
  status: true,
  version: 0,
  updateTime: '2020-02-01 10:45:43'
}, {
  id: '5',
  pid: '0',
  menuType: 1,
  menuIcon: 'iconfont icon-develop',
  menuName: '开发平台',
  menuOrder: 5,
  componentName: '',
  hidden: false,
  external: false,
  permission: 'develop',
  remark: '',
  status: true,
  version: 0,
  updateTime: '2019-12-16 15:19:10'
}]

export const routes = [{
  id: '1',
  pid: '0',
  children: [{
    id: '11',
    pid: '1',
    children: [{
      id: '601',
      pid: '11',
      menuType: 2,
      menuIcon: 'iconfont icon-demo',
      menuName: 'Demo管理',
      menuOrder: 1,
      componentName: 'index-demo-test',
      hidden: false,
      external: false,
      permission: '',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }],
    menuType: 2,
    menuIcon: 'iconfont icon-test',
    menuName: '测试功能',
    menuOrder: 1,
    componentName: '',
    hidden: false,
    external: false,
    permission: '',
    remark: '',
    status: true,
    version: 0,
    updateTime: '2019-12-16 15:19:10'
  }],
  menuType: 1,
  menuIcon: 'iconfont icon-console',
  menuName: '工作台',
  menuOrder: 1,
  componentName: '',
  hidden: false,
  external: false,
  permission: '',
  remark: '',
  status: true,
  version: 0,
  updateTime: '2019-12-16 15:19:10'
}, {
  id: '2',
  pid: '0',
  children: [{
    id: '201',
    pid: '2',
    children: [{
      id: '1001',
      pid: '201',
      menuType: 3,
      menuIcon: '',
      menuName: '用户查询',
      menuOrder: 1,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:user:query',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1008',
      pid: '201',
      menuType: 3,
      menuIcon: '',
      menuName: '部门查询',
      menuOrder: 1,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:post:query',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1002',
      pid: '201',
      menuType: 3,
      menuIcon: '',
      menuName: '用户新增',
      menuOrder: 2,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:user:add',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1009',
      pid: '201',
      menuType: 3,
      menuIcon: '',
      menuName: '部门新增',
      menuOrder: 2,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:post:add',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1003',
      pid: '201',
      menuType: 3,
      menuIcon: '',
      menuName: '用户修改',
      menuOrder: 3,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:user:update',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1010',
      pid: '201',
      menuType: 3,
      menuIcon: '',
      menuName: '部门修改',
      menuOrder: 3,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:post:update',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1004',
      pid: '201',
      menuType: 3,
      menuIcon: '',
      menuName: '用户删除',
      menuOrder: 4,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:user:remove',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1011',
      pid: '201',
      menuType: 3,
      menuIcon: '',
      menuName: '部门删除',
      menuOrder: 4,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:post:remove',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1005',
      pid: '201',
      menuType: 3,
      menuIcon: '',
      menuName: '用户导入',
      menuOrder: 5,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:user:import',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1012',
      pid: '201',
      menuType: 3,
      menuIcon: '',
      menuName: '部门导出',
      menuOrder: 5,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:post:export',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1006',
      pid: '201',
      menuType: 3,
      menuIcon: '',
      menuName: '用户导出',
      menuOrder: 6,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:user:export',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1007',
      pid: '201',
      menuType: 3,
      menuIcon: '',
      menuName: '用户密码重置',
      menuOrder: 7,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:user:resetpwd',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }],
    menuType: 2,
    menuIcon: 'el-icon-user',
    menuName: '用户管理',
    menuOrder: 1,
    componentName: 'index-sys-user-dept',
    hidden: false,
    external: false,
    permission: 'sys:user:list',
    remark: '',
    status: true,
    version: 0,
    updateTime: '2019-12-16 15:19:10'
  }, {
    id: '202',
    pid: '2',
    children: [{
      id: '1013',
      pid: '202',
      menuType: 3,
      menuIcon: '',
      menuName: '角色查询',
      menuOrder: 1,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:role:query',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1014',
      pid: '202',
      menuType: 3,
      menuIcon: '',
      menuName: '角色新增',
      menuOrder: 2,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:role:add',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1015',
      pid: '202',
      menuType: 3,
      menuIcon: '',
      menuName: '角色修改',
      menuOrder: 3,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:role:update',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1016',
      pid: '202',
      menuType: 3,
      menuIcon: '',
      menuName: '角色删除',
      menuOrder: 4,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:role:remove',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1017',
      pid: '202',
      menuType: 3,
      menuIcon: '',
      menuName: '角色导出',
      menuOrder: 5,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:role:export',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }],
    menuType: 2,
    menuIcon: 'iconfont icon-role',
    menuName: '角色管理',
    menuOrder: 2,
    componentName: 'index-sys-role',
    hidden: false,
    external: false,
    permission: 'sys:role:list',
    remark: '',
    status: true,
    version: 0,
    updateTime: '2019-12-16 15:19:10'
  }, {
    id: '203',
    pid: '2',
    children: [{
      id: '1018',
      pid: '203',
      menuType: 3,
      menuIcon: '',
      menuName: '菜单查询',
      menuOrder: 1,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:menu:query',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1019',
      pid: '203',
      menuType: 3,
      menuIcon: '',
      menuName: '菜单新增',
      menuOrder: 2,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:menu:add',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1020',
      pid: '203',
      menuType: 3,
      menuIcon: '',
      menuName: '菜单修改',
      menuOrder: 3,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:menu:update',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1021',
      pid: '203',
      menuType: 3,
      menuIcon: '',
      menuName: '菜单删除',
      menuOrder: 4,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:menu:remove',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1022',
      pid: '203',
      menuType: 3,
      menuIcon: '',
      menuName: '菜单导出',
      menuOrder: 5,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:menu:export',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }],
    menuType: 2,
    menuIcon: 'iconfont icon-menu',
    menuName: '菜单管理',
    menuOrder: 3,
    componentName: 'index-sys-menu',
    hidden: false,
    external: false,
    permission: 'sys:menu:list',
    remark: '',
    status: true,
    version: 0,
    updateTime: '2019-12-16 15:19:10'
  }, {
    id: '204',
    pid: '2',
    children: [{
      id: '1023',
      pid: '204',
      menuType: 3,
      menuIcon: '',
      menuName: '岗位查询',
      menuOrder: 1,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:post:query',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1024',
      pid: '204',
      menuType: 3,
      menuIcon: '',
      menuName: '岗位新增',
      menuOrder: 2,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:post:add',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1025',
      pid: '204',
      menuType: 3,
      menuIcon: '',
      menuName: '岗位修改',
      menuOrder: 3,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:post:update',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1026',
      pid: '204',
      menuType: 3,
      menuIcon: '',
      menuName: '岗位删除',
      menuOrder: 4,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:post:remove',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1027',
      pid: '204',
      menuType: 3,
      menuIcon: '',
      menuName: '岗位导出',
      menuOrder: 5,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:post:export',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }],
    menuType: 2,
    menuIcon: 'iconfont icon-post',
    menuName: '岗位管理',
    menuOrder: 4,
    componentName: 'index-sys-post',
    hidden: false,
    external: false,
    permission: 'sys:post:list',
    remark: '',
    status: true,
    version: 0,
    updateTime: '2019-12-16 15:19:10'
  }, {
    id: '205',
    pid: '2',
    children: [{
      id: '1028',
      pid: '205',
      menuType: 3,
      menuIcon: '',
      menuName: '字典查询',
      menuOrder: 1,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:dict:query',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1029',
      pid: '205',
      menuType: 3,
      menuIcon: '',
      menuName: '字典新增',
      menuOrder: 2,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:dict:add',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1030',
      pid: '205',
      menuType: 3,
      menuIcon: '',
      menuName: '字典修改',
      menuOrder: 3,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:dict:update',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1031',
      pid: '205',
      menuType: 3,
      menuIcon: '',
      menuName: '字典删除',
      menuOrder: 4,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:dict:remove',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1032',
      pid: '205',
      menuType: 3,
      menuIcon: '',
      menuName: '字典导出',
      menuOrder: 5,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:dict:export',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }],
    menuType: 2,
    menuIcon: 'iconfont icon-dict',
    menuName: '字典管理',
    menuOrder: 5,
    componentName: 'index-sys-dict',
    hidden: false,
    external: false,
    permission: 'sys:dict:list',
    remark: '',
    status: true,
    version: 0,
    updateTime: '2019-12-16 15:19:10'
  }, {
    id: '206',
    pid: '2',
    children: [{
      id: '1033',
      pid: '206',
      menuType: 3,
      menuIcon: '',
      menuName: '参数查询',
      menuOrder: 1,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:config:query',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1034',
      pid: '206',
      menuType: 3,
      menuIcon: '',
      menuName: '参数新增',
      menuOrder: 2,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:config:add',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1035',
      pid: '206',
      menuType: 3,
      menuIcon: '',
      menuName: '参数修改',
      menuOrder: 3,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:config:update',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1036',
      pid: '206',
      menuType: 3,
      menuIcon: '',
      menuName: '参数删除',
      menuOrder: 4,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:config:remove',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1037',
      pid: '206',
      menuType: 3,
      menuIcon: '',
      menuName: '参数导出',
      menuOrder: 5,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:config:export',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }],
    menuType: 2,
    menuIcon: 'iconfont icon-config',
    menuName: '系统设置',
    menuOrder: 6,
    componentName: 'index-sys-config',
    hidden: false,
    external: false,
    permission: 'sys:config:list',
    remark: '',
    status: true,
    version: 0,
    updateTime: '2019-12-16 15:19:10'
  }, {
    id: '207',
    pid: '2',
    children: [{
      id: '1038',
      pid: '207',
      menuType: 3,
      menuIcon: '',
      menuName: '公告查询',
      menuOrder: 1,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:notice:query',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2020-01-24 19:14:52'
    }, {
      id: '1039',
      pid: '207',
      menuType: 3,
      menuIcon: '',
      menuName: '公告新增',
      menuOrder: 2,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:notice:add',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2020-01-24 19:14:52'
    }, {
      id: '1040',
      pid: '207',
      menuType: 3,
      menuIcon: '',
      menuName: '公告修改',
      menuOrder: 3,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:notice:update',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2020-01-24 19:14:52'
    }, {
      id: '1041',
      pid: '207',
      menuType: 3,
      menuIcon: '',
      menuName: '公告删除',
      menuOrder: 4,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:notice:remove',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2020-01-24 19:14:52'
    }, {
      id: '1042',
      pid: '207',
      menuType: 3,
      menuIcon: '',
      menuName: '公告导出',
      menuOrder: 5,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'sys:notice:export',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2020-01-24 19:14:52'
    }],
    menuType: 2,
    menuIcon: 'iconfont icon-notice',
    menuName: '消息通知',
    menuOrder: 7,
    componentName: 'index-sys-notification',
    hidden: false,
    external: false,
    permission: 'sys:notice:list',
    remark: '',
    status: true,
    version: 0,
    updateTime: '2020-01-24 19:14:52'
  }, {
    id: '208',
    pid: '2',
    menuType: 2,
    menuIcon: 'iconfont icon-feedback',
    menuName: '意见反馈',
    menuOrder: 8,
    componentName: 'index-sys-feedback',
    hidden: false,
    external: false,
    permission: 'sys:feedback:list',
    remark: '',
    status: true,
    version: 0,
    updateTime: '2020-01-09 19:46:39'
  }],
  menuType: 1,
  menuIcon: 'el-icon-setting',
  menuName: '系统管理',
  menuOrder: 2,
  componentName: '',
  hidden: false,
  external: false,
  permission: 'sys',
  remark: '',
  status: true,
  version: 0,
  updateTime: '2019-12-16 15:19:10'
}, {
  id: '3',
  pid: '0',
  children: [{
    id: '301',
    pid: '3',
    children: [{
      id: '1043',
      pid: '301',
      menuType: 3,
      menuIcon: '',
      menuName: '概述查询',
      menuOrder: 1,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'monitor:server:query',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }],
    menuType: 2,
    menuIcon: 'iconfont icon-monitor-info',
    menuName: '服务概述',
    menuOrder: 1,
    componentName: 'index-monitor-info',
    hidden: false,
    external: false,
    permission: 'monitor:server:list',
    remark: '',
    status: true,
    version: 0,
    updateTime: '2019-12-16 15:19:10'
  }, {
    id: '303',
    pid: '3',
    menuType: 2,
    menuIcon: 'iconfont icon-login-history',
    menuName: '登录日志',
    menuOrder: 3,
    componentName: 'index-monitor-login-history',
    hidden: false,
    external: false,
    permission: 'monitor:login:list',
    remark: '',
    status: true,
    version: 0,
    updateTime: '2020-01-04 21:03:24'
  }, {
    id: '304',
    pid: '3',
    children: [{
      id: '501',
      pid: '304',
      children: [{
        id: '1045',
        pid: '501',
        menuType: 3,
        menuIcon: '',
        menuName: '前端日志查询',
        menuOrder: 1,
        componentName: '',
        hidden: false,
        external: false,
        permission: 'monitor:log:frontend:query',
        remark: '',
        status: true,
        version: 0,
        updateTime: '2019-12-16 15:19:10'
      }, {
        id: '1046',
        pid: '501',
        menuType: 3,
        menuIcon: '',
        menuName: '前端日志删除',
        menuOrder: 2,
        componentName: '',
        hidden: false,
        external: false,
        permission: 'monitor:log:frontend:remove',
        remark: '',
        status: true,
        version: 0,
        updateTime: '2019-12-16 15:19:10'
      }, {
        id: '1047',
        pid: '501',
        menuType: 3,
        menuIcon: '',
        menuName: '前端日志导出',
        menuOrder: 3,
        componentName: '',
        hidden: false,
        external: false,
        permission: 'monitor:log:frontend:export',
        remark: '',
        status: true,
        version: 0,
        updateTime: '2019-12-16 15:19:10'
      }],
      menuType: 2,
      menuIcon: 'iconfont icon-frontend',
      menuName: '前端日志',
      menuOrder: 1,
      componentName: 'index-monitor-log-frontend',
      hidden: false,
      external: false,
      permission: 'monitor:log:frontend:list',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '502',
      pid: '304',
      children: [{
        id: '1048',
        pid: '502',
        menuType: 3,
        menuIcon: '',
        menuName: '接口日志查询',
        menuOrder: 1,
        componentName: '',
        hidden: false,
        external: false,
        permission: 'monitor:log:service:query',
        remark: '',
        status: true,
        version: 0,
        updateTime: '2019-12-16 15:19:10'
      }, {
        id: '1049',
        pid: '502',
        menuType: 3,
        menuIcon: '',
        menuName: '接口日志删除',
        menuOrder: 2,
        componentName: '',
        hidden: false,
        external: false,
        permission: 'monitor:log:service:remove',
        remark: '',
        status: true,
        version: 0,
        updateTime: '2019-12-16 15:19:10'
      }, {
        id: '1050',
        pid: '502',
        menuType: 3,
        menuIcon: '',
        menuName: '接口日志导出',
        menuOrder: 3,
        componentName: '',
        hidden: false,
        external: false,
        permission: 'monitor:log:serivce:export',
        remark: '',
        status: true,
        version: 0,
        updateTime: '2019-12-16 15:19:10'
      }],
      menuType: 2,
      menuIcon: 'iconfont icon-backend',
      menuName: '接口日志',
      menuOrder: 2,
      componentName: 'index-monitor-log-backend',
      hidden: false,
      external: false,
      permission: 'monitor:log:service:list',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '503',
      pid: '304',
      children: [{
        id: '1051',
        pid: '503',
        menuType: 3,
        menuIcon: '',
        menuName: '错误日志查询',
        menuOrder: 1,
        componentName: '',
        hidden: false,
        external: false,
        permission: 'monitor:log:exception:query',
        remark: '',
        status: true,
        version: 0,
        updateTime: '2019-12-16 15:19:10'
      }, {
        id: '1052',
        pid: '503',
        menuType: 3,
        menuIcon: '',
        menuName: '错误日志删除',
        menuOrder: 2,
        componentName: '',
        hidden: false,
        external: false,
        permission: 'monitor:log:exception:remove',
        remark: '',
        status: true,
        version: 0,
        updateTime: '2019-12-16 15:19:10'
      }, {
        id: '1053',
        pid: '503',
        menuType: 3,
        menuIcon: '',
        menuName: '错误日志导出',
        menuOrder: 3,
        componentName: '',
        hidden: false,
        external: false,
        permission: 'monitor:log:exception:export',
        remark: '',
        status: true,
        version: 0,
        updateTime: '2019-12-16 15:19:10'
      }],
      menuType: 2,
      menuIcon: 'el-icon-error',
      menuName: '错误日志',
      menuOrder: 3,
      componentName: 'index-monitor-log-exception',
      hidden: false,
      external: false,
      permission: 'monitor:log:exception:list',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }],
    menuType: 2,
    menuIcon: 'iconfont icon-logs',
    menuName: '操作日志',
    menuOrder: 4,
    componentName: '',
    hidden: false,
    external: false,
    permission: 'monitor:log',
    remark: '',
    status: true,
    version: 0,
    updateTime: '2019-12-16 15:19:10'
  }, {
    id: '305',
    pid: '3',
    children: [{
      id: '1055',
      pid: '305',
      menuType: 3,
      menuIcon: '',
      menuName: '任务查询',
      menuOrder: 1,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'monitor:job:query',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1056',
      pid: '305',
      menuType: 3,
      menuIcon: '',
      menuName: '任务新增',
      menuOrder: 2,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'monitor:job:add',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1057',
      pid: '305',
      menuType: 3,
      menuIcon: '',
      menuName: '任务修改',
      menuOrder: 3,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'monitor:job:update',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1058',
      pid: '305',
      menuType: 3,
      menuIcon: '',
      menuName: '任务删除',
      menuOrder: 4,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'monitor:job:remove',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1059',
      pid: '305',
      menuType: 3,
      menuIcon: '',
      menuName: '任务状态修改',
      menuOrder: 4,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'monitor:job:status',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }, {
      id: '1060',
      pid: '305',
      menuType: 3,
      menuIcon: '',
      menuName: '任务导出',
      menuOrder: 5,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'monitor:job:export',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }],
    menuType: 2,
    menuIcon: 'iconfont icon-job',
    menuName: '定时任务',
    menuOrder: 5,
    componentName: 'index-monitor-job',
    hidden: false,
    external: false,
    permission: 'monitor:job:list',
    remark: '',
    status: true,
    version: 0,
    updateTime: '2019-12-16 15:19:10'
  }, {
    id: '306',
    pid: '3',
    children: [{
      id: '1061',
      pid: '306',
      menuType: 3,
      menuIcon: '',
      menuName: '缓存查询',
      menuOrder: 1,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'monitor:cache:query',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }],
    menuType: 2,
    menuIcon: 'el-icon-upload',
    menuName: '缓存动态',
    menuOrder: 6,
    componentName: 'index-monitor-redis',
    hidden: false,
    external: false,
    permission: 'monitor:cache:list',
    remark: '',
    status: true,
    version: 0,
    updateTime: '2019-12-16 15:19:10'
  }],
  menuType: 1,
  menuIcon: 'iconfont icon-monitor',
  menuName: '系统监控',
  menuOrder: 3,
  componentName: '',
  hidden: false,
  external: false,
  permission: 'monitor',
  remark: '',
  status: true,
  version: 0,
  updateTime: '2019-12-16 15:19:10'
}, {
  id: '4',
  pid: '0',
  children: [{
    id: '1223852900133928962',
    pid: '4',
    children: [{
      id: '1223863307238146049',
      pid: '1223852900133928962',
      children: [{
        id: '1224217948723380225',
        pid: '1223863307238146049',
        menuType: 3,
        menuIcon: '',
        menuName: '菜单新增',
        menuOrder: 1,
        componentName: '',
        hidden: false,
        external: false,
        permission: 'lib:wx:menu:add',
        remark: '',
        status: true,
        version: 0,
        updateTime: '2020-02-03 14:54:47'
      }, {
        id: '1224218191749742593',
        pid: '1223863307238146049',
        menuType: 3,
        menuIcon: '',
        menuName: '文章管理',
        menuOrder: 1,
        componentName: '',
        hidden: false,
        external: false,
        permission: 'lib:wx:article:add',
        remark: '',
        status: true,
        version: 0,
        updateTime: '2020-02-03 14:54:47'
      }],
      menuType: 2,
      menuIcon: 'el-icon-chat-dot-round',
      menuName: '公众号管理',
      menuOrder: 1,
      componentName: 'index-lib-wx-list',
      hidden: false,
      external: false,
      permission: 'sys:mp:list',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2020-02-03 14:54:47'
    }],
    menuType: 2,
    menuIcon: 'iconfont icon-weixin',
    menuName: '微信平台',
    menuOrder: 1,
    componentName: '',
    hidden: false,
    external: false,
    permission: 'sys:wx:list',
    remark: '',
    status: true,
    version: 0,
    updateTime: '2020-02-03 14:54:47'
  }, {
    id: '1223853934981971969',
    pid: '4',
    menuType: 2,
    menuIcon: 'el-icon-medal-1',
    menuName: '支付平台',
    menuOrder: 2,
    componentName: 'index-sys-config',
    hidden: false,
    external: false,
    permission: 'sys:pay:list',
    remark: '',
    status: true,
    version: 0,
    updateTime: '2020-02-02 14:21:30'
  }],
  menuType: 1,
  menuIcon: 'iconfont icon-third',
  menuName: '第三方平台',
  menuOrder: 4,
  componentName: '',
  hidden: false,
  external: false,
  permission: 'third',
  remark: '',
  status: true,
  version: 0,
  updateTime: '2020-02-01 10:45:43'
}, {
  id: '5',
  pid: '0',
  children: [{
    id: '401',
    pid: '5',
    children: [{
      id: '1062',
      pid: '401',
      menuType: 3,
      menuIcon: '',
      menuName: '接口文档查询',
      menuOrder: 1,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'develop:api:query',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }],
    menuType: 2,
    menuIcon: 'iconfont icon-api',
    menuName: '接口文档',
    menuOrder: 1,
    componentName: 'https://open.pingbook.top/jvfast-service/doc.html',
    hidden: false,
    external: true,
    permission: 'develop:api:list',
    remark: '',
    status: true,
    version: 0,
    updateTime: '2019-12-16 15:19:10'
  }, {
    id: '402',
    pid: '5',
    children: [{
      id: '1063',
      pid: '402',
      menuType: 3,
      menuIcon: '',
      menuName: '代码生成工具',
      menuOrder: 1,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'develop:code:query',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }],
    menuType: 2,
    menuIcon: 'iconfont icon-code',
    menuName: '代码生成',
    menuOrder: 2,
    componentName: 'index-develop-code-builder',
    hidden: false,
    external: false,
    permission: 'develop:code:list',
    remark: '',
    status: true,
    version: 0,
    updateTime: '2019-12-16 15:19:10'
  }, {
    id: '403',
    pid: '5',
    children: [{
      id: '1064',
      pid: '403',
      menuType: 3,
      menuIcon: '',
      menuName: '表单工具',
      menuOrder: 1,
      componentName: '',
      hidden: false,
      external: false,
      permission: 'develop:form:query',
      remark: '',
      status: true,
      version: 0,
      updateTime: '2019-12-16 15:19:10'
    }],
    menuType: 2,
    menuIcon: 'iconfont icon-builder',
    menuName: '表单设计',
    menuOrder: 3,
    componentName: 'https://crud.avuejs.com/',
    hidden: false,
    external: true,
    permission: 'develop:form:list',
    remark: '',
    status: true,
    version: 0,
    updateTime: '2019-12-16 15:19:10'
  }],
  menuType: 1,
  menuIcon: 'iconfont icon-develop',
  menuName: '开发平台',
  menuOrder: 5,
  componentName: '',
  hidden: false,
  external: false,
  permission: 'develop',
  remark: '',
  status: true,
  version: 0,
  updateTime: '2019-12-16 15:19:10'
}]
