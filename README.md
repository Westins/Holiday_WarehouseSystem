# Holiday_WarehouseSystem
## 项目介绍
* 这是一个仓库管理系统，实现了对商品的进货、销售、退货以及人员管理、权限管理分配等操作，集成Echarts使数据呈现图表等形式可视化。
## 所使用的技术栈
* 后端
    * Spring Boot: 容器和MVC框架
    * Mybatis-Plus: 持久层框架、简单CURD生成、复杂SQL编写
        * PaginationInterceptor: Mybatis-Plus 分页插件
    * shiro: 用户验证、用户授权
    * Redis: 数据缓存处理
    * Druid: 数据库连接池
* 前端
    * Layui: 前端框架
    * Thymeleaf: 模板引擎
    * Echarts: 数据图表
* 插件
    * Log4j: 日志
    * Lombok: 生成样板式代码
    * Hutool: 工具包
    * lang3: 工具包
* 工具
    * IDEA 2019
    * Git
* 其他
    * windows 10
    * jdk 1.8
    * maven 3.6
    * Tomcat 8.5
    * MySQL
## 主要功能
![系统功能](https://github.com/Westins/holiday_WarehouseSystem/raw/master/demoImg/仓库管理系统功能图.png) 
## 系统部分功能截图
![登录页面](https://github.com/Westins/holiday_WarehouseSystem/raw/master/demoImg/登录界面.png) 
![登录主页(工作台)](https://github.com/Westins/holiday_WarehouseSystem/raw/master/demoImg/工作台.png) 
![商品管理](https://github.com/Westins/holiday_WarehouseSystem/raw/master/demoImg/商品管理.png) 
![权限管理](https://github.com/Westins/holiday_WarehouseSystem/raw/master/demoImg/权限管理_显示查询.png) 
![角色权限分配](https://github.com/Westins/holiday_WarehouseSystem/raw/master/demoImg/角色权限分配.png) 
## 项目心得总结
* 项目独立完成，学习并掌握Shiro、Redis等技术的基本使用，对Shiro基本工作原理和Redis的存储数据类型以及原理都有一定的了解。
* 深度结合阿里编码规约，规范编码格式。
* 深刻理解MVC开发模式。
* 活络思维，从不同的角度思考问题。
## 不足以及后期改进
* 查询并转换成图表、树结构所需要的数据格式稍显生涩，需加强提升SQL编写能力，提升算法能力。
* 有部分的代码沉积，后期提炼代码，精简代码。
* 后期学习Avitivi流程框架，完成员工请假审批功能。
