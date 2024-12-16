# **Multi-Backend Full Stack App**

## **Overview**

This project is a multi-backend full-stack application demonstrating my skills with three different backend technologies:

- **Express.js (Node.js)**
- **Spring Boot (Java)**
- **ASP.NET Core (C#)**

The front end is built with **Vite** and **React Router**, allowing the user to interact with one backend at a time. It showcases multiple features, focusing on **authentication**, dynamic dashboards, and modular functionality.

The app is designed to provide the same user experience regardless of the backend, enabling me to apply consistent functionality across different stacks.

---

## **Tech Stack**

### **Frontend**

- Vite
- React Router
- Tailwind CSS (optional styling)

### **Backends**

- Express.js (Node.js)
- Spring Boot (Java)
- ASP.NET Core (C#)

### **Database**

- PostgreSQL (using Drizzle ORM for the Express backend)

---

## **Features to Implement**

Here is a structured list of features I plan to implement. Each feature will be marked off as it is completed.

### **Core Features**

- **Authentication System**

  - User registration and login (email/password-based)
  - Password recovery with tokens
  - Secure password hashing

- **User Profile Management**

  - View and update profile details (name, avatar, etc.)
  - Change password
  - Upload profile picture

- **TODO CRUD App**

  - Add, edit, delete, and mark TODOs as complete
  - Filter/sort TODOs based on status

- **Real-Time WebSocket Chat**

  - Send and receive messages in real-time
  - Room-based or user-to-user chat functionality

- **Dashboard Analytics and Charts**

  - Display user stats (e.g., last login, tasks completed)
  - Visualize data using charts (Chart.js or Recharts)

- **File Storage and Management**

  - Upload/download files (images, PDFs)
  - Manage files with previews

- **Notifications System**

  - Send notifications for important events (e.g., new messages, TODO status)
  - Allow users to mark notifications as read/unread

- **Multi-Role Access Control**

  - Define roles like `user` and `admin`
  - Admins can view/manage users and permissions

- **Activity Logs/Audit Trail**

  - Log user actions (e.g., logins, CRUD operations)
  - Display activity history to admins

- **External API Integration**

  - Fetch and display data from third-party APIs (e.g., weather, GitHub)

- **Dark Mode & Preferences Management**

  - Toggle dark/light mode
  - Save and persist user preferences

- **Reports Generation**

  - Generate and download reports (PDF/Excel) for activity logs or tasks

---

## **Project Structure**

### **Frontend**

The Vite/React frontend dynamically interacts with the chosen backend through API calls. Tabs and components render different features seamlessly, including real-time updates.

### **Backends**

The app supports three interchangeable backends:

1. **Express.js**: Fast and flexible backend using Drizzle ORM for PostgreSQL.
2. **Spring Boot**: Enterprise-grade Java backend with JPA for database handling.
3. **ASP.NET Core**: C# backend for high performance and scalability.

Each backend implements:

- RESTful APIs
- WebSocket endpoints for real-time features
- Secure authentication

---

## **Setup Instructions**

### **Frontend Setup**

1. Clone the repository:
   ```bash
   git clone https://github.com/maiko26/multi-backend-app.git
   cd multi-backend-app
   ```
2. Install dependencies:

```bash
npm install
```

3. Run the Vite development server:

```bash
Copy code
npm run dev
```

### **Backend Setup**

Each backend is in its respective folder:

- `backend/express` for Node.js
- `backend/springboot` for Java
- `backend/dotnet` for ASP.NET Core

Follow the README instructions in each backend directory to set up and run the API.

---

## **Progress Tracking**

### **Frontend Features**

| Feature                            | Status         |
| ---------------------------------- | -------------- |
| Dark Mode & Preferences Management | ✅ Completed   |
| Authentication Integration         | ✅ Completed   |
| User Profile Page                  | 🔜 Not Started |
| TODO CRUD Interface                | 🔜 Not Started |
| Real-Time WebSocket Chat UI        | 🔜 Not Started |
| Dashboard with Analytics/Charts    | 🔜 Not Started |
| External API Integration           | 🔜 Not Started |
| Notifications UI                   | 🔜 Not Started |

---

### **Backend Features**

| Feature                     | Backend-Express | Backend-SpringBoot | Backend-DotNet |
| --------------------------- | --------------- | ------------------ | -------------- |
| Authentication System       | ✅ Completed    | 🚧 In Progress     | 🚧 In Progress |
| User Profile Management     | 🔜 Not Started  | 🔜 Not Started     | 🔜 Not Started |
| File Storage and Management | 🔜 Not Started  | 🔜 Not Started     | 🔜 Not Started |
| TODO CRUD API               | 🔜 Not Started  | 🔜 Not Started     | 🔜 Not Started |
| Real-Time WebSocket Chat    | 🔜 Not Started  | 🔜 Not Started     | 🔜 Not Started |
| Multi-Role Access Control   | 🔜 Not Started  | 🔜 Not Started     | 🔜 Not Started |
| Notifications System        | 🔜 Not Started  | 🔜 Not Started     | 🔜 Not Started |
| External API Integration    | 🔜 Not Started  | 🔜 Not Started     | 🔜 Not Started |

---

## **Why This Project?**

This app allows me to put everything I’ve learned into practice:

- **Frontend Development** with Vite, React Router, and state management
- **Backend Development** with Express.js, Spring Boot, and ASP.NET Core
- **Database Design** and ORM usage (Drizzle ORM, JPA, etc.)
- **Real-Time Features** using WebSockets
- **Authentication and Security** best practices

It serves as a **portfolio project** demonstrating versatility across tech stacks, RESTful API design, and real-world app features.

---

## **License**

This project is open-source and available under the [MIT License](LICENSE).

---

## **Next Steps**

I’ll be actively developing this project, crossing off features as I go. Stay tuned for updates! 🚀
