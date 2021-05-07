-- C:/Users/g.gehrer/Google Drive/Schule/Java/IntelliJ/MusicStore/createdb.sql
drop database musicstoredb;
create database musicstoredb;
use musicstoredb;

create table Genre (
    id int not null primary key,
    Name varchar(50) not null
);

create table Artist (
    id int not null primary key,
    Name varchar(256) not null
);

create table Album (
    id int not null primary key,
    ArtistId int not null,
    Title varchar(256) not null,
    FOREIGN KEY (ArtistId) REFERENCES Artist(id)
);

create table Track (
    id int not null primary key,
    GenreId int not null,
    AlbumId int not null,
    Name varchar(256) not null,
    FOREIGN KEY (GenreId) REFERENCES Genre(id),
    FOREIGN KEY (AlbumId) REFERENCES Album(id)
);

