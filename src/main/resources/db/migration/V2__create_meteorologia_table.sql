create table IF NOT EXISTS meteorologia (

    id bigint not null,
    cidade varchar(255) not null,
    data date not null,
    tempo_dia varchar(20) not null,
    tempo_noite varchar(20) not null,
    temperatura_maxima float not null,
    temperatura_minima float not null,
    precipitacao float not null,
    umidade float not null,
    velocidade_ventos float not null,

    primary key (id)
);