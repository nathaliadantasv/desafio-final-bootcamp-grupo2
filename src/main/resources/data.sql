drop table if exists address;
drop table if exists advertising;
drop table if exists batch;
drop table if exists hibernate_sequence;
drop table if exists inbound_order;
drop table if exists product;
drop table if exists product_type;
drop table if exists representative;
drop table if exists section;
drop table if exists seller;
drop table if exists warehouse;

-- CREATE TABLES ZONE

create table address (
                         id bigint not null auto_increment,
                         city varchar(255),
                         country varchar(255),
                         postal_code integer not null,
                         state varchar(255),
                         street varchar(255),
                         primary key (id)
) engine=InnoDB;

create table warehouse (
                           id bigint not null auto_increment,
                           name varchar(255),
                           address_id bigint,
                           primary key (id)
) engine=InnoDB;

alter table warehouse add constraint fk_address_warehouse foreign key (address_id) references address (id);

create table seller (
                        id bigint not null,
                        email varchar(255),
                        name varchar(255),
                        password varchar(255),
                        primary key (id)
) engine=InnoDB;

create table product_type (
                              id bigint not null auto_increment,
                              type varchar(255),
                              primary key (id)
) engine=InnoDB;

create table product (
                         id bigint not null auto_increment,
                         name varchar(255),
                         volume double precision not null,
                         product_type_id bigint,
                         primary key (id)
) engine=InnoDB;

alter table product add constraint fk_product_type_product  foreign key (product_type_id) references product_type (id);

create table advertising (
                             id bigint not null auto_increment,
                             description varchar(255),
                             price decimal(19,2),
                             product_id bigint,
                             seller_id bigint,
                             primary key (id)
) engine=InnoDB;

alter table advertising add constraint fk_product_advertising foreign key (product_id) references product (id);
alter table advertising add constraint fk_seller_advertising foreign key (seller_id) references seller (id);

create table representative (
                                id bigint not null,
                                email varchar(255),
                                name varchar(255),
                                password varchar(255),
                                primary key (id)
) engine=InnoDB;

create table section (
                         id bigint not null auto_increment,
                         description varchar(255),
                         name varchar(255),
                         temperature double precision not null,
                         volume double precision not null,
                         product_type_id bigint,
                         representative_id bigint,
                         warehouse_id bigint,
                         primary key (id)
) engine=InnoDB;

alter table section add constraint fk_product_type_section foreign key (product_type_id) references product_type (id);
alter table section add constraint fk_representative_section foreign key (representative_id) references representative (id);
alter table section add constraint fk_warehouse_section foreign key (warehouse_id) references warehouse (id);

create table inbound_order (
                               id bigint not null auto_increment,
                               creation_date date,
                               section_id bigint,
                               primary key (id)
) engine=InnoDB;

create table batch (
                       id bigint not null auto_increment,
                       current_quantity integer not null,
                       current_temperature double precision not null,
                       expiration_date date,
                       initial_quantity integer not null,
                       manufacturing_date date,
                       manufacturing_time time,
                       minimum_temperature double precision not null,
                       advertising_id bigint,
                       inbound_order_id bigint,
                       primary key (id)
) engine=InnoDB;

alter table batch add constraint fk_advertising_batch foreign key (advertising_id) references advertising (id);
alter table batch add constraint fk_inbound_order_batch foreign key (inbound_order_id) references inbound_order (id);

-- INSERT ZONE

INSERT INTO
    product_type (type)
VALUES
    ("FRESH"), ("COLD"), ("FREEZE");


INSERT INTO
    product (name, volume, product_type_id)
VALUES
    ("Banana Prata", 1, 1), ("Banana Nanica", 1, 1), ("Melância", 3, 1),
    ("Maçã", 1, 1), ("Peça de Carne", 2, 3), ("Linguiça", 1, 3),
    ("Sorvete de Chocolate", 1, 3), ("Queijo Mussarela", 1, 2), ("Salame", 1, 2),
    ("Queijo Mineiro", 1, 2);


INSERT INTO
    seller
VALUES
    (1, "Fulano","fulano@email.com","Fulano123"),
    (2, "Ciclano","ciclano@email.com","Ciclano123"),
    (3, "Aderson","aderson@email.com","aderson123"),
    (4, "Matheus","matheus@email.com","Matheus123");


INSERT INTO
    advertising (price, description, product_id, seller_id)
VALUES
    (20.0, "Melancia gostosinha nham nham", 3, 4),
    (25.0, "Sorvetinho gostosinho nham nham", 7, 3),
    (5.0, "Queijinho gostosinho nham nham", 8, 2),
    (10.0, "Salaminho gostosinho nham nham", 9, 2),
    (2.50, "Maçazinha gostosinha nham nham", 4, 3);


INSERT INTO
    address (street, city, state, country, postal_code)
VALUES
    ("Av. das Nações Unidas", "Osasco", "SP", "Brasil", 06233200),
    ("Rod. José Carlos Daux", "Florianópolis", "SC", "Brasil", 88032005);


INSERT INTO
    warehouse (name, address_id)
VALUES
    ("Armazem Melicidade", 1),
    ("Armazem Floripa", 2);

INSERT INTO
    representative
VALUES
    (1, "Nathalia","nathalia@email.com","nat123"),
    (2, "Luan Albert","l.albert@email.com","albertinho123"),
    (3, "Rodrigo","rodrigo@email.com","rodrigao123"),
    (4, "Gabriel","gabrielemail.com","Gabriel123");

INSERT INTO
    section (description, name, temperature, volume, product_type_id, representative_id, warehouse_id)
values
    ("Setor de Produtos Fresco do Tipo Fruta", "SETOR01-SP", 15.0, 500, 1, 1, 1),
    ("Setor de Produtos Fresco", "SETOR02-SP", 18.0, 500, 1, 1, 1),
    ("Setor de Produtos Frios", "SETOR03-SP", 10.0, 500, 2, 3, 1),
    ("Setor de Produtos Congelados", "SETOR04-SP", 15.0, 500, 3, 3, 1),
    ("Setor de Produtos Fresco do Tipo Fruta", "SETOR01-SC", 15.0, 500, 1, 2, 2),
    ("Setor de Produtos Fresco", "SETOR02-SC", 18.0, 500, 1, 2, 2);

INSERT INTO
    buyer
VALUE (1, "kenyo@meli.com", "kenyo", "java2020");


INSERT INTO purchase_status VALUE (1, "APPROVED");
INSERT INTO purchase_status VALUE (2, "PENDING");
INSERT INTO purchase_status VALUE (3, "REJECTED");
INSERT INTO purchase_status VALUE (4, "DELIVERED");