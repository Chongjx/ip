# Jay User Guide

## Features 

### \#1: Add a task into the list:
Creates and add a new Todo or Event or Deadline task into the task list.

### Usage

#### Keywords
`todo` - Specifies a Todo task to be added.<br/>
`event` - Specifies an Event task.<br/>
`deadline` - Specifies a Deadline task to be added.

#### Identifiers
`/at` - Specifies the date and time information for the Event.<br/>
`/by` - Specifies the date and time information for the Deadline.

The user first enters the keyword to indicate the type of the task to be added, followed by the task description
. The user is also required to enter the respective identifiers followed by the date and time information for Event and
 Deadline task.

*Note:*<br/>
*The keywords are case-insensitive while the identifiers are case-sensitive. A list of acceptable date and time
 format will be listed below.*

Example of usage: 

`Todo task 1`<br/>
`Event event 2 /at 30 Sep 2020 15.00`<br/>
`Deadline deadline 3 /by 30 09 20 03:00pm`<br/>

Expected outcome:

`Added a todo task [T][✘] task 1!`<br/>
`Now you have 1 task(s) in the list!`

`Added an event [E][✘] event 2 (At: 30 Sep 2020 03:00PM)!`<br/>
`Now you have 2 task(s) in the list!`

`Added a deadline task [D][✘] deadline 3 (By: 30 Sep 2020 03:00PM)!`<br/>
`Now you have 3 task(s) in the list!`

### \#2: List all the tasks:
List all the tasks in the task list, showing their task type, either Todo, event or deadline, done status and
 description. Date and time info will also be displayed for event and deadline tasks. 

### Usage

#### Keyword
`list`

Example of usage:

`list`

Expected outcome:

`Here is your list of task(s):`<br/>
`1.[T][✘] task 1`<br/>
`2.[E][✘] event 2 (At: 30 Sep 2020 03:00PM)`<br/>
`3.[D][✘] deadline 3 (By: 30 Sep 2020 03:00PM)`

### \#3: List all events and deadlines that occur on a specific date:
Find and display all the events and deadlines in the task list that occur on a specific date.

### Usage

#### Keyword
`on`

The user enters the keyword followed by the date.

Example of usage:

`on 30 sep 2020`

Expected outcome:

`Here is the list of event(s) and deadline(s) on 30 Sep 2020:`<br/>
`1.[E][✘] event 2 (At: 30 Sep 2020 03:00PM)`<br/>
`2.[D][✘] deadline 3 (By: 30 Sep 2020 03:00PM)`

### \#4: Mark task as done:
Mark a completed task as done.

### Usage

#### Keyword
`done`

The user enters the keyword followed by the <b>INDEX</b> of the task in the task list.  The <b>INDEX</b> must be a
 positive integer 1, 2, 3, … 

Example of usage:

`done 1`

Expected outcome:

`Completed task 1!`<br/>  
`[T][✓] task 1`

### \#5: Delete a task from the list:
Delete an unwanted task from the task list.

### Usage

#### Keyword
`delete`

The user enters the keyword followed by the <b>INDEX</b> of the task in the task list.  The <b>INDEX</b> must be a
 positive integer 1, 2, 3, … 

Example of usage:

`delete 1`

Expected outcome:

`I have removed the task!`<br/>
`[T][✓] task 1`<br/>
`Now you have 2 task(s) in the list!`

### \#6: List all the tasks that contain the keyword:
Find and list all the tasks that contain the specific keyword.

### Usage

#### Keyword
`find`

The user enters the keyword command followed by the string of the keyword.

*Note:*<br/>
*The string of the keyword is case-sensitive.*

Example of usage:

`find Sep`

Expected outcome:

`Found these task(s) that match the keyword "Sep":`<br/>
`1.[E][✘] event 2 (By: 30 Sep 2020 03:00PM)`<br/>
`2.[D][✘] deadline 3 (At: 30 Sep 2020 03:00AM)`

### \#7: Saving and loading of the list:
Automated saving and loading of the task list to and fro a text file.

### Usage

On start-up, the program tries to load the saved task list from the text file. If the folder or file is not present
, the program will create the folder and file respectively. The task list is automatically saved everytime any
 changes is made.

Expected outcome if no folder or file is found: <br/>
`Looking for existing file...`<br/>
`No saved file found!`

Expected outcome if the saved data is found: <br/>
`Looking for existing file...`<br/>
`Found file, loading saved info...` <br/>
`Successfully loaded saved info!`

### List of acceptable date time format:
Date formats: 
* dd-M-yy
* dd-MM-yy
* dd-MMM-yy
* dd-M-yyyy
* dd-MM-yyyy
* dd-MMM-yyyy
* dd/M/yy
* dd/MM/yy
* dd/MMM/yy
* dd/M/yyyy
* dd/MM/yyyy
* dd/MMM/yyyy
* dd M yy
* dd MM yy
* dd MMM yy
* dd M yyyy
* dd MM yyyy
* dd MMM yyyy

Time formats:
* HH:mm
* HH.mm
* HHmm
* hh:mma
* hh.mma

*Note:*<br/>
*HH represents the time in 24hr format while hh represents the time in 12hr format. The 'a' in the 12hr format
 represents 'AM' or 'PM'.*

Examples:<br/>
`01-JAN-2020 06:00`<br/>
`01/01/20 02:00`<br/>
`01 01 2020 17.00`<br/>
`01-jan-20 05.00pm`