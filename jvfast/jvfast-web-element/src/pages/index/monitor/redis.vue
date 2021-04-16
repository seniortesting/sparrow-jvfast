<template>
  <el-card-container>
    <div class="header">
      <el-alert
        :closable="false"
        class="msg"
        type="success"
        show-icon
      >
        <div slot="title">
          上次更新时间: {{ updateTime }}
          <el-divider direction="vertical" />
          <a @click.prevent="refreshHandler">立即刷新</a>
        </div>
      </el-alert>
    </div>
    <div class="chart">
      <el-row :gutter="8">
        <el-col :sm="24" :md="12">
          <!--          <area-chart-ty v-bind="memory" />-->
        </el-col>
        <el-col :sm="24" :md="12">
          <!--          <area-chart-ty v-bind="key" />-->
        </el-col>
      </el-row>
    </div>
    <div class="info">
      <h3>Redis 详细信息</h3>
      <el-table
        :data="redisInfo"
        style="width: 100%"
      >
        <el-table-column
          prop="key"
          label="参数"
          width="200"
        />
        <el-table-column
          prop="description"
          label="描述"
          width="800"
        />
        <el-table-column
          prop="value"
          label="值"
        />
      </el-table>
    </div>
  </el-card-container>
</template>

<script>
import { mapGetters } from 'vuex'
import dayjs from 'dayjs'

const getRedisInfoAction = 'monitor/server/getRedisInfo'
const getRedisKeysAction = 'monitor/server/getRedisKeys'
const getRedisMemoryAction = 'monitor/server/getRedisMemory'
export default {
  name: 'Redis',
  data () {
    return {
      redisInfo: [],
      // Key 实时数量
      key: {
        title: 'Redis Key 实时数量（个）',
        dataSource: [],
        y: '数量（个）',
        height: 340,
        min: 0,
        max: 100,
        color: '#FF6987',
        lineSize: 8,
        lineColor: '#DC143C'
      },
      // 内存实时占用情况
      memory: {
        title: 'Redis 内存实时占用情况（KB）',
        dataSource: [],
        y: '内存（KB）',
        min: 0,
        max: 3000,
        height: 340,
        lineSize: 8
      },
      updateTime: '',
      // 定时器ID
      timer: null,
      // 定时器周期
      millisec: 5000
    }
  },
  computed: {
    ...mapGetters(['website'])
  },
  mounted () {
    this.fetchRedisInfo()
    this.loadData()
    this.openTimer()
  },
  beforeDestroy () {
    this.closeTimer()
  },
  methods: {
    openTimer () {
      this.timer = setInterval(() => {
        if (this.$route.name === 'index-monitor-redis') {
          this.loadData()
        }
      }, this.millisec)
    },
    /** 关闭定时器 */
    closeTimer () {
      if (this.timer) { clearInterval(this.timer) }
    },
    refreshHandler () {
      this.loadData()
    },
    fetchRedisInfo () {
      this.$store.dispatch(getRedisInfoAction).then((res) => {
        const resCode = res.code
        if (resCode === 0) {
          this.redisInfo = res.data
        }
      })
    },
    loadData () {
      Promise.all([
        this.$store.dispatch(getRedisKeysAction),
        this.$store.dispatch(getRedisMemoryAction)
      ]).then((res) => {
        const time = dayjs(new Date()).format('YYYY年MM月DD日HH时mm分ss秒')
        this.updateTime = time
        const [{ data: currentSizeData }, { data: memoryInfo }] = res
        const currentMemory = memoryInfo.used_memory / 1000

        // push 数据
        this.key.dataSource.push({ x: time, y: currentSizeData.dbSize })
        this.memory.dataSource.push({ x: time, y: currentMemory })
        // 最大长度为6
        if (this.key.dataSource.length > 6) {
          this.key.dataSource.splice(0, 1)
          this.memory.dataSource.splice(0, 1)
        }

        // 计算 Key 最大最小值
        const keyPole = this.getMaxAndMin(this.key.dataSource, 'y')
        this.key.max = Math.floor(keyPole[0]) + 10
        this.key.min = Math.floor(keyPole[1]) - 10
        if (this.key.min < 0) { this.key.min = 0 }

        // 计算 Memory 最大最小值
        const memoryPole = this.getMaxAndMin(this.memory.dataSource, 'y')
        this.memory.max = Math.floor(memoryPole[0]) + 100
        this.memory.min = Math.floor(memoryPole[1]) - 100
        if (this.memory.min < 0) { this.memory.min = 0 }
      }).catch((e) => {
        console.error(e)
        this.closeTimer()
        this.$message.error('获取 Redis 信息失败')
      }).finally(() => {
      })
    },
    // 获取一组数据中最大和最小的值
    getMaxAndMin (dataSource, field) {
      let maxValue = null; let minValue = null
      dataSource.forEach((item) => {
        const value = Number.parseInt(item[field])
        // max
        if (maxValue == null) {
          maxValue = value
        } else if (value > maxValue) {
          maxValue = value
        }
        // min
        if (minValue == null) {
          minValue = value
        } else if (value < minValue) {
          minValue = value
        }
      })
      return [maxValue, minValue]
    }
  },
  head () {
    return {
      title: '缓存概述 | ' + this.website.name,
      meta: [
        { hid: 'description', name: 'description', content: '缓存概述' }
      ]
    }
  }
}
</script>

<style scoped>
 .msg {
   background-color: #e6f7ff;
   border: 1px solid #91d5ff;
 }
  .msg >>> .el-alert__title {
    color: rgba(0,0,0,.65);
    font-size: 14px;
    font-variant: tabular-nums;
    line-height: 1.5;
    list-style: none;
    font-feature-settings: "tnum";
  }
  a {
    color: #1890ff;
    text-decoration: none;
    background-color: transparent;
    outline: none;
    cursor: pointer;
    transition: color .3s;
  }
  .chart {
    margin-top: 15px;
  }
  .info {
    margin-top: 15px;
  }
</style>
