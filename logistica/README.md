🚚 Logistic Microservice

Microservicio de logística para la gestión de envíos (Rutas) y flotas (Vehículos).

🚀 Pasos para Ejecutar el Proyecto
1️⃣ Clonar el repositorio
git clone <URL_DEL_REPOSITORIO>
cd Logistic
2️⃣ Levantar la base de datos con Docker

Desde la raíz del proyecto:

docker-compose up -d
3️⃣ Ejecutar la aplicación
mvn spring-boot:run

📍 La aplicación estará disponible en:

http://localhost:8080
4️⃣ Verificar que funciona
curl http://localhost:8080/api/v1/vehicles

✅ Respuesta esperada (si no hay datos):

[]
🌐 REST API
📌 Base URL
http://localhost:8080/api/v1
🚛 Vehículos
Método	Ruta	Descripción	Status
GET	/vehicles	Listar todos	200 / 204
GET	/vehicles/{plate}	Buscar por matrícula	200 / 404
POST	/vehicles	Crear vehículo	201 / 400
PUT	/vehicles/{plate}	Actualizar vehículo	200 / 404
DELETE	/vehicles/{plate}	Eliminar vehículo	204 / 404
🛣 Rutas
Método	Ruta	Descripción	Status
GET	/routes	Listar todas	200 / 204
GET	/routes/{id}	Buscar por ID	200 / 404
POST	/routes	Crear ruta	201 / 400
PUT	/routes/{id}	Actualizar ruta	200 / 404
DELETE	/routes/{id}	Eliminar ruta	204 / 404
🔗 Asignaciones
Método	Ruta	Descripción	Status
PUT	/routes/{id}/vehicle?licensePlate={plate}	Asignar vehículo	200 / 404
GET	/routes/{id}/vehicle	Ver vehículo asignado	200 / 404
DELETE	/routes/{id}/vehicle	Eliminar asignación	204 / 404
GET	/vehicles/{plate}/routes	Rutas de un vehículo	200 / 204
📊 Códigos de Estado

200 OK → Operación exitosa con contenido

201 Created → Recurso creado correctamente

204 No Content → Operación exitosa sin contenido

400 Bad Request → Datos inválidos

404 Not Found → Recurso no encontrado

500 Internal Server Error → Error interno

📝 Ejemplos de Uso
🚛 1. Crear un Vehículo
curl -X POST http://localhost:8080/api/v1/vehicles \
  -H "Content-Type: application/json" \
  -d '{
    "licensePlate": "ABC123",
    "brand": "Toyota",
    "model": "Camry"
  }'
✅ Respuesta (201 Created)
{
  "id": 1,
  "licensePlate": "ABC123",
  "brand": "Toyota",
  "model": "Camry"
}
🚛 2. Listar Vehículos
curl http://localhost:8080/api/v1/vehicles
🔎 3. Buscar Vehículo por Matrícula
curl http://localhost:8080/api/v1/vehicles/ABC123
🔄 4. Actualizar Vehículo
curl -X PUT http://localhost:8080/api/v1/vehicles/ABC123 \
  -H "Content-Type: application/json" \
  -d '{
    "licensePlate": "ABC123",
    "brand": "Toyota",
    "model": "Camry XLE"
  }'
❌ 5. Eliminar Vehículo
curl -X DELETE http://localhost:8080/api/v1/vehicles/ABC123

✅ Respuesta: 204 No Content

🛣 6. Crear Ruta
curl -X POST http://localhost:8080/api/v1/routes \
  -H "Content-Type: application/json" \
  -d '{
    "origin": "Madrid",
    "destination": "Barcelona",
    "distance": 628.5,
    "duration": 360,
    "shipmentDate": "2026-03-10",
    "status": "PENDING",
    "vehicleAssigned": null
  }'
🔗 7. Asignar Vehículo a Ruta
curl -X PUT "http://localhost:8080/api/v1/routes/1/vehicle?licensePlate=ABC123"
👀 8. Ver Vehículo Asignado
curl http://localhost:8080/api/v1/routes/1/vehicle
🚚 9. Listar Rutas de un Vehículo
curl http://localhost:8080/api/v1/vehicles/ABC123/routes
❌ 10. Eliminar Asignación
curl -X DELETE http://localhost:8080/api/v1/routes/1/vehicle

✅ Respuesta: 204 No Content