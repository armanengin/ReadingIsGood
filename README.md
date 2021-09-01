# ReadingIsGood
ReadingIsGood is a basic library system with a feature of statistics for every customer's orders. Customers can get their ordering datas for a time period that they want to know.

# Project Description
Backend of a basic Library Management System called ReadingIsGood.

# Technologies
* Backend: Java Springboot.
* UI: thymleaf
* Database: PostgreSQL
* AWS: SQS
* OpenAPI: Swagger

## Swagger As An Open API
![Ekran Resmi 2021-08-25 23 59 24](https://user-images.githubusercontent.com/63503839/130864440-cac94e30-9a7d-4d06-ba89-f02a689d823a.png)
![Ekran Resmi 2021-08-26 00 00 04](https://user-images.githubusercontent.com/63503839/130864561-2c85e2b8-92cc-4700-b9cd-9cdf29b5d6e3.png)

## User Interface(Just Security)
![Ekran Resmi 2021-08-26 09 34 55](https://user-images.githubusercontent.com/63503839/130913214-49ab18cd-23a7-41b8-8076-22be9a509e9e.png)
![Ekran Resmi 2021-08-26 09 34 37](https://user-images.githubusercontent.com/63503839/130913252-248389c2-16a1-417b-a9f6-414370e40332.png)

## Installation
To run the app using docker:
1) mvn clean install
2) move jar from target to publish/app
3) build the docker image from publish folder using:
docker build -t getir/reading-is-good .

To run the docker container you can use the following command:
docker run -p 9090:8080 getir/reading-is-good
