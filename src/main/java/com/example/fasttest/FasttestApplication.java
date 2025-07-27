package com.example.fasttest;

import com.example.fasttest.querybuilder.PreparedQuery;
import com.example.fasttest.querybuilder.SubscriptionCriteriaBuilder;
import com.example.fasttest.querybuilder.SubscriptionCriteriaParams;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class FasttestApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(FasttestApplication.class, args);
        // repo
        System.out.println("from repo");
        var repo = context.getBean(SubscriptionRepository.class);
        repo.findAll().forEach(System.out::println);
        //service
        System.out.println("from service");
        var subscriptionCriteriaBuilder = context.getBean(SubscriptionCriteriaBuilder.class);
        var jdbcTemplate = context.getBean(JdbcTemplate.class);
        /// hardcode here some calls

        // the idea is that when i got to moy work pc in our repository service that already has repos injected i will inject jdbc template only
        System.out.println("\n--- TEST CASE 1: All filters (playerId, isLive, lastSeenId, pageSize) ---");
        var criteria1 = new SubscriptionCriteriaParams(101L, 1L, 10, true);
        PreparedQuery preparedQuery1 = subscriptionCriteriaBuilder.buildDynamicQuery(criteria1);;
        jdbcTemplate.query(preparedQuery1.sql(), new BeanPropertyRowMapper<>(Subscription.class), preparedQuery1.params())
                .forEach(System.out::println);

        System.out.println("\n--- TEST CASE 2: Only playerId ---");
        var criteria2 = new SubscriptionCriteriaParams(101L, null, null, null);
        PreparedQuery preparedQuery2 = subscriptionCriteriaBuilder.buildDynamicQuery(criteria2);
        jdbcTemplate.query(preparedQuery2.sql(), new BeanPropertyRowMapper<>(Subscription.class), preparedQuery2.params())
                .forEach(System.out::println);

        System.out.println("\n--- TEST CASE 3: Only isLive ---");
        var criteria3 = new SubscriptionCriteriaParams(null, null, null, true);
        PreparedQuery preparedQuery3 = subscriptionCriteriaBuilder.buildDynamicQuery(criteria3);
        jdbcTemplate.query(preparedQuery3.sql(), new BeanPropertyRowMapper<>(Subscription.class), preparedQuery3.params())
                .forEach(System.out::println);

        System.out.println("\n--- TEST CASE 4: No filters (first page) ---");
        var criteria4 = new SubscriptionCriteriaParams(null, null, 5, null);
        PreparedQuery preparedQuery4 = subscriptionCriteriaBuilder.buildDynamicQuery(criteria4);
        jdbcTemplate.query(preparedQuery4.sql(), new BeanPropertyRowMapper<>(Subscription.class), preparedQuery4.params())
                .forEach(System.out::println);

        System.out.println("\n--- TEST CASE 5: No filters (subsequent page using lastSeenId) ---");
        var criteria5 = new SubscriptionCriteriaParams(null, 2L, 5, null);
        PreparedQuery preparedQuery5 = subscriptionCriteriaBuilder.buildDynamicQuery(criteria5);
        jdbcTemplate.query(preparedQuery5.sql(), new BeanPropertyRowMapper<>(Subscription.class), preparedQuery5.params())
                .forEach(System.out::println);

        System.out.println("\n--- TEST CASE 6: No parameters at all (should use default pageSize) ---");
        var criteria6 = new SubscriptionCriteriaParams(null, null, null, null);
        PreparedQuery preparedQuery6 = subscriptionCriteriaBuilder.buildDynamicQuery(criteria6);
        jdbcTemplate.query(preparedQuery6.sql(), new BeanPropertyRowMapper<>(Subscription.class), preparedQuery6.params())
                .forEach(System.out::println);
    }
}
