Backend code for Restaurant Management System.

The frontend code for this is written in Angular, and the respective Respository is "RestaurantManagementSytem_Angular".

Tech : Java Spring Boot + JWT + Spring Boot + MySql + Angular + NgZorro

Usage: 
  Spring Boot : API codes 
  JWT : when a user logs in, server generates a JWT token and send back to client. The client then include this token in the headers of requests.
  Spring Security : for Authentication of the users.
  MySql : Database
  Angular: frontend code
  NgZorro : UI components for Angular
  

JWT : JWT stands for JSON Web Token. It is a compact, URL-safe means of representing claims to be transferred between two parties. 
      This token is often used for authentication and information exchange in a secure and compact way.
       Here are some key points about JWT:

   1. Structure:
        JWTs consist of three parts: a header, a payload, and a signature. These parts are concatenated with dots (periods) to form a string.
        The header typically consists of information about the type of token and the signing algorithm.
        The payload contains the claims. Claims are statements about an entity (typically, the user) and additional data.
        The signature is used to verify that the sender of the JWT is who it says it is and to ensure that the message wasn't changed along the way.

   2. Usage:
        JWTs are often used in authentication and authorization scenarios. For example, after a user logs in, a server could generate a JWT that contains the user's ID and possibly other information, sign it, and send it back to the client.
        The client can then include this JWT in the headers of subsequent requests to access protected resources on the server.

   3. Stateless and Compact:
        One of the advantages of JWT is that it is stateless, meaning the server doesn't need to store any information about the user. All the necessary information is contained in the token itself.
        JWTs are also compact, making them suitable for scenarios where the payload needs to be transmitted over the network.

  4.  Security Considerations:
        When using JWTs, it's important to store sensitive information securely in the token. While the token can be decoded on the client side, the information in the payload is visible to anyone who has access to the token.
        Always use a secure, well-established library to handle JWT creation, validation, and decoding.

   5. Libraries:
        Various programming languages have libraries and frameworks for working with JWTs. For example, libraries like jsonwebtoken in Node.js, PyJWT in Python, and others in different languages.

Here's a simple example of a JWT:

plaintext

eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c

This JWT consists of three parts: Header (eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9), Payload (eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ), and Signature (SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c).
      
      
NgZorro : NgZorro is a set of UI components for Angular, designed and maintained by the Ant Design team. 
          It provides a library of pre-designed and customizable components that developers can use to build modern and 
          visually appealing user interfaces for their Angular applications.

=======================================================================================================================================================================================
There are two kinds of users :
  1. Admin
  2. Customer

Admin : Login, Logout, Dashboard, Category, Reservations 
      - Dashboard : Shows list of Categories with Post Products & View Products Buttons-
                  * Post Products : Add new product page for that category
                  * View Products : Shows page of that category related Products with Update& Delete Button.
      - Category : Add new Category page.
      - Reservations: Page of Reservation requests made by customers. with Approve & reject button against each reservation request.

Customer :  Login, Logout, Dashboard, Reservation, All Reservations
        - Dashboard : Shows list of Categories with View Products Button-
                  * View Products : Shows page of that category related Products.
        - Reservation : Reservation request for table booking with Table type, date & time & description.
        - All Reservations : All the reservation requests details page with status (Booked or not).

** The category and Product page consist of search box where user can search for the category (by name or consisting letters) and products (by category id and products name or consisting letters) 
        
