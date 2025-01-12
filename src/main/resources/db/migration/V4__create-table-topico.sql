create table topico(

    id bigint not null auto_increment,
    titulo varchar(100) not null,
    mensaje text not null,
    fecha_creacion date default(current_date),
    status boolean default(1),
    usuario_id bigint null,
    curso_id bigint null,

    primary key(id),
    constraint fk_topico_usuario_id foreign key(usuario_id) references usuario(id),
    constraint fk_topico_curso_id foreign key(curso_id) references curso(id)
)