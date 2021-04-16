<template>
  <div class="profile">
    <div class="account-settings-info-title">
      <span data-v-26b608e6="">基本资料</span>
    </div>
    <div class="account-settings-info-view">
      <div class="avue-form" style="width: 100%">
        <el-form
          ref="userForm"
          status-icon
          :model="userForm"
          label-suffix=":"
          label-position="left"
          label-width="150px"
          :rules="userFormRules"
          @submit.native.prevent
        >
          <el-row :span="24">
            <el-group>
              <div class="avue-form__group">
                <!-- 各个组件-->
                <el-col
                  :sm="12"
                  :xs="24"
                  :md="24"
                  :offset="0"
                  class="avue-form__row"
                  style="padding-left: 10px; padding-right: 10px;"
                >
                  <el-form-item
                    label="头像"
                    prop="avatar"
                    class="avue-form__item--left"
                  >
                    <div
                      v-loading.lock="uploadLoading"
                      class="avue-upload"
                    >
                      <el-upload
                        :disabled="uploadDisabled"
                        :auto-upload="true"
                        :class="{'avue-upload--list':uploadType==='picture-img'}"
                        :multiple="false"
                        :drag="uploadDrag"
                        :limit="uploadLimit"
                        accept="image/jpeg,image/png"
                        :action="uploadAction"
                        :list-type="uploadType"
                        :show-file-list="uploadShowFileList"
                        :file-list="uploadFileList"
                        :before-upload="handleUploadBefore"
                        :http-request="handleUploadHttpRequest"
                        :on-exceed="handleUploadExceedLimit"
                        :on-success="handleUploadSuccess"
                        :on-error="handleUploadError"
                        :before-remove="handleUploadRemove"
                        :on-remove="handleUploadOnRemove"
                        :on-preview="handleUploadPreview"
                        :on-change="handleUploadChange"
                      >
                        <template v-if="uploadType=='picture-card'">
                          <i slot="default" class="el-icon-plus" />
                        </template>
                        <template v-else-if="uploadType=='picture-img'">
                          <img
                            v-if="uploadFileUrl"
                            :src="uploadFileUrl"
                            class="avue-upload__avatar"
                            @mouseover="uploadMenu=uploadDisabled?false:true"
                          >
                          <i
                            v-else
                            class="el-icon-plus avue-upload__icon"
                          />
                          <div
                            v-if="uploadMenu"
                            class="el-upload-list__item-actions avue-upload__menu"
                            @mouseover="uploadMenu=true"
                            @mouseout="uploadMenu=false"
                            @click.stop="()=>{return false}"
                          >
                            <i
                              class="el-icon-zoom-in"
                              @click.stop="handleUploadPreview({url: uploadFileUrl})"
                            />
                            <i
                              class="el-icon-delete"
                              @click.stop="handleUploadOnRemove({url: uploadFileUrl})"
                            />
                          </div>
                        </template>
                        <template v-else-if="uploadDrag">
                          <i class="el-icon-upload" />
                          <div class="el-upload__text">
                            将文件拖到此处，或
                            <em>点击上传</em>
                          </div>
                        </template>
                        <template v-else>
                          <el-button
                            size="small"
                            type="primary"
                          >
                            点击上传
                          </el-button>
                        </template>
                        <div
                          slot="tip"
                          class="el-upload__tip"
                        >
                          只能上传jpg/png文件，且不超过2mb
                        </div>
                      </el-upload>
                      <el-dialog
                        append-to-body
                        class="avue-upload__dialog"
                        :modal-append-to-body="false"
                        :visible.sync="uploadPreviewDlgShow"
                      >
                        <img
                          v-if="uploadTypeList.img.test(uploadPreviewDlgImgUrl)"
                          :src="uploadPreviewDlgImgUrl"
                          style="max-width:100%"
                          alt
                        >
                        <video
                          v-else-if="uploadTypeList.video.test(uploadPreviewDlgImgUrl)"
                          controls="controls"
                          style="max-width:100%"
                          :src="uploadPreviewDlgImgUrl"
                        />
                      </el-dialog>
                    </div>
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
                    label="用户名"
                    prop="userName"
                    class="avue-form__item--left"
                  >
                    <el-tooltip placement="bottom">
                      <div slot="content">
                        请输入用户名
                      </div>
                      <el-input
                        v-model="userForm.userName"
                        type="text"
                        :minlength="3"
                        :maxlength="120"
                        :show-word-limit="true"
                        class="avue-input"
                        :clearable="true"
                        :disabled="true"
                        placeholder="请输入用户名"
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
                    label="昵称"
                    prop="nickName"
                    class="avue-form__item--left"
                  >
                    <el-tooltip placement="bottom">
                      <div slot="content">
                        请输入昵称
                      </div>
                      <el-input
                        v-model="userForm.nickName"
                        type="text"
                        :minlength="3"
                        :maxlength="120"
                        :show-word-limit="true"
                        class="avue-input"
                        :clearable="true"
                        placeholder="请输入昵称"
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
                    label="性别"
                    prop="sex"
                    class="avue-form__item--left"
                  >
                    <el-tooltip placement="bottom">
                      <div slot="content">
                        请选择性别
                      </div>
                      <div class="avue-radio">
                        <el-radio-group
                          v-model="userForm.sex"
                        >
                          <el-radio :label="1">
                            男
                          </el-radio>
                          <el-radio :label="2">
                            女
                          </el-radio>
                          <el-radio :label="3">
                            未知
                          </el-radio>
                        </el-radio-group>
                      </div>
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
                    label="出生日期"
                    prop="birth"
                    class="avue-form__item--left"
                  >
                    <div class="avue-date">
                      <el-date-picker
                        v-model="userForm.birth"
                        :picker-options="endPickerOptions"
                        type="date"
                        :unlink-panels="false"
                        format="yyyy-MM-dd"
                        :clearable="true"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择出生日期"
                      />
                    </div>
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
                    label="签名"
                    prop="signature"
                    class="avue-form__item--left"
                  >
                    <el-tooltip placement="bottom">
                      <div slot="content">
                        请输入您的个性签名
                      </div>
                      <el-input
                        v-model="userForm.signature"
                        type="textarea"
                        :minlength="3"
                        :maxlength="120"
                        :rows="5"
                        :show-word-limit="true"
                        class="avue-input"
                        :clearable="true"
                        placeholder="请输入您的个性签名"
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
                    label="地址"
                    prop="address"
                    class="avue-form__item--left"
                  >
                    <el-tooltip placement="bottom">
                      <div slot="content">
                        请输入联系地址
                      </div>
                      <el-input
                        v-model="userForm.address"
                        type="text"
                        :minlength="3"
                        :maxlength="220"
                        :show-word-limit="true"
                        class="avue-input"
                        :clearable="true"
                        placeholder="请输入联系地址"
                      />
                    </el-tooltip>
                  </el-form-item>
                </el-col>
                <el-col :span="24" class="avue-form__menu avue-form__menu--center">
                  <el-form-item label-width="0px">
                    <el-button
                      type="primary"
                      icon="el-icon-check"
                      :loading="addViewEditFormSubmitDisable"
                      @click="handleSubmit"
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
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import upload from '@/api/sys/upload'
import { uniqueStr } from '@/util/NumberUtil'
import { formatDate } from '@/util/DateUtil'
import { deepClone } from '@/util/ObjectUtil'

