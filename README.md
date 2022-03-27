# Spring Blog API

## Simple Spring Boot Blog Application RESTful API including Basic Auth

The application is secured using role based Basic Auth.

Application users can access all GET endpoints, POST, PUT and DELETE endpoints 
can be accessed depending on business logic.

Example: A user can only update or delete posts which he owns 
or only add a tag to a post which he owns.

Entity classes: User, Post, Tag, Comment

Code is well documented and should be self explanatory.
