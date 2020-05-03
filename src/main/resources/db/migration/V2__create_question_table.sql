create table question(
    id bigint auto_increment primary key,
    creator bigint,
    title varchar(100),
    description text,
    tag varchar(256),
    comment_count int,
    view_count int,
    like_count int,
    gmt_modified bigint,
    gmt_create bigint
);