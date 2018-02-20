# WebServer667
Dillon Brosnan
Dylan Abrames
February 19, 2018
CSC 667
TeamDoubleD Web Server Project Spring 2018

Link to Github: 

Project Introduction and Overview:
	Our goal at the beginning of this assignment was to create, from scratch, a web server capable of handling HTTP requests in Java. Based off of the UML’s provided, our team got to work on creating the 9 main classes and other supporting classes. These classes would work together to read in HTTP requests, parse them, decide what actions to take based of the request, execute those actions and send the appropriate response back to the client. 
	Our coding environment was text editors, compiling and running our code from the command line, either in Ubuntu or the Ubuntu subsystem on Windows 10.
	Below is a list of what our team was tasked to accomplish, and what we feel like we accomplished:




















Command Line Instructions to compile and Execute:
	To compile this code, first navigate to the Code directory. Once there, please run these 3 commands:
find . -type f -name '*.class' -delete
javac Webserver.java
java Webserver
	The first line to delete any .class files that may be, however unlikely, leftover from old compilations for testing. The second line compiles the code, and the third executes the code.

Assumptions:
	We are assuming nothing is being PUT or POSTED to the document root, which ends up corrupting index.html. We also assume Content-Length headers are always correct for files, as well as there will be no empty input for username and password authentication. We also assume that the httpd.conf and mime.types will be located in the conf/ folder.  There were many questions we had that we did get answers for, either from the internet, peers or our instructor. I would not go so far as to say the directions were perfectly clear. They were vague and invited many questions, which we believe to be the goal of this assignment, ask questions, figure it out, implement. 










Implementation Discussion:
	We used the UML diagram that was provided as a baseline and expanded from there. Our team decided that the best, easiest, most effective and efficient way to implement was to do so how our instructor would have. Any further questions about our implementation could be hopefully answered by our instructor. 
	Our code was organized into 9 packages: Code, Authorization, ConfigurationReader, Date, Exceptions, Logger, Request, ResponseFactory and Worker, as well has our WebServer.java and Server.java files in the root directory.  These packages below contain the following .java files

Configuration Reader:
	ConfigurationReader.java: parent class. Contains methods for the reading of files.
	HttpdConf.java: extends ConfigurationReader, stores values of httpd.conf in hashmaps, as well as accessor methods.
	MimeTypes.java: extends ConfigurationReader, reads and stores mime.types into hashmap, and contains accessor functions to lookup in hashmap.

Authorization:
	Htaccess.java: extends ConfigurationReader, loads our .htaccess and stores values into local variables, also contains accessor methods.
	Htpassword.java: extends ConfigurationReader, reads .htpassword file into hashmap,  checks for valid username/password combination, and responsible for decoding encoded usernames and passwords.


Date:
	FormattedDate.java: creates an object that contains a formatted date based of the local time as well as converts it into a String.

Exceptions:
	ServerException.java: Parent class, extends Exception. Sets a reason phrase and status code. Contains accessor function for code. 

Logger: 
	Logger.java: outputs IP address, user info, date, request line and response code with content length of response to a text file located in our logs folder.

Request:
	Request.java: responsible for parsing client request and storing that information into appropriate data structures and variables. Also contains accessor methods. 
	Resource.java: Resolves uri to absolute path, checks if uri is aliased. 

Response Factory:
	ResponseFactory.java: handles the returning of the correct response based on the request verb and other factors. 
	Response.java: parent class, responsible for sending headers and accessor methods.
	BadRequest.java: extends Response.java, sends out 400 response.
	CreatedResponse.java: extends Response.java, sends out 201 response.
	FileNotFoundResponse.java: extends Response.java, sends out 404 response.
	ForbiddenResponse: extends Response.java, sends out 403 response.
	NoContentResponse.java: extends Response.java, sends out 204 response.
	NotModifiedResponse.java: extends Response.java, sends out 304 response.
	OKResponse.java: extends Response.java, sends out 200 response and correct body, depending on if we are running a script. 
	UnauthorizedResponse.java: extends Response.java, sends out 401 response.

