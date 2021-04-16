<template>
  <div class="login-container pull-height authorizationlogin">
    <div class="login-info text-white animated fadeInLeft">
      <h2 class="login-info-title" /> <ul class="login-info-list" />
    </div>
    <div :style="{height: tabHeight}" class="login-border animated bounceInDown">
      <div class="login-main">
        <div style="text-align: center;">
          <!--          <img src="data:image/png;base64,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" style="width: 120px;">-->
        </div> <div class="matelogo" style="text-align: center;">
          <!--          <img src="/static/img/matelogin2.74f857a.png" style="width: 80px;">-->
        </div> <div class="el-tabs el-tabs--top">
          <div class="el-tabs__header is-top">
            <div class="el-tabs__nav-wrap is-top">
              <div class="el-tabs__nav-scroll">
                <div role="tablist" class="el-tabs__nav" style="transform: translateX(0px);">
                  <div class="el-tabs__active-bar is-top" style="width: 199px; transform: translateX(0px);" /><div
                    id="tab-user"
                    aria-controls="pane-user"
                    role="tab"
                    aria-selected="true"
                    tabindex="0"
                    class="el-tabs__item is-top"
                  >
                    {{ website.name }}
                  </div>
                  <div class="sub_title">
                    {{ website.desc }}
                  </div>
                </div>
              </div>
            </div>
          </div><div class="el-tabs__content">
            <div id="pane-user" role="tabpanel" aria-labelledby="tab-user" class="el-tab-pane">
              <!--登录操作-->
              <transition enter-active-class="animated fadeInLeft" leave-active-class="animated fadeOutRight">
                <form v-if="tabView==='login'" class="el-form login-form">
                  <div class="el-form-item el-form-item--feedback">
                    <div class="el-form-item__content" style="margin-left: 0px;">
                      <div class="el-input el-input--small el-input--prefix">
                        <input v-model="login.username" type="text" autocomplete="off" placeholder="请输入用户名" class="el-input__inner"><span class="el-input__prefix"><i class="iconfont icon-user" /></span>
                      </div>
                    </div>
                  </div> <div class="el-form-item el-form-item--feedback" status-icon="">
                    <div class="el-form-item__content" style="margin-left: 0px;">
                      <div class="el-input el-input--small el-input--prefix el-input--suffix">
                        <input v-model="login.passwd" :type="passwdType" autocomplete="off" placeholder="请输入密码" class="el-input__inner"><span class="el-input__prefix"><i class="iconfont icon-passwd" /></span>
                        <span class="el-input__suffix"><span class="el-input__suffix-inner"><i :class="[ passwdType === 'password'? 'icon-eye-close':'icon-eye-open' ]" class="iconfont el-input__icon" @click="switchPasswdType" /></span></span>
                      </div>
                    </div>
                  </div> <div class="el-form-item el-form-item--feedback">
                    <div class="el-form-item__content" style="margin-left: 0px;">
                      <div class="el-row" span="24">
                        <div class="el-col el-col-14">
                          <div class="el-input el-input--small el-input--prefix">
                            <input
                              v-model="login.code"
                              type="text"
                              autocomplete="off"
                              maxlength="5"
                              placeholder="请输入验证码"
                              class="el-input__inner"
                              @keyup.enter="loginHandle"
                            ><span class="el-input__prefix"><i class="iconfont icon-captcha" /></span>
                          </div>
                        </div> <div class="el-col el-col-10">
                          <div class="login-code">
                            <img :src="captcha.code" :alt="captchaError" class="login-code-img" @click="getCaptcha">
                          </div>
                        </div>
                      </div>
                    </div>
                  </div> <div class="el-form-item el-form-item--feedback">
                    <div class="el-form-item__content" style="margin-left: 0px;">
                      <button :disabled="disabledBtn" type="button" class="el-button login-submit el-button--primary el-button--small" @click="loginHandle">
                        <span>登录</span>
                      </button>
                    </div>
                  </div>
                </form>
              </transition>
              <!--注册操作-->
              <transition enter-active-class="animated fadeInLeft" leave-active-class="animated fadeOutRight">
                <form v-if="tabView==='register'" class="el-form login-form">
                  <div class="el-form-item el-form-item--feedback">
                    <div class="el-form-item__content" style="margin-left: 0px;">
                      <div class="el-input el-input--small el-input--prefix">
                        <input v-model="register.username" type="text" autocomplete="off" placeholder="请输入注册手机号或邮箱" class="el-input__inner"><span class="el-input__prefix"><i class="iconfont icon-user" /></span>
                      </div>
                    </div>
                  </div>
                  <div class="el-form-item el-form-item--feedback" status-icon="">
                    <div class="el-form-item__content" style="margin-left: 0px;">
                      <div class="el-input el-input--small el-input--prefix el-input--suffix">
                        <input v-model="register.passwd" :type="passwdType" autocomplete="off" placeholder="请输入注册密码" class="el-input__inner"><span class="el-input__prefix"><i class="iconfont icon-passwd" /></span>
                        <span class="el-input__suffix"><span class="el-input__suffix-inner"><i :class="[ passwdType === 'password'? 'icon-eye-close':'icon-eye-open' ]" class="iconfont el-input__icon" @click="switchPasswdType" /></span></span>
                      </div>
                    </div>
                  </div>
                  <div class="el-form-item el-form-item--feedback" status-icon="">
                    <div class="el-form-item__content" style="margin-left: 0px;">
                      <div class="el-input el-input--small el-input--prefix el-input--suffix">
                        <input v-model="register.passwd2" :type="passwdType" autocomplete="off" placeholder="请再次确认密码" class="el-input__inner"><span class="el-input__prefix"><i class="iconfont icon-passwd" /></span>
                        <span class="el-input__suffix"><span class="el-input__suffix-inner"><i :class="[ passwdType === 'password'? 'icon-eye-close':'icon-eye-open' ]" class="iconfont el-input__icon" @click="switchPasswdType" /></span></span>
                      </div>
                    </div>
                  </div>
                  <div class="el-form-item el-form-item--feedback">
                    <div class="el-form-item__content" style="margin-left: 0px;">
                      <div class="el-row" span="24">
                        <div class="el-col el-col-14">
                          <div class="el-input el-input--small el-input--prefix">
                            <input
                              v-model="register.code"
                              type="text"
                              autocomplete="off"
                              maxlength="5"
                              placeholder="请输入验证码"
                              class="el-input__inner"
                              @keyup.enter="loginHandle"
                            ><span class="el-input__prefix"><i class="iconfont icon-captcha" /></span>
                          </div>
                        </div> <div class="el-col el-col-10">
                          <div class="login-code">
                            <img :src="captcha.code" :alt="captchaError" class="login-code-img" @click="getCaptcha">
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="el-form-item el-form-item--feedback">
                    <div class="el-form-item__content" style="margin-left: 0px;">
                      <button :disabled="disabledBtn" type="button" class="el-button register-submit el-button--primary el-button--small" @click="registerHandle">
                        <span>注册并登录</span>
                      </button>
                    </div>
                  </div>
                </form>
              </transition>
              <!--忘记密码-->
              <transition enter-active-class="animated fadeInLeft" leave-active-class="animated fadeOutRight">
                <form v-if="tabView==='forget'" class="el-form login-form">
                  <!--                  <div class="el-form-item el-form-item&#45;&#45;feedback" style="text-align: center">-->
                  <!--                    <div class="el-form-item__content" style="margin-left: 0px;">-->
                  <!--                      <el-radio-group v-model="forgetPasswdType">-->
                  <!--                        <el-radio :label="1">-->
                  <!--                          邮箱找回-->
                  <!--                        </el-radio>-->
                  <!--                        <el-radio :label="2">-->
                  <!--                          手机号码找回-->
                  <!--                        </el-radio>-->
                  <!--                      </el-radio-group>-->
                  <!--                    </div>-->
                  <!--                  </div>-->
                  <div class="el-form-item el-form-item--feedback">
                    <div class="el-form-item__content" style="margin-left: 0px;">
                      <div class="el-input el-input--small el-input--prefix">
                        <input v-model="forget.username" type="text" autocomplete="off" placeholder="请输入账号/手机号/邮箱" class="el-input__inner"><span class="el-input__prefix"><i class="iconfont icon-user" /></span>
                      </div>
                    </div>
                  </div>
                  <div class="el-form-item el-form-item--feedback">
                    <div class="el-form-item__content" style="margin-left: 0px;">
                      <div class="el-row" span="24">
                        <div class="el-col el-col-14">
                          <div class="el-input el-input--small el-input--prefix">
                            <input
                              v-model="forget.code"
                              type="text"
                              autocomplete="off"
                              maxlength="5"
                              placeholder="请输入验证码"
                              class="el-input__inner"
                              @keyup.enter="forgetHandle"
                            ><span class="el-input__prefix"><i class="iconfont icon-captcha" /></span>
                          </div>
                        </div> <div class="el-col el-col-10">
                          <div class="login-code">
                            <img :src="captcha.code" :alt="captchaError" class="login-code-img" @click="getCaptcha">
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="el-form-item el-form-item--feedback">
                    <div class="el-form-item__content" style="margin-left: 0px;">
                      <button :disabled="disabledBtn" type="button" class="el-button forget-submit el-button--primary el-button--small" @click="forgetHandle">
                        <span>找回密码</span>
                      </button>
                    </div>
                  </div>
                </form>
              </transition>
              <div>
                <form v-if="tabView==='changepwd'" class="el-form login-form">
                  <div v-if="isEmailCheckType">
                    <div class="mail-container">
                      <h3>修改密码邮箱发送成功！</h3>
                      <p>
                        请点击邮箱<el-link type="primary" @click="openEmail">
                          {{ forgetUser.email }}
                        </el-link>中的链接进行重置密码操作！
                      </p>
                    </div>
                    <div class="el-form-item el-form-item--feedback">
                      <div class="el-form-item__content" style="margin-left: 0px;">
                        <button type="button" class="el-button change-submit el-button--success el-button--small" @click="openEmail">
                          <span>打开邮箱</span>
                        </button>
                      </div>
                    </div>
                  </div>
                  <div v-else>
                    <div class="el-form-item el-form-item--feedback" status-icon="">
                      <div class="el-form-item__content" style="margin-left: 0px;">
                        <div class="el-input el-input--small el-input--prefix el-input--suffix">
                          <input v-model="changepwd.passwd" :type="passwdType" autocomplete="off" placeholder="请输入新的密码" class="el-input__inner"><span class="el-input__prefix"><i class="iconfont icon-passwd" /></span>
                          <span class="el-input__suffix"><span class="el-input__suffix-inner"><i :class="[ passwdType === 'password'? 'icon-eye-close':'icon-eye-open' ]" class="iconfont el-input__icon" @click="switchPasswdType" /></span></span>
                        </div>
                      </div>
                    </div>
                    <div class="el-form-item el-form-item--feedback" status-icon="">
                      <div class="el-form-item__content" style="margin-left: 0px;">
                        <div class="el-input el-input--small el-input--prefix el-input--suffix">
                          <input v-model="changepwd.passwd2" :type="passwdType" autocomplete="off" placeholder="请再次确认新的密码" class="el-input__inner"><span class="el-input__prefix"><i class="iconfont icon-passwd" /></span>
                          <span class="el-input__suffix"><span class="el-input__suffix-inner"><i :class="[ passwdType === 'password'? 'icon-eye-close':'icon-eye-open' ]" class="iconfont el-input__icon" @click="switchPasswdType" /></span></span>
                        </div>
                      </div>
                    </div>
                    <div class="el-form-item el-form-item--feedback">
                      <div class="el-form-item__content" style="margin-left: 0px;">
                        <button :disabled="disabledBtn" type="button" class="el-button change-submit el-button--success el-button--small" @click="changeHandle">
                          <span>确认修改</span>
                        </button>
                      </div>
                    </div>
                  </div>
                </form>
              </div>
              <div class="el-col" style="clear: both;margin: 15px 10px;width: 94%">
                <a v-if="tabView==='register'||tabView==='forget'" style="float: left;" @click.prevent="switchTab('login')"><button type="button" class="el-button el-button--text"><span>立即登录</span></button></a>
                <a v-if="tabView==='login'" style="float: left;" @click.prevent="switchTab('register')"><button type="button" class="el-button el-button--text"><span>注册账号</span></button></a>
                <a v-if="tabView==='register'||tabView==='login'" style="float: right;" @click.prevent="switchTab('forget')"><button type="button" class="el-button el-button--text"><span>忘记密码?</span></button></a>
                <a v-if="tabView==='changepwd'" style="float: left;" @click.prevent="switchTab('login')"><button type="button" class="el-button el-button--text"><span>返回登录</span></button></a>
                <a v-if="tabView==='changepwd'&&isEmailCheckType" style="float: right;" @click.prevent="switchTab('forget')"><button type="button" class="el-button el-button--text"><span>没有收到邮件？点击重试</span></button></a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--    下载APP提示-->
    <div v-if="$device.isDesktopOrTablet&&tabView==='login' && website.enableAPPDownload" class="download animated fadeInUp">
      <el-button type="primary" class="btn" round @click="downloadApp">
        下载{{ website.name }}APP
      </el-button>
    </div>
    <div class="foot-info text-white animated fadeInUp">
      V{{ website.version }}（Build {{ website.buildVersion }}） ©{{ new Date().getFullYear() }} {{ website.name }} <a href="http://www.beian.miit.gov.cn/">{{ website.beian }}</a>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import config from '@/config'
