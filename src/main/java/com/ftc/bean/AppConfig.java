package com.ftc.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//@Component
//@PropertySource(value="classpath:jdbc.properties")
//@ConfigurationProperties(prefix="db")
//public class AppConfig {
//	@Value("jdbcUrl")
//	private String jdbcUrl;
//	@Value("username")
//	private String username;
//	@Value("password")
//	private String password;
//	private String database;
//	private String sysName;
//	private String charsetName;
//
//	public String getJdbcUrl() {
//		return jdbcUrl;
//	}
// 
//	public void setJdbcUrl(String jdbcUrl) {
//		this.jdbcUrl = jdbcUrl;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public String getDatabase() {
//		return database;
//	}
//
//	public void setDatabase(String database) {
//		this.database = database;
//	}
//
//	public String getSysName() {
//		return sysName;
//	}
//
//	public void setSysName(String sysName) {
//		this.sysName = sysName;
//	}
//
//	public String getCharsetName() {
//		return charsetName;
//	}
//
//	public void setCharsetName(String charsetName) {
//		this.charsetName = charsetName;
//	}
//
//}
