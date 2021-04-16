---
title: el-date组件,日期模块
---

## 1. 日期格式代码

```
                        <div class="avue-date">
                          <el-date-picker
                            v-model="addViewEditForm.admissiveDate"
                            type="date"
                            :unlink-panels="false"
                            range-separator="至"
                            start-placeholder="结束日期"
                            end-placeholder="开始日期"
                            format="yyyy-MM-dd"
                            :clearable="true"
                            value-format="yyyy-MM-dd"
                            placeholder="请选择日期"
                            :disabled="addViewEditDlgType==='view'"
                          />
                        </div>
```

## 2. 禁用日期区间代码

设置禁用某个区间的日期，可以通过属性: `:picker-options="endPickerOptions"` 中的 `disabledDate`设置需要禁用的日期段。

```
 data () {
   return {
      endPickerOptions: {
        disabledDate: this.handleDisabledDate
      }
   }
 },
 methods: {
    handleDisabledDate (date) {
      const startDate = this.addViewEditForm.startDate
      return startDate ? new Date(formatDate(date)) < new Date(startDate) : false
    }
 }
```