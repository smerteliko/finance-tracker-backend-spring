-- V1__create_tables.sql
-- Создание таблиц для синхронизированной структуры (UUID, Account, CategoryType enum)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
-- Удаление существующих таблиц для чистого запуска

-- Активация расширения UUID для PostgreSQL

-- 1. Таблица пользователей (Users)
CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       first_name VARCHAR(255),
                       last_name VARCHAR(255),
                       email VARCHAR(180) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       settings JSONB DEFAULT '{"currency": "USD", "locale": "en"}',
                       created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                       updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

-- 2. Таблица счетов (Accounts)
CREATE TYPE account_type_enum AS ENUM ('CASH', 'BANK_ACCOUNT', 'CREDIT_CARD', 'INVESTMENT');

CREATE TABLE accounts (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          user_id UUID NOT NULL,
                          name VARCHAR(255) NOT NULL,
                          account_type account_type_enum NOT NULL,
                          balance NUMERIC(19, 4) NOT NULL DEFAULT 0.00,
                          currency VARCHAR(50) DEFAULT 'USD',
                          created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                          updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,

                          CONSTRAINT fk_account_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- 3. Таблица категорий (Categories)
CREATE TYPE category_type_enum AS ENUM ('INCOME', 'EXPENSE');

CREATE TABLE categories (
                            id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                            user_id UUID NOT NULL,
                            name VARCHAR(255) NOT NULL,
                            color VARCHAR(7) NOT NULL, -- Hex code
                            type category_type_enum NOT NULL,
                            created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                            updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,

                            CONSTRAINT fk_category_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- 4. Таблица транзакций (Transactions)
CREATE TYPE transaction_type_enum AS ENUM ('INCOME', 'EXPENSE');

CREATE TABLE transactions (
                              id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                              user_id UUID NOT NULL,
                              account_id UUID NOT NULL,
                              category_id UUID NOT NULL,
                              amount NUMERIC(15, 2) NOT NULL,
                              type transaction_type_enum NOT NULL,
                              description VARCHAR(500),
                              notes TEXT,
                              date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                              created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                              updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,

                              CONSTRAINT fk_transaction_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                              CONSTRAINT fk_transaction_account FOREIGN KEY (account_id) REFERENCES accounts (id) ON DELETE RESTRICT,
                              CONSTRAINT fk_transaction_category FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE RESTRICT
);