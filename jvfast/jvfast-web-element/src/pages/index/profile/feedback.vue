<template>
  <el-card-container title="我的反馈建议">
    <!--    列表组件-->
    <div class="avue-crud">
      <!--      搜索框部分-->
      <!-- 表格功能列 -->
      <div class="avue-crud__menu">
        <div class="avue-crud__right">
          <!--              <div class="avue-date" style="display:inline-block;margin-right:20px;">-->
          <!--                <el-date-picker-->
          <!--                  v-model="dateRange"-->
          <!--                  type="daterange"-->
          <!--                  value-format="yyyy-MM-dd"-->
          <!--                  align="right"-->
          <!--                  unlink-panels-->
          <!--                  range-separator="至"-->
          <!--                  start-placeholder="开始日期"-->
          <!--                  end-placeholder="结束日期"-->
          <!--                  :picker-options="datePickerOptions"-->
          <!--                  @change="changeDateRange"-->
          <!--                />-->
          <!--              </div>-->
          <el-tooltip
            effect="dark"
            content="刷新"
            placement="top"
          >
            <el-button
              icon="el-icon-refresh"
              circle
              @click="handleRefreshTable"
            />
          </el-tooltip>
        </div>
      </div>
      <!-- 提示信息部分-->
      <!--      表格部分-->
      <el-table
        ref="table"
        v-loading="table.loading"
        :data="table.data"
        :row-key="handleGetRowKeys"
        :class="{'avue-crud--indeterminate': false}"
        size="medium"
        :lazy="false"
        :tree-props="{}"
        :expand-row-keys="table.expandRowKeys"
        :default-expand-all="table.defaultExpandAll"
        :highlight-current-row="table.highlightCurrentRow"
        :show-summary="table.showSummary"
        :stripe="table.stripe"
        :show-header="table.showHeader"
        :default-sort="table.defaultSort"
        :fit="table.fit"
        :max-height="table.maxHeight"
        :height="table.height"
        :width="table.width"
        :border="table.border"
        @row-click="handleTableRowClick"
        @selection-change="handleTableSelectionChange"
      >
        <!-- 暂无数据提醒 -->
        <template slot="empty">
          <div class="avue-crud__empty">
            <slot
              v-if="$slots.empty"
              name="empty"
            />
            <el-empty
              v-else
              size="50"
              image="data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iNjQiIGhlaWdodD0iNDEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CiAgPGcgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoMCAxKSIgZmlsbD0ibm9uZSIgZmlsbC1ydWxlPSJldmVub2RkIj4KICAgIDxlbGxpcHNlIGZpbGw9IiNGNUY1RjUiIGN4PSIzMiIgY3k9IjMzIiByeD0iMzIiIHJ5PSI3Ii8+CiAgICA8ZyBmaWxsLXJ1bGU9Im5vbnplcm8iIHN0cm9rZT0iI0Q5RDlEOSI+CiAgICAgIDxwYXRoIGQ9Ik01NSAxMi43Nkw0NC44NTQgMS4yNThDNDQuMzY3LjQ3NCA0My42NTYgMCA0Mi45MDcgMEgyMS4wOTNjLS43NDkgMC0xLjQ2LjQ3NC0xLjk0NyAxLjI1N0w5IDEyLjc2MVYyMmg0NnYtOS4yNHoiLz4KICAgICAgPHBhdGggZD0iTTQxLjYxMyAxNS45MzFjMC0xLjYwNS45OTQtMi45MyAyLjIyNy0yLjkzMUg1NXYxOC4xMzdDNTUgMzMuMjYgNTMuNjggMzUgNTIuMDUgMzVoLTQwLjFDMTAuMzIgMzUgOSAzMy4yNTkgOSAzMS4xMzdWMTNoMTEuMTZjMS4yMzMgMCAyLjIyNyAxLjMyMyAyLjIyNyAyLjkyOHYuMDIyYzAgMS42MDUgMS4wMDUgMi45MDEgMi4yMzcgMi45MDFoMTQuNzUyYzEuMjMyIDAgMi4yMzctMS4zMDggMi4yMzctMi45MTN2LS4wMDd6IiBmaWxsPSIjRkFGQUZBIi8+CiAgICA8L2c+CiAgPC9nPgo8L3N2Zz4K"
              desc="暂无数据"
            />
          </div>
        </template>
        <!-- 选择框 -->
        <el-table-column
          type="selection"
          :reserve-selection="false"
          :width="50"
          fixed="left"
          align="center"
        />
        <!-- 序号 -->
        <!--        <el-table-column-->
        <!--          type="index"-->
        <!--          label="序号"-->
        <!--          :width="50"-->
        <!--          :index="handleIndexMethod"-->
        <!--          fixed="left"-->
        <!--          align="center"-->
        <!--        />-->
        <!--        <el-table-column width="1px" />-->
        <!--        主体部分-->
        <template v-for="(column) in table.columns">
          <el-table-column
            :key="column.prop"
            :prop="column.prop"
            :label="column.label"
            filter-placement="bottom-end"
            :filter-multiple="true"
            :show-overflow-tooltip="true"
            :min-width="column.minWidth"
            :sortable="column.sortable"
            :render-header="column.renderHeader"
            :align="column.align"
            :header-align="column.headerAlign"
            :width="column.width"
            :fixed="$device.isMobile?false:column.fixed"
          >
            <template slot-scope="scope">
              <span>
                <span v-if="['status'].includes(column.type)">
                  <div class="avue-crud__img">
                    <el-tooltip content="意见反馈的处理状态" placement="top">
                      <el-tag :type="scope.row[column.prop]===1000? 'info':(scope.row[column.prop]===1001?'warning': 'success')">
                        {{ formatStatus(scope.row[column.prop]) }}
                      </el-tag>
                    </el-tooltip>
                  </div>
                </span>
                <span
                  v-else
                  v-html="handleDetail(scope.row,column)"
                />
              </span>
            </template>
          </el-table-column>
        </template>
        <!--        菜单栏部分-->
      </el-table>
    </div>
    <!--    新增对话框查看对话框编辑对话框-->
    <component
      :is="table.tableDlgType"
      v-draggable
      lock-scroll
      :wrapper-closable="true"
      direction="rtl"
      class="avue-dialog avue-crud__dialog"
      :fullscreen="false"
      :modal-append-to-body="false"
      append-to-body
      :close-on-press-escape="true"
      :close-on-click-modal="true"
      :modal="true"
      :show-close="true"
      :visible.sync="addViewEditDlgShow"
      size="50%"
      width="50%"
      @close="handleAddViewEditDlgClose"
    >
      <div slot="title" class="avue-crud__dialog__header">
        <span class="el-dialog__title">我的反馈建议{{ addViewEditDlgType==='add'? '新增':(addViewEditDlgType==='edit'? '编辑': '查看') }}</span>
      </div>
      <div
        ref="content"
        :style="{overflow:'hidden'}"
      >
        <el-scrollbar style="height:100%">
          <div class="avue-form" style="width: 100%">
            <el-form
              ref="addViewEditForm"
              status-icon
              :model="addViewEditForm"
              label-suffix=":"
              label-position="left"
              label-width="140px"
              @submit.native.prevent
            >
              <el-row :span="24">
                <el-group>
                  <div class="avue-form__group">
                    <el-col
                      :sm="12"
                      :xs="24"
                      :md="24"
                      :offset="0"
                      class="avue-form__row"
                      style="padding-left: 10px; padding-right: 10px;"
                    >
                      <el-form-item
                        label="详情"
                        prop="detail"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入详情
                          </div>
                          <el-input
                            v-model="addViewEditForm.detail"
                            type="textarea"
                            :minlength="3"
                            :maxlength="120"
                            :rows="5"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            :disabled="addViewEditDlgType==='view'"
                            placeholder="请输入详情"
                          />
                        </el-tooltip>
                      </el-form-item>
                    </el-col>
                    <el-col
                      :sm="12"
                      :xs="24"
                      :md="24"
                      :offset="0"
                      class="avue-form__row"
                      style="padding-left: 10px; padding-right: 10px;"
                    >
                      <el-form-item
                        label="截屏"
                        prop="screenshot"
                        class="avue-form__item--left"
                      >
                        <el-image :src="addViewEditForm.screenshot" :preview-src-list="[addViewEditForm.screenshot]" />
                      </el-form-item>
                    </el-col>
                    <el-col
                      :sm="12"
                      :xs="24"
                      :md="24"
                      :offset="0"
                      class="avue-form__row"
                      style="padding-left: 10px; padding-right: 10px;"
                    >
                      <el-form-item
                        label="处理状态"
                        prop="feedbackStatus"
                        class="avue-form__item--left"
                      >
                        <el-select
                          ref="selectProjectSearch"
                          v-model="addViewEditForm.feedbackStatus"
                          class="avue-select"
                          :multiple="false"
                          :multiple-limit="1"
                          :filterable="false"
                          :allow-create="false"
                          :default-first-option="true"
                          :remote="false"
                          :readonly="false"
                          :clearable="true"
                          placeholder="选择处理状态"
                          :disabled="addViewEditDlgType==='view'"
                        >
                          <el-option
                            v-for="item in feedbackStatusDicData"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                          />
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col
                      :sm="12"
                      :xs="24"
                      :md="24"
                      :offset="0"
                      class="avue-form__row"
                      style="padding-left: 10px; padding-right: 10px;"
                    >
                      <el-form-item
                        label="处理原因/结果"
                        prop="reason"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入处理原因/结果
                          </div>
                          <el-input
                            v-model="addViewEditForm.reason"
                            type="textarea"
                            :minlength="3"
                            :maxlength="120"
                            :rows="5"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            :disabled="addViewEditDlgType==='view'"
                            placeholder="请输入处理原因/结果"
                          />
                        </el-tooltip>
                      </el-form-item>
                    </el-col>
                    <!-- 各个组件-->
                  </div>
                </el-group>
              </el-row>
            </el-form>
          </div>
        </el-scrollbar>
      </div>
    </component>
  </el-card-container>
