Contacts Book
Overview
In today's age with ever-changing information, having a reliable record of information is a daunting task.

Looking only at the people contact information, people are constantly changing contact numbers or they are contacted by the number they don't recognize.

With our service, we intend to make available a global contact list, were people can easily identify the contact behind a phone number.

So each of our clients will be able to know the number that is calling them without having them in their personal contact list.

In addition, our clients will be able to create a safe backup of their contacts on our platform, losing their phones will no longer imply to lose all of your contacts.

Requirements
The service will offer REST endpoints that will offer the following functionalities:

1. List all available contacts
An entity when invoking this endpoint should receive a list of contacts.

2. List all contacts that match a phone number
An entity when invoking this endpoint, should provide a phone number and receive a list of contacts matching the phone number. The result should be a list because more than one user can have the same contact with different names.

3. List all the platform users
An entity when invoking this should receive a list of registered users on the service. It should be possible to list users by registration date, update date, First and last name.

4. List all users contacts
An entity when invoking this endpoint should provide the user identification and receive a list of contacts that are associated with that user.

5. Add a user to the platform
An entity when invoking this endpoint must provide the new user information and receive in return the newly created user.

The user information should contain at least:

First Name
Last Name
6. Update user information
An entity when invoking this endpoint should provide the user identification and the complete user information, in result it will receive the updated user.

The updated user will reflect all the provided information.

6. The user will also have a contact entry that represents their number
All users have also a contact, the service should be able to provide that information

7. Add a contact to a user
An entity when invoking this endpoint should provide the user identification and the new contact information, in return it will receive the newly created contact.

A contact should have at least:

First Name
Last Name
Contact Number
The same contact should be able to have more than one contact number.

7. Update a user contact
An entity when invoking this endpoint should provide the user identification, contact identification and the complete updated contact, in return it will receive the updated contact.

8. Access Authorization
Only authorized entities should be able to use this service, the service should only allow access to request with Valid credentials, making usage of Basic Authentication

Quality
The code for this service should guarantee the requirements are working correctly. In addition, the data model for this service should also be under version control.
 
 






