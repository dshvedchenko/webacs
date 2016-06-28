CREATE OR REPLACE FUNCTION app.update_updated_at_column()
  RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = now();
  RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';


-- Table: app.appuser

-- DROP TABLE app.appuser;

CREATE TABLE app.appuser
(
  id          BIGSERIAL NOT NULL,
  username    CHARACTER VARYING(64),
  password    CHARACTER VARYING(128), -- to store as md5/sha256 hash, and need to use Apache Shiro
  firstname   CHARACTER VARYING(64),
  lastname    CHARACTER VARYING(64),
  email       CHARACTER VARYING(64),
  sysrole     INTEGER,
  enabled     BOOLEAN,
  disabled_at TIMESTAMPTZ DEFAULT NULL,
  created_at  TIMESTAMPTZ DEFAULT now(),
  updated_at  TIMESTAMPTZ DEFAULT now(),
  CONSTRAINT appuser_pkey PRIMARY KEY (id),
  CONSTRAINT appuser_username_key UNIQUE (username)
)
WITH (
OIDS = FALSE
);
ALTER TABLE app.appuser
  OWNER TO acs_app;
COMMENT ON COLUMN app.appuser.password IS 'to store as md5/sha256 hash, and need to use Apache Shiro';


CREATE TRIGGER update_appuser_updated_at BEFORE UPDATE ON app.appuser FOR EACH ROW EXECUTE PROCEDURE app.update_updated_at_column();
-- DROP TABLE app.resource;

CREATE TABLE app.restype
(
  id   SERIAL NOT NULL PRIMARY KEY,
  name CHARACTER VARYING(64),
  CONSTRAINT restype_name UNIQUE (name)
) WITH (
OIDS = FALSE
);
ALTER TABLE app.restype
  OWNER TO acs_app;

CREATE TABLE app.resource
(
  id                  BIGSERIAL NOT NULL,
  name                CHARACTER VARYING(128),
  restype_id          INTEGER,
  detail              CHARACTER VARYING(256),
  owner_permission_id BIGINT,
  CONSTRAINT resource_pkey PRIMARY KEY (id),
  CONSTRAINT resource_name_key UNIQUE (name)
)
WITH (
OIDS = FALSE
);
ALTER TABLE app.resource
  OWNER TO acs_app;
ALTER TABLE app.resource
  ADD CONSTRAINT restype_id_fk FOREIGN KEY (restype_id)
REFERENCES app.restype (id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE RESTRICT;

-- Table: app.permission

-- DROP TABLE app.permission;

CREATE TABLE app.permission
(
  id          BIGSERIAL NOT NULL,
  resource_id BIGINT,
  title       CHARACTER VARYING(32),
  description CHARACTER VARYING(128),
  CONSTRAINT resource_role_pkey PRIMARY KEY (id),
  CONSTRAINT resource_role_resource_id_fkey FOREIGN KEY (resource_id)
  REFERENCES app.appuser (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE RESTRICT,
  CONSTRAINT permission_resource_id_title_key UNIQUE (resource_id, title)
)
WITH (
OIDS = FALSE
);
ALTER TABLE app.permission
  OWNER TO acs_app;

ALTER TABLE app.resource
  ADD
  CONSTRAINT owned_by_permission FOREIGN KEY (owner_permission_id)
  REFERENCES app.permission (id) MATCH SIMPLE
  ON UPDATE CASCADE ON DELETE SET NULL DEFERRABLE INITIALLY DEFERRED;

-- DROP TABLE app.user_permission;

CREATE TABLE app.user_permission
(
  id            BIGSERIAL NOT NULL,
  permission_id BIGINT,
  user_id       BIGINT,
  claim_id      BIGINT,
  CONSTRAINT user_permission_pkey PRIMARY KEY (id),
  CONSTRAINT user_permission_permission_id_fkey FOREIGN KEY (permission_id)
  REFERENCES app.permission (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT user_permission_user_id_fkey FOREIGN KEY (user_id)
  REFERENCES app.appuser (id) MATCH SIMPLE
  ON UPDATE RESTRICT ON DELETE CASCADE,
  CONSTRAINT unique_claim UNIQUE (claim_id)
)
WITH (
OIDS = FALSE
);
ALTER TABLE app.user_permission
  OWNER TO acs_app;

-- Table: app.permission_claim

-- DROP TABLE app.permission_claim;

CREATE TABLE app.claim_state
(
  id   INTEGER NOT NULL,
  name VARCHAR(16),
  CONSTRAINT claim_state_pkey PRIMARY KEY (id),
  CONSTRAINT unique_name UNIQUE (name)
)
WITH (
OIDS = FALSE
);
ALTER TABLE app.claim_state
  OWNER TO acs_app;

CREATE TABLE app.permission_claim
(
  id            BIGSERIAL                                 NOT NULL,
  user_id       BIGINT,
  permission_id BIGINT,
  state_id      INTEGER DEFAULT 0,
  approver_id   BIGINT,
  approved_at   TIMESTAMP WITH TIME ZONE,
  claimed_at    TIMESTAMP(6) WITH TIME ZONE DEFAULT NOW() NOT NULL,
  granted_at    TIMESTAMP(6) WITH TIME ZONE,
  granter_id    BIGINT,
  revoked_at    TIMESTAMP(6) WITH TIME ZONE,
  revoker_id    BIGINT,
  start_at      TIMESTAMP WITH TIME ZONE,
  end_at        TIMESTAMP WITH TIME ZONE,
  CONSTRAINT permission_claim_pkey PRIMARY KEY (id),
  CONSTRAINT approved_by_fk FOREIGN KEY (approver_id)
  REFERENCES app.appuser (id) MATCH SIMPLE
  ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT claimed_by_fk FOREIGN KEY (user_id)
  REFERENCES app.appuser (id) MATCH SIMPLE
  ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT claimed_for FOREIGN KEY (permission_id)
  REFERENCES app.permission (id) MATCH SIMPLE
  ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT granted_by_fk FOREIGN KEY (granter_id)
  REFERENCES app.appuser (id) MATCH SIMPLE
  ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT revoked_by_fk FOREIGN KEY (revoker_id)
  REFERENCES app.appuser (id) MATCH SIMPLE
  ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT claim_state_fk FOREIGN KEY (state_id)
  REFERENCES app.claim_state (id) MATCH SIMPLE
  ON UPDATE CASCADE ON DELETE RESTRICT
)
WITH (
OIDS = FALSE
);
ALTER TABLE app.permission_claim
  OWNER TO acs_app;

ALTER TABLE app.user_permission
  ADD CONSTRAINT controlled_by_claim FOREIGN KEY (claim_id)
REFERENCES app.permission_claim (id) MATCH SIMPLE
ON UPDATE CASCADE ON DELETE RESTRICT;

CREATE UNIQUE INDEX permission_claim_user_id_permission_id_initial ON app.permission_claim (user_id, permission_id)
  WHERE state_id = 0;

CREATE UNIQUE INDEX permission_claim_user_id_permission_id_claimed_at_key ON app.permission_claim (user_id, permission_id, claimed_at);

