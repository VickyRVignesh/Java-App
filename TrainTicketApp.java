import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class TrainTicket {
    private String from;
    private String to;
    private String userName;
    private String userEmail;
    private double price;
    private String seatSection;

    TrainTicket(String from, String to, String userName, String userEmail, double price, String seatSection) {
        this.from = from;
        this.to = to;
        this.userName = userName;
        this.userEmail = userEmail;
        this.price = price;
        this.seatSection = seatSection;
    }

    // Constructor to copy values from an existing ticket
    TrainTicket(TrainTicket existingTicket, String newSeatSection) {
        this(existingTicket.from, existingTicket.to, existingTicket.userName,
                existingTicket.userEmail, existingTicket.price, newSeatSection);
    }

    String getUserName() {
        return userName;
    }

    String getSeatSection() {
        return seatSection;
    }

    @Override
    public String toString() {
        return "TrainTicket{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", price=" + price +
                ", seatSection='" + seatSection + '\'' +
                '}';
    }
}

public class TrainTicketApp {
    private static Map<String, TrainTicket> tickets = new HashMap<>();

    public static void purchaseTicket(String from, String to, String userName, String userEmail, double price, String seatSection) {
        validatePurchaseInput(userName, userEmail, price);

        if (tickets.containsKey(userName)) {
            throw new IllegalStateException("User '" + userName + "' already has a ticket. Cannot purchase another.");
        }

        TrainTicket ticket = new TrainTicket(from, to, userName, userEmail, price, seatSection);
        tickets.put(userName, ticket);
    }

    public static TrainTicket getReceiptDetails(String userName) {
        validateUserName(userName);

        TrainTicket ticket = tickets.get(userName);
        if (ticket == null) {
            throw new IllegalArgumentException("User '" + userName + "' not found. No receipt details available.");
        }
        return ticket;
    }

    public static Map<String, String> getUsersBySection(String section) {
        Objects.requireNonNull(section, "Section cannot be null.");

        Map<String, String> usersBySection = new HashMap<>();
        for (TrainTicket ticket : tickets.values()) {
            if (ticket.getSeatSection().equals(section)) {
                usersBySection.put(ticket.getUserName(), ticket.getSeatSection());
            }
        }
        return usersBySection;
    }

    public static void removeUser(String userName) {
        validateUserName(userName);

        if (!tickets.containsKey(userName)) {
            throw new IllegalArgumentException("User '" + userName + "' not found. Cannot remove.");
        }
        tickets.remove(userName);
    }

    public static void modifyUserSeat(String userName, String newSeatSection) {
        validateUserName(userName);

        TrainTicket existingTicket = tickets.get(userName);
        if (existingTicket == null) {
            throw new IllegalArgumentException("User '" + userName + "' not found. Cannot modify seat.");
        }

        // Create a new ticket with the modified seat section
        TrainTicket modifiedTicket = new TrainTicket(existingTicket, newSeatSection);
        tickets.put(userName, modifiedTicket);
    }

    private static void validatePurchaseInput(String userName, String userEmail, double price) {
        Objects.requireNonNull(userName, "User name cannot be null.");
        Objects.requireNonNull(userEmail, "User email cannot be null.");
        if (userName.isEmpty() || userEmail.isEmpty() || price <= 0) {
            throw new IllegalArgumentException("Invalid input parameters for purchasing a ticket.");
        }
    }

    private static void validateUserName(String userName) {
        Objects.requireNonNull(userName, "User name cannot be null.");
        if (!tickets.containsKey(userName)) {
            throw new IllegalArgumentException("User '" + userName + "' not found.");
        }
    }

    public static void main(String[] args) {
        try {
            // Example
            purchaseTicket("London", "France", "John Doe", "john.doe@example.com", 20.0, "A");
            System.out.println(getReceiptDetails("John Doe"));

            purchaseTicket("London", "France", "Jane Doe", "jane.doe@example.com", 20.0, "B");
            System.out.println(getUsersBySection("B"));

            modifyUserSeat("John Doe", "B");
            System.out.println(getUsersBySection("A"));

            removeUser("Jane Doe");
            System.out.println(getReceiptDetails("Jane Doe"));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
