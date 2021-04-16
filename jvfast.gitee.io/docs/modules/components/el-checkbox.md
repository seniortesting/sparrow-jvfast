---
title: el-checkbox组件,复选框框模块
---

## 复选框代码

```
                    <el-tooltip placement="bottom">
                      <div slot="content">
                        请选择性别
                      </div>
                      <div class="avue-radio">
                        <el-radio-group
                          v-model="userForm.sex"
                        >
                          <el-radio :label="1">男</el-radio>
                          <el-radio :label="2">女</el-radio>
                          <el-radio :label="3">未知</el-radio>
                        </el-radio-group>
                      </div>
                    </el-tooltip>
```