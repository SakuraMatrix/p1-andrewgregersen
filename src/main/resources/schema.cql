CREATE KEYSPACE IF NOT EXISTS personalFinance WITH replication = {'class': 'SimpleStrategy','replication_factor':1};

USE personalFinance;

Create TABLE IF NOT EXISTS accounts (
    account_id uuid,
    first_name text,
    last_name text,
    income double,
    budget double,
    PRIMARY KEY (account_id, last_name)
);

CREATE TABLE budget(
    account_id uuid,
    weekly bool
);