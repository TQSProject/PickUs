# PickUs

## How to run:

### Locally
In `application.properties`, make sure `spring.datasource.url=jdbc:mysql://localhost:3306/mydb` is the url being used.

Then run it like a normal springboot application in a dev environment.

### Docker
In `application.properties`, make sure `spring.datasource.url=jdbc:mysql://mysql-db:3306/mydb` is the url being used.

Then, in the root folder, run in this order:
 - `mvn clean package`
 - `docker compose up`

The containers will now run. To test changes made to the code, run `down.bat` or execute `docker compose down -v --rmi local` in the terminal before executing the above commands again.