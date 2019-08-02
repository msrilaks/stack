docker-compose down
docker system prune -a --volumes -f
call stack-backend-service/commands.bat
call stack-task-service/commands.bat
docker-compose up