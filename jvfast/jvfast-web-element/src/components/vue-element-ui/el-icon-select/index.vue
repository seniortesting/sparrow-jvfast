<template>
  <div class="avue-icon-select">
    <el-input
      ref="main"
      v-model="text"
      :placeholder="placeholder"
      size="medium"
      :clearable="disabled?false:clearable"
      :disabled="disabled"
      @change="handleChange"
      @focus="handleShow"
    >
      <template slot="append">
        <i v-if="text" :class="text" />
        <span v-else>没有选择图标</span>
      </template>
    </el-input>
    <el-dialog
      :title="placeholder"
      :modal-append-to-body="false"
      append-to-body
      :visible.sync="box"
      :width="$device.isMobile? '100%':'60%'"
    >
      <el-scrollbar style="height:400px;overflow-x:hidden;overflow-y: auto">
        <el-tabs v-model="active" tab-position="top">
          <el-tab-pane v-for="(column,index) in iconList" :key="index" :name="index+''">
            <span slot="label">
              <i :class="column.icon" />&nbsp;
              {{ column.label }}
            </span>
          </el-tab-pane>
        </el-tabs>
        <div class="avue-icon-select__list">
          <div
            v-for="(item,index) in list"
            :key="index"
            class="avue-icon-select__item"
            :class="{'active': text===item}"
          >
            <i
              :class="['avue-icon-select__icon',item.value]"
              @click="handleSubmit(item.value)"
            />
            <p v-if="item.label">
              {{ item.label }}
            </p>
          </div>
        </div>
      </el-scrollbar>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'IconSelect',
  props: {
    iconList: {
      type: Array,
      default: () => {
        return []
      }
    },
    // 其他属性
    blur: Function,
    focus: Function,
    change: Function,
    click: Function,
    // eslint-disable-next-line vue/require-prop-types
    value: {},
    column: {
      type: Object,
      default: () => { }
    },
    readonly: {
      type: Boolean,
      default: false
    },
    disabled: {
      type: Boolean,
      default: false
    },
    clearable: {
      type: Boolean,
      default: true
    },
    type: {
      type: String,
      default: ''
    },
    placeholder: {
      type: String,
      default: '请点击选择图标'
    },
    multiple: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      box: false,
      tabs: {},
      text: undefined,
      // tabs
      active: '0'
    }
  },
  computed: {
    list () {
      const list = (this.tabs.list || []).map((ele) => {
        if (!ele.value) {
          return {
            value: ele
          }
        }
        return ele
      })
      return list
    },
    option () {
      return {
        column: this.iconList
      }
    },
    isString () {
      return this.dataType === 'string'
    },
    isNumber () {
      return this.dataType === 'number'
    }
  },
  watch: {
    value: {
      handler (val) {
        this.initVal()
      },
      immediate: true
    },
    active: {
      handler (val) {
        this.handleTabs(val)
      },
      immediate: true
    }
  },
  created () {
    this.tabs = this.iconList[0] || {}
  },
  methods: {
    handleTabs (tabName) {
      this.tabs = this.iconList[parseInt(tabName)]
    },
    handleSubmit (item) {
      this.box = false
      this.text = item
      this.handleChange(item)
    },
    handleShow () {
      this.$refs.main.blur()
      if (this.disabled || this.readonly) {
        return
      }
      this.box = true
    },
    handleChange (value) {
      let result = value
      this.text = result
      if ((this.isString || this.isNumber) && (this.multiple || ['checkbox', 'cascader', 'img', 'array'].includes(this.type))) {
        result = value.join(',')
      }
      if (typeof this.change === 'function') {
        this.change({ value: result, column: this.column })
      }
      this.$emit('input', result)
      this.$emit('change', result)
    },
    initVal () {
      this.text = this.value
    }
  }
}
</script>
