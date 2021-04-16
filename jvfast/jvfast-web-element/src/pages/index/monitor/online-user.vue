<template>
  <el-card-container>
    <div>
      <h3>TBD</h3>
    </div>
  </el-card-container>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'OnlineUser',
  computed: {
    ...mapGetters(['website'])
  },
  head () {
    return {
      title: '在线用户管理 | ' + this.website.name,
      meta: [
        { hid: 'description', name: 'description', content: '在线用户管理' }
      ]
    }
  }
}
</script>

<style scoped>

</style>
