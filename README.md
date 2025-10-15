# Gas Booking System

## Overview
This project is a complete desktop application for managing gas cylinder bookings and related services, built using Java Swing and connected to a MySQL database. It supports both user and admin roles, providing comprehensive booking, update, management, and registration functionality[file:6][file:7][file:8][file:11].

## Features

- **User Registration and Login:** Secure sign-up and login for both users and admins.
- **Gas Services:** Users can select services such as pipeline changing, cylinder changing, servicing, new connection, and regulator exchange[file:5].
- **Booking:** Users can book cylinders specifying agency, quantity, type, address, price, and receive automated email confirmation[file:12][file:4].
- **View Bookings:** Users and admins can view all bookings in a table with filters and details[file:14][file:13].
- **Update Booking:** Admins can update booking details and delivery dates[file:13].
- **Manage Users:** Admins can view, manage, and delete users from the system[file:11].
- **Admin Dashboard:** Admins get a panel to manage bookings and users and logout securely[file:7].

## Technologies Used

- **Java Swing** (GUI)
- **JDBC** (Database connectivity)
- **MySQL** (Backend database)
- **EmailService** for automated notifications
- Java.util, AWT, SQL, Calendar, TableModel

## How the System Works

- **Users:** Sign up and login, book cylinders, select extra services, view booking history, update/cancel bookings.
- **Admins:** Register and login as admin, view all bookings, manage all registered users, update bookings, and oversee cancellation requests.
- **Email Notification:** Users receive confirmation and updates automatically via email after booking or registration.

## Running the Project

1. Clone or download the project.
2. Ensure MySQL database `gasbooking` is running and connection parameters are set (`root`/`soham28`).
3. Compile all `.java` files and run `Main.java` or relevant class.
4. For email functionality, configure SMTP details in `EmailService.java`.

## Author

- **Name:** Soham Kachale (example, update with your info)
- **Role:** Java Swing Developer
- **Contact:** sohamkachale7@gmail.com
- **Location:** Pune, Maharashtra
- **GitHub:** https://github.com/sohamkachale

---

Feel free to copy this content to your README.md file. It summarizes all classes, features, and usage from your project[file:5][file:6][file:7][file:8][file:9][file:10][file:11][file:12][file:13][file:14].
