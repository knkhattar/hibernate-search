#!/bin/sh
echo "script started.............."
echo "adding book1..."
echo
curl -d '{"title":"titleOne","author":"authorOne"}' -H "Content-Type: application/json" -X POST http://localhost:8080/book
echo
echo
echo "adding book2 and 3..."
echo
curl -d '{"title":"titleTwo","author":"authorTwo"}' -H "Content-Type: application/json" -X POST http://localhost:8080/book
curl -d '{"title":"title3","author":"author3"}' -H "Content-Type: application/json" -X POST http://localhost:8080/book
echo
echo "Updating book2"
echo
curl -d '{"title":"2222","author":"2222"}' -H 'Content-Type: application/json' -X PUT http://localhost:8080/book/2
echo
echo "deleting book 3"
echo
curl -X DELETE http://localhost:8080/book/3
echo
echo "get all books ..."
echo
curl http://localhost:8080/book
echo
echo
echo searching author 2222....................
curl http://localhost:8080/book/search/2222
echo
echo script ends