<template>
  <el-card-container>
    <el-row class="avue-view">
      <el-col :md="6" :sm="24">
        <div
          class="avue-tree__filter label"
        >
          <div>
            <div>部门组织架构</div>
            <i class="el-icon-refresh" @click="refreshTree" />
          </div>
          <div style="margin-top: 10px;font-size: 11px;color: #606266;background-color: #ecf8ff;border-radius: 4px;border-left: 5px solid #50bfff;padding: 8px 6px;">
            <p>右键部门节点进行新增编辑删除等操作</p>
          </div>
          <div style="padding-top: 10px">
            <el-input v-model="filterText" :size="treeOption.size" placeholder="输入关键字过滤" />
          </div>
        </div>
        <!-- 树形结构-->
        <el-tree
          ref="tree"
          v-loading="treeOption.loading"
          :data="treeOption.data"
          :node-key="treeOption.nodeKey"
          :filter-node-method="treeFilterNode"
          :expand-on-click-node="false"
          :default-expand-all="true"
          :draggable="true"
          @node-click="treeClickHandle"
          @node-contextmenu="contextMenuHandle"
        >
          <div slot-scope="{ node, data }" class="avue-tree__item">
            <div class="avue-tree__title">
              {{ data.deptName }}
            </div>
          </div>
        </el-tree>
        <!--          右键显示弹出层-->
        <div v-show="contextMenuShow" :style="{left: contextMenuLeft, top: contextMenuTop}" class="el-context-menu">
          <div class="el-context-menu-box">
            <div @click="addDeptHandle">
              <span>新增子部门</span><i class="el-icon-plus" />
            </div>
            <div @click="editDeptHandle">
              <span>编辑</span><i class="el-icon-edit" />
            </div>
            <div @click="delDeptHandle">
              <span>删除</span><i class="el-icon-delete" />
            </div>
            <div @click="addDeptUserHandle">
              <span>添加部门人员</span><i class="el-icon-user" />
            </div>
          </div>
        </div>
        <el-dialog
          :title="deptDlgTitle"
          :visible.sync="deptDlgShow"
          width="35%"
        >
          <div class="avue-form" style="width: 100%">
            <el-form
              ref="deptForm"
              v-loading="deptLoading"
              :model="deptForm"
              status-icon
              label-suffix=":"
              label-position="left"
              label-width="130px"
              :rules="deptFormRules"
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
                        label="上级部门名称"
                        prop="pDeptName"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入上级部门名称
                          </div>
                          <el-input
                            v-model="deptForm.pDeptName"
                            type="text"
                            :minlength="3"
                            :maxlength="120"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="false"
                            disabled
                            placeholder="请输入上级部门名称"
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
                        :label="deptType==='add'?'子部门(节点)名称': '部门(节点)名称'"
                        prop="name"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入对应的部门名称
                          </div>
                          <el-input
                            v-model="deptForm.deptName"
                            type="text"
                            :minlength="3"
                            :maxlength="120"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="false"
                            placeholder="请输入对应的部门名称"
                          />
                        </el-tooltip>
                      </el-form-item>
                    </el-col>
                  </div>
                </el-group>
              </el-row>
            </el-form>
          </div>
          <span slot="footer" class="dialog-footer">
            <el-button @click="deptFormHide">取 消</el-button>
            <el-button type="primary" @click="deptFormSubmit">确 定</el-button>
          </span>
        </el-dialog>
      </el-col>
      <el-col :md="18" :sm="24">
        <el-card-container>
          <!--    列表组件-->
          <div class="avue-crud">
            <div class="padding-content" style="margin-bottom: 20px">
              <h5>部门人员列表{{ table.title?'(部门:'+table.title+')':'' }} </h5>
            </div>
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
                    label-width="80px"
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
                              label="用户名"
                              label-width="60px"
                              prop="userName"
                              class="avue-form__item--left"
                            >
                              <el-tooltip placement="bottom">
                                <div slot="content">
                                  请输入对应的用户名
                                </div>
                                <el-input
                                  v-model="searchForm.userName"
                                  type="text"
                                  :minlength="3"
                                  :maxlength="120"
                                  :show-word-limit="true"
                                  class="avue-input"
                                  :clearable="true"
                                  placeholder="请输入用户名"
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
                              label="手机号码"
                              prop="phone"
                              class="avue-form__item--left"
                            >
                              <el-tooltip placement="bottom">
                                <div slot="content">
                                  请输入对应的手机号码
                                </div>
                                <el-input
                                  v-model="searchForm.phone"
                                  type="text"
                                  :minlength="3"
                                  :maxlength="120"
                                  :show-word-limit="true"
                                  class="avue-input"
                                  :clearable="true"
                                  placeholder="请输入手机号码"
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
                              label="邮箱"
                              label-width="50px"
                              prop="email"
                              class="avue-form__item--left"
                            >
                              <el-tooltip placement="bottom">
                                <div slot="content">
                                  请输入对应的邮箱
                                </div>
                                <el-input
                                  v-model="searchForm.email"
                                  type="text"
                                  :minlength="3"
                                  :maxlength="120"
                                  :show-word-limit="true"
                                  class="avue-input"
                                  :clearable="true"
                                  placeholder="请输入邮箱"
                                />
                              </el-tooltip>
                            </el-form-item>
                          </el-col>
                          <el-col class="avue-form__menu avue-form__menu--center" style="padding: 0px;">
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
                  icon="el-icon-edit"
                  @click="handleResetPasswd"
                >
                  密码重置
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
              <el-table-column type="expand">
                <template slot-scope="props">
                  <el-form label-position="left" inline class="table-expand">
                    <el-form-item label="性别">
                      <span>{{ props.row.sex ===1 ? '男':(props.row.sex===2? '女':'未知') }}</span>
                    </el-form-item>
                    <el-form-item v-if="props.row.birth" label="生日">
                      <span>{{ props.row.birth }}</span>
                    </el-form-item>
                    <el-form-item label="头像">
                      <el-image style="width: 64px;height: 64px" :src="props.row.avatar" />
                    </el-form-item>
                    <el-form-item v-if="props.row.email" label="邮箱">
                      <span>{{ props.row.email }}</span>
                    </el-form-item>
                    <el-form-item v-if="props.row.signature" label="签名">
                      <span>{{ props.row.signature }}</span>
                    </el-form-item>
                    <el-form-item v-if="props.row.address" label="联系地址">
                      <span>{{ props.row.address }}</span>
                    </el-form-item>
                    <el-form-item label="创建时间">
                      <span>{{ props.row.updateTime }}</span>
                    </el-form-item>
                  </el-form>
                </template>
              </el-table-column>
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
                :width="$device.isMobile? 180:230"
              >
                <template slot-scope="scope">
                  <el-button
                    type="success"
                    icon="el-icon-view"
                    size="medium"
                    @click.stop="handleRowViewDlgShow(scope.row,scope.$index)"
                  />
                  <el-button
                    type="primary"
                    icon="el-icon-edit"
                    size="medium"
                    @click.stop="handleRowEditDlgShow(scope.row,scope.$index)"
                  />
                  <el-button
                    type="danger"
                    icon="el-icon-delete"
                    size="medium"
                    @click.stop="handleRowDeleteShow(scope.row,scope.$index)"
                  />
                  <el-button
                    type="warning"
                    icon="el-icon-key"
                    size="medium"
                    @click.stop="handleRowAuthorizeShow(scope.row,scope.$index)"
                  >
                    角色/授权
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
            size="60%"
            width="450"
            @close="handleAddViewEditDlgClose"
          >
            <div slot="title" class="avue-crud__dialog__header">
              <span class="el-dialog__title">用户{{ addViewEditDlgType==='add'? '新增':(addViewEditDlgType==='edit'? '编辑': '查看') }}</span>
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
                            v-if="addViewEditDlgType==='add'"
                            :sm="24"
                            :xs="24"
                            :md="24"
                            :offset="0"
                            class="avue-form__row"
                            style="padding-left: 10px; padding-right: 10px;padding-bottom: 20px"
                          >
                            <el-alert
                              :closable="false"
                              :title="`新增用户默认密码为：${defaultPasswd}`"
                              type="warning"
                              effect="dark"
                            />
                          </el-col>
                          <el-col
                            :sm="24"
                            :xs="24"
                            :md="24"
                            :offset="0"
                            class="avue-form__row"
                            style="padding-left: 10px; padding-right: 10px;"
                          >
                            <el-form-item
                              label="登录账号"
                              prop="userName"
                              class="avue-form__item--left"
                            >
                              <el-tooltip placement="bottom">
                                <div slot="content">
                                  请输入登录账号
                                </div>
                                <el-input
                                  v-model="addViewEditForm.userName"
                                  type="text"
                                  :minlength="3"
                                  :maxlength="120"
                                  :show-word-limit="true"
                                  class="avue-input"
                                  :clearable="true"
                                  :disabled="['view','edit'].includes(addViewEditDlgType)"
                                  placeholder="请输入登录账号"
                                />
                              </el-tooltip>
                            </el-form-item>
                          </el-col>
                          <el-col
                            :sm="24"
                            :xs="24"
                            :md="12"
                            :offset="0"
                            class="avue-form__row"
                            style="padding-left: 10px; padding-right: 10px;"
                          >
                            <el-form-item
                              label="所属角色"
                              prop="roleId"
                              class="avue-form__item--left"
                            >
                              <el-select
                                ref="selectRoleId"
                                v-model="addViewEditForm.roleId"
                                class="avue-select"
                                multiple
                                placeholder="请选择角色"
                                :disabled="addViewEditDlgType==='view'"
                              >
                                <el-option
                                  v-for="item in roles"
                                  :key="item.id"
                                  :label="item.roleName"
                                  :value="item.id"
                                />
                              </el-select>
                            </el-form-item>
                          </el-col>
                          <el-col
                            :sm="24"
                            :xs="24"
                            :md="12"
                            :offset="0"
                            class="avue-form__row"
                            style="padding-left: 10px; padding-right: 10px;"
                          >
                            <el-form-item
                              label="所属部门"
                              prop="deptId"
                              class="avue-form__item--left"
                            >
                              <el-input-tree
                                v-model="addViewEditForm.deptId"
                                placeholder="请选择所属部门"
                                :dic="treeOption.data"
                                :disabled="['view'].includes(addViewEditDlgType)"
                                :dicobj="{
                                  nodeKey: 'id',
                                  label: 'deptName',
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
                            :md="24"
                            :offset="0"
                            class="avue-form__row"
                            style="padding-left: 10px; padding-right: 10px;"
                          >
                            <el-form-item
                              label="头像"
                              prop="avatar"
                              class="avue-form__item--left"
                            >
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
                            </el-form-item>
                          </el-col>
                          <el-col
                            :sm="24"
                            :xs="24"
                            :md="12"
                            :offset="0"
                            class="avue-form__row"
                            style="padding-left: 10px; padding-right: 10px;"
                          >
                            <el-form-item
                              label="昵称"
                              prop="nickName"
                              class="avue-form__item--left"
                            >
                              <el-tooltip placement="bottom">
                                <div slot="content">
                                  请输入昵称
                                </div>
                                <el-input
                                  v-model="addViewEditForm.nickName"
                                  type="text"
                                  :minlength="3"
                                  :maxlength="120"
                                  :show-word-limit="true"
                                  class="avue-input"
                                  :clearable="true"
                                  :disabled="addViewEditDlgType==='view'"
                                  placeholder="请输入昵称"
                                />
                              </el-tooltip>
                            </el-form-item>
                          </el-col>
                          <el-col
                            :sm="24"
                            :xs="24"
                            :md="12"
                            :offset="0"
                            class="avue-form__row"
                            style="padding-left: 10px; padding-right: 10px;"
                          >
                            <el-form-item
                              label="性别"
                              prop="sex"
                              class="avue-form__item--left"
                            >
                              <el-tooltip placement="bottom">
                                <div slot="content">
                                  请选择性别
                                </div>
                                <div class="avue-radio">
                                  <el-radio-group
                                    v-model="addViewEditForm.sex"
                                    :disabled="addViewEditDlgType==='view'"
                                  >
                                    <el-radio :label="1">
                                      男
                                    </el-radio>
                                    <el-radio :label="2">
                                      女
                                    </el-radio>
                                    <el-radio :label="3">
                                      未知
                                    </el-radio>
                                  </el-radio-group>
                                </div>
                              </el-tooltip>
                            </el-form-item>
                          </el-col>
                          <el-col
                            :sm="24"
                            :xs="24"
                            :md="12"
                            :offset="0"
                            class="avue-form__row"
                            style="padding-left: 10px; padding-right: 10px;"
                          >
                            <el-form-item
                              label="出生日期"
                              prop="birth"
                              class="avue-form__item--left"
                            >
                              <div class="avue-date">
                                <el-date-picker
                                  v-model="addViewEditForm.birth"
                                  :picker-options="endPickerOptions"
                                  type="date"
                                  :unlink-panels="false"
                                  format="yyyy-MM-dd"
                                  :clearable="true"
                                  :disabled="addViewEditDlgType==='view'"
                                  value-format="yyyy-MM-dd"
                                  placeholder="请选择出生日期"
                                />
                              </div>
                            </el-form-item>
                          </el-col>
                          <el-col
                            :sm="24"
                            :xs="24"
                            :md="24"
                            :offset="0"
                            class="avue-form__row"
                            style="padding-left: 10px; padding-right: 10px;"
                          >
                            <el-form-item
                              label="签名"
                              prop="signature"
                              class="avue-form__item--left"
                            >
                              <el-tooltip placement="bottom">
                                <div slot="content">
                                  请输入您的个性签名
                                </div>
                                <el-input
                                  v-model="addViewEditForm.signature"
                                  type="textarea"
                                  :minlength="3"
                                  :maxlength="120"
                                  :rows="5"
                                  :show-word-limit="true"
                                  class="avue-input"
                                  :clearable="true"
                                  :disabled="addViewEditDlgType==='view'"
                                  placeholder="请输入您的个性签名"
                                />
                              </el-tooltip>
                            </el-form-item>
                          </el-col>
                          <el-col
                            :sm="24"
                            :xs="24"
                            :md="24"
                            :offset="0"
                            class="avue-form__row"
                            style="padding-left: 10px; padding-right: 10px;"
                          >
                            <el-form-item
                              label="地址"
                              prop="address"
                              class="avue-form__item--left"
                            >
                              <el-tooltip placement="bottom">
                                <div slot="content">
                                  请输入联系地址
                                </div>
                                <el-input
                                  v-model="addViewEditForm.address"
                                  type="text"
                                  :minlength="3"
                                  :maxlength="220"
                                  :show-word-limit="true"
                                  class="avue-input"
                                  :clearable="true"
                                  :disabled="addViewEditDlgType==='view'"
                                  placeholder="请输入联系地址"
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
                              label="账号状态"
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
      </el-col>
    </el-row>
  </el-card-container>
