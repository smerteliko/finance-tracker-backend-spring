-- V2__insert_synchronized_mock_data.sql
-- Вставляет тестовые данные для синхронизированной схемы.

DO $$
    DECLARE
        -- Mock User ID
        user_alice_id UUID := '382e70e0-c956-4c40-9a3d-3a3395c10567';

        -- Mock Account IDs
        acc_checking_id UUID := '5e1f0e4b-6d9a-4f51-b82b-8a50b1d03c62';
        acc_savings_id UUID := '9d4c7b8e-3a2f-4e0d-b1c1-4b5c7a8e0f1d';

        -- Mock Category IDs
        cat_salary_id UUID := '1a2b3c4d-5e6f-7a8b-9c0d-1e2f3a4b5c6d';
        cat_groceries_id UUID := 'c9d1a3e2-f4b5-6c7d-8e9f-0a1b2c3d4e5f';
        cat_rent_id UUID := 'd4c5b6e7-a8f9-1c0d-2e3f-4a5b6c7d8e9f';

        -- Timestamps
        now_ts TIMESTAMP WITHOUT TIME ZONE := NOW();

    BEGIN
        -- 1. Insert User (Alice)
        -- Пароль: 'password' (хешировано через bcrypt)
        INSERT INTO users (id, first_name, last_name, email, password, settings, created_at, updated_at)
        VALUES (
                   user_alice_id,
                   'Alice',
                   'Smith',
                   'alice@example.com',
                   '$2a$10$42w7h0R12j9tV7gW1J4S.e7tK5a8f7c9b0v3y2C.1X2D3E4F5G6H7',
                   '{"currency": "USD", "locale": "en"}',
                   now_ts,
                   now_ts
               );

        -- 2. Insert Accounts for Alice
        -- Начальный баланс 1500.00, будет скорректирован транзакциями
        INSERT INTO accounts (id, user_id, name, account_type, balance, currency, created_at, updated_at)
        VALUES
            (
                acc_checking_id,
                user_alice_id,
                'Main Checking',
                'BANK_ACCOUNT',
                1500.00,
                'USD',
                now_ts,
                now_ts
            ),
            (
                acc_savings_id,
                user_alice_id,
                'High Yield Savings',
                'INVESTMENT',
                5000.00,
                'USD',
                now_ts,
                now_ts
            );

        -- 3. Insert Categories for Alice
        INSERT INTO categories (id, user_id, name, color, type, created_at, updated_at)
        VALUES
            (
                cat_salary_id,
                user_alice_id,
                'Salary',
                '#00CC00',
                'INCOME',
                now_ts,
                now_ts
            ),
            (
                cat_groceries_id,
                user_alice_id,
                'Groceries',
                '#FF5733',
                'EXPENSE',
                now_ts,
                now_ts
            ),
            (
                cat_rent_id,
                user_alice_id,
                'Rent',
                '#3366FF',
                'EXPENSE',
                now_ts,
                now_ts
            );

        -- 4. Insert Transactions for Alice

        -- Транзакция 1: Доход (Зарплата) - $2500.00
        INSERT INTO transactions (id, user_id, account_id, category_id, amount, type, description, date, created_at, updated_at)
        VALUES (
                   uuid_generate_v4(),
                   user_alice_id,
                   acc_checking_id,
                   cat_salary_id,
                   2500.00,
                   'EXPENSE',
                   'Monthly salary deposit',
                   now_ts - INTERVAL '1 month',
                   now_ts,
                   now_ts
               );

        -- Транзакция 2: Расход (Аренда) - $1200.00
        INSERT INTO transactions (id, user_id, account_id, category_id, amount,type, description, date, created_at, updated_at)
        VALUES (
                   uuid_generate_v4(),
                   user_alice_id,
                   acc_checking_id,
                   cat_rent_id,
                   1200.00,
                   'EXPENSE',
                   'Monthly rent payment',
                   now_ts - INTERVAL '1 month' + INTERVAL '2 days',
                   now_ts,
                   now_ts
               );

        -- Транзакция 3: Расход (Продукты) - $45.50
        INSERT INTO transactions (id, user_id, account_id, category_id, amount,type, description, date, created_at, updated_at)
        VALUES (
                   uuid_generate_v4(),
                   user_alice_id,
                   acc_checking_id,
                   cat_groceries_id,
                   45.50,
                   'EXPENSE',
                   'Weekly grocery run',
                   now_ts - INTERVAL '10 days',
                   now_ts,
                   now_ts
               );

        -- Транзакция 4: Доход (Бонус) - $50.00
        INSERT INTO transactions (id, user_id, account_id, category_id, amount,type, description, date, created_at, updated_at)
        VALUES (
                   uuid_generate_v4(),
                   user_alice_id,
                   acc_checking_id,
                   cat_salary_id,
                   50.00,
                   'EXPENSE',
                   'Small bonus payment',
                   now_ts - INTERVAL '5 days',
                   now_ts,
                   now_ts
               );

        -- 5. Финальная корректировка баланса счета Main Checking
        -- Расчет: 1500 (Начальный) + 2500 - 1200 - 45.50 + 50 = 2804.50
        UPDATE accounts
        SET balance = 2804.50
        WHERE id = acc_checking_id;

    END $$;