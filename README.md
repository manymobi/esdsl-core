EsDsl elasticsearch query mapper framework for Java
===============================================

EsDsl 框架使得将 Elasticsearch 与面向对象应用程序结合使用变得更加容易。


# 如何使用?

```java
       RestClient restClient;

        esdsl = new Esdsl.Build()
                // esdsl文件目录
                .setEsdslFileResourceHandler(new PathEsdslFileResourceHandler(new File("src/test/resources/")))   
                // 设置Elasticsearch客户端
                .setRestHandler(new DefaultRestHandler(restClient))
                // 设置 json解码框架
                .setJsonEncoder(new FastjsonJsonEncoder())
                .build();
        // 获取Mapper对象
        TestMapper testMapper = esdsl.target(TestMapper.class);
        // 调用搜索
        testMapper.search("test");
```

### TestMapper 和 esdsl/test.esdsl文件内容 
![esdsl](./images/esdsl.png)

# esdsl句法介绍
## 主体结构
    ==> {方法名}
    {请求方式} {请求地址}
    {json}
### 关键字
1. 请求方式: PUT POST GET DELETE
1. 判断: #if()  #elseif() #endif
   ```java
    #if(${name}=="1")
    "match" : {
      "id" :123133131223123212313131
      }
    #elseif(${name}=="2")
    "match" : {
      "id" :123133131223123212313131
      }
    #endif
    ```
1. 循环:  
    ```java
    #for(index,item in #{list} open='[' close=']' separator=',')
       #{item}
    #endfor
    ```
    1. index: 循环序号
    1. item: 循环的每一项
    1. list: 被循环的变量
    1. open: foreach代码的开始符号
    1. separator: 元素之间的分隔符
    1. close: foreach代码的关闭符号
    
1. 变量:#{} 会对变量预处理   ${}会直接拼接上


# [Spring Boot 中使用请点击这里](https://github.com/manymobi/esdsl-spring)

# esdsl 文件配色配置
    在idea设置里面添加一个新的文件类型，点击 “1” 即可打开 2 的设置框,在框中按图设置即可
    下边Keywords 里面得的内容
    Keywords 1 中
    ==>

    Keywords 2 中 
    #else
    #elseif
    #endfor
    #endif
    #for
    #if
    DELETE
    GET
    POST
    PUT
    close=
    in
    open=
    separator=

    Keywords 2 中 
    #
    $
![esdsl](images/idea_setting.png)
# 语法校验

暂时没有开发对应插件，请使用 [Esdsl.g4](./src/main/java/com/manymobi/esdsl/antlr4/Esdsl.g4)配合上ANTLR V4插件校验错误
[插件github](https://github.com/antlr/intellij-plugin-v4) 
[jetbrains地址](https://plugins.jetbrains.com/plugin/7358-antlr-v4/)
