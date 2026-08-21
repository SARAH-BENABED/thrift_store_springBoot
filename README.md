# Thrift store - Backend 

REST API backend for a full stack thrift clothing e-commerce website 
This backend is responsible for authentication , product management , processing orders , email sending and verification and communication with mySQL database 

## Features 

### Authentication and Authorization 

-User registration and login 
-JWT (json web token ) based authentication (token expiry after 10 minutes)
-Role based authorization 
-Protected admin endpoints
-Email verification (integration with Brevo's API) 

### Product Management 

-Create products  (Admin)
-Retrieve products 
-Update products  (Admin)
-Delete products  (Admin)
-Product information stored in MySQL

### Orders 

-Create orders 
-Guest checkout 
-Store customer information at the time of checkout 
-Store ordered products and quantities 
-Calculate and store total price 
-Track order status (PENDING,CONFIRMED,DELIVERED,CANCELED)

## Technologies 

-JAVA 17
-Spring Boot 
-JWT
-MySQL
-Brevo's API 

## Frontend 
repository : 
https://github.com/SARAH-BENABED/thrift_store_frontend.git


