
INSERT INTO app.appuser (
  username
  , password
  , firstname
  , lastname
  , email
  , sysrole
  , enabled
  , disabled_at
)
VALUES
  ('internalworkflow', 'oijodenfoeiwjroiwejfoweiowfinoefinowefi', 'internal', 'system', 'admin@example.com', 0,
   TRUE, NULL);

SELECT setval('app.appuser_id_seq', 1000, TRUE);

INSERT INTO app.appuser (
  username
  , password
  , firstname
  , lastname
  , email
  , sysrole
  , enabled
  , disabled_at
)
VALUES
  ('admin', '$2a$10$iaEVN7Mxm.81v1YiJGwUE.sdVVyBDRlItotRDgYVzTCk9abE8qGj.', 'admin', 'admin', 'admin@example.com', 0,
   TRUE, NULL)
  , ('johns', '$2a$10$iaEVN7Mxm.81v1YiJGwUE.sdVVyBDRlItotRDgYVzTCk9abE8qGj.', 'John', 'Salivan', 'johns@example.com', 1,
     TRUE, NULL)
  , ('billk', '$2a$10$iaEVN7Mxm.81v1YiJGwUE.sdVVyBDRlItotRDgYVzTCk9abE8qGj.', 'Bill', 'Klark', 'billk@example.com', 1,
     TRUE, NULL)
  , ('maryl', '$2a$10$iaEVN7Mxm.81v1YiJGwUE.sdVVyBDRlItotRDgYVzTCk9abE8qGj.', 'Mary', 'Linn', 'maryl@example.com', 1,
     TRUE, NULL)
  , ('ninaa', '$2a$10$iaEVN7Mxm.81v1YiJGwUE.sdVVyBDRlItotRDgYVzTCk9abE8qGj.', 'Nina', 'Alessio', 'ninaa@example.com', 1,
     TRUE, NULL)
  ,
  ('fireoff', '$2a$10$iaEVN7Mxm.81v1YiJGwUE.sdVVyBDRlItotRDgYVzTCk9abE8qGj.', 'Nina', 'Alessio', 'ninaa@example.com', 1,
   FALSE, now() - INTERVAL '3 week');
;
INSERT INTO app.restype (
  id,
  name
)
VALUES (1, 'Calendar'), (2, 'wiki'), (3, 'room');

SELECT setval('app.restype_id_seq', 10, TRUE);

INSERT INTO app.resource (
  name
  , restype_id
  , detail
  , owner_permission_id
)
VALUES
  ('xDep Calendar', 1, 'xDep shared calendar', NULL),
  ('xDep wiki space', 2, 'xDep wiki ', NULL),
  ('Large RestRoom', 3, 'company shared rest room', NULL);

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
    2                                    AS state_id,
    appr.id,
    '20160610 10:00:00+0' :: TIMESTAMPTZ AS approved_at,
    '20160610 09:00:00+0' :: TIMESTAMPTZ AS claimed_at,
    '20160610 10:20:00+0' :: TIMESTAMPTZ AS granted_at,
    gr.id,
    '20160611 10:00:00+0' :: TIMESTAMPTZ,
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
    JOIN app.restype rt ON demo.column3 = rt.name
    JOIN app.appuser au ON demo.column1 = au.username
    JOIN app.resource r ON r.name = demo.column2 AND r.restype_id = rt.id
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

