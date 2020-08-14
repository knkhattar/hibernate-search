#!/bin/sh
echo "script started.............."
echo "adding book1..."
echo
curl -d '{"title":"title1","author":"author1"}' -H "Content-Type: application/json" POST http://localhost:8080/book
echo
echo
echo "adding book2..."
echo
curl -d '{"title":"title2","author":"author2"}' -H "Content-Type: application/json" POST http://localhost:8080/book
echo
echo "get all books ..."
echo
curl http://localhost:8080/book
echo
echo
echo searching author2....................
curl http://localhost:8080/book/search/author1
echo
echo script ends