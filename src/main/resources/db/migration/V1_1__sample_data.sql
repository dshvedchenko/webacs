
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
  ('admin', '$2a$10$iaEVN7Mxm.81v1YiJGwUE.sdVVyBDRlItotRDgYVzTCk9abE8qGj.', 'admin', 'admin', 'admin@example.com', 0,
   TRUE)
  , ('johns', '$2a$10$iaEVN7Mxm.81v1YiJGwUE.sdVVyBDRlItotRDgYVzTCk9abE8qGj.', 'John', 'Salivan', 'johns@example.com', 1,
     TRUE)
  , ('billk', '$2a$10$iaEVN7Mxm.81v1YiJGwUE.sdVVyBDRlItotRDgYVzTCk9abE8qGj.', 'Bill', 'Klark', 'billk@example.com', 1,
     TRUE)
  , ('maryl', '$2a$10$iaEVN7Mxm.81v1YiJGwUE.sdVVyBDRlItotRDgYVzTCk9abE8qGj.', 'Mary', 'Linn', 'maryl@example.com', 1,
     TRUE)
  , ('ninaa', '$2a$10$iaEVN7Mxm.81v1YiJGwUE.sdVVyBDRlItotRDgYVzTCk9abE8qGj.', 'Nina', 'Alessio', 'ninaa@example.com', 1,
     TRUE);


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

INSERT INTO app.claim_state (
  id
  , name
) VALUES (0, 'CLAIMED'), (1, 'APPROVED'), (2, 'GRANTED'), (3, 'REVOKED');


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
  , start_at
  , end_at
) SELECT
    au.id,
    rr.id,
    2     AS state_id,
    appr.id,
    now() AS approved_at,
    now() AS claimed_at,
    now() AS granted_at,
    gr.id,
    now() - INTERVAL '1 day',
    demo.column7 :: TIMESTAMPTZ
  FROM (VALUES
    ('billk', 'xDep Calendar', 'Calendar', 'Owner', 'admin', 'admin', 'infinity'),
    ('johns', 'xDep Calendar', 'Calendar', 'Reader', 'billk', 'admin', 'infinity'),
    ('maryl', 'xDep Calendar', 'Calendar', 'Reader', 'billk', 'admin', 'infinity'),
    ('ninaa', 'xDep Calendar', 'Calendar', 'Reader', 'billk', 'admin', 'infinity'),
    ('billk', 'xDep wiki space', 'wiki', 'Owner', 'admin', 'admin', 'infinity'),
    ('ninaa', 'xDep wiki space', 'wiki', 'Owner', 'billk', 'admin', 'infinity'),
    ('johns', 'xDep wiki space', 'wiki', 'Reader', 'ninaa', 'admin', 'infinity'),
    ('maryl', 'xDep wiki space', 'wiki', 'Editor', 'ninaa', 'admin', 'infinity'),
    ('ninaa', 'xDep wiki space', 'wiki', 'Editor', 'ninaa', 'admin', 'infinity'),
    ('billk', 'Large RestRoom', 'room', 'Owner', 'admin', 'admin', 'infinity'),
    ('ninaa', 'Large RestRoom', 'room', 'Coordinator', 'billk', 'admin', 'infinity'),
    ('johns', 'Large RestRoom', 'room', 'Visitor', 'ninaa', 'admin', 'infinity'),
    ('maryl', 'Large RestRoom', 'room', 'Visitor', 'ninaa', 'admin', 'infinity'),
    ('ninaa', 'Large RestRoom', 'room', 'Visitor', 'ninaa', 'admin', '20160515')
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
  , claim_id
)
  SELECT
    permission_id,
    user_id,
    id
  FROM app.permission_claim;

