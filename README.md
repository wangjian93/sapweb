# sapweb

## 简介

> 本项目为SpringBoot类型的Java后台管理系统，实现了后台管理的一些基本功能。公司使用SAP系统，会需要将SAP的数据提供给外部系统使用，和将外部
> 系统的数据维护至SAP中。本项目中使用SAPJCO3调用SAP系统的RFC函数来获取或传送数据，然后以接口的方式对外提供服务，并提供定时器功能来实现对
> 数据定时同步任务。

<br>

## 使用技术

### 后台
描述    |    框架 
:---|:---
核心框架 | Spring、Spring Boot、Spring MVC
持久层 | MyBatis、MyBatis-Plus
数据源 | Druid
权限框架 | Shiro
模板引擎 | thymeleaf
SAP访问 | sapjco3
定时任务调度 | Quartz

### 前端
描述 | 框架 
:---|:---
核心框架 | [Layui](http://www.layui.com/)、  [jQuery](http://jquery.cuishifeng.cn/)
路由框架 | [Q.js](https://github.com/itorr/q.js)  (纯js轻量级路由框架)
mvvm框架 | [pandyle.js](https://gitee.com/pandarrr/pandyle)  (专为jquery编写的mvvm)
主要特色 | 单页面 / 响应式 / 简约 / 极易上手

> 开发工具为IDEA，数据库文件存放在项目的`src/main/resources/sql`目录下。


## 项目结构

### 后台结构
```text
|-src
    |-main
          |-java
          |      |-com.ivo.sapweb
          |             |-common        // 核心模块
          |             |      |-captcha              // 验证码                
          |             |      |-config               // 存放系统配置类
          |             |      |-core                 // 系统核心数据模型类
          |             |      |-exception            // 自定义异常类，统一异常处理器
          |             |      |-shiro                // shiro配置模块
          |             |      |-util                 // 工具类
          |             |      |-BaseController.java    
          |             |      |-JsonResult.java
          |             |      |-PageResult.java
          |             |-mm           // 采购业务模块
          |             |-quartz       // 定时器模块
          |             |-sap          // SAP模块
          |             |-system       // 系统管理模块
          |             |-SapwebApplication.java     // SpringBoot启动类
          |              
          |-resources
          |      |-mapper     // mapper文件
          |      |      |-bapi
          |      |      |-quartz
          |      |      |-system
          |      |-sapjco                   // sap需要的jar和各平台需要的资源库
          |      |-shiro                    // shiro的缓存配置
          |      |-sql                      // 系统的sap文件
          |      |-static                   // css、js、图片文件
          |      |-templates                // html文件
          |      |-application.yml          // 配置文件
          |      |-application-dev.yml  
          |       |-application-pro.yml
          |       |-application-ter.yml
          |       |-quartz.properties        // quarzt配置
          |       |-SAPConnectionPool.properties   // sap连接池配置
          
```

### 前台结构
```text
|-assets
    |     |-css                     // 样式
    |     |-images                  // 图片
    |     |-libs                    // 第三方库
    |
    |-components            // html组件
    |     |-system                  // 系统管理页面
    |     |-xxxxxx                  // 其他业务页面
    |     |-tpl                     // 公用组件
    |     |     |-message.html                 // 消息
    |     |-console.html            // 主页一
    |     |-header.html             // 头部
    |     |-side.html               // 侧导航
    |
    |-module                // js模块 (使用layui的模块开发方式)
    |     |-admin.js                // admin模块
    |     |-config.js                // config模块
    |     |-index.js                // index模块
    |
    |-index.html            // 主界面
    |-login.html            // 登陆界面
```

## 快速上手
### 后台快速上手

**如何添加自己的业务代码：**

&emsp;&emsp; 跟common、system同级建一个包，名字为你的业务模块名称，然后下面依次建controller、dao、model、service、service.impl等包，
&emsp;&emsp; 然后在resource/mapper下面也建一个模块文件夹，里面放mapper的xml文件。

- "MyBatis的Mpper.xml" 扫描路径是 `classpath:mapper/**/*Mapper.xml`
- "MyBatis的mapper接口" 扫描路径是 `com.ivo.sapweb.*.dao`, `com.ivo.sapweb.*.repository`
- "MyBatis的实体类" 扫描路径是 `com.ivo.sapweb.*.model`, `com.ivo.sapweb.*.entity`

### 前台台快速上手

**前端项目：**

&emsp;&emsp; css、图片、第三方lib（layui扩展模块除外）全部放在“/assets/”下面，layui扩展模块放在“module”下面，页面html放在“template”下面

    