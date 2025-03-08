import java.util.*;
import java.io.*;

// Abstract class representing a Bus
abstract class Bus {
    protected int busNumber;
    protected String busType;
    protected int capacity;
    protected double farePerKm;
    protected int availableSeats;
    protected List<String> route;

    public Bus(int busNumber, String busType, int capacity, double farePerKm, List<String> route) {
        this.busNumber = busNumber;
        this.busType = busType;
        this.capacity = capacity;
        this.availableSeats = capacity;
        this.farePerKm = farePerKm;
        this.route = route;
    }

    public abstract double calculateFare(double distance);

    public boolean bookSeat() {
        if (availableSeats > 0) {
            availableSeats--;
            return true;
        }
        return false;
    }

    public void cancelSeat() {
        if (availableSeats < capacity) {
            availableSeats++;
        }
    }

    public boolean isValidRoute(String source, String destination) {
        return route.contains(source) && route.contains(destination)
                && route.indexOf(source) < route.indexOf(destination);
    }

    public String getBusDetails() {
        return busNumber + " | " + busType + " | Seats: " + availableSeats + "/" + capacity + " | Route: "
                + String.join(" -> ", route);
    }
}

// Concrete Bus types
class ACBus extends Bus {
    static double farePerKm = 2.5;

    public ACBus(int busNumber, int capacity, List<String> route) {
        super(busNumber, "AC", capacity, farePerKm, route);
    }

    public double calculateFare(double distance) {
        return distance * farePerKm;
    }
}

class NonACBus extends Bus {
    static double farePerKm = 1.5;

    public NonACBus(int busNumber, int capacity, List<String> route) {
        super(busNumber, "Non-AC", capacity, farePerKm, route);
    }

    public double calculateFare(double distance) {
        return distance * farePerKm;
    }
}

class SleeperBus extends Bus {
    static double farePerKm = 3.0;

    public SleeperBus(int busNumber, int capacity, List<String> route) {
        super(busNumber, "Sleeper", capacity, farePerKm, route);
    }

    public double calculateFare(double distance) {
        return distance * farePerKm;
    }
}

// User class
class User {
    protected String name;
    protected String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}

// Passenger class
class Passenger extends User {
    private ArrayList<Ticket> bookings = new ArrayList<>();

    public Passenger(String name, String email) {
        super(name, email);
    }

    public void bookTicket(Bus bus, String source, String destination, double distance) {
        if (!bus.isValidRoute(source, destination)) {
            System.out.println("Invalid route! This bus does not travel from " + source + " to " + destination + ".");
            return;
        }

        if (bus.bookSeat()) {
            Ticket ticket = new Ticket(bus, name, source, destination, distance);
            bookings.add(ticket);
            ticket.saveToFile();
            System.out.println("Ticket Booked Successfully! Ticket ID: " + ticket.getTicketID());
        } else {
            System.out.println("No seats available on this bus.");
        }
    }

    public void cancelTicket(int ticketID) {
        for (Ticket ticket : bookings) {
            if (ticket.getTicketID() == ticketID) {
                ticket.getBus().cancelSeat();
                bookings.remove(ticket);
                System.out.println("Ticket Cancelled Successfully!");
                return;
            }
        }
        System.out.println("Invalid Ticket ID.");
    }
}

// Ticket class
class Ticket {
    private static int ticketCounter = 1000;
    private int ticketID;
    private Bus bus;
    private String passengerName;
    private String source, destination;
    private double fare;

    public Ticket(Bus bus, String passengerName, String source, String destination, double distance) {
        this.ticketID = ticketCounter++;
        this.bus = bus;
        this.passengerName = passengerName;
        this.source = source;
        this.destination = destination;
        this.fare = bus.calculateFare(distance);
    }

    public int getTicketID() {
        return ticketID;
    }

    public Bus getBus() {
        return bus;
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter("tickets.txt", true)) {
            writer.write(ticketID + " | " + passengerName + " | " + source + " -> " + destination + " | Fare: " + fare
                    + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Main Driver Class
public class BusReservationSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Bus> buses = new ArrayList<>();
        buses.add(new ACBus(1, 10, Arrays.asList("A", "B", "C")));
        buses.add(new NonACBus(2, 20, Arrays.asList("D", "E", "F")));
        buses.add(new SleeperBus(3, 15, Arrays.asList("G", "H", "I")));

        System.out.println("Welcome to the Bus Reservation System");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        Passenger passenger = new Passenger(name, email);

        while (true) {
            System.out.println("\n1. View Buses\n2. Book Ticket\n3. Cancel Ticket\n4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("Available Buses:");
                for (Bus bus : buses) {
                    System.out.println(bus.getBusDetails());
                }
            } else if (choice == 2) {

                System.out.print("Enter Bus Number: ");
                int busNumber = scanner.nextInt();
                scanner.nextLine(); // Consume leftover newline

                Bus temp = null;
                for (Bus bus : buses) {
                    if (bus.busNumber == busNumber) {
                        temp = bus;
                        break;
                    }
                }

                if (temp == null) {
                    System.out.println("Bus not available, try again!");
                    continue;
                }

                System.out.print("Enter Source: ");
                String source = scanner.nextLine();

                System.out.print("Enter Destination: ");
                String destination = scanner.nextLine();

                System.out.print("Enter Distance (km): ");
                double distance = scanner.nextDouble();
                scanner.nextLine(); // Consume the leftover newline character

                passenger.bookTicket(temp, source, destination, distance);

            } else if (choice == 3) {
                System.out.print("Enter Ticket ID to Cancel: ");
                int ticketID = scanner.nextInt();
                scanner.nextLine();
                passenger.cancelTicket(ticketID);
            } else if (choice == 4) {
                System.out.println("Thank you for using the Bus Reservation System!");
                break;
            }
        }
        scanner.close();
    }
}
