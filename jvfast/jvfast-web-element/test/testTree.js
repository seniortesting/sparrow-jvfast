
let filterRouter = null
const filterRouterPermission = function (routers, componentName) {
  for (let i = 0; i < routers.length; i++) {
    if (routers[i].componentName === componentName) {
      filterRouter = routers[i]
      filterRouter.children = []
      break
    };
    if (routers[i].children) {
      filterRouterPermission(routers[i].children, componentName)
    };
  }
  return filterRouter
}

const routers = [
  {
    'id': '1',
    'pid': '0',
    'children': [
      {
        'id': '11',
        'pid': '1',
        'children': [
          {
            'id': '601',
            'pid': '11',
            'children': [
              {
                'id': '2001',
                'pid': '601',
                'menuType': 3,
                'menuIcon': '',
                'menuName': 'Demo列表查询',
                'menuOrder': 1,
                'componentName': '',
                'hidden': true,
                'external': false,
                'permission': '',
                'remark': '',
                'status': true,
                'version': 0,
                'updateTime': '2020-01-08 22:41:34'
              }
            ],
            'menuType': 2,
            'menuIcon': 'iconfont icon-demo',
            'menuName': 'Demo管理',
            'menuOrder': 1,
            'componentName': 'index-demo-test',
            'hidden': false,
            'external': false,
            'permission': '',
            'remark': '',
            'status': true,
            'version': 0,
            'updateTime': '2019-12-16 15:19:10'
          }
        ],
        'menuType': 2,
        'menuIcon': 'iconfont icon-test',
        'menuName': '测试功能',
        'menuOrder': 1,
        'componentName': '',
        'hidden': false,
        'external': false,
        'permission': '',
        'remark': '',
        'status': true,
        'version': 0,
        'updateTime': '2019-12-16 15:19:10'
      }
    ],
    'menuType': 1,
    'menuIcon': 'iconfont icon-console',
    'menuName': '工作台',
    'menuOrder': 1,
    'componentName': '',
    'hidden': false,
    'external': false,
    'permission': '',
    'remark': '',
    'status': true,
    'version': 0,
    'updateTime': '2019-12-16 15:19:10'
  }
]

const result = filterRouterPermission(routers, 'index-demo-test2')
console.log(result)
