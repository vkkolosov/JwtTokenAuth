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

