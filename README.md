# Simple Spring Boot Login Demo
這是使用Spring Boot框架下的簡單登入實作。<br/>
以下會簡單簡述使用到相關資料表。

## Users Table
```sql
CREATE TABLE users(
	id INTEGER AUTO_INCREMENT,
    user_email VARCHAR(150) UNIQUE NOT NULL,
    user_salt VARCHAR(10) NOT NULL DEFAULT "$%7!@^*+2#",
    password_hash VARCHAR(256) NOT NULL DEFAULT "bc5da5cc5e6f0336725807848122d55315f128d9d9b4989f7ce21992defeb7ba",
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    is_admin BIT DEFAULT 0,
    is_verified BIT DEFAULT 0,
    last_login TIMESTAMP DEFAULT NOW(),
    created_at DATETIME DEFAULT NOW(),
    modified_at DATETIME DEFAULT NOW(),
    PRIMARY KEY (id)
);
ALTER TABLE users AUTO_INCREMENT=100;
```
`user_salt`為密碼加密用的鹽值<br/>
`password_hash`為加密後的密碼

<strong>Insert Data</strong>
```sql
INSERT INTO users(user_email, first_name, last_name)
VALUES ("Admin@gmail.com", "Admin", "Admin"),
	   ("Jacob@gmail.com", "Jacob", "Smith"),
	   ("Emily@gmail.com", "Emily", "Johnson"),
	   ("Michael@gmail.com", "Michael", "Brown"),
	   ("Grace@gmail.com", "Grace", "Williams"),
	   ("Benjamin@gmail.com", "Benjamin", "Davis"),
       ("Sophia@gmail.com", "Sophia", "Garcia"),
       ("Matthew@gmail.com", "Matthew", "Rodriguez"),
       ("Isabella@gmail.com", "Isabella", "Martinez"),
       ("William@gmail.com", "William", "Hernandez"),
       ("Ava@gmail.com", "Ava", "Lopez"),
       ("Christopher@gmail.com", "Christopher", "Lee"),
       ("Mia@gmail.com", "Mia", "Davis"),
       ("Nicholas@gmail.com", "Nicholas", "Perez"),
       ("Olivia@gmail.com", "Olivia", "Robinson"),
       ("Daniel@gmail.com", "Daniel", "Mitchell"),
       ("Charlotte@gmail.com", "Charlotte", "Wright"),
       ("Andrew@gmail.com", "Andrew", "King"),
       ("Amelia@gmail.com", "Amelia", "Turner"),
       ("James@gmail.com", "James", "Campbell"),
       ("Abigail@gmail.com", "Abigail", "Baker");
UPDATE users SET users.is_admin = 1 WHERE id = 100;
UPDATE users SET users.is_verified = 1;
```
`is_verified`表示是否經過信箱驗證。
