<template>
  <span>
    <i
      class="iconfont icon-lock"
      @click="handleLock"
    />
    <el-dialog
      :visible.sync="box"
      title="设置锁屏密码"
      width="30%"
      append-to-body
    >
      <el-form
        ref="form"
        :model="form"
        label-width="80px"
      >
        <el-form-item
          :rules="[{ required: true, message: '锁屏密码不能为空'}]"
          label="锁屏密码"
          prop="passwd"
        >
          <el-input
            ref="passwd"
            v-model="form.passwd"
            placeholder="请输入锁屏密码"
          />
        </el-form-item>
      </el-form>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          @click="handleSetLock"
        >确 定</el-button>
      </span>
    </el-dialog>
  </span>
</template>

<script>
import { mapGetters } from 'vuex'
import config from '@/config'
import encrypt from '@/util/EncryptUtil'

const setLockMutation = 'app/SET_LOCK'
const setLockPasswdMutation = 'app/SET_LOCK_PASSWD'
export default {
  name: 'TopLock',
  data () {
    return {
      box: false,
      form: {
        passwd: ''
      }
    }
  },
  computed: {
    ...mapGetters(['lockPasswd'])
  },
  methods: {
    handleSetLock () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$store.commit(setLockPasswdMutation, encrypt.md5(this.form.passwd))
          this.handleLock()
        }
      })
    },
    handleLock () {
      if (!this.lockPasswd) {
        this.box = true
        // 自动聚焦
        this.$nextTick(() => {
          this.$refs.passwd.focus()
        })
        return
      }
      this.$store.commit(setLockMutation)
      setTimeout(() => {
        this.$router.push({ name: config.defaultSettings.page.lockComponentName })
      }, 100)
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
