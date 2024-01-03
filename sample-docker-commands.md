``
docker run \
-e MYSQL_ALLOW_EMPTY_PASSWORD='yes' \
-e MYSQL_DATABASE='mobilele' \
-p 3306:3306 \
arm64v8/mysql:oracle
``