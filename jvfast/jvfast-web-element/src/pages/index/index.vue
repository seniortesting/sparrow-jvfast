<template>
  <!--  Home Page-->
  <div class="page-header-index-wide">
    <div class="detail">
      <div class="main">
        <div class="row">
          <div class="avatar" @click="avatarClickHandler">
            <el-avatar :size="60" :src="userInfo.avatar" @error="errorAvatarHandler">
              <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png">
            </el-avatar>
          </div>
          <div class="headerContent">
            <div>
              <div class="title">
                {{ welcomeMsg() }} {{ userInfo.userName }}, 上次登录时间: {{ userInfo.lastLoginTime }}
              </div>
            </div>
            <!--            广告-->
            <div v-if="enableAds" class="ads">
              <in-feed-adsense
                data-ad-layout-key="-6t+ed+2i-1n-4w"
                data-ad-slot="2627967536"
              />
              <!--              <in-article-adsense-->
              <!--                ins-style="display:block; text-align:center;"-->
              <!--                data-ad-slot="7727965566"-->
              <!--              />-->
            </div>
          </div>
          <div class="extra">
            <div>
              <el-row class="more-info">
                <el-col :span="8">
                  <div class="head-info">
                    <span>项目数</span>
                    <p>{{ totalInfo.projects }}</p>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div class="head-info">
                    <span>团队数</span>
                    <p>{{ totalInfo.teams }}</p>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div class="head-info">
                    <span>项目访问</span>
                    <p>{{ totalInfo.visits }}</p>
                  </div>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="release">
            <el-card>
              <div class="block">
                <el-timeline>
                  <el-timeline-item v-for="item in timelines" :key="item.id" :timestamp="item.date" :color="item.color" placement="top">
                    <el-card>
                      <h4>{{ item.title }}</h4>
                      <div style="margin-top: 10px" v-html="item.desc" />
                    </el-card>
                  </el-timeline-item>
                </el-timeline>
              </div>
            </el-card>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import config from '@/config'
export default {
  name: 'Home',
  data () {
    return {
      enableAds: config.website.enableAds,
      totalInfo: {
        projects: 0,
        teams: 0,
        visits: 0
      },
      timelines: []
    }
  },
  computed: {
    ...mapGetters(['website', 'userInfo'])
  },
  mounted () {
    this.fetchReleaseNotes()
  },
  methods: {
    welcomeMsg () {
      const time = new Date()
      const hour = time.getHours()
      return hour < 9 ? '早上好' : hour <= 11 ? '上午好' : hour <= 13 ? '中午好' : hour < 19 ? '下午好' : '晚上好'
    },
    errorAvatarHandler () {
      return true
    },
    avatarClickHandler () {
      this.$router.push({ name: 'index-profile-basic' })
    },
    fetchReleaseNotes () {
      const releaseNotes = [{
        id: 1110,
        date: '2020/01/12',
        title: 'v1.0.0',
        desc: '<h3>初始化版本</h3>'
      }, {
        id: 1111,
        date: '2020/01/13',
        title: 'v1.0.3',
        desc: '<h3>修复: 反馈和菜单问题</h3>'
      }, {
        id: 1112,
        date: '2020/01/25',
        title: 'v1.1.0',
        desc: '<ul><li>功能: 添加消息通知模块功能</li></ul>'
      }, {
        id: 1113,
        date: '2020/01/31',
        title: 'v1.2.0',
        desc: '<ul><li>功能: 增加富文本，后台短信，邮件，oss存储功能验证</li></ul>'
      },
      {
        id: 1114,
        date: '2020/02/04',
        title: 'v1.3.5',
        desc: '<ul><li>修复: 公众号问题</li></ul>'
      }, {
        id: 1115,
        date: '2020/02/07',
        title: 'v1.3.6',
        desc: '<ul><li>修复: 公众号分页粉丝拉取</li><li>功能: 保存微信用户增加appid字段</li><li>功能: 忘记密码确认页面</li></ul>'
      }, {
        id: 1116,
        date: '2020/02/08',
        title: 'v1.4.6',
        desc: '<ul><li>功能: 代码工具升级</li><li>修复: 通知显示模板消息信息不可读</li><li>修复: 多公众号自动回复信息路由,开放平台字段添加</li><li>修复: 短信发送的过期时间没有设置</li><li>功能: 增加uniapp代码模板</li><li>功能: 省市接口数据</li><li>修复: 前台excel导出的数据不可读问题</li><li>升级: avue升级到2.3.8</li><li>功能: 忘记密码修改密码页面重构完成</li><li>修复: 顶部通知栏消息提醒显示数字错误</li></ul>'
      }, {
        id: 1117,
        date: '2020/02/15',
        title: 'v1.4.8',
        desc: '<ul><li>修复: 微信菜单异常查询问题</li><li>修复: 增加地图组件，修复地图key不匹配问题</li><li>修复: 权限指令不起作用</li></ul>'
      },
      {
        id: 1118,
        date: '2020/02/16',
        title: 'v1.4.9',
        desc: '<ul><li>修复: 微信小程序换取sessionKey接口调整</li><li>修复: 选择地图插件的提交按钮被反馈按钮遮挡</li><li>特性：升级avue到2.4.0</li></ul>'
      },
      {
        id: 1119,
        date: '2020/02/26',
        title: 'v1.4.10',
        desc: '<ul><li>修复: 微信jsapi接口签名错误</li></ul>'
      },
      {
        id: 1120,
        date: '2020/03/13',
        title: 'v1.4.11',
        desc: '<ul><li>修复: vue-loading插件问题</li></ul>'
      },
      {
        id: 1121,
        date: '2020/06/17',
        title: 'v1.4.12',
        desc: '<ul><li>重构: 删除avue库，采用element-ui框架</li></ul>',
        color: '#0bbd87'
      }
      ].reverse()
      this.timelines = releaseNotes
    }
  },
  head () {
    return {
      title: '首页 | ' + this.website.name,
      meta: [
        { hid: 'description', name: 'description', content: '首页' }
      ]
    }
  }
}
</script>

