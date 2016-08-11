Welcome to the ppTemplate wiki!
 欢迎来到鹏鹏模板wiki！

This is a tool for sending post request to alipay,wxpay,cuppay more conveniently and efficiently.
 这个工具使你可以更加便捷与高效的向支付宝、微信支付、银联支付发送post请求。
 By template, you can pay more attention to your business rather than reqest body style like json, xml or others.
 你可以不再关注发送报文本身是json、xml或者是像支付宝一样的特殊格式，一切都可以通过模板请求转换。

How to start(开始)：
1.打开src\main\java\com\xinyipay\cmi\core\support\CommConfig.java文件进行相关配置。
2.根据需要配置src\main\resources\template下的模板。

3.模板说明：

1）##之间为需要替换的参数key，与请求参数map中的key对应。

2）~为组分隔符。

3）src\main\resources\template下的模板中有具体的示例。


How to use(使用说明)：
1.assemble parameters(组装发送参数)
2.get sign(获取签名)
3.create request body(创建请求报文)
4.send post request(发送请求报文)

WeixinTest.java in the path(src\test\java\com\xinyipay\cmi\core\weixin\WeixinTest.java) can help you understand how to use.
 你可以在路径（src\test\java\com\xinyipay\cmi\core\weixin\WeixinTest.java）下找到WeixinTest.java参考示例。

