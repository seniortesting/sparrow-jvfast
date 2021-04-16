<template>
  <div
    class="avue-tags"
  >
    <div
      :class="{'avue-tags__box--close': false}"
      class="avue-tags__box"
    >
      <el-tabs
        v-model="active"
        :closable="visitedViews.length!==1"
        type="card"
        @contextmenu.native="handleContextmenu"
        @tab-click="openTag"
        @edit="menuTag"
      >
        <el-tab-pane
          v-for="item in visitedViews"
          ref="tag"
          :key="item.id"
          :label="item.title"
          :name="item.componentName"
        />
      </el-tabs>
      <!-- tag盒子 -->
      <div
        v-if="contextmenuFlag"
        :style="{left:contentmenuX+'px',top:contentmenuY+'px'}"
        class="avue-tags__contentmenu"
      >
        <div
          class="item"
          @click="refreshSelectedTag"
        >
          刷新
        </div>
        <div
          v-if="!isAffix(selectedTag)"
          class="item"
          @click="closeSelectedTag"
        >
          关闭
        </div>
        <div
          class="item"
          @click="closeOthersTags"
        >
          关闭其它
        </div>
        <div
          class="item"
          @click="closeAllTags"
        >
          关闭所有
        </div>
      </div>
      <!--      更多菜单-->
      <el-dropdown class="avue-tags__menu">
        <el-button
          type="primary"
          size="mini"
        >
          更多
          <i class="el-icon-arrow-down el-icon--right" />
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="closeOthersTags">
            关闭其它
          </el-dropdown-item>
          <el-dropdown-item @click.native="closeAllTags">
            关闭所有
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import config from '@/config'

