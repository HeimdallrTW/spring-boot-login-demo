# Simple Spring Boot Login Demo
這是使用Spring Boot框架下的簡單登入實作。<br/>
以下會簡單簡述使用到相關資料表。

## Users Table
此範例使用的是MySQL，Table的結構可以參閱下列檔案。<br/>

> https://reurl.cc/mlQ4Xj<br/>

`user_salt`為密碼加密用的鹽值<br/>
`password_hash`為加密後的密碼
`is_verified`表示是否經過信箱驗證。
