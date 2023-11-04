# video-platform

TODO: 权限管理正在实现
已经实现了用map全局变量保存用户的权限
待实现：用aop+注解，通过判断某个请求的权限是否存在于map中，来判断用户是否有权限执行这个请求


**请规范命名文件夹名字**

## 项目结构
- `admin`
    - 主要逻辑/视频
- `common`
    - 工具/公共
- `mbg`
    - mybatis generator
- `security`
    - 用户权限
- `manage`
    - 后台管理
  
## 部署问题
1. 请修改yaml配置
2. 请修改mbg模块resources下的关于数据库的配置
3. Swagger访问网址`http://ip:port/swagger-ui/`，亲测少个下划线都不行！
4. 模块的文件夹格式必须为：szu.*，否则全局拦截器会失效