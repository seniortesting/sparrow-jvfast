<template>
  <div class="profile">
    <div class="account-settings-info-title">
      <span data-v-26b608e6="">安全设置</span>
    </div>
    <div class="">
      <div class="ant-list ant-list-split">
        <div class="ant-spin-nested-loading">
          <div class="ant-spin-container">
            <div class="ant-list-item">
              <div class="ant-list-item-meta">
                <div class="ant-list-item-meta-content">
                  <h4 class="ant-list-item-meta-title">
                    <a>账户密码</a>
                  </h4>
                  <div class="ant-list-item-meta-description">
                    <span>
                      <span class="security-list-description">当前密码强度</span>
                      <span> : </span>
                      <span class="security-list-value">中</span>
                    </span>
                  </div>
                </div>
              </div>
              <ul class="ant-list-item-action">
                <li><a @click.prevent="handlePasswdPopup">修改</a></li>
              </ul>
            </div>
            <div class="ant-list-item">
              <div class="ant-list-item-meta">
                <div class="ant-list-item-meta-content">
                  <h4 class="ant-list-item-meta-title">
                    <a>密保手机</a>
                  </h4>
                  <div class="ant-list-item-meta-description">
                    <span>
                      <span class="security-list-description">已绑定手机</span>
                      <span> : </span>
                      <span class="security-list-value">{{ userInfo.phone || '未填写' }}</span>
                    </span>
                  </div>
                </div>
              </div>
              <ul class="ant-list-item-action">
                <li><a @click.prevent="handlePhonePopup">修改</a></li>
              </ul>
            </div>
            <div class="ant-list-item">
              <div class="ant-list-item-meta">
                <div class="ant-list-item-meta-content">
                  <h4 class="ant-list-item-meta-title">
                    <a>邮箱</a>
                  </h4>
                  <div class="ant-list-item-meta-description">
                    <span>
                      <span class="security-list-description">用于找回密码等其他安全验证操作, 已绑定邮箱</span>
                      <span> : </span>
                      <span class="security-list-value">{{ userInfo.email || '未填写' }}</span>
                    </span>
                  </div>
                </div>
              </div>
              <ul class="ant-list-item-action">
                <li><a @click.prevent="handleEmailPopup">修改</a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--    相关弹出修改对话框-->
    <el-dialog
      :visible.sync="passwdBox"
      width="45%"
      append-to-body
    >
      <div slot="title" class="ant-modal-title">
        修改密码<el-divider />
      </div>
      <div class="avue-form" style="width: 100%">
        <el-form
          ref="passwdForm"
          status-icon
          :model="passwdForm"
          label-suffix=":"
          label-position="left"
          label-width="150px"
          :rules="passwdFormRules"
          @submit.native.prevent
        >
          <el-row :span="24">
            <el-group>
              <div class="avue-form__group">
                <!-- 各个组件-->
                <el-col
                  :sm="24"
                  :xs="24"
                  :md="24"
                  :offset="0"
                  class="avue-form__row"
                  style="padding-left: 10px; padding-right: 10px;"
                >
                  <el-form-item
                    label="密码"
                    prop="passwd"
                    class="avue-form__item--left"
                  >
                    <el-tooltip placement="bottom">
                      <div slot="content">
                        请输入新的密码
                      </div>
                      <el-input
                        v-model="passwdForm.passwd"
                        type="password"
                        :minlength="3"
                        :maxlength="80"
                        :show-word-limit="true"
                        class="avue-input"
                        :clearable="true"
                        placeholder="请输入新的密码"
                      />
                    </el-tooltip>
                  </el-form-item>
                </el-col>
                <el-col
                  :sm="24"
                  :xs="24"
                  :md="24"
                  :offset="0"
                  class="avue-form__row"
                  style="padding-left: 10px; padding-right: 10px;"
                >
                  <el-form-item
                    label="密码"
                    prop="passwd2"
                    class="avue-form__item--left"
                  >
                    <el-tooltip placement="bottom">
                      <div slot="content">
                        请再次输入新的密码
                      </div>
                      <el-input
                        v-model="passwdForm.passwd2"
                        type="password"
                        :minlength="3"
                        :maxlength="80"
                        :show-word-limit="true"
                        class="avue-input"
                        :clearable="true"
                        placeholder="请再次输入新的密码"
                      />
                    </el-tooltip>
                  </el-form-item>
                </el-col>
                <el-col :span="24" class="avue-form__menu avue-form__menu--center">
                  <el-form-item label-width="0px">
                    <el-button
                      type="primary"
                      icon="el-icon-check"
                      :loading="passwordFormSubmitDisable"
                      @click="handlePasswdSubmit"
                    >
                      保存
                    </el-button>
                  </el-form-item>
                </el-col>
              </div>
            </el-group>
          </el-row>
        </el-form>
      </div>
    </el-dialog>
    <el-dialog
      :visible.sync="phoneBox"
      width="45%"
      append-to-body
    >
      <div slot="title" class="ant-modal-title">
        绑定手机号码<el-divider />
      </div>
      <div class="avue-form" style="width: 100%">
        <el-form
          ref="phoneForm"
          status-icon
          :model="phoneForm"
          label-suffix=":"
          label-position="left"
          label-width="100px"
          :rules="phoneFormRules"
          @submit.native.prevent
        >
          <el-row :span="24">
            <el-group>
              <div class="avue-form__group">
                <el-col
                  :sm="24"
                  :xs="24"
                  :md="24"
                  :offset="0"
                  class="avue-form__row"
                  style="padding-left: 10px; padding-right: 10px;"
                >
                  <el-form-item
                    label="手机号码"
                    prop="phone"
                    class="avue-form__item--left"
                  >
                    <el-tooltip placement="bottom">
                      <div slot="content">
                        请输入11位有效的手机号码
                      </div>
                      <el-input
                        v-model="phoneForm.phone"
                        type="text"
                        :minlength="11"
                        :maxlength="11"
                        :show-word-limit="true"
                        class="avue-input"
                        :clearable="true"
                        placeholder="请输入11位有效的手机号码"
                      />
                    </el-tooltip>
                  </el-form-item>
                </el-col>
                <el-col
                  :sm="24"
                  :xs="24"
                  :md="24"
                  :offset="0"
                  class="avue-form__row"
                  style="padding-left: 10px; padding-right: 10px;"
                >
                  <el-form-item
                    label="手机验证码"
                    label-width="100px"
                    prop="code"
                    class="avue-form__item--left"
                  >
                    <el-tooltip placement="bottom">
                      <div slot="content">
                        请输入正确的6位手机验证码
                      </div>
                      <el-input
                        v-model="phoneForm.code"
                        type="text"
                        :minlength="6"
                        :maxlength="6"
                        :show-word-limit="true"
                        class="avue-input"
                        :clearable="true"
                        placeholder="请输入正确的6位手机验证码"
                      >
                        <template slot="append">
                          <span @click="sendCaptcha">{{ phoneForm.append }}</span>
                        </template>
                      </el-input>
                    </el-tooltip>
                  </el-form-item>
                </el-col>
                <el-col :span="24" class="avue-form__menu avue-form__menu--center">
                  <el-form-item label-width="0px">
                    <el-button
                      type="primary"
                      icon="el-icon-check"
                      :loading="phoneFormSubmitDisable"
                      @click="handlePhoneSubmit"
                    >
                      保存
                    </el-button>
                  </el-form-item>
                </el-col>
              </div>
            </el-group>
          </el-row>
        </el-form>
      </div>
    </el-dialog>
    <el-dialog
      :visible.sync="emailBox"
      width="45%"
      append-to-body
    >
      <div slot="title" class="ant-modal-title">
        绑定邮箱<el-divider />
      </div>
      <div class="avue-form" style="width: 100%">
        <el-form
          ref="emailForm"
          status-icon
          :model="emailForm"
          label-suffix=":"
          label-position="left"
          label-width="150px"
          :rules="emailFormRules"
          @submit.native.prevent
        >
          <el-row :span="24">
            <el-group>
              <div class="avue-form__group">
                <el-col
                  :sm="24"
                  :xs="24"
                  :md="24"
                  :offset="0"
                  class="avue-form__row"
                  style="padding-left: 10px; padding-right: 10px;"
                >
                  <el-form-item
                    label="绑定邮箱"
                    prop="email"
                    class="avue-form__item--left"
                  >
                    <el-tooltip placement="bottom">
                      <div slot="content">
                        请输入你需要绑定的邮箱
                      </div>
                      <el-input
                        v-model="emailForm.email"
                        type="text"
                        :minlength="3"
                        :maxlength="80"
                        :show-word-limit="true"
                        class="avue-input"
                        :clearable="true"
                        placeholder="请输入你需要绑定的邮箱"
                      />
                    </el-tooltip>
                  </el-form-item>
                </el-col>
                <el-col :span="24" class="avue-form__menu avue-form__menu--center">
                  <el-form-item label-width="0px">
                    <el-button
                      type="primary"
                      icon="el-icon-check"
                      :loading="emailFormSubmitDisable"
                      @click="handleEmailSubmit"
                    >
                      保存
                    </el-button>
                  </el-form-item>
                </el-col>
              </div>
            </el-group>
          </el-row>
        </el-form>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { isEmail, isMobile } from '@/util/ValidateUtil'
