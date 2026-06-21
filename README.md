# UniVerse Lite — Campus Management System

This project is a comprehensive **Campus Management System** developed as a semester project for the 2nd semester of Computer Science at **COMSATS University Islamabad**.

## 👥 Developers
* **Aleena Mubashar**
* **Aila Siddiqui**

---

## 📖 Project Overview
The Campus Management System is a Java-based application designed to streamline and manage various administrative and academic entities within a university environment. The system utilizes **Object-Oriented Programming (OOP)** principles to handle data for courses, students, facilities, staff, and campus services efficiently.

## ✨ Key Features
* **Academic Management:** Manages Departments, Courses, Classrooms, Labs, and Students.
* **Assignment Handling:** Facilitates the creation, modification, and tracking of assignment deadlines and student submissions.
* **Facility Management:** Tracks operational costs and usage for campus facilities like the Cafeteria, Library, Hostels, and Health Center.
* **Scheduling System:** Automates class scheduling and handles classroom assignments based on availability and capacity.
* **Data Persistence:** Implements file-based storage using **Serialization**, ensuring data is saved and retrieved across sessions.
* **Reporting:** Generates analytical performance reports for academic departments.

## 🖥 Frontend & UI
The application features a graphical user interface (GUI) built using **Java Swing**, designed to provide an intuitive experience for different user roles.

* **Login System:** Secure access control with `LoginFrame`, verifying credentials and permissions before granting system entry.
* **Main Dashboard:** A tabbed interface (`MainFrame`) that organizes functionality:
    * **Students Tab:** Perform CRUD operations on student records and manage course enrollments.
    * **Courses Tab:** Register and manage university courses.
    * **Facilities Tab:** Manage library books, view cafeteria menus, and track hostel occupancy.
    * **Reports Tab:** Generate/view performance metrics for departments and library usage statistics.
    * **Schedules Tab:** Visualize class timetables and campus transport bus schedules via a clear, scrollable text area.

## 🛠 Tech Stack
* **Language:** Java
* **UI Framework:** Java Swing
* **Paradigm:** Object-Oriented Programming (OOP)
* **Concepts Used:** Inheritance, Polymorphism, Encapsulation, Generics, Serialization, and File I/O.

## 📂 Project Structure
* **Package: `Backend`**
    * Contains core business logic, entity models (e.g., `Student`, `Course`, `Library`), and data management classes like `CampusData`, `CampusRepository<T>`, and `FileHandler`.
* **Package: `Frontend`**
    * Contains GUI components (`LoginFrame`, `MainFrame`) and the `Runner` class which initializes the application.

## 🚀 How to Run
1. Ensure you have **Java Development Kit (JDK)** installed on your machine.
2. Clone this repository: `git clone <your-repository-url>`
3. Open the project in your preferred IDE (IntelliJ, Eclipse, or NetBeans).
4. Locate the `Runner` class (within the `Frontend` package) and run the `main` method to launch the application.

## 📝 License
This project is for academic purposes as part of the CS curriculum at COMSATS University Islamabad.