<style lang="scss" scoped>
  .page-header-index-wide {
    background: #fff;
    padding: 16px 32px 0;
    border-bottom: 1px solid #e8e8e8;

    .detail {
      display: -webkit-box;
      display: -ms-flexbox;
      display: flex;

      .main {
        width: 100%;
        -webkit-box-flex: 0;
        -ms-flex: 0 1 auto;
        flex: 0 1 auto;
        .row {
          display: -webkit-box;
          display: -ms-flexbox;
          display: flex;
          width: 100%;
          .avatar {
            margin-bottom: 16px;
            -webkit-box-flex: 0;
            -ms-flex: 0 1 72px;
            flex: 0 1 72px;
            margin: 0 24px 8px 0;
            margin-top: 0px;
            margin-right: 24px;
            margin-bottom: 8px;
            margin-left: 0px;
            cursor: pointer;
          }
          .headerContent {
            -webkit-box-flex: 1;
            -ms-flex: auto;
            flex: auto;
            color: rgba(0,0,0,.45);
            line-height: 22px;
            margin-top: 12px;
          }
          .release {
            border: 3px;
            width: 95%;
          }
        }
        .title {
          font-size: 20px;
          line-height: 28px;
          font-weight: 500;
          color: rgba(0,0,0,.85);
          margin-bottom: 16px;
          -webkit-box-flex: 1;
          -ms-flex: auto;
          flex: auto;
        }
        .extra {
          -webkit-box-flex: 0;
          -ms-flex: 0 1 auto;
          flex: 0 1 auto;
          margin-left: 88px;
          min-width: 242px;
          text-align: center;
        }
      }
    }

    .head-info {
      position: relative;
      text-align: left;
      padding: 0 32px 0 0;
      min-width: 125px;
      span {
        color: rgba(0,0,0,.45);
        display: inline-block;
        font-size: 14px;
        line-height: 22px;
        margin-bottom: 4px;
      }
      p {
        color: rgba(0,0,0,.85);
        font-size: 30px;
        line-height: 32px;
        margin: 0;
      }
    }

  }
</style>
