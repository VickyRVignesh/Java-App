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

    String getFrom() {
        return from;
    }

    String getTo() {
        return to;
    }

    String getUserName() {
        return userName;
    }

    String getUserEmail() {
        return userEmail;
    }

    double getPrice() {
        return price;
    }

    String getSeatSection() {
        return seatSection;
    }

    void setSeatSection(String newSeatSection) {
        this.seatSection = newSeatSection;
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

    public static void purchaseTicket(String from, String to, String userName, String userEmail, double price) {
        validatePurchaseInput(userName, userEmail, price);

        if (tickets.containsKey(userName)) {
            throw new IllegalStateException("User '" + userName + "' already has a ticket. Cannot purchase another.");
        }

        String seatSection = assignSeatSection();
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

        TrainTicket ticket = tickets.get(userName);
        if (ticket == null) {
            throw new IllegalArgumentException("User '" + userName + "' not found. Cannot modify seat.");
        }
        ticket.setSeatSection(newSeatSection);
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

    private static String assignSeatSection() {
        return tickets.size() % 2 == 0 ? "A" : "B";
    }
}
