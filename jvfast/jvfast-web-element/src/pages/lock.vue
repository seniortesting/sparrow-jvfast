<template>
  <div class="lock-container pull-height">
    <div class="lock-form animated bounceInDown">
      <div
        :class="{'shake':passwdError,'bounceOut':pass}"
        class="animated"
      >
        <h3 class="title">
          当前用户: {{ userInfo.userName }}
        </h3>
        <el-input
          ref="passwd"
          v-model="passwd"
          placeholder="请输入锁屏密码"
          type="password"
          class="input-with-select animated"
          @keyup.enter.native="handleLogin"
        >
          <template slot="append">
            <el-tooltip
              effect="dark"
              content="解除锁屏"
              placement="bottom"
            >
              <el-button
                icon="el-icon-unlock"
                @click="handleLogin"
              />
            </el-tooltip>
          </template>
          <template slot="append">
            <el-tooltip
              effect="dark"
              content="重新登录"
              placement="bottom"
            >
              <el-button
                icon="iconfont icon-login"
                @click="handleLogout"
              />
            </el-tooltip>
          </template>
        </el-input>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import config from '@/config'
import encrypt from '@/util/EncryptUtil'

const logoutAction = 'sys/user/logout'
const clearLockMutation = 'app/CLEAR_LOCK'
export default {
  name: 'Lock',
  data () {
    return {
      passwd: '',
      passwdError: false,
      pass: false
    }
  },
  computed: {
    // ...mapState({
    //   userInfo: state => state.sys.user.userInfo
    // }),
    ...mapGetters(['website', 'lockPasswd', 'userInfo'])
  },
  mounted () {
    this.$nextTick(() => {
      this.$refs.passwd.focus()
    })
  },
  methods: {
    handleLogout () {
      this.$confirm('退出系统重新登录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$store.dispatch(logoutAction).then(() => {
          this.$router.push({ name: config.defaultSettings.page.loginComponentName })
        })
      }).catch((err) => {
        console.log(`User Cancelled the request ! ${err}`)
      })
    },
    handleLogin () {
      if (encrypt.md5(this.passwd) !== this.lockPasswd) {
        this.passwd = ''
        this.$message({
          message: '解锁密码错误,请重新输入',
          showClose: true,
          type: 'error'
        })
        this.passwdError = true
        setTimeout(() => {
          this.passwdError = false
        }, 1000)
        return
      }
      const loading = this.$loading({
        fullscreen: true,
        text: '加载中...'
      })
      this.pass = true
      setTimeout(() => {
        this.$store.commit(clearLockMutation)
        this.$router.push({ name: config.defaultSettings.page.homeComponentName })
        loading.close()
      }, 1000)
    }
  },
  head () {
    return {
      title: '当前系统已经锁屏,请输入解锁密码 | ' + this.website.name,
      meta: [
        { hid: 'description', name: 'description', content: '锁屏' }
      ]
    }
  }
}
</script>

<style lang="scss">
  .lock-container {
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
    .title {
      margin-bottom: 8px;
      color: #333;
    }
  }
  .lock-container::before {
    z-index: -999;
    content: '';
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background-image: url('~assets/img/bg/login.jpeg');
    background-size: cover;
  }
  .lock-form {
    width: 300px;
  }
</style>