import { isEmail, isMobile } from '@/util/ValidateUtil'
import encrypt from '@/util/EncryptUtil'

const getCaptchaAction = 'sys/user/getCaptcha'
const loginByUserNameAction = 'sys/user/loginByUserName'
const registerAction = 'sys/user/register'
const forgetPasswdAction = 'sys/user/forgetPwd'
const updateUserAction = 'sys/user/updateUser'

export default {
  name: 'Login',
  data () {
    return {
      forgetPasswdType: 1,
      tabView: 'login',
      tabHeight: '508px',
      passwdType: 'password',
      disabledBtn: false,
      captcha: {},
      captchaError: '',
      login: {
        username: '13088881010',
        passwd: '123456',
        code: ''
      },
      register: {
        username: '',
        passwd: '',
        passwd2: '',
        code: ''
      },
      forget: {
        username: '',
        code: ''
      },
      forgetUser: { id: '' },
      isEmailCheckType: false,
      changepwd: {
        passwd: '',
        passwd2: ''
      }
    }
  },
  computed: {
    ...mapGetters(['website'])
  },
  mounted () {
    this.getCaptcha()
  },
  methods: {
    openEmail () {
      const email = this.forgetUser.email
      const index = email.indexOf('@')
      const openEmail = email.slice((index + 1), email.length)
      window.open('http://mail.' + openEmail, '_blank')
    },
    downloadApp () {
      window.open(this.website.appDownloadUrl, '_blank')
    },
    switchPasswdType () {
      this.passwdType = (this.passwdType === 'password' ? 'text' : 'password')
    },
    getCaptcha () {
      this.$store.dispatch(getCaptchaAction).then((res) => {
        const resCode = res.code
        if (resCode === 0) {
          const resData = res.data
          this.captcha = resData
        } else {
          this.captchaError = res.message
          if (resCode === 400) {
            this.$message({
              type: 'error',
              message: this.captchaError || '获取验证码失败,请稍后再试!'
            })
          }
        }
      })
    },
    switchTab (tabName) {
      this.tabView = tabName
      // 注册
      this.register.username = ''
      this.register.code = ''
      this.register.passwd = ''
      this.register.passwd2 = ''
      // 忘记密码
      this.forget.username = ''
      this.forget.code = ''
      this.changepwd.passwd = ''
      this.changepwd.passwd2 = ''
      // 设置高度
      if (this.tabView === 'login') {
        this.tabHeight = '508px'
        this.getCaptcha()
      } else if (this.tabView === 'register') {
        this.tabHeight = '608px'
        this.getCaptcha()
      } else if (this.tabView === 'forget') {
        this.tabHeight = '468px'
      } else if (this.tabView === 'changepwd') {
        this.getCaptcha()
      }
    },
    loginHandle () {
      const validInput = this.checkLoginInputData()
      if (validInput) {
        const loading = this.$loading({
          fullscreen: true,
          text: '登录请求中...'
        })
        this.disabledBtn = true

        const loginData = {
          username: this.login.username,
          passwd: encrypt.md5(this.login.passwd),
          code: this.login.code,
          captchaId: this.captcha.session
        }
        this.$store.dispatch(loginByUserNameAction, loginData).then((res) => {
          loading.close()
          const resCode = res.code
          if (resCode === 0) {
            this.goDashboardPage()
            this.$notify.success({
              title: '登录提示',
              message: '登录成功!',
              position: 'top-right',
              duration: 300,
              showClose: true
            })
          } else {
            this.disabledBtn = false
            const errMsg = res.message
            this.$message({
              type: 'error',
              duration: 3000,
              message: errMsg || '登录失败,请稍后再试!'
            })
            // 如果密码错误
            if (resCode === 500) {
              this.login.passwd = ''
            }
            // 验证码错误
            this.getCaptcha()
            this.login.code = ''
          }
        }).catch((err) => {
          console.log(err)
          loading.close()
          this.disabledBtn = false
        })
      }
    },
    checkLoginInputData () {
      if (this.login.username.length === 0) {
        this.$message({
          type: 'error',
          message: '用户名不能为空!'
        })
        return false
      }
      if (this.login.passwd.length === 0) {
        this.$message({
          type: 'error',
          message: '密码不能为空!'
        })
        return false
      }
      if (this.login.code.length === 0) {
        this.$message({
          type: 'error',
          message: '验证码输入不正确!'
        })
        return false
      }
      if (this.login.passwd.length < 6) {
        this.$message({
          type: 'error',
          message: '密码格式输入错误,最少六位!'
        })
        return false
      }
      return true
    },
    registerHandle () {
      const validInput = this.checkRegisterInputData()
      if (validInput) {
        const loading = this.$loading({
          fullscreen: true,
          text: '注册请求中...'
        })
        this.disabledBtn = true

        const registerData = {
          username: this.register.username,
          passwd: encrypt.md5(this.register.passwd),
          code: this.register.code,
          captchaId: this.captcha.session
        }
        this.$store.dispatch(registerAction, registerData).then((res) => {
          loading.close()
          this.disabledBtn = false
          const resCode = res.code
          if (resCode === 0) {
            this.goDashboardPage()
            this.$notify.success({
              title: '注册提示',
              message: '注册成功!',
              position: 'top-right'
            })
          } else {
            const errMsg = res.message
            this.$message({
              type: 'error',
              duration: 3000,
              message: errMsg || '注册失败,请稍后再试!'
            })
            // 验证码错误
            this.getCaptcha()
            this.register.code = ''
          }
        }).catch((err) => {
          console.log(err)
          loading.close()
          this.disabledBtn = false
        })
      }
    },
    checkRegisterInputData () {
      if (this.register.username.length === 0) {
        this.$message({
          type: 'error',
          message: '用户名不能为空!'
        })
        return false
      }
      if (!(isEmail(this.register.username) || isMobile(this.register.username))) {
        this.$message({
          type: 'error',
          message: '请输入合法的手机号码或者邮箱!'
        })
        return false
      }
      if (this.register.passwd.length === 0 || this.register.passwd2.length === 0) {
        this.$message({
          type: 'error',
          message: '密码不能为空!'
        })
        return false
      }
      if (this.register.code.length === 0) {
        this.$message({
          type: 'error',
          message: '验证码输入不正确!'
        })
        return false
      }

      if (this.register.passwd.length < 6) {
        this.$message({
          type: 'error',
          message: '密码格式输入错误,最少六位!'
        })
        return false
      }
      if (this.register.passwd !== this.register.passwd2) {
        this.$message({
          type: 'error',
          message: '确认密码输入错误!'
        })
        return false
      }
      return true
    },
    forgetHandle () {
      const validInput = this.checkForgetInputData()
      if (validInput) {
        const loading = this.$loading({
          fullscreen: true,
          text: '找回密码请求中...'
        })
        this.disabledBtn = true

        const forgetData = {
          username: this.forget.username,
          code: this.forget.code,
          captchaId: this.captcha.session
        }
        this.$store.dispatch(forgetPasswdAction, forgetData).then((res) => {
          loading.close()
          this.disabledBtn = false
          const resCode = res.code
          if (resCode === 0) {
            const resData = res.data
            this.forgetUser = resData
            this.isEmailCheckType = resData.email === this.forget.username
            this.switchTab('changepwd')
            // this.$notify.success({
            //   title: '找回密码提示',
            //   message: '找回密码成功,请重新登陆!',
            //   position: 'top-right',
            //   duration: 500
            // })
          } else {
            const errMsg = res.message
            this.$message({
              type: 'error',
              duration: 3000,
              message: errMsg || '找回密码失败,请稍后再试!'
            })
            // 验证码错误
            this.getCaptcha()
            this.forget.code = ''
          }
        }).catch((err) => {
          console.log(err)
          loading.close()
          this.disabledBtn = false
        })
      }
    },
    checkForgetInputData () {
      if (this.forget.username.length === 0) {
        this.$message({
          type: 'error',
          message: '手机号或者邮箱不能为空!'
        })
        return false
      }
      if (!isMobile(this.forget.username) && !isEmail(this.forget.username)) {
        this.$message({
          type: 'error',
          message: '非法的输入账号!'
        })
        return false
      }
      if (this.forget.code.length === 0) {
        this.$message({
          type: 'error',
          message: '验证码输入不正确!'
        })
        return false
      }
      return true
    },
    changeHandle () {
      const validInput = this.checkChangePwdInputData()
      if (validInput) {
        const loading = this.$loading({
          fullscreen: true,
          text: '修改密码请求中...'
        })
        this.disabledBtn = true
        const changeData = {
          id: this.forgetUser.id,
          passwd: encrypt.md5(this.changepwd.passwd)
        }
        this.$store.dispatch(updateUserAction, changeData).then((res) => {
          loading.close()
          this.disabledBtn = false
          const resCode = res.code
          if (resCode === 0) {
            const resData = res.data
            this.forgetUser = resData
            this.switchTab('login')
            this.$notify.success({
              title: '修改密码提示',
              message: '修改密码成功,请重新登陆!',
              position: 'top-right',
              duration: 3000
            })
          } else {
            const errMsg = res.message
            this.$message({
              type: 'error',
              duration: 3000,
              message: errMsg || '修改密码失败,请稍后再试!'
            })
          }
        }).catch((err) => {
          console.log(err)
          loading.close()
          this.disabledBtn = false
        })
      }
    },
    checkChangePwdInputData () {
      if (this.forgetUser.id.length === 0) {
        this.$message({
          type: 'error',
          message: '非法的请求!'
        })
        return false
      }
      if (this.changepwd.passwd.length < 6 || this.changepwd.passwd2.length < 6) {
        this.$message({
          type: 'error',
          message: '密码不能为空,且最少6个字符!'
        })
        return false
      }
      if (this.changepwd.passwd !== this.changepwd.passwd2) {
        this.$message({
          type: 'error',
          message: '两次输入的密码不一致!'
        })
        return false
      }
      return true
    },
    goDashboardPage () {
      this.$router.push({ name: config.defaultSettings.page.homeComponentName })
    }
  },
  head () {
    return {
      title: '用户登录 | ' + this.website.name,
      meta: [
        { hid: 'description', name: 'description', content: '登录' }
      ]
    }
  }

}
</script>

