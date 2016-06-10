BEGIN;

INSERT INTO app.appuser (
  username
  , password
  , firstname
  , lastname
  , email
  , sysrole
  , enabled
)
VALUES
  ('admin', '1qaz2wsx', 'admin', 'admin', 'admin@example.com', 0, TRUE)
  , ('johns', '1qaz2wsx', 'John', 'Salivan', 'johns@example.com', 1, TRUE)
  , ('billk', '1qaz2wsx', 'Bill', 'Klark', 'billk@example.com', 1, TRUE)
  , ('maryl', '1qaz2wsx', 'Mary', 'Linn', 'maryl@example.com', 1, TRUE)
  , ('ninaa', '1qaz2wsx', 'Nina', 'Alessio', 'ninaa@example.com', 1, TRUE);


INSERT INTO app.resource (
  name
  , kind
  , detail
  , owner_permission_id
)
VALUES
  ('xDep Calendar', 'Calendar', 'xDep shared calendar', NULL),
  ('xDep wiki space', 'wiki', 'xDep wiki ', NULL),
  ('Large RestRoom', 'room', 'company shared rest room', NULL);

INSERT INTO app.permission (
  resource_id
  , title
  , description
)
VALUES
  ((SELECT id
    FROM app.resource
    WHERE name = 'xDep Calendar'), 'Reader', 'readers'),
  ((SELECT id
    FROM app.resource
    WHERE name = 'xDep Calendar'), 'Owner', 'owners'),
  ((SELECT id
    FROM app.resource
    WHERE name = 'xDep wiki space'), 'Reader', 'readers'),
  ((SELECT id
    FROM app.resource
    WHERE name = 'xDep wiki space'), 'Editor', 'editors'),
  ((SELECT id
    FROM app.resource
    WHERE name = 'xDep wiki space'), 'Owner', 'owners'),
  ((SELECT id
    FROM app.resource
    WHERE name = 'Large RestRoom'), 'Visitor', 'room visitor'),
  ((SELECT id
    FROM app.resource
    WHERE name = 'Large RestRoom'), 'Coordinator', 'room coordinator'),
  ((SELECT id
    FROM app.resource
    WHERE name = 'Large RestRoom'), 'Owner', 'owners');

UPDATE app.resource res
SET owner_permission_id = rr.id
FROM app.permission rr
WHERE res.id = rr.resource_id AND rr.title = 'Owner';

INSERT INTO app.permission_claim
(
  user_id
  , permission_id
  , state_id
  , approver_id
  , approved_at
  , claimed_at
  , granted_at
  , granter_id
) SELECT
    au.id,
    rr.id,
    2     AS state_id,
    appr.id,
    now() AS approved_at,
    now() AS claimed_at,
    now() AS granted_at,
    gr.id
  FROM (VALUES
    ('billk', 'xDep Calendar', 'Calendar', 'Owner', 'admin', 'admin'),
    ('johns', 'xDep Calendar', 'Calendar', 'Reader', 'billk', 'admin'),
    ('maryl', 'xDep Calendar', 'Calendar', 'Reader', 'billk', 'admin'),
    ('ninaa', 'xDep Calendar', 'Calendar', 'Reader', 'billk', 'admin'),
    ('billk', 'xDep wiki space', 'wiki', 'Owner', 'admin', 'admin'),
    ('ninaa', 'xDep wiki space', 'wiki', 'Owner', 'billk', 'admin'),
    ('johns', 'xDep wiki space', 'wiki', 'Reader', 'ninaa', 'admin'),
    ('maryl', 'xDep wiki space', 'wiki', 'Editor', 'ninaa', 'admin'),
    ('ninaa', 'xDep wiki space', 'wiki', 'Editor', 'ninaa', 'admin'),
    ('billk', 'xDep wiki space', 'room', 'Owner', 'admin', 'admin'),
    ('ninaa', 'xDep wiki space', 'room', 'Coordinator', 'billk', 'admin'),
    ('johns', 'xDep wiki space', 'room', 'Visitor', 'ninaa', 'admin'),
    ('maryl', 'xDep wiki space', 'room', 'Visitor', 'ninaa', 'admin'),
    ('ninaa', 'xDep wiki space', 'room', 'Visitor', 'ninaa', 'admin')
       ) demo
    JOIN app.appuser au ON demo.column1 = au.username
    JOIN app.resource r ON r.name = demo.column2 AND r.kind = demo.column3
    JOIN app.permission rr ON r.id = rr.resource_id AND rr.title = demo.column4
    JOIN app.appuser appr ON appr.username = demo.column5
    JOIN app.appuser gr ON gr.username = demo.column6;

INSERT INTO app.user_permission
(
  permission_id
  , user_id
  , start_at
  , end_at
  , claim_id
  , deleted
)
  SELECT
    permission_id,
    user_id,
    now(),
    'infinity',
    id,
    FALSE
  FROM app.permission_claim;

COMMIT;