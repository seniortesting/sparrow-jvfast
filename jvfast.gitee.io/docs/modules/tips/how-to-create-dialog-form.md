---
title: 如何新建一个弹出对话框的表单组件
---

因为`avue`的源码封装的`dialog`有些自定义还是比较麻烦，而且如果真的自定义复杂的东西多的时候，你会发现`avue`真的很痛苦，所以最好的还是直接使用原始的`element`的组件进行开发。所以下面代码就做了一个建议的element的弹出框模块代码。

### 1. Html代码

全部代码样式引入的是`avue`配置好的样式，没有做封装，只是使用的`element`的原始组件，这样更方便查看文档，进行个性化定制，代码如下：
```
 <!--  导入表单-->
    <el-dialog
      v-dialogDrag
      lock-scroll
      :destroy-on-close="false"
      class="avue-crud__dialog"
      :fullscreen="false"
      :modal-append-to-body="false"
      append-to-body
      :close-on-press-escape="true"
      :close-on-click-modal="true"
      :modal="true"
      :show-close="true"
      :visible.sync="importExcelBox"
      width="45%"
      @close="handleDialogClose"
    >
      <div slot="title" class="avue-crud__dialog__header">
        <span class="el-dialog__title">通过Excel导入项目实景任务</span>
      </div>
      <div
        ref="content"
        :style="{overflow:'hidden'}"
      >
        <el-scrollbar style="height:100%">
          <el-form
            ref="importForm"
            v-loading="submitBtnDisabled"
            status-icon
            :model="excelForm"
            label-suffix=":"
            label-position="left"
            label-width="180px"
            :rules="importFormRules"
            @submit.native.prevent
          >
            <el-row :span="24">
              <el-group>
                <div class="avue-form__group">
                  <!-- 各个组件-->
                  <el-col :xs="24" :sm="12" :md="24" :span="24" :offset="0" class="avue-form__row" style="padding-left: 10px; padding-right: 10px;">
                    <el-form-item
                      label="上传任务计划表"
                      prop="file"
                      class="avue-form__item--left"
                    >
                      <el-upload
                        :drag="true"
                        action=""
                        name="file"
                        :limit="1"
                        accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/csv"
                        :on-change="handleUploadChange"
                        :before-upload="handleUploadBefore"
                        :on-exceed="handleUploadExceedLimit"
                        :on-success="handleUploadSuccess"
                        :on-remove="handleUploadRemove"
                        :file-list="uploadFileList"
                      >
                        <i class="el-icon-upload" />
                        <div class="el-upload__text">
                          将文件拖到此处，或
                          <em>点击上传</em>
                        </div>
                        <div
                          slot="tip"
                          class="el-upload__tip"
                        >
                          只能上传xls/xlsx格式的Excel文件，且不超过15mb
                        </div>
                      </el-upload>
                    </el-form-item>
                  </el-col>
                  <el-col :md="24" :xs="24" :offset="0" class="avue-form__row" style="padding-left: 10px; padding-right: 10px;">
                    <el-form-item
                      label="Excel工作簿"
                      prop="sheetNumber"
                      class="avue-form__item--left"
                    >
                      <el-input-number v-model="excelForm.sheetNumber" :min="1" />
                    </el-form-item>
                  </el-col>
                  <el-col :span="24" class="avue-form__menu avue-form__menu--right">
                    <el-form-item label-width="0px">
                      <el-button
                        type="primary"
                        icon="el-icon-check"
                        :loading="submitBtnDisabled"
                        @click="handleImportsubmitForm"
                      >
                        提交
                      </el-button>
                      <el-button
                        icon="el-icon-delete"
                        :loading="submitBtnDisabled"
                        @click="handleImportResetForm"
                      >
                        清空
                      </el-button>
                    </el-form-item>
                  </el-col>
                </div>
              </el-group>
            </el-row>
          </el-form>
        </el-scrollbar>
      </div>
    </el-dialog>
```

## 2. JavaScript代码

基于`avue`的样式，可以更加方便自定义 `element`的所有组件和表单。代码如下：

```
export default {
  data() {
      return {
        // 导入任务弹出框，开始
        importExcelBox: false,
        excelForm: {
            projectId: null,
            sheetNumber: 1,
            file: null
        },
        importFormRules: {
            sheetNumber: [
            { required: true, message: '请输入上传Excell工作簿', trigger: 'blur' }
            ],
            file: [
            { required: true, message: '请上传excel任务文件', trigger: 'blur' }
            ]
        },
        uploadFileList: [],
        submitBtnDisabled: false,
        // 导入任务弹出框，结束
      }
  },
  methods: {
    handleDialogTaskShow (row, index) {
      this.importExcelBox = true
      this.excelForm.projectId = row.id
    },
    handleDialogClose (row) {
      if (row) {
        row = {}
      }
      this.importExcelBox = false
    },
    handleDialogFullscreen () {
      // if (this.fullscreen) {
      //   this.fullscreen = false
      // } else {
      //   this.fullscreen = true
      // }
    },
    handleUploadBefore (file) {
    },
    handleUploadExceedLimit (files, fileList) {
      this.$message.warning(
        `当前限制选择 1 个文件，本次选择了 ${
          files.length
        } 个文件，共上传了 ${files.length + fileList.length} 个文件`
      )
    },
    handleUploadChange (file, fileList) {
      this.excelForm.file = file.raw
    },
    handleUploadSuccess (res) {
      // this.excelForm.file = res.url
    },
    // 上传删除文件
    handleUploadRemove (file, fileList) {

    },
    handleImportsubmitForm () {
      this.$refs.importForm.validate((valid) => {
        if (valid) {
          this.submitBtnDisabled = true
          const formdata = new FormData()
          formdata.append('projectId', this.excelForm.projectId)
          formdata.append('sheetNumber', this.excelForm.sheetNumber)
          formdata.append('file', this.excelForm.file)
          this.$store.dispatch(importProjectTaskAction, formdata).then((res) => {
            this.submitBtnDisabled = false
            const resCode = res.code
            const message = res.message
            if (resCode === 0) {
              // this.form = res.data
              // 关闭对话框
              this.$message({ type: 'success', message: '导入任务完成！' })
              this.handleDialogClose(this.excelForm)
            } else {
              this.$message({
                type: 'error',
                message: message || '查询数据失败,请稍后再试!'
              })
            }
          }).catch((err) => {
            console.log(err)
            this.submitBtnDisabled = false
            this.$message({
              type: 'error',
              message: '导入数据异常,请稍后再试!'
            })
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    handleImportResetForm () {
      this.$refs.importForm.resetFields()
      this.uploadFileList = []
      this.$emit('input', this.excelForm)
    }
  }

}
```