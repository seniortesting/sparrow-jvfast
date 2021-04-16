---
title: el-upload组件,上传文件组件模块
---

上传组件基于avue，完全采用element的所有操作接口，自定义更方便，模板代码基本集成大部分功能，如果还不能满足的功能，可以参考element的文档进行修改。

## 组件部分代码

```
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
                            :readonly="addViewEditDlgType==='view'"
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
                            :disabled="addViewEditDlgType==='view'"
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
```

其他的文件类型accept参数参考：<https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/file#Unique_file_type_specifiers>

## js部分代码

```
export default {
  data () {
    return {
      uploadTypeList: {
        img: /\.(gif|jpg|jpeg|png|GIF|JPG|PNG)/,
        video: /\.(swf|avi|flv|mpg|rm|mov|wav|asf|3gp|mkv|rmvb|ogg)/
      },
      uploadLoading: false,
      uploadDisabled: false,
      // 类型， 头像: picture-img, 图片列表: picture-card,拖动： drag
      uploadType: 'picture-card',
      uploadMenu: false,
      uploadAction: '',
      uploadToken: '',
      uploadLimit: 6,
      uploadDrag: false,
      uploadShowFileList: true,
      uploadFileList: [],
      uploadFileUrl: '',
      uploadPreviewDlgShow: false,
      uploadPreviewDlgImgUrl: ''
    }
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
      this.$message.success('上传成功')
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
      upload.delFile({ url: file.url }).then((res) => {
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
  }    
```

### 问题

1. 从以前的代码可以知道上传的文件对应的变量是单独维护的，所以如果需要需要引用上传的文件的信息，注意需要单独设置。例如新增对话框时需要设置:
```
        this.uploadToken = ''
        this.uploadFileList = []
```
更新对话框也需要进行相应的设置