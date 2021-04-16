<template>
  <el-card-container>
    <el-data-box :span="6" :data="jobData" />
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
                      :sm="24"
                      :md="12"
                      :span="12"
                      :offset="0"
                      class="avue-form__row"
                      style="padding-left: 10px; padding-right: 10px;"
                    >
                      <el-form-item
                        label="任务名称"
                        prop="jobName"
                        class="avue-form__item--right"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入定时任务名称
                          </div>
                          <el-input
                            v-model="searchForm.jobName"
                            type="text"
                            :minlength="3"
                            :maxlength="120"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            :disabled="addViewEditDlgType==='view'"
                            placeholder="请输入定时任务名称"
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
            icon="el-icon-plus"
            @click="handleRowAddDlgShow"
          >
            新增
          </el-button>
        </div>
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
                <span v-else-if="['url'].includes(column.type)">
                  <el-link
                    type="primary"
                    :href="scope.row[column.prop]"
                    :target="column.target || '_blank'"
                  >{{ scope.row[column.prop] }}</el-link>
                </span>
                <span v-else-if="['color'].includes(column.type)">
                  <i
                    class="avue-crud__color"
                    :style="{backgroundColor:scope.row[column.prop]}"
                  />
                </span>
                <span v-else-if="['array'].includes(column.type)">
                  {{ scope.row[column.prop].join(' | ') }}
                </span>
                <span v-else-if="['icon-select'].includes(column.type)">
                  <i
                    class="avue-crud__icon-select"
                    :class="scope.row[column.prop]"
                  />
                </span>
                <span v-else-if="['switch'].includes(column.type)">
                  <el-switch
                    v-model="scope.row[column.prop]"
                    disabled
                  />
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
          header-align="left"
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
              编辑
            </el-button>
            <el-button
              type="text"
              icon="el-icon-delete"
              @click.stop="handleRowDeleteDlgShow(scope.row,scope.$index)"
            >
              删除
            </el-button>
            <el-button
              type="text"
              icon="el-icon-video-play"
              @click.stop="jobStatusHandler(scope.row,scope.$index)"
            >
              {{ jobStatusText(scope.row) }}
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
      size="50%"
      width="50%"
      @close="handleAddViewEditDlgClose"
    >
      <div slot="title" class="avue-crud__dialog__header">
        <span class="el-dialog__title">定时任务{{ addViewEditDlgType==='add'? '新增':(addViewEditDlgType==='edit'? '编辑': '查看') }}</span>
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
              label-width="140px"
              :rules="addViewEditFormRules"
              @submit.native.prevent
            >
              <el-row :span="24">
                <el-group>
                  <div class="avue-form__group">
                    <el-col
                      :sm="12"
                      :xs="24"
                      :md="12"
                      :offset="0"
                      class="avue-form__row"
                      style="padding-left: 10px; padding-right: 10px;"
                    >
                      <el-form-item
                        label="定时任务名称"
                        prop="jobName"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入定时任务名称
                          </div>
                          <el-input
                            v-model="addViewEditForm.jobName"
                            type="text"
                            :minlength="3"
                            :maxlength="120"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            :disabled="addViewEditDlgType==='view'"
                            placeholder="请输入定时任务名称"
                          />
                        </el-tooltip>
                      </el-form-item>
                    </el-col>
                    <el-col
                      :sm="12"
                      :xs="24"
                      :md="12"
                      :offset="0"
                      class="avue-form__row"
                      style="padding-left: 10px; padding-right: 10px;"
                    >
                      <el-form-item
                        label="执行类"
                        label-width="80px"
                        prop="jobClassName"
                        class="avue-form__item--left"
                      >
                        <el-select
                          ref="selectJobClassNameSearch"
                          v-model="addViewEditForm.jobClassName"
                          class="avue-select"
                          :multiple="false"
                          :multiple-limit="1"
                          :filterable="false"
                          :allow-create="false"
                          :default-first-option="true"
                          :remote="false"
                          :readonly="false"
                          :clearable="true"
                          placeholder="选择执行的类"
                          :disabled="addViewEditDlgType==='view'"
                        >
                          <el-option
                            v-for="item in jobClassNames"
                            :key="item.key"
                            :label="item.value"
                            :value="item.key"
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
                        label="正则表达式"
                        prop="cronExpression"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入对应的正则表达式
                          </div>
                          <el-input
                            v-model="addViewEditForm.cronExpression"
                            type="text"
                            :minlength="3"
                            :maxlength="120"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            :disabled="addViewEditDlgType==='view'"
                            placeholder="请输入对应的正则表达式"
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
                        label="执行参数"
                        prop="jobData"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入执行参数
                          </div>
                          <el-input
                            v-model="addViewEditForm.jobData"
                            type="textarea"
                            :minlength="3"
                            :maxlength="120"
                            :rows="5"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            :disabled="addViewEditDlgType==='view'"
                            placeholder="输入该任务的相关运行参数,每个参数一行，格式为： 参数名=运行参数值，例如：name=小明"
                          />
                        </el-tooltip>
                      </el-form-item>
                    </el-col>
                    <el-col
                      :sm="12"
                      :xs="24"
                      :md="12"
                      :offset="0"
                      class="avue-form__row"
                      style="padding-left: 10px; padding-right: 10px;"
                    >
                      <el-form-item
                        label="失败是否自动恢复执行"
                        label-width="190px"
                        prop="recovery"
                        class="avue-form__item--left"
                      >
                        <el-switch
                          v-model="addViewEditForm.recovery"
                          active-text="是"
                          inactive-text="否"
                          :disabled="addViewEditDlgType==='view'"
                          :readonly="addViewEditDlgType==='view'"
                        />
                      </el-form-item>
                    </el-col>
                    <el-col
                      :sm="12"
                      :xs="24"
                      :md="12"
                      :offset="0"
                      class="avue-form__row"
                      style="padding-left: 10px; padding-right: 10px;"
                    >
                      <el-form-item
                        label="创建后立即执行"
                        prop="isPause"
                        class="avue-form__item--left"
                      >
                        <el-switch
                          v-model="addViewEditForm.isPause"
                          active-text="是"
                          inactive-text="否"
                          :disabled="addViewEditDlgType==='view'"
                          :readonly="addViewEditDlgType==='view'"
                        />
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
                        label="任务描述"
                        prop="description"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入任务描述
                          </div>
                          <el-input
                            v-model="addViewEditForm.description"
                            type="textarea"
                            :minlength="3"
                            :maxlength="120"
                            :rows="5"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            :disabled="addViewEditDlgType==='view'"
                            placeholder="请输入任务描述"
                          />
                        </el-tooltip>
                      </el-form-item>
                    </el-col>
                    <!-- 各个组件-->
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
import ElDataBox from '@/components/vue-element-ui/el-data-box'

