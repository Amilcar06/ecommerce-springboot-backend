-- PRIMERO REGISTRAR DESDE EL ENPOINT
/*{
  "username": "carlos",
  "email": "carlos@gmail.com",
  "password": "carlos123",
  "firstName": "Carlos",
  "lastName": "Flores",
  "roles": [
    "ROLE_USUARIO"
  ]
}*/
/*{
  "username": "sofia",
  "email": "sofia@gmail.com",
  "password": "sofia123",
  "firstName": "Sofia",
  "lastName": "Mendez",
  "roles": [
    "ROLE_USUARIO"
  ]
}*/
-- Usuario
INSERT INTO usuario (id_usuario, nombre, email, user_id) VALUES
(1, 'Admin', 'admin@ecommerce.com',1),
(2, 'Carlos Flores', 'carlos@gmail.com',2),
(3, 'Sofia Mendez', 'sofia@gmail.com',3);
-- Tiendas
INSERT INTO tienda (id, codigo_tienda, nombre, descripcion) VALUES
(1, 'T001', 'Tienda Tecno', 'Tienda de tecnología'),
(2, 'T002', 'ModaXpress', 'Tienda de ropa y accesorios');

-- Categorías
INSERT INTO categoria (id_categoria, nombre, id_tienda) VALUES
(1, 'Celulares', 1),
(2, 'Laptops', 1),
(3, 'Ropa Hombre', 2),
(4, 'Accesorios', 2);

-- Productos
INSERT INTO producto (id_producto, nombre, descripcion, precio, stock, estado, fecha_registro, id_categoria) VALUES
(1, 'iPhone 14', 'Apple iPhone 14 nuevo', 999.99, 10, 'ACTIVO', now(), 1),
(2, 'MacBook Pro', 'Laptop Apple M2', 1999.99, 5, 'ACTIVO', now(), 2),
(3, 'Camisa Casual', 'Camisa de algodón talla M', 29.99, 20, 'ACTIVO', now(), 3),
(4, 'Gorra Adidas', 'Gorra deportiva negra', 19.99, 30, 'ACTIVO', now(), 4);

-- Reseñas de Tienda
INSERT INTO resenia_tienda (id, calificacion, comentario, fecha, id_tienda, id_usuario) VALUES
(1, 5, 'Excelente atención', current_date - INTERVAL '10 days', 1, 2),
(2, 4, 'Buena variedad de productos', current_date - INTERVAL '5 days', 2, 3);

-- Reseñas de Producto
INSERT INTO resenia_producto (id, calificacion, comentario, fecha, id_producto, id_usuario) VALUES
(1, 5, 'Muy buen producto', current_date - INTERVAL '15 days', 1, 2),
(2, 3, 'Calidad media', current_date - INTERVAL '2 days', 3, 3);

-- Carrito
INSERT INTO carrito (id_carrito, id_usuario) VALUES
(1, 2),
(2, 3);

-- Carrito Item
INSERT INTO carrito_item (id_carrito_item, id_carrito, id_producto, cantidad, precio_unitario) VALUES
(1, 1, 1, 1, 999.99),
(2, 1, 3, 2, 29.99),
(3, 2, 4, 3, 19.99);

-- Pedido
INSERT INTO pedido (id_pedido, fecha, id_usuario) VALUES
(1, now() - INTERVAL '3 days', 2),
(2, now() - INTERVAL '1 day', 3);

-- Pedido Detalle
INSERT INTO pedido_detalle (id, id_pedido, id_producto, cantidad, precio_unitario) VALUES
(1, 1, 1, 1, 999.99),
(2, 1, 3, 2, 29.99),
(3, 2, 4, 1, 19.99);

-- Pago
INSERT INTO pago (id, monto, metodo, estado, fecha, referencia, id_pedido) VALUES
(1, 1059.97, 'TARJETA', 'PAGADO', now() - INTERVAL '2 days', 'REF123', 1),
(2, 19.99, 'QR', 'PAGADO', now() - INTERVAL '1 day', 'REF456', 2);

-- Factura
INSERT INTO factura (id, numero, fecha_emision, monto_total, id_pago) VALUES
(1, 'F001-0001', now() - INTERVAL '2 days', 1059.97, 1),
(2, 'F001-0002', now() - INTERVAL '1 day', 19.99, 2);