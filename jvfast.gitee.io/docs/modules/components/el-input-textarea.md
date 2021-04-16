---
title: el-input组件,输入框模块
---

## 输入框类型代码

```
                            <el-tooltip placement="bottom">
                              <div slot="content">
                                请输入对应的项目名称
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
                                placeholder="请输入对应的项目名称"
                              />
                            </el-tooltip>
```

## 富文本框类型代码

```
                        <el-tooltip placement="bottom">
                          <div slot="content">
                            请输入规格,型号,产地
                          </div>
                          <el-input
                            v-model="addViewEditForm.fromWhere"
                            type="textarea"
                            :minlength="3"
                            :maxlength="120"
                            :rows="5"
                            :show-word-limit="true"
                            class="avue-input"
                            :clearable="true"
                            :disabled="addViewEditDlgType==='view'"
                            placeholder="请输入规格,型号,产地"
                          />
                        </el-tooltip>
```