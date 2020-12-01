DROP VIEW v_subOrganizationOf CASCADE;
CREATE OR REPLACE VIEW v_subOrganizationOf AS 
SELECT innerRel.x2 AS att1, innerRel.x1 AS att2
FROM (
SELECT ora_0.b AS x1, ora_0.a AS x2
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=153
UNION 
SELECT ora_0.a AS x1, ora_0.b AS x2
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=135
) as innerRel