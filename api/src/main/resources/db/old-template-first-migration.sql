CREATE DATABASE finance;

CREATE TABLE account (
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    pass VARCHAR(16) NOT NULL,
    phone_number VARCHAR(16) NOT NULL,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    created_on TIMESTAMP NOT NULL,
    last_login TIMESTAMP
);

CREATE TABLE portfolio (
    id SERIAL PRIMARY KEY,
    title VARCHAR(20) NOT NULL,
    account_id INT,
    FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE TABLE asset_currency (
    id SERIAL PRIMARY KEY,
    asset_type VARCHAR(20) NOT NULL,
    asset_value NUMERIC NOT NULL,
    portfolio_id INT,
    FOREIGN KEY (portfolio_id) REFERENCES portfolio(id)
);

CREATE TABLE goal (
    id SERIAL PRIMARY KEY,
    title VARCHAR(15) NOT NULL,
    state VARCHAR(15) NOT NULL,
    description VARCHAR(100),
    target_amount NUMERIC NOT NULL,
    deadline TIMESTAMP NOT NULL,
    created_on TIMESTAMP NOT NULL,
    last_change TIMESTAMP,
    account_id INT,
    portfolio_id INT,
    FOREIGN KEY (portfolio_id) REFERENCES portfolio(id),
    FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE TABLE role (
   id SERIAL PRIMARY KEY,
   naming VARCHAR(15)
);

CREATE TABLE role_account (
    role_id INT,
    account_id INT,
    PRIMARY KEY (role_id, account_id),
    FOREIGN KEY (role_id) REFERENCES role(id),
    FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE TABLE bill (
    id SERIAL PRIMARY KEY,
    bill_type VARCHAR(5) NOT NULL,
    bill_value NUMERIC NOT NULL,
    account_id INT,
    FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE TABLE user_transaction (
    id SERIAL PRIMARY KEY,
    title VARCHAR(15) NOT NULL,
    description VARCHAR(100),
    date_of_action TIMESTAMP NOT NULL,
    transaction_value NUMERIC NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    transaction_category VARCHAR(20),
    bill_id INT,
    FOREIGN KEY (bill_id) REFERENCES bill(id)
);