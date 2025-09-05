-- V1__init.sql — Initial schema for RaffleApp
-- Conventions: UTC timestamps (TIMESTAMPTZ), UUID PKs via pgcrypto

-- Enable UUID generator (safe to run multiple times)
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Core example entity to verify schema + Flyway
CREATE TABLE IF NOT EXISTS campaigns (
  id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name        VARCHAR(120) NOT NULL,
  description TEXT,
  owner       VARCHAR(120),
  created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Helpful index for listing newest first
CREATE INDEX IF NOT EXISTS idx_campaigns_created_at ON campaigns (created_at DESC);

-- Optional: document the table
COMMENT ON TABLE campaigns IS 'Raffle campaigns/projects';
COMMENT ON COLUMN campaigns.created_at IS 'Creation time (UTC)';
