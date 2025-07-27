package com.example.fasttest;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Table("subscriptions")
public class Subscription {

    @Id
    private Long id;

    private String status;

    @Column("wagerinfo")
    private String wagerInfo;

    @Column("playerid")
    private Long playerId;

    @Column("nextbillingcycledate")
    private Instant nextBillingCycleDate;
}
