Endpoints:

GET /events

Description: Retrieve a list of available events (venues, conferences, concerts, etc.).
Example: GET https://eventbookingservice.com/events
GET /events/{eventId}

Description: Get details of a specific event by its ID.
Example: GET https://eventbookingservice.com/events/12345
POST /events/{eventId}/bookings

Description: Initiate a booking for a specific event.
Example: POST https://eventbookingservice.com/events/12345/bookings
Request Body: Contains details such as user information, number of tickets, seat preferences (if applicable), etc.
GET /bookings/{bookingId}

Description: Retrieve details of a specific booking by its ID.
Example: GET https://eventbookingservice.com/bookings/67890
PUT /bookings/{bookingId}

Description: Update a booking (e.g., change number of tickets, seats).
Example: PUT https://eventbookingservice.com/bookings/67890
Request Body: Contains updated details for the booking.
DELETE /bookings/{bookingId}

Description: Cancel a booking by its ID.
Example: DELETE https://eventbookingservice.com/bookings/67890