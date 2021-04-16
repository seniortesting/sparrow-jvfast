<template>
  <div class="profile">
    <!--    <div class="account-settings-info-title">-->
    <!--      <span data-v-26b608e6="">消息通知</span>-->
    <!--    </div>-->
    <div class="account-settings-info-view">
      <!--    列表组件-->
      <div class="avue-crud">
        <!-- 表格功能列 -->
        <div class="avue-crud__menu">
          <div class="avue-crud__left">
            <el-button
              :plain="notificationType[0]==0"
              type="primary"
              size="small"
              @click="handleFilterRecord(0)"
            >
              全部类型消息
            </el-button>
            <el-button
              :plain="notificationType[1]===0"
              type="primary"
              size="small"
              @click="handleFilterRecord(1)"
            >
              站内信
            </el-button>
            <el-button
              :plain="notificationType[2]===0"
              type="primary"
              size="small"
              @click="handleFilterRecord(2)"
            >
              邮件
            </el-button>
            <el-button
              :plain="notificationType[3]===0"
              type="primary"
              size="small"
              @click="handleFilterRecord(3)"
            >
              短信
            </el-button>
            <el-button
              :plain="notificationType[4]===0"
              type="primary"
              size="small"
              @click="handleFilterRecord(4)"
            >
              微信公众号
            </el-button>
            <el-button
              :plain="notificationType[5]===0"
              type="primary"
              size="small"
              @click="handleFilterRecord(5)"
            >
              微信小程序消息
            </el-button>
            <el-button
              :plain="notificationType[6]===0"
              type="primary"
              size="small"
              @click="handleFilterRecord(6)"
            >
              APP消息
            </el-button>
          </div>
          <div class="avue-crud__right">
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
        <el-tag class="avue-crud__tip">
          <span class="avue-crud__tip-name">
            当前表格已选择<span class="avue-crud__tip-count">{{ selectRowLen }}</span>项
          </span>
          <el-button
            type="text"
            size="small"
            @click="handleSelectClear"
          >
            清空
          </el-button>
        </el-tag>
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
          :row-style="handleTableRowStyle"
          :cell-style="handleTableCellStyle"
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
              :filters="handleColumnFilters(column)"
              :filter-method="column.filter? handleFiltersMethod : undefined"
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
                  <span v-if="['notificationType'].includes(column.type)">
                    <div class="avue-crud__img">
                      {{ formatNotificationType(scope.row,column) }}
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
        size="80%"
        width="80%"
        @close="handleAddViewEditDlgClose"
      >
        <div slot="title" class="avue-crud__dialog__header">
          <span class="el-dialog__title">消息通知{{ addViewEditDlgType==='add'? '新增':(addViewEditDlgType==='edit'? '编辑': '查看') }}</span>
        </div>
        <div
          ref="content"
          :style="{overflow:'hidden'}"
        >
          <el-scrollbar style="height:100%">
            <div class="avue-form" style="width: 100%">
              <el-form
                ref="addViewEditForm"
                v-loading="addViewEditFormSubmitDisable"
                status-icon
                :model="addViewEditForm"
                label-suffix=":"
                label-position="left"
                label-width="150px"
                :rules="addViewEditFormRules"
                @submit.native.prevent
              >
                <el-row :span="24">
                  <el-group>
                    <div class="avue-form__group">
                      <!-- 各个组件-->
                      <el-col
                        :sm="12"
                        :xs="12"
                        :md="24"
                        :offset="0"
                        class="avue-form__row"
                        style="padding-left: 10px; padding-right: 10px;"
                      >
                        <el-form-item
                          label="标题"
                          prop="title"
                          class="avue-form__item--left"
                        >
                          <el-tooltip placement="bottom">
                            <div slot="content">
                              请输入标题
                            </div>
                            <el-input
                              v-model="addViewEditForm.title"
                              type="text"
                              :minlength="3"
                              :maxlength="120"
                              :show-word-limit="true"
                              class="avue-input"
                              :clearable="true"
                              :disabled="addViewEditDlgType==='view'"
                              placeholder="请输入标题"
                            />
                          </el-tooltip>
                        </el-form-item>
                      </el-col>
                      <el-col
                        :sm="12"
                        :xs="12"
                        :md="24"
                        :offset="0"
                        class="avue-form__row"
                        style="padding-left: 10px; padding-right: 10px;"
                      >
                        <el-form-item
                          label="内容"
                          prop="content"
                          class="avue-form__item--left"
                        >
                          <el-tooltip placement="bottom">
                            <div slot="content">
                              请输入内容
                            </div>
                            <el-input
                              v-model="addViewEditForm.content"
                              type="textarea"
                              :minlength="3"
                              :maxlength="120"
                              :rows="5"
                              :show-word-limit="true"
                              class="avue-input"
                              :clearable="true"
                              :disabled="addViewEditDlgType==='view'"
                              placeholder="请输入内容"
                            />
                          </el-tooltip>
                        </el-form-item>
                      </el-col>
                      <el-col
                        :sm="12"
                        :xs="12"
                        :md="24"
                        :offset="0"
                        class="avue-form__row"
                        style="padding-left: 10px; padding-right: 10px;"
                      >
                        <el-form-item
                          label="类型"
                          prop="notificationType"
                          class="avue-form__item--left"
                        >
                          <el-select
                            ref="selectNotificationType"
                            v-model="addViewEditForm.notificationType"
                            class="avue-select"
                            :multiple="false"
                            :multiple-limit="1"
                            :filterable="false"
                            :allow-create="false"
                            :default-first-option="true"
                            :remote="false"
                            :readonly="false"
                            :clearable="true"
                            placeholder="选择类型"
                            :disabled="addViewEditDlgType==='view'"
                          >
                            <el-option
                              v-for="item in notificationTypes"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value"
                            />
                          </el-select>
                        </el-form-item>
                      </el-col>
                      <el-col
                        :sm="12"
                        :xs="12"
                        :md="24"
                        :offset="0"
                        class="avue-form__row"
                        style="padding-left: 10px; padding-right: 10px;"
                      >
                        <el-form-item
                          label="时间"
                          prop="updateTime"
                          class="avue-form__item--left"
                        >
                          <el-tooltip placement="bottom">
                            <div slot="content">
                              请输入时间
                            </div>
                            <el-input
                              v-model="addViewEditForm.updateTime"
                              type="text"
                              :minlength="3"
                              :maxlength="120"
                              :show-word-limit="true"
                              class="avue-input"
                              :clearable="true"
                              :disabled="addViewEditDlgType==='view'"
                              placeholder="请输入时间"
                            />
                          </el-tooltip>
                        </el-form-item>
                      </el-col>
                    </div>
                  </el-group>
                </el-row>
              </el-form>
            </div>
          </el-scrollbar>
        </div>
      </component>
      <div class="page">
        <div class="left">
          <el-checkbox v-model="checkAll" @change="changeCheckAll" />
          <el-button :disabled="btnDisabledStatus" size="small" @click="handleDelete">
            删除
          </el-button>
          <el-button :disabled="btnDisabledStatus" size="small" @click="handleRead">
            标记已读
          </el-button>
          <el-button size="small" @click="handleReadAll">
            全部已读
          </el-button>
          <el-button size="small" @click="handleDeleteAll">
            全部删除
          </el-button>
        </div>
        <div class="right">
          <el-pagination
            :small="$device.isMobile"
            :pager-count="$device.isMobile?5:7"
            :page-size="page.pageSize"
            :page-sizes="page.pageSizes"
            :total="page.total"
            current-page.sync="1"
            background
            layout="total, sizes, prev, pager, next, jumper"
            @size-change="handlePageSizeChange"
            @current-change="handlePageCurrentChange"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import { mapGetters } from 'vuex'

