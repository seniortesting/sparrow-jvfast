---
title: el-radio组件，单选框模块
---

## 单选框代码

```
                        <div class="avue-radio">
                          <el-radio-group
                            v-model="addViewEditForm.menuType"
                            size="medium"
                            :disabled="addViewEditDlgType==='view'"
                          >
                              <component
                                :is="'ElRadioButton'"
                                v-for="(item) in menuTypeDicData1"
                                :key="item.value"
                                :label="item.value"
                                :border="true"
                              >
                                {{ item.label }}
                              </component>
                          </el-radio-group>
                        </div>

```