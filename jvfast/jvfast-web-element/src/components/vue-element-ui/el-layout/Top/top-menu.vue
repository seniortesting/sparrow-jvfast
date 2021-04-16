<template>
  <div class="top-menu">
    <el-menu
      :default-active="topMenuIndex"
      mode="horizontal"
      text-color="#333"
    >
      <template v-for="(item,index) in topMenus">
        <el-menu-item
          :key="item.id"
          :index="item.id"
          @click.native="topMenuItemClickHandle(item, index)"
        >
          <template slot="title">
            <i :class="item.menuIcon" />
            <!--            <i v-else :class="['iconfont',item.menuIcon]" />-->
            <span>{{ item.menuName }}</span>
          </template>
        </el-menu-item>
      </template>
    </el-menu>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

const getTopMenusAction = 'sys/user/getTopMenus'
const setTopMenuIndexAction = 'sys/user/setTopMenuIndex'
const setSidebarMenusAction = 'sys/user/setSidebarMenus'
export default {
  name: 'TopMenu',
  data () {
    return {
      items: []
    }
  },
  computed: {
    ...mapGetters(['routes', 'topMenus', 'topMenuIndex'])
  },
  created () {
    this.getTopMenus()
  },
  methods: {
    topMenuItemClickHandle (item, index) {
      const menuId = item.id
      const external = item.external
      // 设置激活的顶部菜单
      this.$store.dispatch(setTopMenuIndexAction, menuId)
      if (!external) {
        const foundItem = this.routes.find(item => item.id === menuId)
        if (foundItem) {
          const sidebarMenus = foundItem.children
          if (sidebarMenus) {
            this.$store.dispatch(setSidebarMenusAction, sidebarMenus)
          } else {
            this.$message({
              type: 'error',
              message: '该顶部导航菜单对应的侧边栏没有设置菜单!'
            })
          }
        } else {
          this.$message({
            type: 'error',
            message: '顶部导航菜单没有对应的侧边栏菜单!'
          })
        }
      } else {
        this.$router.replace(
          { name: 'index-iframe', query: { src: item.componentName } }
        )
      }
    },
    getTopMenus () {
      this.$store.dispatch(getTopMenusAction)
    }
  }
}
</script>
