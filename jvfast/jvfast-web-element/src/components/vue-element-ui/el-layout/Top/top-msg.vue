<template>
  <el-popover
    placement="bottom"
    width="300"
    trigger="hover"
  >
    <div
      slot="reference"
      class="top-bar__item top-bar__item--show"
    >
      <el-badge v-if="notificationSize>0" :value="notifications.length+notices.length" :max="99">
        <i class="el-icon-bell" />
      </el-badge>
      <el-badge v-else>
        <i class="el-icon-bell" />
      </el-badge>
    </div>
    <el-tabs v-model="activeTabName" class="tab" @tab-click="clickTabHandler">
      <el-tab-pane name="first">
        <span slot="label">通知{{ notifications.length>0? '('+notifications.length+')': '' }}</span>
      </el-tab-pane>
      <el-tab-pane name="second">
        <span slot="label">系统消息{{ notices.length>0? '('+notices.length+')': '' }}</span>
      </el-tab-pane>
    </el-tabs>
    <div v-if="showNotification">
      <div v-if="notifications.length>0">
        <div class="msg">
          <nuxt-link v-for="(item,index) in notifications" :key="item.id" :to="{name:'index-profile-notification'}" class="item">
            <span class="title">{{ item.title }}</span>
            <time class="time">{{ formatTimestamp(item.updateTime) }}</time>
            <el-divider v-if="index!=notifications.length" />
          </nuxt-link>
        </div>
      </div>
      <div v-else>
        <el-empty desc="暂无通知" />
      </div>
    </div>
    <div v-if="showNotice">
      <div v-if="notices.length>0">
        <div class="msg">
          <nuxt-link v-for="(item, index) in notices" :key="item.id" :to="{name:'index-profile-notification'}" class="item">
            <span class="title">{{ item.title }}</span>
            <time class="time">{{ formatTimestamp(item.updateTime) }}</time>
            <el-divider v-if="index!=notices.length" />
          </nuxt-link>
        </div>
      </div>
      <div v-else>
        <el-empty desc="暂无消息" />
      </div>
    </div>
    <nuxt-link
      :to="{name:'index-profile-notification'}"
      class="top-msg__menu"
    >
      <el-button
        type="text"
        size="mini"
      >
        查看全部
      </el-button>
    </nuxt-link>
  </el-popover>
</template>

<script>
import { mapGetters } from 'vuex'
import { formatDate } from '@/util/DateUtil'

const noticeListAction = 'sys/notification/listSysNotification'
export default {
  name: 'TopMsg',
  data () {
    return {
      activeTabName: 'first',
      showNotification: true,
      notificationSize: 0,
      notifications: [],
      showNotice: false,
      notices: [],
      intervalId: null
    }
  },
  computed: { ...mapGetters(['userInfo']) },
  mounted () {
    this.fetchNotifications()
    this.fetchNotificationsIntervaler()
  },
  beforeDestroy () {
    clearInterval(this.intervalId)
  },
  methods: {
    clickTabHandler (tab, event) {
      if (tab.name === 'first') {
        this.showNotification = true
        this.showNotice = false
      }
      if (tab.name === 'second') {
        this.showNotice = true
        this.showNotification = false
      }
    },
    fetchNotificationsIntervaler () {
      this.intervalId = setInterval(() => {
        this.fetchNotifications()
      }, 60000)
    },
    fetchNotifications () {
      this.$store.dispatch(noticeListAction, { type: 1, userId: this.userInfo.id, readStatus: 0 }).then((res) => {
        const resCode = res.code
        const errorMsg = res.message
        if (resCode === 0) {
          const resData = res.data
          this.notifications = resData.filter((n) => { return n.notificationType !== 1 })
          this.notices = resData.filter((n) => { return n.notificationType === 1 })
          this.notificationSize = this.notifications.length + this.notices.length
        } else {
          this.$message({
            type: 'error',
            showClose: true,
            message: errorMsg || '查询通知信息失败,请稍后再试!'
          })
        }
      })
    },
    formatTimestamp (date) {
      return formatDate(date)
    }
  }

}
</script>

<style lang="scss" scoped>
  .top-msg {
    &__menu {
      float: right;
    }
  }
  .tab /deep/ .el-tabs__item.is-active {
    color: #409EFF;
    background-color: initial !important;
  }
  .msg {
    font-size: 12px;
    color: rgb(51, 51, 51);
    line-height: 1.5;
    .item {
      padding: 3px 5px;
    }
    a {
      width: 100%;
      padding: 8px 12px;
      border-top: 0px;
      box-sizing: border-box;
      line-height: inherit;
      cursor: pointer;
      font-family: inherit;
      font-size: inherit;
      vertical-align: middle;
      text-align: left;
      color: rgb(85, 85, 85);
      display: block;
      width: 100%;
      white-space: nowrap;
      padding: 0px;
      outline: medium;
      background: none;
      text-decoration: none;
      transition: all 0.3s ease-out 0s;
      border-width: initial;
      border-style: none;
      border-color: initial;
      border-image: initial;
      overflow: hidden;
      text-rendering: optimizelegibility;
      -webkit-font-smoothing: antialiased;
      span {
       float: left;
        margin-bottom: 4px;
        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;
        /*color: rgb(85, 85, 85);*/
        font-weight: 500;
        line-height: inherit;
        cursor: pointer;
        font-family: inherit;
        font-size: 14px;
        vertical-align: middle;
        text-align: left;
        // 设置超过字数限制
        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;
        width: 60%;
      }
      time {
        float: right;
        color: rgb(136, 136, 136);
        box-sizing: border-box;
        line-height: inherit;
        cursor: pointer;
        font-family: inherit;
        font-size: inherit;
        vertical-align: middle;
        text-align: left;
        white-space: nowrap;
      }
    }
  }
</style>
