alter table person_info
    drop constraint if exists person_info_pkey;
alter table person_info
    drop constraint if exists person_info_user_id_key;
alter table person_info
    drop column if exists id;
alter table person_info
    add primary key (user_id);

alter table organization_info
    drop constraint if exists organization_info_pkey;
alter table organization_info
    drop constraint if exists organization_info_user_id_key;
alter table organization_info
    drop column if exists id;
alter table organization_info
    add primary key (user_id);
