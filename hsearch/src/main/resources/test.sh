#!/bin/sh
echo "script started.............."
echo "adding book1..."
echo
curl -d '{"title":"title1","author":"author1"}' -H "Content-Type: application/json" -X POST http://localhost:8080/book
echo
echo
echo "adding book2 and 3..."
echo
curl -d '{"title":"title2","author":"author2"}' -H "Content-Type: application/json" -X POST http://localhost:8080/book
curl -d '{"title":"title3","author":"author3"}' -H "Content-Type: application/json" -X POST http://localhost:8080/book
echo
echo "Updating book2"
echo
curl -d '{"title":"title22","author":"author22"}' -H 'Content-Type: application/json' -X PUT http://localhost:8080/book/2
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
echo searching author1....................
curl http://localhost:8080/book/search/author1
echo
echo script ends