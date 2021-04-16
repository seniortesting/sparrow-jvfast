<template>
  <div v-if="!item.hidden" class="menu-wrapper">
    <template v-if="hasOneShowingChild(item.children, item) && (!onlyOneChild.children||onlyOneChild.noShowingChildren)">
      <!--      <app-link v-if="onlyOneChild.componentName" :to="onlyOneChild.componentName" :external="onlyOneChild.external">-->
      <div v-if="onlyOneChild.componentName" @click="menuItemClickHandler(onlyOneChild)">
        <el-menu-item :index="item.componentName" :class="{'submenu-title-noDropdown':!isNest}">
          <item :icon="onlyOneChild.menuIcon" :title="onlyOneChild.menuName" />
        </el-menu-item>
      </div>
      <!--    </app-link>-->
    </template>

    <el-submenu v-else ref="subMenu" :index="item.componentName" popper-append-to-body>
      <template slot="title">
        <item v-if="item.menuIcon" :icon="item.menuIcon" :title="item.menuName" />
      </template>
      <sidebar-item
        v-for="child in item.children"
        :key="child.id"
        :is-nest="true"
        :item="child"
        class="nest-menu"
      />
    </el-submenu>
  </div>
</template>

<script>
import Item from './Item'
// import AppLink from './Link'
import FixiOSBug from './FixiOSBug'

const setSidebarMenuActiveAction = 'sys/user/setSidebarMenuActive'
export default {
  name: 'SidebarItem',
  components: { Item },
  mixins: [FixiOSBug],
  props: {
    // route object
    item: {
      type: Object,
      required: true
    },
    isNest: {
      type: Boolean,
      default: false
    },
    basePath: {
      type: String,
      default: ''
    }
  },
  data () {
    // To fix https://github.com/PanJiaChen/vue-admin-template/issues/237
    // TODO: refactor with render function
    this.onlyOneChild = null
    return {}
  },
  methods: {
    hasOneShowingChild (children = [], parent) {
      const showingChildren = children.filter((item) => {
        if (item.hidden || item.menuType === 3) {
          return false
        } else {
          // Temp set(will be used if only has one showing child)
          this.onlyOneChild = item
          return true
        }
      })

      // When there is only one child router, the child router is displayed by default
      if (showingChildren.length === 1) {
        return true
      }

      // Show parent if there are no child router to display
      if (showingChildren.length === 0) {
        this.onlyOneChild = { ...parent, path: '', noShowingChildren: true }
        return true
      }

      return false
    },
    menuItemClickHandler (item) {
      const external = item.external
      const routePath = item.componentName
      // 设置tag
      this.$store.dispatch(setSidebarMenuActiveAction, item)
      if (external) {
        this.$router.push({ name: 'index-iframe', query: { src: routePath } })
      } else {
        this.$router.push({ name: routePath })
      }
    }
  }
}
</script>
