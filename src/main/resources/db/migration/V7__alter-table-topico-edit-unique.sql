ALTER TABLE topico
    ADD CONSTRAINT unique_titulo_mensaje UNIQUE (titulo, mensaje);
