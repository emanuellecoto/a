CREATE OR REPLACE VIEW FIDE_FACTURAS_PENDIENTES_CLIENTES_DOCTORES_V AS
SELECT 
    F.ID_FACTURA, 
    U.NOMBRE AS CLIENTE, 
    D.NOMBRE AS DOCTOR, 
    F.FECHA, 
    F.COSTO_TOTAL
FROM 
    FIDE_FACTURA_TB F
JOIN 
    FIDE_USUARIO_TB U ON F.ID_USUARIO = U.ID_USUARIO
LEFT JOIN 
    FIDE_DOCTORES_TB DO ON F.ID_DOCTOR = DO.ID_DOCTOR
JOIN 
    FIDE_ESTADO_TB E ON F.ID_ESTADO = E.ID_ESTADO
WHERE 
    E.DESCRIPCION = 'I';

-- Vista: Citas con servicios y consultorios
CREATE OR REPLACE VIEW FIDE_CITAS_SERVICIOS_CONSULTORIOS_V AS
SELECT 
    C.ID_CITA, 
    U.NOMBRE AS CLIENTE, 
    S.DESCRIPCION AS SERVICIO, 
    CO.HILERA AS CONSULTORIO, 
    C.FECHA, 
    C.HORA, 
    E.DESCRIPCION AS ESTADO
FROM 
    FIDE_CITAS_TB C
JOIN 
    FIDE_USUARIO_TB U ON C.ID_USUARIO = U.ID_USUARIO
JOIN 
    FIDE_SERVICIOS_TB S ON C.ID_SERVICIO = S.ID_SERVICIO
LEFT JOIN 
    FIDE_CONSULTORIO_TB CO ON C.ID_CONSULTORIO = CO.ID_CONSULTORIO
JOIN 
    FIDE_ESTADO_TB E ON C.ID_ESTADO = E.ID_ESTADO;

-- Vista: Proveedores con productos y su estado
CREATE OR REPLACE VIEW FIDE_PROVEEDORES_PRODUCTOS_ESTADOS_V AS
SELECT 
    P.ID_PROVEEDOR, 
    P.NOMBRE_PROVEEDOR, 
    COUNT(I.ID_PRODUCTO) AS TOTAL_PRODUCTOS, 
    E.DESCRIPCION AS ESTADO
FROM 
    FIDE_PROVEEDORES_TB P
LEFT JOIN 
    FIDE_INVENTARIO_TB I ON P.ID_PROVEEDOR = I.ID_PROVEEDOR
JOIN 
    FIDE_ESTADO_TB E ON P.ID_ESTADO = E.ID_ESTADO
GROUP BY 
    P.ID_PROVEEDOR, P.NOMBRE_PROVEEDOR, E.DESCRIPCION;

-- Vista: Recetas con pacientes y productos
CREATE OR REPLACE VIEW FIDE_RECETAS_PACIENTES_PRODUCTOS_V AS
SELECT 
    R.ID_RECETA, 
    U.NOMBRE AS PACIENTE, 
    P.DESCRIPCION AS PRODUCTO, 
    R.DOSIS, 
    R.FECHA, 
    E.DESCRIPCION AS ESTADO
FROM 
    FIDE_RECETAS_TB R
JOIN 
    FIDE_USUARIO_TB U ON R.ID_USUARIO = U.ID_USUARIO
JOIN 
    FIDE_INVENTARIO_TB P ON R.ID_PRODUCTO = P.ID_PRODUCTO
JOIN 
    FIDE_ESTADO_TB E ON R.ID_ESTADO = E.ID_ESTADO;

-- Vista: Métodos de pago con facturas asociadas
CREATE OR REPLACE VIEW FIDE_METODOS_PAGO_FACTURAS_V AS
SELECT 
    MP.ID_METODO, 
    MP.METODO_PAGO, 
    COUNT(F.ID_FACTURA) AS TOTAL_FACTURAS, 
    E.DESCRIPCION AS ESTADO
FROM 
    FIDE_METODO_PAGO_TB MP
LEFT JOIN 
    FIDE_FACTURA_TB F ON MP.ID_METODO = F.ID_METODO_PAGO
JOIN 
    FIDE_ESTADO_TB E ON MP.ID_ESTADO = E.ID_ESTADO
GROUP BY 
    MP.ID_METODO, MP.METODO_PAGO, E.DESCRIPCION;

