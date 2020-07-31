# HackatonWebServer
Web server for hackaton application Drinker

# API DOCS

Server address: https://hackaton-web-server.herokuapp.com/

1. Login - use endpoint "/login". Type: GET.  
Necessary query parameters: login, pass. Request example: https://hackaton-web-server.herokuapp.com/login?login=Asd&pass=123  
Description: used to log users into the system.  
Successful response code: 200 OK; json body response example: {"pass":"123","id":"1","login":"Asd"}  
Error codes:  
a) 401 UNAUTHORIZED; error json body variants: {"error":"User does not exist"}, {"error":"Password is incorrect"}.  
b) 400 BAD REQUEST; no error json body. Description: missing one of necessary query parameters.  
  
2. Registration - use endpoint "/register". Type: POST.  
Necessary query parameters: login, pass. Request example: https://hackaton-web-server.herokuapp.com/register?login=Asd&pass=123  
Description: used to user registration.  
Successful response code: 201 CREATED; json body response example: {"pass":"123","id":"1","login":"Asd"}  
Error codes:  
a) 403 FORBIDDEN; error json body example: {"error":"exist"}. Description: the user is already registered.  
b) 500 Internal Server Error; error json body example: {"error":"unknown error"}. Description: server error (like a database connection error adn ect)  
c) 400 BAD REQUEST; no error json body. Description: missing one of necessary query parameters.  
