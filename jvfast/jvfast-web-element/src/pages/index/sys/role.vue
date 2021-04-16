<template>
  <el-card-container>
    <!--    列表组件-->
    <div class="avue-crud">
      <!--      搜索框部分-->
      <!-- 表格功能列 -->
      <div class="avue-crud__menu">
        <div class="avue-crud__left">
          <el-button
            type="primary"
            icon="el-icon-plus"
            @click="handleAddDlgShow"
          >
            新增
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
              编辑
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
        <span class="el-dialog__title">岗位{{ addViewEditDlgType==='add'? '新增':(addViewEditDlgType==='edit'? '编辑': '查看') }}</span>
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
                      :md="12"
                      :offset="0"
                      class="avue-form__row"
                      style="padding-left: 10px; padding-right: 10px;"
                    >
                      <el-form-item
                        label="角色名称"
                        prop="roleName"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入角色名称
                          </div>
                          <el-input
                            v-model="addViewEditForm.roleName"
                            type="text"
                            :minlength="3"
                            :maxlength="120"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            :disabled="addViewEditDlgType==='view'"
                            placeholder="请输入角色名称"
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
                        label="角色标识"
                        prop="roleCode"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入角色标识
                          </div>
                          <el-input
                            v-model="addViewEditForm.roleCode"
                            type="text"
                            :minlength="3"
                            :maxlength="120"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            :disabled="addViewEditDlgType==='view'"
                            placeholder="请输入角色标识"
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
                        label="菜单权限"
                        prop="dataMenu"
                        class="avue-form__item--left"
                      >
                        <el-tree
                          ref="tree"
                          :default-checked-keys="checkedMenuKeys"
                          :props="menuDataProps"
                          :data="menuData"
                          show-checkbox
                          :check-strictly="false"
                          node-key="id"
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
                        label="显示顺序"
                        prop="roleOrder"
                        class="avue-form__item--left"
                      >
                        <el-input-number
                          v-model="addViewEditForm.roleOrder"
                          class="avue-input-number"
                          :precision="0"
                          :min="0"
                          :step="1"
                          :clearable="true"
                          :readonly="addViewEditDlgType==='view'"
                          label="请输入显示顺序"
                          :disabled="addViewEditDlgType==='view'"
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
                        label="备注"
                        prop="remark"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入配置备注信息
                          </div>
                          <el-input
                            v-model="addViewEditForm.remark"
                            type="textarea"
                            :minlength="3"
                            :maxlength="120"
                            :rows="5"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            :disabled="addViewEditDlgType==='view'"
                            placeholder="请输入配置备注信息"
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
                        label="状态"
                        prop="status"
                        class="avue-form__item--left"
                      >
                        <el-switch
                          v-model="addViewEditForm.status"
                          active-text="启用"
                          inactive-text="禁用"
                          :disabled="addViewEditDlgType==='view'"
                          :readonly="addViewEditDlgType==='view'"
                        />
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
import { isAlphabets } from '@/util/ValidateUtil'

const addSysRoleAction = 'sys/role/addSysRole'
const updateSysRoleAction = 'sys/role/updateSysRole'
const removeSysRoleAction = 'sys/role/removeSysRole'
const removeSysRolesAction = 'sys/role/removeSysRoles'
const getSysRoleByIdAction = 'sys/role/getSysRoleById'
const getSysRolePageListAction = 'sys/role/getSysRolePageList'

const getSysRoleMenuListAction = 'sys/menu/getSysMenuList'
const getSysRoleMenuByRoleIdAction = 'sys/menu/listSidebarMenuByRoleId'

