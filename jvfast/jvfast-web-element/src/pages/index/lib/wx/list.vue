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
            size="medium"
            icon="el-icon-menu"
            @click="customMenuHandle"
          >
            自定义菜单
          </el-button>
          <el-button
            type="primary"
            size="medium"
            icon="el-icon-user"
            @click="fansListDlgHandle"
          >
            关注粉丝列表
          </el-button>
          <!--          <el-button-->
          <!--            type="primary"-->
          <!--            size="medium"-->
          <!--            icon="el-icon-notebook-2"-->
          <!--            @click="handleArticlePublish"-->
          <!--          >-->
          <!--            文章发布-->
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
        <el-table-column
          type="index"
          label="序号"
          :width="50"
          :index="handleIndexMethod"
          fixed="left"
          align="center"
        />
        <el-table-column width="1px" />
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
              icon="el-icon-menu"
              @click.stop="customMenuRowHandle(scope.row,scope.$index)"
            >
              自定义菜单
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
        <span class="el-dialog__title">公众号信息{{ addViewEditDlgType==='add'? '新增':(addViewEditDlgType==='edit'? '编辑': '查看') }}</span>
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
                        label="公众号名称"
                        prop="name"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入公众号名称
                          </div>
                          <el-input
                            v-model="addViewEditForm.name"
                            type="text"
                            :minlength="3"
                            :maxlength="120"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            :disabled="addViewEditDlgType==='view'"
                            placeholder="请输入公众号名称"
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
                        label="appId"
                        prop="appId"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入公众号AppId
                          </div>
                          <el-input
                            v-model="addViewEditForm.appId"
                            type="text"
                            :minlength="3"
                            :maxlength="120"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            :disabled="addViewEditDlgType==='view'"
                            placeholder="请输入公众号AppId"
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
                        label="secret"
                        prop="secret"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入公众号secret
                          </div>
                          <el-input
                            v-model="addViewEditForm.secret"
                            type="text"
                            :minlength="3"
                            :maxlength="120"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            :disabled="addViewEditDlgType==='view'"
                            placeholder="请输入公众号secret"
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
                        label="token"
                        prop="token"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入公众号token
                          </div>
                          <el-input
                            v-model="addViewEditForm.token"
                            type="text"
                            :minlength="3"
                            :maxlength="120"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            :disabled="addViewEditDlgType==='view'"
                            placeholder="请输入公众号token"
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
                        label="aesKey"
                        prop="aesKey"
                        class="avue-form__item--left"
                      >
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入公众号aesKey
                          </div>
                          <el-input
                            v-model="addViewEditForm.aesKey"
                            type="text"
                            :minlength="3"
                            :maxlength="120"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            :disabled="addViewEditDlgType==='view'"
                            placeholder="请输入公众号aesKey"
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
    <!--    自定义菜单弹出框-->
    <el-dialog
      :visible.sync="menuDlgShow"
      :fullscreen="false"
      title="公众号菜单设置"
      modal-append-to-body
      append-to-body
      width="70%"
      @close="handleMenuClose"
    >
      <div>
        <div id="app-menu">
          <!-- 预览窗 -->
          <div class="weixin-preview">
            <div class="weixin-bd">
              <ul id="weixin-menu" class="weixin-menu">
                <li
                  v-for="(btn,i) in mpMenu.buttons"
                  :key="i"
                  :class="{current:selectedMenuIndex===i&&selectedMenuLevel==1}"
                  class="menu-item"
                  @click="selectMenu(i)"
                >
                  <div class="menu-item-title">
                    <span>{{ btn.name }}</span>
                  </div>
                  <ul v-show="selectedMenuIndex===i" class="weixin-sub-menu">
                    <li
                      v-for="(sub,i2) in btn.subButtons"
                      :key="i2"
                      :class="{current:selectedSubMenuIndex===i2&&selectedMenuLevel==2}"
                      class="menu-sub-item"
                      @click.stop="selectSubMenu(i2)"
                    >
                      <div class="menu-item-title">
                        <span>{{ sub.name }}</span>
                      </div>
                    </li>
                    <li v-if="btn.subButtons.length<5" class="menu-sub-item" @click.stop="addMenu(2)">
                      <div class="menu-item-title">
                        <i class="el-icon-plus" />
                      </div>
                    </li>
                    <i class="menu-arrow arrow_out" />
                    <i class="menu-arrow arrow_in" />
                  </ul>
                </li>
                <li v-if="mpMenu.buttons.length<3" class="menu-item" @click="addMenu(1)">
                  <i class="el-icon-plus" />
                </li>
              </ul>
            </div>
          </div>
          <!-- 菜单编辑器 -->
          <div v-if="selectedMenuLevel>0" class="weixin-menu-detail">
            <wx-menu-button-editor :button="selectedButton" :selected-menu-level="selectedMenuLevel" @delMenu="delMenu" />
          </div>
        </div>
        <div class="weixin-btn-group" @click="updateWxMenu">
          <el-button type="success" icon="el-icon-upload">
            发布
          </el-button>
          <el-button type="warning" icon="el-icon-delete" @click="delMenu">
            清空
          </el-button>
        </div>
      </div>
    </el-dialog>
    <!--    粉丝列表弹出框-->
    <el-dialog
      :visible.sync="fansDlgShow"
      title="公众号关注粉丝列表"
      modal-append-to-body
      append-to-body
      fullscreen
      @close="fansDlgCloseHandle"
    >
      <!--    列表组件-->
      <div class="avue-crud">
        <!-- 表格功能列 -->
        <div class="avue-crud__menu">
          <div class="avue-crud__left">
            <el-button
              type="primary"
              icon="el-icon-plus"
            >
              导出
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
                @click="handleRefreshFansTable"
              />
            </el-tooltip>
          </div>
        </div>
        <!-- 提示信息部分-->
        <el-tag class="avue-crud__tip">
          <span class="avue-crud__tip-name">
            当前表格已选择<span class="avue-crud__tip-count">{{ fansSelectRowLen }}</span>项
          </span>
          <el-button
            type="text"
            size="small"
            @click="handleFansSelectClear"
          >
            清空
          </el-button>
        </el-tag>
        <!--      表格部分-->
        <el-table
          ref="fansTable"
          v-loading="fansTable.loading"
          :data="fansTable.data"
          :row-key="handleGetFansRowKeys"
          :class="{'avue-crud--indeterminate': false}"
          size="medium"
          :lazy="false"
          :tree-props="{}"
          :expand-row-keys="fansTable.expandRowKeys"
          :default-expand-all="fansTable.defaultExpandAll"
          :highlight-current-row="fansTable.highlightCurrentRow"
          :show-summary="fansTable.showSummary"
          :stripe="fansTable.stripe"
          :show-header="fansTable.showHeader"
          :default-sort="fansTable.defaultSort"
          :row-style="handleFansTableRowStyle"
          :cell-style="handleFansTableCellStyle"
          :fit="fansTable.fit"
          :max-height="fansTable.maxHeight"
          :height="fansTable.height"
          :width="fansTable.width"
          :border="fansTable.border"
          @row-click="handleFansTableRowClick"
          @selection-change="handleFansTableSelectionChange"
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
          <!--        主体部分-->
          <template v-for="(column) in fansTable.columns">
            <el-table-column
              :key="column.prop"
              :prop="column.prop"
              :label="column.label"
              filter-placement="bottom-end"
              :filters="handleFansColumnFilters(column)"
              :filter-method="column.filter? handleFansFiltersMethod : undefined"
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
                    v-html="handleFansDetail(scope.row,column)"
                  />
                </span>
              </template>
            </el-table-column>
          </template>
          <!--        菜单栏部分-->
        </el-table>
        <el-pagination
          ref="tablePage"
          class="avue-crud__pagination"
          :small="$device.isMobile"
          :hide-on-single-page="false"
          :pager-count="fansPage.pagerCount"
          :current-page.sync="fansPage.currentPage"
          :background="fansPage.background"
          :page-size="fansPage.pageSize"
          :page-sizes="fansPage.pageSizes"
          layout="total, sizes, prev, pager, next, jumper"
          :total="fansPage.total"
          @size-change="handleFansPageSizeChange"
          @current-change="handleFansPageCurrentChange"
        />
      </div>
    </el-dialog>
  </el-card-container>
