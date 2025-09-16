-- Add table comments
COMMENT ON TABLE users IS 'System users table';
COMMENT ON TABLE transactions IS 'Financial transactions table (income/expense)';
COMMENT ON TABLE categories IS 'Transaction categories table';

-- Add column comments for users table
COMMENT ON COLUMN users.id IS 'Unique record identifier (auto-increment)';
COMMENT ON COLUMN users.uuid IS 'Public unique identifier for API';
COMMENT ON COLUMN users.created_at IS 'Record creation timestamp';
COMMENT ON COLUMN users.updated_at IS 'Record last update timestamp';
COMMENT ON COLUMN users.version IS 'Record version for optimistic locking';
COMMENT ON COLUMN users.email IS 'User email address (unique)';
COMMENT ON COLUMN users.password IS 'Hashed user password';
COMMENT ON COLUMN users.first_name IS 'User first name';
COMMENT ON COLUMN users.last_name IS 'User last name';

-- Add column comments for transactions table
COMMENT ON COLUMN transactions.id IS 'Unique record identifier (auto-increment)';
COMMENT ON COLUMN transactions.uuid IS 'Public unique identifier for API';
COMMENT ON COLUMN transactions.created_at IS 'Record creation timestamp';
COMMENT ON COLUMN transactions.updated_at IS 'Record last update timestamp';
COMMENT ON COLUMN transactions.version IS 'Record version for optimistic locking';
COMMENT ON COLUMN transactions.amount IS 'Transaction amount';
COMMENT ON COLUMN transactions.description IS 'Transaction description';
COMMENT ON COLUMN transactions.type IS 'Transaction type: INCOME (income) or EXPENSE (expense)';
COMMENT ON COLUMN transactions.date IS 'Transaction date and time';
COMMENT ON COLUMN transactions.user_id IS 'Reference to the user who owns the transaction';
COMMENT ON COLUMN transactions.category_id IS 'Reference to the transaction category';

-- Add column comments for categories table
COMMENT ON COLUMN categories.id IS 'Unique record identifier (auto-increment)';
COMMENT ON COLUMN categories.uuid IS 'Public unique identifier for API';
COMMENT ON COLUMN categories.created_at IS 'Record creation timestamp';
COMMENT ON COLUMN categories.updated_at IS 'Record last update timestamp';
COMMENT ON COLUMN categories.version IS 'Record version for optimistic locking';
COMMENT ON COLUMN categories.name IS 'Category name';
COMMENT ON COLUMN categories.color IS 'Category color in HEX format (e.g., #FF5733)';
COMMENT ON COLUMN categories.type IS 'Category type: INCOME (for income) or EXPENSE (for expenses)';