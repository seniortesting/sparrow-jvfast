<template>
  <div class="page-header-index-wide">
    <el-card :bordered="false" :body-style="{ padding: '16px 0', height: '100%' }" :style="{ height: '100%' }">
      <div :class="device" class="account-settings-info-main">
        <el-tabs :value="userCenterActiveTab" tab-position="left" style="height: 100%;width: 100%" @tab-click="tabClick">
          <el-tab-pane name="basic">
            <span slot="label">
              基本资料
            </span>
            <nuxt-child />
          </el-tab-pane>
          <el-tab-pane label="安全设置" name="security">
            <nuxt-child />
          </el-tab-pane>
          <el-tab-pane name="notification">
            <span slot="label">
              消息通知
              <!--              <el-badge :value="100" :max="99" class="badge-item" />-->
            </span>
            <nuxt-child />
          </el-tab-pane>
          <el-tab-pane label="反馈建议" name="feedback">
            <nuxt-child />
          </el-tab-pane>
        </el-tabs>
      </div>
    </el-card>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

const activeUserTabAction = 'app/switchUserCenterActiveTab'
export default {
  name: 'Profile',
  data () {
    return {}
  },
  computed: {
    ...mapGetters(['device', 'userCenterActiveTab'])
  },
  created () {
    const routeName = this.$route.name
    const activeRouteName = 'index-profile-' + this.userCenterActiveTab
    if (routeName !== activeRouteName) {
      this.$router.replace({ name: 'index-profile-' + this.userCenterActiveTab })
    }
  },
  methods: {
    tabClick (tab, event) {
      this.$store.dispatch(activeUserTabAction, tab.name)
      this.$router.push({ name: 'index-profile-' + tab.name })
    }
  }
}
</script>

<style lang="scss" scoped>

  .page-header-index-wide {
    margin: 10px;
  }

  .account-settings-info-main {
    width: 100%;
    display: flex;
    height: 100%;
    overflow: auto;

    &.mobile {
       display: block;
    }
  }
  .badge-item {
    /*margin-top: 10px;*/
    /*margin-left: 40px;*/
  }
</style>
