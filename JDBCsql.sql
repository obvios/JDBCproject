/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Daniel Gione 
 * Created: Oct 16, 2018
 */
--inital drop of leftover tables from previous runs
drop table Book;
drop table Publisher;
drop table WritingGroup;



--creating the publisher table
create table Publisher (
PublisherName varchar(30) NOT NULL,
PublisherAddress varchar(70) NOT NULL,
PublisherPhone varchar(19) NOT NULL, 
PublisherEmail varchar(60)NOT NULL); 

--creating the writing group table
create table WritingGroup(
GroupName varchar(50) NOT NULL, 
HeadWriter varchar(30) NOT NULL, 
YearFormed integer NOT NULL, 
Subject varchar(20) NOT NULL);

--creating the book table
create table Book(GroupName varchar(50) NOT NULL, 
PublisherName varchar(30) NOT NULL, 
BookTitle varchar(30) NOT NULL, 
YearPublished integer NOT NULL, 
NumberPages integer NOT NULL);

--adding the primary key to publisher that is the value PublisherName
alter table Publisher
    add constraint publisher_pk 
    PRIMARY KEY (PublisherName);

--adding the primary key to writing group that is the value GroupName
alter table WritingGroup
    add constraint writinggroup_pk
    PRIMARY KEY (GroupName);

--adding the foreign key to book from wriitng group which is GroupName
alter table Book
    add constraint book_writinggroup_fk
    FOREIGN KEY (GroupName)
    references WritingGroup(GroupName);

--adding the foregin key to book from publisher which is  PublisherName
alter table Book
    add constraint book_publisher_fk
    FOREIGN KEY (PublisherName)
    references Publisher(PublisherName);

--adding the primary key to book which is groupName, bookTitle
alter table Book
    add constraint book_pk
    PRIMARY KEY (GroupName, BookTitle);


/** Additions to the tables begin from this point using insert statements*/
--Insertion of all publishers into the publisher table
insert into Publisher (PublisherName, PublisherAddress, PublisherPhone, PublisherEmail) 
values 
('HappierCollins','3222 Lochmere Lane, Hartford, CT 06103','(274) 741-2886','HappierPubs@gmail.com'),
('Pearcin','3313 Whitman Court, Waterbury, CT 06702','(877) 856-8832','PercinReads@yahoo.com'),
('Maximillion','2598 Matthews Street, Sterling, IL 61081','(830) 275-7951','MaxamillionsOfBooks@hotmail.com'),
('Simon & Cluster','1758 Clarence Court, Wilmington, NC 28412','(402) 256-6571','SimonsCluster@gmail.com'),
('Schocrapstic','307 Elliot Avenue, Seattle, WA 98133','(503) 563-0548','SoCrapTastic@aol.com'),
('Cengaging Novellas','3789 Sycamore Fork Road, Hopkins, MN 55343','(775) 204-1393','OppositeOfCengagement@gmail.com'),
('Krieg','903 Hanover Street, Modoc, SC 29838','(656) 579-5683','BlitzKriegBooks@hotmail.com'),
('Readers Digestive Tract','4574 Matthews Street, Buffalo Grove, IL 60089','(406) 257-1532','ReadsForThePooper@yahoo.com'),
('Haufe Groupee','2678 Drainer Avenue, Land o Lakes, WI 54540','(202) 660-4013','BilingualPublishers@yahoo.com'),
('Publisher Men','308 Thunder Road, Fremont, CA 94539','(769) 908-4231','GenericPublisherEmail@email.com');


--insertion of all writing groups into thie table WritingGroup
insert into WritingGroup(GroupName, HeadWriter, YearFormed, Subject)
values
('Jokers Are Wild','Mack Sandoval',1749,'Comedy'),
('Backdoor Sliders','Jacquelyn Craig',1968,'Fantasy'),
('Alcoballics','Otis Cohen',1654,'Romance'),
('Fight of the Conchords','Kristy Wagner',1666,'Comedy'),
('The Jelly Beans','Dennis Simmons',1666,'Non-Fiction'),
('Air Farce','Wanda Garza',1789,'Tragedy'),
('Deadly Page Turners','Kimberly Oliver',1987,'Mythology'),
('Readers Bifocals','Oscar Mccoy ',1987,'Mystery'),
('English Desire','Maxine Morrison',1899,'Drama'),
('Final Desire','Delbert Pittman',1989,'Science-Fiction'),
('Friends Enchantments','Louise Torrest',2015,'Satire'),
('Literary Sistas','Margaret Evans',2011,'Horror'),
('Chapter Divas','Jesse James',2016,'Action'),
('Brothers in Literature','Eugene Krabs',2023,'Adventure'),
('Til Pen do We Part','Ronald Ward',1536,'Comedy'),
('Shakespearean Talent with a McDonalds Budget','Ronald McDonald',1799,'Tragic Comedy'),
('Romantic Readers','Jennifer Hallmark',1893,'Romance'),
('Writers on Strike','Kathy Robinson',1998,'Drama'),
('Livid Linguists','Catly Le',1973,'Fantasy'),
('Passionate People of Poetry','Norma Coleman',1982,'Non-Fiction'),
('Living Poets Society','Jerry Hill',1964,'Satire'),
('Edgy Edgelords','Calvin Harris',1976,'Drama'),
('Fantasizing Fantastic Four','Mark Hamilton',1984,'Action'),
('Frantic Five','Alexander Jefferson',1852,'Adventure'),
('Spellbound Spellers','John Doe',1258,'Mythology');


