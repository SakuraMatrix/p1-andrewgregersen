CREATE KEYSPACE IF NOT EXISTS personalFinance WITH replication = {'class': 'SimpleStrategy','replication_factor':1};

USE personalFinance;

Create TABLE IF NOT EXISTS accounts (
    account_id int,
    first_name text,
    last_name text,
    income double,
    budget double,
    PRIMARY KEY (account_id)
);

insert into accounts (account_id, first_name,last_name,income,budget) values (1,"Andrew","Gregersen",1234.50,500.00);
