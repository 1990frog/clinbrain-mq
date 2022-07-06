#### clinbrain mq 包装了mq，用来实现各种不同现场需要适配短信，邮箱，等通讯接口

### 短信模板处理
com.clinbrain.mq.service 下提供了BaseSmsService 主要实现模板的解析，com.clinbrain.mq.service.custom下为实现类  

自己实现的类必须用 @Service(value="") 标注，value的值为 spring.profile.active的值 + "Service"
- 默认使用 " {0}模板,{1}模板内容{2} " 这种方式使用模板
- 可以自己写实现继承BaseSmsService 来重写 parseTemplate 方法，注意需要标注@Service的value
----
模板解析:  有些短信平台需要先注册模板,然后直接发送参数就行,这时就可以在本平台创建一样的模板内容,然后自己做实现拼接短信发送内容


### 短信发送： 

com.clinbrain.mq.message 下为短信接口实现
统一实现接口ISmsSender， 需要添加的配置在resource 下添加自己的 application-XXX.yml 配置
实现为具体的短信发送方式,调用第三方平台接口发送短信

#### 开发进度:

**2022.7.6**  
* 添加南阳医专短信平台对接



