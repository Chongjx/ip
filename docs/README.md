# Jay User Guide

## Features 

### Feature 1 
Adding of a new Todo or Event or Deadline task.

## Usage

### Keywords
#### `todo` - Specifies a Todo task to be added.
#### `event` - Specifies an Event task.
#### `deadline` - Specifies a Deadline task to be added.

### Identifiers
#### `/at` - Specifies the date and time information for the Event.
#### `/by` - Specifies the date and time information for the Deadline

The user first enters the keyword to indicate the type of the task to be added, followed by the task description
. The user is also required to enter the date and time information for Event and Deadline task after the respective
  identifiers.

*Note:*<br/>
*The keywords are case-insensitive while the identifiers are case-sensitive. A list of acceptable date and time
 format will be listed below.*

Example of usage: 

`Todo task 1`<br/>
`Event event 2 /at 30 Sep 2020 15.00`<br/>
`Deadline deadline 3 /by 30 09 20 03:00pm`<br/>

Expected outcome:

`Added a todo task [T][✘] task 1!` <br/>
> `Now you have n task(s) in the list!`

`Added a an event [E][✘] event 2 (At: 30 Sep 2020 03:00PM)!` <br/>
> `Now you have n task(s) in the list!`

`Added a deadline task [D][✘] deadline 3 (By: 30 Sep 2020 03:00PM)!`
>`Now you have n task(s) in the list!`

### Feature 2
Listing all the tasks in the task list.

## Usage

### Keywords