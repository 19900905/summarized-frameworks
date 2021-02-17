Feature: 安全密码设定
  为了防止密码被破解，避免客户利益遭受损失
  作为一个系统管理员
  我希望密码安全性能有所保证

  Scenario Outline: 密码有效性判断
    Given 系统管理员新增用户输入此密码<password>时
    Then 系统需要返回对应的提示信息<message>
    Examples: 密码不能为空
      | password | message       |
      |          | user.1002 |
    Examples: 密码必须包含数字、字母和特殊字符
      | password | message         |
      | 12345678 | user.1005 |
      | abcdefgh | user.1005 |
      | 1234abcd | user.1005 |
      | 1234abCD | user.1005 |
    Examples: 满足条件的密码
      | password | message |
      | 1qaz!QAZ | success |