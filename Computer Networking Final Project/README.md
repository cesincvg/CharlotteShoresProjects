This project was created for CS371: Computer Networking
I collaborated with Quinn Brandt and J.D. Givens
I created the server file

In this project, we created a file transfer between a client and server using socket programming.
There are two clients, one of which requests a file from the server. The server then checks if it 
has the file, and if it doesn’t, it will request the file from the second client. Client 2 will 
send it to the server, which then sends it back over to client 1. There are multiple different ways
to implement this and, we looked into four different strategies to do so in our report. Using a wireless 
connection to complete the socket connection allows us to also test the effect of distance on the amount of
time it takes for the server/client to download/upload files.
