# Gas Booking System

## Overview
A comprehensive desktop application for managing LPG gas cylinder bookings built with Java Swing and MySQL. The system supports user registration, login, booking management, admin controls, and automated email notifications. Designed with a user-friendly GUI and robust database connectivity.

## Features

### User Features
- **User Registration & Login:** Secure signup with personal details (Aadhar, DOB, phone, address) and role-based authentication (User/Admin).
- **Gas Cylinder Booking:** Book cylinders with details like agency, gas type (Domestic/Commercial), quantity, booking date, and delivery date (auto-calculated 3 days ahead).
- **Price Calculation:** Automatic price calculation based on gas type and quantity.
- **View Bookings:** Users can view their booking history with complete details including delivery dates.
- **Update Bookings:** Modify existing booking information including contact, address, gas type, and quantity.
- **Cancel Bookings:** Cancel unwanted bookings through a dedicated interface.
- **Service Page:** Access additional services like pipeline/cylinder changing, servicing, new connections, and regulator exchange.
- **Email Notifications:** Automated email confirmations sent after registration and booking.

### Admin Features
- **Admin Dashboard:** Centralized panel to manage bookings and users.
- **View All Bookings:** Access complete database of all user bookings with filtering and sorting.
- **Manage Users:** View, edit, and delete registered users from the system.
- **Update Bookings:** Admins can modify any booking details and delivery dates.
- **Logout:** Secure session management with logout confirmation.

## Technologies Used
- **Frontend:** Java Swing (JFrame, JPanel, JTable, GridBagLayout)
- **Backend:** JDBC for MySQL database connectivity
- **Database:** MySQL (database: `gasbooking`, credentials: root/soham28)
- **Email Service:** Custom EmailService class for automated notifications
- **Date Handling:** SimpleDateFormat, Calendar API for date calculations

## Database Schema
- **register table:** User/Admin registration details (username, email, phone, Aadhar, DOB, gender, address, password, role)
- **booking table:** Booking details (booking ID, name, contact, agency, address, booking date, delivery date, gas type, quantity, price)

## How to Run
1. Install MySQL and create database `gasbooking` with tables `register` and `booking`.
2. Update database credentials in all Java files if different from `root/soham28`.
3. Add MySQL JDBC driver (e.g., `mysql-connector-java.jar`) to classpath.
4. Compile all `.java` files.
5. Run `Main.java` or `LoginPage.java` to start the application.
6. For email notifications, configure SMTP settings in `EmailService.java`.

## File Structure
- **Main.java:** Application entry point
- **LoginPage.java:** User/Admin login with role-based routing
- **Register.java:** User registration form
- **Registeradmin.java:** Admin registration form
- **Home.java:** User home dashboard with navigation buttons
- **AdminHome.java:** Admin dashboard with management options
- **Booking.java:** Gas cylinder booking form with price calculation
- **BookingInfo.java:** View user-specific bookings
- **adminSee.java:** View all bookings (admin)
- **UpdatedPage.java:** Admin booking update interface
- **updateBooking.java:** User booking update interface
- **cancelpage.java:** Booking cancellation interface
- **ServicePage.java:** Additional gas services selection
- **ManageUser.java:** Admin user management panel
- **EmailService.java:** Email notification handler

## Key Functionalities
- **Automatic Delivery Date:** Calculates delivery date as booking date + 3 days
- **Input Validation:** Form validation for required fields and date format (DD-MM-YYYY)
- **Dynamic Price Updates:** Real-time price calculation based on selected gas type and quantity
- **Session Management:** Username passed across windows to maintain user session
- **Responsive UI:** Modern color scheme (blue header, cream background, orange buttons) with consistent fonts

## Author
- **Name:** Soham Kachale
- **Contact:** sohamkachale7@gmail.com
- **Location:** Pune, Maharashtra
- **GitHub:** https://github.com/sohamkachale

---

This README provides a complete overview of the Gas Booking System including features, technologies, setup instructions, and file structure.
