package com.example.fasttest.querybuilder;

public record SubscriptionCriteriaParams(
        Long playerId,
        Long lastSeenId,
        Integer pageSize,
        Boolean isLive) {

}
