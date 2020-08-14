#!/bin/sh
echo "script started.............."
echo "Deleting all books first"
echo
curl -X DELETE http://localhost:8080/book
echo
echo "get all books ..."
echo
curl http://localhost:8080/book
echo
echo
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
curl -d '{"title":"2222","author":"iPhone"}' -H 'Content-Type: application/json' -X PUT http://localhost:8080/book/2
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
echo searching author iPhaen....................
curl http://localhost:8080/book/search/iPhaen
echo
echo
echo
echo searching author iPhone....................
curl http://localhost:8080/book/search/iPhone
echo
echo
echo
echo searching author iPhaaaen....................
curl http://localhost:8080/book/search/iPhaaaen
echo

echo script ends