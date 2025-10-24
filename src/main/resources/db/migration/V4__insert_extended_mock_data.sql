-- Insert additional categories
INSERT INTO categories (uuid, created_at, updated_at, version, name, color, type) VALUES
-- Additional income categories
('k1l2m3n4-o5p6-7890-opqr-123456789012', NOW(), NOW(), 0, 'Bonus', '#4CAF50', 'INCOME'),
('l2m3n4o5-p6q7-8901-pqrs-234567890123', NOW(), NOW(), 0, 'Commission', '#8BC34A', 'INCOME'),
('m3n4o5p6-q7r8-9012-qrst-345678901234', NOW(), NOW(), 0, 'Royalties', '#CDDC39', 'INCOME'),
('n4o5p6q7-r8s9-0123-rstu-456789012345', NOW(), NOW(), 0, 'Gifts', '#4DB6AC', 'INCOME'),

-- Additional expense categories
('o5p6q7r8-s9t0-1234-stuv-567890123456', NOW(), NOW(), 0, 'Clothing', '#FF5722', 'EXPENSE'),
('p6q7r8s9-t0u1-2345-tuvw-678901234567', NOW(), NOW(), 0, 'Electronics', '#FF9800', 'EXPENSE'),
('q7r8s9t0-u1v2-3456-uvwx-789012345678', NOW(), NOW(), 0, 'Fitness', '#FFC107', 'EXPENSE'),
('r8s9t0u1-v2w3-4567-vwxy-890123456789', NOW(), NOW(), 0, 'Education', '#FFEB3B', 'EXPENSE'),
('s9t0u1v2-w3x4-5678-wxyz-901234567890', NOW(), NOW(), 0, 'Travel', '#9C27B0', 'EXPENSE'),
('t0u1v2w3-x4y5-6789-xyza-012345678901', NOW(), NOW(), 0, 'Insurance', '#673AB7', 'EXPENSE'),
('u1v2w3x4-y5z6-7890-yzab-123456789012', NOW(), NOW(), 0, 'Home Maintenance', '#E91E63', 'EXPENSE'),
('v2w3x4y5-z6a7-8901-zabc-234567890123', NOW(), NOW(), 0, 'Car Maintenance', '#F44336', 'EXPENSE'),
('w3x4y5z6-a7b8-9012-abcd-345678901234', NOW(), NOW(), 0, 'Subscriptions', '#9C27B0', 'EXPENSE'),
('x4y5z6a7-b8c9-0123-bcde-456789012345', NOW(), NOW(), 0, 'Charity', '#607D8B', 'EXPENSE');

