#!/bin/sh
echo "script started.............."
echo 
echo Run POD1 put delay of 30 seconds -------------------------------------------------------------------------
echo
gnome-terminal -e "mvn -Djetty.http.port=8081 jetty:run -f /home/office/git/hibernate-search/hsearch/pom.xml"
sleep 30
echo
echo searching author iPhaen....................
curl http://localhost:8081/book/search/iPhaen
echo
echo "adding book4 on pod2..."
echo
curl -d '{"title":"Pod2Title","author":"Pod2Author"}' -H "Content-Type: application/json" -X POST http://localhost:8080/book
echo
echo searching author Pod2Authar on Pod2....................
curl http://localhost:8081/book/search/Pod2Authar
echo
echo
echo searching author Pod2Authar on Pod1....................
curl http://localhost:8080/book/search/Pod2Authar
echo
echo CHECK IF INFINISPAN DIRECTORY HAS INDEX FILES....................
echo
echo script ends