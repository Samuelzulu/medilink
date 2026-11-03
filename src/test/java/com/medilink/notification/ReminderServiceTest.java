package com.medilink.notification;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class ReminderServiceTest {

    @Test
    void cancelAppointmentPurgesPendingReminders() {
        ReminderService service = new ReminderService();
        service.scheduledReminder("APPT-101");
        assertEquals(1, service.getPendingReminders().size(), "Should have 1 pending reminder staged");

        // Execute cancellation
        service.cancelAppointment("APPT-101");

        // Defect #1 assertion: Cancelled
        List<String> activeReminders = service.getPendingReminders();
        assertTrue(activeReminders.isEmpty(), "Cancelled appointments must not have pending reminders in queue");
    }
}
