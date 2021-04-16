<template>
  <div class="avue-map">
    <el-button
      :disabled="disabled"
      class="avue-map__submit"
      @click="box=true"
    >
      {{ textTitle }}
    </el-button>
    <el-dialog
      :visible.sync="box"
      fullscreen
      center
      class="avue-map__dialog"
      width="100%"
      append-to-body
      modal-append-to-body
      title="选择坐标"
      @close="handleClose"
    >
      <div
        v-if="box"
        class="avue-map__content"
      >
        <el-input
          id="map__input"
          v-model="text"
          class="avue-map__content-input"
          size="small"
          clearable
          placeholder="输入关键字选取地点"
        />
        <div class="avue-map__content-box">
          <div
            id="map__container"
            class="avue-map__content-container"
            tabindex="0"
          />
          <div
            id="map__result"
            class="avue-map__content-result"
          />
        </div>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          @click="handleSubmit()"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'VueMap',
  props: {
    lnglat: {
      type: Object,
      default: () => {
        return {}
      }
    },
    disabled: {
      type: Boolean,
      default: false
    },
    value: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      poi: {},
      marker: null,
      map: null,
      text: '',
      box: false
    }
  },
  computed: {
    isPR () {
      return this.R && this.P
    },
    P () {
      return this.lnglat.P || 0
    },
    R () {
      return this.lnglat.R || 0
    },
    textTitle () {
      return this.poi.formattedAddress === undefined ? '选择坐标' : '重新选择(' + this.poi.formattedAddress + ')'
    }
  },
  watch: {
    box: {
      handler () {
        if (this.box) {
          this.$nextTick(() =>
            this.init(() => {
              if (this.isPR) {
                this.addMarker(this.R, this.P)
                this.getAddress(this.R, this.P)
              }
            })
          )
        }
      },
      immediate: true
    }
  },
  mounted () {
    console.log(`获取value: ${this.value}`)
  },
  methods: {
    // 新增坐标
    addMarker (R, P) {
      this.clearMarker()
      this.marker = new window.AMap.Marker({
        position: [R, P]
      })
      this.marker.setMap(this.map)
    },
    // 清空坐标
    clearMarker () {
      if (this.marker) {
        this.marker.setMap(null)
        this.marker = null
      }
    },
    // 获取坐标
    getAddress (R, P) {
      // eslint-disable-next-line no-new,new-cap
      new window.AMap.service('AMap.Geocoder', () => {
        // 回调函数
        const geocoder = new window.AMap.Geocoder({})
        geocoder.getAddress([R, P], (status, result) => {
          if (status === 'complete' && result.info === 'OK') {
            const regeocode = result.regeocode
            this.poi = Object.assign(regeocode, {
              longitude: R,
              latitude: P
            })
            this.text = regeocode.formattedAddress
            // 自定义点标记内容
            const markerContent = document.createElement('div')

            // 点标记中的图标
            const markerImg = document.createElement('img')
            markerImg.src =
                '//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png'
            markerContent.appendChild(markerImg)

            // 点标记中的文本
            const markerSpan = document.createElement('span')
            markerSpan.className = 'avue-map__marker'
            markerSpan.innerHTML = this.text
            markerContent.appendChild(markerSpan)
            this.marker.setContent(markerContent) // 更新点标记内容
          }
        })
      })
    },
    handleClose () {
      this.text = ''
      window.poiPicker.clearSearchResults()
      // this.poi = {}
    },
    handleSubmit () {
      const value = this.poi.formattedAddress
      this.$emit('input', value)
      // this.poi = {}
      this.box = false
    },
    addClick () {
      this.map.on('click', (e) => {
        const lnglat = e.lnglat
        const P = lnglat.P
        const R = lnglat.R
        this.addMarker(R, P)
        this.getAddress(R, P)
      })
    },
    init (callback) {
      this.map = new window.AMap.Map('map__container', {
        zoom: 13,
        center: (() => {
          if (this.isPR) {
            return [this.R, this.P]
          }
        })()
      })
      this.initPoip()
      this.addClick()
      callback()
    },
    initPoip () {
      window.AMapUI.loadUI(['misc/PoiPicker'], (PoiPicker) => {
        const poiPicker = new PoiPicker({
          input: 'map__input',
          placeSearchOptions: {
            map: this.map,
            pageSize: 10
          },
          searchResultsContainer: 'map__result'
        })
        // 初始化poiPicker
        this.poiPickerReady(poiPicker)
      })
    },
    poiPickerReady (poiPicker) {
      window.poiPicker = poiPicker
      // 选取了某个POI
      poiPicker.on('poiPicked', (poiResult) => {
        this.clearMarker()
        const source = poiResult.source
        const poi = poiResult.item
        this.text = poi.name
        this.poi = poi
        if (source !== 'search') {
          poiPicker.searchByKeyword(poi.name)
        }
      })
    }
  }
}
</script>

<style lang="scss">
  .amap-icon img,
  .amap-marker-content img {
    width: 25px;
    height: 34px;
  }
  .avue-map {
    &__submit {
      width: 100%;
    }
    &__marker {
      position: absolute;
      top: -20px;
      right: -118px;
      color: #fff;
      padding: 4px 10px;
      box-shadow: 1px 1px 1px rgba(10, 10, 10, 0.2);
      white-space: nowrap;
      font-size: 12px;
      font-family: "";
      background-color: #25a5f7;
      border-radius: 3px;
    }
    &__content {
      &-input {
        margin-bottom: 10px;
      }
      &-box {
        position: relative;
      }
      &-container {
        width: 100%;
        height: 450px;
      }
      &-result {
        display: block !important;
        position: absolute;
        top: 0;
        right: -8px;
        width: 250px;
        height: 450px;
        overflow-y: auto;
      }
    }
  }
</style>
