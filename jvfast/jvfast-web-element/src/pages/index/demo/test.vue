<template>
  <el-card-container>
    <!--    数据表表组件-->
    <div class="avue-crud">
      <!--  搜索框部分-->
      <el-collapse-transition>
        <div v-show="searchShow" class="avue-crud__search">
          <!-- 表单组件-->
          <div class="avue-form" style="width: 100%">
            <el-form
              ref="searchForm"
              status-icon
              :model="searchForm"
              label-suffix=":"
              label-position="left"
              label-width="90px"
              :rules="searchFormRules"
              @submit.native.prevent
            >
              <el-row :span="24">
                <el-group>
                  <div class="avue-form__group">
                    <!-- 各个搜索组件-->
                    <el-col
                      :xs="24"
                      :sm="12"
                      :md="8"
                      :lg="8"
                      :offset="0"
                      class="avue-form__row"
                      style="padding-left: 10px; padding-right: 10px;"
                    >
                      <el-form-item
                        label="测试标题"
                        prop="title"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入对应的测试标题
                          </div>
                          <el-input
                            v-model="searchForm.title"
                            type="text"
                            :minlength="3"
                            :maxlength="120"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            placeholder="请输入对应的测试标题"
                          />
                        </el-tooltip>
                      </el-form-item>
                    </el-col>
                    <el-col
                      :xs="24"
                      :sm="12"
                      :md="8"
                      :lg="8"
                      :offset="0"
                      class="avue-form__row"
                      style="padding-left: 10px; padding-right: 10px;"
                    >
                      <el-form-item
                        label="测试内容"
                        prop="content"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入对应的测试内容
                          </div>
                          <el-input
                            v-model="searchForm.content"
                            type="text"
                            :minlength="3"
                            :maxlength="120"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            placeholder="请输入对应的测试内容"
                          />
                        </el-tooltip>
                      </el-form-item>
                    </el-col>
                    <el-col :span="11" class="avue-form__menu avue-form__menu--center" style="padding: 0px;">
                      <el-form-item label-width="0px">
                        <el-button
                          type="primary"
                          icon="el-icon-search"
                          :loading="searchBtnDisabled"
                          @click="handleSearchFormSubmit"
                        >
                          搜索
                        </el-button>
                        <el-button
                          icon="el-icon-delete"
                          :loading="searchBtnDisabled"
                          @click="handleSearchFormClear"
                        >
                          清空
                        </el-button>
                      </el-form-item>
                    </el-col>
                  </div>
                </el-group>
              </el-row>
            </el-form>
          </div>
        </div>
      </el-collapse-transition>
      <!-- 表格功能列,过滤，新增按钮 -->
      <div class="avue-crud__menu">
        <div class="avue-crud__left">
          <div class="avue-date" style="display:inline-block;margin-right:20px;">
            <el-date-picker
              v-model="dateRange"
              type="daterange"
              value-format="yyyy-MM-dd"
              align="right"
              unlink-panels
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :picker-options="datePickerOptions"
              @change="changeDateRange"
            />
          </div>
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
          <el-tooltip
            effect="dark"
            content="搜索"
            placement="top"
          >
            <el-button
              icon="el-icon-search"
              circle
              @click="handleSearchShow"
            />
          </el-tooltip>
        </div>
        <div class="avue-crud__right">
          <el-button
            type="primary"
            icon="el-icon-plus"
            @click="handleAddDlgShow"
          >
            新增
          </el-button>
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
      <!-- 表格部分-->
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
                <span v-if="['img','upload'].includes(column.type)">
                  <div class="avue-crud__img">
                    <el-image
                      :src="scope.row[column.prop]"
                      :preview-src-list="[scope.row[column.prop]]"
                    />
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
        <el-table-column
          class="avue-crud__btn"
          fixed="right"
          label="操作"
          align="center"
          header-align="center"
          :width="$device.isMobile? 100:240"
        >
          <template slot-scope="scope">
            <el-button
              type="text"
              icon="el-icon-view"
              @click.stop="handleRowViewDlgShow(scope.row,scope.$index)"
            >
              详情
            </el-button>
            <el-button
              type="text"
              icon="el-icon-edit"
              @click.stop="handleRowEditDlgShow(scope.row,scope.$index)"
            >
              修改
            </el-button>
            <el-button
              type="text"
              icon="el-icon-delete"
              @click.stop="handleRowDeleteShow(scope.row,scope.$index)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 删除和分页操作部分 -->
      <el-row :span="24">
        <el-col
          :sm="1"
          :xs="1"
          :md="1"
          :offset="0"
          style="padding-left: 10px;padding-right: 14px;text-align: center;padding-top: 20px"
        >
          <div class="cell">
            <el-button
              plain
              size="small"
              icon="el-icon-delete"
              :disabled="deleteAllBtnDisabled"
              @click="handleBatchDeleteRow"
            >
              删除
            </el-button>
          </div>
        </el-col>
        <el-col
          :sm="24"
          :xs="24"
          :md="23"
          :offset="0"
          style="text-align: right"
        >
          <el-pagination
            ref="tablePage"
            class="avue-crud__pagination"
            :small="$device.isMobile"
            :hide-on-single-page="false"
            :pager-count="page.pagerCount"
            :current-page.sync="page.currentPage"
            :background="page.background"
            :page-size="page.pageSize"
            :page-sizes="page.pageSizes"
            layout="total, sizes, prev, pager, next, jumper"
            :total="page.total"
            @size-change="handlePageSizeChange"
            @current-change="handlePageCurrentChange"
          />
        </el-col>
      </el-row>
    </div>
    <!--   新增对话框,查看对话框,编辑对话框部分 -->
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
      :size="$device.isMobile? '100%':'80%'"
      :width="$device.isMobile? '100%':'80%'"
      @close="handleAddViewEditDlgClose"
    >
      <div slot="title" class="avue-crud__dialog__header">
        <span class="el-dialog__title">测试表(无用){{ addViewEditDlgType==='add'? '新增':(addViewEditDlgType==='edit'? '编辑': '查看') }}</span>
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
              label-width="90px"
              :rules="addViewEditFormRules"
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
                        label="测试标题"
                        prop="title"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入对应的测试标题
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
                            placeholder="请输入对应的测试标题"
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
                        label="测试内容"
                        prop="content"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入对应的测试内容
                          </div>
                          <el-input
                            v-model="addViewEditForm.content"
                            type="text"
                            :minlength="3"
                            :maxlength="120"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            :disabled="addViewEditDlgType==='view'"
                            placeholder="请输入对应的测试内容"
                          />
                        </el-tooltip>
                      </el-form-item>
                    </el-col>
                    <el-col
                      v-if="addViewEditDlgType==='view'"
                      :sm="12"
                      :xs="24"
                      :md="24"
                      :offset="0"
                      class="avue-form__row"
                      style="padding-left: 10px; padding-right: 10px;"
                    >
                      <el-form-item
                        label="更新时间"
                        prop="updateTime"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入最后更新时间
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
                            placeholder="请输入最后更新时间"
                          />
                        </el-tooltip>
                      </el-form-item>
                    </el-col>
                    <el-col v-if="addViewEditDlgType!=='view'" :span="24" class="avue-form__menu avue-form__menu--right">
                      <el-form-item label-width="0px">
                        <el-button
                          type="primary"
                          icon="el-icon-check"
                          :loading="addViewEditFormSubmitDisable"
                          @click="handleAddViewEditDlgSave"
                        >
                          保存
                        </el-button>
                        <el-button
                          icon="el-icon-delete"
                          :loading="addViewEditFormSubmitDisable"
                          @click="handleAddViewEditDlgClear"
                        >
                          清空
                        </el-button>
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
  </el-card-container>
