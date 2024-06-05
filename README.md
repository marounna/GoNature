##Centralized Park Managing System

###This project is built with a FullStack approach. We have a remote server 
managing a database called GoNature. We also have few types of clients that can connect: Clients, Park employees, Service employees, Guides, Park managers and Department manager.

Following are the operations each can perform:

At the beginning you need to click on the import data button on the server's screen.

Clients: 

  •	Logs in the system with ID.
  
  •	Can watch theirs old reservations, make new reservations, get into waiting list (if there is no available space for the amount of visitors on the new reservation). 
  
  •	Can update or cancel existing reservations.

Guides: 

  At first, guide users does not defined on DB as guide user. 
  
  •	Need to call service employee and give him guide ID so he can approve it. Logs in the system with user ID and password. 
  
  •	Can watch theirs old reservations, make new reservations, get into waiting list (if there is no available space for the amount of visitors on the new reservation). 
  • Can update or cancel existing reservations.

Park employees: 

  •	Logs in the system with user ID and password. 
  
  •	Can check the amount of visitors that on the park at any time he want. 
  
  •	Can make new reservation for client or guide (group reservation). 
  
  •	Can update amount of visitors that have been arrived from the reservation.

Service employees: 

  •	Logs in the system with user ID and password. 
  
  •	Can update the guide user permission to guide type of user so the guide will be able to log in his user.

Park managers: 

  •	Logs in the system with user ID and password. 
  
  •	Can make a request to the department manager about changing dwell time or maximum capacity of the park. 
  
  •	Can make Usage report (shows dates and hours that the park wasn't on full capacity). 
  
  •	Can make Total report(shows the amount of groups visitors and individuals visitors on the selected month).
  

Department manager: 

  •	Logs in the system with user ID and password.
  
  •	Can watch on the park managers dwell time and maximum capacity requests.
  
  •	 Can approve or reject requests. 
  
  •	Can watch Visit report of selected date (shows the amount of groups and individuals visitors and theirs entry time and amount of time they visit on the park). 
  
  •	Can watch Cancellation report (shows the amount of orders cancellation and the reservation date, and its cancellation distribution).

There is an SMS simulation that sends an SMS message to the client when a order has been approved. Also, there is a SMS simulation that send the user a notification 24 hours before the reservation date.

This entire project is built upon an OCSF project, taken from [source]. It utilizes their abstract OCSF Server-Client model and implements our own version.


Here are some screens from the project:

Server screen:

![image](https://github.com/AdarCohen1/GoNature/assets/146540241/f77128e6-abcf-4847-b373-0ea7dc1409ff)

Login screen:

![image](https://github.com/AdarCohen1/GoNature/assets/146540241/560a8537-920d-4c07-9e46-c4dc79268646)

Department manager menu screen:

![image](https://github.com/AdarCohen1/GoNature/assets/146540241/160a9bda-c296-4afe-9577-175ed66d47c0)

![image](https://github.com/AdarCohen1/GoNature/assets/146540241/6a64c20f-edbc-4edd-85ed-d70493b38e05)

Department manager visit report screen:

![image](https://github.com/AdarCohen1/GoNature/assets/146540241/4812b168-d985-4588-a317-86ae637a5034)

Department manager cancellation report screen:

![image](https://github.com/AdarCohen1/GoNature/assets/146540241/6ce02f2a-89bd-442a-88ed-5de5ae07d1a3)

Park manager menu screen:

![image](https://github.com/AdarCohen1/GoNature/assets/146540241/f4615bee-3720-4196-a8e8-069dc4672a73)

Park manager total visitors report screen:

![image](https://github.com/AdarCohen1/GoNature/assets/146540241/9a47932d-eb37-4afa-91e3-65edccba0ca5)

Park manager usage report screen (where there is not red circle means the park was on full capacity at the specific hour):

![image](https://github.com/AdarCohen1/GoNature/assets/146540241/3cc8c629-d2e7-43b9-b2a2-46ab1bfbade7)

Service employee menu screen:

![image](https://github.com/AdarCohen1/GoNature/assets/146540241/052b5ca6-6556-4ff6-b045-a612dcf03cb6)

Service employee define guide screen:

![image](https://github.com/AdarCohen1/GoNature/assets/146540241/edef3905-0a37-4eed-bc94-0e116775be3f)

Park employee menu screen:

![image](https://github.com/AdarCohen1/GoNature/assets/146540241/91144016-33c0-436f-b544-51bffb401927)

Park employee new reservation screen (same for client or guide):

![image](https://github.com/AdarCohen1/GoNature/assets/146540241/1912a285-e19a-49d0-b588-091df760ab87)

Client / Guide menu screen:

![image](https://github.com/AdarCohen1/GoNature/assets/146540241/f8453326-78a4-48bf-844d-c507a7eebd55)

Client / Guide update reservation screen:

![image](https://github.com/AdarCohen1/GoNature/assets/146540241/73a5c475-cc75-4fbc-b840-efae19b4a921)

Client / Guide order details screen (after making new reservation and click confirm for payment):

![image](https://github.com/AdarCohen1/GoNature/assets/146540241/4e71cb90-2e38-4253-a7fe-fc29cbccfcb7)

Client / Guide order details sms simulation screen (after click pay now):

![image](https://github.com/AdarCohen1/GoNature/assets/146540241/6b684f90-91b6-4830-8e8c-8cee244c16b0)


A thing worth noting about this project:

The entire design used a FullStack approach with TCP/IP protocols based on the OCSF implementation. We utilized JavaFX 21 jars and jars for creating Word documents.

