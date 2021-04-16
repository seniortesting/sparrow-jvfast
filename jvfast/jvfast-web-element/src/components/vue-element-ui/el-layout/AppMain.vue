<template>
  <section class="app-main">
    <!--    <transition name="fade-transform" mode="out-in">-->
    <transition>
      <!--      <keep-alive :include="cachedViews">-->
      <!--        <router-view :key="key" />-->
      <!--      </keep-alive>-->
      <nuxt-child :keep-alive="false" />
    </transition>
  </section>
</template>

<script>
export default {
  name: 'AppMain',
  computed: {
    cachedViews () {
      return this.$store.state.app.cachedViews
    },
    key () {
      return this.$route.path
    }
  }
}
</script>

<style lang="scss" scoped>
.app-main {
  /* 50= navbar  50  */
  min-height: calc(100vh - 50px);
  width: 100%;
  position: relative;
  overflow: hidden;
}

.fixed-header+.app-main {
  padding-top: 50px;
}

.hasTagsView {
  .app-main {
    /* 84 = navbar + tags-view = 50 + 40 */
    min-height: calc(100vh - 94px);
  }

  .fixed-header+.app-main {
    padding-top: 98px;
  }
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {
  .fixed-header {
    padding-right: 15px;
  }
}
</style>
