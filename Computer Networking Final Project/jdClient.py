import socket, imutils, time, sys, queue, base64
from cv2 import cv2
import threading, wave, pyaudio, pickle, struct, soundfile
import os

#TYPE
TYPE = "utf-8"

#SIZE
SIZE = 1024

ClientSocket = socket.socket()
print('Socket Created!')

#authorization = "NOTOK"
#while authorization != "OK":
ClientSocket.connect(('10.47.117.235',2230))

    #password = "cs371"

    #ClientSocket.send(password.encode(TYPE))

    #authorization = ClientSocket.recv(SIZE).decode(TYPE)

    #if authorization == "OK":
        #break
    #else:
        #print("Wrong password.")

ClientSocket.send(('connected').encode(TYPE))
#ClientSocket.send(bytes('Hello Server :)','utf-8'))
q = queue.Queue(maxsize=10)

# Receive requested file
filename = ClientSocket.recv(SIZE).decode(TYPE)

# Change file type variable to decide which section of code to run
filetype = "text"                     # can be "text", "audio", or "video"

# Open sent file
# START TIMER
start = time.time()
if (filetype == "text"):
    with open(filename, 'rb') as f:
        print ('file opened.')
        
        while True:
            bytes_read = f.read(SIZE)
            if not bytes_read:
                break
            print('sending data...')
            ClientSocket.send(bytes_read)
            print('Data sent: ', bytes_read.decode(TYPE))
            
    ClientSocket.send(('').encode(TYPE))
    f.close()

if (filetype == "video"):
    command = "ffmpeg -i {} -ab 160k -ac 2 -ar 44100 -vn {}".format(filename,'temp.wav')
    os.system(command)

    BUFF_SIZE = 65536

    vid = cv2.VideoCapture(filename)
    FPS = vid.get(cv2.CAP_PROP_FPS)
    global TS
    TS = (0.5/FPS)
    BREAK=False
    print('FPS:',FPS,TS)
    totalNoFrames = int(vid.get(cv2.CAP_PROP_FRAME_COUNT))
    durationInSeconds = float(totalNoFrames) / float(FPS)
    d=vid.get(cv2.CAP_PROP_POS_MSEC)
    print(durationInSeconds,d)

    def video_stream_gen():
    
        WIDTH=400
        while(vid.isOpened()):
            try:
                _,frame = vid.read()
                frame = imutils.resize(frame,width=WIDTH)
                q.put(frame)
            except:
                os._exit(1)
        print('Player closed')
        
        vid.release()
        

    def video_stream():
        global TS
        fps,st,frames_to_count,cnt = (0,0,1,0)
        cv2.namedWindow('TRANSMITTING VIDEO')        
        cv2.moveWindow('TRANSMITTING VIDEO', 10,30) 
        while True:
            
            
            
            while(True):
                frame = q.get()
                frame = cv2.putText(frame,'FPS: '+str(round(fps,1)),(10,40),cv2.FONT_HERSHEY_SIMPLEX,0.7,(0,0,255),2)
                if cnt == frames_to_count:
                    try:
                        fps = (frames_to_count/(time.time()-st))
                        st=time.time()
                        cnt=0
                        if fps>FPS:
                            TS+=0.001
                        elif fps<FPS:
                            TS-=0.001
                        else:
                            pass
                    except:
                        pass
                cnt+=1
                
                
                
                cv2.imshow('TRANSMITTING VIDEO', frame)
                key = cv2.waitKey(int(1000*TS)) & 0xFF	
                if key == ord('q'):
                    os._exit(1)
                    TS=False
                    break	
                    

    from concurrent.futures import ThreadPoolExecutor
    with ThreadPoolExecutor(max_workers=3) as executor:
        executor.submit(video_stream_gen)
        executor.submit(video_stream)



if (filetype == "audio"):
    
    def audio_stream():
        
        CHUNK = 10*1024

        data, samplerate = soundfile.read(filename)
        soundfile.write(filename, data, samplerate)

        wf = wave.open(filename, 'rb')
        p = pyaudio.PyAudio()
        
        stream = p.open(format=p.get_format_from_width(wf.getsampwidth()),
                        channels=wf.getnchannels(),
                        rate=wf.getframerate(),
                        input=True,
                        frames_per_buffer=CHUNK)

        data = None
        sample_rate = wf.getframerate()
        
            
        while True:
            data = wf.readframes(CHUNK)
            ClientSocket.send(data)
            time.sleep(0.8*CHUNK/sample_rate)

    t1 = threading.Thread(target=audio_stream, args=())
    t1.start()

end = time.time()
print("Upload Time: ", end-start, "seconds")

ClientSocket.close()
print('Connection closed.')