</template>

<script>
import { mapGetters } from 'vuex'
import config from '@/config'
import upload from '@/api/sys/upload'
import { formatDate } from '@/util/DateUtil'
import { uniqueStr } from '@/util/NumberUtil'
import ElInputTree from '@/components/vue-element-ui/el-input-tree'
// 部门相关接口
const getDeptTreeAction = 'sys/dept/getSysDeptTreeList'
const addDeptAction = 'sys/dept/addSysDept'
const updateDeptAction = 'sys/dept/updateSysDept'
const removeDeptAction = 'sys/dept/removeSysDept'

// 人员相关接口

const addUserAction = 'sys/user/addUser'
const updateUserAction = 'sys/user/updateUser'
const removeUserAction = 'sys/user/removeUserById'
const removeUsersAction = 'sys/user/removeUsersById'
const getUserByIdAction = 'sys/user/getUserById'
const getUserPageListAction = 'sys/user/getUserPageList'

const resetUserPasswdBatchAction = 'sys/user/resetUserPasswdBatch'
// const lockUserAction = 'sys/user/lockUser'
const getRoleListAction = 'sys/role/getSysRoleList'
const getUserRoleByUserIdAction = 'sys/user/getUserRoleByUserId'
const delFileAction = 'sys/upload/deleteFile'

