/**
 * Copyright: Copyright (c)2016
 * Company: 易宝支付(YeePay)
 */
package com.websystique.springmvc.configuration;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 类名称: DataSourceConfiguration <br>
 * 类描述: <br>
 *
 * @author: zhongyi.ning
 * @since: 16/5/27 下午6:13
 * @version: 1.0.0
 */
@Configuration
@MapperScan("com.websystique.springmvc.dao")
public class DataSourceConfiguration {
    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://172.17.100.6:3306/TEST_HAC");
        dataSource.setUsername("root");
        dataSource.setPassword("newpassword");
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(getDataSource());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        return sessionFactory.getObject();
    }
}
