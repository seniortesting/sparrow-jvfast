const WIDTH = 992 // refer to Bootstrap's responsive design
const closeSidebarAction = 'app/closeSideBar'
const toggleDeviceAction = 'app/toggleDevice'
export default {
  watch: {
    $route (route) {
      if (this.device === 'mobile' && this.sidebar.opened) {
        this.$store.dispatch(closeSidebarAction, { withoutAnimation: false })
      }
    }
  },
  beforeMount () {
    window.addEventListener('resize', this.$_resizeHandler)
  },
  beforeDestroy () {
    window.removeEventListener('resize', this.$_resizeHandler)
  },
  mounted () {
    const isMobile = this.$_isMobile()
    if (isMobile) {
      this.$store.dispatch(toggleDeviceAction, 'mobile')
      this.$store.dispatch(closeSidebarAction, { withoutAnimation: true })
    }
  },
  methods: {
    // use $_ for mixins properties
    // https://vuejs.org/v2/style-guide/index.html#Private-property-names-essential
    $_isMobile () {
      if (process.client) {
        const rect = document.body.getBoundingClientRect()
        return rect.width - 1 < WIDTH
      }
    },
    $_resizeHandler () {
      if (process.client) {
        if (!document.hidden) {
          const isMobile = this.$_isMobile()
          this.$store.dispatch(toggleDeviceAction, isMobile ? 'mobile' : 'desktop')

          if (isMobile) {
            this.$store.dispatch(closeSidebarAction, { withoutAnimation: true })
          }
        }
      }
    }
  }
}
