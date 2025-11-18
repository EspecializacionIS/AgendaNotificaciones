-- Extensiones útiles
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Tipos
CREATE TYPE genero AS ENUM ('MASCULINO','FEMENINO','OTRO','NO_INFORMA');
CREATE TYPE agenda_estado AS ENUM ('PROGRAMADA','ATENDIDA','CANCELADA');

-- Tabla Medicos
CREATE TABLE medicos (
  id UUID PRIMARY KEY,
  nombre_completo VARCHAR(160) NOT NULL,
  especialidad VARCHAR(120),
  email VARCHAR(160) UNIQUE NOT NULL,
  telefono VARCHAR(40),
  jornada_inicio TIME NOT NULL,      -- p.ej. 08:00
  jornada_fin TIME NOT NULL,         -- p.ej. 18:00
  activo BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Tabla Pacientes
CREATE TABLE pacientes (
  id UUID PRIMARY KEY,
  numero_identificacion VARCHAR(40) UNIQUE NOT NULL,
  nombre_completo VARCHAR(160) NOT NULL,
  fecha_nacimiento DATE NOT NULL,
  genero genero NOT NULL,
  direccion VARCHAR(200),
  telefono VARCHAR(40),
  email VARCHAR(160),
  activo BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Tabla Agenda (Citas)
CREATE TABLE agenda (
  id UUID PRIMARY KEY,
  medico_id UUID NOT NULL REFERENCES medicos(id),
  paciente_id UUID NOT NULL REFERENCES pacientes(id),
  inicio TIMESTAMPTZ NOT NULL,
  fin TIMESTAMPTZ NOT NULL,
  estado agenda_estado NOT NULL DEFAULT 'PROGRAMADA',
  motivo VARCHAR(200),
  notas TEXT,
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  CONSTRAINT ck_agenda_rango CHECK (fin > inicio)
);

-- Índices de consulta
CREATE INDEX idx_agenda_medico_fecha ON agenda (medico_id, inicio, fin);
CREATE INDEX idx_agenda_paciente_fecha ON agenda (paciente_id, inicio, fin);