ALTER TABLE usuario
    ADD CONSTRAINT unique_correo_electronico UNIQUE (correo_electronico);
