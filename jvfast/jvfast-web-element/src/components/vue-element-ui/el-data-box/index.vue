<template>
  <div class="data-box">
    <el-row :span="24">
      <el-col
        v-for="(item,index) in data"
        :key="index"
        :md="span"
        :xs="24"
        :sm="12"
      >
        <div class="item">
          <a
            :href="item.href?item.href:'javascript:void(0);'"
            :target="item.target"
            @click="item.click?item.click(item):''"
          >
            <div
              class="item-icon"
              :style="{backgroundColor:item.color}"
            >
              <i :class="item.icon" />
            </div>
            <div class="item-info">
              <el-count-up
                :animation="animation"
                :style="{color:item.color}"
                class="title"
                :end="item.count"
              />
              <div class="info">{{ item.title }}</div>
            </div>
          </a>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import ElCountUp from '../el-count-up'
export default {
  name: 'Index',
  components: { ElCountUp },
  props: {
    span: {
      type: Number,
      default: 8
    },
    animation: {
      type: String,
      default: ''
    },
    data: {
      type: Array,
      default: () => {
        return []
      }
    }
  }
}
</script>

<style lang="scss" scoped>
  .data-box {
    $height: 100px;
    $radius: 5px;
  .item {
    position: relative;
    margin: 0 auto 10px auto;
    width: 96%;
    height: $height;
    overflow: hidden;
    border-radius: $radius;
    box-sizing: border-box;
  &:hover .item-text {
     top: 0;
   }
  a {
    display: flex;
  }
  }
  .item-icon {
    width: 100px;
    height: $height;
    color: #fff;
    text-align: center;
    line-height: $height;
    align-items: center;
    justify-content: center;
    display: flex;
  i {
    font-size: 48px !important;
  }
  }
  .item-info {
    border-radius: 0 $radius $radius 0;
    border: 1px solid #eee;
    border-left: none;
    background-color: #fff;
    flex: 1;
    display: flex;
    justify-content: center;
    flex-direction: column;
  .title {
    font-size: 30px;
    line-height: 40px;
    text-align: center;
  }
  .info {
    color: #999;
    font-size: 14px;
    text-align: center;
  }
  }
  }
</style>
