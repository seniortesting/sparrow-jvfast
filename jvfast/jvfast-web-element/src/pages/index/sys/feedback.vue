<template>
  <el-card-container>
    <!--    列表组件-->
    <div class="avue-crud">
      <!--      搜索框部分-->
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
              label-width="150px"
              :rules="searchFormRules"
              @submit.native.prevent
            >
              <el-row :span="24">
                <el-group>
                  <div class="avue-form__group">
                    <!-- 各个组件-->
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
                        label="反馈账号"
                        prop="userName"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入对应的反馈账号
                          </div>
                          <el-input
                            v-model="searchForm.userName"
                            type="text"
                            :minlength="3"
                            :maxlength="120"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            placeholder="请输入对应的反馈账号"
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
      <!-- 表格功能列 -->
      <div class="avue-crud__menu">
        <div class="avue-crud__left">
          <el-button
            type="primary"
            icon="el-icon-download"
            @click="handleExportDlgShow"
          >
            导出
          </el-button>
          <el-button
            type="danger"
            icon="el-icon-delete"
            @click="handleBatchDeleteRow"
          >
            删除
          </el-button>
        </div>
        <div class="avue-crud__right">
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
                <span v-if="['img','upload'].includes(column.type)">
                  <div class="avue-crud__img">
                    <el-image
                      :src="scope.row[column.prop]"
                      :preview-src-list="[scope.row[column.prop]]"
                    />
                  </div>
                </span>
                <span v-else-if="['feedbackStatus'].includes(column.type)">
                  <el-tooltip content="意见反馈的处理状态" placement="top">
                    <el-tag :type="scope.row[column.prop]===1000? 'info':(scope.row[column.prop]===1001?'warning': 'success')">
                      {{ formatStatus(scope.row[column.prop]) }}
                    </el-tag>
                  </el-tooltip>
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
              icon="el-icon-delete"
              @click.stop="handleRowDeleteShow(scope.row,scope.$index)"
            >
              删除
            </el-button>
            <el-button
              v-if="scope.row.feedbackStatus!==1002"
              type="text"
              icon="el-icon-edit"
              @click.stop="handleRowEditDlgShow(scope.row,scope.$index)"
            >
              处理反馈
            </el-button>
          </template>
        </el-table-column>
      </el-table>
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
        <span class="el-dialog__title">意见反馈{{ addViewEditDlgType==='add'? '新增':(addViewEditDlgType==='edit'? '编辑': '查看') }}</span>
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
                      :xs="24"
                      :md="24"
                      :offset="0"
                      class="avue-form__row"
                      style="padding-left: 10px; padding-right: 10px;"
                    >
                      <el-form-item
                        label="反馈处理结果"
                        label-width="180px"
                        prop="feedbackStatus"
                        class="avue-form__item--left"
                      >
                        <el-select
                          ref="selectfeedbackStatus"
                          v-model="addViewEditForm.feedbackStatus"
                          class="avue-select"
                          :multiple="false"
                          :multiple-limit="1"
                          :filterable="false"
                          :allow-create="false"
                          :default-first-option="true"
                          :remote="false"
                          :readonly="false"
                          :clearable="false"
                          placeholder="请选择一个反馈的处理结果"
                          :disabled="['view'].includes(addViewEditDlgType)"
                        >
                          <el-option
                            v-for="item in feedbackStatusDicData.filter(f=>f.value!==1000)"
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
                        label="处理结果备注"
                        prop="reason"
                        label-width="180px"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入处理结果备注
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
                            placeholder="请输入处理结果备注"
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
                        label="反馈账号"
                        label-width="180px"
                        prop="userName"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入反馈账号
                          </div>
                          <el-input
                            v-model="addViewEditForm.userName"
                            type="text"
                            :minlength="3"
                            :maxlength="120"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            :disabled="['view', 'edit'].includes(addViewEditDlgType)"
                            placeholder="请输入反馈账号"
                          />
                        </el-tooltip>
                      </el-form-item>
                    </el-col>
                    <el-col
                      v-if="addViewEditForm.title"
                      :sm="12"
                      :xs="24"
                      :md="24"
                      :offset="0"
                      class="avue-form__row"
                      style="padding-left: 10px; padding-right: 10px;"
                    >
                      <el-form-item
                        label="反馈标题"
                        prop="title"
                        label-width="180px"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入对应的反馈标题
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
                            placeholder="请输入对应的反馈标题"
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
                        label="反馈详情"
                        prop="detail"
                        label-width="180px"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入反馈详情
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
                            :disabled="['view', 'edit'].includes(addViewEditDlgType)"
                            placeholder="请输入反馈详情"
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
                        label-width="180px"
                        prop="screenshot"
                        class="avue-form__item--left"
                      >
                        <el-image v-model="addViewEditForm.screenshot" :src="addViewEditForm.screenshot" :preview-src-list="[addViewEditForm.screenshot]" />
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
                          保存处理结果
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

const addSysFeedbackAction = 'sys/feedback/addSysFeedback'
const updateSysFeedbackAction = 'sys/feedback/updateSysFeedback'
const removeSysFeedbackAction = 'sys/feedback/removeSysFeedback'
const removeSysFeedbacksAction = 'sys/feedback/removeSysFeedbacks'
const getSysFeedbackByIdAction = 'sys/feedback/getSysFeedbackById'
const getSysFeedbackPageListAction = 'sys/feedback/pageSysFeedback'

