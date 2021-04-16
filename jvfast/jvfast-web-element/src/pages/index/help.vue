<template>
  <el-card-container>
    <div>
      <h3>需要帮助</h3>
      <ul>
        <li><span class="title">邮件联系:</span>  <a href="mailto:alterhu2020@gmail.com">alterhu2020@gmail.com</a></li>
        <li>
          <span class="title">公众号:</span>
          <el-popover trigger="hover" placement="top-start" width="600">
            <img style="width: 600px;height: 250px" src="~/assets/img/wx/wxmp.png">
            <a slot="reference">胡萝卜网(CarrotSoft)</a>
          </el-popover>
        </li>
        <li>
          <span class="title">QQ群:</span>
          <el-popover trigger="hover" placement="top-start" width="330">
            <img style="width: 330px;height: 350px" src="~/assets/img/wx/qqgroup.png">
            <a slot="reference">JVFast学习交流(983459275)</a>
          </el-popover>
        </li>
      </ul>
    </div>
  </el-card-container>
</template>

<script>
export default {
  name: 'Help'
}
</script>

<style lang="scss" scoped>
   ul {
     margin-top: 15px;
     padding: 10px 15px;
   }
   .title {
     margin-right: 10px;
     line-height: 22px;
     font-size: 14px;
     font-weight: 600;
     color: #111;
     overflow: hidden;
   }
   a {
     text-decoration: none;
     margin-right: 40px;
   &:hover{
      /*background-color: #409EFF;*/
      color:#409EFF;
    }
   }
</style>