Worker:
	Worker.java: extends thread, creates threads to accept multiple requests and return multiple responses.

WebServer.java: responsible for executing the code. Creates sockets and threads, and calls on thread.start().

	We organized our code into the following packages for simplification and abstraction purposes. Code that does similar work is organized into the same package. There is nothing special about or code organization, very standard and efficient. 




UML


Test Plan: 
	Our teams test plan was to utilize the index.html provided by our instructor as well as Postman. Postman was used earlier in our code, when it was less complete. We were able to attempt a GET request with no authorization headers, and received our 401, and with an incorrect authorization header, where we received a 403. Once our code was more complete and we were able to, we tested on the index.html provided. We spun up our server, and went to our localhost. When prompted for a username and password, we entered an incorrect combination and received a 403 response. Finally after entering in the correct username and password, index appeared.  We checked Chromes developer tools network tab, and saw we were sending and receiving the correct responses and headers. We were loading the image, as well as getting a 404 on the /protected/ file that was not included, and the threading button as well as running the pearl script was functioning. We encountered a problem with caching. Our caching is working, but that means that we had to use the “Incognito” mode on chrome, and any time we wanted to re-test, we had to close the previous window, and open up a new “Incognito” window. This issue, as stated before, confirms that our caching is working correctly, as we were getting a 304 response after reloading index.html from a previously logged in session. After, when this was functional, we continued to test on Postman with POST, DELETE and PUT. We successfully implemented DELETE, POST and PUT however gave us problems, documented below. 







Results and Conclusions:
	The greatest thing we learned during our time in this department is how to learn, how to teach ourselves. That is not to say that we did not have good instructors, but it is necessary to be able to not know a subject, research it, and use your newly acquired knowledge in a productive way. This project tested our team on this ability. Our team walked into this project knowing very little about HTTP requests and what actually happens when you click “send” in your browser. Now we know, a lot of work is done behind the scenes to give the user an optimal experience while knowing very little, if anything, about how a request works. Each click of the button is sending a different verb, to a different uri, and trying to do something different with data and information. We learned that for a simple HTTP request, a lot of things must happen in a very specific way. First, a correct verb must be used, with correct headers depending on what that verb is. Secondly, a request is parsed and executed, depending on if the client is authorized to make a request to the server. If so, the server is responsible for performing the requested actions, and return to the user the appropriate response, code, headers and body. 
	We faced many challenges in this project. The biggest one of all, was what I mentioned before, how a request must be handled in a very specific way. The request must be formatted and read in as follows:
	verb uri httpversion CRLF
	headers CRLF
	CRLF
	body
Our code was reading in the request incorrectly, looking for 3 CRLF after our request headers instead of two. One line of code was responsible for almost an entire days delay, and only after our instructor looked over our code for a while was he able to point it out. Procedure and protocol are very important, and can not deviated from in any way.
	Our second biggest problem was a static scanner. While using a scanner to read our httpd.conf and mime.types files, we created a single, static scanner that was now responsible for the reading of both files. This is a no-no. Our files were getting mixed up and information was getting stored in an incorrect place. This was another issue that was a single line of code that caused us hours of delay searching for this bug. What we learned from this, never make a scanner in a parent class static. 
	When testing our PUT and POST requests on Postman, we ran into problems. In our attempts to PUT and POST a .jpg image, we saw that we were putting a corrupted file, with the correct name, into the correct directory in our file structure. After multiple requests, we soon realized that every third or fourth request, after deleting the image from our file structure, the image would be placed correctly and not corrupted. We could not discover a solution or a reason for this issue. Our best guess is that it has to do with the threading, but that is only a guess. Hours of research gave us no more knowledge on the issue. 
	Finally, not necessarily a problem, but a challenge was figuring out how each of these classes were responsible for which part of the work, and how that fits into the work flow diagram. With a total of 24 classes and over 1,100 lines of code, it is by far one of the biggest projects we have worked on in our careers at SFSU. The challenge is figuring out what each class does, what does the class have access to, do we need more accessor methods, where in the work flow should which class be used. All of these are normal problems every team faced with this assignment, especially with an assignment as unstructured as this. 
