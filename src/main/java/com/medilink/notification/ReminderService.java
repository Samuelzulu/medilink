package com.medilink.notification;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service managing scheduled reminders and notifications for clinic
 * appointments.
 * Resolves Defect #1: Purges reminder queues upon appointment cancellation.
 */
public class ReminderService {
    private final Map<String, String> appointmentStatus = new ConcurrentHashMap<>();
    private final List<String> reminderQueue = new ArrayList<>();

    public void scheduledReminder(String appointmentId) {
        appointmentStatus.put(appointmentId, "CONFIRMED");
        reminderQueue.add(appointmentId);
    }

    /**
     * Cancels the appointment and immediately removes scheduled reminder jobs from
     * the queue, preventing ghost reminders being sent to cancelled patients.
     */
    public void cancelAppointment(String appointmentId) {
        appointmentStatus.put(appointmentId, "CANCELLED");
        reminderQueue.removeIf(id -> id.equals(appointmentId));
    }

    /**
     * Returns pending reminders with defensive status verification.
     */
    public List<String> getPendingReminders() {
        List<String> active = new ArrayList<>();
        for (String id : reminderQueue) {
            if ("CONFIRMED".equals(appointmentStatus.get(id))) {
                active.add(id);
            }
        }
        return active;
    }
}
