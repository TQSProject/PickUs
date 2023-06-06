# TQS 2023 Project: PickUs

# Abstract

PickUs is a solution for managing deliveries of products and managing delivery points.

Any external store can use our solution to manage their product delivery.

Registered ACPs (Associated Collection Point) have their own dashboard to manage their orders, and PickUs admins have a dedicated dashboard too.

Separately, we also have a web store where end users can buy products and have them delivered to an ACP.

# Team

Alexandre Gazur (102751) - Team Leader, PickUs backend, QA

Ricardo Pinto (103078) - QA Engineer, PickUs backend, QA

Emanuel (102565) - DevOps Master, PickUs frontend (admin dashboard and ACP dashboard)

Daniel Ferreira (102885) - Product Owner, Web eStore

# Related repos

PickUs frontend: https://github.com/TQSProject/WebUI

Web eStore: https://github.com/TQSProject/eStore

# API Documentation

https://documenter.getpostman.com/view/13973483/2s93m7X25U

# How to run PickUs

Make sure application.properties contains

`spring.datasource.url=jdbc:mysql://mysql-db:3306/PickUs`

then type in CMD:

`run`

# How to run PickUs tests

Make sure application.properties contains

`spring.datasource.url=jdbc:mysql://localhost:3306/PickUs`

Then type in CMD:

```
make-mysql
mvn verify
```
