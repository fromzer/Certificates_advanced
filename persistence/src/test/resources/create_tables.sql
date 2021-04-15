create table gift_certificate
(
    id               bigint auto_increment primary key,
    name             varchar(50)                           not null,
    description      varchar(500)                          not null,
    price            decimal(10, 2)                        not null,
    duration         int                                   not null,
    create_date      timestamp default (CURRENT_TIMESTAMP) not null,
    last_update_date timestamp default (CURRENT_TIMESTAMP) not null
);

create table tag
(
    id   bigint auto_increment primary key,
    name varchar(50) not null
);

create table gift_certificate_tag
(
    gift_certificate_id bigint not null,
    tag_id              bigint not null,
    primary key (tag_id, gift_certificate_id),
    constraint gift_certificate_tag_gift_certificate_id_fk
        foreign key (gift_certificate_id) references gift_certificate (id)
            on delete cascade,
    constraint gift_certificate_tag_tag_id_fk
        foreign key (tag_id) references tag (id)
            on delete cascade
);