</template>

<script>
import { mapGetters } from 'vuex'

const addDemoTestAction = 'demo/test/addDemoTest'
const updateDemoTestAction = 'demo/test/updateDemoTest'
const removeDemoTestAction = 'demo/test/removeDemoTest'
const removeDemoTestsAction = 'demo/test/removeDemoTests'
const getDemoTestByIdAction = 'demo/test/getDemoTestById'
const getDemoTestPageListAction = 'demo/test/pageDemoTest'

export default {
  name: 'DemoTest',
  data () {
    return {
      // 搜索部分
      searchShow: true,
      searchForm: {
        title: '',
        content: ''
      },
      searchFormRules: {
        title: [{ required: true, message: '请输入测试标题', trigger: 'blur' }],
        content: [{ required: true, message: '请输入测试内容', trigger: 'blur' }]
      },
      searchBtnDisabled: false,
      // 顶部菜单栏部分
      datePickerOptions: {
        shortcuts: [{
          text: '今天',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一周',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一个月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近三个月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }]
      },
      dateRange: '',
      deleteAllBtnDisabled: true,
      // -----表格部分开始
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
        height: 'auto',
        maxHeight: 'auto',
        width: '100%',
        border: true,
        data: [],
        columns: [
          {
            prop: 'id',
            label: '记录唯一标识id',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '180px',
            fixed: false
          },
          {
            prop: 'title',
            label: '测试标题',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '180px',
            fixed: false
          },
          {
            prop: 'content',
            label: '测试内容',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '180px',
            fixed: false
          }
        ],
        selectList: [],
        clickRow: {}
      },
      query: {},
      page: {
        total: 0, // 总页数
        pagerCount: 7, // 超过多少条隐藏
        pageNum: 1, // 当前页数
        pageSize: 10, // 每页显示多少条
        pageSizes: [10, 20, 30, 40, 50, 100],
        background: true // 背景颜色
      },
      // 表格弹出对话框表单
      addViewEditDlgShow: false,
      addViewEditDlgType: 'add',
      addViewEditForm: {
        title: '',
        content: ''
      },
      addViewEditFormRules: {
        title: [{ required: true, message: '请输入测试标题', trigger: 'blur' }],
        content: [{ required: true, message: '请输入测试内容', trigger: 'blur' }]
      },
      addViewEditFormSubmitDisable: false
      // -----表格部分结束
    }
  },
  computed: {
    ...mapGetters(['permissions', 'website', 'userInfo']),
    selectRowLen () {
      return this.table.selectList ? this.table.selectList.length : 0
    }
  },
  watch: {
    'table.selectList': {
      handler (value) {
        this.deleteAllBtnDisabled = !(value.length > 0)
      }
    },
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
    // --------------搜索部分
    handleSearchFormSubmit () {
      this.$refs.searchForm.validate((valid) => {
        if (valid) {
          this.searchBtnDisabled = true
          this.query = this.searchForm
          this.page.currentPage = 1
          this.fetchTable(this.page, this.query)
        }
      })
    },
    handleSearchFormClear () {
      this.$refs.searchForm.resetFields()
      this.searchForm = {}
      this.query = {}
      this.fetchTable(this.page)
    },
    // --------------工具条
    handleBatchDeleteRow () {
      if (this.table.selectList.length === 0) {
        this.$message.warning('请至少选择一条数据进行操作!')
        return
      }
      const ids = []
      this.table.selectList.forEach((e) => {
        ids.push(e.id)
      })
      this.$confirm('确定将当前选择的数据全部删除吗?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.table.loading = true
          const idParams = { ids }
          this.$store.dispatch(removeDemoTestsAction, idParams).then((res) => {
            const resCode = res.code
            const message = res.message
            if (resCode === 0) {
              this.$message({
                type: 'success',
                message: '批量删除数据成功!'
              })
              this.fetchTable(this.page)
              this.handleToggleSelection()
            } else {
              console.log('批量删除数据返回失败: ', message)
              this.$message({
                type: 'error',
                message: '批量删除数据失败,请稍后再试!'
              })
            }
          }).catch((err) => {
            console.log('批量删除数据请求异常: ', err)
            this.$message({
              type: 'error',
              message: '批量删除数据请求异常,请稍后再试!'
            })
          }).finally(() => {
            this.table.loading = false
          })
        }).catch(() => {
          // 取消操作
        })
    },
    changeDateRange (value) {
      if (value && value.length === 2) {
        this.query = {
          projectId: this.searchForm.projectId,
          startDate: value[0],
          endDate: value[1]
        }
      } else {
        this.query = {
          projectId: this.searchForm.projectId
        }
      }
      this.page.currentPage = 1
      this.fetchTable(this.page, this.query)
    },
    handleRefreshTable () {
      this.fetchTable(this.page)
    },
    handleSearchShow () {
      this.searchShow = !this.searchShow
    },
    // --------------表格顶部提示部分,点击清空按钮
    handleSelectClear () {
      this.table.selectList = []
      this.$refs.table.clearSelection()
    },
    // -------------- 表格列表
    fetchTable (page, params = {}) {
      this.table.loading = true
      let queryData = {
        pageNo: page.currentPage,
        pageSize: page.pageSize
      }
      queryData = Object.assign(queryData, Object.assign(params, this.query))
      this.$store.dispatch(getDemoTestPageListAction, queryData).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          const data = res.data
          this.page.total = parseInt(data.total)
          this.table.data = data.records
          this.handleSelectClear()
        } else {
          console.log('查询列表数据返回失败: ', message)
          this.$message({
            type: 'error',
            message: '列表数据失败,请稍后再试!'
          })
        }
      }).catch((err) => {
        console.log('查询列表数据请求异常: ', err)
        this.$message({
          type: 'error',
          message: '查询列表数据请求异常,请稍后再试!'
        })
      }).finally(() => {
        this.searchBtnDisabled = false
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
      // return {
      //   color: 'green',
      //   fontWeight: 'bold',
      //   fontSize: '20'
      // }
      return {}
    },
    handleTableRowClick (row, event, column) {
      this.table.clickRow = row
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
    // ************------------------操作功能栏
    handleAddDlgShow () {
      this.addViewEditDlgShow = true
      this.addViewEditDlgType = 'add'
      // 此处可以设置新增对话框的默认值
      this.addViewEditForm = {
      }
    },
    handleRowViewDlgShow (row, index) {
      // 显示弹出框
      this.addViewEditDlgShow = true
      this.addViewEditDlgType = 'view'
      this.addViewEditForm = this.$deepclone(row)
      // 获取对应的数据
      this.fetchRowByRowId(row)
    },
    handleRowEditDlgShow (row, index) {
      // 显示弹出框
      this.addViewEditDlgShow = true
      this.addViewEditDlgType = 'edit'
      this.addViewEditForm = this.$deepclone(row)
      // 获取对应的数据
      this.fetchRowByRowId(row)
    },
    fetchRowByRowId (row) {
      const idParams = { id: row.id }
      this.$store.dispatch(getDemoTestByIdAction, idParams).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          this.addViewEditForm = Object.assign(this.addViewEditForm, res.data)
        } else {
          console.log('查询当前数据返回失败: ', message)
          this.$message({
            type: 'error',
            message: '查询数据失败,请稍后再试!'
          })
        }
      }).catch((err) => {
        console.log('查询当前数据请求异常: ', err)
        this.$message({
          type: 'error',
          message: '查询当前数据请求异常,请稍后再试!'
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
      this.addViewEditForm = {}
    },
    handleAddViewEditDlgClose (row) {
      if (row) {
        row = {}
      }
      this.addViewEditDlgShow = false
      this.fetchTable(this.page)
    },
    handleRowSave () {
      this.addViewEditFormSubmitDisable = true
      const addData = {
        ...this.addViewEditForm,
        createBy: this.userInfo.id
      }
      this.$store.dispatch(addDemoTestAction, addData).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          this.handleAddViewEditDlgClose(this.addViewEditForm)
          this.$message({
            type: 'success',
            message: '添加数据成功!'
          })
        } else {
          console.log('添加数据返回失败: ', message)
          this.$message({
            type: 'error',
            message: '添加数据失败,请稍后再试!'
          })
        }
      }).catch((err) => {
        console.log('添加数据请求异常: ', err)
        this.$message({
          type: 'error',
          message: '添加数据请求异常,请稍后再试!'
        })
      }).finally(() => {
        this.addViewEditFormSubmitDisable = false
      })
    },
    handleRowUpdate () {
      this.addViewEditFormSubmitDisable = true
      const updateData = {
        ...this.addViewEditForm,
        updateBy: this.userInfo.id
      }
      this.$store.dispatch(updateDemoTestAction, updateData).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          this.handleAddViewEditDlgClose(this.addViewEditForm)
          this.$message({
            type: 'success',
            message: '修改数据成功!'
          })
        } else {
          console.log('修改数据返回失败: ', message)
          this.$message({
            type: 'error',
            message: '修改数据失败,请稍后再试!'
          })
        }
      }).catch((err) => {
        console.log('修改数据请求异常: ', err)
        this.$message({
          type: 'error',
          message: '修改数据请求异常,请稍后再试!'
        })
      }).finally(() => {
        this.addViewEditFormSubmitDisable = false
      })
    },
    handleRowDeleteShow (row, index) {
      this.$confirm('确定将当前选择的数据删除吗?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.table.loading = true
          const idParams = { id: row.id }
          this.$store.dispatch(removeDemoTestAction, idParams).then((res) => {
            const resCode = res.code
            const message = res.message
            if (resCode === 0) {
              this.handleAddViewEditDlgClose(this.addViewEditForm)
              this.$message({
                type: 'success',
                message: '删除当前选择数据成功!'
              })
            } else {
              console.log('删除当前选择数据返回失败: ', message)
              this.$message({
                type: 'error',
                message: '删除当前选择数据失败,请稍后再试!'
              })
            }
          }).catch((err) => {
            console.log('删除当前数据请求异常: ', err)
            this.$message({
              type: 'error',
              message: '删除当前数据请求异常,请稍后再试!'
            })
          }).finally(() => {
            this.table.loading = false
          })
        }).catch(() => {
          // 取消操作
        })
    },
    // -------------- 分页功能操作
    handlePageSizeChange (value) {
      // 页大小回调,10,20
      // this.page.currentPage = 1
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
      title: 'DemoTest管理 | ' + this.website.name,
      meta: [
        { hid: 'keywords', name: 'keywords', content: this.website.keyword || '' },
        { hid: 'description', name: 'description', content: 'DemoTest管理' }
      ]
    }
  }
}
</script>
<style scoped>
</style>
