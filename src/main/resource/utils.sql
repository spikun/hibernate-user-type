select * from foo;

drop table foo;
drop type bars_typ;
drop type bar_typ;


insert into foo(ID, BAR) values (
  6, bar_typ('Jon','Snow')
);
insert into foo values (
  2, bar_typ('Jon','Snow'), bars_typ(bar_typ('Arya','Stark'), bar_typ('Tyrion','Lannister'))
);