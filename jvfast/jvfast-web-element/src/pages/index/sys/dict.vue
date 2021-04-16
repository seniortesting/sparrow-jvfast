<template>
  <el-card-container>
    <el-row class="avue-view">
      <el-col :span="11">
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
                  @click="leftHandleAddDlgShow"
                >
                  新增
                </el-button>
                <el-button
                  type="danger"
                  icon="el-icon-delete"
                  @click="leftHandleBatchDeleteRow"
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
                    @click="leftHandleRefreshTable"
                  />
                </el-tooltip>
              </div>
            </div>
            <!-- 提示信息部分-->
            <el-tag class="avue-crud__tip">
              <span class="avue-crud__tip-name">
                当前表格已选择<span class="avue-crud__tip-count">{{ leftSelectRowLen }}</span>项
              </span>
              <el-button
                type="text"
                size="small"
                @click="leftHandleSelectClear"
              >
                清空
              </el-button>
            </el-tag>
            <!--      表格部分-->
            <el-table
              ref="leftTable"
              v-loading="leftTable.loading"
              :data="leftTable.data"
              :row-key="leftHandleGetRowKeys"
              :class="{'avue-crud--indeterminate': false}"
              size="medium"
              :lazy="false"
              :tree-props="{}"
              :expand-row-keys="leftTable.expandRowKeys"
              :default-expand-all="leftTable.defaultExpandAll"
              :highlight-current-row="leftTable.highlightCurrentRow"
              :show-summary="leftTable.showSummary"
              :stripe="leftTable.stripe"
              :show-header="leftTable.showHeader"
              :default-sort="leftTable.defaultSort"
              :row-style="leftHandleTableRowStyle"
              :cell-style="leftHandleTableCellStyle"
              :fit="leftTable.fit"
              :max-height="leftTable.maxHeight"
              :height="leftTable.height"
              :width="leftTable.width"
              :border="leftTable.border"
              @row-click="leftHandleTableRowClick"
              @selection-change="leftHandleTableSelectionChange"
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
              <template v-for="(column) in leftTable.columns">
                <el-table-column
                  :key="column.prop"
                  :prop="column.prop"
                  :label="column.label"
                  filter-placement="bottom-end"
                  :filters="leftHandleColumnFilters(column)"
                  :filter-method="column.filter? leftHandleFiltersMethod : undefined"
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
                        v-html="leftHandleDetail(scope.row,column)"
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
                    @click.stop="leftHandleRowViewDlgShow(scope.row,scope.$index)"
                  >
                    详情
                  </el-button>
                  <el-button
                    type="text"
                    icon="el-icon-edit"
                    @click.stop="leftHandleRowEditDlgShow(scope.row,scope.$index)"
                  >
                    编辑
                  </el-button>
                  <el-button
                    type="text"
                    icon="el-icon-delete"
                    @click.stop="leftHandleRowDeleteShow(scope.row,scope.$index)"
                  >
                    删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-pagination
              ref="leftTablePage"
              class="avue-crud__pagination"
              :small="$device.isMobile"
              :hide-on-single-page="false"
              :pager-count="leftPage.pagerCount"
              :current-page.sync="leftPage.currentPage"
              :background="leftPage.background"
              :page-size="leftPage.pageSize"
              :page-sizes="leftPage.pageSizes"
              layout="total, sizes, prev, pager, next, jumper"
              :total="leftPage.total"
              @size-change="leftHandlePageSizeChange"
              @current-change="leftHandlePageCurrentChange"
            />
          </div>
          <!--    新增对话框查看对话框编辑对话框-->
          <component
            :is="leftTable.tableDlgType"
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
            :visible.sync="leftAddViewEditDlgShow"
            size="80%"
            width="80%"
            @close="leftHandleAddViewEditDlgClose"
          >
            <div slot="title" class="avue-crud__dialog__header">
              <span class="el-dialog__title">字典{{ leftAddViewEditDlgType==='add'? '新增':(leftAddViewEditDlgType==='edit'? '编辑': '查看') }}</span>
            </div>
            <div
              ref="content"
              :style="{overflow:'hidden'}"
            >
              <el-scrollbar style="height:100%">
                <div class="avue-form" style="width: 100%">
                  <el-form
                    ref="leftAddViewEditForm"
                    v-loading="leftAddViewEditFormSubmitDisable"
                    status-icon
                    :model="leftAddViewEditForm"
                    label-suffix=":"
                    label-position="left"
                    label-width="150px"
                    :rules="leftAddViewEditFormRules"
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
                              label="字典名称"
                              prop="name"
                              class="avue-form__item--left"
                            >
                              <el-tooltip placement="bottom">
                                <div slot="content">
                                  请输入字典名称
                                </div>
                                <el-input
                                  v-model="leftAddViewEditForm.name"
                                  type="text"
                                  :minlength="3"
                                  :maxlength="120"
                                  :show-word-limit="true"
                                  class="avue-input"
                                  :clearable="true"
                                  :disabled="leftAddViewEditDlgType==='view'"
                                  placeholder="请输入字典名称"
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
                              label="备注"
                              prop="remark"
                              class="avue-form__item--left"
                            >
                              <el-tooltip placement="bottom">
                                <div slot="content">
                                  请输入配置备注信息
                                </div>
                                <el-input
                                  v-model="leftAddViewEditForm.remark"
                                  type="textarea"
                                  :minlength="3"
                                  :maxlength="120"
                                  :rows="5"
                                  :show-word-limit="true"
                                  class="avue-input"
                                  :clearable="true"
                                  :disabled="leftAddViewEditDlgType==='view'"
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
                                v-model="leftAddViewEditForm.status"
                                active-text="启用"
                                inactive-text="禁用"
                                :disabled="leftAddViewEditDlgType==='view'"
                                :readonly="leftAddViewEditDlgType==='view'"
                              />
                            </el-form-item>
                          </el-col>
                          <el-col v-if="leftAddViewEditDlgType!=='view'" :span="24" class="avue-form__menu avue-form__menu--right">
                            <el-form-item label-width="0px">
                              <el-button
                                type="primary"
                                icon="el-icon-check"
                                :loading="leftAddViewEditFormSubmitDisable"
                                @click="leftHandleAddViewEditDlgSave"
                              >
                                保存
                              </el-button>
                              <el-button
                                icon="el-icon-delete"
                                :loading="leftAddViewEditFormSubmitDisable"
                                @click="leftHandleAddViewEditDlgClear"
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
      <el-col :span="13">
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
                  @click="rightHandleAddDlgShow"
                >
                  新增
                </el-button>
                <el-button
                  type="danger"
                  icon="el-icon-delete"
                  @click="rightHandleBatchDeleteRow"
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
                    @click="rightHandleRefreshTable"
                  />
                </el-tooltip>
              </div>
            </div>
            <!-- 提示信息部分-->
            <el-tag class="avue-crud__tip">
              <span class="avue-crud__tip-name">
                当前表格已选择<span class="avue-crud__tip-count">{{ rightSelectRowLen }}</span>项
              </span>
              <el-button
                type="text"
                size="small"
                @click="rightHandleSelectClear"
              >
                清空
              </el-button>
            </el-tag>
            <!--      表格部分-->
            <el-table
              ref="rightTable"
              v-loading="rightTable.loading"
              :data="rightTable.data"
              :row-key="rightHandleGetRowKeys"
              :class="{'avue-crud--indeterminate': false}"
              size="medium"
              :lazy="false"
              :tree-props="{}"
              :expand-row-keys="rightTable.expandRowKeys"
              :default-expand-all="rightTable.defaultExpandAll"
              :highlight-current-row="rightTable.highlightCurrentRow"
              :show-summary="rightTable.showSummary"
              :stripe="rightTable.stripe"
              :show-header="rightTable.showHeader"
              :default-sort="rightTable.defaultSort"
              :row-style="rightHandleTableRowStyle"
              :cell-style="rightHandleTableCellStyle"
              :fit="rightTable.fit"
              :max-height="rightTable.maxHeight"
              :height="rightTable.height"
              :width="rightTable.width"
              :border="rightTable.border"
              @row-click="rightHandleTableRowClick"
              @selection-change="rightHandleTableSelectionChange"
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
              <template v-for="(column) in rightTable.columns">
                <el-table-column
                  :key="column.prop"
                  :prop="column.prop"
                  :label="column.label"
                  filter-placement="bottom-end"
                  :filters="rightHandleColumnFilters(column)"
                  :filter-method="column.filter? rightHandleFiltersMethod : undefined"
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
                        v-html="rightHandleDetail(scope.row,column)"
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
                :width="$device.isMobile? 100:220"
              >
                <template slot-scope="scope">
                  <el-button
                    type="text"
                    icon="el-icon-view"
                    @click.stop="rightHandleRowViewDlgShow(scope.row,scope.$index)"
                  >
                    详情
                  </el-button>
                  <el-button
                    type="text"
                    icon="el-icon-edit"
                    @click.stop="rightHandleRowEditDlgShow(scope.row,scope.$index)"
                  >
                    编辑
                  </el-button>
                  <el-button
                    type="text"
                    icon="el-icon-delete"
                    @click.stop="rightHandleRowDeleteShow(scope.row,scope.$index)"
                  >
                    删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-pagination
              ref="rightTablePage"
              class="avue-crud__pagination"
              :small="$device.isMobile"
              :hide-on-single-page="false"
              :pager-count="rightPage.pagerCount"
              :current-page.sync="rightPage.currentPage"
              :background="rightPage.background"
              :page-size="rightPage.pageSize"
              :page-sizes="rightPage.pageSizes"
              layout="total, sizes, prev, pager, next, jumper"
              :total="rightPage.total"
              @size-change="rightHandlePageSizeChange"
              @current-change="rightHandlePageCurrentChange"
            />
          </div>
          <!--    新增对话框查看对话框编辑对话框-->
          <component
            :is="rightTable.tableDlgType"
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
            :visible.sync="rightAddViewEditDlgShow"
            size="60%"
            width="60%"
            @close="rightHandleAddViewEditDlgClose"
          >
            <div slot="title" class="avue-crud__dialog__header">
              <span class="el-dialog__title">{{ rightAddViewEditDlgType==='add'? '新增':(rightAddViewEditDlgType==='edit'? '编辑': '查看') }}</span>
            </div>
            <div
              ref="content"
              :style="{overflow:'hidden'}"
            >
              <el-scrollbar style="height:100%">
                <div class="avue-form" style="width: 100%">
                  <el-form
                    ref="rightAddViewEditForm"
                    v-loading="rightAddViewEditFormSubmitDisable"
                    status-icon
                    :model="rightAddViewEditForm"
                    label-suffix=":"
                    label-position="left"
                    label-width="150px"
                    :rules="rightAddViewEditFormRules"
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
                              label="字典类型"
                              prop="dictTypeId"
                              class="avue-form__item--left"
                            >
                              <el-select
                                ref="selectDictType"
                                v-model="rightAddViewEditForm.dictTypeId"
                                class="avue-select"
                                :multiple="false"
                                :multiple-limit="1"
                                :filterable="false"
                                :allow-create="false"
                                :default-first-option="true"
                                :remote="false"
                                :readonly="false"
                                :clearable="true"
                                placeholder="请选择一个字段类型"
                                :disabled="true"
                              >
                                <el-option
                                  v-for="item in rightDictTypes"
                                  :key="item.id"
                                  :label="item.name"
                                  :value="item.id"
                                />
                              </el-select>
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
                              label="键"
                              prop="dictLabel"
                              class="avue-form__item--left"
                            >
                              <el-tooltip placement="bottom">
                                <div slot="content">
                                  请输入字典键
                                </div>
                                <el-input
                                  v-model="rightAddViewEditForm.dictLabel"
                                  type="text"
                                  :minlength="3"
                                  :maxlength="120"
                                  :show-word-limit="true"
                                  class="avue-input"
                                  :clearable="true"
                                  :disabled="rightAddViewEditDlgType==='view'"
                                  placeholder="请输入字典键"
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
                              label="值"
                              label-width="80px"
                              prop="dictValue"
                              class="avue-form__item--left"
                            >
                              <el-tooltip placement="bottom">
                                <div slot="content">
                                  请输入对应的字典值
                                </div>
                                <el-input
                                  v-model="rightAddViewEditForm.dictValue"
                                  type="text"
                                  :minlength="3"
                                  :maxlength="120"
                                  :show-word-limit="true"
                                  class="avue-input"
                                  :clearable="true"
                                  :disabled="rightAddViewEditDlgType==='view'"
                                  placeholder="请输入对应的字典值"
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
                              label="显示顺序"
                              prop="dictOrder"
                              class="avue-form__item--left"
                            >
                              <el-input-number
                                v-model="rightAddViewEditForm.dictOrder"
                                class="avue-input-number"
                                :precision="0"
                                :min="0"
                                :step="1"
                                :clearable="true"
                                :readonly="rightAddViewEditDlgType==='view'"
                                label="请输入显示顺序"
                                :disabled="rightAddViewEditDlgType==='view'"
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
                                  v-model="rightAddViewEditForm.remark"
                                  type="textarea"
                                  :minlength="3"
                                  :maxlength="120"
                                  :rows="5"
                                  :show-word-limit="true"
                                  class="avue-input"
                                  :clearable="true"
                                  :disabled="rightAddViewEditDlgType==='view'"
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
                                v-model="rightAddViewEditForm.status"
                                active-text="启用"
                                inactive-text="禁用"
                                :disabled="rightAddViewEditDlgType==='view'"
                                :readonly="rightAddViewEditDlgType==='view'"
                              />
                            </el-form-item>
                          </el-col>
                          <el-col v-if="rightAddViewEditDlgType!=='view'" :span="24" class="avue-form__menu avue-form__menu--right">
                            <el-form-item label-width="0px">
                              <el-button
                                type="primary"
                                icon="el-icon-check"
                                :loading="rightAddViewEditFormSubmitDisable"
                                @click="rightHandleAddViewEditDlgSave"
                              >
                                保存
                              </el-button>
                              <el-button
                                icon="el-icon-delete"
                                :loading="rightAddViewEditFormSubmitDisable"
                                @click="rightHandleAddViewEditDlgClear"
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
// 字典类型
const addSysDictTypeAction = 'sys/dict/addSysDictType'
const updateSysDictTypeAction = 'sys/dict/updateSysDictType'
const removeSysDictTypeAction = 'sys/dict/removeSysDictType'
const removeSysDictTypesAction = 'sys/dict/removeSysDictTypes'
const getSysDictTypeByIdAction = 'sys/dict/getSysDictTypeById'
const getSysDictTypePageListAction = 'sys/dict/getSysDictTypePageList'