</template>

<script>
import { mapGetters } from 'vuex'

const listSysFeedbackAction = 'sys/feedback/listSysFeedback'
const getSysFeedbackByIdAction = 'sys/feedback/getSysFeedbackById'
export default {
  name: 'Feedback',
  data () {
    return {
      feedbackStatusDicData: [
        { label: '等待处理', value: 1000 },
        { label: '已解决', value: 1001 },
        { label: '关闭', value: 1002 }
      ],
      table: {
        loading: false,
        tableDlgType: 'elDialog',
        rowKey: 'id',
        expandRowKeys: [],
        defaultExpandAll: false,
        highlightCurrentRow: true,
        showSummary: false,
        stripe: false, // 是否显示表格的斑马条纹
        showHeader: true,
        defaultSort: undefined,
        fit: true, // 列的宽度是否自撑开
        height: undefined,
        maxHeight: 'auto',
        width: '100%',
        border: true,
        data: [],
        columns: [
          {
            prop: 'detail',
            label: '详情',
            display: true,
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false
          },
          {
            prop: 'feedbackStatus',
            label: '处理状态',
            display: true,
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false,
            type: 'status'
          }, {
            prop: 'reason',
            label: '处理原因/结果',
            display: true,
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false
          },
          {
            prop: 'createTime',
            label: '提交时间',
            display: true,
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false
          }
        ],
        selectList: [],
        clickRow: {}
      },
      page: {},
      query: {},
      addViewEditDlgShow: false,
      addViewEditDlgType: 'view',
      addViewEditForm: {
        title: '',
        detail: '',
        screenshot: '',
        feedbackStatus: '',
        reason: ''
      }
    }
  },
  computed: {
    ...mapGetters(['permissions', 'website', 'userInfo']),
    selectRowLen () {
      return this.table.selectList ? this.table.selectList.length : 0
    }
  },
  mounted () {
    this.fetchTable(this.page)
  },
  methods: {
    handleRefreshTable () {
      this.fetchTable(this.page)
    },
    handleGetRowKeys (row) {
      const rowKey = row[this.table.rowKey] + '_' + Math.random()
      return rowKey
    },
    handleTableRowClick (row, event, column) {
      this.table.clickRow = row
      this.handleRowViewDlgShow(row)
    },
    handleTableSelectionChange (selection) {
      // 单行或多行选择回调
      this.table.selectList = selection
    },
    // 格式化输出每行数据
    handleDetail (row, column) {
      let result = row[column.prop]
      // 自定义格式化
      if (column.formatter && typeof column.formatter === 'function') {
        result = column.formatter(row, row[column.prop], result, column)
      }
      return result
    },
    formatStatus (value) {
      const findStatus = this.feedbackStatusDicData.find(f => f.value === value)
      return findStatus ? findStatus.label : '未知状态'
    },
    // 操作栏功能
    handleAddViewEditDlgClose (row) {
      if (row) {
        for (const key in row) {
          row[key] = undefined
        }
      }
      this.addViewEditDlgShow = false
      this.fetchTable(this.page)
    },
    handleRowViewDlgShow (row, index) {
      // 显示弹出框
      this.addViewEditDlgShow = true
      this.addViewEditDlgType = 'view'
      this.$nextTick(() => {
      })
      // 获取对应的数据
      this.handleFetchRowByRowId(row)
    },
    handleFetchRowByRowId (row) {
      const idParams = { id: row.id }
      this.$store.dispatch(getSysFeedbackByIdAction, idParams).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          this.addViewEditForm = res.data
        } else {
          this.$message({
            type: 'error',
            message: message || '查询数据失败,请稍后再试!'
          })
        }
      }).catch((err) => {
        console.log(err)
        this.$message({
          type: 'error',
          message: '查询数据异常,请稍后再试!'
        })
      })
    },
    // -------------- 表格列表
    fetchTable (page, params = {}) {
      this.table.loading = true
      let queryData = {
        id: this.userInfo.id
      }
      queryData = Object.assign(queryData, Object.assign(params, this.query))
      this.$store.dispatch(listSysFeedbackAction, queryData).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          const data = res.data
          this.table.data = data
        } else {
          this.$message({
            type: 'error',
            message: message || '查询数据失败,请稍后再试!'
          })
        }
      }).catch((err) => {
        console.log(err)
        this.$message({
          type: 'error',
          message: '查询数据异常,请稍后再试!'
        })
      }).finally(() => {
        this.table.loading = false
      })
    },
    beforeOpen (done, type) {
      if (['edit', 'view'].includes(type)) {
        const idParams = { id: this.form.id }
        this.$store.dispatch(getSysFeedbackByIdAction, idParams).then((res) => {
          const resCode = res.code
          const errorMsg = res.message
          if (resCode === 0) {
            this.form = res.data
          } else {
            this.$message({
              type: 'error',
              showClose: true,
              message: errorMsg || '查询数据失败,请稍后再试!'
            })
          }
        }).catch((err) => {
          console.log(err)
          this.$message({
            type: 'error',
            showClose: true,
            message: '查询数据异常,请稍后再试!'
          })
        })
      }
      done()
    },
    refresh ({ page, searchForm }) {
      this.onLoad(page)
    },
    onLoad (page, params = {}) {
      this.loading = true
      let queryData = {
        id: this.userInfo.id
      }
      queryData = Object.assign(queryData, Object.assign(params, this.query))
      this.$store.dispatch(listSysFeedbackAction, queryData).then((res) => {
        this.loading = false
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          const data = res.data
          this.data = data
        } else {
          this.$message({
            type: 'error',
            showClose: true,
            message: message || '查询数据失败,请稍后再试!'
          })
        }
      }).catch((err) => {
        console.log(err)
        this.$message({
          type: 'error',
          showClose: true,
          message: '查询数据异常,请稍后再试!'
        })
      })
    }
  },
  head () {
    return {
      title: '意见反馈管理 | ' + this.website.name,
      meta: [
        { hid: 'description', name: 'description', content: '意见反馈管理' }
      ]
    }
  }
}
</script>

<style scoped>

</style>
