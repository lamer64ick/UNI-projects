CREATE TABLE users (
    id int unsigned AUTO_INCREMENT PRIMARY KEY;
    login varchar(20) UNIQUE,
    password NVARCHAR(30),
    screen_name VARCHAR(50)
);

CREATE TABLE posts (
    id int unsigned AUTO_INCREMENT PRIMARY key,
    author_id int unsigned DEFAULT 0,
    FOREIGN key (author_id) REFERENCES users(id),
    date_published TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
);

CREATE TABLE post_content (
    post_id int unsigned default 0,
    FOREIGN key(post_id) REFERENCES posts(id),
    language_id int unsigned,
    title varchar(50),
    body varchar
);

create table comments(
    
)