import encrypt from '@/util/EncryptUtil'

const userUpdateAction = 'sys/user/updateUser'
const updateUserInfoAction = 'sys/user/updateUserInfo'
const forgetPasswdAction = 'sys/user/getForgetSMS'
// const forgetPasswdVerfiyAction = 'sys/user/forgetPwdVerify'
const logoutAction = 'sys/user/logout'

export default {
  name: 'Security',
  data () {
    const validateEmail = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入邮箱!'))
      } else if (!isEmail(value)) {
        callback(new Error('输入的邮箱非法!'))
      } else {
        callback()
      }
    }
    const validatePasswd = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码!'))
      } else if (value.length < 6) {
        callback(new Error('密码长度最少6个字符!'))
      } else {
        callback()
      }
    }
    const validatePasswd2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码!'))
      } else if (value !== this.passwdForm.passwd) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      passwdBox: false,
      passwdForm: {
        passwd: '',
        passwd2: ''
      },
      passwdFormRules: {
        passwd: [{ validator: validatePasswd, trigger: 'blur' }],
        passwd2: [{ validator: validatePasswd2, trigger: 'blur' }]
      },
      passwordFormSubmitDisable: false,
      phoneBox: false,
      phoneForm: {
        phone: '',
        code: '',
        append: '发送验证码'
      },
      phoneFormRules: {
        phone: [{
          min: 11,
          max: 11,
          trigger: 'blur'
        }],
        code: [{
          required: true,
          min: 6,
          max: 6,
          message: '请输入正确的六位手机验证码',
          trigger: 'blur'
        }]
      },
      getCodeisWaiting: false,
      getCodeTimer: null,
      phoneFormSubmitDisable: false,
      emailBox: false,
      emailForm: {
        email: ''
      },
      emailFormRules: {
        email: [{
          validator: validateEmail,
          trigger: 'blur'
        }]
      },
      emailFormSubmitDisable: false
    }
  },
  computed: { ...mapGetters(['userInfo', 'token']) },
  watch: {
    phoneBox (newValue, oldValue) {
      if (!newValue) {
        this.resetTimer()
      }
    }
  },
  methods: {
    // 修改密码相关
    handlePasswdPopup () {
      this.passwdBox = true
    },
    handlePasswdSubmit () {
      this.$refs.passwdForm.validate((valid) => {
        if (valid) {
          this.passwordFormSubmitDisable = true
          const postData = {
            id: this.userInfo.id,
            passwd: encrypt.md5(this.passwdForm.passwd),
            token: this.token
          }
          this.$store.dispatch(userUpdateAction, postData).then((res) => {
            this.passwordFormSubmitDisable = false
            const resCode = res.code
            const errorMsg = res.message
            if (resCode === 0) {
              this.passwdBox = false
              this.$message({
                type: 'success',
                duration: 5000,
                message: '更新密码成功,请使用修改后的密码重新登录!'
              })
              this.$store.dispatch(logoutAction).then((res) => {
                this.$router.push({ name: 'login' })
              })
            } else {
              this.$message({
                type: 'error',
                showClose: true,
                message: errorMsg || '更新数据失败,请稍后再试!'
              })
            }
          }, (error) => {
            this.passwordFormSubmitDisable = false
            this.$message({
              type: 'error',
              showClose: true,
              message: '更新数据异常,请稍后再试!'
            })
            console.log(error)
          })
        }
      })
    },
    // 修改手机号码相关
    handlePhonePopup () {
      this.phoneBox = true
      this.phoneForm.phone = this.userInfo.phone
    },
    sendCaptcha () {
      const phoneNumber = this.phoneForm.phone
      if (this.getCodeisWaiting) {
        return
      }
      if (phoneNumber.length === 0) {
        this.$message({
          type: 'error',
          showClose: true,
          message: '手机号码不能为空!'
        })
        return
      }
      if (!isMobile(phoneNumber)) {
        this.$message({
          type: 'error',
          showClose: true,
          message: '手机号格式错误,请输入合法的11位手机号码!'
        })
        return
      }
      this.phoneForm.append = '发送中...'
      this.getCodeisWaiting = true

      this.$store.dispatch(forgetPasswdAction, phoneNumber).then((res) => {
        const resCode = res.code
        const errorMsg = res.message
        if (resCode === 0) {
          // 发送成功
          this.$message.success('短信验证码发送成功!')
          // 设置倒计时
          this.setTimer()
        } else if (resCode === 404) {
          const errMsg = '手机号没有注册,请先注册!'
          this.$message({
            type: 'error',
            showClose: true,
            message: errMsg
          })
          this.resetTimer()
        } else if (resCode === 400) {
          const errMsg = `手机号${phoneNumber}发送短信太频繁，请60秒后再试!`
          this.$message({
            type: 'error',
            showClose: true,
            message: errMsg
          })
          this.resetTimer()
        } else {
          this.$message({
            type: 'error',
            showClose: true,
            message: errorMsg || '发送验证码异常,请稍后再试!'
          })
          this.resetTimer()
        }
      }).catch(() => {
        this.$message({
          type: 'error',
          showClose: true,
          message: '发送短信验证码失败,请稍后再试!'
        })
        this.resetTimer()
      })
    },
    setTimer () {
      let holdTime = 60
      this.phoneForm.append = '重新获取(60)'
      this.getCodeTimer = setInterval(() => {
        if (holdTime <= 0) {
          this.getCodeisWaiting = false
          this.phoneForm.append = '发送验证码'
          clearInterval(this.getCodeTimer)
          return
        }
        this.phoneForm = '重新获取(' + holdTime + ')'
        holdTime--
      }, 1000)
    },
    resetTimer () {
      this.getCodeisWaiting = false
      this.phoneForm.append = '发送验证码'
      if (this.getCodeTimer) {
        clearInterval(this.getCodeTimer)
      }
    },
    handlePhoneSubmit () {
      // TODO: 先检查验证码是否存在,此处没有验证验证码？？？
      this.$refs.phoneForm.validate((valid) => {
        if (valid) {
          this.phoneFormSubmitDisable = true
          const postData = {
            id: this.userInfo.id,
            phone: this.phoneForm.phone,
            token: this.token
          }
          this.$store.dispatch(userUpdateAction, postData).then((res) => {
            this.phoneFormSubmitDisable = false
            const resCode = res.code
            const errorMsg = res.message
            if (resCode === 0) {
              this.phoneBox = false
              this.$store.dispatch(updateUserInfoAction, { phone: this.phoneForm.phone }).then((res) => {
                this.$message({
                  type: 'success',
                  message: '更新手机号码成功!'
                })
              })
            } else {
              this.$message({
                type: 'error',
                showClose: true,
                message: errorMsg || '更新数据失败,请稍后再试!'
              })
            }
          }, (error) => {
            this.phoneFormSubmitDisable = false
            this.$message({
              type: 'error',
              showClose: true,
              message: '更新手机号码失败,请稍后再试!'
            })
            console.log(error)
          })
        }
      })
    },
    // 修改邮箱相关
    handleEmailPopup () {
      this.emailBox = true
      this.emailForm.email = this.userInfo.email
    },
    handleEmailSubmit () {
      this.$refs.emailForm.validate((valid) => {
        if (valid) {
          this.emailFormSubmitDisable = true
          const postData = {
            id: this.userInfo.id,
            email: this.emailForm.email,
            token: this.token
          }
          this.$store.dispatch(userUpdateAction, postData).then((res) => {
            this.emailFormSubmitDisable = false
            const resCode = res.code
            const errorMsg = res.message
            if (resCode === 0) {
              this.emailBox = false
              this.$store.dispatch(updateUserInfoAction, { email: this.emailForm.email }).then((res) => {
                this.$message({
                  type: 'success',
                  message: '绑定邮箱成功!'
                })
              })
            } else {
              this.$message({
                type: 'error',
                showClose: true,
                message: errorMsg || '更新数据失败,请稍后再试!'
              })
            }
          }, (error) => {
            this.emailFormSubmitDisable = false
            this.$message({
              type: 'error',
              showClose: true,
              message: '更新邮箱异常,请稍后再试!'
            })
            console.log(error)
          })
        }
      })
    }
  },
  head () {
    return {
      title: '安全设置 | 个人中心',
      meta: [
        { hid: 'description', name: 'description', content: '安全设置,个人中心' }
      ]
    }
  }
}
</script>

