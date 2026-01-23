-- Document status enum
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'document_status') THEN
    CREATE TYPE document_status AS ENUM ('UPLOADING', 'UPLOADED');
  END IF;
END$$;

-- Documents
CREATE TABLE IF NOT EXISTS documents (
  id                UUID PRIMARY KEY,
  owner_id          VARCHAR(128) NOT NULL,

  status            document_status NOT NULL,
  filename          VARCHAR(255) NOT NULL,
  content_type      VARCHAR(100) NOT NULL,

  s3_bucket         VARCHAR(255) NOT NULL,
  s3_key_raw        VARCHAR(1024) NOT NULL,

  created_at        TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at        TIMESTAMPTZ NOT NULL DEFAULT now(),

  CONSTRAINT uq_owner_s3key UNIQUE (owner_id, s3_key_raw)
);

CREATE INDEX IF NOT EXISTS idx_documents_owner ON documents(owner_id);
CREATE INDEX IF NOT EXISTS idx_documents_owner_status ON documents(owner_id, status);

-- Status history (audit trail)
CREATE TABLE IF NOT EXISTS document_status_history (
  id           UUID PRIMARY KEY,
  document_id  UUID NOT NULL REFERENCES documents(id) ON DELETE CASCADE,

  from_status  document_status,
  to_status    document_status NOT NULL,

  changed_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
  changed_by   VARCHAR(128) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_doc_status_history_doc ON document_status_history(document_id);
