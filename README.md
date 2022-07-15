# micro-service

基于springCloudAlibaba的后端通用基础服务，技术栈使用springBoot,nacos,gateway,sentinel,
seata,openFeign,xxjob,redis,mq,mybaits。该项目主要用于自己学习使用，记录一些工作中有用的轮子。

## 项目情况记录

- cloud-common
    - 日常工具类整理，公共信息定义
- cloud-starters
    - cloud-nacos-discovery-starter  
           日常开发中前后端联调时，业务往往基于多个服务，常常需要mock很多非真实数据，常常遗漏多种业情况。该starter基于[spring-cloud-starter-alibaba-nacos-discovery](https://github.com/alibaba/spring-cloud-alibaba/tree/master/spring-cloud-alibaba-starters/spring-cloud-starter-alibaba-nacos-discovery)
           添加了灰度联调模式。（也可以用于上线后的回归测试，慎用防止数据污染）
        1. 使用  
           前端小伙伴需要在cook中设置debugVersion=xxx,(版本获取规则顺序 cook>header>parameter)后端小伙伴把需要联调的服务设置如下启动参数：

       ```  
      -Dspring.cloud.nacos.discovery.metadata.debugVersion=XXXX
       ```  
        2. 基本逻辑  
      通过[DebugPatternNacosRule](src/main/java/com/zq/cloud/starter/nacos/discovery/rule/DebugPatternNacosRule.java)
      进行调试服务选取，通过[OpenFeignDebugPatternRequestInterceptor](src/main/java/com/zq/cloud/starter/nacos/discovery/NacosDiscoveryAutoConfiguration.java)
      进行调试版本号传递。  
        3.注意的点  
      在调用下游服务时，如果时自建线程池中使用openFeign调用下游服务，需要自行注入调试版本号[DebugPatternFromCustomizeContext](src/main/java/com/zq/cloud/starter/nacos/discovery/config/DebugPatternFromCustomizeContext.java)
  - cloud-mybatis-starter  
    
    日常开发中经常批量修改单表数据库，新增了一个批量修改的方法。
  - cloud-longging-starter  
    统一logback配置,不用每个项目都去复制一份
  - cloud-redis-starter  
    redis默认的序列话使用的是jdk自带的JdkSerializationRedisSerializer,序列话性能不是特别理想，使用
    Kryo进行了替换。 参考[StringRedisSerializer](org.springframework.data.redis.serializer.StringRedisSerializer)自定义了key序列化前缀，对不同服务
    的数据精选2次隔离。

- cloud-module  
    - cloud-authuser  
    用户服务：基于RBAC模式的一个简单用户管理服务，实现了密码加密传输，验证码登录，接口权限管理，没什么太大的参考价值。主要作用为是为了让其他微服务的使用串联起来。
    - cloud-gateway  
    网关服务：实现了常规的统一鉴权，动态路由，统一日志记录，限流，整合了之前的[cloud-nacos-discovery-starter]()实现了调试服务的分发。
    - cloud-file  
    文件服务：整合了fastDfs和阿里云oss作为存储介质，提供了基础的文件上传,下载,预览功能。基于阿里云oss最佳实践，实现了一套后端签名前端直传回调的功能,可以减少文件传输的不必要的中间层。
    - cloud-sms
    待实现
      


***

后续计划：链路追踪，ELK搭建，docker，代码生成,分布式事务

        
