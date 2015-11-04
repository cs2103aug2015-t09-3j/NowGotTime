# About

NowGotTime is the perfect app for computing students. It designed by combining the good parts of both command line interface and graphical user interface. This app can help you to keep track of deadlines due to your heavy workload.

# Table of Content

+ [Getting Started](#get-started)
  + [Interface](#interface)
  + [Setting your local directory](#setting-your-local-directory)
+ [Event](#event)
  + [Adding an event](#adding-an-event)
  + [Viewing an event](#viewing-an-event)
  + [Editing an event](#editing-an-event)
  + [Deleting an event](#deleting-an-event)
+ [Floating Event](#floating-event)
  + [Adding a floating event](#adding-a-floating-event)
  + [Editing a floating event](#editing-a-floating-event)
  + [Deleting a floating event](#deleting-a-floating-event)
  + [Checking/Marking a floating event](#deleting-a-floating-event)
+ [Switching Event Types](#switching-event-types)
  + [Timed event to floating event](#timed-event-to-floating-event)
  + [Floating event to timed event](#floating-event-to-timed-event)
+ [Projects](#projects)
  + [Creating a new project](#creating-a-new-project)
  + [Add an event to existing project](#add-an-event-to-existing-project)
  + [Viewing an existing project timeline](#viewing-an-existing-project-timeline)
  + [Viewing more details](#viewing-more-details)
  + [Editing additional details and deleting](#editing-additional-details-and-deleting)


# Getting Started
## Interface
![Mockup](manual/assets/interface/mockup.png)
NowGotTime have 4 different views :

+ Command Line
+ Todo List
+ Timeline View
+ Calender View


## Setting your local directory
The first time starting up NowGotTime, save file window will pop up to let you choose local directory for NowGotTime's save directory.

![Save](manual/assets/interface/savefile.png)

# Event
## Adding an event
### Step 1
To add a new event, use the command `add` follow by the name of the event you are entering.

![Step 1](manual/assets/event/add1.png)

### Step 2
The command console will prompt you for the date, enter the date of the event (if any). You can leave the date section empty, this event will then be considered a floating event.

![Step 2](manual/assets/event/add2.png)

### Step 3
The Timeline and To-do-List for that day will pop up, and the command console will prompt you for the time. Fill up the time, if any. The time field can be empty, this will create a floating event only for that date you have entered.

![Step 3](manual/assets/event/add3.png)

### Step 4
Enter any additional information or note of the event. You can leave this space empty.

![Step 4](manual/assets/event/add4.png)

### Step 5
After entering the additional information. The program will now send you a confirmation message, and now you will be able to view your event/task on the timeline view.

![Step 5](manual/assets/event/add5.png)

## Viewing an event
### Step 1
Enter the command `go` follow by a date to go to that specific date.

![Step 1](manual/assets/event/view1.png)

### Step 2
You will now be able to see the timeline and to-do-list for that date. Note the numbering of the events on the timeline view. This numbers are IDs given to the event for easy selection.

![Step 2](manual/assets/event/view2.png)

### Step 3
If you want to select “TaskName” event, we can simply use the `slt` command follow by `3` to select.

![Step 3](manual/assets/event/view3.png)

### Step 4
The information about the event/task will be shown in the box on the right.

![Step 4](manual/assets/event/view4.png)

## Editing an event
### Step 1
Follow the “View an Event” guide to view an event you want to change. Use the `edt` command followed by the ID numbering to select the section you want to edit. In this case, we are editing the “Additional information”, so you enter `edt 3`.

![Step 1](manual/assets/event/edit1.png)

### Step 2
You will be prompted to enter a new additional information or note.

![Step 2](manual/assets/event/edit2.png)

### Step 3
Once you have entered the new additional information, you will receive the confirmation message and the changes will be saved and updated.

![Step 3](manual/assets/event/edit3.png)
## Deleting an event
### Step 1
Go to a specific date, and use the `dlt` command followed by the ID of the event you want to delete.

![Step 1](manual/assets/event/delete1.png)

### Step 2
A confirmation message will be printed and the event will be deleted.

![Step 2](manual/assets/event/delete2.png)


# Floating Event
## Adding a floating event 
### Step 1
To add a new event, use the command add follow by the name of the event you are entering

![Step 1](manual/assets/event/addfloat1.png)

### Step 2
The command console will prompt you for the date, enter the date of the event (if any). You can leave the date section empty, this event will then be considered a floating event.

![Step 2](manual/assets/event/addfloat2.png)

## Editing a floating event
### Step 1
Use the v command to show the To-Do-List. Use the edt command followed by the ID numbering to select the section you want to edit. 

![Step 1](manual/assets/event/editfloat1.png)

### Step 2
Use the v command to show the To-Do-List. Use the edt command followed by the ID numbering to select the section you want to edit. 

![Step 2](manual/assets/event/editfloat2.png)

### Step 3
Once you have entered the new additional information, you will receive the confirmation message and the changes will be saved and updated.

![Step 3](manual/assets/event/editfloat3.png)

## Deleting a floating event
### Step 1
Use the v command to show the To-Do-List. Use the dlt command followed by the ID of the event you want to delete

![Step 1](manual/assets/event/deletefloat1.png)

### Step 2
A confirmation message will be printed and the event will be deleted.

![Step 2](manual/assets/event/deletefloat2.png)


## Checking/Marking a floating event
### Step 1
Use the v command to show the To-Do-List. Use the chk command followed by the ID of the event you want to mark as completed.

![Step 1](manual/assets/event/checkfloat1.png)

### Step 2
A confirmation message will be printed and the task will now be ticked.
![Step 2](manual/assets/event/checkfloat2.png)


# Switching Event Types
## Timed event to floating event
### Step 1
View an event details. (Refer to View an Event)

![Step 1](manual/assets/event/switchtf1.png)

### Step 2
Select the ID `6` which is the toggle function.

![Step 2](manual/assets/event/switchtf2.png)

### Step 3
The command console will prompt the user to double confirm. Enter `y` to confirm.

![Step 3](manual/assets/event/switchtf3.png)

### Step 4
A confirmation message will be printed and the task will now be a floating event.

![Step 4](manual/assets/event/switchtf4.png)

## Floating event to timed event
### Step 1
Use the `v` command to show the To-Do-List.

![Step 1](manual/assets/event/switchtt1.png)

### Step 2
Use the `slt` command followed by the ID to select the task you want to change.

![Step 2](manual/assets/event/switchtt2.png)

### Step 3
You will now see the details of the task to the right.

![Step 3](manual/assets/event/switchtt3.png)

### Step 4
Use `slt 5` to select the “Toggle”. This is the option to switch event type. A confirmation message will appear, enter `y` to confirm.

![Step 4](manual/assets/event/switchtt4.png)

### Step 5
Once you enter the date, the timeline view will pop up, and you will be able to see your schedule for that day.

![Step 5](manual/assets/event/switchtt5.png)

### Step 6
Enter the time and the changes will be saved. The event/task can now be seen on the timeline.

![Step 6](manual/assets/event/switchtt6.png)


#Projects
## Creating a new project
To create a new project, use the command `cprj` follow by the name of the project you want to create. 

![Creating a new project](manual/assets/event/CP.png)


## Add an event to existing project
To add an event to an existing project, use the command `go` followed by the date of the event to access it. The timeline and the to-do-list for that day would be seen.

Type the command `slt` followed by the event number and `adt` followed by the project name to add the selected event to the project you want.

![Add event to project](manual/assets/event/AddEvToP.png)

## Viewing an existing project timeline
To view a project timeline, use the command `v` followed by the name of the project you want to view. The timeline appears.

![Viewing a project](manual/assets/event/ViewProjectTimeline.png)

## Viewing more details
To view more details of the events listed in the timeline, simply use the command `vd`. Additional information will appear as shown.

![Viewing a project](manual/assets/event/ViewMoreDetails.png)

## Editing additional details and deleting
To edit or add additional information to the event in the project timeline, use the command `slt` followed by the event number to select it. 

Then, type the command `edt` followed by the additional information you wish to add. The details added or edited would appear in the timeline.

To delete, simply type `del` followed by the event number of the event you wish to delete.

![Viewing a project](manual/assets/event/EditDetailsAndDel.png)

# Cheatsheet

## Basic commands
### Adding
Command | Description | Status 
--------| ----------- | -------
`add "<EVENT>" on <START> to <END>` | add an event with name `<EVENT>`, start date `<START>` and end date `<END>` | ✅
`add "<TODO>" on <DEADLINE>` | add a todo with name `<TODO>` and deadline `<DEADLINE>` | ✅ | 
`add "<TODO>"` | add a floating todo with name `<TODO>` | ✅ | 
### Deleting
Command | Description | Status 
--------| ----------- | -------
`delete <INDEX>` | delete an item with index | ✅ |
`delete "<KEYWORD>"` | delete an item with keyword `<KEYWORD>` | ✅ |
### Editing
Command | Description | Status 
--------| ----------- | -------
`edit <INDEX> name "<NAME>"` | edit name of an item with index `<INDEX>` | ✅ |
`edit <INDEX> start <START>` | edit start date/time/both of an item with index `<INDEX>` | ✅ |
`edit <INDEX> end <END>` | edit end date/time/both of an item with index `<INDEX>` | ✅ |
`edit <INDEX> due <DEADLINE>` | edit deadline date/time/both of an item with index `<INDEX>` | ✅ |
`edit "<KEYWORD>" name "<NAME>"` | edit name of an item with keyword `<KEYWORD>` | ✅ |
`edit "<KEYWORD>" start <START>` | edit start date/time/both of an item with keyword `<KEYWORD>` | ✅ |
`edit "<KEYWORD>" end <END>` | edit end date/time/both of an item with keyword `<KEYWORD>` | ✅ |
`edit "<KEYWORD>" due <DEADLINE>` | edit deadline date/time/both of an item with keyword `<KEYWORD>` | ✅ |
### Checking and Unchecking
Command | Description | Status 
--------| ----------- | -------
`check <INDEX>` | mark done item with index `<INDEX>` | ✅ |
`check "<KEYWORD>"` | mark done item with keyword `<KEYWORD>` | ✅ |
`uncheck <INDEX>` | mark undone item with index `<INDEX>` | ✅ |
`uncheck "<KEYWORD>"` | mark undone item with keyword `<KEYWORD>` | ✅ |

### Viewing
Command | Description | Status 
--------| ----------- | -------
`search "<KEYWORD>"` | show items with keyword <KEYWORD> | ✅ |
`view` | show today events and todos | ✅ |
`view <DATE>` | show events and todos that occurs on `<DATE>` | ✅ |

### Others
Command | Description | Status 
--------| ----------- | -------
`undo` | undo last add, delete, or edit command | ✅ | 
`set "<DIRECTORY>"` | change save directory to <DIRECTORY> | ✅ |
`exit` | exit NowGotTime | ✅ |

## Project Commands
### Adding
Command | Description | Status 
--------| ----------- | -------
`add project "<PROJECT>"` | add a project with name `<PROJECT>` | ✅ |
`add "<EVENT>" to "<PROJECT>"` | add `<EVENT>` to `<PROJECT>` | ✅ |
`add progress <INDEX> "<PROGRESS>"` | add progress to project | ✅ |

### Deleting
Command | Description | Status 
--------| ----------- | -------
`delete project "<PROJECT>"` | delete a project with name `<PROJECT>` | ✅ |
`delete <INDEX> from project` | delete an event from project with name `<PROJECT>` | ✅ |
`delete progress <INDEX>` | delete progress from index | ✅ |

### Editing
Command | Description | Status 
--------| ----------- | -------
`edit project "<PROJECT>" name "<NAME>"` | edit name of a project | ✅ | 

### Viewing
Command | Description | Status 
--------| ----------- | -------
`view "<PROJECT>"` | show events of `<PROJECT>` | ✅ |


 