export default {
  name: 'Role',
  data () {
    const validateRoleCode = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('角色标识码不能为空'))
      }
      if (!isAlphabets(value)) {
        callback(new Error('角色标识码只能是字母或者拼音!'))
      } else {
        callback()
      }
    }
    return {
      originalMenuData: [],
      menuData: [],
      menuDataProps: {
        label: 'menuName',
        value: 'id',
        children: 'children'
      },
      checkedMenuKeys: [],
      statusDic: [
        {
          label: '启用',
          value: true
        },
        {
          label: '禁用',
          value: false
        }
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
        border: false,
        data: [],
        columns: [
          {
            prop: 'roleName',
            label: '角色名称',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false
          },
          {
            prop: 'roleCode',
            label: '角色标识',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false
          },
          {
            prop: 'roleOrder',
            label: '显示顺序',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '80',
            fixed: false
          },
          {
            prop: 'status',
            label: '状态',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false,
            type: 'switch'
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
        roleName: '',
        roleCode: '',
        roleOrder: 1,
        menuData: [],
        status: true,
        remark: ''
      },
      addViewEditFormRules: {
        roleName: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
        roleCode: [{ required: true, validator: validateRoleCode, trigger: 'blur' }],
        roleOrder: [{ required: false, message: '请输入排列顺序', trigger: 'blur' }],
        status: [{ required: true, message: '请选择启用关闭状态', trigger: 'blur' }],
        remark: [{ required: false, message: '请输入备注信息', trigger: 'blur' }]
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
    this.fetchMenus()
  },
  methods: {
    fetchMenus () {
      // 获取对应的树结构数据
      this.$store.dispatch(getSysRoleMenuListAction, { filterButtonPermission: false }).then((res) => {
        const resCode = res.code
        const errorMsg = res.message
        if (resCode === 0) {
          this.menuData = res.data
        } else {
          this.$message({
            type: 'error',
            showClose: true,
            message: errorMsg || '查询数据异常,请稍后再试!'
          })
        }
      }).catch((err) => {
        console.log(err)
        this.$message({
          type: 'error',
          showClose: true,
          message: '查询菜单数据异常,请稍后再试!'
        })
      })
    },
    markDiabledTree (menuData) {
      menuData.forEach((menu) => {
        menu.disabled = true
        if (menu.children && menu.children.length > 0) {
          this.markDiabledTree(menu.children)
        }
      })
    },
    fetchRoleMenuByRoleCode (data) {
      this.$store.dispatch(getSysRoleMenuByRoleIdAction, data).then((res) => {
        const menuData = res.data
        this.$nextTick(() => {
          if (menuData.length > 0) {
            const menuIds = menuData.map((r) => {
              return r.id
            })
            // 不要再使用 default-checked-keys
            // Tree 组件在渲染时，会多次调用 load 函数
            // 每次加载完成， reinitChecked 都会执行
            // 因为新的子节点还未创建，如果前两个元素是选中的，那么父节点计算的 checked 就是 true
            setTimeout(() => {
              this.$refs.tree.setCheckedKeys(menuIds)
            }, 10)
            // this.checkedMenuKeys = menuIds
          } else {
            setTimeout(() => {
              this.$refs.tree.setCheckedKeys([])
            }, 10)
          }
        })

        // if (menuIds && menuIds.length === 1 && menuIds[0] === -1) {
        //   // this.$set(this.form, 'dataMenu', null)
        //   this.checkedMenuKeys = null
        // } else {
        //   // this.$set(this.form, 'dataMenu', menuIds)
        //   this.checkedMenuKeys = menuIds
        // }
      })
    },
    // 所有菜单节点数据
    getMenuAllCheckedKeys () {
      // 目前被选中的菜单节点
      const checkedKeys = this.$refs.tree.getCheckedKeys()
      // 半选中的菜单节点
      // const halfCheckedKeys = this.$refs.tree.getCheckedKeys()
      // checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys)
      return checkedKeys
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
          return this.$store.dispatch(removeSysRolesAction, idParams)
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
    handleRefreshTable () {
      this.fetchTable(this.page)
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
      this.$store.dispatch(getSysRolePageListAction, queryData).then((res) => {
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
    // 操作功能
    handleAddDlgShow () {
      this.addViewEditDlgShow = true
      this.addViewEditDlgType = 'add'
      // 此处设置默认值
      this.addViewEditForm = {
        status: true,
        roleOrder: 1
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
      this.$store.dispatch(getSysRoleByIdAction, idParams).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          this.addViewEditForm = res.data
          // 查询对应的菜单列表
          this.fetchRoleMenuByRoleCode(idParams)
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
      const saveData = Object.assign(this.addViewEditForm, { dataMenu: this.getMenuAllCheckedKeys() })
      this.$store.dispatch(addSysRoleAction, saveData).then((res) => {
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
      const updateData = Object.assign(this.addViewEditForm, { dataMenu: this.getMenuAllCheckedKeys() })
      this.$store.dispatch(updateSysRoleAction, updateData).then((res) => {
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
          return this.$store.dispatch(removeSysRoleAction, idParams)
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
      title: '角色管理 | ' + this.website.name,
      meta: [
        { hid: 'description', name: 'description', content: '角色管理' }
      ]
    }
  }
}
</script>

<style>
</style>
