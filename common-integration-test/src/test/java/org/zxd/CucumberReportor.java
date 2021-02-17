package org.zxd;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
/**
 * features 用于指定特性文件的根目录
 * plugin 用于指定报告插件
 * tags 用于运行带有指定标签的场景测试
 */
@CucumberOptions(features = "src/test/resources", plugin = {"pretty", "html:target/cucumber-report/",
		"json:target/cucumber-report/cucumber.json"})
public class CucumberReportor {
}