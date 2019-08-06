package com.inspur.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * User: YANG
 * Date: 2019/8/4-21:44
 * Description: No Description
 */
@ConfigurationProperties(prefix = "jdbc")
public class JDBCProperties {

	private String driverClassName;
	private String username;
	private String password;
	private String jdbcUrl;

	@Bean
	public DataSource dataSource() {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDriverClassName(this.driverClassName);
		druidDataSource.setUsername(this.username);
		druidDataSource.setPassword(this.password);
		druidDataSource.setUrl(this.jdbcUrl);
		return druidDataSource;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
}
