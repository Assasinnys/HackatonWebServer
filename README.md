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

3. Location - use endpoint "/location". TYPE: GET, POST.  
Description: GET - receive any user location. POST - send your location to server.  
Necessary query parameters: GET: id. POST: id, lat, lon.  
Successful response code: 200 OK; json body example: {"lon":"27.550268","id":"3","lat":"53.89093"}.  
Error codes:  
a) 400 BAD REQUEST. No error json body. Description: missing query value.  
b) 204 NO CONTENT. Error json body: {"error":"Location not found"}.  
  
4. Find nearest users - use endpoint "/find". Type: GET.  
Necessary query parameter: id.  
Description: return a list of users location (return all users at this moment).  
Successful response code: 200 OK. json body example: [{"lon":"56.668","id":"1","lat":"21.56154"},{"lon":"27.550268","id":"3","lat":"53.89093"}].  
Error code: 400 BAD REQUEST. No error json body. Description: missing query value.  
  
5. User details - use endpoint "/detail". Type GET, POST.  
Necessary query parameters: GET - id, POST - login, pass; optional query data: alcohol [string], gender [string], age [int], username [string].  
Description: GET - receive user details by id, POST - send your details to server.  
Successful response code: 200 OK. json body example: GET - {"alcohol":"vodka, rum, tequila","gender":null,"id":"7","age":"0","username":"oleg"}.
Error codes:  
a) 401 UNAUTHORIZED. No json. Description: user not found or pass is incorrect.  
b) 500 Internal Server Error. No json. Description: server error (like a database connection error adn ect).
