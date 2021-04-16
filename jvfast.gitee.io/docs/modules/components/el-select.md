---
title: el-select组件,下拉框组件模块
---

# 下拉选框

代码：

```
                        <el-select
                          ref="selectProjectSearch"
                          v-model="addViewEditForm.budgetId"
                          class="avue-select"
                          :multiple="false"
                          :multiple-limit="1"
                          :filterable="false"
                          :allow-create="false"
                          :default-first-option="true"
                          :remote="false"
                          :readonly="false"
                          :clearable="true"
                          placeholder="选择预算材料"
                          :disabled="addViewEditDlgType==='view'"
                        >
                          <el-option
                            v-for="item in budgets"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                          />
                        </el-select>
```