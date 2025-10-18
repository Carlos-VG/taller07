-- ===========================================
-- OFICINAS (primero, por FK en DOCENTE)
-- ===========================================
INSERT INTO oficina (nombre, ubicacion) VALUES ('Of-201', 'Bloque A, Piso 2');
INSERT INTO oficina (nombre, ubicacion) VALUES ('Of-305', 'Bloque B, Piso 3');
INSERT INTO oficina (nombre, ubicacion) VALUES ('Of-410', 'Bloque C, Piso 4');

-- ===========================================
-- PERSONAS (base para herencia)
-- ===========================================
INSERT INTO persona (nombre, apellido, correo) VALUES ('Carlos', 'Perez', 'carlos.perez@unicauca.edu.co');
INSERT INTO persona (nombre, apellido, correo) VALUES ('Ana', 'Rojas', 'ana.rojas@unicauca.edu.co');
INSERT INTO persona (nombre, apellido, correo) VALUES ('Luis', 'Martinez', 'luis.martinez@unicauca.edu.co');
INSERT INTO persona (nombre, apellido, correo) VALUES ('Maria', 'Gomez', 'maria.gomez@unicauca.edu.co');

-- ===========================================
-- DOCENTES (hereda de persona)
-- Mantener los mismos IDs que en persona
-- ===========================================
INSERT INTO docente (id, oficina_id) VALUES (1, 1);
INSERT INTO docente (id, oficina_id) VALUES (2, 2);
INSERT INTO docente (id, oficina_id) VALUES (3, 3);

-- ===========================================
-- ADMINISTRATIVO (hereda de persona)
-- ===========================================
-- INSERT INTO administrativo (id, rol) VALUES (4, 'Secretario Academico');

-- ===========================================
-- ASIGNATURAS
-- ===========================================
INSERT INTO asignatura (nombre, codigo) VALUES ('Arquitectura Empresarial', 'ASI-101');
INSERT INTO asignatura (nombre, codigo) VALUES ('Bases de Datos II', 'ASI-102');
INSERT INTO asignatura (nombre, codigo) VALUES ('Ingenieria de Software', 'ASI-103');

-- ===========================================
-- ESPACIOS FISICOS
-- ===========================================
INSERT INTO espacio_fisico (nombre, capacidad, activo) VALUES ('Aula Tulcan 101', 40, true);
INSERT INTO espacio_fisico (nombre, capacidad, activo) VALUES ('Laboratorio Redes 2', 28, true);
INSERT INTO espacio_fisico (nombre, capacidad, activo) VALUES ('Aula Magna', 100, true);
INSERT INTO espacio_fisico (nombre, capacidad, activo) VALUES ('Laboratorio IA', 25, true);

-- ===========================================
-- CURSOS (requiere asignatura)
-- matricula_estimada: nueva columna para validación
-- ===========================================
INSERT INTO curso (nombre, asignatura_id, matricula_estimada) VALUES ('Arquitectura Empresarial - Grupo A', 1, 35);
INSERT INTO curso (nombre, asignatura_id, matricula_estimada) VALUES ('Bases de Datos II - Grupo B', 2, 25);
INSERT INTO curso (nombre, asignatura_id, matricula_estimada) VALUES ('Ingenieria de Software - Grupo C', 3, 30);

-- ===========================================
-- RELACION CURSO–DOCENTE (N:M)
-- ===========================================
INSERT INTO curso_docente (curso_id, docente_id) VALUES (1, 1);
INSERT INTO curso_docente (curso_id, docente_id) VALUES (1, 2);
INSERT INTO curso_docente (curso_id, docente_id) VALUES (2, 2);
INSERT INTO curso_docente (curso_id, docente_id) VALUES (3, 3);

-- ===========================================
-- FRANJAS HORARIAS (requiere curso y espacio)
-- ===========================================
INSERT INTO franja_horaria (dia, hora_inicio, hora_fin, curso_id, espacio_fisico_id) VALUES ('LUNES', '08:00:00', '10:00:00', 1, 1);
INSERT INTO franja_horaria (dia, hora_inicio, hora_fin, curso_id, espacio_fisico_id) VALUES ('MIERCOLES', '10:00:00', '12:00:00', 1, 2);
INSERT INTO franja_horaria (dia, hora_inicio, hora_fin, curso_id, espacio_fisico_id) VALUES ('VIERNES', '14:00:00', '16:00:00', 2, 1);
INSERT INTO franja_horaria (dia, hora_inicio, hora_fin, curso_id, espacio_fisico_id) VALUES ('MARTES', '08:00:00', '10:00:00', 3, 4);

-- ===========================================
-- INDICES PARA OPTIMIZACION DE CONSULTAS
-- ===========================================
CREATE INDEX idx_franja_curso_id ON franja_horaria(curso_id);
CREATE INDEX idx_franja_dia_hora ON franja_horaria(dia, hora_inicio, hora_fin);
CREATE INDEX idx_curso_docente_curso ON curso_docente(curso_id);
CREATE INDEX idx_curso_docente_docente ON curso_docente(docente_id);

-- ===========================================
