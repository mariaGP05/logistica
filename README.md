## Proyecto de Logística
## 🛠 Tecnologías utilizadas
- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Validation
- PostgreSQL
- Docker / Docker Compose
- Lombok
- MapStruct
- JUnit + Mockito
---
## 🚀 Pasos para ejecutar el proyecto
### 1. Clonar el repositorio
 
bash git clone <URL_DEL_REPOSITORIO> cd logistica
### 2. Levantar la base de datos con Docker
Desde la raíz del proyecto:
 
bash docker-compose up -d
### 3. Ejecutar la aplicación
 
bash mvn spring-boot:run
## 📍 Acceso a la aplicación
La aplicación estará disponible en:
 
text http://localhost:8080
### 4. Verificar que funciona
 
bash curl http://localhost:8080/api/v1/vehicles
✅ Respuesta esperada si no hay datos:
 
json []
---
## 🌐 REST API
### 📌 Base URL
 
text http://localhost:8080/api/v1
---
## 🚛 Vehicles
| Método | Endpoint | Descripción | Status |
|--------|----------|-------------|--------|
| GET | `/vehicles` | Listar todos | `200 / 204` |
| GET | `/vehicles/{plate}` | Buscar por matrícula | `200 / 404` |
| POST | `/vehicles` | Crear vehículo | `201 / 400` |
| PUT | `/vehicles/{plate}` | Actualizar vehículo | `200 / 404` |
| DELETE | `/vehicles/{plate}` | Eliminar vehículo | `204 / 404` |
---
## 🛣 Routes
| Método | Endpoint | Descripción | Status |
|--------|----------|-------------|--------|
| GET | `/routes` | Listar todas | `200 / 204` |
| GET | `/routes/{id}` | Buscar por ID | `200 / 404` |
| POST | `/routes` | Crear ruta | `201 / 400` |
| PUT | `/routes/{id}` | Actualizar ruta | `200 / 404` |
| DELETE | `/routes/{id}` | Eliminar ruta | `204 / 404` |
---
## 🔗 Assignments
| Método | Endpoint | Descripción | Status |
|--------|----------|-------------|--------|
| PUT | `/routes/{id}/vehicle?licensePlate={plate}` | Asignar vehículo | `200 / 404` |
| GET | `/routes/{id}/vehicle` | Ver vehículo asignado | `200 / 404` |
| DELETE | `/routes/{id}/vehicle` | Eliminar asignación | `204 / 404` |
| GET | `/vehicles/{plate}/routes` | Rutas de un vehículo | `200 / 204` |
---
## 👤 Clients
| Método | Endpoint | Descripción | Status |
|--------|----------|-------------|--------|
| GET | `/clients` | Listar todos los clientes | `200 / 204` |
| GET | `/clients?email={email}` | Buscar cliente por email | `200 / 404` |
| GET | `/clients/{id}` | Buscar cliente por ID | `200 / 404` |
| POST | `/clients` | Crear cliente | `201 / 400` |
| PUT | `/clients/{id}` | Actualizar cliente | `200 / 404` |
| DELETE | `/clients/{id}` | Eliminar cliente | `204 / 404` |
---
## 🧰 Logistic Services
| Método | Endpoint | Descripción | Status |
|--------|----------|-------------|--------|
| GET | `/services` | Listar todos los servicios logísticos | `200 / 204` |
| GET | `/services?type={type}` | Filtrar servicios por tipo | `200 / 204` |
| GET | `/services/{id}` | Buscar servicio por ID | `200 / 404` |
| POST | `/services` | Crear servicio logístico | `201 / 400` |
| PUT | `/services/{id}` | Actualizar servicio logístico | `200 / 404` |
| DELETE | `/services/{id}` | Eliminar servicio logístico | `204 / 404` |
---
## 📦 Orders
| Método | Endpoint | Descripción | Status |
|--------|----------|-------------|--------|
| POST | `/orders` | Crear pedido | `201 / 400` |
| GET | `/orders/{id}` | Buscar pedido por ID | `200 / 404` |
| PUT | `/orders/{id}/status` | Actualizar estado del pedido | `200 / 404` |
| DELETE | `/orders/{id}` | Cancelar pedido | `204 / 404` |
| GET | `/orders?type={type}` | Buscar pedidos por tipo de servicio | `200 / 204` |
| GET | `/orders?from={date}&to={date}` | Buscar pedidos por rango de fechas | `200 / 204` |
| GET | `/orders/summary` | Obtener resumen de pedidos | `200` |
| GET | `/clients/{id}/orders` | Listar pedidos de un cliente | `200 / 204` |
| GET | `/clients/{id}/orders/total-spent` | Obtener total gastado por cliente | `200` |
---
## 📊 Códigos de estado
| Código | Significado |
|--------|-------------|
| `200 OK` | Operación exitosa con contenido |
| `201 Created` | Recurso creado correctamente |
| `204 No Content` | Operación exitosa sin contenido |
| `400 Bad Request` | Datos inválidos |
| `404 Not Found` | Recurso no encontrado |
| `500 Internal Server Error` | Error interno |
---
## 📝 Ejemplos de uso
### 1. Crear un vehículo
 
bash curl -X POST http://localhost:8080/api/v1/vehicles
-H "Content-Type: application/json" 
-d '{ "licensePlate": "ABC123", "brand": "Toyota", "model": "Camry" }'
✅ Respuesta:
 
