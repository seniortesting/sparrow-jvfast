<script>
export default {
  name: 'MenuItem',
  functional: true,
  props: {
    icon: {
      type: String,
      default: ''
    },
    title: {
      type: String,
      default: ''
    }
  },
  render (h, context) {
    const { icon, title } = context.props
    const vnodes = []

    if (icon) {
      let cls = 'svg-icon'
      // if (icon.startsWith('icon-')) {
      //   cls += ' iconfont ' + icon
      // } else {
      //   cls += ' ' + icon
      // }
      cls += ' ' + icon
      vnodes.push(<i class={cls} />)
    }

    if (title) {
      vnodes.push(<span slot='title'>{(title)}</span>)
    }
    return vnodes
  }
}
</script>
