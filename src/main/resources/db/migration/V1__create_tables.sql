-- Create users table
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       uuid VARCHAR(36) NOT NULL UNIQUE,
                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP NOT NULL,
                       version BIGINT,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100) NOT NULL
);

-- Create categories table
CREATE TABLE categories (
                            id BIGSERIAL PRIMARY KEY,
                            uuid VARCHAR(36) NOT NULL UNIQUE,
                            created_at TIMESTAMP NOT NULL,
                            updated_at TIMESTAMP NOT NULL,
                            version BIGINT,
                            name VARCHAR(100) NOT NULL,
                            color VARCHAR(7),
                            type VARCHAR(10) NOT NULL CHECK (type IN ('INCOME', 'EXPENSE'))
);

-- Create transactions table
CREATE TABLE transactions (
                              id BIGSERIAL PRIMARY KEY,
                              uuid VARCHAR(36) NOT NULL UNIQUE,
                              created_at TIMESTAMP NOT NULL,
                              updated_at TIMESTAMP NOT NULL,
                              version BIGINT,
                              amount NUMERIC(15, 2) NOT NULL,
                              description VARCHAR(500),
                              type VARCHAR(10) NOT NULL CHECK (type IN ('INCOME', 'EXPENSE')),
                              date TIMESTAMP NOT NULL,
                              user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                              category_id BIGINT NOT NULL REFERENCES categories(id) ON DELETE RESTRICT
);

-- Create indexes
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_user_uuid ON users(uuid);
CREATE INDEX idx_user_created ON users(created_at);

CREATE INDEX idx_category_name_type ON categories(name, type);
CREATE INDEX idx_category_type ON categories(type);
CREATE INDEX idx_category_uuid ON categories(uuid);
CREATE INDEX idx_category_created ON categories(created_at);

CREATE INDEX idx_transaction_user_date ON transactions(user_id, date DESC);
CREATE INDEX idx_transaction_date ON transactions(date);
CREATE INDEX idx_transaction_type ON transactions(type);
CREATE INDEX idx_transaction_uuid ON transactions(uuid);
CREATE INDEX idx_transaction_created ON transactions(created_at);