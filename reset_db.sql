-- Script para vaciar las tablas de usuarios y roles
-- Primero desactivamos las restricciones de clave foránea temporalmente
SET session_replication_role = 'replica';

-- Vaciamos las tablas relacionadas con usuarios y roles
TRUNCATE TABLE user_roles CASCADE;
TRUNCATE TABLE users CASCADE;
TRUNCATE TABLE roles CASCADE;

-- Reiniciamos las secuencias de ID
ALTER SEQUENCE users_id_seq RESTART WITH 1;
ALTER SEQUENCE roles_id_seq RESTART WITH 1;

-- Volvemos a activar las restricciones de clave foránea
SET session_replication_role = 'origin';

-- Confirmamos los cambios
COMMIT;