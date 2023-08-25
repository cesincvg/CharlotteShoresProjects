import socket 
from os.path import exists 
import threading
import os
import glob
import shutil
from pathlib import Path
import sys
import time
import requests

s = socket.socket()             # Create a socket object
s.bind(('10.47.117.235', 2230))            # Bind to the port
s.listen(3)                     # Now wait for client connection.
SEPERATOR = "<SEPERATOR>"

print ('Server listening....')

isFile = exists("sending_file.txt")
#this was our attempt at password validation
#conn, addr = s.accept()

#while True:
#    password = conn.recv(1024).decode("utf-8")
#    if password == "cs371":
#        conn.send(("OK").encode("utf-8"))
#        confirmation = conn.recv(1024).decode("utf-8")
#        if confirmation == "connected":
#            break
#        else:
#            conn.send(("you cannot join :(((((((((").encode("utf-8"))


while True:
    #This is where client 1 is asking me for a file, and I send if I have it if not I'll ask client 2
    conn, addr = s.accept()     # Establish connection with client 1
    conn.recv(1024).decode('utf-8') #Makes sure the first thing send from client 1 does not end up in data
    print (('Got connection from'), addr)
    data = conn.recv(1024).decode('utf-8')
    print("printing this")
    print(data)

    strategy = "2-4" #This will give us what strategy we are using
    
    if strategy == ("Strategy-1"):
        fileNeeded, fileNeeded2 = (data.split(SEPERATOR)) #Half of whatever data is sent 
        if exists(fileNeeded): #If the file is found in the server directory, it is automatically sent to the client
            print('Server received', repr(data))
            filename= fileNeeded
            f = open(filename,'rb')
            l = f.read(1024)
            while (l):
                conn.send(l)
                print('Sent ',repr(l))
                l = f.read(1024)
            f.close()
            print('Done sending')
            conn.close()
        else:
            con2, addr = s.accept()     # Establish connection with client #2 once they connect to the server
            text_file = fileNeeded
            con2.send((fileNeeded).encode('utf-8'))

            #This is where we are grabbing the file from
            print (('Got connection from Client 2'), addr)
            con2.recv(32)
            with open(text_file, "wb") as fw:
                print("Receiving..")
                while True:
                    print('receiving')
                    data2 = con2.recv(32)
                    print('Received: ', data2.decode('utf-8'))
                    fw.write(data2)
                    #print(data)
                    if not data2:
                        break
                    print('Wrote to file', text_file)
                fw.close()
                print("Received..")
                
            with open(text_file, 'rb') as f:
                print('file opened')
                while True:
                    print('sending data to client 1')
                    data2 = f.read(1028)
                    conn.send((data2))
                    print('Data sent:', data2.decode('utf-8'))
                    if not data2:
                        break
                conn.send(('').encode('utf-8'))
                f.close()
            print('Done sending')
            conn.close()

            os.remove(text_file)
    elif strategy == ("2-1"):
        fileNeeded, fileNeeded2 = (data.split(SEPERATOR)) #Half of whatever data is sent 
        if exists(fileNeeded): #If the file is found in the server directory, it waits to send to the client
            print('Server received', repr(data))
            start = time.time()
            filename= fileNeeded
            f = open(filename,'rb')
            l = f.read(1024)
            while (l):
                conn.send(l)
                print('Sent ',repr(l))
                l = f.read(1024)
            f.close()
            conn.send(('').encode('utf-8'))
            end = time.time()
            print("this is the time it takes to upload file to client 1", end-start)
            print('Done sending the file to client 1')
           # conn.close()

        con2, addr = s.accept()     # Establish connection with client #2 once they connect to the server
        text_file = fileNeeded2
        con2.send((fileNeeded2).encode('utf-8'))
    

        #This is where we are grabbing the file from
        print (('Got connection from Client 2'), addr)
        con2.recv(32)
        with open(text_file, "wb") as fw:
            print("Receiving..")
            start = time.time()
            while True:
                print('receiving')
                data2 = con2.recv(32)
                print('Received: ', data2.decode('utf-8'))
                fw.write(data2)
                #print(data)
                if not data2:
                    break
                print('Wrote to file', text_file)
            fw.close()
            end = time.time()
            print("this is the time it takes to download file from client 2", end-start)
            print("Received..")
            
        with open(text_file, 'rb') as f:
            print('file opened')
            start = time.time()
            while True:
                print('sending data to client 1')
                data2 = f.read(1028)
                conn.send((data2))
                print('Data sent:', data2.decode('utf-8'))
                if not data2:
                    break
            conn.send(('').encode('utf-8'))
            f.close()
        print('Done sending')
        end = time.time()
        print("this is the time it takes to upload and send file from client 2 to client 1", end-start)
        conn.close()
        con2.close()

        os.remove(text_file)
    elif strategy == ("2-2"):
        fileNeeded, fileNeeded2 = (data.split(SEPERATOR)) #Half of whatever data is sent 
        # This is where server has only one of requested files and client #2 has the other. I am waiting to get the file
        #from client 2 and will merge it to my file and then send merged files to client
        if exists(fileNeeded): 
            print('Server received', repr(data))
            filename= fileNeeded
            print('I have the first file, wait for me to merge it with the one from client 2')
            # conn.close()
            con2, addr = s.accept()     # Establish connection with client #2 once they connect to the server
            text_file = fileNeeded2
            con2.send((fileNeeded2).encode('utf-8'))

            #This is where we are grabbing the file from
        print (('Got connection from Client 2'), addr)
        con2.recv(32)
        with open(text_file, "wb") as fw:
            print("Receiving..")
            start = time.time()
            while True:
                print('receiving')
                data2 = con2.recv(32)
                print('Received: ', data2.decode('utf-8'))
                fw.write(data2)
                #print(data)
                if not data2:
                    break
                print('Wrote to file', text_file)
            fw.close()
            print("Received..")
            end = time.time()
            print("time it took to get download from client 2", end-start)

            filenames = [fileNeeded, text_file]
            mergedFile = "mergedFile.txt"

            #this is where i am merging the two files together
            with open(mergedFile, 'wb') as wfd:
                for f in [fileNeeded, text_file]:
                    with open(f, "rb") as fd:
                        shutil.copyfileobj(fd, wfd, 1024 *1024 *10)
  
            #send the merged file back to client 1
            with open(mergedFile, 'rb') as f:
                print('file opened')
                start = time.time()
                while True:
                    print('sending data to client 1')
                    data3 = f.read(1028)
                    conn.send((data3))
                    print('Data sent:', data3.decode('utf-8'))
                    if not data3:
                        break
                conn.send(('').encode('utf-8'))
                f.close()
                print('Done sending')
                end = time.time()
                print("time it took to upload merged data to client 1", end-start)
                conn.close()
                con2.close()

                os.remove(text_file)
    elif strategy == ("2-3"):
        fileNeeded, fileNeeded2 = (data.split(SEPERATOR)) #Half of whatever data is sent 
        print('Server received', repr(data))
        filename= fileNeeded
        print('I have the first file, wait for me to send it ')
        #conn.close()
    
        con2, addr = s.accept()     # Establish connection with client #2 once they connect to the server
        text_file = fileNeeded2
        con2.send((fileNeeded2).encode('utf-8'))

        #This is where we are grabbing the file from
        print (('Got connection from Client 2'), addr)
        con2.recv(32)
        start = time.time()
        with open(text_file, "wb") as fw:
            print("Receiving..")
            while True:
                print('receiving')
                data2 = con2.recv(32)
                print('Received: ', data2.decode('utf-8'))
                fw.write(data2)
                #print(data)
                if not data2:
                    break
                print('Wrote to file', text_file)
            fw.close()
            print("Received..")
            end = time.time()
            print("this is the time it took to download from client 2", end-start)
                
        with open(text_file, 'rb') as f:
            print('file opened')
            start = time.time()
            while True:
                print('sending data to client 1')
                data2 = f.read(1028)
                conn.send((data2))
                print('Data sent:', data2.decode('utf-8'))
                if not data2:
                    break
            conn.send(('').encode('utf-8'))
            f.close()
        print('Done sending')
        end = time.time()
        print("this is the time it took to upload to client 1", end-start)

        with open(fileNeeded, 'rb') as f:
            print('file opened')
            start = time.time()
            while True:
                print('sending data to client 1')
                data3 = f.read(1028)
                conn.send((data3))
                print('Data sent:', data3.decode('utf-8'))
                if not data3:
                    break
            conn.send(('').encode('utf-8'))
            f.close()
        print('Done sending')
        end = time.time()
        print("this is the time it took to upload to client 1", end-start)

        print('Done sending second file')
        conn.close()
        con2.close()

        os.remove(text_file)
    elif strategy == ("2-4"):
        fileNeeded, random = (data.split(SEPERATOR)) #Half of whatever data is sent 
        #this is a new strategy where client 1 will be asking for my file, i will send it. It will then ask for another file from client 2 and i will send it. The difference between this and strategy 2-1 is when client #1 is asking for each file
        if exists(fileNeeded): #If the file is found in the server directory, it will send to the client
            print('Server received', repr(data))
            start = time.time()
            filename= fileNeeded
            f = open(filename,'rb')
            l = f.read(1024)
            while (l):
                conn.send(l)
                print('Sent ',repr(l))
                l = f.read(1024)
            f.close()
            conn.send(('').encode('utf-8'))
            end = time.time()
            print("this is the time it takes to upload file to client 1", end-start)
            print('Done sending the file to client 1')
           # conn.close()

        fileNeeded, fileNeeded2 = (data.split(SEPERATOR)) #Half of whatever data is sent 
        print("fileNeeded2 is", fileNeeded2)
        

        con2, addr = s.accept()     # Establish connection with client #2 once they connect to the server
        text_file = fileNeeded2
        con2.send((fileNeeded2).encode('utf-8'))
    

        #This is where we are grabbing the file from
        print (('Got connection from Client 2'), addr)
        con2.recv(32)
        with open(text_file, "wb") as fw:
            print("Receiving..")
            start = time.time()
            while True:
                print('receiving')
                data2 = con2.recv(32)
                print('Received: ', data2.decode('utf-8'))
                fw.write(data2)
                #print(data)
                if not data2:
                    break
                print('Wrote to file', text_file)
            fw.close()
            end = time.time()
            print("this is the time it takes to download file from client 2", end-start)
            print("Received..")
            
        with open(text_file, 'rb') as f:
            print('file opened')
            start = time.time()
            while True:
                print('sending data to client 1')
                data2 = f.read(1028)
                conn.send((data2))
                print('Data sent:', data2.decode('utf-8'))
                if not data2:
                    break
            conn.send(('').encode('utf-8'))
            f.close()
        print('Done sending')
        end = time.time()
        print("this is the time it takes to upload and send file from client 2 to client 1", end-start)
        
        conn.close()
        con2.close()