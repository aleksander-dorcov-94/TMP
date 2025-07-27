package com.example.fasttest.querybuilder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SubscriptionCriteriaBuilder {

    private final Clock clock = Clock.system(ZoneOffset.UTC);

    public PreparedQuery buildDynamicQuery(SubscriptionCriteriaParams subscriptionCriteriaParams) {
        StringBuilder startingSql = new StringBuilder("SELECT * FROM subscriptions WHERE 1=1");
        List<Object> params = new ArrayList<>();
        final int defaultPageSize = 100;

        if (subscriptionCriteriaParams.isLive() != null && subscriptionCriteriaParams.isLive()) {
            startingSql.append(" AND (status IN ('ACTIVE', 'PAUSED') OR (status = 'CANCELLED' AND nextbillingcycledate > ?))");
            params.add(OffsetDateTime.ofInstant(clock.instant(), clock.getZone()));
        }

        if (subscriptionCriteriaParams.playerId() != null) {
            startingSql.append(" AND playerid = ?");
            params.add(subscriptionCriteriaParams.playerId());
        }

        if (subscriptionCriteriaParams.lastSeenId() != null) {
            startingSql.append(" AND id > ?");
            params.add(subscriptionCriteriaParams.lastSeenId());
        }

        startingSql.append(" ORDER BY id");

        startingSql.append(" LIMIT ?");
        params.add(subscriptionCriteriaParams.pageSize() != null ? subscriptionCriteriaParams.pageSize() : defaultPageSize);

        return new PreparedQuery(startingSql.toString(), params.toArray());
    }

    public PreparedQuery buildDynamicQuery2(SubscriptionCriteriaParams subscriptionCriteriaParams) {
        StringBuilder startingQuery = new StringBuilder("SELECT * FROM subscriptions");

        List<String> conditions = new ArrayList<>();
        List<Object> queryParameters = new ArrayList<>();
        final int defaultPageSize = 100;

        if (subscriptionCriteriaParams.isLive() != null && subscriptionCriteriaParams.isLive()) {
            conditions.add("(status IN ('ACTIVE', 'PAUSED') OR (status = 'CANCELLED' AND nextbillingcycledate > ?))");
            queryParameters.add(OffsetDateTime.ofInstant(clock.instant(), clock.getZone()));
        }

        if (subscriptionCriteriaParams.playerId() != null) {
            conditions.add("playerid = ?");
            queryParameters.add(subscriptionCriteriaParams.playerId());
        }

        if (subscriptionCriteriaParams.lastSeenId() != null) {
            conditions.add("id > ?");
            queryParameters.add(subscriptionCriteriaParams.lastSeenId());
        }

        if (!conditions.isEmpty()) {
            startingQuery.append(" WHERE ");
            startingQuery.append(String.join(" AND ", conditions));
        }

        startingQuery.append(" ORDER BY id");

        startingQuery.append(" LIMIT ?");
        queryParameters.add(subscriptionCriteriaParams.pageSize() != null ? subscriptionCriteriaParams.pageSize() : defaultPageSize);

        return new PreparedQuery(startingQuery.toString(), queryParameters.toArray());
    }
}
