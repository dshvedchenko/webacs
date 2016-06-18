CREATE TABLE app.authtoken
(
  user_id   BIGINT,
  token     CHARACTER VARYING(128),
  last_used TIMESTAMP(6) WITH TIME ZONE,
  PRIMARY KEY (token),
  CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES app.appuser (id) ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
);
ALTER TABLE app.authtoken
  OWNER TO acs_app;
