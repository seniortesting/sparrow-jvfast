<template>
  <div>
    <el-row>
      <el-col :span="8" class="padding">
        <el-card class="padding">
          <div slot="header">
            <span>CPU</span>
          </div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;">
              <thead>
                <tr>
                  <th class="is-leaf">
                    <div class="cell">
                      属性
                    </div>
                  </th>
                  <th class="is-leaf">
                    <div class="cell">
                      值
                    </div>
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>
                    <div class="cell">
                      核心数
                    </div>
                  </td>
                  <td>
                    <div v-if="serverInfo.cpu" class="cell">
                      {{ serverInfo.cpu.cpuNum }}
                    </div>
                  </td>
                </tr>
                <tr>
                  <td>
                    <div class="cell">
                      用户使用率
                    </div>
                  </td>
                  <td>
                    <div v-if="serverInfo.cpu" class="cell">
                      {{ serverInfo.cpu.used }}%
                    </div>
                  </td>
                </tr>
                <tr>
                  <td>
                    <div class="cell">
                      系统使用率
                    </div>
                  </td>
                  <td>
                    <div v-if="serverInfo.cpu" class="cell">
                      {{ serverInfo.cpu.sys }}%
                    </div>
                  </td>
                </tr>
                <tr>
                  <td>
                    <div class="cell">
                      当前空闲率
                    </div>
                  </td>
                  <td>
                    <div v-if="serverInfo.cpu" class="cell">
                      {{ serverInfo.cpu.free }}%
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-card>
        <el-card class="padding-top">
          <div slot="header">
            <span>内存</span>
          </div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;">
              <thead>
                <tr>
                  <th class="is-leaf">
                    <div class="cell">
                      属性
                    </div>
                  </th>
                  <th class="is-leaf">
                    <div class="cell">
                      内存
                    </div>
                  </th>
                  <th class="is-leaf">
                    <div class="cell">
                      JVM
                    </div>
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>
                    <div class="cell">
                      总内存
                    </div>
                  </td>
                  <td>
                    <div v-if="serverInfo.memory" class="cell">
                      {{ serverInfo.memory.total }}G
                    </div>
                  </td>
                  <td>
                    <div v-if="serverInfo.jvm" class="cell">
                      {{ serverInfo.jvm.total }}M
                    </div>
                  </td>
                </tr>
                <tr>
                  <td>
                    <div class="cell">
                      已用内存
                    </div>
                  </td>
                  <td>
                    <div v-if="serverInfo.memory" class="cell">
                      {{ serverInfo.memory.used }}G
                    </div>
                  </td>
                  <td>
                    <div v-if="serverInfo.jvm" class="cell">
                      {{ serverInfo.jvm.used }}M
                    </div>
                  </td>
                </tr>
                <tr>
                  <td>
                    <div class="cell">
                      剩余内存
                    </div>
                  </td>
                  <td>
                    <div v-if="serverInfo.memory" class="cell">
                      {{ serverInfo.memory.free }}G
                    </div>
                  </td>
                  <td>
                    <div v-if="serverInfo.jvm" class="cell">
                      {{ serverInfo.jvm.free }}M
                    </div>
                  </td>
                </tr>
                <tr>
                  <td>
                    <div class="cell">
                      使用率
                    </div>
                  </td>
                  <td>
                    <div v-if="serverInfo.memory" :class="{'text-danger': serverInfo.memory.usage > 80}" class="cell">
                      {{ serverInfo.memory.usage }}%
                    </div>
                  </td>
                  <td>
                    <div v-if="serverInfo.jvm" :class="{'text-danger': serverInfo.jvm.usage > 80}" class="cell">
                      {{ serverInfo.jvm.usage }}%
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>
      <el-col :span="16" class="padding">
        <el-card class="padding">
          <div slot="header">
            <span>服务器信息</span>
          </div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;">
              <tbody>
                <tr>
                  <td>
                    <div class="cell">
                      服务器名称
                    </div>
                  </td>
                  <td>
                    <div v-if="serverInfo.sysInfo" class="cell">
                      {{ serverInfo.sysInfo.computerName }}
                    </div>
                  </td>
                  <td>
                    <div class="cell">
                      操作系统
                    </div>
                  </td>
                  <td>
                    <div v-if="serverInfo.sysInfo" class="cell">
                      {{ serverInfo.sysInfo.osName }}
                    </div>
                  </td>
                </tr>
                <tr>
                  <td>
                    <div class="cell">
                      服务器IP
                    </div>
                  </td>
                  <td>
                    <div v-if="serverInfo.sysInfo" class="cell">
                      {{ serverInfo.sysInfo.computerIp }}
                    </div>
                  </td>
                  <td>
                    <div class="cell">
                      系统架构
                    </div>
                  </td>
                  <td>
                    <div v-if="serverInfo.sysInfo" class="cell">
                      {{ serverInfo.sysInfo.osArch }}
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-card>
        <el-card class="padding-top">
          <div slot="header">
            <span>磁盘状态</span>
          </div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;">
              <thead>
                <tr>
                  <th class="is-leaf">
                    <div class="cell">
                      盘符路径
                    </div>
                  </th>
                  <th class="is-leaf">
                    <div class="cell">
                      文件系统
                    </div>
                  </th>
                  <th class="is-leaf">
                    <div class="cell">
                      盘符类型
                    </div>
                  </th>
                  <th class="is-leaf">
                    <div class="cell">
                      总大小
                    </div>
                  </th>
                  <th class="is-leaf">
                    <div class="cell">
                      可用大小
                    </div>
                  </th>
                  <th class="is-leaf">
                    <div class="cell">
                      已用大小
                    </div>
                  </th>
                  <th class="is-leaf">
                    <div class="cell">
                      已用百分比
                    </div>
                  </th>
                </tr>
              </thead>
              <tbody v-if="serverInfo.sysFile">
                <tr v-for="sysFile in serverInfo.sysFile" :key="sysFile.dirName">
                  <td>
                    <div class="cell">
                      {{ sysFile.dirName }}
                    </div>
                  </td>
                  <td>
                    <div class="cell">
                      {{ sysFile.sysTypeName }}
                    </div>
                  </td>
                  <td>
                    <div class="cell">
                      {{ sysFile.typeName }}
                    </div>
                  </td>
                  <td>
                    <div class="cell">
                      {{ sysFile.total }}
                    </div>
                  </td>
                  <td>
                    <div class="cell">
                      {{ sysFile.free }}
                    </div>
                  </td>
                  <td>
                    <div class="cell">
                      {{ sysFile.used }}
                    </div>
                  </td>
                  <td>
                    <div :class="{'text-danger': sysFile.usage > 80}" class="cell">
                      {{ sysFile.usage }}%
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
const serverInfoAction = 'monitor/server/getServerInfo'
export default {
  name: 'ServerInfo',
  data () {
    return {
      serverInfo: {}
    }
  },
  computed: {
    ...mapGetters(['website'])
  },
  mounted () {
    const loading = this.$loading({
      fullscreen: true,
      text: '加载中...',
      spinner: 'el-icon-loading'
    })
    this.$store.dispatch(serverInfoAction).then((res) => {
      loading.close()
      const resCode = res.code
      if (resCode === 0) {
        this.serverInfo = res.data
      }
    }).catch((error) => {
      console.log(`获取服务信息出错: ${error}`)
      loading.close()
    })
  },
  head () {
    return {
      title: '服务器概述 | ' + this.website.name,
      meta: [
        { hid: 'description', name: 'description', content: '服务器概述' }
      ]
    }
  }
}
</script>

<style scoped>
.padding {
  padding: 15px;
}
  .padding-top {
    margin-top: 15px;
  }
</style>