<style lang="scss" scoped>

  .authorizationlogin .login-info {
    padding-left: 0!important
  }

  .authorizationlogin .el-tabs__nav {
    width: 100%!important;
    text-align: center
  }

  .authorizationlogin .el-tabs__item {
    font-size: 20px;
    color: #827266;
    width: 50%;
    margin: 0 auto;
    font-weight: 700
  }

  .authorizationlogin .el-tabs__item.is-active,.authorizationlogin .el-tabs__item:hover {
    color: #827266!important
  }

  .authorizationlogin .el-tabs__active-bar {
    background-color: #827266!important;
    width: 0!important;
    margin-left: 30px
  }

  .authorizationlogin.login-container .el-form {
    width: 94%;
    margin: 15px auto
  }

  .authorizationlogin.login-container .login-form .el-form-item {
    margin-bottom: 15px
  }

  .authorizationlogin.login-container .login-form .el-input input {
    height: 50px;
    line-height: 50px;
    font-size: 15px;
    background-color: #fbfbfb;
    border-radius: 4px;
    border-color: #e4e4e4;
    color: #b1b1b1
  }
  i {
    display: inline-block;
  }
  .authorizationlogin.login-container .login-form .el-input .el-input__prefix i {
    padding: 4px 5px;
    font-size: 18px!important
  }

  .authorizationlogin.login-container .login-code-img {
    width: 140px;
    height: 48px
  }

  .authorizationlogin.login-container .login-submit {
    height: 52px;
    border-radius: 5px;
    font-size: 16px
  }
  .authorizationlogin.login-container .register-submit {
    height: 52px;
    border-radius: 5px;
    font-size: 16px
  }
  .authorizationlogin.login-container .forget-submit {
    height: 52px;
    border-radius: 5px;
    font-size: 16px
  }
  .authorizationlogin.login-container .change-submit {
    height: 52px;
    border-radius: 5px;
    font-size: 16px
  }

  .authorizationlogin.login-container .link {
    text-align: center;
    color: #606367
  }

  .authorizationlogin.login-container .login-border {
    box-shadow: 0 0 50px rgba(0,0,0,.13);
    border-radius: 15px
  }

  .authorizationlogin.login-container {
    display: -ms-flexbox;
    display: flex;
    -ms-flex-align: center;
    align-items: center;
    -ms-flex-pack: distribute;
    justify-content: space-around;
    background: rgba(0,0,0,.3);
    position: relative
  }

  .authorizationlogin.login-container:before {
    z-index: -999;
    content: "";
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background-image: url(~assets/img/bg/login2.jpeg);
    background-size: cover
  }

  .authorizationlogin .login-info {
    padding-left: 60px
  }

  .authorizationlogin .login-info-title {
    line-height: 90px
  }

  .authorizationlogin .login-info-item {
    font-size: 14px
  }

  .authorizationlogin .login-border {
    box-sizing: border-box;
    width: 478px;
    height: 508px;
    margin: 0 auto;
    display: -ms-flexbox;
    display: flex;
    -ms-flex-pack: center;
    justify-content: center;
    -ms-flex-direction: column;
    flex-direction: column;
    padding: 30px 40px 25px;
    background-color: #fff;
    border-radius: 6px;
    box-shadow: 1px 1px 2px #eee
  }

  .authorizationlogin .login-main {
    position: relative;
    border-radius: 3px;
    box-sizing: border-box;
    background-color: #fff;
    height: 100%
  }

  .authorizationlogin .login-main>h3 {
    margin-bottom: 20px
  }

  .authorizationlogin .login-main>p {
    color: #76838f
  }

  .authorizationlogin .login-title {
    margin: 0 0 20px;
    text-align: center;
    color: #409eff;
    letter-spacing: 3px
  }

  .authorizationlogin .login-submit {
    margin-top: 20px;
    width: 100%;
    background: #3080fe;
    border-radius: 28px;
    background-color: #ee7421!important;
    border-color: #ee7421!important
  }

  .authorizationlogin .login-submit:hover {
    background-color: #ee7421!important;
    border-color: #ee7421!important
  }

  .authorizationlogin .register-submit {
    margin-top: 20px;
    width: 100%;
    background: #3080fe;
    border-radius: 28px;
    background-color: #3080fe!important;
    border-color: #3080fe!important
  }

  .authorizationlogin .register-submit:hover {
    background-color: #3080fe!important;
    border-color: #3080fe!important
  }

  .authorizationlogin .forget-submit {
    margin-top: 20px;
    width: 100%;
    background: #f56c6c;
    border-radius: 28px;
    background-color: #f56c6c!important;
    border-color: #f56c6c!important
  }

  .authorizationlogin .forget-submit:hover {
    background-color: #f56c6c!important;
    border-color: #f56c6c!important
  }
  .authorizationlogin .change-submit {
    margin-top: 20px;
    width: 100%;
    background: #67c23a;
    border-radius: 28px;
    background-color: #67c23a !important;
    border-color: #67c23a!important
  }

  .authorizationlogin .change-submit:hover {
    background-color: #67c23a!important;
    border-color: #67c23a!important
  }

  .authorizationlogin .login-form {
    margin: 10px auto;
    width: 70%
  }

  .authorizationlogin .login-form .el-form-item__content {
    width: 375px
  }

  .authorizationlogin .login-form .el-form-item {
    margin-bottom: 12px
  }

  .authorizationlogin .login-form .el-input input {
    text-indent: 5px;
    border-color: #dcdcdc;
    border-radius: 3px
  }
  .el-input__prefix {
    left: 5px;
    transition: all .3s
  }

  .el-input__prefix,.el-input__suffix {
    position: absolute;
    top: 0;
    -webkit-transition: all .3s;
    height: 100%;
    color: #c0c4cc;
    text-align: center
  }
  .authorizationlogin .login-form .el-input .el-input__prefix i {
    padding: 0 5px;
    font-size: 16px!important
  }

  .authorizationlogin .login-code {
    display: -ms-flexbox;
    display: flex;
    -ms-flex-align: center;
    align-items: center;
    -ms-flex-pack: distribute;
    justify-content: space-around;
    margin: 0 0 0 10px
  }

  .authorizationlogin .login-code-img {
    width: 100px;
    height: 38px;
    background-color: #fdfdfd;
    border: 1px solid #f0f0f0;
    color: #333;
    font-size: 14px;
    font-weight: 700;
    letter-spacing: 5px;
    line-height: 32px;
    text-indent: 5px;
    text-align: center
  }

  .authorizationlogin #pane-code .login-form {
    width: 70%!important;
    margin: 10 auto!important
  }

  .authorizationlogin .choose {
    color: #3080fe!important
  }

  .authorizationlogin .dept_item {
    height: 50px;
    line-height: 50px;
    border-bottom: 1px solid #eee;
    cursor: pointer
  }

  .authorizationlogin .dept_item:hover>.el-icon-success {
    color: #3080fe
  }

  .authorizationlogin .el-dialog__body {
    min-height: 330px
  }

  .authorizationlogin #tab-user {
    font-size: 28px;
    color: #666!important
  }

  .authorizationlogin .el-tabs__nav-wrap:after {
    width: 0!important
  }

  .authorizationlogin .matelogo {
    position: absolute;
    bottom: -36px;
    right: -38px
  }

  .user_login .el-dialog {
    margin-bottom: 0
  }

  .mail-container {
    text-align: center;
    padding: 15px 15px;
    p {
      margin-top: 15px;
      a {
        margin-left: 3px;
      }
    }
  }

  /*下载按钮*/
  .download {
    position: absolute;
    text-align: center;
    bottom: 45px;
    width: 400px;
    .btn {
      width: 100%;
    }
  }

  .foot-info {
    position: absolute;
    text-align: center;
    font-size: 12px;
    bottom: 15px;
  }

  .text-white {
    color: #fff
  }
  a {
    color: inherit;
    text-decoration: none;
    display: inline-block;
    margin-left: 40px;
  }
  .pull-height {
    height: 100%;
    overflow: hidden
  }
  .sub_title {
    font-size: 14px;
    color: rgba(0,0,0,.45);
    margin-top: 12px;
    margin-bottom: 40px;
  }
  /*动画*/
</style>
