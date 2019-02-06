create type bar_typ as object (
  fisrt_name  VARCHAR(20),
  last_name   VARCHAR(20)
);
/

create type bars_typ as VARRAY(30) of bar_typ;
/

create table foo (
  "ID" number(10) not null,
  "BAR" bar_typ,
  "BARS" bars_typ
);