const listImplementJobsAction = 'monitor/job/listImplementJobs'
const pageJobTasksAction = 'monitor/job/pageJobTasks'
const addJobAction = 'monitor/job/addJob'
const updateJobAction = 'monitor/job/updateJob'
const startJobAction = 'monitor/job/startJob'
const stopJobAction = 'monitor/job/stopJob'
const pauseJobAction = 'monitor/job/pauseJob'
// const resumeJobAction = 'monitor/job/resumeJob'
const deleteJobAction = 'monitor/job/deleteJob'

export default {
  name: 'Job',
  components: { ElDataBox },
  data () {
    return {
      jobData: [
        {
          title: '总任务',
          color: 'rgb(56, 161, 242)',
          icon: 'el-icon-view',
          count: 0
        },
        {
          title: '等待中任务',
          color: 'rgb(242,143,56)',
          icon: 'el-icon-video-pause',
          count: 0
        },
        {
          title: '进行中任务',
          color: 'rgb(70,252,3)',
          icon: 'el-icon-loading',
          count: 0
        },
        {
          title: '失败任务',
          color: 'rgb(252,3,3)',
          icon: 'el-icon-error',
          count: 0
        }
      ],
      jobClassNames: [],
      // 搜索部分
      searchShow: true,
      searchForm: {
        jobName: ''
      },
      searchFormRules: {},
      searchBtnDisabled: false,
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
            prop: 'jobName',
            label: '任务名称',
            display: true,
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '200',
            fixed: false
          },
          {
            prop: 'state',
            label: '执行状态',
            display: true,
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false
          },
          {
            prop: 'jobClassName',
            label: '执行类',
            display: true,
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false
          }, {
            prop: 'cronExpression',
            label: '正则表达式',
            display: true,
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false
          }, {
            prop: 'prevFire',
            label: '开始执行时间',
            display: true,
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false
          },
          {
            prop: 'nextFire',
            label: '下次执行时间',
            display: true,
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false
          },
          {
            prop: 'jobData',
            label: '执行参数',
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
        jobName: '',
        state: '',
        jobClassName: '',
        cronExpression: '',
        prevFire: '',
        nextFire: '',
        jobData: '',
        recovery: '',
        isPause: '',
        description: ''
      },
      addViewEditFormRules: {
        jobName: [{ required: true, message: '任务名称不能为空', trigger: 'blur' }],
        cronExpression: [{ required: true, message: '请输入定时任务执行的正则表达式', trigger: 'blur' }],
        jobClassName: [{ required: true, message: '请选择一个任务执行类', trigger: 'blur' }],
        jobData: [{ required: false, message: '输入该任务的相关运行参数,每个参数一行，格式为： 参数名=运行参数值，例如：name=小明', trigger: 'blur' }]
      },
      addViewEditFormSubmitDisable: false
      // 表设置，结束
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
    this.fetchTable(this.page)
    this.fetchImplementJobData()
  },
  methods: {
    jobStatusText (row) {
      let status = ''
      if (row.state === '失败' || row.state === '等待') {
        status = '启动'
      } else if (row.state === '阻塞' || row.state === '读取中') {
        status = '停止'
      } else if (row.state === '执行中') {
        status = '暂停'
      } else {
        status = '启动'
      }
      return status
    },
    jobStatusHandler (row) {
      if (row.state === '失败' || row.state === '等待') {
        this.$store.dispatch(startJobAction, { jobName: row.jobName }).then((res) => {
          const resCode = res.code
          const message = res.message
          if (resCode === 0) {
            this.fetchTable(this.page)
            this.$message({
              type: 'success',
              message: '启动任务操作完成!'
            })
          } else {
            this.loading = false
            this.$message({
              type: 'error',
              message: message || '启动任务失败,请稍后再试!'
            })
          }
        })
      } else if (row.state === '阻塞' || row.state === '读取中') {
        this.$store.dispatch(stopJobAction, { jobName: row.jobName }).then((res) => {
          const resCode = res.code
          const message = res.message
          if (resCode === 0) {
            this.fetchTable(this.page)
            this.$message({
              type: 'success',
              message: '停止任务操作完成!'
            })
          } else {
            this.loading = false
            this.$message({
              type: 'error',
              message: message || '停止任务失败,请稍后再试!'
            })
          }
        })
      } else if (row.state === '执行中') {
        this.$store.dispatch(pauseJobAction, { jobName: row.jobName }).then((res) => {
          const resCode = res.code
          const message = res.message
          if (resCode === 0) {
            this.fetchTable(this.page)
            this.$message({
              type: 'success',
              message: '暂停任务操作完成!'
            })
          } else {
            this.loading = false
            this.$message({
              type: 'error',
              message: message || '暂停任务失败,请稍后再试!'
            })
          }
        })
      } else {
        this.$store.dispatch(startJobAction, { jobName: row.jobName }).then((res) => {
          const resCode = res.code
          const message = res.message
          if (resCode === 0) {
            this.fetchTable(this.page)
            this.$message({
              type: 'success',
              message: '启动任务操作完成!'
            })
          } else {
            this.loading = false
            this.$message({
              type: 'error',
              message: message || '启动任务失败,请稍后再试!'
            })
          }
        })
      }
    },
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
      this.page.currentPage = 1
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
          return this.$store.dispatch(deleteJobAction, idParams)
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
    handleRefreshTable () {
      this.fetchTable(this.page)
    },
    handleSearchShow () {
      this.searchShow = !this.searchShow
    },
    // --------------表格提示部分
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
      this.$store.dispatch(pageJobTasksAction, queryData).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          const data = res.data
          this.page.total = parseInt(data.total)
          this.table.data = data.records
          this.handleSelectClear()
          this.filterJobInfo(this.table.data)
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
      const rowKey = row[this.table.rowKey] + '_' + Math.random()
      return rowKey
    },
    handleTableRowStyle ({ row, column, rowIndex }) {
      return {}
    },
    handleTableCellStyle ({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 2) {
        if (row.state === '失败' || row.state === '阻塞') {
          return {
            color: 'red',
            fontWeight: 'bold',
            fontSize: '20'
          }
        } else if (row.state === '等待' || row.state === '执行中') {
          return {
            color: 'rgba(255, 140, 0, 1)',
            fontWeight: 'bold',
            fontSize: '20'
          }
        } else if (row.state === '完成') {
          return {
            color: 'green',
            fontWeight: 'bold',
            fontSize: '20'
          }
        }
      }
      return {}
    },
    handleTableRowClick (row, event, column) {
      this.table.clickRow = row
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
    // 格式化输出每行数据
    handleDetail (row, column) {
      let result = row[column.prop]
      // 自定义格式化
      if (column.formatter && typeof column.formatter === 'function') {
        result = column.formatter(row, row[column.prop], result, column)
      }
      return result
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
    handleRowAddDlgShow () {
      this.addViewEditDlgShow = true
      this.addViewEditDlgType = 'add'
      this.$nextTick(() => {
        // 设置默认值
        if (this.jobClassNames.length > 0) {
          this.addViewEditForm.jobClassName = this.jobClassNames[0].key
        }
        this.addViewEditForm.cronExpression = '0 0 1 * * ?'
        this.addViewEditForm.isPause = false
        this.addViewEditForm.recovery = false
      })
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
    handleRowEditDlgShow (row, index) {
      // 显示弹出框
      this.addViewEditDlgShow = true
      this.addViewEditDlgType = 'edit'
      this.$nextTick(() => {
      })
      // 获取对应的数据
      this.handleFetchRowByRowId(row)
    },
    handleFetchRowByRowId (row) {
      let jobParams = ''
      Object.keys(row.jobData).forEach((key) => {
        const data = key + '=' + row.jobData[key]
        jobParams += data + '\n'
      })
      this.addViewEditForm = row
      this.addViewEditForm.jobData = jobParams
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
    },
    handleRowSave () {
      this.addViewEditFormSubmitDisable = true
      this.addViewEditForm.createBy = this.userInfo.id
      this.$store.dispatch(addJobAction, this.addViewEditForm).then((res) => {
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
      this.$store.dispatch(updateJobAction, this.addViewEditForm).then((res) => {
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
    handleRowDeleteDlgShow (row) {
      this.$confirm('确定将选择数据删除?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.table.loading = true
          const idParams = { jobName: row.jobName }
          return this.$store.dispatch(deleteJobAction, idParams)
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
    },
    // 表格设置结束
    fetchImplementJobData () {
      this.$store.dispatch(listImplementJobsAction).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          this.jobClassNames = res.data
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
    filterJobInfo (records) {
      this.jobData[0].count = 0
      this.jobData[1].count = 0
      this.jobData[2].count = 0
      this.jobData[3].count = 0
      this.jobData[0].count = records.length
      records.forEach((j) => {
        if (j.state === '失败') {
          this.jobData[3].count += 1
        } else if (j.state === '阻塞' || j.state === '读取中' || j.state === '等待') {
          this.jobData[1].count += 1
        } else if (j.state === '执行中') {
          this.jobData[2].count += 1
        }
      })
    }
  },
  head () {
    return {
      title: '定时任务管理 | ' + this.website.name,
      meta: [
        { hid: 'description', name: 'description', content: '定时任务管理' }
      ]
    }
  }
}
</script>

<style lang="scss" scoped>
  .card {
    margin-bottom: 20px;
  }
  .head-info {
    text-align: center;
    padding: 0 32px;
    span{
      color: rgba(0,0,0,.45);
      display: inline-block;
      font-size: 14px;
      line-height: 22px;
      margin-bottom: 4px;
    }
    p {
      color: rgba(0,0,0,.85);
      font-size: 24px;
      line-height: 32px;
      margin: 0;
    }
  }
  .red {
    color: red !important;
  }
</style>
