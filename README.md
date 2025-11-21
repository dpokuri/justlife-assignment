# justlife-assignment
## Instuctions to run the project.
1. Clone the project into environment wherever you want to run and test
2. Pass database(Postgresql) credentials in the environment variable of the server/machine/IDE
3. Just run as java application or create Jar and run as per your convinient

## Assumptions.
1. For the sake of assigment we can implement all the use cases in a spring boot application.
2. system should be able to handle multiple services and also a professional can handle more than one service
3. The Professional availability will be updated everyday through schedulor / updated in the professional_availability table
4. The vehicle availability also will be updated time to time
5. The vehicle - professional mapping also will be updated automatically
6. Customer will be providing only service, date/start_time and duration to book the appointment
7. Customer will be providing date and start_time to update the booked appointment. Then system should be able to handle both date and start_time change
   

## Use cases covered
| S.NO | Use Case | Status |
| :------- | :------ | :------- |
| 1 | Availability check by date | Covered |
| 2 | Availability check by date, start_time | Covered |
| 3 | Booking using service, date, start_time, duration and professional_count | Covered |
| 4 | Update professional availability after booking confirmed | Covered |
| 5 | Update booking by changing date and start_time | Covered |
| 6 | Update booking by changing only start_time | Covered |
| 7 | If single professional is not available to cover the entire duration then multiple professional can contribute to same duration like 1/2 hours by each professional| Not Covered |
| 8 | Customer can choose available slots from one more professionals and system use that information and book the appointment. This is a kind of customer preferences| Not Covered |
| 9 | Merging multiple hops into a single booking| Not Covered |


## Tasks Status
| S.NO | Task | Status | Comments |
| :------- | :---------- | :------- | :-------|
| 1 | The project should be written with Java (with Spring Boot) programming language | Completed |
| 2 | Create a Restful API by applying proper design patterns | Completed |
| 3 | Functional and unit tests should be written. | Completed |
| 4 | API documentation should be created automatically | Completed | Integrated Swagger |
| 5 | All data should be saved in MySQL, PostgreSQL, or any relational database | Completed |
| 6 | Implementing booking requests with multiple cleaner professionals is a huge plus | Completed | Also planning to cover the use case where single duration can be shared by multiple professionals |
| 7 | Exception handling | In Progress | Added basic but wanted to improve further|
| 8 | Optimize the code further to improve the performance | In Progress |
| 9 | Create proper Tech design doc| In Progress |
| 10 | Implement schedulors| In Progress |
| 11 | Enhance the capabilities of the system by covering various corner case| In Progress |
| 12 | High Level Architecture diagram| In Progress |
| 13 | Database Schema design to the doc| In Progress |Schema design is ready but I will have to add to the doc|