json { "id": 1, "licensePlate": "ABC123", "brand": "Toyota", "model": "Camry" }
### 2. Listar vehículos
 
bash curl http://localhost:8080/api/v1/vehicles
### 3. Buscar vehículo por matrícula
 
bash curl http://localhost:8080/api/v1/vehicles/ABC123
### 4. Actualizar vehículo
 
bash curl -X PUT http://localhost:8080/api/v1/vehicles/ABC123
-H "Content-Type: application/json" 
-d '{ "licensePlate": "ABC123", "brand": "Toyota", "model": "Camry XLE" }'
### 5. Eliminar vehículo
 
bash curl -X DELETE http://localhost:8080/api/v1/vehicles/ABC123
✅ Respuesta esperada:
 
text 204 No Content
---
### 6. Crear ruta
 
bash curl -X POST http://localhost:8080/api/v1/routes
-H "Content-Type: application/json" 
-d '{ "origin": "Madrid", "destination": "Barcelona", "distance": 628.5, "duration": 360, "shipmentDate": "2026-03-10", "status": "PENDING", "vehicleAssigned": null }'
### 7. Asignar vehículo a ruta
 
bash curl -X PUT "http://localhost:8080/api/v1/routes/1/vehicle?licensePlate=ABC123"
### 8. Ver vehículo asignado a una ruta
 
bash curl http://localhost:8080/api/v1/routes/1/vehicle
### 9. Listar rutas de un vehículo
 
bash curl http://localhost:8080/api/v1/vehicles/ABC123/routes
### 10. Eliminar asignación de vehículo
 
bash curl -X DELETE http://localhost:8080/api/v1/routes/1/vehicle
✅ Respuesta esperada:
 
text 204 No Content
---
### 11. Crear cliente
 
bash curl -X POST http://localhost:8080/api/v1/clients
-H "Content-Type: application/json" 
-d '{ "firstName": "María", "lastName": "García", "email": "maria@example.com", "phone": "600123123", "address": "Calle Mayor 10, Madrid" }'
### 12. Listar clientes
 
bash curl http://localhost:8080/api/v1/clients
### 13. Buscar cliente por email
 
bash curl "http://localhost:8080/api/v1/clients?email=maria@example.com"
### 14. Buscar cliente por ID
 
bash curl http://localhost:8080/api/v1/clients/1
### 15. Actualizar cliente
 
bash curl -X PUT http://localhost:8080/api/v1/clients/1
-H "Content-Type: application/json" 
-d '{ "firstName": "María", "lastName": "García", "email": "maria@example.com", "phone": "600999888", "address": "Avenida de Europa 25, Madrid" }'
### 16. Eliminar cliente
 
bash curl -X DELETE http://localhost:8080/api/v1/clients/1
✅ Respuesta esperada:
 
text 204 No Content
---
### 17. Crear servicio logístico
 
bash curl -X POST http://localhost:8080/api/v1/services
-H "Content-Type: application/json" 
-d '{ "name": "Envío Express", "type": "EXPRESS", "pricePerKm": 0.75, "description": "Entrega rápida en 24 horas", "available": true }'
### 18. Listar servicios logísticos
 
bash curl http://localhost:8080/api/v1/services
### 19. Filtrar servicios por tipo
 
bash curl "http://localhost:8080/api/v1/services?type=EXPRESS"
### 20. Buscar servicio por ID
 
bash curl http://localhost:8080/api/v1/services/1
### 21. Actualizar servicio logístico
 
bash curl -X PUT http://localhost:8080/api/v1/services/1
-H "Content-Type: application/json" 
-d '{ "name": "Envío Express Premium", "type": "EXPRESS", "pricePerKm": 0.95, "description": "Entrega prioritaria en menos de 24 horas", "available": true }'
### 22. Eliminar servicio logístico
 
bash curl -X DELETE http://localhost:8080/api/v1/services/1
✅ Respuesta esperada:
 
text 204 No Content
---
### 23. Crear pedido
 
bash curl -X POST http://localhost:8080/api/v1/orders
-H "Content-Type: application/json" 
-d '{ "clientId": 1, "serviceId": 1, "originCity": "Madrid", "destinationCity": "Sevilla", "distanceKm": 530.0, "notes": "Entrega urgente" }'
### 24. Buscar pedido por ID
 
bash curl http://localhost:8080/api/v1/orders/1
### 25. Actualizar estado de un pedido
 
bash curl -X PUT http://localhost:8080/api/v1/orders/1/status
-H "Content-Type: application/json" 
-d '{ "status": "IN_TRANSIT" }'
### 26. Cancelar pedido
 
bash curl -X DELETE http://localhost:8080/api/v1/orders/1
✅ Respuesta esperada:
 
text 204 No Content
### 27. Listar pedidos de un cliente
 
bash curl http://localhost:8080/api/v1/clients/1/orders
### 28. Obtener total gastado por un cliente
 
bash curl http://localhost:8080/api/v1/clients/1/orders/total-spent
### 29. Filtrar pedidos por tipo de servicio
 
bash curl "http://localhost:8080/api/v1/orders?type=EXPRESS"
### 30. Filtrar pedidos por rango de fechas
 
bash curl "http://localhost:8080/api/v1/orders?from=2026-03-01&to=2026-03-31"
### 31. Obtener resumen de pedidos
 
bash curl http://localhost:8080/api/v1/orders/summary
