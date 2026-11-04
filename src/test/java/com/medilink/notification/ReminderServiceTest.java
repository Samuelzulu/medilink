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

    @Test
    void cancelAppointmentLeavesOtherAppointmentsPending() {
        ReminderService service = new ReminderService();
        service.scheduledReminder("APPT-101");
        service.scheduledReminder("APPT-102");

        service.cancelAppointment("APPT-101");

        assertEquals(List.of("APPT-102"), service.getPendingReminders());
    }

    @Test
    void cancelAppointmentRemovesEveryQueuedReminderForThatAppointment() {
        ReminderService service = new ReminderService();
        service.scheduledReminder("APPT-101");
        service.scheduledReminder("APPT-101");
        service.scheduledReminder("APPT-102");

        service.cancelAppointment("APPT-101");

        assertEquals(List.of("APPT-102"), service.getPendingReminders());
    }

    @Test
    void pendingRemindersReturnsAnIndependentList() {
        ReminderService service = new ReminderService();
        service.scheduledReminder("APPT-101");

        List<String> reminders = service.getPendingReminders();
        reminders.clear();

        assertEquals(List.of("APPT-101"), service.getPendingReminders());
    }
}
