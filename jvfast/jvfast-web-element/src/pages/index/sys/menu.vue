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
                      :md="10"
                      :lg="10"
                      :offset="0"
                      class="avue-form__row"
                      style="padding-left: 10px; padding-right: 10px;"
                    >
                      <el-form-item
                        label="菜单名称"
                        prop="menuName"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入菜单名称
                          </div>
                          <el-input
                            v-model="searchForm.menuName"
                            type="text"
                            :minlength="3"
                            :maxlength="120"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            placeholder="请输入对应的菜单名称"
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
            @click="handleAddDlgShow"
          >
            新增
          </el-button>
          <!--          <el-button-->
          <!--            type="danger"-->
          <!--            icon="el-icon-delete"-->
          <!--            @click="handleBatchDeleteRow"-->
          <!--          >-->
          <!--            删除-->
          <!--          </el-button>-->
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
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
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
                <span v-if="['icon-select'].includes(column.type)">
                  <i
                    class="avue-crud__icon-select"
                    :class="scope.row[column.prop]"
                  />
                </span>
                <span v-if="['menuType'].includes(column.type)">
                  {{ formatMenuType(scope.row[column.prop]) }}
                </span>
                <span v-if="['icon'].includes(column.type)">
                  <i v-if="scope.row[column.prop]" :class="scope.row[column.prop]" />
                  <span v-else>按钮/权限</span>
                </span>
                <span v-else-if="['tag'].includes(column.type)">
                  <el-tag :type="scope.row[column.prop]? 'danger':'success'"> {{ scope.row[column.prop]? '隐藏/禁用':'显示/可用' }} </el-tag>
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
          :width="$device.isMobile? 100:210"
        >
          <template slot-scope="scope">
            <el-button
              v-if="scope.row.menuType===1||scope.row.menuType===2"
              type="text"
              icon="el-icon-plus"
              @click.stop="handleRowAddSubMenuDlgShow(scope.row,scope.$index)"
            >
              子菜单
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
      :size="$device.isMobile? '100%':'60%'"
      :width="$device.isMobile? '100%':'60%'"
      @close="handleAddViewEditDlgClose"
    >
      <div slot="title" class="avue-crud__dialog__header">
        <span class="el-dialog__title">菜单{{ addViewEditDlgType==='addSub'? '新增':(addViewEditDlgType==='edit'? '编辑': '查看') }}</span>
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
              label-width="100px"
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
                        label="菜单类型"
                        prop="menuType"
                        class="avue-form__item--left"
                      >
                        <div class="avue-radio">
                          <el-radio-group
                            v-model="addViewEditForm.menuType"
                            size="medium"
                            :disabled="addViewEditDlgType==='view'"
                          >
                            <template v-if="addViewEditDlgType!=='add'">
                              <component
                                :is="'ElRadioButton'"
                                v-for="(item) in menuTypeDicData1"
                                :key="item.value"
                                :label="item.value"
                                :border="true"
                              >
                                {{ item.label }}
                              </component>
                            </template>
                            <template v-else>
                              <component
                                :is="'ElRadioButton'"
                                v-for="(item) in menuTypeDicData2"
                                :key="item.value"
                                :label="item.value"
                                :border="true"
                              >
                                {{ item.label }}
                              </component>
                            </template>
                          </el-radio-group>
                        </div>
                      </el-form-item>
                    </el-col>
                    <el-col
                      v-if="addViewEditForm.menuType!==1"
                      :sm="12"
                      :xs="24"
                      :md="24"
                      :offset="0"
                      class="avue-form__row"
                      style="padding-left: 10px; padding-right: 10px;"
                    >
                      <el-form-item
                        label="上级菜单"
                        prop="pid"
                        class="avue-form__item--left"
                      >
                        <el-input-tree
                          v-model="addViewEditForm.pid"
                          placeholder="请选择上级菜单"
                          :dic="topMenus"
                          :disabled="['view'].includes(addViewEditDlgType)"
                          :dicobj="{
                            nodeKey: 'id',
                            label: 'menuName',
                            value: 'id',
                            groups: 'groups',
                            leaf: 'leaf',
                            children: 'children',
                          }"
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
                        label="名称"
                        prop="menuName"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入对应的菜单名称
                          </div>
                          <el-input
                            v-model="addViewEditForm.menuName"
                            type="text"
                            :minlength="3"
                            :maxlength="120"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            :disabled="addViewEditDlgType==='view'"
                            placeholder="请输入对应的菜单名称"
                          />
                        </el-tooltip>
                      </el-form-item>
                    </el-col>
                    <el-col
                      v-if="addViewEditForm.menuType!==3"
                      :sm="12"
                      :xs="24"
                      :md="12"
                      :offset="0"
                      class="avue-form__row"
                      style="padding-left: 10px; padding-right: 10px;"
                    >
                      <el-form-item
                        label="菜单图标"
                        prop="menuIcon"
                        class="avue-form__item--left"
                      >
                        <el-icon-select
                          v-model="addViewEditForm.menuIcon"
                          :icon-list="iconList"
                        />
                      </el-form-item>
                    </el-col>
                    <el-col
                      v-if="addViewEditForm.menuType===2&&(!addViewEditForm.external)"
                      :sm="12"
                      :xs="24"
                      :md="24"
                      :offset="0"
                      class="avue-form__row"
                      style="padding-left: 10px; padding-right: 10px;"
                    >
                      <el-form-item
                        label="路由名称"
                        prop="permission"
                        class="avue-form__item--left"
                      >
                        <el-input-tree
                          v-model="addViewEditForm.deptId"
                          placeholder="请选择路由名称"
                          :dic="routes"
                          :disabled="['view'].includes(addViewEditDlgType)"
                          :dicobj="{
                            nodeKey: 'name',
                            label: 'name',
                            value: 'name',
                            groups: 'groups',
                            leaf: 'leaf',
                            children: 'children',
                          }"
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
                        prop="menuOrder"
                        class="avue-form__item--left"
                      >
                        <el-input-number
                          v-model="addViewEditForm.menuOrder"
                          class="avue-input-number"
                          :precision="0"
                          :min="1"
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
                        label="权限标识"
                        prop="permission"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入对应的菜单权限标识
                          </div>
                          <el-input
                            v-model="addViewEditForm.permission"
                            type="text"
                            :minlength="3"
                            :maxlength="120"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            :disabled="addViewEditDlgType==='view'"
                            placeholder="请输入对应的菜单权限标识"
                          />
                        </el-tooltip>
                      </el-form-item>
                    </el-col>
                    <el-col
                      v-if="addViewEditForm.menuType!==3"
                      :sm="12"
                      :xs="24"
                      :md="12"
                      :offset="0"
                      class="avue-form__row"
                      style="padding-left: 10px; padding-right: 10px;"
                    >
                      <el-form-item
                        label="是否外链"
                        prop="external"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请选择是否外链
                          </div>
                          <div class="avue-radio">
                            <el-radio-group
                              v-model="addViewEditForm.external"
                            >
                              <el-radio :label="false">
                                否
                              </el-radio>
                              <el-radio :label="true">
                                是
                              </el-radio>
                            </el-radio-group>
                          </div>
                        </el-tooltip>
                      </el-form-item>
                    </el-col>
                    <el-col
                      v-if="addViewEditForm.external&&addViewEditForm.menuType!==3"
                      :sm="12"
                      :xs="24"
                      :md="12"
                      :offset="0"
                      class="avue-form__row"
                      style="padding-left: 10px; padding-right: 10px;"
                    >
                      <el-form-item
                        label="外链地址"
                        prop="externalUrl"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入外链地址
                          </div>
                          <el-input
                            v-model="addViewEditForm.componentName"
                            type="text"
                            :minlength="3"
                            :maxlength="120"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            :disabled="addViewEditDlgType==='view'"
                            placeholder="请输入外链地址"
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
                        label="是否显示/可用"
                        label-width="130px"
                        prop="hidden"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请选择是否显示/可用
                          </div>
                          <div class="avue-radio">
                            <el-radio-group
                              v-model="addViewEditForm.hidden"
                            >
                              <el-radio :label="false">
                                显示/可用
                              </el-radio>
                              <el-radio :label="true">
                                隐藏/禁用
                              </el-radio>
                            </el-radio-group>
                          </div>
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
                        label="备注"
                        prop="remark"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入备注
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
                            placeholder="请输入备注"
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
import ElInputTree from '@/components/vue-element-ui/el-input-tree'
import ElIconSelect from '@/components/vue-element-ui/el-icon-select'
const addSysMenuAction = 'sys/menu/addSysMenu'
const updateSysMenuAction = 'sys/menu/updateSysMenu'
const removeSysMenuAction = 'sys/menu/removeSysMenu'
const removeSysMenusAction = 'sys/menu/removeSysMenus'
const getSysMenuByIdAction = 'sys/menu/getSysMenuById'
const getSysMenuPageListAction = 'sys/menu/getSysMenuPageList'

