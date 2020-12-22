#!/bin/sh
# Run as : bash ./test-pod1.sh
clear
echo "script started.............."
echo 

echo "gnome-terminal -e "mvn -Djetty.http.port=8080 jetty:run -f /home/office/git/hibernate-search/hsearch/pom.xml""
gnome-terminal -e "mvn -Djetty.http.port=8080 jetty:run -f /home/office/git/hibernate-search/hsearch-elasticsearch/pom.xml"

echo "sleep 30"
sleep 30
echo ""

echo curl http://localhost:8080/book
curl http://localhost:8080/book
echo
echo ""
echo "Create First Book - comment this in the next run"
echo curl -d '{"title":"Book 1","author":"iPhone"}' -H "Content-Type: application/json" -X POST http://localhost:8080/book
curl -d '{"title":"Book 1","author":"iPhone"}' -H "Content-Type: application/json" -X POST http://localhost:8080/book
echo " "
echo " "
echo searching author iPhaen....................
curl http://localhost:8080/book/search/iPhaen
echo " "
echo " "
echo script ends