export default {
  name: 'UserDept',
  components: { ElInputTree },
  data () {
    return {
      defaultPasswd: config.defaultSettings.defaultPasswd,
      filterText: '',
      treeOption: {
        loading: false,
        data: [],
        nodeKey: 'id',
        addBtn: true,
        checkStrictly: true,
        menu: true,
        size: 'small'
      },
      contextMenuShow: false,
      contextMenuLeft: '',
      contextMenuTop: '',
      treeNode: {},
      treeObj: {},
      // 弹出新建，编辑删除
      deptType: '',
      deptDlgTitle: '',
      deptDlgShow: false,
      deptForm: {
        pDeptName: '',
        deptName: ''
      },
      deptLoading: false,
      deptFormRules: {
        deptName: [{ required: true, message: '部门名称不能为空', trigger: 'blur' }]
      },
      // -----------------------------------------
      // 搜索部分
      // 上传相关
      uploadTypeList: {
        img: /\.(gif|jpg|jpeg|png|GIF|JPG|PNG)/,
        video: /\.(swf|avi|flv|mpg|rm|mov|wav|asf|3gp|mkv|rmvb|ogg)/
      },
      uploadLoading: false,
      uploadDisabled: false,
      uploadType: 'picture-img',
      uploadMenu: false,
      uploadAction: '',
      uploadToken: '',
      uploadLimit: 1,
      uploadDrag: false,
      uploadShowFileList: false,
      uploadFileList: [],
      uploadFileUrl: '',
      uploadPreviewDlgShow: false,
      uploadPreviewDlgImgUrl: '',
      // 出生日期
      endPickerOptions: {
        disabledDate: this.handleDisabledDate
      },
      roles: [],
      searchShow: true,
      searchForm: {
        userName: '',
        phone: '',
        email: ''
      },
      searchFormRules: {},
      searchBtnDisabled: false,
      // 顶部菜单栏部分
      // -----表格部分开始
      table: {
        title: '',
        loading: false,
        tableDlgType: 'elDrawer',
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
            prop: 'deptName',
            label: '部门名称',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '130',
            fixed: false
          },
          {
            prop: 'id',
            label: '用户ID',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '130',
            fixed: false
          },
          {
            prop: 'userName',
            label: '用户名',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '130',
            fixed: false
          },
          {
            prop: 'nickName',
            label: '昵称',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '150',
            fixed: false
          },
          {
            prop: 'phone',
            label: '手机号码',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '150',
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
        userName: '',
        nickName: '',
        roleId: [],
        deptId: '',
        phone: '',
        email: '',
        sex: '',
        birth: '',
        avatar: '',
        signature: '',
        address: '',
        status: true
      },
      addViewEditFormRules: {
        userName: [{ required: true, message: '用户账号不能为空', trigger: 'blur' }],
        roleId: [{ required: true, message: '请选择一个用户角色', trigger: 'blur' }],
        deptId: [{ required: true, message: '请选择一个部门', trigger: 'blur' }],
        status: [{ required: true, message: '请选择账号状态', trigger: 'blur' }]
      },
      addViewEditFormSubmitDisable: false,
      // -----表格部分结束
      authorizeBox: false
    }
  },
  computed: {
    ...mapGetters(['permissions', 'website', 'userInfo', 'sidebar']),
    selectRowLen () {
      return this.table.selectList ? this.table.selectList.length : 0
    }
  },
  watch: {
    filterText (val) {
      this.$refs.tree.filter(val)
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
    this.fetchDeptTree()
    this.fetchTable(this.page)
    // 获取对应的用户的角色信息
    this.fetchRoleList()
  },
  methods: {
    refreshTree () {
      this.fetchDeptTree()
      this.fetchTable(this.page)
    },
    treeFilterNode (value, data) {
      if (!value) { return true }
      return this.treeOption.data.includes(value)
    },
    treeClickHandle (data, node, el) {
      const deptId = data.id
      this.table.title = data.deptName
      this.query = { deptId }
      this.page.currentPage = 1
      this.fetchTable(this.page)
    },
    contextMenuHandle (e, data, node, el) {
      const self = this
      this.contextMenuTop = e.pageY - 50 - 120 + 'px'
      // 修复菜单定位不准问题
      const sidebarWidth = this.sidebar.opened ? 210 : 50
      this.contextMenuLeft = e.pageX - sidebarWidth + 'px'
      this.contextMenuShow = true
      this.treeObj = data
      this.treeNode = node
      document.onclick = function (ev) {
        // if (ev.target !== self.$refs.tree) {
        self.contextMenuShow = false
        // }
      }
    },
    // 点击新增
    addDeptHandle () {
      this.deptType = 'add'
      const pDeptName = this.treeObj.deptName
      this.deptForm = {
        id: '',
        pid: this.treeObj.id,
        pDeptName
      }
      this.deptDlgTitle = `新增部门(上级部门:${pDeptName})`
      this.deptFormShow()
    },
    // 点击编辑
    editDeptHandle () {
      this.deptType = 'edit'
      const deptName = this.treeObj.deptName
      const pDeptName = this.treeNode.parent.data.deptName
      this.deptForm = {
        id: this.treeObj.id,
        pDeptName,
        deptName
      }
      this.deptDlgTitle = `编辑部门(部门:${deptName})`
      this.deptFormShow()
    },
    deptFormShow () {
      this.deptDlgShow = true
      setTimeout(() => {
        this.$refs.deptForm.clearValidate()
      }, 0)
    },
    deptFormHide () {
      this.deptDlgShow = false
      this.treeNode = {}
      this.treeObj = {}
      this.$refs.deptForm.resetFields()
      this.$refs.deptForm.clearValidate()
    },
    deptFormSubmit () {
      this.$refs.deptForm.validate((valid) => {
        if (valid) {
          this.deptLoading = true
          this.deptType === 'add' ? this.addDept() : this.editDept()
        }
      })
    },
    addDept () {
      const addData = {
        pid: this.deptForm.pid,
        deptName: this.deptForm.deptName
      }
      this.$store.dispatch(addDeptAction, addData).then((res) => {
        this.deptLoading = false
        const resCode = res.code
        const errorMsg = res.message
        if (resCode === 0) {
          this.deptFormHide()
          this.fetchDeptTree()
          this.$message({
            type: 'success',
            message: '保存操作成功!'
          })
        } else {
          this.$message({
            type: 'error',
            showClose: true,
            message: errorMsg || '保存数据失败,请稍后再试!'
          })
        }
      }, (error) => {
        this.deptLoading = false
        console.log(error)
      })
    },
    editDept () {
      const updateData = {
        id: this.deptForm.id,
        deptName: this.deptForm.deptName
      }
      this.$store.dispatch(updateDeptAction, updateData).then((res) => {
        this.deptLoading = false
        const resCode = res.code
        const errorMsg = res.message
        if (resCode === 0) {
          this.deptFormHide()
          this.fetchDeptTree()
          this.$message({
            type: 'success',
            message: '编辑操作成功!'
          })
        } else {
          this.$message({
            type: 'error',
            showClose: true,
            message: errorMsg || '编辑数据失败,请稍后再试!'
          })
        }
      }, (error) => {
        this.deptLoading = false
        console.log(error)
      })
    },
    delDeptHandle (node, data) {
      const self = this
      const callback = () => {
        const parent = node.parent
        const children = parent.data.children || parent.data
        const index = children.findIndex(d => d.id === data.id)
        children.splice(index, 1)
      }
      this.$confirm(`是否删除岗位${this.treeObj.deptName}?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          self.treeOption.loading = true
          const idParams = { id: this.treeObj.id }
          return self.$store.dispatch(removeDeptAction, idParams)
        })
        .then((res) => {
          self.treeOption.loading = false
          const resCode = res.code
          const errorMsg = res.message
          if (resCode === 0) {
            self.fetchDeptTree()
            self.$message({
              type: 'success',
              message: '删除操作成功!'
            })
            callback()
          } else {
            this.treeOption.loading = false
            this.$message({
              type: 'error',
              showClose: true,
              message: errorMsg || '删除数据失败,请稍后再试!'
            })
          }
        }).catch(() => {
          this.treeOption.loading = false
        })
    },
    addDeptUserHandle () {
      this.addViewEditForm.deptId = this.treeObj.id
      this.handleAddDlgShow()
    },
    fetchDeptTree () {
      this.treeOption.loading = true
      this.$store.dispatch(getDeptTreeAction).then((res) => {
        const resCode = res.code
        if (resCode === 0) {
          this.treeOption.loading = false
          this.treeOption.data = res.data
          // 设置右边的用户的部门树数据
        }
      }).catch((err) => {
        console.log(err)
        this.treeOption.loading = false
      })
    },
    // --------------搜索部分
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
      this.$message.success('头像更新成功')
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
      this.$store.dispatch(delFileAction, { url: file.url }).then((res) => {
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
    handleDisabledDate (date) {
      return new Date(formatDate(date)) >= new Date()
    },
    fetchRoleList () {
      this.$store.dispatch(getRoleListAction).then((res) => {
        const resCode = res.code
        if (resCode === 0) {
          this.roles = res.data
        }
      })
    },
    fetchUserRoleByUserId (data) {
      this.addViewEditForm.roleId = []
      this.$store.dispatch(getUserRoleByUserIdAction, data).then((res) => {
        const resCode = res.code
        if (resCode === 0) {
          const roleData = res.data
          const roleIds = roleData.map((r) => {
            return r.roleId
          })
          let roleCode = []
          if (roleIds && roleIds[0] > 0) {
            // this.$set(this.form, 'roleId', null)
            roleCode = roleIds
          }
          this.$nextTick(() => {
            this.addViewEditForm.roleId = roleCode
          })
        }
      })
    },
    // 切换用户状态
    changeStatus (row) {
      this.$confirm('确认要' + (row.status ? "'启用'" : "'停用'") + '用户吗?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.table.loading = true
        const idParams = { id: row.id, status: row.status, version: row.version }
        return this.$store.dispatch(updateUserAction, idParams)
      }).then((res) => {
        this.table.loading = false
        const resCode = res.code
        const errorMsg = res.message
        if (resCode === 0) {
          this.fetchTable(this.page)
          this.$message({
            type: 'success',
            message: '用户状态切换成功!'
          })
        } else {
          this.table.loading = false
          row.status = !row.status
          this.$message({
            type: 'error',
            showClose: true,
            message: errorMsg || '切换用户状态失败,请稍后再试!'
          })
        }
      }).catch(() => {
        this.table.loading = false
        row.status = !row.status
      })
    },
    handleSearchFormSubmit () {
      if (this.searchForm.userName === '' &&
          this.searchForm.phone === '' &&
          this.searchForm.email === '') {
        this.$message.warning('搜索条件不能为空!')
        return
      }
      this.$refs.searchForm.validate((valid) => {
        if (valid) {
          this.query = Object.assign(this.query, this.searchForm)
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
    handleResetPasswd () {
      if (this.table.selectList.length === 0) {
        this.$message.warning('请选择至少一条数据')
        return
      }
      this.$confirm(`确定将选择账号密码重置为${this.defaultPasswd}?`, {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.loading = true
          const ids = []
          this.table.selectList.forEach((e) => {
            ids.push(e.id)
          })
          const idParams = { ids }
          return this.$store.dispatch(resetUserPasswdBatchAction, idParams)
        })
        .then((res) => {
          this.loading = false
          const resCode = res.code
          const errorMsg = res.message
          if (resCode === 0) {
            this.$message({
              type: 'success',
              message: '批量重置用户密码操作成功!'
            })
            this.fetchTable(this.page)
            this.handleToggleSelection()
          } else {
            this.loading = false
            this.$message({
              type: 'error',
              showClose: true,
              message: errorMsg || '批量重置用户密码失败,请稍后再试!'
            })
          }
        })
    },
    handleBatchDeleteRow () {
      if (this.table.selectList.length === 0) {
        this.$message.warning('请选择至少一条数据')
        return
      }
      this.$confirm('确定将选择数据删除?', {
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
          return this.$store.dispatch(removeUsersAction, idParams)
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
      this.$store.dispatch(getUserPageListAction, queryData).then((res) => {
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
      this.uploadFileUrl = ''
      this.addViewEditForm = {
        userName: '',
        nickName: '',
        roleId: '',
        deptId: this.treeObj.id || null,
        phone: '',
        email: '',
        sex: 3,
        birth: '',
        avatar: '',
        signature: '',
        address: '',
        status: true
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
      // 获取对应的用户的角色信息
      this.fetchUserRoleByUserId(idParams)
      this.$store.dispatch(getUserByIdAction, idParams).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          this.addViewEditForm = Object.assign(res.data, this.addViewEditForm)
          this.addViewEditForm.deptId = this.addViewEditForm.deptId || ''
          // 设置额外上传组件
          this.uploadFileUrl = res.data.avatar
          this.uploadDisabled = (this.addViewEditDlgType === 'view')
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
      this.$store.dispatch(addUserAction, this.addViewEditForm).then((res) => {
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
      const updateData = Object.assign(this.addViewEditForm, { avatar: this.uploadFileUrl })
      this.$store.dispatch(updateUserAction, updateData).then((res) => {
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
          return this.$store.dispatch(removeUserAction, idParams)
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
    handleRowAuthorizeShow (row, index) {
      this.authorizeBox = true
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
  }
}
</script>

<style scoped>
  .avue-view {
    padding: 0 10px!important;
    width: 100%;
    box-sizing: border-box;
  }
  .label {
    display: flex;
    flex-direction: column;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: initial;
    -webkit-box-pack: justify;
    -ms-flex-pack: justify;
    justify-content: flex-start;
    height: 90px;
    background-color: #fff;
    letter-spacing: 1px;
  }
  .label div {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    font-size: 15px;
    /*color: #008ad3;*/
    -webkit-box-flex: 1;
    -ms-flex: 1;
    flex: 1;
    min-width: 100px;
  }

  /*右键弹出框*/
  .el-context-menu {
    position: absolute;
    z-index: 9999
  }

  .el-context-menu .el-context-menu-box {
    -webkit-box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
    box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
    width: 160px;
    padding: 5px 0;
    background-color: #fff;
    border-radius: 3px;
    position: absolute;
    top: 0
  }

  .el-context-menu .el-context-menu-box._append {
    right: calc(-100% - 5px);
    top: -5px
  }

  .el-context-menu .el-context-menu-box>div {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    height: 35px;
    font-size: 13px;
    cursor: pointer;
    padding: 0 15px;
    color: #666;
    position: relative
  }

  .el-context-menu .el-context-menu-box>div span {
    height: 35px;
    line-height: 35px;
    -webkit-box-flex: 1;
    -ms-flex: 1;
    flex: 1
  }

  .el-context-menu .el-context-menu-box>div:hover {
    background-color: #f7f7f7;
    color: #000
  }

  .el-context-menu .el-context-menu-box>div i:first-child {
    margin-right: 5px
  }

  .el-context-menu .el-context-menu-box>div i:last-child {
    margin-left: 5px
  }

  .el-context-menu .el-context-menu-box>div._active {
    background-color: #f7f7f7;
    color: #000
  }

  .el-context-menu .el-context-menu-box>div._ellipsis span {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap
  }

  .el-context-menu .el-context-menu-box>div._disabled span,.el-context-menu .el-context-menu-box>div._disabled span:hover {
    color: #ccc
  }
  .table-expand {
    font-size: 0;
  }
  .table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
</style>
