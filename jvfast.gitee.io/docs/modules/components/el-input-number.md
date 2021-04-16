---
title: el-input-number组件,输入数字框模块
---

## 输入数据格式为数字

代码如下：

```
                        <el-input-number
                          v-model="addViewEditForm.price"
                          class="avue-input-number"
                          :precision="2"
                          :min="0"
                          :step="1"
                          :clearable="true"
                          :readonly="addViewEditDlgType==='view'"
                          label="请输入价格"
                          :disabled="addViewEditDlgType==='view'"
                        />
```