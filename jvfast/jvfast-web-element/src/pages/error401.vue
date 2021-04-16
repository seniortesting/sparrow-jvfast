<template>
  <div class="error403">
    <div class="error403-body-con">
      <el-card class="box-card">
        <div class="error403-body-con-title">
          4
          <span class="error403-0-span">
            0
          </span>
          <span class="error403-key-span">
            1
          </span>
        </div>
        <p class="error403-body-con-message">
          抱歉，你无权访问该页面
        </p>
        <div class="error403-btn-con">
          <el-button size="large" style="width: 150px;" type="text" @click="goHome">
            返回首页
          </el-button>
          <el-button size="large" style="width: 150px;margin-left: 40px;" type="primary" @click="backPage">
            返回上一页
          </el-button>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import config from '@/config'
export default {
  name: 'NoAuth',
  methods: {
    backPage () {
      this.$router.go(-1)
    },
    goHome () {
      this.$router.push({
        name: config.defaultSettings.page.homeComponentName
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  @keyframes error403animation {
    0% {
      transform: rotateZ(0deg);
    }
    40% {
      transform: rotateZ(-20deg);
    }
    45% {
      transform: rotateZ(-15deg);
    }
    50% {
      transform: rotateZ(-20deg);
    }
    55% {
      transform: rotateZ(-15deg);
    }
    60% {
      transform: rotateZ(-20deg);
    }
    100% {
      transform: rotateZ(0deg);
    }
  }
  .error403 {
    &-body-con {
      width: 700px;
      height: 500px;
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%);
      &-title {
        text-align: center;
        font-size: 240px;
        font-weight: 700;
        color: #2d8cf0;
        height: 260px;
        line-height: 260px;
        margin-top: 40px;
        .error403-0-span {
          display: inline-block;
          position: relative;
          width: 170px;
          height: 170px;
          border-radius: 50%;
          /*border: 20px solid #ed3f14;*/
          color: #ed3f14;
          margin-right: 10px;
          i {
            display: inline-block;
            font-size: 120px !important;
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
          }
        }
        .error403-key-span {
          display: inline-block;
          position: relative;
          width: 100px;
          height: 190px;
          border-radius: 50%;
          margin-right: 10px;
          i {
            display: inline-block;
            font-size: 190px !important;
            position: absolute;
            left: 20px;
            transform: translate(-50%, -60%);
            transform-origin: center bottom;
            animation: error403animation 2.8s ease 0s infinite;
          }
        }
      }
      &-message {
        display: block;
        text-align: center;
        font-size: 30px;
        font-weight: 500;
        letter-spacing: 4px;
        color: #dddde2;
      }
    }
    &-btn-con {
      text-align: center;
      padding: 20px 0;
      margin-bottom: 40px;
    }
  }
</style>
