package org.zxd.dao.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis 接口扫描
 */
@MapperScan("org.zxd.dao")
@Configuration
class MybatisConfig {
}
