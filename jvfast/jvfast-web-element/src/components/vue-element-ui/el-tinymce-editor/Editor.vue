<template>
  <div class="tinymce-editor">
    <editor
      v-model="myValue"
      :inline="inline"
      :disabled="disabled"
      :init="init"
      @onClick="onClick"
    />
  </div>
</template>
<script>
import tinymce from 'tinymce/tinymce'
import 'tinymce/themes/silver'
import 'tinymce/icons/default'
import Editor from '@tinymce/tinymce-vue'
// 编辑器插件plugins
// 更多插件参考：https://www.tiny.cloud/docs/plugins/
import 'tinymce/plugins/link' // 链接
import 'tinymce/plugins/code' // 代码
import 'tinymce/plugins/image'
import 'tinymce/plugins/media'
import 'tinymce/plugins/table' // 表单
import 'tinymce/plugins/lists'
import 'tinymce/plugins/contextmenu'
import 'tinymce/plugins/wordcount'
import 'tinymce/plugins/paste' // 张贴
import 'tinymce/plugins/autosave' // 自动保存
import 'tinymce/plugins/imagetools' // 图片
import 'tinymce/plugins/fullscreen'
import 'tinymce/plugins/preview'
// 自定义插件
// import './plugin/letterspacing/plugin' // 字间距
export default {
  name: 'TinymceEditor',
  components: {
    Editor
  },
  props: {
    value: {
      type: String,
      default: ''
    },
    inline: {
      type: Boolean
    },
    // 基本路径，默认为空根目录，如果你的项目发布后的地址为目录形式，
    // 即abc.com/tinymce，baseUrl需要配置成tinymce，不然发布后资源会找不到
    upload: {
      type: Object,
      default: () => {
        return {}
      }
    },
    disabled: {
      type: Boolean,
      default: false
    },
    plugins: {
      type: [String, Array],
      default: 'link code image media table lists contextmenu wordcount paste autosave imagetools fullscreen preview'
    },
    toolbar: {
      type: String,
      default: `undo redo | bold italic underline strikethrough | forecolor backcolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent |
                bullist numlist outdent indent | link lists image media table | pastetext removeformat |fullscreen `
    }
  },
  data () {
    return {
      init: {
        language_url: '/tinymce/langs/zh_CN.js',
        language: 'zh_CN',
        skin_url: '/tinymce/skins/ui/oxide',
        content_css: '/tinymce/skins/content/default/content.css',
        height: 500,
        plugins: this.plugins,
        toolbar: this.toolbar,
        // 一些常用的配置属性
        browser_spellcheck: true, // 拼写检查
        branding: false, // 是否禁用“Powered by TinyMCE”
        menubar: false, // 顶部菜单栏显示
        elementpath: false, // 禁用编辑器底部的状态栏
        fontsize_formats: '11px 12px 14px 16px 18px 24px 36px 48px 50px 56px 60px 64px',
        // 自定义字间距
        // letterspacing_val: '1px 2px 3px 4px 5px',
        paste_data_images: true,
        setup: (editor) => {
          // 自定义新的工具栏按钮
          // editor.ui.registry.addButton('customBtn', {
          //   tooltip: '新的工具栏按钮',
          //   text: '新的工具栏按钮',
          //   onAction: () => {
          //     window.alert('新的工具栏按钮')
          //   }
          // })
        },
        // 此处为图片上传处理函数，这个直接用了base64的图片形式上传图片，
        // 如需ajax上传可参考https://www.tiny.cloud/docs/configure/file-image-upload/#images_upload_handler
        images_upload_handler: async (blobInfo, success, failure) => {
          const headers = { 'Content-Type': 'multipart/form-data' }
          const formData = new FormData()
          const url = this.url
          formData.append('file', blobInfo.blob(), blobInfo.filename())
          if (this.params && this.params.length > 0) {
            this.params.forEach((p) => {
              formData.append(p.name, p.value)
            })
          }
          const { data } = await this.$axios.$post(url, formData, { headers })
          const img = data[this.imgUrl]
          // const img = 'data:image/jpeg;base64,' + blobInfo.base64()
          success(img)
        }
      },
      myValue: this.value,
      isShowFileDlg: false
    }
  },
  computed: {
    url () {
      return this.upload.url
    },
    params () {
      return this.upload.params
    },
    imgUrl () {
      return this.upload.imgUrl || 'url'
    }
  },
  watch: {
    value (newValue) {
      this.myValue = newValue
    },
    myValue (newValue) {
      this.$emit('input', newValue)
    }
  },
  mounted () {
    tinymce.init({})
  },
  methods: {
    setContent () {
      if (tinymce.activeEditor !== null && 'parser' in tinymce.activeEditor && 'parse' in tinymce.activeEditor.parser) { // When you first load, you rely on the content second times in V-model to use the setContent method to assign.
        tinymce.activeEditor.setContent(this.myValue, { format: 'raw' })
      }
    },
    // 添加相关的事件，可用的事件参照文档=> https://github.com/tinymce/tinymce-vue => All available events
    // 需要什么事件可以自己增加
    onClick (e) {
      this.$emit('onClick', e, tinymce)
    },
    // 可以添加一些自己的自定义事件，如清空内容
    clear () {
      this.myValue = ''
    }
  }
}
</script>
