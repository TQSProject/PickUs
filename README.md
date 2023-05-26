# TQS 2023 Project: PickUs

# Abstract

PickUs is a solution for managing deliveries of products and managing delivery points.

Any external store can use our solution to manage their product delivery.

Registered ACPs (Associated Collection Point) have their own dashboard to manage their orders, and PickUs admins have a dedicated dashboard too.

Separately, we also have a web store where end users can buy products and have them delivered to an ACP.

# Team

Alexandre Gazur (102751) - PickUs backend

Ricardo Pinto (103078) - PickUs backend, QA

Emanuel (102565) - PickUs frontend (admin dashboard and ACP dashboard)

Daniel Ferreira (102885) - Web eStore

# Related repos

PickUs frontend: https://github.com/TQSProject/WebUI

Web eStore: https://github.com/TQSProject/eStore

## How to run:

### Locally
In `application.properties`, make sure `spring.datasource.url=jdbc:mysql://localhost:3306/mydb` is the url being used.

Start up the sql container with `docker compose up mysql-db -d`.

Then run it like a normal springboot application in a dev environment.

### Docker
In `application.properties`, make sure `spring.datasource.url=jdbc:mysql://mysql-db:3306/mydb` is the url being used.

Then, in the root folder, run in this order:
- `mvn clean package`
- `docker compose up`

The containers will now run. To test changes made to the code, run `down.bat` or execute `docker compose down -v --rmi local` in the terminal before executing the above commands again.