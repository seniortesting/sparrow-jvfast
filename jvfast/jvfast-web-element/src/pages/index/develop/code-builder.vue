<template>
  <el-card-container>
    <div>
      <h3>介绍</h3>
      <p>{{ website.name }}采用的代码生成工具是基于后台的 <code>mybatis-plus</code>的generator工具, 默认我们认为是开发者来执行代码生成工作.</p>
    </div>
    <div>
      <h3>开始使用</h3>
      <p>1. 打开后台项目<code>jvfast-api</code>,切换到模块：<code>jvfast-generator</code> 定位到代码文件: <code>src/test/java/com/jvfast/generator/MybatisPlusCodeGenerator.java</code>, 直接运行;</p>
      <p>2. 运行前需要对<code>MybatisPlusCodeGenerator</code>做一些简单的调整:</p>
      <p>2.1 修改数据库相关配置信息,对应的参数是: <code>DRIVER_URL</code>, <code>DRIVER_NAME</code>, <code>USER_NAME</code>, <code>PASSWORD</code></p>
      <p>2.2 修改产生的后台源码存放的根目录文件,对应的参数是: <code>OUTPUT_PACKAGE_NAME</code></p>
      <p>2.3 修改对应的表对应的模块名称,jvfast的数据库表设计遵循阿里java开发规约,命名格式为:模块名+下划线+业务名,所以需要设置一个模块名. 模块名后对应上面设置的根目录文件目录, 对应的参数是: <code>MODULE_NAME</code></p>
      <p>2.4 是否需要覆盖已经存在的同名文件,如果执行过程中在目录下发现同名的文件是否需要覆盖,对应的参数是: <code>OVERIDE_FILE</code>, 该参数主要用于多次运行脚本避免覆盖已经修改过的文件</p>
      <p>2.5 如果需要产生前台页面代码带有avue权限标识的代码,需要设置参数: <code>GENERATE_PERMISSION</code></p>
      <p>3. 完成以上的配置参数后,直接运行,按照提示输入对应的数据库表名(表名格式为:模块名+下划线+业务名),多个表需要以逗号分隔. 如果需要执行所有的表,输入<code>a</code>即可 </p>
      <p>4. 直接复制产生的前台代码目录<b>src</b>(<code>src/main/resources/templates/src</code>)到jvfast-web根目录覆盖<b>src</b>目录即可</p>
    </div>
    <div>
      <h3>注意事项</h3>
      <p>1. 所有的前后台代码模板文件位于: <code>src/test/resources/templates</code>,其中前台的源码模块位于子目录: <code>frontend</code></p>
    </div>
  </el-card-container>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'CodeBuilder',
  data () {
    return {
    }
  },
  computed: {
    ...mapGetters(['website'])
  },
  methods: {
  },
  head () {
    return {
      title: '前后台代码生成工具 | ' + this.website.name,
      meta: [
        { hid: 'description', name: 'description', content: '前后台代码生成工具' }
      ]
    }
  }
}
</script>

<style scoped>
  h3 {
    color: #2f495e;
    position: relative;
    font-size: 1.875rem;
    display: table;
    margin-bottom: 2rem;
  }
  p {
    margin-bottom: 20px;
    word-break: break-word;
    display: block;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
    font-weight: 400;
    line-height: 1.8;
    font-size: 16px;
    font-family: -apple-system,BlinkMacSystemFont,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol","Segoe UI","PingFang SC","Hiragino Sans GB","Microsoft YaHei","Helvetica Neue",Helvetica,Arial,sans-serif;
    font-feature-settings: "tnum";
    font-variant: tabular-nums;
  }
  code {
    word-wrap: normal;
    word-break: break-all;
    white-space: pre;
    overflow-x: scroll;
    overscroll-behavior-x: contain;
    margin-top: 0;
    margin-bottom: 20px;
    border-radius: 4px;
    z-index: 0;
    padding: 0.2em;
    line-height: 1.5;
    color: #ccc;
    background: #2d2d2d;
  }
</style>
