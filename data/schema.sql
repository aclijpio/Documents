create sequence document_seq increment by 50;

-- Создание таблицы "counterparties"
create table if not exists counterparties (
                                              id bigserial,
                                              name varchar(255),
                                              primary key (id)
);

-- Создание таблицы "currencies"
create table if not exists currencies (
                                          id bigserial,
                                          currencycode varchar(255),
                                          rate double precision not null,
                                          primary key (id),
                                          constraint currencies_currencycode_check check ((currencycode)::text = ANY ((ARRAY['USD'::character varying, 'EUR'::character varying, 'JPY'::character varying, 'GBP'::character varying, 'AUD'::character varying, 'RUB'::character varying])::text[]))
);

-- Создание таблицы "employees"
create table if not exists employees (
                                         id bigserial,
                                         name varchar(255),
                                         primary key (id)
);

-- Создание таблицы "products"
create table if not exists products (
                                        id bigserial,
                                        name varchar(255),
                                        quantity double precision not null,
                                        primary key (id)
);

-- Создание таблицы "users"
create table if not exists users (
                                     id bigserial,
                                     username varchar(255),
                                     primary key (id)
);

-- Создание таблицы "invoice"
create table if not exists invoice (
                                       id bigint not null default nextval('document_seq'::regclass),
                                       number varchar(255) not null,
                                       date date,
                                       user_id bigint not null,
                                       amountofmoney double precision not null,
                                       currency_id bigint not null,
                                       product_id bigint not null,
                                       primary key (id),
                                       unique (number),
                                       constraint fkqcdeo6b8ns0swuqkoxvl536si foreign key (currency_id) references currencies,
                                       constraint fknb8ivibar54xnxakcxmor14e foreign key (product_id) references products,
                                       constraint fk_bk564yqlhii6cxdhxu2u3txvg foreign key (user_id) references users
);

-- Создание таблицы "payment_requests"
create table if not exists payment_requests (
                                                id bigint not null default nextval('document_seq'::regclass),
                                                number varchar(255) not null,
                                                date date,
                                                user_id bigint not null,
                                                amountofmoney double precision not null,
                                                counterparty_id bigint not null,
                                                currency_id bigint not null,
                                                commission double precision not null,
                                                primary key (id),
                                                unique (number),
                                                constraint fkaaeaekcgsnpauf1x56iow72w1 foreign key (counterparty_id) references counterparties,
                                                constraint fkslq44cvomx6vbmnp6g656a3wh foreign key (currency_id) references currencies,
                                                constraint fk_ltb6ogrxpg4vhegdsx867p521 foreign key (user_id) references users
);

-- Создание таблицы "payments"
create table if not exists payments (
                                        id bigint not null default nextval('document_seq'::regclass),
                                        number varchar(255) not null,
                                        date date,
                                        user_id bigint not null,
                                        amountofmoney double precision not null,
                                        employee_id bigint not null,
                                        primary key (id),
                                        unique (number),
                                        constraint fkgg9970yjb56tmui83b0dccqv5 foreign key (employee_id) references employees,
                                        constraint fk_hhlmueh5j2c0pjahrb4mrgw7a foreign key (user_id) references users
);
insert into users (username) values ('Maxim'),
                                    ('Oleg'),
                                    ('Egor'),
                                    ('Masha'),
                                    ('Misha');

insert into currencies(currencycode, rate) VALUES ('USD', 50.63),
                                                  ('EUR', 55.34),
                                                  ('JPY', 24.13),
                                                  ('GBP', 7.12),
                                                  ('AUD', 24.19),
                                                  ('RUB', 1);