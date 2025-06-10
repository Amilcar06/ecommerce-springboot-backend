| Método HTTP | Endpoint | Descripción |
| ----- | ----- | ----- |
| **POST** | /api/auth/signup | Registra un nuevo usuario en el sistema. Valida que el nombre de usuario y correo electrónico sean únicos. |
| **POST** | /api/auth/login | Autentica al usuario mediante credenciales y devuelve un token JWT junto con información básica del usuario y sus roles. |
| **GET** | /api/auth/session-info | Proporciona información sobre la sesión actual del usuario autenticado sin generar un nuevo token. |
| **POST** | /api/auth/logout | Cierra la sesión del usuario, limpiando el contexto de seguridad. |
| **GET** | /api/profile | Recupera la información completa del perfil del usuario actualmente autenticado, excluyendo datos sensibles. |
| **PUT** | /api/profile | Permite al usuario actualizar su información personal (nombre de usuario, correo, nombre y apellidos). |
| **POST** | /api/profile/change-password | Endpoint especializado para cambiar la contraseña del usuario autenticado. Requiere la contraseña actual como medida de seguridad. |
| **GET** | /api/admin/usuarios | Lista todos los usuarios registrados en el sistema con su información básica y roles asignados. Solo accesible para administradores. |
| **GET** | /api/admin/usuarios/{id} | Recupera la información detallada de un usuario específico mediante su ID. Solo accesible para administradores. |
| **PATCH** | /api/admin/usuarios/{id}/active | Permite activar o desactivar usuarios mediante un parámetro booleano. Solo accesible para administradores. |
| **POST** | /api/admin/usuarios/vendedores | Crea un nuevo usuario con rol de vendedor. Funcionalidad exclusiva para administradores. |
| **GET** | /api/admin/usuarios/vendedores | Lista todos los usuarios con rol de vendedor en el sistema. Solo accesible para administradores. |
| **GET** | /api/public/test | Endpoint de prueba accesible públicamente sin autenticación. |
| **GET** | /api/usuarios/test | Endpoint de prueba accesible para usuarios autenticados con cualquier rol. |
| **GET** | /api/vendedores/test | Endpoint de prueba accesible para usuarios con rol de vendedor o administrador. |
| **GET** | /api/admin/test | Endpoint de prueba accesible exclusivamente para administradores. |

### **Controlador REST: /api/pagos**

### El controlador PagoController expone los siguientes endpoints para gestionar pagos:

| Método HTTP	 | Endpoint | Descripción |
| :---- | :---- | :---- |
| POST | /api/pagos	 | Crea un nuevo pago asociado a un pedido. |
| GET	 | /api/pagos/{id}	 | Obtiene un pago por su ID. |
| GET | /api/pagos/pedido/{pedidoId}	 | Lista todos los pagos relacionados a un pedido. |
| PATCH	 | /api/pagos/{id}/estado	 | Actualiza el estado de un pago. |
| GET | /api/pagos	 | Lista todos los pagos registrados. |

**Controlador REST: /api/facturas**

El controlador FacturaController gestiona las operaciones relacionadas con la facturación y expone los siguientes endpoints:

| Método HTTP	 | Endpoint | Descripción |
| :---- | :---- | :---- |
| POST	 | /api/facturas/generar/{pagoId} | Genera una nueva factura asociada a un pago. |
| GET	 | /api/facturas/{id}	 | Obtiene una factura por su ID. |

No se exponen endpoints para eliminar, actualizar o listar múltiples facturas. Debido a que la generación de factura está vinculada estrictamente a un pago único.

**Controlador REST /api/tiendas**

El encargado de gestionar a las tiendas es el TiendaController el cual tiene los siguientes endpoint para realizar las siguientes operaciones:

| Método HTTP	 | Endpoint | Descripción |
| :---- | :---- | :---- |
| POST | /api/tiendas	 | Permite crear una nueva tienda |
| GET	 | /api/tiendas	 | Obtiene todas la tiendas del ecommerce |
| PUT | /api/tiendas/{id} | Actualiza la tienda en cuestión por su id . |
| DELETE | /api/tiendas/{id}	 | Elimina la tienda respectiva del ecommerce completamente con sus categorías y los productos que hay dentro de ella |

**Controlador REST /api/categorias**

El encargado de las categorías es el CategoriaController el cual tiene los siguientes endpoint para realizar:

