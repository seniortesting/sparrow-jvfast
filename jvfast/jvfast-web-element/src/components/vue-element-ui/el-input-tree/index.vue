<template>
  <el-select
    ref="main"
    class="avue-input-tree"
    :size="size"
    :value="labelShow"
    :clearable="disabled?false:clearable"
    :placeholder="placeholder"
    :disabled="disabled"
    :readonly="true"
    @click.native="initScroll"
    @change="handleChange"
    @focus="handleFocus"
    @blur="handleBlur"
  >
    <div
      v-if="filter"
      style="padding:0 10px;margin:5px 0 0 0;"
    >
      <el-input
        v-model="filterText"
        size="mini"
        placeholder="输入关键字进行过滤"
      />
    </div>
    <el-option :value="text">
      <el-tree
        ref="tree"
        :data="dicList"
        class="tree-option"
        style="padding:10px 0;"
        :lazy="lazy"
        :load="treeLoad"
        :node-key="valueKey"
        :accordion="accordion"
        :show-checkbox="multiple"
        :props="treeProps"
        :check-strictly="checkStrictly"
        :highlight-current="!multiple"
        :current-node-key="multiple?'':text"
        :filter-node-method="filterNode"
        :default-expanded-keys="defaultExpandedKeys?defaultExpandedKeys:(defaultExpandAll?[]:keysList)"
        :default-checked-keys="defaultCheckedKeys?defaultCheckedKeys:keysList"
        :default-expand-all="defaultExpandAll"
        @check="checkChange"
        @node-click.self="handleNodeClick"
      >
        <div
          slot-scope="{ data }"
          style="width:100%;padding-right:10px;"
        >
          <slot
            v-if="typeslot"
            :name="prop+'Type'"
            :label="labelKey"
            :value="valueKey"
            :item="data"
          />
          <span
            v-else
            :class="{'avue--disabled':data[disabledKey]}"
          >{{ data[labelKey] }}</span>
        </div>
      </el-tree>
    </el-option>
  </el-select>
</template>

<script>
import { isNull } from '@/util/ValidateUtil'
import { deepClone } from '@/util/ObjectUtil'