const getSysRoleMenuListAction = 'sys/menu/getSysMenuList'
// 图标
const listIconFontIconsAction = 'sys/menu/listIconFontIcons'
const listElementIconsAction = 'sys/menu/listElementIcons'

export default {
  name: 'Menu',
  components: { ElInputTree, ElIconSelect },
  data () {
    return {
      addSubMenu: false,
      addSubMenuRow: {},
      menuTypeDicData1: [
        {
          label: '顶部导航',
          value: 1
        },
        {
          label: '侧边菜单/模块',
          value: 2
        },
        {
          label: '按钮/权限',
          value: 3
        }
      ],
      menuTypeDicData2: [
        {
          label: '侧边菜单/模块',
          value: 2
        },
        {
          label: '按钮/权限',
          value: 3
        }
      ],
      topMenus: [],
      routes: [],
      iconList: [
        {
          label: '饿了么图标',
          icon: 'el-icon-platform-eleme',
          list: []
        },
        {
          label: 'iconfont图标',
          icon: 'el-icon-s-shop',
          list: []
        }
      ],
      // 搜索部分
      searchShow: true,
      searchForm: {
        menuName: ''
      },
      searchFormRules: {},
      searchBtnDisabled: false,
      // 顶部菜单栏部分
      // -----表格部分开始
      table: {
        loading: false,
        tableDlgType: 'elDialog',
        rowKey: 'id',
        expandRowKeys: ['1', '2'],
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
            prop: 'menuType',
            label: '类型',
            minWidth: 'auto',
            sortable: false,
            align: 'left',
            headerAlign: 'left',
            width: '170',
            fixed: false,
            type: 'menuType'
          },
          {
            prop: 'menuName',
            label: '菜单名称',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '110',
            fixed: false
          },
          {
            prop: 'menuIcon',
            label: '图标',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '60',
            fixed: false,
            type: 'icon'
          },
          {
            prop: 'componentName',
            label: '路由名称',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '120',
            fixed: false
          },
          {
            prop: 'menuOrder',
            label: '显示顺序',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '80',
            fixed: false
          },
          {
            prop: 'permission',
            label: '权限标识',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '110',
            fixed: false
          },
          {
            prop: 'external',
            label: '是否外链',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false,
            type: 'switch'
          },
          {
            prop: 'hidden',
            label: '显示/隐藏',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false,
            type: 'tag'
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
      addViewEditDlgTopType: false,
      addViewEditForm: {
        menuType: 1,
        pid: '',
        menuName: '',
        menuIcon: '',
        componentName: '',
        menuOrder: 1,
        permission: '',
        external: false,
        externalUrl: '',
        hidden: false,
        remark: ''
      },
      addViewEditFormRules: {
        menuType: [{ required: true, message: '菜单类型不能为空', trigger: 'blur' }],
        menuName: [{ required: true, message: '菜单名称不能为空', trigger: 'blur' }]
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
    this.fetchParentMenuTree()
    this.fetchRouteComponentNames()
    this.fetchIconfonts()
  },
  methods: {
    fetchParentMenuTree () {
      this.$store.dispatch(getSysRoleMenuListAction, { filterButtonPermission: true }).then((res) => {
        const resCode = res.code
        const errorMsg = res.message
        if (resCode === 0) {
          this.topMenus = res.data
        } else {
          this.$message({
            type: 'error',
            showClose: true,
            message: errorMsg || '查询菜单数据失败,请稍后再试!'
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
    fetchRouteComponentNames () {
      const routes = this.$router.options.routes
      this.routes = routes
    },
    fetchIconfonts () {
      Promise.all([
        this.$store.dispatch(listElementIconsAction),
        this.$store.dispatch(listIconFontIconsAction)
      ]).then((res) => {
        const [elementIcons, iconfontIcons] = res
        const elements = elementIcons.map((i) => {
          return i.name
        })
        const iconfonts = iconfontIcons.map((i) => {
          return 'iconfont ' + i.name
        })
        this.iconList[0].list = elements
        this.iconList[1].list = iconfonts
      }).catch((err) => {
        console.log(err)
        this.$message({
          type: 'error',
          showClose: true,
          message: '查询图标数据异常,请稍后再试!'
        })
      })
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
          return this.$store.dispatch(removeSysMenusAction, idParams)
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
      this.$store.dispatch(getSysMenuPageListAction, queryData).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          const data = res.data
          // this.page.total = parseInt(data.total)
          this.page.total = 10
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
    formatMenuType (value) {
      const foundMenu = this.menuTypeDicData1.find(menu => menu.value === value)
      return foundMenu.label
    },
    // 操作功能
    handleAddDlgShow () {
      // 此处设置默认值
      this.addViewEditForm = {
        menuType: 1,
        pid: null,
        menuName: '',
        menuIcon: '',
        componentName: '',
        menuOrder: 1,
        permission: '',
        external: false,
        externalUrl: '',
        hidden: false,
        remark: ''
      }
      this.addViewEditDlgShow = true
      this.addViewEditDlgType = 'addAll'
    },
    handleAddViewEditDlgClose (row) {
      if (row) {
        row = {}
      }
      this.addViewEditDlgShow = false
      this.fetchTable(this.page)
    },
    handleRowAddSubMenuDlgShow (row, index) {
      // 显示弹出框
      this.addViewEditForm = {
        // 此处设置默认的选中菜单选项是“子菜单”，没有顶级菜单
        menuType: 2,
        pid: row.id || '',
        menuName: '',
        menuIcon: '',
        componentName: '',
        menuOrder: 1,
        permission: '',
        external: false,
        externalUrl: '',
        hidden: false,
        remark: ''
      }
      this.addViewEditDlgType = 'add'
      this.addViewEditDlgShow = true
      // 获取对应的数据
    },
    handleRowEditDlgShow (row, index) {
      // 显示弹出框
      this.addViewEditDlgShow = true
      this.addViewEditDlgType = 'edit'
      this.addViewEditForm = row
      console.log(this.addViewEditForm)
      // 获取对应的数据
      this.fetchRowByRowId(row)
    },
    fetchRowByRowId (row) {
      const idParams = { id: row.id }
      this.$store.dispatch(getSysMenuByIdAction, idParams).then((res) => {
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
          if (this.addViewEditDlgType === 'add' || this.addViewEditDlgType === 'addSub') {
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
      this.addViewEditForm.userId = this.userInfo.id
      this.$store.dispatch(addSysMenuAction, this.addViewEditForm).then((res) => {
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
      this.addViewEditForm.userId = this.userInfo.id
      this.$store.dispatch(updateSysMenuAction, this.addViewEditForm).then((res) => {
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
          return this.$store.dispatch(removeSysMenuAction, idParams)
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
      title: '菜单管理 | ' + this.website.name,
      meta: [
        { hid: 'description', name: 'description', content: '菜单管理' }
      ]
    }
  }
}
</script>

<style>
</style>
