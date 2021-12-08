#### clinbrain mq 包装了mq，用来实现各种不同现场需要适配短信，邮箱，等通讯接口

### 短信模板映射
com.clinbrain.mq.service 下提供了BaseSmsService 主要实现模板的解析，com.clinbrain.mq.service.custom下为实现类  

自己实现的类必须用 @Service(value="") 标注，value的值为 spring.profile.active的值 + "Service"
- 默认使用 " {0}模板,{1}模板内容{2} " 这种方式使用模板
- 可以自己写实现继承BaseSmsService 来重写 parseTemplate 方法，注意需要标注@Service的value


### 短信发送实现： 

com.clinbrain.mq.message 下为短信接口实现  
统一实现接口ISmsSender ， 需要添加的配置在resource 下添加自己的 application-XXX.yml 配置

不想写了。自己看代码应该也能懂了



