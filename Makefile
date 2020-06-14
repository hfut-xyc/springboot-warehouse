all: vue server mysql redis
	echo "all project build complete!"

vue:
	cd ./Warehouse-Vue && npm run build
	sudo docker-compose build vue

server:
	cd ./Warehouse-Server && mvn clean package -Dmaven.test.skip=true
	sudo docker-compose build server

mysql:
	@echo 'todo'
	
redis:
	@echo 'todo'

