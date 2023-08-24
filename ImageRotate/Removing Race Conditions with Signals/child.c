#include <stdio.h>
#include <sys/types.h> // for pid_t
#include <unistd.h> // for pid_t
#include <stdlib.h> // for getenv(), exit()
#include "proj5.h" // for MAX_PROC, MAX_COOKIE, PROGNAME, COOKIE_KEY
#include <time.h>
#include <string.h>
#include <sys/wait.h>
#include <signal.h>

void usr1handler(int sig);
void await_event(void);
void setup_handler(int sig);
void DieWithError(char *errstring){
	perror(errstring);
	exit(1);
}
static volatile int eventdone = 0;
int main(int argc, char *argv[]){
    //step #1 Parse arguments to get the PID of the process to be sent sigusr1.
    //step #2 Use getenv() and atoi() to get the file descriptor number of the write end of the pipe
    // inherited from the parent.
    
   char *fdstr = getenv("WRITEFDNO");
   char *pid;
   pid_t pidb = atoi(argv[2]);
   int fd = atoi(fdstr);
   int r;
   
    
    //step #3 c-all the setup_handler to set up the signal handler
    setup_handler(SIGUSR1);
    
    //step #4 Write its process ID (as a 4-byte integer) to the pipe.
    //int *buf1;
    //buf1 = (int) getpid();
    //write(fdnum,buf1, sizeof(int));
    
    pid = (char*) malloc(sizeof(char));
    sprintf(pid, "%d", getpid());
    write(fd, pid, 4);
    
    //step #5 Call await_event to block until sigusr1 arrives.
    
    await_event();
    
    //step #6 print the greeting
    printf("Process %s (pid %d) greets you.\n",argv[1],getpid());
    
    //step #7 Send sigusr1 to the PID given in its third argument
    
    kill(pidb, SIGUSR1); //let the child know event 1 has occured

    //step #8 exit with random status between 100 and 200
    srandom(getpid());
    r = (long) (random() % 200 + 50);
    exit(r);
    
}
