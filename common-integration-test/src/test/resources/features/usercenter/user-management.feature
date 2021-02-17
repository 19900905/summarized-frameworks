@user_management
@release_1.0.0
Feature: 用户管理
  为了有效管理系统用户
  作为一个系统管理员
  我希望能够增加新用户、查看用户信息

  Scenario: 新增用户
    Given 张三拥有系统管理员角色
    When 当他在新增用户页面输入有效用户信息并提交表单时
      | username    | password | conform  |
      | 15505883728 | 1qaz@WSX | 1qaz@WSX |
    Then 系统新增用户 15505883728 成功

  @existUserCheck
  Scenario Outline: 系统管理员张三在注册用户时提交一个已经存在的用户名称
    Given 张三提交一个已存在的用户名称<username>和密码<password>，并且此用户有如下状态<userStatus>
    Then 系统返回提示信息<errorCode>
    Examples:
      | username    | password | userStatus | errorCode        |
      | 15505883728 | 1qaz!QAZ | LOCKED     | user.1009      |
      | 15505883728 | 1qaz!QAZ | INVALID    | user.1010     |
      | 15505883728 | 1qaz!QAZ | OK         | user.1006 |