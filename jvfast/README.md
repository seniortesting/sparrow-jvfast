## 平台简介

看到市场上有很多网站前后台模板框架，有很多做的很优秀也容易集成，比如[若依管理系统](http://ruoyi.vip/),[BladeX企业级开发平台](https://bladex.vip/),[renren-fast]()一直想做一款前后台整合的快速开发脚手架系统,一来方面自己快速做项目,再者给大家提供
一个学习参考的模板,防止走太多的坑. 于是JVFast就这样形成了,
JVFast是Java+Vue+Fast的拼写. 正如其名,我们的目的就是快速开发一台现代化主流的前后台脚手架系统. 

前台使用到的技术栈主要是依赖Element-UI进行二次开发的Avue做界面渲染,整体框架采用的是`Nuxt`,这个是区别于市面上很多采用Vue-CLI做的脚手架系统
,好处就是简单快速,nuxt已经相当成熟.内置的功能已经相当完善,可以节省更多的开发时间,而且支持单页和服务端渲染,这个对后期用到SEO有很多的帮助.

后台使用到的技术主要是SpringBoot+Mybatis+Shiro做的RBAC(Role Based Access Control)结构的权限系统.接口主要完成以下功能模块:

1.  用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2.  部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
3.  岗位管理：配置系统用户所属担任职务。
4.  菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5.  角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6.  字典管理：对系统中经常使用的一些较为固定的数据进行维护。
7.  通知公告：系统通知公告信息发布维护。
8.  操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
9.  登录日志：系统登录日志记录查询包含登录异常。
10. 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
11. 代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。
12. 系统接口：根据业务代码自动生成相关的api接口文档。
13. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
14. 在线构建器：拖动表单元素生成相应的HTML代码。
15. 分离打包: 将springboot的依赖jar包放在单独的lib目录.


可能出现的问题：

1. 运行命令： `./bootstrap.sh start`出现如下错误：

```
-bash: ./bootstrap.sh：/bin/bash^M：解释器错误: 没有那个文件或目录
```
有时候编写脚本时会出现类似标题列出的错误，这个问题大多数是因为你的脚本文件在windows下编辑过。windows下，每一行的结尾是`\n\r`，而在linux下文件的结尾是`\n`，那么你在windows下编辑过的文件在linux下打开看的时候每一行的结尾就会多出来一个字符\r,用`cat -A bootstrap.sh`时你可以看到这个\r字符被显示为^M，这时候只需要删除这个字符就可以了。可以使用命令`sed -i 's/\r$//' urfile` 。
可以使用如下命令：
```
sed -i 's/\r$//' bootstrap.sh
```


#### 在线体验演示

前端访问:
- 主站: [https://jvfast.pingbook.top]( https://jvfast.pingbook.top)
- 备用网站: [https://jvfast.yitieyilu.com]( https://jvfast.yitieyilu.com)

### 演示图

<table>
    <tr>
        <td><img src="https://oscimg.oschina.net/oscnet/25b5e333768d013d45a990c152dbe4d9d6e.jpg"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/e29fd81b2d43b517f99535564af41f9d1d5.jpg"/></td>
    </tr>
</table>

## 1. 前台配置

## 1.1 环境配置

- 下载`node`,并执行命令安装node配置`PATH`路径:

```$shell
$ cat /proc/cpuinfo 
Processor       : ARMv7 Processor rev 10 (v7l)
ARMv7 (and below) is 32-bit. ARMv8 introduces the 64-bit instruction set.
If you want to see whether your system supports 64-bit binaries, check the kernel architecture:
$ uname -m
$ wget https://nodejs.org/dist/v10.16.3/node-v10.16.3-linux-x64.tar.xz
$ tar xvf node-v10.16.3-linux-x64.tar.xz
$ mv node-v10.16.3-linux-x64 /opt/
$ sudo vi /etc/profile
增加对应的node的PATH路径,例如如下:
export path ="/opt/node-v10.16.3-linux-x64/bin:$PATH"

执行如下命令使上面的PATH配置生效
$ source /etc/profile 

执行如下命令确认node环境变量配置成功
$ node -v
$ npm -v 

1. 设置npm的全局安装包目录：
$ npm config set prefix  E:\nodejs\node_global
2. 设置npm的全局缓存目录：
$ npm config set cache E:\nodejs\node_cache
3. 设置npm和yarn的镜像为淘宝镜像
$ npm config set registry https://registry.npm.taobao.org
```

- 安装node的包管理器`yarn`(当然此处也可以不安装,直接使用node内置的`npm`来管理包):

```$shell
$ npm i yarn -g
$ yarn config set registry https://registry.npm.taobao.org
```

- 安装`pm2`命令用于后台服务静默执行:

```$shell
$ npm i pm2 -g

配置pm2开机启动脚本
$ pm2 startup 
```

## 1.1.1 测试环境运行调试

``` bash
# install dependencies
$ yarn install

# serve with hot reload at localhost:3000
$ yarn dev

# build for production and launch server
$ yarn build
$ yarn start

# generate static project
$ yarn generate
```

## 1.1.2 生产环境项目部署

- 复制当前所有包文件,解压,然后在当前文件夹下执行命令安装编译工程:

```$shell
$ yarn 
$ yarn build

编译程序, 然后配置pm2程序
$ pm2 start  ecosystem.config.js --watch
$ pm2 save
```

## 1.2 发布/新版更新

```$shell
# 首先复制源码目录文件到对应的服务器,然后直接如下命令:
$ yarn build
$ pm2 reload name| all
$ pm2 logs
```

- 执行pm2相关命令确认程序启动没有问题:

```shell script
$ pm2 list    # 查看所有的pm2程序
$ pm2 monit   # 查看pm2的所有程序的监控面板
$ pm2 reload
```

如果上述配置都没有问题,可以访问地址: `http://127.0.0.0:3000`查看启动程序,然后我们只需要在nginx配置
将所有请求反转到`nginx`,参考配置如下:

```
 # proxy for  project
 location  / {
          alias   /www/the project folder/;
          proxy_pass http://127.0.0.1:3000/;
          
          proxy_http_version      1.1;
          proxy_cache_bypass      $http_upgrade;
          
          proxy_set_header Upgrade                        $http_upgrade;
          proxy_set_header Connection             "upgrade";
          proxy_set_header Host                           $host;
          proxy_set_header X-Real-IP                      $remote_addr;
          proxy_set_header X-Forwarded-For        $proxy_add_x_forwarded_for;
          proxy_set_header X-Forwarded-Proto      $scheme;
          proxy_set_header X-Forwarded-Host       $host;
          proxy_set_header X-Forwarded-Port       $server_port;

 }
```


## 2. 后台配置运行

### 2.1 使用步骤
1. git clone该项目的所有源码到本地;
2. 执行`doc/schema.sql`中的所有sql脚本文件


### 2.2 如何打包?

1. Windows下修改`src\main\resources\bin\bootstrap.bat`中各个启动脚本为你的对应`APPLICATION`变量名,此处格式为`artifactId`, 
Linux直接运行目录下的`bootstrap.sh start`命令即可
2. 切换不同的maven的profile环境,打包为开发和生产环境包:
2.1 测试环境包: `mvn clean install` 或者 `mvn clean install -Pdev`
2.1 生产环境包: `mvn clean install -Pprod` 或者`mvn clean install --activate-profiles prod`

**注意事项**

1. 新表创建使用脚本: `doc/table-template.sql`模板脚本创建

#### 3. 交流群

JVFast学习交流群QQ群: JVFast学习交流(QQ群: **983459275**)