const delFileAction = 'sys/upload/deleteFile'
const userUpdateAction = 'sys/user/updateUser'
const updateUserInfoAction = 'sys/user/updateUserInfo'
// const activeUserTabAction = 'app/switchUserCenterActiveTab'
export default {
  name: 'Basic',
  data () {
    return {
      // 上传相关
      uploadTypeList: {
        img: /\.(gif|jpg|jpeg|png|GIF|JPG|PNG)/,
        video: /\.(swf|avi|flv|mpg|rm|mov|wav|asf|3gp|mkv|rmvb|ogg)/
      },
      uploadLoading: false,
      uploadDisabled: false,
      uploadType: 'picture-img',
      uploadMenu: false,
      uploadAction: '',
      uploadToken: '',
      uploadLimit: 1,
      uploadDrag: false,
      uploadShowFileList: false,
      uploadFileList: [],
      uploadFileUrl: '',
      uploadPreviewDlgShow: false,
      uploadPreviewDlgImgUrl: '',
      // 出生日期
      endPickerOptions: {
        disabledDate: this.handleDisabledDate
      },
      // 上传结束
      userForm: {
        avatar: '',
        userName: '',
        nickName: '',
        sex: 3,
        birth: formatDate(new Date()),
        signature: '',
        address: ''
      },
      userFormRules: {
        avatar: [{ required: false, message: '请上传头像', trigger: 'blur' }],
        userName: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        nickName: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
        sex: [{ required: false, message: '请输入性别', trigger: 'blur' }],
        birth: [{ required: false, message: '请输入出生日期', trigger: 'blur' }],
        signature: [{ required: false, message: '请输入签名', trigger: 'blur' }],
        address: [{ required: false, message: '请输入地址', trigger: 'blur' }]
      },
      addViewEditFormSubmitDisable: false
    }
  },
  computed: { ...mapGetters(['userInfo', 'token']) },
  mounted () {
    // 设置激活的用户中心tab标签
    // this.$store.dispatch(activeUserTabAction, 'basic')
    // 设置对应的用户的头像相关上传信息
    this.userForm = deepClone(this.userInfo)
    this.uploadFileUrl = this.userForm.avatar
  },
  methods: {
    handleUploadBefore (file) {
      const accept = file.type
      const filesize = file.size
      console.log('上传文件类型:', accept, '上传文件大小: ', filesize)
    },
    handleUploadHttpRequest (config) {
      this.uploadLoading = true
      const uploadData = new FormData()
      uploadData.append('file', config.file)
      uploadData.append('userId', this.userInfo.id)
      uploadData.append('token', this.uploadToken || uniqueStr())
      return upload.uploadFile(uploadData)
    },
    handleUploadExceedLimit (files, fileList) {
      this.$message.warning(
        `当前限制选择 1 个文件，本次选择了 ${
          files.length
        } 个文件，共上传了 ${files.length + fileList.length} 个文件`
      )
    },
    handleUploadChange (file, fileList) {
      this.uploadFileList = fileList
    },
    handleUploadSuccess (res, file, fileList) {
      this.uploadLoading = false
      const resCode = res.code
      if (resCode === 0) {
        const resData = res.data
        this.uploadFileUrl = resData.url
        this.uploadToken = resData.token
        file.url = resData.url
      } else {
        file.url = ''
        fileList.splice(fileList.length - 1, 1)
      }
      this.$message.success('头像更新成功')
    },
    handleUploadError (res, file, fileList) {
      this.uploadLoading = false
      file.url = ''
      fileList.splice(fileList.length - 1, 1)
      this.$message.error('上传失败，请稍后重试!')
    },
    handleUploadPreview (file) {
      if (file.url) {
        const url = file.url
        this.uploadPreviewDlgImgUrl = url
        this.uploadPreviewDlgShow = true
      }
    },
    // 上传删除文件
    handleUploadRemove (file, fileList) {
      return new Promise((resolve, reject) => {
        this.$confirm('确定将选择数据删除?', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          resolve()
        }).catch((action) => {
          this.$message.info('取消删除!')
          reject(action)
        })
      })
    },
    handleUploadOnRemove (file, fileList) {
      this.uploadLoading = true
      this.$store.dispatch(delFileAction, { url: file.url }).then((res) => {
        this.uploadLoading = false
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          this.uploadFileUrl = ''
          this.$message({
            type: 'success',
            showClose: true,
            message: '删除上传文件操作成功!'
          })
        } else {
          this.$message({
            type: 'error',
            duration: 5000,
            showClose: true,
            message: message || '删除上传文件失败,请稍后再试!'
          })
        }
      }).finally(() => {
        this.uploadLoading = false
      })
    },
    handleDisabledDate (date) {
      return new Date(formatDate(date)) >= new Date()
    },
    handleSubmit (form, done) {
      this.$refs.userForm.validate((valid) => {
        if (valid) {
          this.addViewEditFormSubmitDisable = true
          const updateFormData = {
            id: this.userInfo.id,
            avatar: this.uploadFileUrl,
            userName: this.userForm.userName,
            nickName: this.userForm.nickName,
            sex: this.userForm.sex,
            birth: this.userForm.birth,
            signature: this.userForm.signature,
            address: this.userForm.address,
            token: this.token
          }
          this.$store.dispatch(userUpdateAction, updateFormData).then((res) => {
            this.addViewEditFormSubmitDisable = false
            const resCode = res.code
            const errorMsg = res.message
            if (resCode === 0) {
              // 更新本地的state用户数据信息
              const updateUserInfo = {
                avatar: this.uploadFileUrl,
                userName: this.userForm.userName,
                nickName: this.userForm.nickName,
                sex: this.userForm.sex,
                birth: this.userForm.birth,
                signature: this.userForm.signature,
                address: this.userForm.address
              }
              this.$store.dispatch(updateUserInfoAction, updateUserInfo).then(() => {
                this.$message({
                  type: 'success',
                  message: '更新用户资料成功!'
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
            this.addViewEditFormSubmitDisable = false
            this.$message({
              type: 'error',
              showClose: true,
              message: '更新用户数据异常,请稍后再试!'
            })
            console.log(error)
          })
        }
      })
    }
  },
  head () {
    return {
      title: '基本资料 | 个人中心',
      meta: [
        { hid: 'description', name: 'description', content: '个人资料,个人中心' }
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
    color: rgba(0,0,0,.85);
    font-size: 20px;
    font-weight: 500;
    line-height: 28px;
    margin-bottom: 12px;
  }
  .account-settings-info-view {
    padding-top: 12px;
  }
}
</style>
