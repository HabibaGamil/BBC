# User-Service
The User Service in a Spring Boot application is responsible 
for managing user profiles, performing administrative actions, 
and handling authentication using JSON Web Tokens (JWT).

At a high level, the User Service provides the following functionalities:

- ### User Profile Manipulation:
   - User Registration: Allows users to create an account by providing necessary information such as username, email, and password.
   - User Profile Update: Enables users to update their profile  details, such as username, email, password, or any other relevant information.

- ### Admin Actions:
   - Access Control: Implements a role-based access control,  where certain administrative actions can only be performed by 
                     admins, such as adding category to the database.
                     
- ### Authentication using JWT:
   - User Authentication: Handles the authentication process when 
users attempt to log in, verifying their credentials against 
stored data (email and password).        
   - JWT Generation: Generates a JWT token upon successful 
authentication, containing user details and necessary claims.
   - JWT Validation: Verifies the authenticity and integrity of 
incoming JWT tokens, ensuring they have not been tampered with 
or expired.
   - Authorization: Utilizes the JWT to authenticate and authorize 
users for accessing protected resources or performing certain 
actions within the application.

## Framework used
   - Java Spring Boot.
 
## Database
   - MySQL.

## Cache
   - Redis.

## Features
   - register a new user
   - login
   - logout
   - Show user profile
   - Change password
   - Change email
   - Change username
   - Change country
   - Show user preferences 
   - Add preference
   - delete preference
   - delete account
   - Add category  (only admin)
   - Delete category  (only admin)
   - Edit category name  (only admin)
   - Show all categories  (only admin)
   - Show all users   (only admin)
