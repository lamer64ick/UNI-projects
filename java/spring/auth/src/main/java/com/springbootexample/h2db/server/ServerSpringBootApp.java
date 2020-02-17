package com.springbootexample.h2db.server;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@SpringBootApplication
@ComponentScan("com.springbootexample.h2db.server")
public class SpringBootApp {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);
    }

    @PostConstruct
    private void initDb() {
        System.out.println(String.format("****** Creating table: %s, and Inserting test data ******", "Users"));

        String sqlStatements[] = {
                "drop table users if exists",
                "create table Users(id serial, username varchar(255))",
                "insert into employees(username) values('user0')"
        };

        Arrays.asList(sqlStatements).stream().forEach(sql -> {
            System.out.println(sql);
            jdbcTemplate.execute(sql);
        });

        System.out.println(String.format("****** Fetching from table: %s ******", "Users"));
        jdbcTemplate.query("select id, username from users",
                new RowMapper<Object>() {
                    @Override
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        System.out.println(String.format("id:%s,first_name:%s,last_name:%s",
                                rs.getString("id"),
                                rs.getString("username")));
                        return null;
                    }
                });
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server inMemoryH2DatabaseServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9091");
    }
}