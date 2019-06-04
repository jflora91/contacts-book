# Contacts Book

## Overview

Global contact list, were people can easily identify the contact behind a phone number.

Clients will be able to know the number that is calling them without having them in their personal contact list.

In addition, our clients will be able to create a safe backup of their contacts on our platform, losing their phones will no longer imply to lose all of your contacts.

---

## Functionalities

1. List all available contacts

An entity when invoking this endpoint receive a list of contacts.

2. List all contacts that match a phone number

An entity when invoking this endpoint, provide a phone number and receive a list of contacts matching the phone number. The result should be a list because more than one user can have the same contact with different names.

3. List all the platform users

An entity when invoking this should receive a list of registered users on the service. It should be possible to list users by registration date, update date, First and last name.

4. List all users contacts

An entity when invoking this endpoint provide the user identification and receive a list of contacts that are associated with that user.

5. Add a user to the platform

An entity when invoking this endpoint provide the new user information and receive in return the newly created user.

The user information contain:

* First Name
* Last Name

6. Update user information

An entity when invoking this endpoint provide the user identification and the complete user information, in result it will receive the updated user.

The updated user will reflect all the provided information.

7. The user will also have a contact entry that represents their number.
All users have also a contact, the service is able to provide that information.

8. Add a contact to a user

An entity when invoking this endpoint provide the user identification and the new contact information, in return it will receive the newly created contact.

A contact have:

* First Name
* Last Name
* Contact Number
    
The same contact is able to have more than one contact number.

9. Update a user contact

An entity when invoking this endpoint provide the user identification, contact identification and the complete updated contact, in return it will receive the updated contact.

10. <span style="color:red"> Access Authorization </span>

Only authorized entities should be able to use this service, the service only allow access to request with Valid credentials, making usage of Basic Authentication

---

## Documentation

### Data model

* User
* Contact
* Contact_number

---

### Endpoints 

|Paths||||
|:-|:-|:-|:-|:-|
|/users|GET,POST|
|/users/{user_id}|GET,PUT, DELETE|
|/users/{user_id}/contacts|GET, POST|
|/users/{user_id}/contacts/{contact_id}|GET,PUT,DELETE|
|<span style="color:red">/users/{user_id}/contacts/{contact_id}/phone_number</span>|GET
|contacts|GET
|contacts/{contact_id}|GET|
