
#include <stdio.h>
#include <sys/types.h> // for pid_t
#include <unistd.h> // for pid_t
#include <stdlib.h> // for getenv(), exit()
#include "proj5.h" // for MAX_PROC, MAX_COOKIE, PROGNAME, COOKIE_KEY
#include <time.h>
#include <string.h>
#include <sys/wait.h>

// #include "await.c" // taken from Lab 3
//forker.c is also referenced in this parent.c file

#define PIDCHARS 16
#define MAX_ENV_STRING 64
#define MAX_ARG_STRING 64
#define MAX_ARGS 12

void await_event(void);
void setup_handler(int sig);

extern sig_atomic_t eventdone;

void DieWithError(char *errstring){
	perror(errstring);
	exit(1);
}
int main(int argc, char *argv[]) {
  int nprocs;
  int fd[2];
  int children[MAX_PROC];
  int x;
  int i;
  int status;
  pid_t pid;
  char *cstr;
  char buf;
  char pidbuf[64];
  char *envp[2] = {pidbuf, 0};
  char *args[NUMARGS];
  char argstrings[NUMARGS][64];

  char rebuf[80];


    // Step #1 that prints the process ID to standard output
  printf("Forker parent process ID = %d\n",getpid());
  
  //This will only pass 2 or three arguments to each process
  if (argc != 2) {
  	fprintf(stderr,"Error: wrong number of arguments: %d\n",argc);
  	exit(1);
  }
  
  /* argc == 2 or argc == 3 */
  
  //Step #2 check and parse argument to get nprocs, the number of children
  nprocs = atoi(argv[1]);
  if (nprocs < MIN_PROC || nprocs > MAX_PROC) {
    printf("Error: argument must be in [%d,%d]\n",MIN_PROC,MAX_PROC);
    exit(1);
  }
  
  //Step #3 call setup_handler() to set up the signal setup_handler
  setup_handler(SIGUSR1);
  
  //step #4 
    //create a pipe using the pipe() system call
    // fd[0]; the read end of the pipe
    // fd[1]; the write end of the pipe
    if (pipe(fd)){
        perror("An error occured with opening the pipe\n");
        exit(0);
    }
    
    //Create a character string "WRITEFDNO=x" (where x 
    // is the file descriptor number of the write end of the pipe)
    cstr = (char *) malloc(sizeof(char));
    sprintf(cstr, "WRITEFDNO=%d", fd[1]);
    envp[0] =cstr;
    envp[1] = NULL;
   
   
  for (i =0; i < (NUMARGS); i ++){
  	args[i] = argstrings[i];
  }
  
  strcpy(args[0], PROGNAME);
  //creating nprocs children
  //Step #5, adjusting the second and third arguments each time through the loop so process i’s second argument is i (as a string), and its third argument is process i −1’s PID.
  for (i = 0; i < nprocs; i++){
  	children[i] = fork();
  	sprintf(pidbuf, "%s=%d", WRITEFDKEY, fd[1]);
  	
  	if (children[i] < 0){
  		perror("fork() failed");
  		exit(7);
  	}
  	if(children[i]){
  		close(fd[1]);
  		sprintf(args[2], "%d", children[i]);
  	}
  	else{
 
  		sprintf(args[1], "%d", i);
  		args[3] = NULL;
  		close(fd[1]);

  	//Calls execve() with the established argv and envp.
  		if (execve(args[0], args, envp) < 0){
  			perror("failed");
  			exit(21);
  		}
  		exit(0);
  	}
  }
  //Closes the read end of the pipe.
  close(fd[1]);
  
  //Step 6. Loop n times, reading 4 bytes from the pipe each time and verifying that it is one of the process IDs.
  for (i = 0; i < nprocs; i++){
  	read(fd[0], rebuf, 8);
  }
  sleep(1);
  //Step #7.Send sigusr1 to process n −1, using kill().
  kill((children[nprocs-1]), SIGUSR1);
  
  //Step #8. Call await_event() to wait for sigusr1.
  await_event();

  for (i =0; i <nprocs; i ++){
    	pid_t pid = wait(&status);
    	if (pid < 0){
    		perror("wait returned -1: ");
    		exit(10);
    	}
 }

  for (i =0; i <nprocs; i ++){
  	pid_t pidc = wait(&status);
  	if(pidc == -1){
  		return 1;
  	}
    	printf("Process %d: ", pid);
    	if (WIFEXITED(status))
      		printf("ended normally, with status %d.\n",WEXITSTATUS(status));
    	else if (WIFSIGNALED(status))
      		printf("was terminated via signal %d.\n",WTERMSIG(status));
    	else
     		 printf("unknown state change (can't happen?) status = %x\n",status);
    }
   exit(0);
   }
  
  	
    
  
