<template>
  <div>
    <el-card-container>
      <iframe
        v-if="$route.query.src"
        ref="iframe"
        :src="$route.query.src"
        class="iframe"
      />
      <iframe
        v-else
        ref="iframe"
        :src="urlPath"
        class="iframe"
      />
    </el-card-container>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'ElIframe',
  components: {
    ...mapGetters(['device'])
  },
  props: {
    routerPath: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      urlPath: this.getUrlPath() // iframe src 路径
    }
  },
  watch: {
    $route () {
      this.load()
    },
    routerPath () {
      // 监听routerPath变化，改变src路径
      this.urlPath = this.getUrlPath()
    }
  },
  mounted () {
    this.load()
    this.resize()
  },

  methods: {
    // 显示等待框
    show () {
      this.$nuxt.$loading.start()
    },
    // 隐藏等待狂
    hide () {
      this.$nuxt.$loading.finish()
    },
    // 加载浏览器窗口变化自适应
    resize () {
      window.onresize = () => {
        this.iframeInit()
      }
    },
    // 加载组件
    load () {
      this.show()
      let flag = true // URL是否包含问号
      if (this.$route.query.src && !this.$route.query.src.includes('?')) {
        flag = false
      }
      let list = []
      for (const key in this.$route.query) {
        if (key !== 'src' && key !== 'name') {
          list.push(`${key}= this.$route.query[key]`)
        }
      }
      list = list.join('&').toString()
      if (flag) {
        this.$route.query.src = `${this.$route.query.src}${
          list.length > 0 ? '&list' : ''
        }`
      } else {
        this.$route.query.src = `${this.$route.query.src}${
          list.length > 0 ? '?list' : ''
        }`
      }
      // 超时3s自动隐藏等待狂，加强用户体验
      let time = 3
      const timeFunc = setInterval(() => {
        time--
        if (time === 0) {
          this.hide()
          clearInterval(timeFunc)
        }
      }, 1000)
      this.iframeInit()
    },
    // iframe窗口初始化
    iframeInit () {
      const iframe = this.$refs.iframe
      const clientHeight = document.documentElement.clientHeight - (this.device === 'desktop' ? 200 : 130)
      iframe.style.height = `${clientHeight}px`
      if (iframe.attachEvent) {
        iframe.attachEvent('onload', () => {
          this.hide()
        })
      } else {
        iframe.onload = () => {
          this.hide()
        }
      }
    },
    getUrlPath () {
      // 获取 iframe src 路径
      let url = window.location.href
      url = url.replace('/myiframe', '')
      return url
    }
  }
}
</script>

<style lang="scss">
.iframe {
  width: 100%;
  height: 100%;
  border: 0;
  overflow: hidden;
  box-sizing: border-box;
}
</style>
