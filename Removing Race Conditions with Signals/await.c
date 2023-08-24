/* This will contain three functions, which are used by both the parent and child; therefore they
are factored out and the same code can be used in both. The functions are: usr1handler(),
shown above, which will execute when sigusr1 is delivered and set the variable eventdone
to 1; setup_handler(), which will install the handler using the sigaction() system call;
and await_event(), which will execute the pattern shown above to block until sigusr1 is
delivered. (Hint: Lab 3.) */

#include <stdio.h>
#include <signal.h>
#include <stdlib.h>

/* Signal-handling routines. */
/* See guidelines G4 and G5 in Section 8.5 of the textbook */

// void DieWithError(char *msg);  // defined in mains

//void DieWithError(char *errstring) {
 // perror(errstring); // expect errno to be set when this is called.
 // exit(1);
 //}

volatile sig_atomic_t eventdone = 0;

//This is the first function usr1handler asked for in project 5
void usr1handler(int sig) {
  eventdone = 1;
}

//This is the second function set_up handler which will install the handler using t
//the sigaction() system call
void setup_handler(int sig) {
  struct sigaction myaction;


  myaction.sa_handler = usr1handler;
  myaction.sa_flags = 0;
  // block everything while handler is running
  if (sigfillset(&myaction.sa_mask) < 0)
    DieWithError("sigfillset");

  /* third argument is NULL b/c we don't care about previous disposition */
  if (sigaction(sig,&myaction,NULL) < 0)
    DieWithError("sigaction");

}

/* This is the third fnction asked for in Project 5. wait for eventdone to become true.
 * Precondition: handler already established */
void await_event(void) {
  sigset_t blockmask, oldmask;

  // create a mask containing all signals
  if ((sigfillset(&blockmask) < 0))
    DieWithError("sigfillset");

  // block everything - blunt instrument, but simpler
  if (sigprocmask(SIG_BLOCK,&blockmask,&oldmask) < 0)
    DieWithError("sigprocmask");

  /* SIGUSR1 (and everything) is now blocked - no race condition */
  while (!eventdone)  // handler has not run yet
    sigsuspend(&oldmask); // unblock everything and pause, all at once.
  /* SIGUSR1 was handled */
  // restore the original set of blocked signals
  if (sigprocmask(SIG_SETMASK,&oldmask,NULL) < 0)
    DieWithError("sigprocmask (after loop)");
}
