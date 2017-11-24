## Denis Gallo (194441) | denis.gallo@studenti.unitn.it
My heroku server: https://assignment2-denisgallo.herokuapp.com/sdelab
My github repositories: 
https://github.com/DenisGallo/introsde-2017-assignment-2-server
https://github.com/DenisGallo/introsde-2017-assignment-2-client
I worked with Mattia Buffa.
Heroku server: https://server-as2.herokuapp.com/as2
His github repositories:
https://github.com/CommanderBuffin/introsde-2017-assignment-2-server
https://github.com/CommanderBuffin/introsde-2017-assignment-2-client

## Project

### Description of the code
The package of the server project is the same as the lab7 application because i opened that folder to actually implement the tasks. So it is basically **introsde.rest.ehealth**. In the main folder of the package there are the 2 application classes to run the project locally on a standalone http server. In the **dao** package there is the good-old LifeCoachDao, who keeps the connections and persistence alive. The database operations were not implement here but in the model classes. All basic database operations were implemented for every model (even if the names in the code might be copy-pasted and not very beautyful): getAll(), save(), update(), remove().
In the **model** package 3 models were created to run this assignment: person, activity and activity type. One person can have more activites and one activitytype can have more activities.
In **resources** there are 2 classes for each main models: PersonCollectionResource and PersonResource for more people or a single person, ActivityTypeCollectionResource and ActivityTypeResource for more activitytypes or a single activitytype. This classes handle the GET/POST/PUT/DELETE operations on the various requests. Only the specified tasks were implemented and tested, so it's pretty easy to break the server by sending requests other than those.
The database used is in the main folder of the project, named **people.sqlite**.

### Description of the tasks
The server is able to execute all the 11 (+ init) requests specified on the assignment website.
Request 0: to create and initialize the database you just need to /init (.../sdelab/init).
Request 1/2/3/5: simple get requests on /person or /person/id, put on /person/id or delete on /person/id, everyone callable with XML or JSON
Request 4: When doing a POST please send an id for the person (and every activity) you are creating, it's not autogenerated.
Request 6: simple get on /activity_types.
Request 7/8: pretty similar gets, one is returning a list of activities, the other a single activity
Request 9: hard understandable request to be honest. I decided to implement the creation of a new activity with a specified activity_type, this activity will be show as the first activity of a person, moving the other activities below this one
Request 10: simple PUT to modify the activitytype of an activity
Request 11: simple GET with query parameters (strings in my application are in the format YYYY-MM-DD-hh:mm or YYYY-MM-DD for birthdates)


## Execution
The server is currently running on heroku, PLEASE do a https://assignment2-denisgallo.herokuapp.com/sdelab/person . It seems that starting with a /init after a while breaks completely the application. So even if you want to run the client on the asleep server, remember to do a /person first. If the server breaks, just contact me and i restart the dynos (fixing the problem).

## Additional Notes
As already mentioned, when not specified please add an id for everything you are creating (person and activity). Some requests were hard to understand and maybe i got a different interpretion on what i should do, you can ask me if something is really not working because all of my tests went fine on the server and the client of my partner is running perfectly.