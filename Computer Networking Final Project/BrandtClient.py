# CS371 Project Part 1--------------------------------------
# Group Members: Charlotte Shores, JD Givens, Quinn Brandt
# Date last Edited: 17 November 2022
# ----------------------------------------------------------
import time
import socket
import os
ClientSocket = socket.socket()
print('Socket Created!')

# Establishing Connection------------------------------
IP = "10.47.117.235"
PORT = 2230
FORMAT = "utf-8"
BUFFSIZE = 1024
SEPERATOR = "<SEPERATOR>"

ClientSocket.connect((IP,PORT))

#authorization = "NOTOK"
#while authorization != "OK":

    #password = "cs371"

    #ClientSocket.send(password.encode(FORMAT))

    #authorization = ClientSocket.recv(BUFFSIZE).decode(FORMAT)

    #if authorization == "OK":
        #break
    #else:
       #print("Wrong password.")

#ClientSocket.send(('connected'.encode(FORMAT)))

ClientSocket.send(('Hello Server :)'.encode(FORMAT)))


print("Connection established with Server.")

# Request Files From Server-----------------------------
filename1 = "server1.txt"
filename2 = "client2.txt"     #-----------------------------------------> change this for a request of all the different file options
ClientSocket.send(f"{filename1}{SEPERATOR}{filename2}".encode(FORMAT))   #    options of files include: server1.txt
                                                                         #                              server2.txt
# Recieving File From Server---------------------------                  #                              client2.txt
print('Waiting for file...')                                             #                              client2-1.txt


with open('recieved_file', 'wb') as f:
    print('Reciept initiated...')
    start = time.time()
    while True:
        data = ClientSocket.recv(BUFFSIZE)
        f.write(data) # writes data to file
        if not data:
            # file is done sending/being recieved
            break
        print("Recieved: ", data.decode(FORMAT))

    f.close()    
print('Successful reciept of file.')
end = time.time()
print(end - start)

# Acknowledgement sent to server
ack = "Files were recieved."
ClientSocket.send((ack).encode(FORMAT))

# Closure of new text file, and of connection with the server

ClientSocket.close()
print('Connection closed.')


# In theory, Client 1 (me) should request a SPECIFIC file name (as all three entities should have distinct files)
# from the server.
# If Server is not currently in possession of said file, Server requests file from Client 2, all while Client 1
# remains connected to the server waiting to recieve a file
# Client 2 uploads the file to the server, who sends the file over to Client 1
# If Server was not already in possession of the file, the file must be deleted since it did not originally
# belong on the server.
# Finally, Client 1 sends an acknowledgement to the server that it has recived the file and all connections are closed.