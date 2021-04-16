---
title: el-tree组件,树结构组件模块
---

## 异步设置选中的树节点

```
            // 不要再使用 default-checked-keys
            // Tree 组件在渲染时，会多次调用 load 函数
            // 每次加载完成， reinitChecked 都会执行
            // 因为新的子节点还未创建，如果前两个元素是选中的，那么父节点计算的 checked 就是 true
            setTimeout(() => {
              this.$refs.tree.setCheckedKeys(menuIds)
            }, 10)
```