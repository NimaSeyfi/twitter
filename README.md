# twitter
a simpler version of twitter based on spring framework

# Setup
Database : to use this Application create a database with name 'twitterdb' in Mysql and set username to "root" and let the password empty.

After first run of application, an Admin user will be registered and you can deal with this Main Admin user to manage your database.

# Domains
User: user domain describe a User in our application. This entity contains these parameters:
  - long id
  - String username
  - String password
  - String email
  - String phone
  - UserRole role

Twit: a twit entity. This entity contains these parameters:
  - long id
  - User user
  - String content
  - Date pubDate

Comment: a comment entity wich has Many-to-one relations with User and Twit domains. This entity contains these parameters:
  - long id
  - User user
  - Twit twit
  - String text
  - Date date
  

LikeObj: a like entity wich has Many-to-one relations with User and Twit domains too. This entity contains these parameters:
  - long id
  - User user
  - Twit twit
  - Date date
 
![Relation Between Domains](https://i.ibb.co/D8RQR4h/Blank-diagram.png)<br>

**NOTE:** All ids are auto generated. 
**NOTE:** UserRole is a ENUM class that used for Roles in this application. Any Role containts some Permissions wich are used for authorize methods and giving access to users.
Permissions are in UserPermission ENUM class listed bellow:
  - user:read | user:write
  - twit:read | twit:write
  - like:read | like:write
  - comment:read | comment:write
  
in this version we Have 2 Roles : **Admin** and **Client**:<br>
  - CLIENT - Permissions : LIKE_READ,LIKE_WRITE, COMMENT_READ, COMMENT_WRITE,TWIT_READ,TWIT_WRITE,USER_READ
  - ADMIN - Permissions : LIKE_READ,   LIKE_WRITE, COMMENT_READ, COMMENT_WRITE,TWIT_READ,TWIT_WRITE, USER_READ, USER_WRITE
  
# Repositories
All Domains have a repository that can handle access to database and make some CRUD (Create, Read, Update, Delete) operations easier.

# Services
Any Domain Also have a Service. in any service we user the Repository of that domain and maybe other service to handle some methods. all services have CRUD Methods and other functionalities added to the services individually .<br>
<br>
**UserService:**<br>
  - create : In this Method we check if a user not exist with entered credentials, then create and return it.<br>
  - update : updating selected user.<br>
  - delete : delete selected user.<br>
  - getAllUsers : returns a list of all users.<br>
  - getUserTwits : returns selected user twits.<br>
  - getUserLikes : returns selected user likes.<br>
  - getUserComments : returns selected user comments.<br>
  - findUser : find user by ID.<br>
  - findUserByEmail : find user by email.<br>
  - findUserByPhone : find user by phone.<br>
<br>

**TwitService:**<br>
  - create : creating new twit.<br>
  - update : updating selected twit.<br>
  - delete : delete selected twit.<br>
  - getAllTwits : returns a list of all twits.<br>
  - getTwitLikes : returns selected twit likes.<br>
  - getTwitComments : returns selected twit comments.<br>
  - findTwit : find twit by ID.<br>
<br>

**CommentService:**<br>
  - create : creating new comment.<br>
  - update : updating selected comment.<br>
  - delete : delete selected comment.<br>
  - getAllComments : returns a list of all comment.<br>
  - getTwitComments : returns selected twit comments.<br>
  - getUserComments : returns selected user comments.<br>
  - findComment : find comment by ID.<br>
  - findCommentBetweenDates : find comments between start date and end date.<br>
  
     **NOTE:** Date Format entered (In string type) should be : "yyyy/mm/dd hh:mm:ss"
<br>

**LikeObjService:**<br>
  - create : creating new like.<br>
  - update : updating selected like.<br>
  - delete : delete selected like.<br>
  - getAllLikes : returns a list of all likes.<br>
  - getTwitLikes : returns selected twit likes.<br>
  - getUserLikes : returns selected user likes.<br>
  - findLike : find like by ID.<br>
  - findLikeBetweenDates : find likes between start date and end date.<br>
  
     **NOTE:** Date Format entered (In string type) should be : "yyyy/mm/dd hh:mm:ss"

# Exceptions
RestExceptionHandler class contains all expections handlers in our application. this class 
extends ResponseEntityExceptionHandler and Override all methods and returns
a ResponseEntity based on our custom pattern.When any exception occured an
ApiError class will created and RestExceptionHandler response it to the user.
<br>**ApiError Class Parameters:**<br>
- HttpStatus status;
- LocalDateTime timestamp;
- String message;
- String debugMessage;

<br>**Custom Exception Classes:**<br>
In addition to above handlers, some Exceptions occures based on our functionality. So we should 
create an exception for the wrong situations. these Exception Classes listet as bellow:<br>
- **Exception404 :** This exception occures when a entity didn't found in database.
- **RoleNotFoundException :** This exception occures when user role is not entered correctly.
- **EntityExistException :** This exception occures when registering a user that credentials exist in database.
- **DateFormatException :** This exception occures when dates entered in wrong format.

All above custom exceptions handled in RestExceptionHandler class with @ExceptionHandler(Exceptionclass.class)
annotation.

# Security
For Security this application uses JWT(Jason Web Token) and generates a Token for every user and any token 
expires in 5 days(this option can be changed in application.properties).
A user can authenticate with generated token.
Also two filters are created for any request: **JwtUsernameAndPasswordAuthenticationFilter** which is used
to authenticate username and password and generate the Token and **JwtTokenVerifier** wich is used to
verify the token in Request "Authorization" header.
every token contains "sub" (username) and all "authorities" of the user.
for Authorize the access of Users and Permissions to application parts, we use this "authorities" list of every Request's User.
 <br>
Folder "security" contains Application Security Config class that setups all options we need for the security. Also this class 
configures our custom **userDetailsServvice(userService)** to access the 
Users and uses **BCryptPasswordEncoder** to encode passwords.
<br>**Security Configurations:**<br>
<code>
 http<br>
                .csrf().disable()  </code><br>
- We use Postman to send requests so we can disable CSRF. for further uses we should enable it for more security.
 
 
<code>.sessionManagement()<br>
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)</code>
              <br> 