export default {
  name: 'InputTree',
  props: {
    nodeClick: Function,
    treeLoad: Function,
    checked: Function,
    // eslint-disable-next-line vue/require-prop-types
    value: {},
    column: {
      type: Object,
      default: () => { }
    },
    lazy: {
      type: Boolean,
      default: false
    },
    filter: {
      type: Boolean,
      default: true
    },
    checkStrictly: {
      type: Boolean,
      default: false
    },
    accordion: {
      type: Boolean,
      default: false
    },
    parent: {
      type: Boolean,
      default: true
    },
    defaultExpandedKeys: {
      type: Array
    },
    defaultCheckedKeys: {
      type: Array
    },
    defaultExpandAll: {
      type: Boolean,
      default: true
    },
    // 其他属性
    typeformat: Function,
    dic: {
      type: Array,
      default: () => {
        return []
      }
    },
    typeslot: {
      type: Boolean,
      default: false
    },
    size: {
      type: String,
      default: ''
    },
    dataType: {
      type: String
    },
    disabled: {
      type: Boolean,
      default: false
    },
    clearable: {
      type: Boolean,
      default: true
    },
    placeholder: {
      type: String,
      default: ''
    },
    multiple: {
      type: Boolean,
      default: false
    },
    prop: {
      type: String,
      default: ''
    },
    dicobj: {
      type: Object,
      default: () => {
        return {
          nodeKey: 'id',
          label: 'label',
          value: 'value',
          groups: 'groups',
          leaf: 'leaf',
          children: 'children',
          labelText: '名称',
          disabled: 'disabled'
        }
      }
    }
  },
  data () {
    return {
      node: {},
      filterText: '',
      box: false,
      labelText: this.multiple ? [] : '',
      // 其他设置
      text: undefined
    }
  },
  computed: {
    treeProps () {
      return Object.assign(this.dicobj, {
        isLeaf: this.leafKey
      })
    },
    dicList () {
      function addParent (result, parent) {
        result.forEach((ele) => {
          const children = ele.children
          if (children) {
            addParent(children, ele)
          }
          if (parent) {
            ele.$parent = parent
          }
        })
      }
      const list = this.dic
      addParent(list)
      return list
    },
    keysList () {
      if (isNull(this.text)) {
        return []
      }
      return this.multiple ? this.text : [this.text]
    },
    labelShow () {
      const DIC_SPLIT = ' | '
      if (isNull(this.value)) {
        return ''
      } else if (this.multiple) {
        return (this.labelText || []).join(DIC_SPLIT).toString()
      }
      return this.getLabelText(this.node)
    },
    // 其他属性
    isString () {
      return this.dataType === 'string'
    },
    valueKey () {
      return this.dicobj.value || 'value'
    },
    leafKey () {
      return this.dicobj.leaf || 'leaf'
    },
    labelKey () {
      return this.dicobj.label || 'label'
    },
    childrenKey () {
      return this.dicobj.children || 'children'
    },
    disabledKey () {
      return this.dicobj.disabled || 'disabled'
    }
  },
  watch: {
    text: {
      handler (value) {
        if (isNull(value)) {
          this.clearHandle()
        }
        this.handleChange(value)
      }
    },
    value () {
      this.initVal()
      this.init()
    },
    filterText (val) {
      this.$refs.tree.filter(val)
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    // 初始化滚动条
    initScroll () {
      setTimeout(() => {
        this.$nextTick(() => {
          const scrollBar = document.querySelectorAll('.el-scrollbar .el-select-dropdown__wrap')
          scrollBar.forEach((ele) => {
            ele.scrollTop = 0
          })
        })
      }, 0)
    },
    filterNode (value, data) {
      if (!value) { return true }
      return data[this.labelKey].includes(value)
    },
    checkChange (checkedNodes, checkedKeys, halfCheckedNodes, halfCheckedKeys) {
      this.text = []
      this.labelText = []
      const list = checkedKeys.checkedNodes
      list.forEach((node) => {
        if (isNull(node[this.childrenKey]) && !this.checkStrictly) {
          this.text.push(node[this.valueKey])
          this.labelText.push(node[this.labelKey])
        } else if (this.checkStrictly) {
          this.text.push(node[this.valueKey])
          this.labelText.push(node[this.labelKey])
        }
      })
      if (typeof this.checked === 'function') { this.checked(checkedNodes) }
      const result =
          this.isString && this.multiple ? this.text.join(',') : this.text
      this.$emit('input', result)
      this.$emit('change', result)
    },
    init () {
      if (this.multiple) {
        this.labelText = []
      } else {
        this.labelText = ''
      }
      const check = setInterval(() => {
        if (isNull(this.dic)) {
          this.labelText = ''
          clearInterval(check)
          return
        }
        // 是否禁止父类
        this.disabledParentNode(this.dic, this.parent)
        if (this.multiple) {
          this.labelText = []
          if (!isNull(this.text)) {
            this.text.forEach((ele) => {
              // 特殊处理0
              ele = isNull(ele) ? 0 : ele
              const label = this.findLabelNode(this.dic, ele, this.dicobj) || ele
              this.labelText.push(label)
            })
          }
        } else {
          this.labelText = ''
          if (!isNull(this.text)) {
            this.labelText = this.text
            const label = this.findLabelNode(this.dic, this.text, this.dicobj) || this.text
            this.node = {}
            this.node[this.labelKey] = label
            this.labelText = label
          }
        }
        setTimeout(() => {
          this.$partent && this.$partent.$parent.clearValidate()
        }, 0)
        clearInterval(check)
      }, 500)
    },
    disabledParentNode (dic, parent) {
      dic.forEach((ele) => {
        const children = ele[this.childrenKey]
        if (!isNull(children)) {
          if (!parent) {
            ele.disabled = true
          }
          this.disabledParentNode(children, parent)
        }
      })
    },
    clearHandle () {
      const allNode = document.querySelectorAll('.tree-option .el-tree-node')
      allNode.forEach(element => element.classList.remove('is-current'))
      this.$refs.tree.setCheckedKeys([])
    },
    handleNodeClick (data) {
      const callback = () => {
        this.node = data
        this.$refs.main.blur()
      }
      if (data.disabled) { return }
      if (typeof this.nodeClick === 'function') { this.nodeClick(data) }
      if (this.multiple) { return }
      if (
        (isNull(data[this.childrenKey]) && !this.multiple) ||
          this.parent
      ) {
        const value = data[this.valueKey]
        const label = data[this.labelKey]
        const result = this.isString && this.multiple ? value.join(',') : value
        this.text = value
        this.labelText = label
        this.$emit('input', result)
        this.$emit('change', result)
        callback()
      }
    },
    handleClick () {
      const result =
          this.isString && this.multiple ? this.text.join(',') : this.text
      if (typeof this.click === 'function') { this.click({ value: result, column: this.column }) }
    },
    handleChange (value) {
      // eslint-disable-next-line no-unused-vars
      const text = this.text
      const result = this.isString && this.multiple ? value.join(',') : value
      if (typeof this.change === 'function') {
        this.change({ value: result, column: this.column })
      }
      this.$emit('input', result)
      this.$emit('change', result)
    },
    // 其他事件
    handleFocus () {
      typeof this.focus === 'function' && this.focus()
    },
    handleBlur () {
      typeof this.blur === 'function' && this.blur()
    },
    getLabelText (item) {
      if (isNull(item)) { return '' }
      if (typeof this.typeformat === 'function') {
        return this.typeformat(item, this.labelKey, this.valueKey)
      }
      return item[this.labelKey]
    },
    initVal () {
      const list = this.value
      // if (!Array.isArray(this.value)) {
      //   if (!isNull(this.value)) {
      //     list = (this.value || '').split(',') || []
      //   } else {
      //     list = []
      //   }
      // }
      // // 数据转化
      // list.map((ele, index) => {
      //   list[index] = this.detailDataType(ele, undefined)
      // })
      this.text = list
    },
    detailDataType  (value, type) {
      if (type === 'number') {
        return Number(value)
      } else if (type === 'string') {
        return value + ''
      } else {
        return value
      }
    },
    // 其他函数
    findLabelNode (dic, value, props, obj) {
      let result
      if (!obj) { dic = this.detailDicGroup(dic) }
      const rev = (dic1, value1, props1) => {
        const labelKey = props1.label || 'label'
        const valueKey = props1.value || 'value'
        const childrenKey = props1.children || 'children'
        for (let i = 0; i < dic1.length; i++) {
          const ele = dic1[i]
          const children = ele[childrenKey] || []
          if (ele[valueKey] === value1) {
            result = obj ? ele : ele[labelKey]
          } else {
            rev(children, value1, props1)
          }
        }
      }
      rev(dic, value, props)
      return result
    },
    detailDicGroup  (dic) {
      dic = deepClone(dic)
      let list = []
      if ((dic[0] || {}).groups) {
        dic.forEach((ele) => {
          if (ele.groups) {
            list = list.concat(ele.groups)
          }
        })
        return list
      }
      return dic
    }

  }
}
</script>
