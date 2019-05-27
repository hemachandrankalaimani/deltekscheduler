# deltekscheduler

1. Each person/meeting will have a uniqueID. On creating a person or meeting, a ID will be assigned to this. Any request to get the details of the object should be using this ID.
2. A meeting can only be in the future.
3. To create the meeting, a list of people and timeslot start and end in timestamp milliseconds are needed.
4. I have used two third party libraries - Jacksonjson - for converting to and from json and jerseytests - to test the API resources.

Please find below the samples to call the API using curl for the set of tasks, considering the application is deployed at http://localhost:8080/


1) Create persons with a name and unique email. -  Create a person with an email returns the person object with an ID



              curl -d '{"firstName":"H","lastName":"H","email":"hk@doc.co.uk"}' -H "Content-Type: application/json" -X POST http://localhost:8080/person/create



              Response : {"id":1,"firstName":"H","lastName":"H","email":"hk@doc.co.uk"}



2) Create meetings involving one or more persons at a given time slot. - Create a meeting for that person with a timeslsot and it gives a meeting and the people attending the meeting



              curl -d '{"startTimeInMilliSeconds":1558962000000,"endTimeInMilliSeconds":1558965600000,"personsInMeeting": [ { "id": 1} ]}' -H "Content-Type: application/json" -X POST http://localhost:8080/meeting/create



             Response : {{"id":1,"startTimeInMilliSeconds":1558962000000,"endTimeInMilliSeconds":1558965600000,"personsInMeeting":[{"id":1,"firstName":"H","lastName":"H","email":"hk@doc.co.uk"}]}



4) Show the schedule, i.e., the upcoming meetings, for a given person. - Gives a list of meetings of the person ID along with all the persons attending the meeting



            curl http:/localhost:8080/person/1/meetings



           Response : {"meetings":[{"id":1,"startTimeInMilliSeconds":1558962000000,"endTimeInMilliSeconds":1558965600000,"personsInMeeting":[{"id":1,"firstName":"H","lastName":"H","email":"hk@doc.co.uk"}]}]}



5) Suggest one or more available timeslots for meetings given a group of persons - Given a person or a group, it suggests a meeting so that it does not clash with the passed person's meetings. The number of suggestions needed can be set in the url, in this case I have chosen 3.



curl -d '[{"id":1}]' -H "Content-Type: application/json" -X POST http://localhost:8080/meeting/suggestMeetings/3



           Response: {"meetings":[{"id":null,"startTimeInMilliSeconds":1558958400000,"endTimeInMilliSeconds":1558962000000,"personsInMeeting":null},{"id":null,"startTimeInMilliSeconds":1558965600000,"endTimeInMilliSeconds":1558969200000,"personsInMeeting":null},{"id":null,"startTimeInMilliSeconds":1558969200000,"endTimeInMilliSeconds":1558972800000,"personsInMeeting":null}]}

