<template>
  <div :class="classObj" class="app-wrapper">
    <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside" />
    <sidebar class="sidebar-container" />
    <div :class="{hasTagsView:showTagsView}" class="main-container">
      <div :class="{'fixed-header':fixedHeader}">
        <navbar />
        <tags-view v-if="showTagsView" />
      </div>
      <app-main />
      <div class="jvfast-footer animated fadeInUp">
        <span>V{{ website.version }}（Build {{ website.buildVersion }}） ©{{ new Date().getFullYear() }} <a :href="website.url">{{ website.name }}</a>
          <a href="http://www.beian.miit.gov.cn/">{{ website.beian }}</a>
        </span>
      </div>
      <div v-if="feedbackButton" class="chat-button">
        <el-tooltip content="反馈或建议">
          <div class="body">
            <div class="router-button" @click="feedbackHandler">
              <div class="frame-button-content">
                <div class="frame-button-content__RoundWrapper">
                  <label class="icon"><span type="chat-button-1" width="32px" height="32px" class="base-icon__Container" /></label>
                </div>
              </div>
            </div>
          </div>
        </el-tooltip>
      </div>
    </div>
    <!--    反馈弹出框-->
    <el-dialog
      :visible.sync="feedbackBox"
      modal-append-to-body
      append-to-body
      title="意见反馈"
      top="50"
      width="80%"
      @close="exitFeedbackBox"
    >
      <el-form ref="ruleForm" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="问题或建议" prop="detail">
          <el-input
            v-model="form.detail"
            type="textarea"
            placeholder="请告诉我们遇到的问题或建议"
            maxlength="2000"
            show-word-limit
          />
        </el-form-item>
        <br><br><br>
        <div v-loading="loadingScreenshot" element-loading-text="正在截取屏幕数据">
          <img :src="form.screenshot" width="100%">
        </div>
      </el-form>
      <span slot="footer">
        <el-button type="primary" icon="el-icon-check" @click="handleSubmit">发送</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import Sidebar from './Sidebar'
import Navbar from './Top/Navbar'
import TagsView from './TagsView/Tags'
import AppMain from './AppMain'

import ResizeMixin from './mixin/ResizeHandler'

const closeSidebarAction = 'app/closeSideBar'
const addSysFeedbackAction = 'sys/feedback/addSysFeedback'
export default {
  name: 'Layout',
  components: {
    AppMain,
    Navbar,
    Sidebar,
    TagsView
  },
  mixins: [ResizeMixin],
  data () {
    return {
      feedbackButton: true,
      feedbackBox: false,
      form: {
        detail: '',
        screenshot: ''
      },
      rules: {
        detail: [
          { required: true, message: '请输入问题或建议', trigger: 'blur' },
          { min: 3, message: '长度至少三个字符', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    ...mapState({
      userInfo: state => state.sys.user.userInfo,
      sidebar: state => state.app.sidebar,
      device: state => state.app.device,
      showTagsView: state => state.app.tagsView,
      fixedHeader: state => state.app.fixedHeader,
      website: state => state.app.website,
      projectInfo: state => state.cvr.project.projectInfo
    }),
    classObj () {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === 'mobile'
      }
    },
    loadingScreenshot () {
      return this.form.screenshot === null || this.form.screenshot === ''
    }
  },
  methods: {
    handleClickOutside () {
      this.$store.dispatch(closeSidebarAction, { withoutAnimation: false })
    },
    feedbackHandler () {
      this.form = {
        detail: '',
        screenshot: ''
      }
      this.feedbackButton = false
      setTimeout(() => {
        this.$screenshot(document.querySelector('#__nuxt')).then((canvas) => {
          this.form.screenshot = canvas.toDataURL('image/jpeg', 0.8)
        })
      }, 100)

      this.feedbackBox = true
    },
    handleSubmit () {
      this.$refs.ruleForm.validate((valid) => {
        if (!valid) {
          return false
        }
        // 提交信息
        const loading = this.$loading({ fullscreen: true, text: '提交反馈中...' })
        const postData = { detail: this.form.detail, screenshot: [this.form.screenshot], userId: this.userInfo.id }
        this.$store.dispatch(addSysFeedbackAction, postData).then((res) => {
          loading.close()
          const resCode = res.code
          const errorMsg = res.message
          if (resCode === 0) {
            this.feedbackBox = false
            this.$message({
              type: 'success',
              message: '提交成功,感谢您的反馈!'
            })
          } else {
            this.$message({
              type: 'error',
              message: errorMsg || '提交数据失败,请稍后再试!'
            })
          }
        }, (error) => {
          loading.close()
          this.$message({
            type: 'error',
            message: '提交数据异常,请稍后再试!'
          })
          console.log(error)
        })
      })
    },
    exitFeedbackBox () {
      this.feedbackButton = true
    }
  }
}
</script>

<style lang="scss" scoped>
  @import "~@/assets/css/element/mixin.scss";
  @import "~@/assets/css/element/variables.scss";

  .app-wrapper {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;

    &.mobile.openSidebar {
      position: fixed;
      top: 0;
    }
  }

  .drawer-bg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
  }

  .fixed-header {
    position: fixed;
    top: 0;
    right: 0;
    z-index: 9;
    width: calc(100% - #{$sideBarWidth});
    transition: width 0.28s;
  }

  .hideSidebar .fixed-header {
    width: calc(100% - 54px)
  }

  .mobile .fixed-header {
    width: 100%;
  }

  .jvfast-footer {
    padding: 24px 50px;
    color: rgba(0,0,0,.65);
    font-size: 14px;
    text-align: center;
    flex: 0 0 auto;
    /* background: #f0f2f5; */
    a {
      text-decoration: none;
      margin-right: 40px;
      &:hover{
        /*background-color: #409EFF;*/
        color:#409EFF;
      }
    }
  }

  //悬浮按钮
  .chat-button {
    overflow-x: visible;
    overflow-y: visible;
    overflow-wrap: normal;
    position: fixed;
    z-index: 9999999;
    text-align: center;
    box-sizing: border-box;
    display: block !important;
    border-width: initial;
    border-style: none;
    border-color: initial;
    border-image: initial;
    margin: 0px;
    padding: 0px;
    bottom: 55px;
    background-color: transparent;
    right: 35px;
    height: 42px !important;
    width: 42px !important;
    .body {
      box-sizing: border-box;
      /*padding: 15px;*/
      height: 100%;
      width: 100%;
      margin: 0px;
      .router-button {
        width: 100%;
        height: 100%;

        .frame-button-content {
          height: 100%;
          width: 100%;
          background-color: rgb(0, 166, 128);
          box-shadow: rgba(0, 0, 0, 0.16) 0px 5px 14px;
          position: relative;
          text-align: center;
          cursor: pointer;
          border-radius: 50%;

          .frame-button-content__RoundWrapper {
            padding-top: 22%;

            label {
              cursor: pointer;
              display: inline-block;
              vertical-align: middle;

              .base-icon__Container {
                vertical-align: middle;
                padding-right: 32px;
                height: 32px;
                display: inline-block;
                background: url(https://static.meiqia.com/widget/static/icon-mq-round@2x.png) 0px -3px / 64px no-repeat;
                background-position: 0px -3px;
              }
            }
          }
        }
      }
    }
  }
</style>
