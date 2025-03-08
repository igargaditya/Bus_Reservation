# Bus Reservation System

A simple Bus Reservation System** implemented in Java, supporting AC, Non-AC, and Sleeper buses. 
The system allows users to view available buses, book tickets, and cancel tickets dynamically.

# Features
- **View available buses** with details like type, route, and available seats.
- **Book a ticket** if the selected bus has available seats and supports the travel route.
- **Cancel a ticket**, freeing up the booked seat.
- **Fare Calculation** based on distance and bus type.

# Class Diagram
The system follows an **OOP-based modular structure**:



#Class Overview

| Class | Description |
|--------|-------------|
| `Bus` | Abstract class representing a generic bus. |
| `ACBus`, `NonACBus`, `SleeperBus` | Concrete bus types with specific fare calculations. |
| `User` | Base class for a user. |
| `Passenger` | Extends `User`, allowing passengers to book and cancel tickets. |
| `Ticket` | Represents a booked ticket with fare calculation. |
| `BusReservationSystem` | The main class handling user input and system flow. |

![alt text](https://github.com/igargaditya/Bus_Reservation/blob/main/img4.png?raw=true)
![alt text](https://github.com/igargaditya/Bus_Reservation/blob/main/img3.png?raw=true)
![alt text](https://github.com/igargaditya/Bus_Reservation/blob/main/img2.png?raw=true)
![alt text](https://github.com/igargaditya/Bus_Reservation/blob/main/img1.png?raw=true)



# How to Run
1. **Clone the Repository**:
   ```sh
   git clone https://github.com/your-username/bus-reservation-system.git
   cd bus-reservation-system
2. **Compile the Code:**:
   ```sh
   javac BusReservationSystem.java

3. **Run the Code**:
   ```sh
   java BusReservationSystem
   
