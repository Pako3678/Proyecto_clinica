# Panel Médico — API REST + MySQL + Frontend Bootstrap

Migración del proyecto Java Swing original (`PanelMedico.java`) a una arquitectura web moderna con gestión integral de pacientes (CRUD):

- **Backend headless**: Spring Boot 3 + Spring Data JPA (`/src/main/java`)
- **Base de datos**: MySQL (persistencia del modelo `Persona/Medico/Paciente/HistoriaClinica/EvolucionMedica/...`)
- **Frontend**: HTML5 + JS + CSS con Bootstrap 5, diseño médico moderno ('Plus Jakarta Sans') y consumo de API vía `fetch`.

## 1. Base de datos

Instalar MySQL y crear la base (o dejar que la cree sola según `application.properties`):

```sql
CREATE DATABASE clinica_db;
```

Editar usuario/contraseña en `src/main/resources/application.properties` si difieren de `root/root`.

Al arrancar, Hibernate crea/actualiza las tablas automáticamente (`ddl-auto=update`) y `data.sql` carga pacientes de ejemplo.

## 2. Backend

Requiere JDK 17+ y Maven (o ejecutar directamente con `run.bat` en Windows).

```bash
# Opción 1: Ejecutar script automático (compila y levanta la app)
run.bat

# Opción 2: Con Maven
mvn spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

### Endpoints de Pacientes

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/api/pacientes` | Lista todos los pacientes (soporta filtro opcional `?q=...`) |
| `GET` | `/api/pacientes/{id}` | Obtiene el detalle de un paciente por su ID |
| `GET` | `/api/pacientes/buscar/{dni}` | Consulta la ficha médica e historia clínica del paciente por DNI |
| `POST` | `/api/pacientes` | Da de alta un nuevo paciente (crea su historia clínica inicial) |
| `PUT` | `/api/pacientes/{id}` | Modifica los datos personales y de cobertura del paciente |
| `DELETE` | `/api/pacientes/{id}` | Elimina al paciente y su historia clínica asociada |

#### Ejemplo de cuerpo (Body) para `POST` y `PUT`:

```json
{
  "nombre": "Carlos",
  "apellido": "Gomez",
  "dni": 38999888,
  "telefono": 2619876543,
  "obraSocial": "OSDE",
  "numeroAfiliado": 123456,
  "alergias": "Polen / Rinitis estacional"
}
```

## 3. Frontend

Es una aplicación web moderna sin necesidad de NodeJS ni bundles:

- Se puede abrir directamente el archivo `frontend/index.html` en cualquier navegador.
- O bien acceder a `http://localhost:8080/index.html` cuando Spring Boot está corriendo.

### Funcionalidades del Frontend:

1. **Directorio de Pacientes**:
   - Tabla interactiva con avatares, nombres, DNI, teléfono, obra social y badges de alerta para alergias.
   - Buscador en tiempo real por texto o DNI.
   - Botón `+ Nuevo Paciente` que abre el modal de registro con validaciones.
   - Acciones por fila: ver ficha completa, editar datos y eliminar paciente.
2. **Búsqueda & Ficha Médica**:
   - Consulta rápida por número de DNI.
   - Muestra ficha completa con ID de reporte, datos del paciente, alerta destacada de alergias, medicamentos recetados, tratamientos y médicos visitados.
   - Botones para editar o eliminar directamente desde la propia ficha médica.
   - Botón para imprimir o exportar la ficha médica.
3. **Modales y Notificaciones**:
   - Modal reactivo para crear y modificar pacientes con manejo de errores del servidor.
   - Modal de confirmación para borrado seguro.
   - Notificaciones Toast flotantes para confirmar acciones exitosas.
