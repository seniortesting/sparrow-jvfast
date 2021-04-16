<template>
  <span @click="handleOpen">
    <el-badge :value="logs.length===0?'':logs.length">
      <i class="iconfont icon-bug" />
    </el-badge>
    <el-dialog
      :visible.sync="logDialogShow"
      title="错误日志"
      fullscreen
      width="100%"
      append-to-body
    >
      <div class="avue-crud">
        <!-- 表格功能列 -->
        <div class="avue-crud__menu">
          <div class="avue-crud__left">
            <el-button
              type="danger"
              size="small"
              icon="el-icon-delete"
              @click="clearLogHandle"
            >
              清空日志
            </el-button>
            <el-button
              type="primary"
              size="small"
              icon="el-icon-upload"
              @click="sendLogHandle"
            >
              反馈日志
            </el-button>
          </div>
          <div class="avue-crud__right">
            <!--          <div class="avue-date" style="display:inline-block;margin-right:20px;">-->
            <!--            <el-date-picker-->
            <!--              v-model="searchForm.dateRange"-->
            <!--              type="daterange"-->
            <!--              value-format="yyyy-MM-dd"-->
            <!--              align="right"-->
            <!--              unlink-panels-->
            <!--              range-separator="至"-->
            <!--              start-placeholder="开始日期"-->
            <!--              end-placeholder="结束日期"-->
            <!--              :picker-options="datePickerOptions"-->
            <!--              @change="changeDateRange"-->
            <!--            />-->
            <!--          </div>-->
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
        <!--        <el-tag class="avue-crud__tip">-->
        <!--          <span class="avue-crud__tip-name">-->
        <!--            当前表格已选择<span class="avue-crud__tip-count">{{ selectRowLen }}</span>项-->
        <!--          </span>-->
        <!--          <el-button-->
        <!--            type="text"-->
        <!--            size="small"-->
        <!--            @click="handleSelectClear"-->
        <!--          >-->
        <!--            清空-->
        <!--          </el-button>-->
        <!--        </el-tag>-->
        <!--      表格部分-->
        <el-table
          ref="table"
          v-loading="table.loading"
          :data="table.data"
          :class="{'avue-crud--indeterminate': false}"
          size="medium"
          :lazy="false"
          :tree-props="{}"
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
          <el-table-column type="expand" align="left">
            <template slot-scope="props">
              <pre class="code">
                 {{ props.row.requestErrorMsg }}
              </pre>
            </template>
          </el-table-column>
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
                  <span v-else-if="['tag'].includes(column.type)">
                    <el-tag type="danger" effect="dark">bug</el-tag>
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
    </el-dialog>
  </span>
</template>

<script>
import { mapGetters } from 'vuex'
const clearErrorLogAction = 'log/clearLogs'
const sendErrorLogAction = 'log/sendLogs'
export default {
  name: 'ErrorLog',
  data () {
    return {
      logDialogShow: false,
      // -----表格部分开始
      table: {
        loading: false,
        tableDlgType: 'elDialog',
        rowKey: 'id',
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
        border: false,
        data: [],
        columns: [
          {
            prop: 'operationType',
            label: '类型',
            display: true,
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '200',
            fixed: false,
            type: 'tag'
          },
          {
            prop: 'requestUrl',
            label: '请求地址',
            display: true,
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false
          },
          {
            prop: 'businessTitle',
            label: '异常内容',
            display: true,
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false
          }, {
            prop: 'address',
            label: '错误堆栈',
            display: true,
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false
          }, {
            prop: 'createTime',
            label: '时间',
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
      page: {
        total: 0, // 总页数
        pagerCount: 7, // 超过多少条隐藏
        pageNum: 1, // 当前页数
        pageSize: 10, // 每页显示多少条
        pageSizes: [10, 20, 30, 40, 50, 100],
        background: true // 背景颜色
      }
    }
  },
  computed: {
    ...mapGetters(['logs']),
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
  methods: {
    handleOpen () {
      if (this.logs.length === 0) {
        this.$message.warning('暂无错误异常日志信息!')
        return
      }
      this.logDialogShow = true
      this.fetchTable()
    },
    // 表格相关
    // --------------工具条
    handleRefreshTable () {
      this.fetchTable()
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
      this.$nextTick(() => {
        const startIndex = page ? (page.currentPage - 1) * this.page.pageSize : 0
        const data = this.logs.slice(startIndex, startIndex + this.page.pageSize)
        this.page.total = this.logs.length
        this.table.data = data
        this.handleSelectClear()
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
      // return {
      //   color: 'green',
      //   fontWeight: 'bold',
      //   fontSize: '20'
      // }
      return {}
    },
    handleTableRowClick (row, event, column) {
      this.table.clickRow = row
      this.$refs.table.toggleRowExpansion(row)
    },
    handleTableSelectionChange (selection) {
      // 单行或多行选择回调
      this.table.selectList = selection
    },
    // ************ 序列号
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
    formatOperationType (row, value, result, column) {
      return '<el-tag type="danger" effect="dark">bug</el-tag>'
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
    clearLogHandle () {
      this.$confirm('确定清空所有错误日志记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$store.dispatch(clearErrorLogAction)
        this.logDialogShow = false
        this.page.total = 0
        this.table.data = []
        this.$message({
          type: 'success',
          message: '清空错误日志成功!'
        })
      }).catch(() => {

      })
    },
    sendLogHandle () {
      this.$confirm('确定反馈所有错误日志到服务器吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = this.$loading({
          lock: true,
          fullscreen: true,
          text: '上传中...'
        })
        this.$store.dispatch(sendErrorLogAction).then(() => {
          this.logDialogShow = false
          loading.close()
          this.$message({
            type: 'success',
            message: '上传日志成功!'
          })
        }).catch(() => {
          loading.close()
        })
      }).catch(() => {
      })
    }
  }
}
</script>

<style scoped>
  .code {
    display: block;
    white-space: pre;
    background: #1d1e22;
    color: white;
    font-size: 14px;
    font-weight: 400;
    line-height: 1.5;
    padding: 25px;
  }
</style>
