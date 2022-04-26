create database if not exists performance_system;


use performance_system;


create table if not exists host
(
    host_id   int auto_increment,
    host_name varchar(32) not null,
    constraint hosts_pk
        primary key (host_id)
);
create unique index host_name_uindex
    on host (host_name);


create table if not exists benchmark
(
    benchmark_id   int auto_increment,
    benchmark_name varchar(64) not null,
    constraint benchmark_pk
        primary key (benchmark_id)
);
create unique index benchmark_benchmark_name_uindex
    on benchmark (benchmark_name);


create table if not exists payload
(
    payload_id        int auto_increment,
    payload_name      varchar(512) not null,
    payload_parent_id int          not null,
    constraint payload_pk
        primary key (payload_id),
    constraint payload_benchmark_benchmark_id_fk
        foreign key (payload_parent_id) references benchmark (benchmark_id)
            on update cascade on delete cascade
);


create table if not exists metric_type
(
    metric_type_id   int auto_increment,
    metric_type_name varchar(512) not null,
    constraint metric_type_pk
        primary key (metric_type_id)
);
create unique index metric_type_name_uindex
    on metric_type (metric_type_name);


create table if not exists jdk
(
    jdk_id      int auto_increment,
    jdk_name    varchar(32) not null,
    jdk_version int         not null,
    constraint jdk_pk
        primary key (jdk_id)
);

create table if not exists report
(
    report_id             int auto_increment,
    report_host_id        int      not null,
    report_payload_id     int      not null,
    report_metric_type_id int      not null,
    report_jdk_id         int      not null,
    report_value          double   not null,
    report_date           datetime not null,
    constraint report_pk
        primary key (report_id),
    constraint report_host_host_id_fk
        foreign key (report_host_id) references host (host_id)
            on update cascade on delete cascade,
    constraint report_jdk_jdk_id_fk
        foreign key (report_jdk_id) references jdk (jdk_id)
            on update cascade on delete cascade,
    constraint report_metric_type_metric_type_id_fk
        foreign key (report_metric_type_id) references metric_type (metric_type_id)
            on update cascade on delete cascade,
    constraint report_payload_payload_id_fk
        foreign key (report_payload_id) references payload (payload_id)
            on update cascade on delete cascade
);
