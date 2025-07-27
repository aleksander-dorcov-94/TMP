CREATE TABLE subscriptions
(
    id                   BIGSERIAL PRIMARY KEY,
    status               VARCHAR(50)              NOT NULL,
    wagerinfo            varchar(64)              NOT NULL,
    playerid             BIGINT                   NOT NULL,
    nextbillingcycledate TIMESTAMP WITH TIME ZONE NOT NULL
);

-- Populate with more comprehensive data
INSERT INTO subscriptions (status, wagerinfo, playerid, nextbillingcycledate)
VALUES
    -- Original Data
    ('ACTIVE', '{}', 101, '2025-08-01T10:00:00Z'),
    ('PAUSED', '{}', 101, '2025-09-01T10:00:00Z'),
    ('ACTIVE', '{}', 202, '2025-08-15T12:00:00Z'),
    ('CANCELLED', '{}', 101, '2026-01-01T00:00:00Z'),
    ('CANCELLED', '{}', 202, '2024-01-01T00:00:00Z'),
    ('FAILED', '{}', 101, '2025-10-01T00:00:00Z'),
    ('ACTIVE', '{}', 303, '2025-11-01T00:00:00Z'),
    ('PAUSED', '{}', 303, '2025-12-01T00:00:00Z');
