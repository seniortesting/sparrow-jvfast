---
title: 如何打开一个新的tab？
---

基于jvfast的源码封装，从源代码: `src\components\vue-element-ui\el-layout\Sidebar\SidebarItem.vue`中可以知道对应的点击左侧侧边栏对应的新建tab代码。

## Javascript代码

参考上面的说明，js触发如下代码：

```
    handleNewTaskTab (row, index) {
      const setMenuAction = 'sys/user/setMenu'
      const tabItem = {
        external: false,
        // 如果是外部连接，直接使用url
        componentName: 'index-cvr-task',
        title: '全景任务'
      }
      this.$store.dispatch(setMenuAction, tabItem)
      if (tabItem.external) {
        this.$router.push({ name: 'index-iframe', query: { src: tabItem.componentName } })
      } else {
        this.$router.push({ name: tabItem.componentName })
      }
    },
```