export default {
  name: 'SysFeedback',
  data () {
    return {
      feedbackStatusDicData: [
        { label: '等待处理', value: 1000 },
        { label: '已解决', value: 1001 },
        { label: '关闭', value: 1002 }
      ],
      // 搜索部分
      searchShow: true,
      searchForm: {
        userName: ''
      },
      searchFormRules: {},
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
        height: undefined,
        maxHeight: 'auto',
        width: '100%',
        border: true,
        data: [],
        columns: [
          {
            prop: 'id',
            label: '反馈ID',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '180px',
            fixed: false
          },
          {
            prop: 'feedbackStatus',
            label: '反馈状态',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '120px',
            fixed: false,
            type: 'feedbackStatus'
          },
          {
            prop: 'userName',
            label: '反馈账号',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '130',
            fixed: false
          },
          {
            prop: 'detail',
            label: '反馈详情',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false
          },
          {
            prop: 'screenshot',
            label: '截屏',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false,
            type: 'img'
          },
          {
            prop: 'reason',
            label: '处理结果/意见',
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
        pagerCount: 7, // 超过多少条隐藏
        pageNum: 1, // 当前页数
        pageSize: 10, // 每页显示多少条
        pageSizes: [10, 20, 30, 40, 50, 100],
        background: true // 背景颜色
      },
      // 表格弹出对话框
      addViewEditDlgShow: false,
      addViewEditDlgType: 'add',
      addViewEditForm: {
        userName: '',
        feedbackStatus: 1001,
        title: '',
        detail: '',
        screenshot: '',
        reason: ''
      },
      addViewEditFormRules: {
        feedbackStatus: [{ required: true, message: '请选择反馈状态', trigger: 'blur' }],
        reason: [{ required: false, message: '请输入处理结果或意见', trigger: 'blur' }],
        userName: [{ required: true, message: '请输入反馈账号', trigger: 'blur' }],
        title: [{ required: true, message: '请输入反馈标题', trigger: 'blur' }],
        detail: [{ required: true, message: '请输入反馈详情', trigger: 'blur' }],
        screenshot: [{ required: true, message: '请上传截图', trigger: 'blur' }]
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
          this.query = this.searchForm
          this.page.currentPage = 1
          this.fetchTable(this.page, this.query)
        }
      })
    },
    handleSearchFormClear () {
      this.$refs.searchForm.resetFields()
      this.query = {}
      this.fetchTable(this.page)
    },
    // --------------工具条
    handleBatchDeleteRow () {
      if (this.table.selectList.length === 0) {
        this.$message.warning('请选择至少一条数据')
        return
      }
      const ids = []
      this.table.selectList.forEach((e) => {
        ids.push(e.id)
      })
      this.$confirm('确定将选择数据删除?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.table.loading = true
          const idParams = { ids }
          return this.$store.dispatch(removeSysFeedbacksAction, idParams)
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
            this.handleToggleSelection()
          } else {
            this.table.loading = false
            this.$message({
              type: 'error',
              message: message || '批量删除数据失败,请稍后再试!'
            })
          }
        }).catch(() => {
          this.table.loading = false
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
    // --------------表格顶部提示部分
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
      this.$store.dispatch(getSysFeedbackPageListAction, queryData).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          const data = res.data
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
    formatStatus (value) {
      const findStatus = this.feedbackStatusDicData.find(f => f.value === value)
      return findStatus ? findStatus.label : '未知状态'
    },
    // 操作功能
    handleExportDlgShow () {
      const data = this.table.data
      const title = '反馈数据表'
      this.$export.excel({ data, title })
    },
    handleAddDlgShow () {
      this.addViewEditDlgShow = true
      this.addViewEditDlgType = 'add'
      // 此处设置默认值
      this.addViewEditForm = {
        feedbackStatus: 1001
      }
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
      this.$store.dispatch(getSysFeedbackByIdAction, idParams).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          this.addViewEditForm = Object.assign(this.addViewEditForm, res.data)
          // 排除正在处理的
          if (this.addViewEditForm.feedbackStatus === 1000) {
            this.addViewEditForm.feedbackStatus = 1001
          }
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
      this.$store.dispatch(addSysFeedbackAction, this.addViewEditForm).then((res) => {
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
      const updateData = {
        id: this.addViewEditForm.id,
        userId: this.userInfo.id,
        feedbackStatus: this.addViewEditForm.feedbackStatus,
        title: this.addViewEditForm.title,
        detail: this.addViewEditForm.detail,
        screenshot: [this.addViewEditForm.screenshot],
        reason: this.addViewEditForm.reason,
        updateBy: this.userInfo.id
      }
      this.$store.dispatch(updateSysFeedbackAction, updateData).then((res) => {
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
          return this.$store.dispatch(removeSysFeedbackAction, idParams)
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
      title: '意见反馈管理 | ' + this.website.name,
      meta: [
        { hid: 'description', name: 'description', content: '意见反馈管理' }
      ]
    }
  }
}
</script>

<style>
</style>
