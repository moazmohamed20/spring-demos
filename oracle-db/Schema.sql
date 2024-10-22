create table item (
   id         number(19,0) not null,
   content    clob,
   created_at timestamp,
   name       varchar2(255 char),
   price      double precision not null,
   quantity   number(10,0) not null,
   status     varchar2(255 char),
   primary key ( id )
);