# MailClient

Final project for TSystems Java School: Java application imitating work of german email. 

Entities:

- Letter (from, to, date, subject, body, address, unique address, date of creation, owner)

- User (first name, last name, date of birth, phone number)

- Folder (folder name, address, letters)

Functionality:

- User should be able to register, send and receive mails, delete mail, create/rename/delete folders, 
move letters from one folder to another, proper error processing (wrong address etc)

Result: 
- Multiclient client-server application with network connection. All data is stored on the server side.
Each client can load some data and change it. All changes should be synchronized with the server.

Used technologies:

- Glassfish

- MySQL

- Maven

- JPA

- Spring Framework

- JavaMail