<style lang="scss" scoped>
  .profile {
    flex: 1 1;
    padding: 8px 40px;

    .account-settings-info-title {
      color: rgba(0, 0, 0, .85);
      font-size: 20px;
      font-weight: 500;
      line-height: 28px;
      margin-bottom: 12px;
    }

    .account-settings-info-view {
      padding-top: 12px;
    }
  }
  .ant-list {
    font-family: Chinese Quote,-apple-system,BlinkMacSystemFont,Segoe UI,PingFang SC,Hiragino Sans GB,Microsoft YaHei,Helvetica Neue,Helvetica,Arial,sans-serif,Apple Color Emoji,Segoe UI Emoji,Segoe UI Symbol;
    font-size: 14px;
    font-variant: tabular-nums;
    line-height: 1.5;
    color: rgba(0,0,0,.65);
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    list-style: none;
    position: relative
  }

  .ant-list * {
    outline: none
  }

  .ant-list-pagination {
    margin-top: 24px;
    text-align: right
  }

  .ant-list-more {
    margin-top: 12px;
    text-align: center
  }

  .ant-list-more button {
    padding-left: 32px;
    padding-right: 32px
  }

  .ant-list-spin {
    text-align: center;
    min-height: 40px
  }

  .ant-list-empty-text {
    color: rgba(0,0,0,.45);
    font-size: 14px;
    padding: 16px;
    text-align: center
  }

  .ant-list-item {
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    padding: 12px 0
  }

  .ant-list-item,.ant-list-item-meta {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex
  }

  .ant-list-item-meta {
    -webkit-box-align: start;
    -ms-flex-align: start;
    align-items: flex-start;
    -webkit-box-flex: 1;
    -ms-flex: 1;
    flex: 1;
    font-size: 0
  }

  .ant-list-item-meta-avatar {
    margin-right: 16px
  }

  .ant-list-item-meta-content {
    -webkit-box-flex: 1;
    -ms-flex: 1 0;
    flex: 1 0
  }

  .ant-list-item-meta-title {
    color: rgba(0,0,0,.65);
    margin-bottom: 4px;
    font-size: 14px;
    line-height: 22px
  }

  .ant-list-item-meta-title>a {
    color: rgba(0,0,0,.65);
    -webkit-transition: all .3s;
    transition: all .3s
  }

  .ant-list-item-meta-title>a:hover {
    color: #1890ff
  }

  .ant-list-item-meta-description {
    color: rgba(0,0,0,.45);
    font-size: 14px;
    line-height: 22px
  }

  .ant-list-item-content {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-flex: 1;
    -ms-flex: 1;
    flex: 1;
    -webkit-box-pack: end;
    -ms-flex-pack: end;
    justify-content: flex-end
  }

  .ant-list-item-content-single {
    -webkit-box-pack: start;
    -ms-flex-pack: start;
    justify-content: flex-start
  }

  .ant-list-item-action {
    font-size: 0;
    -webkit-box-flex: 0;
    -ms-flex: 0 0 auto;
    flex: 0 0 auto;
    margin-left: 48px;
    padding: 0;
    list-style: none
  }

  .ant-list-item-action>li {
    display: inline-block;
    color: rgba(0,0,0,.45);
    cursor: pointer;
    padding: 0 8px;
    position: relative;
    font-size: 14px;
    line-height: 22px;
    text-align: center
  }

  .ant-list-item-action>li:first-child {
    padding-left: 0
  }

  .ant-list-item-action-split {
    background-color: #e8e8e8;
    margin-top: -7px;
    position: absolute;
    top: 50%;
    right: 0;
    width: 1px;
    height: 14px
  }

  .ant-list-item-main {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-flex: 1;
    -ms-flex: 1;
    flex: 1
  }
  .ant-spin-spinning {
    opacity: 1;
    position: static;
    display: inline-block
  }

  .ant-spin-nested-loading {
    position: relative
  }

  .ant-spin-nested-loading>div>.ant-spin {
    display: block;
    position: absolute;
    height: 100%;
    max-height: 400px;
    width: 100%;
    z-index: 4
  }

  .ant-spin-nested-loading>div>.ant-spin .ant-spin-dot {
    position: absolute;
    top: 50%;
    left: 50%;
    margin: -10px
  }

  .ant-spin-nested-loading>div>.ant-spin .ant-spin-text {
    position: absolute;
    top: 50%;
    width: 100%;
    padding-top: 5px;
    text-shadow: 0 1px 2px #fff
  }

  .ant-spin-nested-loading>div>.ant-spin.ant-spin-show-text .ant-spin-dot {
    margin-top: -20px
  }

  .ant-spin-nested-loading>div>.ant-spin-sm .ant-spin-dot {
    margin: -7px
  }

  .ant-spin-nested-loading>div>.ant-spin-sm .ant-spin-text {
    padding-top: 2px
  }

  .ant-spin-nested-loading>div>.ant-spin-sm.ant-spin-show-text .ant-spin-dot {
    margin-top: -17px
  }

  .ant-spin-nested-loading>div>.ant-spin-lg .ant-spin-dot {
    margin: -16px
  }

  .ant-spin-nested-loading>div>.ant-spin-lg .ant-spin-text {
    padding-top: 11px
  }

  .ant-spin-nested-loading>div>.ant-spin-lg.ant-spin-show-text .ant-spin-dot {
    margin-top: -26px
  }

  .ant-spin-container {
    position: relative;
    -webkit-transition: opacity .3s;
    transition: opacity .3s
  }

  .ant-spin-container:after {
    content: "";
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    background: #fff;
    opacity: 0;
    pointer-events: none;
    -webkit-transition: all .3s;
    transition: all .3s;
    display: none\9;
    height: 100%;
    width: 100%;
    z-index: 10
  }

  .ant-spin-blur {
    pointer-events: none;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    overflow: hidden;
    opacity: .5
  }

  .ant-spin-blur:after {
    opacity: .4;
    pointer-events: auto
  }

  .ant-list {
    font-family: Chinese Quote,-apple-system,BlinkMacSystemFont,Segoe UI,PingFang SC,Hiragino Sans GB,Microsoft YaHei,Helvetica Neue,Helvetica,Arial,sans-serif,Apple Color Emoji,Segoe UI Emoji,Segoe UI Symbol;
    font-size: 14px;
    font-variant: tabular-nums;
    line-height: 1.5;
    color: rgba(0,0,0,.65);
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    list-style: none;
    position: relative
  }

  .ant-list * {
    outline: none
  }

  .ant-list-pagination {
    margin-top: 24px;
    text-align: right
  }

  .ant-list-more {
    margin-top: 12px;
    text-align: center
  }

  .ant-list-more button {
    padding-left: 32px;
    padding-right: 32px
  }

  .ant-list-spin {
    text-align: center;
    min-height: 40px
  }

  .ant-list-empty-text {
    color: rgba(0,0,0,.45);
    font-size: 14px;
    padding: 16px;
    text-align: center
  }

  .ant-list-item {
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    padding: 12px 0
  }

  .ant-list-item,.ant-list-item-meta {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex
  }

  .ant-list-item-meta {
    -webkit-box-align: start;
    -ms-flex-align: start;
    align-items: flex-start;
    -webkit-box-flex: 1;
    -ms-flex: 1;
    flex: 1;
    font-size: 0
  }

  .ant-list-item-meta-avatar {
    margin-right: 16px
  }

  .ant-list-item-meta-content {
    -webkit-box-flex: 1;
    -ms-flex: 1 0;
    flex: 1 0
  }

  .ant-list-item-meta-title {
    color: rgba(0,0,0,.65);
    margin-bottom: 4px;
    font-size: 14px;
    line-height: 22px
  }

  .ant-list-item-meta-title>a {
    color: rgba(0,0,0,.65);
    -webkit-transition: all .3s;
    transition: all .3s
  }

  .ant-list-item-meta-title>a:hover {
    color: #1890ff
  }

  .ant-list-item-meta-description {
    color: rgba(0,0,0,.45);
    font-size: 14px;
    line-height: 22px
  }

  .ant-list-item-content {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-flex: 1;
    -ms-flex: 1;
    flex: 1;
    -webkit-box-pack: end;
    -ms-flex-pack: end;
    justify-content: flex-end
  }

  .ant-list-item-content-single {
    -webkit-box-pack: start;
    -ms-flex-pack: start;
    justify-content: flex-start
  }

  .ant-list-item-action {
    font-size: 0;
    -webkit-box-flex: 0;
    -ms-flex: 0 0 auto;
    flex: 0 0 auto;
    margin-left: 48px;
    padding: 0;
    list-style: none
  }

  .ant-list-item-action>li {
    display: inline-block;
    color: rgba(0,0,0,.45);
    cursor: pointer;
    padding: 0 8px;
    position: relative;
    font-size: 14px;
    line-height: 22px;
    text-align: center
  }

  .ant-list-item-action>li:first-child {
    padding-left: 0
  }

  .ant-list-item-action-split {
    background-color: #e8e8e8;
    margin-top: -7px;
    position: absolute;
    top: 50%;
    right: 0;
    width: 1px;
    height: 14px
  }

  .ant-list-item-main {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-flex: 1;
    -ms-flex: 1;
    flex: 1
  }
  .ant-spin-spinning {
    opacity: 1;
    position: static;
    display: inline-block
  }

  .ant-spin-nested-loading {
    position: relative
  }

  .ant-spin-nested-loading>div>.ant-spin {
    display: block;
    position: absolute;
    height: 100%;
    max-height: 400px;
    width: 100%;
    z-index: 4
  }

  .ant-spin-nested-loading>div>.ant-spin .ant-spin-dot {
    position: absolute;
    top: 50%;
    left: 50%;
    margin: -10px
  }

  .ant-spin-nested-loading>div>.ant-spin .ant-spin-text {
    position: absolute;
    top: 50%;
    width: 100%;
    padding-top: 5px;
    text-shadow: 0 1px 2px #fff
  }

  .ant-spin-nested-loading>div>.ant-spin.ant-spin-show-text .ant-spin-dot {
    margin-top: -20px
  }

  .ant-spin-nested-loading>div>.ant-spin-sm .ant-spin-dot {
    margin: -7px
  }

  .ant-spin-nested-loading>div>.ant-spin-sm .ant-spin-text {
    padding-top: 2px
  }

  .ant-spin-nested-loading>div>.ant-spin-sm.ant-spin-show-text .ant-spin-dot {
    margin-top: -17px
  }

  .ant-spin-nested-loading>div>.ant-spin-lg .ant-spin-dot {
    margin: -16px
  }

  .ant-spin-nested-loading>div>.ant-spin-lg .ant-spin-text {
    padding-top: 11px
  }

  .ant-spin-nested-loading>div>.ant-spin-lg.ant-spin-show-text .ant-spin-dot {
    margin-top: -26px
  }

  .ant-spin-container {
    position: relative;
    -webkit-transition: opacity .3s;
    transition: opacity .3s
  }

  .ant-spin-container:after {
    content: "";
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    background: #fff;
    opacity: 0;
    pointer-events: none;
    -webkit-transition: all .3s;
    transition: all .3s;
    display: none\9;
    height: 100%;
    width: 100%;
    z-index: 10
  }

  .ant-spin-blur {
    pointer-events: none;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    overflow: hidden;
    opacity: .5
  }

  .ant-spin-blur:after {
    opacity: .4;
    pointer-events: auto
  }
  a {
    color: rgb(24, 144, 255);
    background-color: transparent;
    cursor: pointer;
    text-decoration: none;
    outline: none;
    transition: color 0.3s ease 0s;
  }
  a:active, a:hover {
    outline: 0px;
    text-decoration: none;
  }
  a:active {
    color: rgb(9, 109, 217);
  }
  .ant-modal-title {
    margin: 0;
    line-height: 22px;
    font-weight: 500;
    line-height: 24px;
    font-size: 18px;
    color: #303133;
  }
  .security-list-value {
    font-weight: bold;
  }
</style>