const addSysNotificationAction = 'sys/notification/addSysNotification'
const updateSysNotificationAction = 'sys/notification/updateSysNotification'
// const removeSysNotificationAction = 'sys/notification/removeSysNotification'
const removeSysNotificationsAction = 'sys/notification/removeSysNotifications'
const getSysNotificationByIdAction = 'sys/notification/getSysNotificationById'
const getSysNotificationPageListAction = 'sys/notification/pageSysNotification'
const readNotificationAction = 'sys/notification/readNotification'
export default {
  name: 'Notification',
  data () {
    return {
      notificationType: [1, 0, 0, 0, 0, 0, 0],
      checkAll: false,
      btnDisabledStatus: true,
      originalData: [],
      // -----表格部分开始
      notificationTypes: [
        { label: '站内信', value: 1 },
        { label: '邮件', value: 2 },
        { label: '短信', value: 3 },
        { label: '微信公众号', value: 4 },
        { label: '微信小程序消息', value: 5 },
        { label: 'APP消息', value: 6 }
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
            prop: 'id',
            label: '消息ID',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '200',
            fixed: false
          },
          {
            prop: 'title',
            label: '标题',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '200',
            fixed: false
          },
          {
            prop: 'content',
            label: '内容',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false
          },
          {
            prop: 'notificationType',
            label: '类型',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '80',
            fixed: false,
            type: 'notificationType'
          },
          {
            prop: 'updateTime',
            label: '时间',
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
      query: {},
      page: {
        total: 0, // 总页数
        pagerCount: 10, // 超过多少条隐藏
        pageNum: 1, // 当前页数
        pageSize: 10, // 每页显示多少条
        pageSizes: [10, 20, 30, 40, 50, 100],
        background: true // 背景颜色
      },
      // 表格弹出对话框
      addViewEditDlgShow: false,
      addViewEditDlgType: 'add',
      addViewEditForm: {
        title: '',
        content: '',
        notificationType: '',
        updateTime: ''
      },
      addViewEditFormRules: {
        title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
        content: [{ required: true, message: '请输入内容', trigger: 'blur' }],
        notificationType: [{ required: true, message: '请输入类型', trigger: 'blur' }],
        updateTime: [{ required: true, message: '请输入时间', trigger: 'blur' }]
      },
      addViewEditFormSubmitDisable: false
    }
  },
  computed: {
    ...mapGetters(['permissions', 'website', 'userInfo']),
    selectRowLen () {
      return this.table.selectList ? this.table.selectList.length : 0
    }
  },
  watch: {
    'page.total': {
      handler (value) {
        // 如果当前页面删除没数据了调用第一页
        if (this.page.total === (this.page.currentPage - 1) * this.page.pageSize && this.page.total !== 0) {
          this.page.currentPage = this.page.currentPage - 1
          this.fetchTable(this.page)
        }
      }
    }
  },
  mounted () {
    // 设置表单
    const self = this
    this.$nextTick(() => {
      const clientHeight = document.documentElement.clientHeight
      if (this.table.height === 'auto') {
        this.$nextTick(() => {
          const tableStyle = self.$refs.table.$el
          const pageStyle = self.$refs.tablePage.$el
          self.table.height = clientHeight - tableStyle.offsetTop - (pageStyle.offsetHeight * 3) - self.calcHeight
        })
      }
    })
    this.fetchTable(this.page)
  },
  methods: {
    changeCheckAll (newValue) {
      // if (newValue) {
      //   this.table.selectList = this.table.data
      //   this.$refs.table.toggleAllSelection()
      // } else {
      //   this.table.selectList = []
      //   this.$refs.table.toggleAllSelection()
      // }
      this.$refs.table.toggleAllSelection()
    },
    handleFilterRecord (type) {
      const filterData = type > 0 ? this.originalData.filter(d => d.notificationType === type) : this.originalData
      this.notificationType = this.notificationType.map((n, index) => {
        n = type === index ? 1 : 0
        return n
      })
      this.table.data = filterData
    },
    handleRead () {
      if (this.table.selectList.length === 0) {
        this.$message.warning('请选择至少一条数据')
        return
      }
      this.$confirm(`您确定要标记当前${this.table.selectList.length}条消息为已读吗？`, {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.table.loading = true
          const ids = []
          this.table.selectList.forEach((e) => {
            ids.push(e.id)
          })
          const idParams = { ids }
          return this.$store.dispatch(readNotificationAction, idParams)
        })
        .then((res) => {
          this.table.loading = false
          const resCode = res.code
          const message = res.message
          if (resCode === 0) {
            this.$message({
              type: 'success',
              message: '标记已读消息操作成功!'
            })
            this.fetchTable(this.page)
            this.$refs.table.toggleSelection()
          } else {
            this.table.loading = false
            this.$message({
              type: 'error',
              showClose: true,
              message: message || '标记已读消息失败,请稍后再试!'
            })
          }
        }).catch((action) => {
          this.table.loading = false
        })
    },
    handleReadAll () {
      this.table.selectList = this.table.data
      this.handleRead()
    },
    handleDeleteAll () {
      this.table.selectList = this.table.data
      this.handleDelete()
    },
    handleDelete () {
      if (this.table.selectList.length === 0) {
        this.$message.warning('请选择至少一条数据')
        return
      }
      this.$confirm(`确定将当前${this.table.selectList.length}条消息删除吗?`, {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.table.loading = true
          const ids = []
          this.table.selectList.forEach((e) => {
            ids.push(e.id)
          })
          const idParams = { ids }
          return this.$store.dispatch(removeSysNotificationsAction, idParams)
        })
        .then((res) => {
          this.table.loading = false
          const resCode = res.code
          const message = res.message
          if (resCode === 0) {
            this.$message({
              type: 'success',
              message: '批量删除操作成功!'
            })
            this.fetchTable(this.page)
            this.$refs.table.toggleSelection()
          } else {
            this.table.loading = false
            this.$message({
              type: 'error',
              showClose: true,
              message: message || '批量删除数据失败,请稍后再试!'
            })
          }
        }).catch(() => {
          this.table.loading = false
        })
    },
    // 表格刷新
    handleRefreshTable () {
      this.fetchTable(this.page)
    },
    handleSelectClear () {
      this.table.selectList = []
      this.$refs.table.clearSelection()
    },
    // -------------- 表格列表
    fetchTable (page, params = {}) {
      this.table.loading = true
      let queryData = {
        pageNo: page.currentPage,
        pageSize: page.pageSize,
        userId: this.userInfo.id
      }
      queryData = Object.assign(queryData, Object.assign(params, this.query))
      this.$store.dispatch(getSysNotificationPageListAction, queryData).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          const data = res.data
          this.originalData = data.records
          this.page.total = parseInt(data.total)
          this.table.data = data.records
          this.handleSelectClear()
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
    handleGetRowKeys (row) {
      const rowKey = row[this.table.rowKey]
      return rowKey
    },
    handleTableRowStyle ({ row, column, rowIndex }) {
      return {}
    },
    handleTableCellStyle ({ row, column, rowIndex, columnIndex }) {
      if (row.readStatus === 1) {
        return {
          fontWeight: '400'
        }
      } else {
        return {
          fontWeight: '700'
        }
      }
    },
    handleTableRowClick (row, event, column) {
      this.table.clickRow = row
      this.handleRowViewDlgShow(row)
      // 标记为已读
      const idParams = { ids: [row.id] }
      this.$store.dispatch(readNotificationAction, idParams).then((res) => {
        const resCode = res.code
        if (resCode === 0) {
          row.readStatus = 1
        }
      })
    },
    // 选中实例
    handleToggleSelection (rows) {
      if (rows) {
        rows.forEach((row) => {
          this.$refs.table.toggleRowSelection(row)
        })
      } else {
        this.$refs.table.clearSelection()
      }
    },
    handleTableSelectionChange (selection) {
      // 单行或多行选择回调
      this.table.selectList = selection
      this.btnDisabledStatus = this.table.selectList.length === 0
      this.checkAll = this.table.selectList.length > 0
    },
    // ************ 序列号
    handleIndexMethod (index) {
      return (
        index +
        1 +
        ((this.page.currentPage || 1) - 1) *
        (this.page.pageSize || 10)
      )
    },
    // ************ 点击列过滤
    handleFiltersMethod (value, row, column) {
      const columnNew = this.table.columns.filter(
        ele => ele.prop === column.property
      )[0]
      if (typeof columnNew.filtersMethod === 'function') {
        return columnNew.filtersMethod(value, row, columnNew)
      } else {
        return row[columnNew.prop] === value
      }
    },
    handleColumnFilters (column) {
      if (column.filter !== true) { return undefined }
      // if (this.validatenull(column.dicFilters)) {
      //   const list = [];
      //   (this.crud.DIC[column.prop] || []).forEach((ele) => {
      //     const props = column.props || this.crud.tableOption.props || {}
      //     list.push({
      //       text: ele[props.label || 'label'],
      //       value: ele[props.value || 'value']
      //     })
      //   })
      //   return list
      // }
      // return column.dicFilters
    },
    handleDetail (row, column) {
      let result = row[column.prop]
      // 自定义格式化
      if (column.formatter && typeof column.formatter === 'function') {
        result = column.formatter(row, row[column.prop], result, column)
      }
      // if (!column.dic) {
      //   // result = findByValue(column.dic, result, column.props || option.props, ['cascader', 'tree'].includes(column.type))
      // }
      return result
    },
    formatNotificationType (row, column) {
      const value = row[column.prop]
      const findType = this.notificationTypes.find(n => n.value === value)
      return findType ? findType.label : '未知'
    },
    // 操作功能
    handleAddDlgShow () {
      this.addViewEditDlgShow = true
      this.addViewEditDlgType = 'add'
      // 此处设置默认值
      this.addViewEditForm = {}
    },
    handleAddViewEditDlgClose (row) {
      if (row) {
        row = {}
      }
      this.addViewEditDlgShow = false
      this.fetchTable(this.page)
    },
    handleRowViewDlgShow (row, index) {
      // 显示弹出框
      this.addViewEditDlgShow = true
      this.addViewEditDlgType = 'view'
      this.addViewEditForm = row
      // 获取对应的数据
      this.fetchRowByRowId(row)
    },
    handleRowEditDlgShow (row, index) {
      // 显示弹出框
      this.addViewEditDlgShow = true
      this.addViewEditDlgType = 'edit'
      this.addViewEditForm = row
      // 获取对应的数据
      this.fetchRowByRowId(row)
    },
    fetchRowByRowId (row) {
      const idParams = { id: row.id }
      this.$store.dispatch(getSysNotificationByIdAction, idParams).then((res) => {
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
    handleAddViewEditDlgSave () {
      this.$refs.addViewEditForm.validate((valid) => {
        if (valid) {
          if (this.addViewEditDlgType === 'add') {
            this.handleRowSave()
          } else if (this.addViewEditDlgType === 'edit') {
            this.handleRowUpdate()
          }
        }
      })
    },
    handleAddViewEditDlgClear () {
      this.$refs.addViewEditForm.resetFields()
      this.$emit('input', this.addViewEditForm)
    },
    handleRowSave () {
      this.addViewEditFormSubmitDisable = true
      this.addViewEditForm.createBy = this.userInfo.id
      this.$store.dispatch(addSysNotificationAction, this.addViewEditForm).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          this.handleAddViewEditDlgClose(this.addViewEditForm)
          this.$message({
            type: 'success',
            message: '保存操作成功!'
          })
        } else {
          this.$message({
            type: 'error',
            message: message || '保存数据失败,请稍后再试!'
          })
        }
      }).catch(() => {
        this.$message({
          type: 'error',
          message: '保存数据失败,请稍后再试!'
        })
      }).finally(() => {
        this.addViewEditFormSubmitDisable = false
      })
    },
    handleRowUpdate () {
      this.addViewEditFormSubmitDisable = true
      this.addViewEditForm.updateBy = this.userInfo.id
      this.$store.dispatch(updateSysNotificationAction, this.addViewEditForm).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          this.handleAddViewEditDlgClose(this.addViewEditForm)
          this.$message({
            type: 'success',
            message: '编辑操作成功!'
          })
        } else {
          this.$message({
            type: 'error',
            message: message || '编辑数据失败,请稍后再试!'
          })
        }
      }).catch(() => {
        this.$message({
          type: 'error',
          message: '编辑数据异常,请稍后再试!'
        })
      }).finally(() => {
        this.addViewEditFormSubmitDisable = false
      })
    },
    handleRowDeleteShow (row) {
      this.$confirm('确定将选择数据删除?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.table.loading = true
          const idParams = { id: row.id }
          return this.$store.dispatch(readNotificationAction, idParams)
        })
        .then((res) => {
          this.table.loading = false
          const resCode = res.code
          const message = res.message
          if (resCode === 0) {
            this.handleAddViewEditDlgClose(this.addViewEditForm)
            this.$message({
              type: 'success',
              message: '删除操作成功!'
            })
          } else {
            this.table.loading = false
            this.$message({
              type: 'error',
              message: message || '删除数据失败,请稍后再试!'
            })
          }
        }).catch(() => {
          this.table.loading = false
        })
    },
    // -------------- 分页操作
    handlePageSizeChange (value) {
      // 页大小回调,10,20
      this.page.currentPage = 1
      this.page.pageSize = value
      this.fetchTable(this.page)
    },
    handlePageCurrentChange (value) {
      // 页码回调
      this.page.currentPage = value
      this.fetchTable(this.page)
    }
  },
  head () {
    return {
      title: '消息通知 | 个人中心',
      meta: [
        { hid: 'description', name: 'description', content: '消息通知,个人中心' }
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
  .ant-list-empty-text {
    color: rgba(0,0,0,.45);
    font-size: 14px;
    padding: 16px;
    text-align: center;
  }
  .page {
    .left {
      float: left;
      height: 30px;
      margin-top: 15px;
      margin-bottom: 10px;
      padding-left: 25px;
      /*padding: 10px 20px;*/
    }
    .right {
      float: right;
      height: 25px;
      margin-top: 15px;
      margin-bottom: 10px;
      /*padding: 10px 20px;*/
    }
  }
</style>
