-- Table: app.appuser

-- DROP TABLE app.appuser;

CREATE TABLE app.appuser
(
  id        BIGSERIAL NOT NULL,
  username  CHARACTER VARYING(64),
  password  CHARACTER VARYING(64), -- to store as md5/sha256 hash, and need to use Apache Shiro
  firstname CHARACTER VARYING(64),
  lastname  CHARACTER VARYING(64),
  email     CHARACTER VARYING(64),
  sysrole   INTEGER,
  enabled   BOOLEAN,
  CONSTRAINT appuser_pkey PRIMARY KEY (id),
  CONSTRAINT appuser_username_key UNIQUE (username)
)
WITH (
OIDS = FALSE
);
ALTER TABLE app.appuser
  OWNER TO acs_app;
COMMENT ON COLUMN app.appuser.password IS 'to store as md5/sha256 hash, and need to use Apache Shiro';

-- DROP TABLE app.resource;

CREATE TABLE app.resource
(
  id            BIGSERIAL NOT NULL,
  name          CHARACTER VARYING(128),
  kind          CHARACTER VARYING(64),
  detail        CHARACTER VARYING(256),
  owner_role_id BIGINT,
  CONSTRAINT resource_pkey PRIMARY KEY (id)
)
WITH (
OIDS = FALSE
);
ALTER TABLE app.resource
  OWNER TO acs_app;

-- Table: app.resource_role

-- DROP TABLE app.resource_role;

CREATE TABLE app.resource_role
(
  id          BIGSERIAL NOT NULL,
  resource_id BIGINT,
  title       CHARACTER VARYING(32),
  description CHARACTER VARYING(128),
  CONSTRAINT resource_role_pkey PRIMARY KEY (id),
  CONSTRAINT resource_role_resource_id_fkey FOREIGN KEY (resource_id)
  REFERENCES app.appuser (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE RESTRICT
)
WITH (
OIDS = FALSE
);
ALTER TABLE app.resource_role
  OWNER TO acs_app;

ALTER TABLE app.resource
  ADD
  CONSTRAINT owned_by_role FOREIGN KEY (owner_role_id)
  REFERENCES app.resource_role (id) MATCH SIMPLE
  ON UPDATE CASCADE ON DELETE SET NULL DEFERRABLE INITIALLY DEFERRED;

-- DROP TABLE app.user_resource_role;

CREATE TABLE app.user_resource_role
(
  id               BIGSERIAL NOT NULL,
  resource_role_id BIGINT,
  user_id          BIGINT,
  start_at         TIMESTAMP WITH TIME ZONE,
  end_at           TIMESTAMP WITH TIME ZONE,
  claim_id         BIGINT,
  deleted          BOOLEAN,
  CONSTRAINT user_resource_role_pkey PRIMARY KEY (id),
  CONSTRAINT user_resource_role_resource_role_id_fkey FOREIGN KEY (resource_role_id)
  REFERENCES app.resource_role (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_resource_role_user_id_fkey FOREIGN KEY (user_id)
  REFERENCES app.appuser (id) MATCH SIMPLE
  ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT unique_claim UNIQUE (claim_id)
)
WITH (
OIDS = FALSE
);
ALTER TABLE app.user_resource_role
  OWNER TO acs_app;

-- Table: app.role_claim

-- DROP TABLE app.role_claim;

CREATE TABLE app.role_claim
(
  id               BIGSERIAL NOT NULL,
  user_id          BIGINT,
  resource_role_id BIGINT,
  state_id         INTEGER,
  approver_id      BIGINT,
  approved_at      TIMESTAMP WITH TIME ZONE,
  claimed_at       TIMESTAMP(6) WITH TIME ZONE,
  granted_at       TIMESTAMP(6) WITH TIME ZONE,
  granter_id       BIGINT,
  CONSTRAINT role_claim_pkey PRIMARY KEY (id),
  CONSTRAINT approved_by_fk FOREIGN KEY (approver_id)
  REFERENCES app.appuser (id) MATCH SIMPLE
  ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT claimed_by_fk FOREIGN KEY (user_id)
  REFERENCES app.appuser (id) MATCH SIMPLE
  ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT claimed_for FOREIGN KEY (resource_role_id)
  REFERENCES app.resource_role (id) MATCH SIMPLE
  ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT granted_by_fk FOREIGN KEY (granter_id)
  REFERENCES app.appuser (id) MATCH SIMPLE
  ON UPDATE CASCADE ON DELETE RESTRICT
)
WITH (
OIDS = FALSE
);
ALTER TABLE app.role_claim
  OWNER TO acs_app;

ALTER TABLE app.user_resource_role
  ADD CONSTRAINT controlled_by_claim FOREIGN KEY (claim_id)
REFERENCES app.role_claim (id) MATCH SIMPLE
ON UPDATE CASCADE ON DELETE RESTRICT;