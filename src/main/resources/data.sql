-- Datos de ejemplo. Se ejecutan en cada arranque (spring.sql.init.mode=always).
-- Recomendación: una vez cargados, cambiar esa propiedad a "never" para no reintentar
-- inserts en cada reinicio (medicos/pacientes usan INSERT IGNORE por el DNI único,
-- pero medicamentos/tratamientos no tienen constraint único y podrían duplicarse).

INSERT IGNORE INTO medicos (id, nombre, apellido, dni, telefono, matricula, especialidad)
VALUES (1, 'Laura', 'Gimenez', 30111222, 2610000001, 45678, 'Cardiologia');

INSERT IGNORE INTO pacientes (id, nombre, apellido, dni, telefono, obra_social, numero_afiliado, alergias)
VALUES (1, 'Joaquin', 'Saharrea', 40123456, 2610000002, 'OSEP', 987654, 'Resfrio / Rinitis alergica');

INSERT IGNORE INTO historias_clinicas (id, paciente_id) VALUES (1, 1);

INSERT IGNORE INTO tratamientos (id, nombre, descripcion)
VALUES (1, 'Cardiologia', 'Control cardiologico de rutina con electrocardiograma');

INSERT IGNORE INTO medicamentos (id, nombre_comercial, droga_farma, presentacion)
VALUES (1, 'Ibupirac', 'Ibuprofeno', 'Comprimidos 400mg');

INSERT IGNORE INTO evoluciones_medicas (id, fecha, medico_id, diagnostico, observaciones, historia_clinica_id)
VALUES (1, '2026-06-15 10:30:00', 1, 'Cuadro gripal leve', 'Control en 7 dias si no mejora', 1);

INSERT IGNORE INTO evolucion_tratamientos (evolucion_id, tratamiento_id) VALUES (1, 1);
INSERT IGNORE INTO evolucion_medicamentos (evolucion_id, medicamento_id) VALUES (1, 1);

INSERT IGNORE INTO medicos (id, nombre, apellido, dni, telefono, matricula, especialidad)
VALUES (2, 'Mariana', 'Lopez', 30222333, 2610000003, 56789, 'Dermatologia');

INSERT IGNORE INTO medicos (id, nombre, apellido, dni, telefono, matricula, especialidad)
VALUES (3, 'Federico', 'Ramirez', 30333444, 2610000004, 67890, 'Clinica medica');

INSERT IGNORE INTO pacientes (id, nombre, apellido, dni, telefono, obra_social, numero_afiliado, alergias)
VALUES (2, 'Ciro', 'Spinelli', 41234567, 2610000005, 'Swiss Medical', 456789, 'Alergia al polvo');

INSERT IGNORE INTO pacientes (id, nombre, apellido, dni, telefono, obra_social, numero_afiliado, alergias)
VALUES (3, 'Enzo', 'Oviedo', 42345678, 2610000006, 'OSDE', 567890, 'Sin alergias conocidas');

INSERT IGNORE INTO historias_clinicas (id, paciente_id) VALUES (2, 2);
INSERT IGNORE INTO historias_clinicas (id, paciente_id) VALUES (3, 3);

INSERT IGNORE INTO tratamientos (id, nombre, descripcion)
VALUES (2, 'Control dermatologico', 'Evaluacion de lunares y cuidado preventivo de la piel');

INSERT IGNORE INTO tratamientos (id, nombre, descripcion)
VALUES (3, 'Control de rutina', 'Chequeo general y seguimiento de la presion arterial');

INSERT IGNORE INTO medicamentos (id, nombre_comercial, droga_farma, presentacion)
VALUES (2, 'Cetirizina', 'Cetirizina', 'Comprimidos 10mg');

INSERT IGNORE INTO medicamentos (id, nombre_comercial, droga_farma, presentacion)
VALUES (3, 'Paracetamol', 'Paracetamol', 'Comprimidos 500mg');

INSERT IGNORE INTO evoluciones_medicas
    (id, fecha, medico_id, diagnostico, observaciones, historia_clinica_id)
VALUES
    (2, '2026-07-10 09:15:00', 2, 'Dermatitis leve',
     'Aplicar crema hidratante y realizar control en 30 dias', 2);

INSERT IGNORE INTO evoluciones_medicas
    (id, fecha, medico_id, diagnostico, observaciones, historia_clinica_id)
VALUES
    (3, '2026-08-02 16:45:00', 3, 'Presion arterial normal',
     'Continuar con actividad fisica y control anual', 3);

INSERT IGNORE INTO evolucion_tratamientos (evolucion_id, tratamiento_id) VALUES (2, 2);
INSERT IGNORE INTO evolucion_medicamentos (evolucion_id, medicamento_id) VALUES (2, 2);
INSERT IGNORE INTO evolucion_tratamientos (evolucion_id, tratamiento_id) VALUES (3, 3);
INSERT IGNORE INTO evolucion_medicamentos (evolucion_id, medicamento_id) VALUES (3, 3);
