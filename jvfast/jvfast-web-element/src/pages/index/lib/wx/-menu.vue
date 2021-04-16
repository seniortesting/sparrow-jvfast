<template>
  <div>
    <div class="menu-input-group" style="border-bottom: 2px #e8e8e8 solid;">
      <div class="menu-name">
        {{ button.name }}
      </div>
      <div class="menu-del" @click="$emit('delMenu')">
        删除菜单
      </div>
    </div>
    <div class="menu-input-group">
      <div class="menu-label">
        菜单名称
      </div>
      <div class="menu-input">
        <input
          v-model="button.name"
          type="text"
          name="name"
          placeholder="请输入菜单名称"
          class="menu-input-text"
          @input="checkMenuName(button.name)"
        >
        <p v-show="menuNameBounds" class="menu-tips" style="color:#e15f63">
          字数超过上限
        </p>
        <p class="menu-tips">
          字数不超过{{ selectedMenuLevel==1?'5':'8' }}个汉字
        </p>
      </div>
    </div>
    <div v-show="!button.subButtons || button.subButtons.length==0">
      <div class="menu-input-group">
        <div class="menu-label">
          菜单内容
        </div>
        <div class="menu-input">
          <select v-model="button.type" name="type" class="menu-input-text">
            <option value="view">
              跳转网页(view)
            </option>
            <option value="mediaId">
              发送消息(mediaId)
            </option>
            <!--<option value="view_limited">跳转公众号图文消息链接(view_limited)</option>-->
            <option value="miniprogram">
              打开指定小程序(miniprogram)
            </option>
            <option value="click">
              自定义点击事件(click)
            </option>
            <option value="scancode_push">
              扫码上传消息(scancode_push)
            </option>
            <option value="scancode_waitmsg">
              扫码提示下发(scancode_waitmsg)
            </option>
            <option value="pic_sysphoto">
              系统相机拍照(pic_sysphoto)
            </option>
            <option value="pic_photo_or_album">
              弹出拍照或者相册(pic_photo_or_album)
            </option>
            <option value="pic_weixin">
              弹出微信相册(pic_weixin)
            </option>
            <option value="location_select">
              弹出地理位置选择器(location_select)
            </option>
          </select>
        </div>
      </div>
      <div v-if="button.type=='view'" class="menu-content">
        <div class="menu-input-group">
          <p class="menu-tips">
            订阅者点击该子菜单会跳到以下链接
          </p>
          <div class="menu-label">
            页面地址
          </div>
          <div class="menu-input">
            <input v-model="button.url" type="text" placeholder="" class="menu-input-text">
          </div>
        </div>
      </div>
      <div v-else-if="button.type=='mediaId'" class="menu-content">
        <div class="menu-input-group">
          <p class="menu-tips">
            订阅者点击该菜单会收到以下图文消息
          </p>
          <div class="menu-label">
            media_id
          </div>
          <div class="menu-input">
            <input v-model="button.mediaId" type="text" placeholder="图文消息mediaId" class="menu-input-text">
          </div>
        </div>
      </div>
      <div v-else-if="button.type=='miniprogram'" class="menu-content">
        <div class="menu-input-group">
          <p class="menu-tips">
            订阅者点击该子菜单会跳到以下小程序
          </p>
          <div class="menu-label">
            小程序appId
          </div>
          <div class="menu-input">
            <input v-model="button.appId" type="text" placeholder="小程序的appId（仅认证公众号可配置）" class="menu-input-text">
          </div>
        </div>
        <div class="menu-input-group">
          <div class="menu-label">
            小程序路径
          </div>
          <div class="menu-input">
            <input v-model="button.pagePath" type="text" placeholder="小程序的页面路径 pages/index/index" class="menu-input-text">
          </div>
        </div>
        <div class="menu-input-group">
          <div class="menu-label">
            备用网页
          </div>
          <div class="menu-input">
            <input v-model="button.url" type="text" placeholder="" class="menu-input-text">
            <p class="menu-tips">
              旧版微信客户端无法支持小程序，用户点击菜单时将会打开备用网页。
            </p>
          </div>
        </div>
      </div>
      <div v-else class="menu-content">
        <div class="menu-input-group">
          <p class="menu-tips">
            用于消息接口推送，不超过128字节
          </p>
          <div class="menu-label">
            菜单KEY值
          </div>
          <div class="menu-input">
            <input v-model="button.key" type="text" placeholder="" class="menu-input-text">
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    selectedMenuLevel: {
      type: Number,
      default: 1
    },
    button: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      menuNameBounds: false// 菜单长度是否过长
    }
  },
  methods: {
    // 检查菜单名称长度
    checkMenuName (val) {
      if (this.selectedMenuLevel === 1 && this.getMenuNameLen(val) <= 10) {
        this.menuNameBounds = false
      } else if (this.selectedMenuLevel === 2 && this.getMenuNameLen(val) <= 16) {
        this.menuNameBounds = false
      } else {
        this.menuNameBounds = true
      }
    },
    // 获取菜单名称长度
    getMenuNameLen (val) {
      let len = 0
      for (let i = 0; i < val.length; i++) {
        const a = val.charAt(i)
        // eslint-disable-next-line no-control-regex
        a.match(/[^\x00-\xFF]/ig) != null ? len += 2 : len += 1
      }
      return len
    }
  }
}
</script>
<style scoped>

  /*菜单内容*/
  .menu-name {
    float: left;
    height: 40px;
    line-height: 40px;
    font-size: 18px;
  }

   .menu-del {
    float: right;
    height: 40px;
    line-height: 40px;
    color: #459ae9;
    cursor: pointer;
  }

   .menu-input-group {
    width: 540px;
    margin: 10px 0 30px 0;
    overflow: hidden;
  }

   .menu-label {
    float: left;
    height: 30px;
    line-height: 30px;
    width: 80px;
    text-align: right;
  }

   .menu-input {
    float: left;
    width: 380px
  }

   .menu-input-text {
    border: 0px;
    outline: 0px;
    background: #fff;
    width: 300px;
    padding: 5px 0px 5px 0px;
    margin-left: 10px;
    text-indent: 10px;
    height: 35px;
  }

   .menu-tips {
    color: #8d8d8d;
    padding-top: 4px;
    margin: 0;
  }

   .menu-tips.cursor {
    color: #459ae9;
    cursor: pointer;
  }

   .menu-input .menu-tips {
    margin: 0 0 0 10px;
  }

   .menu-content {
    padding: 16px 20px;
    border: 1px solid #e7e7eb;
    background-color: #fff;
  }

   .menu-content .menu-input-group {
    margin: 0px 0 10px 0;
  }

   .menu-content .menu-label {
    text-align: left;
    width: 100px;
  }

   .menu-content .menu-input-text {
    border: 1px solid #e7e7eb;
  }

   .menu-content .menu-tips {
    padding-bottom: 10px;
  }

   .menu-msg-content {
    padding: 0;
    border: 1px solid #e7e7eb;
    background-color: #fff;
  }

   .menu-msg-content .menu-msg-head {
    overflow: hidden;
    border-bottom: 1px solid #e7e7eb;
    line-height: 38px;
    height: 38px;
    padding: 0 20px;
  }

   .menu-msg-content .menu-msg-panel {
    padding: 30px 50px;
  }

   .menu-msg-content .menu-msg-select {
    padding: 40px 20px;
    border: 2px dotted #d9dadc;
    text-align: center;
  }

   .menu-msg-content .menu-msg-select:hover {
    border-color: #b3b3b3;
  }

   .menu-msg-content strong {
    display: block;
    padding-top: 3px;
    font-weight: 400;
    font-style: normal;
  }

   .menu-msg-content .menu-msg-title {
    float: left;
    width: 310px;
    height: 30px;
    line-height: 30px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

</style>
