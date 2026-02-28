CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "citext";

CREATE TABLE users (
  id                UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  email             CITEXT NOT NULL UNIQUE,
  password_hash     TEXT NOT NULL,
  full_name         TEXT,
  created_at        TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at        TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE bank_accounts (
  id                     UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  user_id                UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,

  provider               TEXT NOT NULL DEFAULT 'PLAID',        -- future-proof
  plaid_item_id          TEXT NOT NULL,                        -- Plaid Item (per institution link)
  plaid_access_token_enc TEXT NOT NULL,                        -- encrypted at rest
  plaid_account_id       TEXT NOT NULL,                        -- specific account within item

  institution_name       TEXT,
  account_name           TEXT,
  mask                   TEXT,
  account_type           TEXT,
  account_subtype        TEXT,

  last_cursor            TEXT,                                 -- for incremental sync (if supported)
  last_synced_at         TIMESTAMPTZ,

  created_at             TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at             TIMESTAMPTZ NOT NULL DEFAULT now(),

  UNIQUE(user_id, plaid_account_id)
);

CREATE INDEX idx_bank_accounts_user ON bank_accounts(user_id);

CREATE TABLE transactions (
  id                   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  user_id              UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  bank_account_id      UUID NOT NULL REFERENCES bank_accounts(id) ON DELETE CASCADE,

  provider             TEXT NOT NULL DEFAULT 'PLAID',
  provider_txn_id      TEXT NOT NULL,                 -- Plaid transaction_id (or equivalent)
  posted_date          DATE NOT NULL,
  amount               NUMERIC(14,2) NOT NULL,         -- + = outflow (spend), - = inflow (refund/credit) by convention
  currency_code        TEXT NOT NULL DEFAULT 'USD',

  merchant_name        TEXT,
  description          TEXT,
  category             TEXT,                           -- e.g. "Food and Drink"
  category_detailed    TEXT,                           -- optional: more detailed if provider supports
  txn_type             TEXT NOT NULL DEFAULT 'CARD',   -- CARD/TRANSFER/etc
  is_pending           BOOLEAN NOT NULL DEFAULT FALSE,

  created_at           TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at           TIMESTAMPTZ NOT NULL DEFAULT now(),

  UNIQUE(user_id, provider, provider_txn_id)
);

CREATE INDEX idx_transactions_user_date ON transactions(user_id, posted_date DESC);
CREATE INDEX idx_transactions_category ON transactions(user_id, category);

CREATE TABLE budgets (
  id                UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  user_id            UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,

  category           TEXT NOT NULL,          -- "Food and Drink"
  monthly_limit      NUMERIC(14,2) NOT NULL,

  start_month        DATE NOT NULL,          -- first day of month (e.g. 2026-02-01)
  created_at         TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at         TIMESTAMPTZ NOT NULL DEFAULT now(),

  UNIQUE(user_id, category, start_month)
);

CREATE INDEX idx_budgets_user_month ON budgets(user_id, start_month);