- JWT is a Stateless Method.

<code>.and().addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
               <br>.addFilterAfter(new JwtTokenVerifier(jwtConfig, secretKey), JwtUsernameAndPasswordAuthenticationFilter.class)
                </code><br>
- Adding JWT Filters.


<code>.authorizeRequests()
                <br>.anyRequest()
                <br>.authenticated()
                ;</code><br>

- Authorize and Authenticate any Request.

# Controllers
There are 4 @RestController in our Application (one for each Service). <br>
**UserController:** (prefix = "/user")<br> 
- **createUser** : @PostMapping
                   @PreAuthorize("hasAuthority('user:write')")<br>
                   **creates a User with Params** : @RequestParam String username,
                                                                                           @RequestParam String password,
                                                                                           @RequestParam String phone,
                                                                                           @RequestParam String email,
                                                                                           @RequestParam String role
- **updateUser** : @PutMapping
                   @PreAuthorize("hasAuthority('user:write')")<br>
                   **updating a User. Params** : @RequestParam long id,
                                                                                            @RequestParam String username,
                                                                                            @RequestParam String password,
                                                                                            @RequestParam String phone,
                                                                                            @RequestParam String email

- **getAllUsers** : @GetMapping("/all")
                    @PreAuthorize("hasRole('ROLE_ADMIN')")
                    **getting All Users. Params** : No Params needed.     

- **getUser** : @GetMapping("/all")
                    @PreAuthorize("hasRole('ROLE_ADMIN')")
                    **get User of entered ID. Params** : @RequestParam long id

- **getUserByPhone** : @GetMapping("/all")
                    @PreAuthorize("hasRole('ROLE_ADMIN')")
                    **get User of entered Phone. Params** : @RequestParam string phone

- **getUserByEmail** : @GetMapping("/all")
                    @PreAuthorize("hasRole('ROLE_ADMIN')")
                    **get User of entered email. Params** : @RequestParam string email
 
- **deleteUser** : @DeleteMapping
                       @PreAuthorize("hasAuthority('user:write')")
                    **Removes User. Params** : @RequestParam long id

- **getUserTwits** : @GetMapping("/gut")
      @PreAuthorize("hasAnyAuthority({'user:read','twit:read'})")
      **Get user twits** : @RequestParam long id   
      
- **getUserLikes** : @GetMapping("/gul") 
                         @PreAuthorize("hasAnyAuthority({'user:read','twit:read','like:read'})")
      **Get user likes** : @RequestParam long id      
      
- **getUserComments** :@GetMapping("/guc")
                        @PreAuthorize("hasAnyAuthority({'user:read','twit:read','comment:read'})")
                         **Get user comments** : @RequestParam long id                                                             