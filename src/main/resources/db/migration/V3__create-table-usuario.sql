create table usuario(

    id bigint not null auto_increment,
    nombre varchar(100) not null,
    correo_electronico varchar(200) not null,
    contrasena varchar(255) not null,
    perfil_id bigint null,

    primary key(id),
    constraint fk_usuario_perfil_id foreign key(perfil_id) references perfil(id)
)