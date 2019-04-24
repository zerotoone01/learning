# Spring Security开发安全的REST服务
课程网址`https://coding.imooc.com/class/chapter/134.html#Anchor`

## 第1章 课程导学
介绍课程内容、课程特点，使用的主要技术栈，以及学习课程所需的前置知识
### 1-1 导学
### 第2章 开始开发
安装开发工具，介绍项目代码结构并搭建，基本的依赖和参数设置，开发hello world
### 2-1 开发环境安装
### 2-2 代码结构介绍
### 2-3 Hello Spring Security
## 第3章 使用Spring MVC开发RESTful API
本章主要开发一些REST风格的服务接口，后面章节中的认证授权模块会为这些服务接口提供安全保护，在这一章中，你会学习开发REST风格服务接口时，一些常用的技巧和工具。我们会介绍如何拦截服务接口来提供一些通用的功能（例如记日志），还会介绍如何通过多线程来提高服务的性能，以及如何自动生成服务文档和伪造服务等。...
### 3-1 Restful简介
### 3-2 查询请求
### 3-3 用户详情请求
### 3-4 用户创建请求
### 3-5 修改和删除请求
### 3-6 服务异常处理

@JsonView ---字段可见性控制

```
import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.validator.MyConstraint;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhailiang
 *
 */
public class User {
	
	public interface UserSimpleView {};
	public interface UserDetailView extends UserSimpleView {};
	
	private String id;
	@MyConstraint(message = "这是一个测试")
	@ApiModelProperty(value = "用户名")
	private String username;
	
	@NotBlank(message = "密码不能为空")
	private String password;
	
	@Past(message = "生日必须是过去的时间")
	private Date birthday;

	@JsonView(UserSimpleView.class)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonView(UserDetailView.class)
	public String getPassword() {
		return password;
	}
	@JsonView(UserSimpleView.class)
	public String getId() {
		return id;
	}
	@JsonView(UserSimpleView.class)
	public Date getBirthday() {
		return birthday;
	}
	
}

```
controller -- 路径正则，展示可选
```
@GetMapping("/{id:\\d+}")
	@JsonView(User.UserDetailView.class)
	public User getInfo(@ApiParam("用户id") @PathVariable String id) {
//		throw new RuntimeException("user not exist");
		System.out.println("进入getInfo服务");
		User user = new User();
		user.setUsername("tom");
		return user;
	}

```
ControllerExceptionHandler --控制层错误详细捕获,只要控制层抛出对应的错误，就可以进行错误的详细封装
```
@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(UserNotExistException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> handleUserNotExistException(UserNotExistException ex) {
		Map<String, Object> result = new HashMap<>();
		result.put("id", ex.getId());
		result.put("message", ex.getMessage());
		return result;
	}

}
```


### 3-7 使用切片拦截REST服务
### 3-8 使用Filter和Interceptor拦截REST服务
### 3-9 使用REST方式处理文件服务
### 3-10 使用多线程提高REST服务性能
### 3-11 使用Swagger自动生成文档
### 3-12 使用WireMock伪造REST服务
## 第4章 使用Spring Security开发基于表单的登录
介绍Spring Security的基本原理和核心概念，学习如何利用Spring Security提供的开箱即用的功能快速开发基于用户名密码的登录，以及如何扩展Spring Security的默认实现来满足个性化的需求，在这个过程中，我们会深入了解Spring Security的源码实现。最后，我们会学习如何向Spring Security中加入完全自定义的登录方式，...
### 4-1 简介
### 4-2 SpringSecurity基本原理
### 4-3 自定义用户认证逻辑
### 4-4 个性化用户认证流程（一）
### 4-5 个性化用户认证流程（二）
### 4-6 认证流程源码级详解
### 4-7 图片验证码
### 4-8 图片验证码重构
### 4-9 添加记住我功能
### 4-10 短信验证码接口开发
### 4-11 短信登录开发
### 4-12 短信登录配置及重构
### 4-13 小结
##  第5章 使用Spring Social开发第三方登录
 首先会介绍OAuth协议和Spring Social的基本原理和核心概念，然后我们会基于Spring Social开发QQ登录和微信登录，通过这些实战开发，你会深入了解Spring Social的底层源码实现，以及如何扩展这些实现来适应不同的服务提供商。本章最后，我们会介绍Spring Security中与session管理相关的特性，如超时处理，并发控制等。...
### 5-1 OAuth协议简介
### 5-2 SpringSocial简介
### 5-3 开发QQ登录（上）
### 5-4 开发QQ登录（中）
### 5-5 开发QQ登录（下）
### 5-6 处理注册逻辑
### 5-7 开发微信登录
### 5-8 绑定和解绑处理
### 5-9 单机Session管理
### 5-10 集群Session管理
### 5-11 退出登录
## 第6章 Spring Security OAuth开发APP认证框架
 首先会介绍Spring Security OAuth的主要功能，然后我们会学习如何基于Spring Security OAuth搭建自己的认证服务器和资源服务器。我们还会重构之前编写的登录功能的代码，使其可以返回认证服务器生成的token来实现对APP登录的支持。在这个过程中，我们会介绍如何控制token的生成和存储策略，以及如何自动刷新token等知识...
### 6-1 SpringSecurityOAuth简介
### 6-2 实现标准的OAuth服务提供商
### 6-3 SpringSecurityOAuth核心源码解析
### 6-4 重构用户名密码登录
### 6-5 重构短信登录
### 6-6 重构社交登录
### 6-7 重构注册逻辑
### 6-8 令牌配置
### 6-9 使用JWT替换默认令牌
### 6-10 基于JWT实现SSO单点登录1
### 6-11 基于JWT实现SSO单点登录2
## 第7章 使用Spring Security控制授权
本章会介绍Spring Security中另一个重要功能：授权的相关知识，首先我们会介绍Spring Security中与授权相关的原理和概念，然后，我们会依据授权逻辑的复杂程度将常见的授权场景分类，并针对每一类场景介绍如何使用Spring Security来控制授权行为。...
### 7-1 SpringSecurity授权简介
### 7-2 SpringSecurity源码解析
### 7-3 权限表达式
### 7-4 基于数据库Rbac数据模型控制权限
## 第8章 课程总结
快速回顾课程中开发的可重用的认证和授权模块，总结功能特性及使用方式，总结配置项，总结扩展点，总结服务响应状态码规则。进一步优化和提升的思路提示。

### 8-1 课程总结
