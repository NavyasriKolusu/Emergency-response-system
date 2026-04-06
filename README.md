#  Emergency Response System

##  Overview

This project is a full-stack Emergency Management System designed to handle emergency requests in a structured and reliable way. The system not only allows users to create and manage emergencies but also ensures that no request is ignored through automated escalation logic.

The focus of this project is on **workflow management, priority-based handling, and automation**.

---

##  Tech Stack

### Frontend

* React (UI development)
* Axios (API communication)
* CSS (styling)

### Backend

* Spring Boot (REST API development)
* Java (core logic)

### Database

* MySQL (data storage)
* Spring Data JPA / Hibernate (ORM)

---

##  Core Implementation

###  1. State-Based Workflow Management

Each emergency follows a strict lifecycle:

PENDING → ACCEPTED → RESOLVED

* **PENDING:** Newly created emergency waiting for response
* **ACCEPTED:** A responder has taken responsibility
* **RESOLVED:** Emergency has been successfully handled

This controlled state transition ensures:

* Proper tracking of every request
* No invalid or skipped states
* Clear visibility of system status

---

###  2. Time-Based Escalation Logic (Key Feature)

The most important logic in this system is **automatic escalation**.

#### How it works:

1. When an emergency is created, a timer is triggered
2. The wait time is determined based on priority
3. After the delay, the system checks the current status
4. If the status is still **PENDING**, the emergency is marked as **escalated**

#### Implementation:

* Uses background thread execution (`Thread.sleep`)
* Runs asynchronously without blocking the main request

#### Purpose:

* Ensures no emergency is ignored
* Improves response reliability
* Converts system from reactive → proactive

---

###  3. Priority-Based Logic

Priority is dynamically assigned based on the **type of emergency**:

* **HIGH Priority**
  * fire
  * accident

* **MEDIUM Priority**
  * blood

* **LOW Priority**
  * other/custom types

#### Impact of Priority:

Priority directly affects:

* Escalation time
  * HIGH → faster escalation
  * MEDIUM → moderate delay
  * LOW → longer delay

This ensures:

* Critical emergencies get faster attention
* System behaves intelligently based on severity

---

###  4. Category-Based Handling

Each emergency is also categorized:

* **AUTHORITY**
  * fire emergencies
  * Intended for official responders

* **COMMUNITY**
  * all other types
  * Handled by general users/volunteers

#### Behavior:

* Initial notifications differ based on category
* Escalation targets change:
  * AUTHORITY → backup responders
  * COMMUNITY → all available users

---

###  5. API Design (RESTful Services)

The backend exposes REST APIs for communication:

* `GET /api/emergencies`
  → Retrieve all emergencies

* `POST /api/emergencies`
  → Create a new emergency

* `PUT /api/emergencies/{id}/accept`
  → Accept an emergency

* `PUT /api/emergencies/{id}/resolve`
  → Resolve an emergency

These APIs enable smooth integration with the frontend.

---

###  6. Data Management

* Uses **Spring Data JPA (Hibernate)** for ORM
* Entity classes map directly to database tables
* Repository layer provides built-in CRUD operations
* MySQL is used for persistent storage

This simplifies database interaction and reduces manual SQL handling.

---

###  7. Frontend Implementation

The frontend is designed using React with focus on usability and interaction:

* **State Management:** `useState`
* **Side Effects:** `useEffect` for API calls and auto-refresh
* **API Communication:** Axios
* **Dashboard:**
  * Displays total, pending, accepted, and resolved counts
* **Filtering:**
  * Allows viewing emergencies by status (All / Pending / Accepted / Resolved)
* **Dynamic Input:**
  * Supports custom emergency types using “Other” option

---

##  System Architecture

The system follows a layered architecture:

Frontend (React) → Backend (Spring Boot) → Database (MySQL)

### Layers:

* **Controller Layer** → Handles incoming requests
* **Service Layer** → Contains business logic
* **Repository Layer** → Database access
* **Entity Layer** → Data representation

This design ensures:

* Separation of concerns
* Better maintainability
* Scalability

---

##  Features

* Create emergency requests
* Track emergency status
* Accept and resolve emergencies
* Automatic escalation
* Priority-based handling
* Category-based routing
* Dashboard statistics
* Filtering functionality

---

##  Screenshots

<img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/0f3239bc-e8f2-4370-ab05-36e4d2f8b9b1" />

---


<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/e3693b5e-dce0-43ca-929a-7b5a5f3b78ec" />




---

##  How to Run

### Backend

Run Spring Boot application  
→ http://localhost:8082  

### Frontend

npm install  
npm start  

→ http://localhost:3000  

---


##  Future Improvements

* Add authentication using JWT
* Implement real-time updates using WebSockets
* Add role-based access control

---

##  Conclusion

This project focuses on implementing a reliable emergency handling system using structured workflow, priority-based logic, and automated escalation. By combining these elements, the system ensures that emergency requests are processed efficiently and are not ignored.

The design emphasizes both functionality and real-world applicability.
