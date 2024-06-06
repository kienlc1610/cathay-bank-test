CREATE SCHEMA IF NOT EXISTS currency_db;
SET SCHEMA currency_db;

CREATE TABLE IF NOT EXISTS currencies (code VARCHAR(5) NOT NULL UNIQUE, symbol VARCHAR(100), rate VARCHAR(100),
 description VARCHAR(200), rate_float FLOAT , created_at TIMESTAMP, updated_at TIMESTAMP, latest_sync_at TIMESTAMP,
 PRIMARY KEY (code)
 );

CREATE TABLE IF NOT EXISTS db_maintenance_management (serviceName VARCHAR(100) NOT NULL UNIQUE, activated BIT,
 created_at TIMESTAMP, updated_at TIMESTAMP, PRIMARY KEY(serviceName));