-- Insert additional mock users
INSERT INTO users (uuid, created_at, updated_at, version, email, password, first_name, last_name) VALUES
                                                                                                      ('u3d4e5f6-g7h8-9012-cdef-345678901234', NOW(), NOW(), 0, 'mike.wilson@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Mike', 'Wilson'),
                                                                                                      ('u4e5f6g7-h8i9-0123-defg-456789012345', NOW(), NOW(), 0, 'sarah.johnson@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Sarah', 'Johnson'),
                                                                                                      ('u5f6g7h8-i9j0-1234-efgh-567890123456', NOW(), NOW(), 0, 'david.brown@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'David', 'Brown'),
                                                                                                      ('u6g7h8i9-j0k1-2345-fghi-678901234567', NOW(), NOW(), 0, 'emily.davis@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Emily', 'Davis');

-- Extended mock transactions for John Doe (User ID: 1) - 35 transactions
INSERT INTO transactions (uuid, created_at, updated_at, version, amount, description, type, date, user_id, category_id) VALUES
-- January 2024 Income
('t7h8i9j0-k1l2-3456-ghij-789012345679', NOW(), NOW(), 0, 3500.00, 'Monthly salary January', 'INCOME', '2024-01-05 09:00:00', 1, 1),
('t8i9j0k1-l2m3-4567-hijk-890123456780', NOW(), NOW(), 0, 500.00, 'Q4 Bonus', 'INCOME', '2024-01-10 10:30:00', 1, 11),
('t9j0k1l2-m3n4-5678-ijkl-901234567891', NOW(), NOW(), 0, 1200.00, 'Freelance project - Website', 'INCOME', '2024-01-15 14:00:00', 1, 2),
('t0k1l2m3-n4o5-6789-jklm-012345678902', NOW(), NOW(), 0, 300.00, 'Stock dividends', 'INCOME', '2024-01-20 16:45:00', 1, 3),

-- January 2024 Expenses
('t1l2m3n4-o5p6-7890-klmn-123456789013', NOW(), NOW(), 0, -1200.00, 'January rent', 'EXPENSE', '2024-01-01 08:00:00', 1, 5),
('t2m3n4o5-p6q7-8901-lmno-234567890124', NOW(), NOW(), 0, -150.00, 'Electricity bill', 'EXPENSE', '2024-01-03 12:00:00', 1, 6),
('t3n4o5p6-q7r8-9012-mnop-345678901235', NOW(), NOW(), 0, -85.50, 'Weekly groceries', 'EXPENSE', '2024-01-05 18:30:00', 1, 4),
('t4o5p6q7-r8s9-0123-nopq-456789012346', NOW(), NOW(), 0, -45.00, 'Monthly bus pass', 'EXPENSE', '2024-01-06 07:15:00', 1, 7),
('t5p6q7r8-s9t0-1234-opqr-567890123457', NOW(), NOW(), 0, -75.00, 'Movie night', 'EXPENSE', '2024-01-07 20:00:00', 1, 8),
('t6q7r8s9-t0u1-2345-pqrs-678901234568', NOW(), NOW(), 0, -120.00, 'Dinner with friends', 'EXPENSE', '2024-01-08 19:30:00', 1, 10),
('t7r8s9t0-u1v2-3456-qrst-789012345679', NOW(), NOW(), 0, -89.99, 'Netflix annual subscription', 'EXPENSE', '2024-01-10 10:00:00', 1, 21),
('t8s9t0u1-v2w3-4567-rstu-890123456780', NOW(), NOW(), 0, -65.00, 'New jeans', 'EXPENSE', '2024-01-12 15:20:00', 1, 15),
('t9t0u1v2-w3x4-5678-stuv-901234567891', NOW(), NOW(), 0, -45.00, 'Gym membership', 'EXPENSE', '2024-01-15 09:00:00', 1, 17),
('t0u1v2w3-x4y5-6789-tuvw-012345678902', NOW(), NOW(), 0, -200.00, 'Car insurance', 'EXPENSE', '2024-01-18 11:30:00', 1, 20),

-- February 2024 Income
('t1v2w3x4-y5z6-7890-uvwx-123456789013', NOW(), NOW(), 0, 3500.00, 'Monthly salary February', 'INCOME', '2024-02-05 09:00:00', 1, 1),
('t2w3x4y5-z6a7-8901-vwxy-234567890124', NOW(), NOW(), 0, 800.00, 'Freelance project - Mobile app', 'INCOME', '2024-02-12 14:30:00', 1, 2),
('t3x4y5z6-a7b8-9012-wxyz-345678901235', NOW(), NOW(), 0, 250.00, 'Commission from sales', 'INCOME', '2024-02-18 16:00:00', 1, 12),

-- February 2024 Expenses
('t4y5z6a7-b8c9-0123-xyza-456789012346', NOW(), NOW(), 0, -1200.00, 'February rent', 'EXPENSE', '2024-02-01 08:00:00', 1, 5),
('t5z6a7b8-c9d0-1234-yzab-567890123457', NOW(), NOW(), 0, -95.75, 'Groceries', 'EXPENSE', '2024-02-03 17:45:00', 1, 4),
('t6a7b8c9-d0e1-2345-zabc-678901234568', NOW(), NOW(), 0, -120.00, 'Internet bill', 'EXPENSE', '2024-02-05 12:00:00', 1, 6),
('t7b8c9d0-e1f2-3456-abcd-789012345679', NOW(), NOW(), 0, -150.00, 'Weekend trip', 'EXPENSE', '2024-02-10 08:30:00', 1, 19),
('t8c9d0e1-f2g3-4567-bcde-890123456780', NOW(), NOW(), 0, -89.99, 'Online course', 'EXPENSE', '2024-02-14 20:15:00', 1, 18),
('t9d0e1f2-g3h4-5678-cdef-901234567891', NOW(), NOW(), 0, -45.00, 'Taxi to airport', 'EXPENSE', '2024-02-16 06:00:00', 1, 7),
('t0e1f2g3-h4i5-6789-defg-012345678902', NOW(), NOW(), 0, -75.00, 'Concert tickets', 'EXPENSE', '2024-02-20 19:00:00', 1, 8),

-- March 2024 Income
('t1f2g3h4-i5j6-7890-efgh-123456789013', NOW(), NOW(), 0, 3500.00, 'Monthly salary March', 'INCOME', '2024-03-05 09:00:00', 1, 1),
('t2g3h4i5-j6k7-8901-fghi-234567890124', NOW(), NOW(), 0, 1500.00, 'Freelance project - E-commerce', 'INCOME', '2024-03-10 15:30:00', 1, 2),
('t3h4i5j6-k7l8-9011-ghij-345678901235', NOW(), NOW(), 0, 100.00, 'Birthday gift', 'INCOME', '2024-03-15 10:00:00', 1, 14),

-- March 2024 Expenses
('t4i5j6k7-l8m9-0122-hijk-456789012346', NOW(), NOW(), 0, -1200.00, 'March rent', 'EXPENSE', '2024-03-01 08:00:00', 1, 5),
('t5j6k7l8-m9n0-1233-ijkl-567890123457', NOW(), NOW(), 0, -110.25, 'Groceries', 'EXPENSE', '2024-03-04 18:20:00', 1, 4),
('t6k7l8m9-n0o1-2344-jklm-678901234568', NOW(), NOW(), 0, -200.00, 'Car maintenance', 'EXPENSE', '2024-03-08 09:45:00', 1, 22),
('t7l8m9n0-o1p2-3455-klmn-789012345679', NOW(), NOW(), 0, -50.00, 'Charity donation', 'EXPENSE', '2024-03-12 14:00:00', 1, 23),
('t8m9n0o1-p2q3-4566-lmno-890123456780', NOW(), NOW(), 0, -299.99, 'New smartphone', 'EXPENSE', '2024-03-18 16:30:00', 1, 16),
('t9n0o1p2-q3r4-5677-mnop-901234567891', NOW(), NOW(), 0, -45.00, 'Monthly bus pass', 'EXPENSE', '2024-03-20 07:15:00', 1, 7);

-- Extended mock transactions for Jane Smith (User ID: 2) - 35 transactions
INSERT INTO transactions (uuid, created_at, updated_at, version, amount, description, type, date, user_id, category_id) VALUES
-- January 2024 Income
('t0o1p2q3-r4s5-6788-nopq-012345678902', NOW(), NOW(), 0, 4200.00, 'Monthly salary January', 'INCOME', '2024-01-05 09:00:00', 2, 1),
('t1p2q3r4-s5t6-7899-opqr-123456789013', NOW(), NOW(), 0, 1000.00, 'Annual bonus', 'INCOME', '2024-01-12 11:00:00', 2, 11),
('t2q3r4s5-t6u7-8900-pqrs-234567890124', NOW(), NOW(), 0, 600.00, 'Consulting project', 'INCOME', '2024-01-18 14:30:00', 2, 2),

-- January 2024 Expenses
('t3r4s5t6-u7v8-9011-qrst-345678901235', NOW(), NOW(), 0, -1350.00, 'January studio rent', 'EXPENSE', '2024-01-01 08:00:00', 2, 5),
('t4s5t6u7-v8w9-0122-rstu-456789012346', NOW(), NOW(), 0, -95.75, 'Organic groceries', 'EXPENSE', '2024-01-03 17:30:00', 2, 4),
('t5t6u7v8-w9x0-1233-stuv-567890123457', NOW(), NOW(), 0, -120.00, 'Internet bill', 'EXPENSE', '2024-01-05 12:00:00', 2, 6),
('t6u7v8w9-x0y1-2344-tuvw-678901234568', NOW(), NOW(), 0, -60.00, 'Taxi rides', 'EXPENSE', '2024-01-07 08:45:00', 2, 7),
('t7v8w9x0-y1z2-3455-uvwx-789012345679', NOW(), NOW(), 0, -120.00, 'Concert tickets', 'EXPENSE', '2024-01-10 19:00:00', 2, 8),
('t8w9x0y1-z2a3-4566-vwxy-890123456780', NOW(), NOW(), 0, -85.00, 'Dinner at Italian restaurant', 'EXPENSE', '2024-01-12 20:30:00', 2, 10),
('t9x0y1z2-a3b4-5677-wxyz-901234567891', NOW(), NOW(), 0, -45.00, 'Yoga class', 'EXPENSE', '2024-01-15 18:00:00', 2, 17),
('t0y1z2a3-b4c5-6788-xyza-012345678902', NOW(), NOW(), 0, -150.00, 'New dress', 'EXPENSE', '2024-01-18 15:20:00', 2, 15),
('t1z2a3b4-c5d6-7899-yzab-123456789013', NOW(), NOW(), 0, -89.99, 'Spotify subscription', 'EXPENSE', '2024-01-20 10:00:00', 2, 21),

-- February 2024 Income
('t2a3b4c5-d6e7-8900-zabc-234567890124', NOW(), NOW(), 0, 4200.00, 'Monthly salary February', 'INCOME', '2024-02-05 09:00:00', 2, 1),
('t3b4c5d6-e7f8-9011-abcd-345678901235', NOW(), NOW(), 0, 750.00, 'Freelance writing', 'INCOME', '2024-02-14 16:00:00', 2, 2),
('t4c5d6e7-f8g9-0122-bcde-456789012346', NOW(), NOW(), 0, 300.00, 'Royalties from book', 'INCOME', '2024-02-20 11:30:00', 2, 13),

-- February 2024 Expenses
('t5d6e7f8-g9h0-1233-cdef-567890123457', NOW(), NOW(), 0, -1350.00, 'February studio rent', 'EXPENSE', '2024-02-01 08:00:00', 2, 5),
('t6e7f8g9-h0i1-2344-defg-678901234568', NOW(), NOW(), 0, -110.50, 'Groceries', 'EXPENSE', '2024-02-04 18:15:00', 2, 4),
('t7f8g9h0-i1j2-3455-efgh-789012345679', NOW(), NOW(), 0, -200.00, 'Flight tickets', 'EXPENSE', '2024-02-08 14:00:00', 2, 19),
('t8g9h0i1-j2k3-4566-fghi-890123456780', NOW(), NOW(), 0, -250.00, 'Hotel booking', 'EXPENSE', '2024-02-08 15:30:00', 2, 19),
('t9h0i1j2-k3l4-5677-ghij-901234567891', NOW(), NOW(), 0, -45.00, 'Gym membership', 'EXPENSE', '2024-02-10 09:00:00', 2, 17),
('t0i1j2k3-l4m5-6788-hijk-012345678902', NOW(), NOW(), 0, -75.00, 'Theater tickets', 'EXPENSE', '2024-02-15 19:30:00', 2, 8),

-- March 2024 Income
('t1j2k3l4-m5n6-7899-ijkl-123456789013', NOW(), NOW(), 0, 4200.00, 'Monthly salary March', 'INCOME', '2024-03-05 09:00:00', 2, 1),
('t2k3l4m5-n6o7-8900-jklm-234567890124', NOW(), NOW(), 0, 900.00, 'Consulting work', 'INCOME', '2024-03-12 14:00:00', 2, 2),
('t3l4m5n6-o7p8-9011-klmn-345678901235', NOW(), NOW(), 0, 150.00, 'Commission', 'INCOME', '2024-03-18 16:45:00', 2, 12),

-- March 2024 Expenses
('t4m5n6o7-p8q9-0122-lmno-456789012346', NOW(), NOW(), 0, -1350.00, 'March studio rent', 'EXPENSE', '2024-03-01 08:00:00', 2, 5),
('t5n6o7p8-q9r0-1233-mnop-567890123457', NOW(), NOW(), 0, -125.75, 'Groceries', 'EXPENSE', '2024-03-05 17:45:00', 2, 4),
('t6o7p8q9-r0s1-2344-nopq-678901234568', NOW(), NOW(), 0, -89.99, 'Online course subscription', 'EXPENSE', '2024-03-08 20:00:00', 2, 18),
('t7p8q9r0-s1t2-3455-opqr-789012345679', NOW(), NOW(), 0, -45.00, 'Monthly bus pass', 'EXPENSE', '2024-03-10 07:30:00', 2, 7),
('t8q9r0s1-t2u3-4566-pqrs-890123456780', NOW(), NOW(), 0, -180.00, 'Health insurance', 'EXPENSE', '2024-03-15 11:00:00', 2, 20),
('t9r0s1t2-u3v4-5677-qrst-901234567891', NOW(), NOW(), 0, -60.00, 'Charity donation', 'EXPENSE', '2024-03-20 14:30:00', 2, 23);

-- Mock transactions for Mike Wilson (User ID: 3) - 30 transactions
INSERT INTO transactions (uuid, created_at, updated_at, version, amount, description, type, date, user_id, category_id) VALUES
-- Income transactions
('t0s1t2u3-v4w5-6788-rstu-012345678902', NOW(), NOW(), 0, 3800.00, 'Monthly salary', 'INCOME', '2024-01-05 09:00:00', 3, 1),
('t1t2u3v4-w5x6-7899-stuv-123456789013', NOW(), NOW(), 0, 500.00, 'Performance bonus', 'INCOME', '2024-01-15 11:00:00', 3, 11),
('t2u3v4w5-x6y7-8900-tuvw-234567890124', NOW(), NOW(), 0, 1200.00, 'Side project', 'INCOME', '2024-01-25 16:30:00', 3, 2),
('t3v4w5x6-y7z8-9011-uvwx-345678901235', NOW(), NOW(), 0, 3800.00, 'Monthly salary', 'INCOME', '2024-02-05 09:00:00', 3, 1),
('t4w5x6y7-z8a9-0122-vwxy-456789012346', NOW(), NOW(), 0, 3800.00, 'Monthly salary', 'INCOME', '2024-03-05 09:00:00', 3, 1),

-- Expense transactions
('t5x6y7z8-a9b0-1233-wxyz-567890123457', NOW(), NOW(), 0, -1100.00, 'Apartment rent', 'EXPENSE', '2024-01-01 08:00:00', 3, 5),
('t6y7z8a9-b0c1-2344-xyza-678901234568', NOW(), NOW(), 0, -150.00, 'Electricity bill', 'EXPENSE', '2024-01-03 12:00:00', 3, 6),
('t7z8a9b0-c1d2-3455-yzab-789012345679', NOW(), NOW(), 0, -80.00, 'Groceries', 'EXPENSE', '2024-01-05 18:00:00', 3, 4),
('t8a9b0c1-d2e3-4566-zabc-890123456780', NOW(), NOW(), 0, -60.00, 'Gas for car', 'EXPENSE', '2024-01-07 10:30:00', 3, 7),
('t9b0c1d2-e3f4-5677-abcd-901234567891', NOW(), NOW(), 0, -200.00, 'New headphones', 'EXPENSE', '2024-01-10 15:45:00', 3, 16),
('t0c1d2e3-f4g5-6788-bcde-012345678902', NOW(), NOW(), 0, -45.00, 'Movie tickets', 'EXPENSE', '2024-01-12 20:00:00', 3, 8),
('t1d2e3f4-g5h6-7899-cdef-123456789013', NOW(), NOW(), 0, -90.00, 'Dinner date', 'EXPENSE', '2024-01-14 19:30:00', 3, 10),
('t2e3f4g5-h6i7-8900-defg-234567890124', NOW(), NOW(), 0, -1100.00, 'February rent', 'EXPENSE', '2024-02-01 08:00:00', 3, 5),
('t3f4g5h6-i7j8-9011-efgh-345678901235', NOW(), NOW(), 0, -85.00, 'Groceries', 'EXPENSE', '2024-02-04 17:30:00', 3, 4),
('t4g5h6i7-j8k9-0122-fghi-456789012346', NOW(), NOW(), 0, -120.00, 'Internet bill', 'EXPENSE', '2024-02-06 12:00:00', 3, 6),
('t5h6i7j8-k9l0-1233-ghij-567890123457', NOW(), NOW(), 0, -300.00, 'Car insurance', 'EXPENSE', '2024-02-10 11:00:00', 3, 20),
('t6i7j8k9-l0m1-2344-hijk-678901234568', NOW(), NOW(), 0, -75.00, 'Fitness membership', 'EXPENSE', '2024-02-15 09:00:00', 3, 17),
('t7j8k9l0-m1n2-3455-ijkl-789012345679', NOW(), NOW(), 0, -1100.00, 'March rent', 'EXPENSE', '2024-03-01 08:00:00', 3, 5),
('t8k9l0m1-n2o3-4566-jklm-890123456780', NOW(), NOW(), 0, -95.00, 'Groceries', 'EXPENSE', '2024-03-05 18:15:00', 3, 4),
('t9l0m1n2-o3p4-5677-klmn-901234567891', NOW(), NOW(), 0, -150.00, 'Car maintenance', 'EXPENSE', '2024-03-08 09:00:00', 3, 22),
('t0m1n2o3-p4q5-6788-lmno-012345678902', NOW(), NOW(), 0, -50.00, 'Books', 'EXPENSE', '2024-03-12 14:30:00', 3, 18),
('t1n2o3p4-q5r6-7899-mnop-123456789013', NOW(), NOW(), 0, -120.00, 'Concert', 'EXPENSE', '2024-03-15 19:00:00', 3, 8),
('t2o3p4q5-r6s7-8900-nopq-234567890124', NOW(), NOW(), 0, -45.00, 'Monthly transport', 'EXPENSE', '2024-03-18 07:45:00', 3, 7),
('t3p4q5r6-s7t8-9011-opqr-345678901235', NOW(), NOW(), 0, -89.99, 'Streaming services', 'EXPENSE', '2024-03-20 10:00:00', 3, 21),
('t4q5r6s7-t8u9-0122-pqrs-456789012346', NOW(), NOW(), 0, -35.00, 'Coffee shop', 'EXPENSE', '2024-03-22 08:30:00', 3, 10),
('t5r6s7t8-u9v0-1233-qrst-567890123457', NOW(), NOW(), 0, -25.00, 'Lunch', 'EXPENSE', '2024-03-25 13:00:00', 3, 10);

-- Mock transactions for Sarah Johnson (User ID: 4) - 25 transactions
INSERT INTO transactions (uuid, created_at, updated_at, version, amount, description, type, date, user_id, category_id) VALUES
-- Income transactions
('t6s7t8u9-v0w1-2344-rstu-678901234568', NOW(), NOW(), 0, 3200.00, 'Monthly salary', 'INCOME', '2024-01-05 09:00:00', 4, 1),
('t7t8u9v0-w1x2-3455-stuv-789012345679', NOW(), NOW(), 0, 400.00, 'Freelance design', 'INCOME', '2024-01-20 15:00:00', 4, 2),
('t8u9v0w1-x2y3-4566-tuvw-890123456780', NOW(), NOW(), 0, 3200.00, 'Monthly salary', 'INCOME', '2024-02-05 09:00:00', 4, 1),
('t9v0w1x2-y3z4-5677-uvwx-901234567891', NOW(), NOW(), 0, 3200.00, 'Monthly salary', 'INCOME', '2024-03-05 09:00:00', 4, 1),

-- Expense transactions
('t0w1x2y3-z4a5-6788-vwxy-012345678902', NOW(), NOW(), 0, -900.00, 'Room rent', 'EXPENSE', '2024-01-01 08:00:00', 4, 5),
('t1x2y3z4-a5b6-7899-wxyz-123456789013', NOW(), NOW(), 0, -70.00, 'Groceries', 'EXPENSE', '2024-01-04 17:00:00', 4, 4),
('t2y3z4a5-b6c7-8900-xyza-234567890124', NOW(), NOW(), 0, -80.00, 'Internet bill', 'EXPENSE', '2024-01-06 12:00:00', 4, 6),
('t3z4a5b6-c7d8-9011-yzab-345678901235', NOW(), NOW(), 0, -45.00, 'Public transport', 'EXPENSE', '2024-01-08 07:30:00', 4, 7),
('t4a5b6c7-d8e9-0122-zabc-456789012346', NOW(), NOW(), 0, -120.00, 'New shoes', 'EXPENSE', '2024-01-12 14:20:00', 4, 15),
('t5b6c7d8-e9f0-1233-abcd-567890123457', NOW(), NOW(), 0, -60.00, 'Haircut', 'EXPENSE', '2024-01-15 11:00:00', 4, 8),
('t6c7d8e9-f0g1-2344-bcde-678901234568', NOW(), NOW(), 0, -900.00, 'February rent', 'EXPENSE', '2024-02-01 08:00:00', 4, 5),
('t7d8e9f0-g1h2-3455-cdef-789012345679', NOW(), NOW(), 0, -75.00, 'Groceries', 'EXPENSE', '2024-02-05 18:00:00', 4, 4),
('t8e9f0g1-h2i3-4566-defg-890123456780', NOW(), NOW(), 0, -150.00, 'Weekend trip', 'EXPENSE', '2024-02-10 08:00:00', 4, 19),
('t9f0g1h2-i3j4-5677-efgh-901234567891', NOW(), NOW(), 0, -45.00, 'Yoga class', 'EXPENSE', '2024-02-14 18:30:00', 4, 17),
('t0g1h2i3-j4k5-6788-fghi-012345678902', NOW(), NOW(), 0, -900.00, 'March rent', 'EXPENSE', '2024-03-01 08:00:00', 4, 5),
('t1h2i3j4-k5l6-7899-ghij-123456789013', NOW(), NOW(), 0, -85.00, 'Groceries', 'EXPENSE', '2024-03-06 17:45:00', 4, 4),
('t2i3j4k5-l6m7-8900-hijk-234567890124', NOW(), NOW(), 0, -200.00, 'New tablet', 'EXPENSE', '2024-03-10 16:00:00', 4, 16),
('t3j4k5l6-m7n8-9011-ijkl-345678901235', NOW(), NOW(), 0, -35.00, 'Coffee with friends', 'EXPENSE', '2024-03-12 10:30:00', 4, 10),
('t4k5l6m7-n8o9-0122-jklm-456789012346', NOW(), NOW(), 0, -89.99, 'Online subscription', 'EXPENSE', '2024-03-15 09:00:00', 4, 21),
('t5l6m7n8-o9p0-1233-klmn-567890123457', NOW(), NOW(), 0, -50.00, 'Charity', 'EXPENSE', '2024-03-18 14:00:00', 4, 23),
('t6m7n8o9-p0q1-2344-lmno-678901234568', NOW(), NOW(), 0, -25.00, 'Lunch', 'EXPENSE', '2024-03-20 13:00:00', 4, 10),
('t7n8o9p0-q1r2-3455-mnop-789012345679', NOW(), NOW(), 0, -45.00, 'Monthly transport', 'EXPENSE', '2024-03-22 07:45:00', 4, 7),
('t8o9p0q1-r2s3-4566-nopq-890123456780', NOW(), NOW(), 0, -30.00, 'Snacks', 'EXPENSE', '2024-03-25 16:00:00', 4, 4),
('t9p0q1r2-s3t4-5677-opqr-901234567891', NOW(), NOW(), 0, -20.00, 'Magazine', 'EXPENSE', '2024-03-28 12:00:00', 4, 18);