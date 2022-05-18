# JwtTokenAuth

Проект тестировался с помощью Postman - https://documenter.getpostman.com/view/19798335/UyrEiFNa
При помощи JUnit проверялась работа БД.

Для регистрации нового пользователя необходимо произвести запрос по роутингу:
GET http://localhost:9000/signup

{
    "username": "test1234",
    "password": "test1234"
}

В ответе сервер отправит JwtToken:
{
    "token": eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIzNCIsInJvbGVzIjpbIlVTRVIiXSwiaWF0IjoxNjUxMTY5MjA0LCJleHAiOjE2NTExNzI4MDR9.rQLVXMb5vPDkOF7JASV6CX5uwNMtA5_5BRDu6hRnTnI
}

Срок действия токена - 1 час.

При истечении срока действия токена можно получить его повторно по роутингу:
GET http://localhost:9000/login

{
    "username": "test1234",
    "password": "test1234"
}

Сообщения сохраняются с использованием токена, имя пользователя указывать не обязательно, оно берется из токена:

POST http://localhost:9000/m/save?message=tokenmessage

HEADERS
'Authorization:
Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIzIiwicm9sZXMiOlsiVVNFUiJdLCJpYXQiOjE2NTExNjczNTksImV4cCI6MTY1MTE3MDk1OX0.BoOlJCiDwMjnic5UIDapAx9t2FrxVSr9H9qW7-uNslg'

Получить список сообщений можно по аналогичному роутингу с указанием вначале слова "history" (ограничение до 9999 сообщений)

POST http://localhost:9000/m/save?message=history 3

HEADERS
'Authorization:
Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIzIiwicm9sZXMiOlsiVVNFUiJdLCJpYXQiOjE2NTExNjczNTksImV4cCI6MTY1MTE3MDk1OX0.BoOlJCiDwMjnic5UIDapAx9t2FrxVSr9H9qW7-uNslg'

v2
--------------------

Теперь создавать сообщения можно с использованием тела запроса:

POST http://localhost:9000/mbody/save

{
    "message": "message from body"
}

HEADERS
'Authorization:
Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIzIiwicm9sZXMiOlsiVVNFUiJdLCJpYXQiOjE2NTExNjczNTksImV4cCI6MTY1MTE3MDk1OX0.BoOlJCiDwMjnic5UIDapAx9t2FrxVSr9H9qW7-uNslg'

Запросы curl:

--------------------

Регистрация пользователя:

curl -X GET http://localhost:9000/signup \
   -H "Content-Type: application/json" \
   -d '{"username": "test123", "password": "test123"}'
   
 Ответ сервера:
 
 {"token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIzIiwicm9sZXMiOlsiVVNFUiJdLCJpYXQiOjE2NTI5MDc2MjAsImV4cCI6MTY1MjkxMTIyMH0.1XG-l7w-VnBpXdmc3ybjWnUii58NMkusCQeCwdy2ch4"}
 
 -------------------
 
 Отправка сообщения:
 
 curl -X POST http://localhost:9000/mbody/save \
   -H "Content-Type: application/json" \
   -d '{"message": "message from body"}' \
   --header 'authorization: Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIzIiwicm9sZXMiOlsiVVNFUiJdLCJpYXQiOjE2NTI5MDc2MjAsImV4cCI6MTY1MjkxMTIyMH0.1XG-l7w-VnBpXdmc3ybjWnUii58NMkusCQeCwdy2ch4'
   
Ответ сервера:

Your message saved

 -------------------

 curl -X POST http://localhost:9000/mbody/save \
   -H "Content-Type: application/json" \
   -d '{"message": "message from body2"}' \
   --header 'authorization: Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIzIiwicm9sZXMiOlsiVVNFUiJdLCJpYXQiOjE2NTI5MDc2MjAsImV4cCI6MTY1MjkxMTIyMH0.1XG-l7w-VnBpXdmc3ybjWnUii58NMkusCQeCwdy2ch4'
   
Ответ сервера:

Your message saved

 -------------------

 curl -X POST http://localhost:9000/mbody/save \
   -H "Content-Type: application/json" \
   -d '{"message": "message from body3"}' \
   --header 'authorization: Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIzIiwicm9sZXMiOlsiVVNFUiJdLCJpYXQiOjE2NTI5MDc2MjAsImV4cCI6MTY1MjkxMTIyMH0.1XG-l7w-VnBpXdmc3ybjWnUii58NMkusCQeCwdy2ch4'
   
Ответ сервера:

Your message saved

--------------------

Получение сообщений:

curl -X POST http://localhost:9000/mbody/save \
   -H "Content-Type: application/json" \
   -d '{"message": "history 2"}' \
   --header 'authorization: Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIzIiwicm9sZXMiOlsiVVNFUiJdLCJpYXQiOjE2NTI5MDc2MjAsImV4cCI6MTY1MjkxMTIyMH0.1XG-l7w-VnBpXdmc3ybjWnUii58NMkusCQeCwdy2ch4'
   
Ответ сервера:

[{"id":2,"userId":2,"messageText":"message from body2"},{"id":3,"userId":2,"messageText":"message from body3"}]

