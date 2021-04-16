<template>
  <div class="navbar">
    <top-hamburger :is-active="sidebar.opened" />
    <!--    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" />-->
    <div class="left-menu">
      <div class="top-bar__item top-bar__item--show">
        <top-menu />
      </div>
      <!--      <div class="top-bar__item search">-->
      <!--        <top-search />-->
      <!--      </div>-->
    </div>

    <div class="right-menu">
      <template v-if="device!=='mobile'">
        <el-tooltip content="主题切换" effect="dark" placement="bottom">
          <div class="top-bar__item right-menu-item hover-effect">
            <top-theme />
          </div>
        </el-tooltip>
        <el-tooltip content="错误日志" effect="dark" placement="bottom">
          <div class="top-bar__item right-menu-item hover-effect">
            <top-logs />
          </div>
        </el-tooltip>
        <el-tooltip
          effect="dark"
          content="锁屏"
          placement="bottom"
        >
          <div class="top-bar__item right-menu-item hover-effect">
            <top-lock />
          </div>
        </el-tooltip>
        <div class="top-bar__item right-menu-item hover-effect">
          <top-msg />
        </div>

        <el-tooltip content="全屏" effect="dark" placement="bottom">
          <div class="top-bar__item right-menu-item hover-effect">
            <top-fullscreen id="screenfull" />
          </div>
        </el-tooltip>
      </template>

      <el-dropdown class="avatar-container hover-effect" trigger="click">
        <div class="avatar-wrapper">
          <el-avatar :size="40" :src="userInfo.avatar" @error="errorAvatarHandler">
            <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png">
          </el-avatar>
          <i class="el-icon-caret-bottom" />
          <!--          <img :src="userInfo.avatar" class="user-avatar">-->
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click="goProfile">
            个人中心
          </el-dropdown-item>
          <nuxt-link :to="{name: 'index-help'}">
            <el-dropdown-item>帮助</el-dropdown-item>
          </nuxt-link>
          <el-dropdown-item divided>
            <span style="display:block;" @click="logout">退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import topHamburger from './top-hamburger'
import topMenu from './top-menu'
// import topSearch from './top-search'

import topTheme from './top-theme'
import topLogs from './top-logs'
import topLock from './top-lock'
import topMsg from './top-msg'
import topFullscreen from './top-fullscreen'
import config from '@/config'

const activeUserTabAction = 'app/switchUserCenterActiveTab'
const logoutAction = 'sys/user/logout'
export default {
  components: {
    topHamburger,
    topMenu,
    // topSearch,

    topTheme,
    topLogs,
    topLock,
    topMsg,
    topFullscreen
  },
  data () {
    return {
    }
  },
  computed: {
    ...mapGetters([
      'device',
      'userInfo',
      'sidebar',
      'website'
    ])
  },
  methods: {
    errorAvatarHandler () {
      return true
    },
    goProfile () {
      this.$store.dispatch(activeUserTabAction, 'basic')
      this.$router.push({ name: 'index-profile-basic' })
    },
    logout () {
      this.$confirm('确认要退出系统吗?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$store.dispatch(logoutAction).then((res) => {
          const resCode = res.code
          if (resCode === 0) {
            this.$router.push({ name: config.defaultSettings.page.loginComponentName, query: { redirect: `${this.$route.fullPath}` } })
          } else {
            this.$message({
              type: 'error',
              message: '退出系统失败,请稍后再试!'
            })
          }
        }).catch(() => {
          this.$message({
            type: 'error',
            message: '退出系统异常,请稍后再试!'
          })
        })
      }).catch(() => {
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .navbar {
    height: 50px;
    overflow: hidden;
    position: relative;
    margin: 0 auto;
    background: #fff;
    box-shadow: 0 1px 4px rgba(0, 21, 41, .08);

    .hamburger-container {
      line-height: 46px;
      height: 100%;
      float: left;
      cursor: pointer;
      transition: background .3s;
      -webkit-tap-highlight-color: transparent;

      &:hover {
        background: rgba(0, 0, 0, .025)
      }
    }

    .breadcrumb-container {
      float: left;
    }

    .errLog-container {
      display: inline-block;
      vertical-align: top;
    }

    .left-menu {
      float: left;
      padding-left: 1px;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      font-size: inherit;
      font-weight: 400;
      height: 100%;
      width: 60%;
      box-sizing: border-box;
    }

    .top-bar__item {
      position: relative;
      display: inline-block;
      /*margin-right: 15px;*/
      height: 50px;
      &--show {
        display: inline-block !important;
      }
    }
    .search {
      width:  400px;
    }

    .right-menu {
      float: right;
      height: 100%;
      /*padding: 10px 10px;*/
      /*line-height: 50px;*/

      &:focus {
        outline: none;
      }

      .right-menu-item {
        display: inline-block;
        margin-right: 15px;
        /*height: 100%;*/
        padding: 8px 0px;
        font-size: 18px;
        color: #5a5e66;
        vertical-align: super;

        &.hover-effect {
          /*cursor: pointer;*/
          /*transition: background .3s;*/

          &:hover {
            /*background: rgba(0, 0, 0, .025)*/
          }
        }
      }

      .avatar-container {
        margin-right: 20px;
        height: 100%;
        vertical-align: bottom;

        .avatar-wrapper {
          /*margin-top: 5px;*/
          position: relative;

          .user-avatar {
            cursor: pointer;
            width: 40px;
            height: 40px;
            border-radius: 10px;
          }

          .el-icon-caret-bottom {
            cursor: pointer;
            position: absolute;
            right: -20px;
            top: 15px;
            font-size: 12px;
          }
        }
      }
    }
  }
</style>
