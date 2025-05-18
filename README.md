# Car sharing app

## Requirements:
Functional (what the system should do):
Web-based
Manage car sharing inventory
Manage car rentals
Manage customers
Display notifications
Handle payments
Non-functional (what the system should deal with):
Support up to 5 concurrent users
Manage up to 1000 cars
Handle 50,000 rentals per year
Approximately 30MB of data per year

## Architecture
![image](https://github.com/user-attachments/assets/340bc79c-1c2d-494c-a09a-0222782eeacd)

## Models
### 1. Car:
 * Model: String
 * Brand: String
 * Type: Enum: SEDAN | SUV | HATCHBACK | UNIVERSAL
 * Inventory (the number of this specific car available for now in the car sharing service): int
 * Daily fee: decimal (in $USD)
### 2. User (Customer):
 * Email: String
 * First name: String
 * Last name: String
 * Password: String
 * Role: Enum: MANAGER | CUSTOMER
### 3. Rental:
 * Rental date: LocalDate
 * Return date: LocalDate
 * Actual return date: LocalDate
 * Car id: Long
 * User id: Long
### 4. Payment:
 * Status: Enum: PENDING | PAID
 * Type: Enum: PAYMENT | FINE
 * Rental id: Long
 * Session url: Url # URL for the payment session with a payment provider
 * Session id: String # ID of the payment session
 * Amount to pay: decimal (in $USD) # calculated rental total price

## Controllers
### 1. Authentication Controller:
 * POST: /register - register a new user
 * POST: /login - get JWT tokens
### 2. Users Controller: Managing authentication and user registration
 * PUT: /users/{id}/role - update user role
 * GET: /users/me - get my profile info
 * PUT/PATCH: /users/me - update profile info
### 3. Cars Controller: Managing car inventory (CRUD for Cars)
 * POST: /cars - add a new car
 * GET: /cars - get a list of cars
 * GET: /cars/ - get car's detailed information
 * PUT/PATCH: /cars/ - update car (also manage inventory)
 * DELETE: /cars/ - delete car
### 4. Rentals Controller: Managing users' car rentals
 * POST: /rentals - add a new rental (decrease car inventory by 1)
 * GET: /rentals/?user_id=...&is_active=... - get rentals by user ID and whether the rental is still active or not
 * GET: /rentals/ - get specific rental
 * POST: /rentals//return - set actual return date (increase car inventory by 1)
### 5. Payments Controller (Stripe): Facilitates payments for car rentals through the platform. Interacts with Stripe API. Use stripe-java library.
 * GET: /payments/?user_id=... - get payments
 * POST: /payments/ - create payment session
 * GET: /payments/success/ - check successful Stripe payments (Endpoint for stripe redirection)
 * GET: /payments/cancel/ - return payment paused message (Endpoint for stripe redirection)
### 6. Notifications Service (Telegram):
 * Notifications about new rentals created, overdue rentals, and successful payments
 * Other services interact with it to send notifications to car sharing service administrators.
 * Uses Telegram API, Telegram Chats, and Bots.