const getSysDictTypeListAction = 'sys/dict/getSysDictTypeList'
// 字典数据
const addSysDictDataAction = 'sys/dict/addSysDictData'
const updateSysDictDataAction = 'sys/dict/updateSysDictData'
const removeSysDictDataAction = 'sys/dict/removeSysDictData'
const removeSysDictDatasAction = 'sys/dict/removeSysDictDatas'
const getSysDictDataByIdAction = 'sys/dict/getSysDictDataById'
const getSysDictDataPageListAction = 'sys/dict/getSysDictDataPageList'

export default {
  name: 'Dict',
  data () {
    return {
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
      leftTable: {
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
            prop: 'name',
            label: '字典名称',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
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
      leftQuery: {},
      leftPage: {
        total: 0, // 总页数
        pagerCount: 7, // 超过多少条隐藏
        pageNum: 1, // 当前页数
        pageSize: 10, // 每页显示多少条
        pageSizes: [10, 20, 30, 40, 50, 100],
        background: true // 背景颜色
      },
      // 表格弹出对话框
      leftAddViewEditDlgShow: false,
      leftAddViewEditDlgType: 'add',
      leftAddViewEditForm: {
        name: '',
        status: true,
        remark: ''
      },
      leftAddViewEditFormRules: {
        name: [{ required: true, message: '请输入字典名称', trigger: 'blur' }],
        status: [{ required: true, message: '请选择启用关闭状态', trigger: 'blur' }],
        remark: [{ required: false, message: '请输入备注信息', trigger: 'blur' }]
      },
      leftAddViewEditFormSubmitDisable: false,
      // 右侧表
      rightDictTypes: [],
      rightTable: {
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
            prop: 'dictLabel',
            label: '键',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false
          },
          {
            prop: 'dictValue',
            label: '值',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false
          },
          {
            prop: 'dictOrder',
            label: '显示顺序',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
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
      rightQuery: {},
      rightPage: {
        total: 0, // 总页数
        pagerCount: 7, // 超过多少条隐藏
        pageNum: 1, // 当前页数
        pageSize: 10, // 每页显示多少条
        pageSizes: [10, 20, 30, 40, 50, 100],
        background: true // 背景颜色
      },
      // 表格弹出对话框
      rightAddViewEditDlgShow: false,
      rightAddViewEditDlgType: 'add',
      rightAddViewEditForm: {
        dictTypeId: '',
        dictLabel: '',
        dictValue: '',
        dictOrder: 1,
        status: true,
        remark: ''
      },
      rightAddViewEditFormRules: {
        dictTypeId: [{ required: true, message: '请选择一个字段类型', trigger: 'blur' }],
        dictLabel: [{ required: true, message: '请输入字典键', trigger: 'blur' }],
        dictValue: [{ required: true, message: '请输入对应的字典值', trigger: 'blur' }],
        dictOrder: [{ required: true, message: '请输入显示顺序', trigger: 'blur' }],
        status: [{ required: true, message: '请选择启用关闭状态', trigger: 'blur' }],
        remark: [{ required: false, message: '请输入备注信息', trigger: 'blur' }]
      },
      rightAddViewEditFormSubmitDisable: false
    }
  },
  computed: {
    ...mapGetters(['permissions', 'website', 'userInfo']),
    leftSelectRowLen () {
      return this.leftTable.selectList ? this.leftTable.selectList.length : 0
    },
    rightSelectRowLen () {
      return this.rightTable.selectList ? this.rightTable.selectList.length : 0
    }
  },
  watch: {
    'leftPage.total': {
      handler (value) {
        // 如果当前页面删除没数据了调用第一页
        if (this.leftPage.total === (this.leftPage.currentPage - 1) * this.leftPage.pageSize && this.leftPage.total !== 0) {
          this.leftPage.currentPage = this.leftPage.currentPage - 1
          this.fetchTable(this.leftPage)
        }
      }
    },
    'rightPage.total': {
      handler (value) {
        // 如果当前页面删除没数据了调用第一页
        if (this.rightPage.total === (this.rightPage.currentPage - 1) * this.rightPage.pageSize && this.rightPage.total !== 0) {
          this.rightPage.currentPage = this.rightPage.currentPage - 1
          this.fetchTable(this.rightPage)
        }
      }
    }
  },
  mounted () {
    // 设置表单
    const self = this
    this.$nextTick(() => {
      const clientHeight = document.documentElement.clientHeight
      if (this.leftPage.height === 'auto') {
        this.$nextTick(() => {
          const tableStyle = self.$refs.leftTable.$el
          const pageStyle = self.$refs.tablePage.$el
          self.leftPage.height = clientHeight - tableStyle.offsetTop - (pageStyle.offsetHeight * 3) - self.calcHeight
        })
      }
    })
    this.leftFetchTable(this.leftPage)
    this.rightFetchTable(this.rightPage)
    this.fetchDictTypeList()
  },
  methods: {
    fetchDictTypeList () {
      this.$store.dispatch(getSysDictTypeListAction).then((res) => {
        // 设置右边的字段类型选择框数据
        this.rightDictTypes = res.data
      }).catch((err) => {
        console.log(err)
      })
    },
    // --------------工具条
    leftHandleBatchDeleteRow () {
      if (this.leftTable.selectList.length === 0) {
        this.$message.warning('请选择至少一条数据')
        return
      }
      const ids = []
      this.leftTable.selectList.forEach((e) => {
        ids.push(e.id)
      })
      this.$confirm('确定将选择数据删除?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.leftTable.loading = true
          const idParams = { ids }
          return this.$store.dispatch(removeSysDictTypesAction, idParams)
        })
        .then((res) => {
          this.leftTable.loading = false
          const resCode = res.code
          const message = res.message
          if (resCode === 0) {
            this.$message({
              type: 'success',
              message: '批量删除操作成功!'
            })
            this.leftFetchTable(this.leftPage)
            this.leftHandleToggleSelection()
          } else {
            this.leftTable.loading = false
            this.$message({
              type: 'error',
              message: message || '批量删除数据失败,请稍后再试!'
            })
          }
        }).catch(() => {
          this.leftTable.loading = false
        })
    },
    leftHandleRefreshTable () {
      this.leftFetchTable(this.leftPage)
      this.rightQuery = {}
      this.rightFetchTable(this.rightPage)
    },
    // --------------表格顶部提示部分
    leftHandleSelectClear () {
      this.leftTable.selectList = []
      this.$refs.leftTable.clearSelection()
    },
    // -------------- 表格列表
    leftFetchTable (page, params = {}) {
      this.leftTable.loading = true
      let queryData = {
        pageNo: page.currentPage,
        pageSize: page.pageSize
      }
      queryData = Object.assign(queryData, Object.assign(params, this.leftQuery))
      this.$store.dispatch(getSysDictTypePageListAction, queryData).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          const data = res.data
          this.leftPage.total = parseInt(data.total)
          this.leftTable.data = data.records
          this.leftHandleSelectClear()
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
        this.leftTable.loading = false
      })
    },
    leftHandleGetRowKeys (row) {
      const rowKey = row[this.leftTable.rowKey]
      return rowKey
    },
    leftHandleTableRowStyle ({ row, column, rowIndex }) {
      return {}
    },
    leftHandleTableCellStyle ({ row, column, rowIndex, columnIndex }) {
      // return {
      //   color: 'green',
      //   fontWeight: 'bold',
      //   fontSize: '20'
      // }
      return {}
    },
    leftHandleTableRowClick (row, event, column) {
      this.leftTable.clickRow = row
      this.rightQuery = {
        dictTypeId: row.id
      }
      this.rightFetchTable(this.rightPage, this.rightQuery)
    },
    // 选中实例
    leftHandleToggleSelection (rows) {
      if (rows) {
        rows.forEach((row) => {
          this.$refs.leftTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.leftTable.clearSelection()
      }
    },
    leftHandleTableSelectionChange (selection) {
      // 单行或多行选择回调
      this.leftTable.selectList = selection
    },
    // ************ 序列号
    leftHandleIndexMethod (index) {
      return (
        index +
        1 +
        ((this.leftPage.currentPage || 1) - 1) *
        (this.leftPage.pageSize || 10)
      )
    },
    // ************ 点击列过滤
    leftHandleFiltersMethod (value, row, column) {
      const columnNew = this.leftTable.columns.filter(
        ele => ele.prop === column.property
      )[0]
      if (typeof columnNew.filtersMethod === 'function') {
        return columnNew.filtersMethod(value, row, columnNew)
      } else {
        return row[columnNew.prop] === value
      }
    },
    leftHandleColumnFilters (column) {
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
    leftHandleDetail (row, column) {
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
    leftHandleAddDlgShow () {
      this.leftAddViewEditDlgShow = true
      this.leftAddViewEditDlgType = 'add'
      // 此处设置默认值
      this.leftAddViewEditForm = {
        status: true
      }
    },
    leftHandleAddViewEditDlgClose (row) {
      if (row) {
        row = {}
      }
      this.leftAddViewEditDlgShow = false
      this.leftFetchTable(this.leftPage)
    },
    leftHandleRowViewDlgShow (row, index) {
      // 显示弹出框
      this.leftAddViewEditDlgShow = true
      this.leftAddViewEditDlgType = 'view'
      this.leftAddViewEditForm = row
      // 获取对应的数据
      this.leftFetchRowByRowId(row)
    },
    leftHandleRowEditDlgShow (row, index) {
      // 显示弹出框
      this.leftAddViewEditDlgShow = true
      this.leftAddViewEditDlgType = 'edit'
      this.leftAddViewEditForm = row
      // 获取对应的数据
      this.leftFetchRowByRowId(row)
    },
    leftFetchRowByRowId (row) {
      const idParams = { id: row.id }
      this.$store.dispatch(getSysDictTypeByIdAction, idParams).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          this.leftAddViewEditForm = res.data
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
    leftHandleAddViewEditDlgSave () {
      this.$refs.leftAddViewEditForm.validate((valid) => {
        if (valid) {
          if (this.leftAddViewEditDlgType === 'add') {
            this.leftHandleRowSave()
          } else if (this.leftAddViewEditDlgType === 'edit') {
            this.leftHandleRowUpdate()
          }
        }
      })
    },
    leftHandleAddViewEditDlgClear () {
      this.$refs.leftAddViewEditForm.resetFields()
      this.$emit('input', this.leftAddViewEditForm)
      this.rightQuery = {}
      this.rightFetchTable(this.rightPage)
    },
    leftHandleRowSave () {
      this.leftAddViewEditFormSubmitDisable = true
      this.leftAddViewEditForm.createBy = this.userInfo.id
      this.$store.dispatch(addSysDictTypeAction, this.leftAddViewEditForm).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          this.leftHandleAddViewEditDlgClose(this.leftAddViewEditForm)
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
        this.leftAddViewEditFormSubmitDisable = false
      })
    },
    leftHandleRowUpdate () {
      this.leftAddViewEditFormSubmitDisable = true
      this.leftAddViewEditForm.updateBy = this.userInfo.id
      this.$store.dispatch(updateSysDictTypeAction, this.leftAddViewEditForm).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          this.leftHandleAddViewEditDlgClose(this.leftAddViewEditForm)
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
        this.leftAddViewEditFormSubmitDisable = false
      })
    },
    leftHandleRowDeleteShow (row) {
      this.$confirm('确定将选择数据删除?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.leftTable.loading = true
          const idParams = { id: row.id }
          return this.$store.dispatch(removeSysDictTypeAction, idParams)
        })
        .then((res) => {
          this.leftTable.loading = false
          const resCode = res.code
          const message = res.message
          if (resCode === 0) {
            this.leftHandleAddViewEditDlgClose(this.leftAddViewEditForm)
            this.$message({
              type: 'success',
              message: '删除操作成功!'
            })
          } else {
            this.leftTable.loading = false
            this.$message({
              type: 'error',
              message: message || '删除数据失败,请稍后再试!'
            })
          }
        }).catch(() => {
          this.leftTable.loading = false
        })
    },
    // -------------- 分页操作
    leftHandlePageSizeChange (value) {
      // 页大小回调,10,20
      this.leftPage.currentPage = 1
      this.leftPage.pageSize = value
      this.fetchTable(this.leftPage)
    },
    leftHandlePageCurrentChange (value) {
      // 页码回调
      this.leftPage.currentPage = value
      this.leftFetchTable(this.leftPage)
    },
    // 右侧表数据
    rightHandleBatchDeleteRow () {
      if (this.rightTable.selectList.length === 0) {
        this.$message.warning('请选择至少一条数据')
        return
      }
      const ids = []
      this.rightTable.selectList.forEach((e) => {
        ids.push(e.id)
      })
      this.$confirm('确定将选择数据删除?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.rightTable.loading = true
          const idParams = { ids }
          return this.$store.dispatch(removeSysDictDatasAction, idParams)
        })
        .then((res) => {
          this.rightTable.loading = false
          const resCode = res.code
          const message = res.message
          if (resCode === 0) {
            this.$message({
              type: 'success',
              message: '批量删除操作成功!'
            })
            this.rightFetchTable(this.rightPage)
            this.rightHandleToggleSelection()
          } else {
            this.rightTable.loading = false
            this.$message({
              type: 'error',
              message: message || '批量删除数据失败,请稍后再试!'
            })
          }
        }).catch(() => {
          this.rightTable.loading = false
        })
    },
    rightHandleRefreshTable () {
      this.rightFetchTable(this.rightPage)
    },
    // --------------表格顶部提示部分
    rightHandleSelectClear () {
      this.rightTable.selectList = []
      this.$refs.rightTable.clearSelection()
    },
    // -------------- 表格列表
    rightFetchTable (page, params = {}) {
      this.rightTable.loading = true
      let queryData = {
        pageNo: page.currentPage,
        pageSize: page.pageSize
      }
      queryData = Object.assign(queryData, Object.assign(params, this.rightQuery))
      this.$store.dispatch(getSysDictDataPageListAction, queryData).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          const data = res.data
          this.rightPage.total = parseInt(data.total)
          this.rightTable.data = data.records
          this.rightHandleSelectClear()
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
        this.rightTable.loading = false
      })
    },
    rightHandleGetRowKeys (row) {
      const rowKey = row[this.rightTable.rowKey]
      return rowKey
    },
    rightHandleTableRowStyle ({ row, column, rowIndex }) {
      return {}
    },
    rightHandleTableCellStyle ({ row, column, rowIndex, columnIndex }) {
      // return {
      //   color: 'green',
      //   fontWeight: 'bold',
      //   fontSize: '20'
      // }
      return {}
    },
    rightHandleTableRowClick (row, event, column) {
      this.rightTable.clickRow = row
    },
    // 选中实例
    rightHandleToggleSelection (rows) {
      if (rows) {
        rows.forEach((row) => {
          this.$refs.rightTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.rightTable.clearSelection()
      }
    },
    rightHandleTableSelectionChange (selection) {
      // 单行或多行选择回调
      this.rightTable.selectList = selection
    },
    // ************ 序列号
    rightHandleIndexMethod (index) {
      return (
        index +
        1 +
        ((this.rightPage.currentPage || 1) - 1) *
        (this.rightPage.pageSize || 10)
      )
    },
    // ************ 点击列过滤
    rightHandleFiltersMethod (value, row, column) {
      const columnNew = this.rightTable.columns.filter(
        ele => ele.prop === column.property
      )[0]
      if (typeof columnNew.filtersMethod === 'function') {
        return columnNew.filtersMethod(value, row, columnNew)
      } else {
        return row[columnNew.prop] === value
      }
    },
    rightHandleColumnFilters (column) {
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
    rightHandleDetail (row, column) {
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
    rightHandleAddDlgShow () {
      if (this.leftTable.clickRow.id) {
        this.rightAddViewEditDlgShow = true
        this.rightAddViewEditDlgType = 'add'
        // 此处设置默认值
        this.rightAddViewEditForm = {
          status: true,
          dictOrder: 1,
          dictTypeId: this.leftTable.clickRow.id
        }
      } else {
        this.$message.warning('请先选择左侧的一个字典记录！')
      }
    },
    rightHandleAddViewEditDlgClose (row) {
      if (row) {
        row = {}
      }
      this.rightAddViewEditDlgShow = false
      this.rightFetchTable(this.rightPage)
    },
    rightHandleRowViewDlgShow (row, index) {
      // 显示弹出框
      this.rightAddViewEditDlgShow = true
      this.rightAddViewEditDlgType = 'view'
      this.rightAddViewEditForm = row
      // 获取对应的数据
      this.rightFetchRowByRowId(row)
    },
    rightHandleRowEditDlgShow (row, index) {
      // 显示弹出框
      this.rightAddViewEditDlgShow = true
      this.rightAddViewEditDlgType = 'edit'
      this.rightAddViewEditForm = row
      // 获取对应的数据
      this.rightFetchRowByRowId(row)
    },
    rightFetchRowByRowId (row) {
      const idParams = { id: row.id }
      this.$store.dispatch(getSysDictDataByIdAction, idParams).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          this.rightAddViewEditForm = res.data
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
    rightHandleAddViewEditDlgSave () {
      this.$refs.rightAddViewEditForm.validate((valid) => {
        if (valid) {
          if (this.rightAddViewEditDlgType === 'add') {
            this.rightHandleRowSave()
          } else if (this.rightAddViewEditDlgType === 'edit') {
            this.rightHandleRowUpdate()
          }
        }
      })
    },
    rightHandleAddViewEditDlgClear () {
      this.$refs.rightAddViewEditForm.resetFields()
      this.$emit('input', this.rightAddViewEditForm)
    },
    rightHandleRowSave () {
      this.rightAddViewEditFormSubmitDisable = true
      this.rightAddViewEditForm.createBy = this.userInfo.id
      this.$store.dispatch(addSysDictDataAction, this.rightAddViewEditForm).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          this.rightHandleAddViewEditDlgClose(this.rightAddViewEditForm)
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
        this.rightAddViewEditFormSubmitDisable = false
      })
    },
    rightHandleRowUpdate () {
      this.rightAddViewEditFormSubmitDisable = true
      this.rightAddViewEditForm.updateBy = this.userInfo.id
      this.$store.dispatch(updateSysDictDataAction, this.rightAddViewEditForm).then((res) => {
        const resCode = res.code
        const message = res.message
        if (resCode === 0) {
          this.rightHandleAddViewEditDlgClose(this.rightAddViewEditForm)
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
        this.rightAddViewEditFormSubmitDisable = false
      })
    },
    rightHandleRowDeleteShow (row) {
      this.$confirm('确定将选择数据删除?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.rightTable.loading = true
          const idParams = { id: row.id }
          return this.$store.dispatch(removeSysDictDataAction, idParams)
        })
        .then((res) => {
          this.rightTable.loading = false
          const resCode = res.code
          const message = res.message
          if (resCode === 0) {
            this.rightHandleAddViewEditDlgClose(this.rightAddViewEditForm)
            this.$message({
              type: 'success',
              message: '删除操作成功!'
            })
          } else {
            this.rightTable.loading = false
            this.$message({
              type: 'error',
              message: message || '删除数据失败,请稍后再试!'
            })
          }
        }).catch(() => {
          this.rightTable.loading = false
        })
    },
    // -------------- 分页操作
    rightHandlePageSizeChange (value) {
      // 页大小回调,10,20
      this.rightPage.currentPage = 1
      this.rightPage.pageSize = value
      this.fetchTable(this.rightPage)
    },
    rightHandlePageCurrentChange (value) {
      // 页码回调
      this.rightPage.currentPage = value
      this.rightFetchTable(this.rightPage)
    }
  },
  head () {
    return {
      title: '字典管理 | ' + this.website.name,
      meta: [
        { hid: 'description', name: 'description', content: '字典管理' }
      ]
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
</style>
