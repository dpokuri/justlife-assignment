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
| :------- | :------: | -------: |
| 1 | Availability check by date | Implemented |
| 2 | Availability check by date, start_time | Implemented |
| 3 | Booking using service, date, start_time, duration and professional_count | Implemented |
| 4 | Update professional availability after booking confirmed | Implemented |
| 5 | Update booking by changing date and start_time | Implemented |
| 6 | Update booking by changing only start_time | Implemented |
