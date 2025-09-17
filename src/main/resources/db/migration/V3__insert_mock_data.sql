-- Insert mock categories
INSERT INTO categories (uuid, created_at, updated_at, version, name, color, type)
VALUES ('a1b2c3d4-e5f6-7890-abcd-ef1234567890', NOW(), NOW(), 0, 'Salary', '#4CAF50', 'INCOME'),
       ('b2c3d4e5-f6g7-8901-bcde-f23456789012', NOW(), NOW(), 0, 'Freelance', '#8BC34A', 'INCOME'),
       ('c3d4e5f6-g7h8-9012-cdef-345678901234', NOW(), NOW(), 0, 'Investments', '#CDDC39', 'INCOME'),
       ('d4e5f6g7-h8i9-0123-defg-456789012345', NOW(), NOW(), 0, 'Groceries', '#FF5722', 'EXPENSE'),
       ('e5f6g7h8-i9j0-1234-efgh-567890123456', NOW(), NOW(), 0, 'Rent', '#FF9800', 'EXPENSE'),
       ('f6g7h8i9-j0k1-2345-fghi-678901234567', NOW(), NOW(), 0, 'Utilities', '#FFC107', 'EXPENSE'),
       ('g7h8i9j0-k1l2-3456-ghij-789012345678', NOW(), NOW(), 0, 'Transportation', '#FFEB3B', 'EXPENSE'),
       ('h8i9j0k1-l2m3-4567-hijk-890123456789', NOW(), NOW(), 0, 'Entertainment', '#9C27B0', 'EXPENSE'),
       ('i9j0k1l2-m3n4-5678-ijkl-901234567890', NOW(), NOW(), 0, 'Healthcare', '#673AB7', 'EXPENSE'),
       ('j0k1l2m3-n4o5-6789-jklm-012345678901', NOW(), NOW(), 0, 'Dining Out', '#E91E63', 'EXPENSE');

-- Insert mock users (password is "password123" bcrypt encoded)
INSERT INTO users (uuid, created_at, updated_at, version, email, password, first_name, last_name)
VALUES ('u1b2c3d4-e5f6-7890-abcd-ef1234567890', NOW(), NOW(), 0, 'john.doe@email.com',
        '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'John', 'Doe'),
       ('u2c3d4e5-f6g7-8901-bcde-f23456789012', NOW(), NOW(), 0, 'jane.smith@email.com',
        '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Jane', 'Smith');

-- Insert mock transactions for John Doe
INSERT INTO transactions (uuid, created_at, updated_at, version, amount, description, type, date, user_id, category_id)
VALUES
-- John's income transactions
('t1b2c3d4-e5f6-7890-abcd-ef1234567890', NOW(), NOW(), 0, 3500.00, 'Monthly salary', 'INCOME',
 NOW() - INTERVAL '15 days', 1, 1),
('t2c3d4e5-f6g7-8901-bcde-f23456789012', NOW(), NOW(), 0, 1200.00, 'Web development project', 'INCOME',
 NOW() - INTERVAL '10 days', 1, 2),
('t3d4e5f6-g7h8-9012-cdef-345678901234', NOW(), NOW(), 0, 450.00, 'Stock dividends', 'INCOME',
 NOW() - INTERVAL '5 days', 1, 3),

-- John's expense transactions
('t4e5f6g7-h8i9-0123-defg-456789012345', NOW(), NOW(), 0, -85.50, 'Weekly groceries', 'EXPENSE',
 NOW() - INTERVAL '3 days', 1, 4),
('t5f6g7h8-i9j0-1234-efgh-567890123456', NOW(), NOW(), 0, -1200.00, 'Apartment rent', 'EXPENSE',
 NOW() - INTERVAL '2 days', 1, 5),
('t6g7h8i9-j0k1-2345-fghi-678901234567', NOW(), NOW(), 0, -150.00, 'Electricity bill', 'EXPENSE',
 NOW() - INTERVAL '1 day', 1, 6),
('t7h8i9j0-k1l2-3456-ghij-789012345678', NOW(), NOW(), 0, -45.00, 'Bus pass', 'EXPENSE', NOW(), 1, 7),
('t8i9j0k1-l2m3-4567-hijk-890123456789', NOW(), NOW(), 0, -75.00, 'Movie tickets', 'EXPENSE', NOW(), 1, 8);

-- Insert mock transactions for Jane Smith
INSERT INTO transactions (uuid, created_at, updated_at, version, amount, description, type, date, user_id, category_id)
VALUES
-- Jane's income transactions
('t9j0k1l2-m3n4-5678-ijkl-901234567890', NOW(), NOW(), 0, 4200.00, 'Monthly salary', 'INCOME',
 NOW() - INTERVAL '15 days', 2, 1),
('t0k1l2m3-n4o5-6789-jklm-012345678901', NOW(), NOW(), 0, 800.00, 'Consulting work', 'INCOME',
 NOW() - INTERVAL '8 days', 2, 2),

-- Jane's expense transactions
('t1l2m3n4-o5p6-7890-klmn-123456789012', NOW(), NOW(), 0, -95.75, 'Organic groceries', 'EXPENSE',
 NOW() - INTERVAL '4 days', 2, 4),
('t2m3n4o5-p6q7-8901-lmno-234567890123', NOW(), NOW(), 0, -1350.00, 'Studio rent', 'EXPENSE', NOW() - INTERVAL '3 days',
 2, 5),
('t3n4o5p6-q7r8-9012-mnop-345678901234', NOW(), NOW(), 0, -120.00, 'Internet bill', 'EXPENSE',
 NOW() - INTERVAL '2 days', 2, 6),
('t4o5p6q7-r8s9-0123-nopq-456789012345', NOW(), NOW(), 0, -60.00, 'Taxi rides', 'EXPENSE', NOW() - INTERVAL '1 day', 2,
 7),
('t5p6q7r8-s9t0-1234-opqr-567890123456', NOW(), NOW(), 0, -120.00, 'Concert tickets', 'EXPENSE', NOW(), 2, 8),
('t6q7r8s9-t0u1-2345-pqrs-678901234567', NOW(), NOW(), 0, -85.00, 'Dinner at restaurant', 'EXPENSE', NOW(), 2, 10);