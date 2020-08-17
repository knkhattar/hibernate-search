#!/bin/sh
echo "script started.............."
echo 
echo Run POD1 put delay of 30 seconds -------------------------------------------------------------------------
echo
gnome-terminal -e "mvn -Djetty.http.port=8080 jetty:run -f /home/office/git/hibernate-search/hsearch/pom.xml"
sleep 30
curl http://localhost:8080/book
echo
echo
echo "adding book1..."
echo
curl -d '{"title":"titleOne","author":"authorOne"}' -H "Content-Type: application/json" -X POST http://localhost:8080/book
echo
echo "adding book2 and 3..."
echo
curl -d '{"title":"2222","author":"iPhone"}' -H "Content-Type: application/json" -X POST http://localhost:8080/book
curl -d '{"title":"title3","author":"author3"}' -H "Content-Type: application/json" -X POST http://localhost:8080/book
echo
echo ----------search all books-----------------------
curl http://localhost:8080/book
echo
echo
echo searching author iPhaen....................
curl http://localhost:8080/book/search/iPhaen
echo
echo
echo
echo CHECK IF INFINISPAN DIRECTORY HAS INDEX FILES....................
echo
echo script ends