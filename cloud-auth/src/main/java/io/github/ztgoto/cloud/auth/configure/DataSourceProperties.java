package io.github.ztgoto.cloud.auth.configure;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="jdbc")
public class DataSourceProperties {
	
	public static final int DEFAULT_INITIAL_SIZE = 5;
	public static final int DEFAULT_MIN_IDLE = 5;
	public static final int DEFAULT_MAX_ACTIVE = 10;
	public static final long DEFAULT_MAX_WAIT = -1;
	public static final int DEFAULT_MAX_WAIT_THREAD_COUNT = -1;
	public static final boolean DEFAULT_TEST_WHILE_IDLE = false;

	private List<JdbcConfig> dsConfigList = new ArrayList<JdbcConfig>(); 
	
	private String masterName;
	
	private List<String> slaveNames = new ArrayList<String>();
	

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public List<JdbcConfig> getDsConfigList() {
		return dsConfigList;
	}

	public List<String> getSlaveNames() {
		return slaveNames;
	}



	public static class JdbcConfig {
		private String url;
		private String driverClassName;
		private String username;
		private String password;
		private String dataSourceName;
		
		private Integer initialSize;
		private Integer minIdle;
		private Integer maxActive;
		private Long maxWait;
		private Integer maxWaitThreadCount;
		private Boolean testWhileIdle;
		private String validationQuery;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
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

		public String getDataSourceName() {
			return dataSourceName;
		}

		public void setDataSourceName(String dataSourceName) {
			this.dataSourceName = dataSourceName;
		}

		public Integer getInitialSize() {
			return initialSize;
		}

		public void setInitialSize(Integer initialSize) {
			this.initialSize = initialSize;
		}

		public Integer getMinIdle() {
			return minIdle;
		}

		public void setMinIdle(Integer minIdle) {
			this.minIdle = minIdle;
		}

		public Integer getMaxActive() {
			return maxActive;
		}

		public void setMaxActive(Integer maxActive) {
			this.maxActive = maxActive;
		}

		public Long getMaxWait() {
			return maxWait;
		}

		public void setMaxWait(Long maxWait) {
			this.maxWait = maxWait;
		}

		public Integer getMaxWaitThreadCount() {
			return maxWaitThreadCount;
		}

		public void setMaxWaitThreadCount(Integer maxWaitThreadCount) {
			this.maxWaitThreadCount = maxWaitThreadCount;
		}

		public Boolean getTestWhileIdle() {
			return testWhileIdle;
		}

		public void setTestWhileIdle(Boolean testWhileIdle) {
			this.testWhileIdle = testWhileIdle;
		}

		public String getValidationQuery() {
			return validationQuery;
		}

		public void setValidationQuery(String validationQuery) {
			this.validationQuery = validationQuery;
		}
		
		
		
	}
	
}
