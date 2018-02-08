package io.github.ztgoto.cloud.auth.configure;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Maps;

import io.shardingjdbc.core.api.MasterSlaveDataSourceFactory;
import io.shardingjdbc.core.api.config.MasterSlaveRuleConfiguration;

@Configuration
@ConditionalOnProperty(name = "jdbc.enabled", matchIfMissing = false)
@EnableConfigurationProperties(DataSourceProperties.class)
public class DataSourceConfiguration {
	
	DataSourceProperties properties;
	
	public DataSourceConfiguration(DataSourceProperties properties) {
		this.properties = properties;
	}

	@Bean
	public DataSource dataSource() throws SQLException{
		
		Map<String, DataSource> dss = createDataSourceMap();
		
		if (properties.getMasterName()==null || !dss.containsKey(properties.getMasterName())) {
			throw new IllegalArgumentException("masterName is null or not exist!");
		}
		
		MasterSlaveRuleConfiguration msConfig = new MasterSlaveRuleConfiguration();
		msConfig.setName("master_slave");
		msConfig.setMasterDataSourceName(properties.getMasterName());
		msConfig.setSlaveDataSourceNames(properties.getSlaveNames());
		
		DataSource ds = MasterSlaveDataSourceFactory.createDataSource(dss, msConfig, Maps.newConcurrentMap());
		
		return ds;
	}
	
	private Map<String, DataSource> createDataSourceMap() {
		Map<String, DataSource> maps = new HashMap<String, DataSource>(properties.getDsConfigList().size());
		
		List<DataSourceProperties.JdbcConfig> configs = properties.getDsConfigList();
		
		for (DataSourceProperties.JdbcConfig jdbcConfig : configs) {
			if (jdbcConfig.getDataSourceName() == null || maps.containsKey(jdbcConfig.getDataSourceName())) {
				throw new IllegalArgumentException("dataSourceName is null or exist!");
			}
			
			DruidDataSource ds = new DruidDataSource();
			ds.setUrl(jdbcConfig.getUrl());
			ds.setDriverClassName(jdbcConfig.getDriverClassName());
			ds.setUsername(jdbcConfig.getUsername());
			ds.setPassword(jdbcConfig.getPassword());
			ds.setDriverClassName(jdbcConfig.getDriverClassName());
			
			ds.setInitialSize(jdbcConfig.getInitialSize()==null
					?DataSourceProperties.DEFAULT_INITIAL_SIZE
							:jdbcConfig.getInitialSize());
			
			ds.setMinIdle(jdbcConfig.getMinIdle()==null
					?DataSourceProperties.DEFAULT_MIN_IDLE
							:jdbcConfig.getMinIdle());
			
			ds.setMaxActive(jdbcConfig.getMaxActive()==null
					?DataSourceProperties.DEFAULT_MAX_ACTIVE
							:jdbcConfig.getMaxActive());
			
			ds.setMaxWait(jdbcConfig.getMaxWait()==null
					?DataSourceProperties.DEFAULT_MAX_WAIT
							:jdbcConfig.getMaxWait());
			
			ds.setMaxWaitThreadCount(jdbcConfig.getMaxWaitThreadCount()==null
					?DataSourceProperties.DEFAULT_MAX_WAIT_THREAD_COUNT
							:jdbcConfig.getMaxWaitThreadCount());
			
			ds.setTestWhileIdle(jdbcConfig.getTestWhileIdle()==null
					?DataSourceProperties.DEFAULT_TEST_WHILE_IDLE
							:jdbcConfig.getTestWhileIdle());
			
			if (jdbcConfig.getValidationQuery()!=null) {
				ds.setValidationQuery(jdbcConfig.getValidationQuery());
			}
			
			maps.put(jdbcConfig.getDataSourceName(), ds);
			
		}
		
		return maps;
	}
	
}
