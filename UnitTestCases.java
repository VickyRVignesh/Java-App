import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TrainTicketAppTest {

    @Test
    void purchaseTicket_validInput_ticketAdded() {
        TrainTicketApp.purchaseTicket("London", "France", "John Doe", "john.doe@example.com", 20.0, "A");
        assertNotNull(TrainTicketApp.getReceiptDetails("John Doe"));
    }

    @Test
    void purchaseTicket_duplicateUser_exceptionThrown() {
        TrainTicketApp.purchaseTicket("London", "France", "Jane Doe", "jane.doe@example.com", 20.0, "B");
        assertThrows(IllegalStateException.class,
                () -> TrainTicketApp.purchaseTicket("Paris", "Germany", "Jane Doe", "jane.doe@example.com", 25.0, "C"));
    }

    @Test
    void getReceiptDetails_existingUser_ticketReturned() {
        TrainTicketApp.purchaseTicket("London", "France", "John Doe", "john.doe@example.com", 20.0, "A");
        assertNotNull(TrainTicketApp.getReceiptDetails("John Doe"));
    }

    @Test
    void getReceiptDetails_nonExistingUser_exceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> TrainTicketApp.getReceiptDetails("Nonexistent User"));
    }

    @Test
    void getUsersBySection_validSection_usersReturned() {
        TrainTicketApp.purchaseTicket("London", "France", "John Doe", "john.doe@example.com", 20.0, "A");
        TrainTicketApp.purchaseTicket("London", "France", "Jane Doe", "jane.doe@example.com", 20.0, "A");
        assertEquals(2, TrainTicketApp.getUsersBySection("A").size());
    }

    @Test
    void getUsersBySection_invalidSection_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> TrainTicketApp.getUsersBySection(null));
    }

    @Test
    void removeUser_existingUser_userRemoved() {
        TrainTicketApp.purchaseTicket("London", "France", "John Doe", "john.doe@example.com", 20.0, "A");
        TrainTicketApp.removeUser("John Doe");
        assertThrows(IllegalArgumentException.class, () -> TrainTicketApp.getReceiptDetails("John Doe"));
    }

    @Test
    void removeUser_nonExistingUser_exceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> TrainTicketApp.removeUser("Nonexistent User"));
    }

    @Test
    void modifyUserSeat_existingUser_seatModified() {
        TrainTicketApp.purchaseTicket("London", "France", "John Doe", "john.doe@example.com", 20.0, "A");
        TrainTicketApp.modifyUserSeat("John Doe", "B");
        assertEquals("B", TrainTicketApp.getReceiptDetails("John Doe").getSeatSection());
    }

    @Test
    void modifyUserSeat_nonExistingUser_exceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> TrainTicketApp.modifyUserSeat("Nonexistent User", "C"));
    }
}
