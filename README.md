# HealthHaven

![HealthHaven Logo](src/main/resources/static/images/logo.png)

## Description

HealthHaven is a web-based application designed to streamline the management of doctor appointments. The application provides a seamless experience for both patients and doctors, allowing them to efficiently manage appointments and profiles.

## Technologies Used

- Java 11
- Spring Boot 2
- Spring Data JPA
- Spring Security
- Spring Boot Validation
- MySQL
- Thymeleaf
- jBcrypt
- Bootstrap

## Key Features


- **User Registration and Login**:
  - Users can sign up as either a doctor or a patient.
  - User authentication is implemented using Spring Security.
  - User credentials are securely stored in the database, and passwords are hashed using the BCrypt algorithm for enhanced security.
  - **Registration Success Modal**: After a successful registration, a modal appears confirming the successful registration.

- **Patient Dashboard**:
  - Patients can book appointments with doctors, update their profiles, and view their booked appointments.
  - **Search and Book Doctors**: Patients can search for doctors based on their speciality, and book an appointment with them. The booking process involves selecting a doctor, and selecting a date and time for the appointment. Validation is implemented to ensure the correctness of user inputs during the booking process. If the booking process is successful, a success modal appears.
  - **Viewing Appointments**: Patients can view their upcoming appointments and see the status of each appointment (e.g., pending, confirmed, cancelled).
  - **Updating Profile**: Patients can update their personal information, such as name, email, and phone number. **Profile Update Success Modal**: After successfully updating their profile, a modal appears confirming the successful update.

- **Doctor Dashboard**:
  - Doctors can view their appointments, update the status of appointments, and update their profiles.
  - **Managing Appointments**: Doctors can view all their appointments and update the status of each appointment (e.g., confirm, cancel).
  - **Updating Profile**: Doctors can update their professional information, such as speciality and personal details. **Profile Update Success Modal**: After successfully updating their profile, a modal appears confirming the successful update.

- **Security**:
  - Implemented with Spring Security to ensure that all user data and interactions are secure.
  - Passwords are hashed using the BCrypt algorithm to protect user credentials.

- **Responsive Design**:
  - The application is designed to be responsive, ensuring a seamless experience across different devices, including desktops, tablets, and mobile phones.
    
### Login Page

![Login Page](src/main/resources/static/images/screenshots/login-page.PNG)

### Registration Page

![Registration Page](src/main/resources/static/images/screenshots/sign-up.PNG)
The user can register as either a doctor or a patient.

## Patient Workflow

### Patient Registration Form

![Patient Registration Form](src/main/resources/static/images/screenshots/patient-registration-form.png)

### Registration Success Modal

![Registration Success Modal](src/main/resources/static/images/screenshots/patient-registration-success-modal.PNG)

### Patient Dashboard

![Dashboard](src/main/resources/static/images/screenshots/patient-dashboard.PNG)
The patient can search a doctor based on their speciality, view their appointments or update their profile.

### Patient Dashboard - Search Doctor by Speciality

![Search Dashboard](src/main/resources/static/images/screenshots/search-doctor-by-speciality.PNG)


### Booking Appointment

![Booking Appointment](src/main/resources/static/images/screenshots/book-appointment-form.PNG)

### Booking Appointment Success Modal

![Booking Appointment Success Modal](src/main/resources/static/images/screenshots/book-appointment-success-modal.PNG)

### Patient Appointments

![View Appointments](src/main/resources/static/images/screenshots/patient-appointments.PNG)
The patient can see their current appointments and their status.

### Update Patient Profile

![Update Patient Profile](src/main/resources/static/images/screenshots/patient-update-profile.PNG)

### Update Patient Profile Success Modal

![Update Patient Profile Success Modal](src/main/resources/static/images/screenshots/update-patient-profile-success-modal.PNG)


## Doctor Workflow

### Doctor Registration Form

![Doctor Registration Form](src/main/resources/static/images/screenshots/doctor-registration-form.PNG)

### Registration Success Modal

![Registration Success Modal](src/main/resources/static/images/screenshots/doctor-registration-success-modal.PNG)

### Doctor Dashboard

![Dashboard](src/main/resources/static/images/screenshots/doctors-dashboard.PNG)
The doctor can see their appointments and change their status or update their profile.

### Update Doctor Profile

![Update Doctor Profile](src/main/resources/static/images/screenshots/doctor-update-profile.PNG)

### Update Doctor Profile Success Modal

![Update Doctor Profile Success Modal](src/main/resources/static/images/screenshots/update-doctor-profile-success-modal.PNG)