</template>

<script>
import { mapGetters } from 'vuex'
import { formatDateTime } from '@/util/DateUtil'

const getWxMpPageListAction = 'lib/wx/getWxMpPageList'
const updateWxMpMenuAction = 'lib/wx/updateWxMpMenu'
const listWxMpMenuAction = 'lib/wx/listWxMpMenu'
const getWxMpUserPageListAction = 'lib/wx/listWxMpUser'
export default {
  name: 'WX',
  components: {
    wxMenuButtonEditor: () => import('./-menu')
  },
  data () {
    return {
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
            prop: 'name',
            label: '公众号名称',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false
          },
          {
            prop: 'appId',
            label: 'appId',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '200',
            fixed: false
          },
          {
            prop: 'secret',
            label: 'secret',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false
          },
          {
            prop: 'token',
            label: 'token',
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
        name: '',
        appId: '',
        secret: '',
        token: '',
        aesKey: ''
      },
      addViewEditFormRules: {
        name: [{ required: true, message: '请输入公众号名称', trigger: 'blur' }],
        appId: [{ required: true, message: '请输入公众的appId', trigger: 'blur' }],
        secret: [{ required: true, message: '请输入公众号的secret', trigger: 'blur' }]
      },
      addViewEditFormSubmitDisable: false,
      // 表格弹出对话框
      menuDlgShow: false,
      mpMenu: { buttons: [] }, // 当前菜单
      selectedMenuIndex: '', // 当前选中菜单索引
      selectedSubMenuIndex: '', // 当前选中子菜单索引
      selectedMenuLevel: 0, // 选中菜单级别
      selectedButton: '', // 选中的菜单按钮
      appId: null,
      // 粉丝表
      fansDlgShow: false,
      fansTable: {
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
            prop: 'headImgUrl',
            label: '头像',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false,
            type: 'img'
          },
          {
            prop: 'nickname',
            label: '昵称',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: '200',
            fixed: false
          },
          {
            prop: 'openId',
            label: 'openid',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false
          },
          {
            prop: 'sexDesc',
            label: '性别',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false
          },
          {
            prop: 'city',
            label: '城市',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false,
            formatter: this.formatUserCity
          },
          {
            prop: 'subscribeTime',
            label: '订阅时间',
            minWidth: 'auto',
            sortable: false,
            align: 'center',
            headerAlign: 'center',
            width: 'auto',
            fixed: false,
            formatter: this.formatUserDateTime
          }
        ],
        selectList: [],
        clickRow: {}
      },
      fansQuery: { nextOpenid: null },
      fansPage: {
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
    ...mapGetters(['permissions', 'website']),
    selectRowLen () {
      return this.table.selectList ? this.table.selectList.length : 0
    },
    fansSelectRowLen () {
      return this.fansTable.selectList ? this.fansTable.selectList.length : 0
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
    },
    'fansPage.total': {
      handler (value) {
        // 如果当前页面删除没数据了调用第一页
        if (this.fansPage.total === (this.fansPage.currentPage - 1) * this.fansPage.pageSize && this.fansPage.total !== 0) {
          this.fansPage.currentPage = this.fansPage.currentPage - 1
          this.fetchTable(this.fansPage)
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
      this.$store.dispatch(getWxMpPageListAction, queryData).then((res) => {
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
      // const idParams = { id: row.id }
      // this.$store.dispatch(getCvrProjectTaskByIdAction, idParams).then((res) => {
      //   const resCode = res.code
      //   const message = res.message
      //   if (resCode === 0) {
      //     this.addViewEditForm = res.data
      //   } else {
      //     this.$message({
      //       type: 'error',
      //       message: message || '查询数据失败,请稍后再试!'
      //     })
      //   }
      // }).catch((err) => {
      //   console.log(err)
      //   this.$message({
      //     type: 'error',
      //     message: '查询数据异常,请稍后再试!'
      //   })
      // })
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
    // ------------开始菜单设置
    customMenuHandle () {
      if (this.table.selectList.length !== 1) {
        this.$message.warning('请选择一个公众号且只能选择一个公众号!')
        return
      }
      this.menuDlgShow = true
      this.mpMenu = { buttons: [] }
      this.appId = this.table.selectList[0].appId
      this.getWxMenu()
    },
    customMenuRowHandle (row) {
      this.menuDlgShow = true
      this.mpMenu = { buttons: [] }
      this.appId = row.appId
      this.getWxMenu()
    },
    handleMenuClose () {
      this.menuDlgShow = false
    },
    getWxMenu () {
      this.$store.dispatch(listWxMpMenuAction, { appId: this.appId })
        .then((res) => {
          if (res.code === 0) {
            this.mpMenu = res.data.menu || { buttons: [] }
          } else {
            const message = res.message
            this.$message({
              type: 'error',
              showClose: true,
              message
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
    },
    // 选中主菜单
    selectMenu (i) {
      this.selectedMenuLevel = 1
      this.selectedSubMenuIndex = ''
      this.selectedMenuIndex = i
      this.selectedButton = this.mpMenu.buttons[i]
    },
    // 选中子菜单
    selectSubMenu (i) {
      this.selectedMenuLevel = 2
      this.selectedSubMenuIndex = i
      this.selectedButton = this.mpMenu.buttons[this.selectedMenuIndex].subButtons[i]
    },
    // 添加菜单
    addMenu (level) {
      if (level === 1 && this.mpMenu.buttons.length < 3) {
        this.mpMenu.buttons.push({
          type: 'view',
          name: '菜单名称',
          subButtons: [],
          url: ''
        })
        this.selectMenu(this.mpMenu.buttons.length - 1)
      }
      if (level === 2 && this.mpMenu.buttons[this.selectedMenuIndex].subButtons.length < 5) {
        this.mpMenu.buttons[this.selectedMenuIndex].subButtons.push({
          type: 'view',
          name: '子菜单名称',
          url: ''
        })
        this.selectSubMenu(this.mpMenu.buttons[this.selectedMenuIndex].subButtons.length - 1)
      }
    },
    // 删除菜单
    delMenu () {
      if (this.selectedMenuLevel === 1 && confirm('删除后菜单下设置的内容将被删除')) {
        this.mpMenu.buttons.splice(this.selectedMenuIndex, 1)

        this.selectedMenuLevel = 0// 删除主菜单后不选中任何菜单
        this.selectedMenuIndex = ''
        this.selectedSubMenuIndex = ''
        this.selectedButton = ''
      } else if (this.selectedMenuLevel === 2) {
        this.mpMenu.buttons[this.selectedMenuIndex].subButtons.splice(this.selectedSubMenuIndex, 1)

        this.selectedMenuLevel = 1// 删除后选中主菜单不选子菜单
        this.selectedSubMenuIndex = ''
        this.selectedButton = this.mpMenu.buttons[this.selectedMenuIndex]
      }
    },
    updateWxMenu () {
      this.$store.dispatch(updateWxMpMenuAction, { appId: this.appId, menu: this.mpMenu })
        .then((res) => {
          if (res.code === 0) {
            this.menuDlgShow = false
            this.$message({
              message: '操作成功',
              type: 'success'
            })
          } else {
            const message = res.message
            this.$message({
              type: 'error',
              showClose: true,
              message
            })
          }
        }).catch((err) => {
          this.$message({
            type: 'error',
            showClose: true,
            message: err
          })
        })
    },
    // ---------------开始粉丝列表
    fansListDlgHandle () {
      if (this.table.selectList.length !== 1) {
        this.$message.warning('请选择一个公众号')
        return
      }
      this.appId = this.table.selectList[0].appId
      this.fansQuery.nextOpenid = null
      this.fansDlgShow = true
      this.fetchFansTable(this.fansPage)
    },
    fansDlgCloseHandle () {
      this.fansDlgShow = false
    },
    handleRefreshFansTable () {
      this.fansQuery.nextOpenid = null
      this.fetchFansTable(this.fansPage)
    },
    handleFansSelectClear () {
      this.fansTable.selectList = []
      this.$refs.fansTable.clearSelection()
    },
    handleGetFansRowKeys (row) {
      const rowKey = row[this.fansTable.rowKey]
      return rowKey
    },
    handleFansTableRowStyle ({ row, column, rowIndex }) {
      return {}
    },
    handleFansTableCellStyle ({ row, column, rowIndex, columnIndex }) {
      // return {
      //   color: 'green',
      //   fontWeight: 'bold',
      //   fontSize: '20'
      // }
      return {}
    },
    handleFansTableRowClick (row, event, column) {
      this.fansTable.clickRow = row
    },
    handleFansTableSelectionChange (selection) {
      // 单行或多行选择回调
      this.fansTable.selectList = selection
    },
    // ************ 点击列过滤
    handleFansFiltersMethod (value, row, column) {
      const columnNew = this.table.columns.filter(
        ele => ele.prop === column.property
      )[0]
      if (typeof columnNew.filtersMethod === 'function') {
        return columnNew.filtersMethod(value, row, columnNew)
      } else {
        return row[columnNew.prop] === value
      }
    },
    handleFansColumnFilters (column) {
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
    handleFansDetail (row, column) {
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
    formatUserCity (row, value, label, column) {
      return row.country + ', ' + row.province + ', ' + row.city
    },
    formatUserDateTime (row, value, label, column) {
      return formatDateTime(row.subscribeTime * 1000)
    },
    handleFansPageSizeChange (currentPage) {
      this.fansPage.currentPage = currentPage
    },
    handleFansPageCurrentChange (pageSize) {
      this.fansPage.pageSize = pageSize
    },
    fetchFansTable (page, params = {}) {
      this.fansTable.loading = true
      let queryData = {
        pageNo: page.currentPage,
        pageSize: page.pageSize
      }
      queryData = Object.assign(queryData, Object.assign(params, this.fansQuery))
      this.$store.dispatch(getWxMpUserPageListAction, { appId: this.appId, query: queryData }).then((res) => {
        this.fansTable.loading = false
        const resCode = res.code
        const errorMsg = res.message
        if (resCode === 0) {
          const data = res.data
          this.fansPage.total = parseInt(data.total)
          this.fansTable.data = data.records
          this.fansQuery.nextOpenid = data.nextOpenid
          this.handleFansSelectClear()
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
    },
    handleArticlePublish () {
      if (this.selectionList.length !== 1) {
        this.$message.warning('请选择一个公众号')
      }
    }
  },
  head () {
    return {
      title: '微信管理 | ' + this.website.name,
      meta: [
        { hid: 'description', name: 'description', content: '微信管理' }
      ]
    }
  }
}
</script>
<style scoped>
  .tip {
    padding: 8px 16px;
    background-color: #ecf8ff;
    border-radius: 4px;
    border-left: 5px solid #50bfff;
    margin: 20px 0;
  }
  .tip p {
    font-size: 14px;
    color: #5e6d82;
    line-height: 1.5em;
  }
  /**
 以下为微信菜单设置的样式文件
  */
  * {
    box-sizing: border-box;
  }

  #app-menu ul {
    padding: 0;
  }

  #app-menu li {
    list-style: none;
  }

  #app-menu {
    overflow: hidden;
    width: 100%;
  }

  .weixin-preview {
    position: relative;
    width: 320px;
    height: 540px;
    float: left;
    margin-right: 10px;
    border: 1px solid #e7e7eb;
  }

  .weixin-preview a {
    text-decoration: none;
    color: #616161;
  }

  .weixin-preview .weixin-hd .weixin-title {
    color: #fff;
    font-size: 15px;
    width: 100%;
    text-align: center;
    position: absolute;
    top: 33px;
    left: 0px;
  }

  .weixin-preview .weixin-bd {

  }

  .weixin-preview .weixin-menu {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    border-top: 1px solid #e7e7e7;
    background-position: 0 0;
    background-repeat: no-repeat;
    margin-bottom: 0px;
  }

  /*一级*/
  .weixin-preview .weixin-menu .menu-item {
    position: relative;
    float: left;
    line-height: 50px;
    height: 50px;
    text-align: center;
    width: 33.33%;
    border-left: 1px solid #e7e7e7;
    cursor: pointer;
    color: #616161;
  }

  /*二级*/
  .weixin-preview .weixin-sub-menu {
    position: absolute;
    bottom: 60px;
    left: 0;
    right: 0;
    border-top: 1px solid #d0d0d0;
    margin-bottom: 0px;
    background: #fafafa;
    display: block;
    padding: 0;
  }

  .weixin-preview .weixin-sub-menu .menu-sub-item {
    line-height: 50px;
    height: 50px;
    text-align: center;
    width: 100%;
    border: 1px solid #d0d0d0;
    border-top-width: 0px;
    cursor: pointer;
    position: relative;
    color: #616161;
  }

  .weixin-preview .menu-arrow {
    position: absolute;
    left: 50%;
    margin-left: -6px;
  }

  .weixin-preview .arrow_in {
    bottom: -4px;
    display: inline-block;
    width: 0px;
    height: 0px;
    border-width: 6px 6px 0px;
    border-style: solid dashed dashed;
    border-color: #fafafa transparent transparent;
  }

  .weixin-preview .arrow_out {
    bottom: -5px;
    display: inline-block;
    width: 0px;
    height: 0px;
    border-width: 6px 6px 0px;
    border-style: solid dashed dashed;
    border-color: #d0d0d0 transparent transparent;
  }

  .weixin-preview .menu-item .menu-item-title, .weixin-preview .menu-sub-item .menu-item-title {
    width: 100%;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    box-sizing: border-box;
  }

  .weixin-preview .menu-item.current, .weixin-preview .menu-sub-item.current {
    border: 1px solid #44b549;
    background: #fff;
    color: #44b549;
  }

  .weixin-preview .weixin-sub-menu.show {
    display: block;
  }

  .weixin-preview .icon_menu_dot {
    /* background: url(../images/index_z354723.png) 0px -36px no-repeat; */
    width: 7px;
    height: 7px;
    vertical-align: middle;
    display: inline-block;
    margin-right: 2px;
    margin-top: -2px;
  }

  .weixin-preview .icon14_menu_add {
    /* background: url(../images/index_z354723.png) 0px 0px no-repeat; */
    width: 14px;
    height: 14px;
    vertical-align: middle;
    display: inline-block;
    margin-top: -2px;
  }

  .weixin-preview li:hover .icon14_menu_add {
    /* background: url(../images/index_z354723.png) 0px -18px no-repeat; */
  }

  .weixin-preview .menu-item:hover {
    color: #000;
  }

  .weixin-preview .menu-sub-item:hover {
    background: #eee;
  }

  .weixin-preview li.current:hover {
    background: #fff;
    color: #44b549;
  }

  .weixin-btn-group {
    text-align: center;
    width: 100%;
    margin: 30px 0px;
    overflow: hidden;
  }

  .weixin-btn-group .btn {
    width: 100px;
    border-radius: 0px;
  }

  #material-list table {
    width: 100%;
  }
  .weixin-menu-detail {
    width: 600px;
    padding: 0px 20px 5px;
    background-color: #f4f5f9;
    border: 1px solid #e7e7eb;
    float: left;
    min-height: 540px;
  }

</style>