const addVisitedViewAction = 'app/addVisitedView'
// const updateVisitedViewAction = 'app/updateVisitedView'
const delCachedViewAction = 'app/delCachedView'
const delOthersViewsAction = 'app/delOthersViews'
const addViewAction = 'app/addView'
const delViewAction = 'app/delView'
const delAllViewsAction = 'app/delAllViews'
export default {
  name: 'Tags',
  data () {
    return {
      active: '',
      contentmenuX: '',
      contentmenuY: '',
      contextmenuFlag: false,
      selectedTag: {},
      affixTags: []
    }
  },
  computed: {
    ...mapGetters(['sidebarMenuActive', 'routes', 'visitedViews'])
  },
  watch: {
    $route () {
      this.addTags()
      this.setActiveIndex()
    },
    contextmenuFlag (value) {
      if (value) {
        window.addEventListener('click', this.watchContextmenu)
      } else {
        document.body.removeEventListener('click', this.watchContextmenu)
      }
    }
  },
  mounted () {
    this.initTags()
    // this.addTags()
    this.setActiveIndex()
  },
  methods: {
    isActive (route) {
      return route.componentName === this.$route.name
    },
    setActiveIndex () {
      const routeName = this.$route.query.src || this.$route.name
      const tag = this.findTag(routeName).tag
      if (tag) {
        this.active = tag.componentName
        this.selectedTag = tag
      }
    },
    isAffix (tag) {
      return tag.affix
    },
    filterAffixTags (routes) {
      let tags = []
      routes.forEach((route) => {
        if (route.affix) {
          tags.push(route)
        }
        if (route.children) {
          const tempTags = this.filterAffixTags(route.children)
          if (tempTags.length >= 1) {
            tags = [...tags, ...tempTags]
          }
        }
      })
      return tags
    },
    initTags () {
      const affixTags = this.affixTags = this.filterAffixTags(this.routes)
      for (const tag of affixTags) {
        // Must have tag name
        if (tag.componentName) {
          this.$store.dispatch(addVisitedViewAction, tag)
        }
      }
    },
    addTags () {
      const sidebarMenuActive = this.sidebarMenuActive
      if (Object.keys(sidebarMenuActive).length > 0) {
        this.$store.dispatch(addViewAction, sidebarMenuActive)
      }
      return false
    },
    // moveToCurrentTag () {
    //   const tags = this.$refs.tag
    //   this.$nextTick(() => {
    //     for (const tag of tags) {
    //       if (tag.name === this.$route.name) {
    //         // this.active = tag.name
    //         // this.selectedTag = tag
    //         // when query is different then update
    //         // if (tag.to.fullPath !== this.$route.fullPath) {
    //         //   this.$store.dispatch(updateVisitedViewAction, this.menu)
    //         // }
    //         break
    //       }
    //     }
    //   })
    // },
    watchContextmenu (event) {
      if (!this.$el.contains(event.target) || event.button !== 0) {
        this.contextmenuFlag = false
      }
      window.removeEventListener('click', this.watchContextmenu)
    },
    handleContextmenu (event) {
      let target = event.target
      // 解决 https://github.com/d2-projects/d2-admin/issues/54
      let flag = false
      if (target.className.includes('el-tabs__item')) { flag = true } else if (target.parentNode.className.includes('el-tabs__item')) {
        target = target.parentNode
        flag = true
      }
      if (flag) {
        event.preventDefault()
        event.stopPropagation()
        this.contentmenuX = event.clientX
        this.contentmenuY = event.clientY
        // this.tagName = target.getAttribute('aria-controls').slice(5)
        this.contextmenuFlag = true
      }
    },
    // remove
    menuTag (value, action) {
      if (action === 'remove') {
        const tag = this.findTag(value).tag
        this.$store.dispatch(delViewAction, tag).then(({ visitedViews }) => {
          if (this.isActive(tag)) {
            this.toLastView(visitedViews, tag)
          }
        })
      }
    },
    openTag (item) {
      let tag
      if (item.name) {
        tag = this.findTag(item.name).tag
      } else {
        tag = item
      }
      this.selectedTag = tag
      if (tag.external) {
        this.$router.push(
          { name: 'index-iframe', query: { src: tag.componentName } }
        )
      } else {
        this.$router.push({
          name: tag.componentName
        })
      }
    },
    findTag (value) {
      let tag, key
      this.visitedViews.map((item, index) => {
        if (item.componentName === value) {
          tag = item
          key = index
        }
      })
      return { tag, key }
    },
    refreshSelectedTag () {
      this.contextmenuFlag = false
      this.$store.dispatch(delCachedViewAction, this.selectedTag).then(() => {
        const { componentName, external } = this.selectedTag
        if (external) {
          this.$router.replace(
            { name: 'index-iframe', query: { src: componentName } }
          ).catch(() => { })
        } else {
          this.$router.replace({
            name: componentName
          }).catch(() => { })
        }
      })
    },
    closeSelectedTag () {
      this.contextmenuFlag = false
      this.$store.dispatch(delViewAction, this.selectedTag).then(({ visitedViews }) => {
        if (this.isActive(this.selectedTag)) {
          this.toLastView(visitedViews, this.selectedTag)
        }
      })
    },
    closeOthersTags () {
      this.contextmenuFlag = false
      if (this.selectedTag.external) {
        this.$router.push(
          { name: 'index-iframe', query: { src: this.selectedTag.componentName } }
        )
      } else {
        this.$router.push({ name: this.selectedTag.componentName })
      }
      this.$store.dispatch(delOthersViewsAction, this.selectedTag).then(() => {
        this.setActiveIndex()
      })
    },
    closeAllTags (view) {
      this.contextmenuFlag = false
      this.$store.dispatch(delAllViewsAction).then(({ visitedViews }) => {
        if (this.affixTags.some(tag => tag.componentName === this.selectedTag.componentName)) {
          return
        }
        this.toLastView(visitedViews, view)
      })
    },
    toLastView (visitedViews, view) {
      const latestView = visitedViews.slice(-1)[0]
      if (latestView) {
        if (latestView.external) {
          this.$router.push(
            { name: 'index-iframe', query: { src: latestView.componentName } }
          )
        } else {
          this.$router.push({
            name: latestView.componentName
          })
        }
      } else {
        // now the default is to redirect to the home page if there is no tags-view,
        // you can adjust it according to your needs.
        // eslint-disable-next-line no-lonely-if
        if (view.name === config.defaultSettings.page.homeComponentName) {
          // to reload home page
          this.$router.push({ name: config.defaultSettings.page.homeComponentName })
        } else {
          this.$router.push({ name: config.defaultSettings.page.homeComponentName })
        }
      }
    }
  }
}
</script>
<style lang="scss">

  .avue-tags {
    user-select: none;
    position: relative;
    padding: 0 10px;
    margin-bottom: 10px;
    box-sizing: border-box;
    overflow: hidden;
    border-top: 1px solid #f6f6f6;
    background-color: #fff;
    box-shadow: 0 1px 2px 0 rgba(0, 0, 0, .05);
  .el-tabs--card>.el-tabs__header {
    margin: 0;
  }
  .el-tabs--card>.el-tabs__header .el-tabs__nav,
  .el-tabs--card>.el-tabs__header .el-tabs__item,
  .el-tabs--card>.el-tabs__header {
    border: none;
  }
  .el-tabs--card>.el-tabs__header .el-tabs__item:first-child {
    border-left-width: 1px
  }
  .el-tabs--card>.el-tabs__header .el-tabs__item {
    margin: 0 3px;
    height: 40px;
    line-height:40px;
    font-size: 0.8em;
    font-weight: 600;
    color: #ccc;
  &.is-active {
     color: #409EFF;
     border-bottom: 3px solid #409EFF;
   }
  }
  .el-tabs__nav-prev,
  .el-tabs__nav-next {
    width: 20px;
    line-height: 34px;
    font-size: 18px;
    text-align: center;
  }
  &__box {
     position: relative;
     box-sizing: border-box;
     padding-right: 106px;
     width: 100%;
  &--close {
  .el-tabs__item {
  &:first-child {
     padding: 0 20px !important;
  .el-icon-close {
    display: none;
  }
  }
  }
  }
  }
  &__contentmenu{
     position: fixed;
     width:120px;
     background-color: #fff;
     z-index:1024;
     border-radius: 5px;
     box-shadow: 1px 2px 10px #ccc;
  .item{
    cursor: pointer;
    font-size: 14px;
    padding: 8px 20px 8px 15px;
    color: #606266;
  &:first-child{
     border-top-left-radius: 5px;
     border-top-right-radius: 5px;
   }
  &:last-child{
     border-bottom-left-radius: 5px;
     border-bottom-right-radius: 5px;
   }
  &:hover{
     background-color: #409EFF;
     color:#fff;
   }
  }
  }
  &__menu {
     position: absolute !important;
     top: 3px;
     right: 0;
     padding: 1px 0 0 15px;
     box-sizing: border-box;
   }
  }
</style>
