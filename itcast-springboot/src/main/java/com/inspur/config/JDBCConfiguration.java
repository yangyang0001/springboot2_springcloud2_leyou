package com.inspur.config;

/**
 * User: YANG
 * Date: 2019/8/4-19:34
 * Description: No Description
 */

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
//@PropertySource("classpath:jdbc.properties")
@EnableConfigurationProperties(JDBCProperties.class)
public class JDBCConfiguration {

//	@Value("${jdbc.driverClassName}")
//	private String driverClassName;
//
//	@Value("${jdbc.username}")
//	private String username;
//
//	@Value("${jdbc.password}")
//	private String password;
//
//	@Value("${jdbc.jdbcUrl}")
//	private String jdbcUrl;

	@Autowired
	private JDBCProperties jdbcProperties;

	@Bean
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(jdbcProperties.getDriverClassName());
		dataSource.setUsername(jdbcProperties.getUsername());
		dataSource.setPassword(jdbcProperties.getPassword());
		dataSource.setUrl(jdbcProperties.getJdbcUrl());
		return dataSource;
	}

}
