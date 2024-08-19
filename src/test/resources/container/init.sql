CREATE TYPE saving_state AS ENUM ('ACTIVE', 'ACHIEVED', 'OVERDUE');
CREATE TYPE currency_code AS ENUM ('EUR', 'USD', 'PLN', 'BYN', 'RUB');
CREATE TYPE app_role AS ENUM ('ADMIN', 'USER');
CREATE TYPE user_gender AS ENUM ('MALE', 'FEMALE');
CREATE TYPE risk_profile_type AS ENUM ('CONSERVATIVE', 'MODERATE', 'AGGRESSIVE');
CREATE TYPE transaction_type AS ENUM ('DEPOSIT', 'WITHDRAW');
CREATE TYPE saving_image_type AS ENUM ('JPEG', 'JPG', 'PNG');

CREATE CAST (varchar AS currency_code) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS saving_state) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS risk_profile_type) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS saving_image_type) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS transaction_type) WITH INOUT AS IMPLICIT;

CREATE TABLE user_account
(
    id_user_account SERIAL PRIMARY KEY,
    role            app_role     NOT NULL,
    email           VARCHAR(100) NOT NULL,
    pass            VARCHAR(16)  NOT NULL,
    first_name      VARCHAR(30)  NOT NULL,
    last_name       VARCHAR(30)  NOT NULL,
    gender          user_gender  NOT NULL,
    date_of_birth   DATE         NOT NULL,
    phone_number    VARCHAR(16)  NOT NULL,
    created_on      TIMESTAMP    NOT NULL,
    last_login      TIMESTAMP
);
CREATE TABLE saving
(
    id_saving       SERIAL PRIMARY KEY,
    title           VARCHAR(25)    NOT NULL,
    state           saving_state   NOT NULL,
    currency        currency_code  NOT NULL,
    description     VARCHAR(100),
    balance         NUMERIC(38, 2) NOT NULL,
    target_amount   NUMERIC(38, 2),
    deadline        DATE,
    risk_profile    risk_profile_type,
    image           bytea,
    type_image      saving_image_type,
    created_on      TIMESTAMP      NOT NULL,
    last_change     TIMESTAMP,
    id_user_account INT            NOT NULL,
    FOREIGN KEY (id_user_account) REFERENCES user_account (id_user_account)
);
CREATE TABLE transaction
(
    id_transaction SERIAL PRIMARY KEY,
    type           transaction_type NOT NULL,
    description    VARCHAR(100),
    deal_date      TIMESTAMP        NOT NULL,
    amount         NUMERIC(38, 2)   NOT NULL,
    id_saving      INT              NOT NULL,
    FOREIGN KEY (id_saving) REFERENCES saving (id_saving)
);
CREATE FUNCTION saving_update_last_change()
    RETURNS TRIGGER
    LANGUAGE PLPGSQL AS
$$
BEGIN
    NEW.last_change = NOW()::timestamp;
    RETURN NEW;
END
$$;
CREATE TRIGGER refresh_last_change
    BEFORE UPDATE ON saving
    FOR EACH ROW
EXECUTE PROCEDURE saving_update_last_change();
CREATE FUNCTION saving_init_created_on()
    RETURNS TRIGGER
    LANGUAGE PLPGSQL AS
$$
BEGIN
    NEW.created_on = NOW()::timestamp;
    RETURN NEW;
END
$$;
CREATE TRIGGER init_created_on
    BEFORE INSERT ON saving
    FOR EACH ROW
EXECUTE PROCEDURE saving_init_created_on();
--
-- TEST DATA / TEST DATA / TEST DATA / TEST DATA
--
insert into user_account (role, email, pass, first_name, last_name, gender, date_of_birth, phone_number, created_on)
values ('USER', 'jjoyes0@woothemes.com', 'pP0&*2KN&.S', 'Janey', 'Joyes', 'MALE', '1992-11-21', '713-667-4844', '2018-10-24 00:56:41');
insert into saving (title, state, currency, description, balance, target_amount, deadline, created_on, id_user_account)
values ('Trichilia', 'ACTIVE', 'USD', 'Adiantum obliquum Willd.', 950, 1500.50, '2050-10-24', '2018-10-24 00:56:41', 1);
insert into transaction (type, deal_date, amount, id_saving)
values ('DEPOSIT', '2019-01-05 02:56:41', 500, 1);
insert into transaction (type, deal_date, amount, id_saving)
values ('DEPOSIT', '2018-06-10 06:56:41', 500, 1);