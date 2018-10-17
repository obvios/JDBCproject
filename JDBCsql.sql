/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Daniel Gione 
 * Created: Oct 16, 2018
 */

drop table Book;
drop table Publisher;
drop table WritingGroup;




create table Publisher (
PublisherName varchar(20) NOT NULL,
PublisherAddress varchar(40) NOT NULL,
PublisherPhone varchar(12) NOT NULL, 
PublisherEmail varchar(40) NOT NULL); 

create table WritingGroup(
GroupName varchar(20) NOT NULL, 
HeadWriter varchar(20) NOT NULL, 
YearFormed integer NOT NULL, 
Subject varchar(20) NOT NULL);

create table Book(GroupName varchar(20) NOT NULL, 
PublisherName varchar(20) NOT NULL, 
BookTitle varchar(30) NOT NULL, 
YearPublished integer NOT NULL, 
NumberPages integer NOT NULL);

alter table Publisher
    add constraint publisher_pk 
    PRIMARY KEY (PublisherName);

alter table WritingGroup
    add constraint writinggroup_pk
    PRIMARY KEY (GroupName);

alter table Book
    add constraint book_writinggroup_fk
    FOREIGN KEY (GroupName)
    references WritingGroup(GroupName);
alter table Book
    add constraint book_publisher_fk
    FOREIGN KEY (PublisherName)
    references Publisher(PublisherName);
alter table Book
    add constraint book_pk
    PRIMARY KEY (GroupName, BookTitle);

