drop table if exists public.databasechangelog;
drop table if exists public.databasechangeloglock;

drop schema if exists flight_finder cascade;
create schema if not exists flight_finder;