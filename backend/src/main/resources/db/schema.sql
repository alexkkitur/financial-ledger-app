CREATE TABLE accounts (
  id UUID PRIMARY KEY, account_number VARCHAR(40) NOT NULL UNIQUE,
  holder_name VARCHAR(200) NOT NULL, balance_cents BIGINT NOT NULL CHECK (balance_cents >= 0),
  version BIGINT NOT NULL DEFAULT 0, created_at TIMESTAMPTZ NOT NULL
);
CREATE TABLE ledger_transactions (
  id UUID PRIMARY KEY, transaction_hash CHAR(64) NOT NULL UNIQUE,
  sender_id UUID NOT NULL REFERENCES accounts(id), recipient_id UUID NOT NULL REFERENCES accounts(id),
  amount_cents BIGINT NOT NULL CHECK (amount_cents > 0),
  occurred_at TIMESTAMPTZ NOT NULL, flagged BOOLEAN NOT NULL DEFAULT FALSE,
  flag_reason VARCHAR(120), CHECK (sender_id <> recipient_id)
);
CREATE INDEX idx_transactions_sender_time ON ledger_transactions(sender_id, occurred_at DESC);
CREATE INDEX idx_transactions_flagged ON ledger_transactions(flagged) WHERE flagged = TRUE;
CREATE TABLE aml_alerts (
  id UUID PRIMARY KEY, transaction_id UUID NOT NULL REFERENCES ledger_transactions(id),
  account_id UUID NOT NULL REFERENCES accounts(id), rule_code VARCHAR(80) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL
);
