
select name, value from v$parameter where name='common_user_prefix';


create user Dental identified by dent;
grant create session to Dental;
grant dba to Dental;
grant all privileges to Dental;


------------------------


CREATE TABLE DENT_DOCTERES_TB(
ID_DOCTORES NUMBER PRIMARY KEY, 
ID_USUARIO NUMBER,
SERVIO VARCHAR2(50),
ID_ESTADO BOOLEAN);

CREATE TABLE DENT_ESTADO_TB(
ID_ESTADO BOOLEAN PRIMARY KEY,
DESCRIPCION VARCHAR2(100));

CREATE TABLE DENT_ROLE (
ID_ROLE NUMBER PRIMARY KEY, 
DESCRIPCION VARCHAR2(100),
ID_ESTADO BOOLEAN);

CREATE TABLE DENT_ALMACEN_TB(
ID_UBICACIO NUMBER PRIMARY KEY, 
PISO VARCHAR2(10),
HILERA VARCHAR2(10),
ID_ESTADO BOOLEAN);

CREATE TABLE DENT_SERVICIOS_TB(
ID_SERVICIOS NUMBER PRIMARY KEY,
DESCRIPCION VARCHAR2(100), 
COSTO NUMBER, 
ID_MEDICO NUMBER, 
ID_ESTADO BOOLEAN);

CREATE TABLE DENT_CONSULTORIO_TB(
ID_CONSULTORIO NUMBER PRIMARY KEY, 
ID_SERVICO NUMBER, 
ID_ESTADO BOOLEAN);

CREATE TABLE DENT_INVENTARIO_TB (
ID_PRODUCTO NUMBER PRIMARY KEY, 
DECRIPCION VARCHAR2(50),
FECHA_INGRESADO DATE, 
FECHA_CADUCIDAD DATE, 
CANTIDAD NUMBER, 
ID_ALMACEN NUMBER, 
ID_ESTADO BOOLEAN);


CREATE TABLE DENT_RECETAS_TB( 
ID_RECETAS NUMBER PRIMARY KEY,
ID_USUARIO NUMBER, 
ID_MEDICO NUMBER, 
IN_PRODUCTOS NUMBER, 
FECHA DATE, 
DOSIS VARCHAR2 (50), 
ID_ESTADO BOOLEAN);


CREATE TABLE DENT_DETALLE_TB (
ID_DETALLE NUMBER PRIMARY KEY, 
ID_FACTURA NUMBER, 
ID_MEDICO NUMBER, 
ID_PRODUCTO NUMBER,
CANTIDAD NUMBER, 
PRECIO_UNITARIO NUMBER, 
SUBTOTAL NUMBER, 
TOTAL NUMBER, 
ID_ESTADO BOOLEAN);

CREATE TABLE DENT_FACTURA_TB (
ID_FACTURA NUMBER PRIMARY KEY, 
ID_USUARIO NUMBER, 
ID_MEDICO NUMBER,
FECHA DATE, 
ID_METODO_PAGO NUMBER, 
COSTOS NUMBER, 
ID_CITA NUMBER, 
ID_PRODUCTO NUMBER, 
ID_DETALLE NUMBER,
ID_ESTADO BOOLEAN);


CREATE TABLE DENT_CITAS_TB(
ID_CITAS NUMBER PRIMARY KEY, 
ID_USUARIO NUMBER, 
ID_MEDICO NUMBER, 
FECHA DATE, 
ID_SERVICIO NUMBER, 
ID_CONSULTORIO NUMBER, 
ID_ESTADO BOOLEAN);

CREATE TABLE DENT_METODO_PAGO_TB(
ID_METODO NUMBER PRIMARY KEY,
METODO_PAGO VARCHAR2(50),
ENTIDAD_BANCARIA VARCHAR2(50),
N_TARJETA NUMBER, 
ID_ESTADO BOOLEAN);

CREATE TABLE DENT_ENTIDA_TB(
ID_ENTIDAD NUMBER PRIMARY KEY,
NOMBRE VARCHAR2(40));

CREATE TABLE DENT_PROVEEDORES_TB (
ID_PROVEEDOR NUMBER PRIMARY KEY, 
NOMBRE_PROVEEDOR VARCHAR2 (50), 
EMAIL VARCHAR2(100) UNIQUE,
TELEFONO NUMBER,
ID_DISTRITO NUMBER, 
ID_CANTON NUMBER, 
ID_PROVINCIA NUMBER, 
ID_ESTADO BOOLEAN);

CREATE TABLE DENT_DISTRITO_TB(
ID_DISTRITO NUMBER PRIMARY KEY, 
NOMBRE VARCHAR2(50),
ID_CANTON NUMBER);

CREATE TABLE DENT_CANTON_TB(
ID_CANTON NUMBER PRIMARY KEY,
NOMBRE VARCHAR2 (30), 
ID_PROVINCIA NUMBER); 

CREATE TABLE DENT_PRONVICIA_TB (
ID_PRONVINCIA NUMBER PRIMARY KEY, 
NONBRE VARCHAR2 (50));

CREATE TABLE DENT_USUARIO_TB(
ID_USUARIO NUMBER PRIMARY KEY, 
NOMBRE VARchar2(50),
PRIMER_APELLIDO VARCHAR2(50), 
SEGUNDO_APELLIDO VARCHAR2(50),
EMAIL VARCHAR2 (100) UNIQUE, 
TELEFONO NUMBER, 
ID_ROLE NUMBER, 
ID_ESTADO BOOLEAN);









    










