# Agency-Center

This project was created as part of the Object Oriented Programming course, and is focused on parallel programming using threads.
this is a simulation program for an agency center, which manages incoming calls, assigns tasks to the workers, and maintains a database of clients and their information.

### How to use the program:

1. Run the GUI.java file to open the GUI window.
2. Enter the number of operators working time, extra number of executives you want to create (added to the base number of 5 executives), and the number of operations for   the day in the respective fields.
3. Click on the "START" button to start the simulation.
4. To stop the simulation at any time, click on the "ABORT" button.

The simulation includes the following components:
* Call queue - All incoming calls are stored in this queue.
* Task queue - All tasks to be performed by the workers are stored in this queue.
* Answering machine - This object reads incoming calls from a file and adds them to the call queue.
* Secretary - There are 5 secretary threads, each of which retrieves calls from the call queue and creates tasks for the workers.
* Task Manager - There are 3 task manager threads, each of which retrieves tasks from the task queue and assigns them to available workers.
* Operator - There are several operator threads, each of which performs a task assigned by a task manager.
* Executive - This object assists the agency manager by performing tasks that require higher authority.
* Agency Manager - This object manages the operations of the agency center, including assigning tasks to workers and keeping track of the number of tasks completed.
* Information System - This object maintains a database of clients and their information.

Note: The number of operator and task manager threads can be adjusted in the AgencyManager constructor in the GUI.java file.