| Método HTTP	 | Endpoint | Descripción |
| :---- | :---- | :---- |
| GET	 | /api/categorias	 | Obtiene todas la categorías existentes del e-commerce |
| GET	 | /api/categorias/{tiendaid} | Obtiene las categorías específicas para cada tienda mediante su id |
| POST | /api/categorias	 | Permite crear una nueva categoríatambién se debe poner la tienda a la que pertenece esa categoría  |
| PUT | /api/tiendas/{id} | Actualiza la la categoria por su id único |
| DELETE | /api/tiendas/{id}	 | Elimina la categoría con sus productos si es que los tenía  |

**Controlador REST /api/productos**

El encargado de los productos es el ProductoController el cual tiene estos endpoints:

| Método HTTP	 | Endpoint | Descripción |
| :---- | :---- | :---- |
| GET	 | /api/productos | Obtiene todos los productos que hay existentes en el  e-commerce |
| GET	 | /api/productos/tiendas/{tiendaid} | Obtiene los productos de una tienda específica por su id |
| GET	 | /api/productos/categoria/{categoriaid} | Obtiene los productos de una categoría en especifico por su id |
| POST | /api/productos | Permite crear un nuevo producto dentro de una categoría |
| PUT | /api/productos/{id} | Actualiza el producto por su id único |
| DELETE | /api/productos/{id}	 | Elimina el productos por su id |

**Controlador REST /api/reseniaProducto**

El encargado de las categorías es el ReseniaProductoController el cual tiene los siguientes endpoint para realizar:

| Método HTTP	 | Endpoint | Descripción |
| :---- | :---- | :---- |
| GET	 | /api/reseniaProducto/producto/{productoid} | Obtiene todas las reseñas de cierto producto por su id |
| POST | /api/reseniaProducto | Permite crear una nueva reseña para un producto  |
| PUT | /api/reseniaProducto/{id} | Actualiza la reseña de un producto por su id único  |
| DELETE | /api/reseniaProducto/{id}	 | Elimina la reseña de un producto en específico  |

**Controlador REST /api/reseniaTienda**

El encargado de las categorías es el ReseniaTiendaController el cual tiene los siguientes endpoint para realizar:

| Método HTTP	 | Endpoint | Descripción |
| :---- | :---- | :---- |
| GET	 | /api/reseniaTienda/{id} | Obtiene las reseñas hechas a la tienda por su id |
| POST | /api/reseniaTienda | Crea una nueva reseña para la tienda |
| PUT | /api/reseniaTienda/{id} | Actualiza la reseña hecha a la tienda |
| DELETE | /api/reseniaTienda/{id}	 | Elimina la reseña de la tienda por su id único  |

**Controlador REST: /api/carrito.**

El controlador CarritoController ofrece los siguientes endpoints para manejar las operaciones del carrito:

| Método HTTP |                  Endpoint |          Descripción |
| ----- | :---- | :---- |
| GET | /api/carrito/{usuarioId} | Obtiene el carrito actual del usuario. |
| POST | /api/carrito/{usuarioId}/agregar | Agrega un producto al carrito del usuario. |
| DELETE | /api/carrito/{usuarioId}/quitar/{productoId} | Elimina un producto específico del carrito. |
| DELETE | /api/carrito/{usuarioId}/vaciar | Vacía por completo el carrito del usuario |

### **Controlador REST: /api/pedidos.**

El PedidoController expone las siguientes operaciones:

| Método HTTP |                  Endpoint |          Descripción |
| ----- | :---- | :---- |
| POST | /api/pedidos/{usuarioId} | Procesa el carrito del usuario y genera un pedido confirmado. |
| GET | /api/pedidos/{id} | Obtiene los detalles de un pedido por su ID. |
| GET | /api/pedidos/usuario/{id} | Lista todos los pedidos realizados por un usuario. |

**Controlador REST: /api/catalogo**

El CatalogoController expone las siguientes operaciones:

| Método HTTP |                  Endpoint |              Descripción |
| ----- | :---- | :---- |
| POST | /api/catalogo | Crea un nuevo catálogo |
| GET | /api/catalogo | Lista todos los catálogos disponibles |
| GET | /api/catalogo/{id} | Obtiene los detalles de un catálogo específico por su ID. |
| PUT | /api/catalogo/{id} | Actualiza completamente un catálogo existente |
| PATCH | /api/catalogo/{id} | Actualiza parcialmente un catálogo existente. |
| DELETE | /api/catalogo/{id} | Elimina un catálogo por su ID. |


