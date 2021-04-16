<template>
  <el-autocomplete
    v-model="value"
    :fetch-suggestions="querySearch"
    class="top-search"
    popper-class="my-autocomplete"
    placeholder="请输入搜索内容"
    @select="handleSelect"
  >
    <template slot-scope="{ item }">
      <i :class="[item[iconKey],'icon']" />
      <div class="name">
        {{ item[labelKey] }}
      </div>
      <p class="addr">
        {{ item[pathKey] }}
      </p>
    </template>
  </el-autocomplete>
</template>

<script>
export default {
  data () {
    return {
      value: ''
    }
  },
  methods: {
    querySearch (queryString, cb) {
      // 调用 callback 返回建议列表的数据
      // cb(results)
    },
    handleSelect (item) {
      this.value = ''
    }
  }

}
</script>

<style lang="scss">

  .my-autocomplete {
    li {
      line-height: normal;
      padding: 7px;

      .icon {
        margin-right: 5px;
        display: inline-block;
        vertical-align: middle;
      }

      .name {
        display: inline-block;
        text-overflow: ellipsis;
        overflow: hidden;
        vertical-align: middle;
      }

      .addr {
        padding-top: 5px;
        width: 100%;
        font-size: 12px;
        color: #b4b4b4;
      }

      .highlighted .addr {
        color: #ddd;
      }
    }
  }
</style>
