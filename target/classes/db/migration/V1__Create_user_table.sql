create table user (
id bigint auto_increment primary key,
account_id varchar (100),
name varchar (50),
token char (50),
avatar_url varchar (100),
bio varchar (100),
gmt_create bigint,
gmt_modified bigint
);