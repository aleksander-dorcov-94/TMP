package com.example.fasttest;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
    // You can add simple derived queries here later, like:
    // List<Subscription> findByPlayerId(Long playerId);
}
