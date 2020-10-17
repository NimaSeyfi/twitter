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
