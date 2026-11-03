// Load saved appointments (or use an empty list).
let appointments = JSON.parse(localStorage.getItem("medilinkAppointments") || "[]");

function saveAppointments() {
  localStorage.setItem("medilinkAppointments", JSON.stringify(appointments));
}

function showAppointments() {
  const list = document.getElementById("appointments");
  const filter = document.getElementById("filter").value;
  list.innerHTML = "";

  const visible = appointments.filter(a => filter === "All" || a.doctor === filter);
  if (visible.length === 0) {
    list.textContent = "No appointments found.";
    return;
  }

  visible.forEach(a => {
    const row = document.createElement("div");
    row.className = "appointment";

    // textContent keeps patient input from being interpreted as HTML.
    const details = document.createElement("p");
    details.textContent = a.patient + " - " + a.doctor + " - " + a.date + " at " + a.time;
    row.appendChild(details);

    const status = document.createElement("p");
    status.textContent = "Status: " + a.status + (a.reason ? " (Reason: " + a.reason + ")" : "");
    row.appendChild(status);

    if (a.status === "Booked") {
      const cancelButton = document.createElement("button");
      cancelButton.className = "cancel";
      cancelButton.textContent = "Cancel";
      cancelButton.addEventListener("click", () => cancelAppointment(a.id));
      row.appendChild(cancelButton);
    }
    list.appendChild(row);
  });
}

function cancelAppointment(id) {
  const reason = prompt("Why are you cancelling? (e.g. schedule conflict)");
  if (reason === null || reason.trim() === "") return;

  const appointment = appointments.find(a => a.id === id);
  if (!appointment || appointment.status !== "Booked") return;
  appointment.status = "Cancelled";
  appointment.reason = reason.trim();
  saveAppointments();
  showAppointments();
  document.getElementById("reminders").textContent = "";
}

document.getElementById("bookingForm").addEventListener("submit", function(event) {
  event.preventDefault();
  const patient = document.getElementById("patient").value.trim();
  const doctor = document.getElementById("doctor").value;
  const date = document.getElementById("date").value;
  const time = document.getElementById("time").value;
  const message = document.getElementById("message");

  if (!patient || !date || date < new Date().toLocaleDateString("en-CA")) {
    message.textContent = "Enter a patient name and a valid date.";
    return;
  }

  // Prevent booking the same doctor at the same time twice.
  const taken = appointments.some(a => a.doctor === doctor && a.date === date &&
    a.time === time && a.status === "Booked");
  if (taken) {
    message.textContent = "That appointment time is already taken.";
    return;
  }

  appointments.push({ id: Date.now() + Math.random(), patient, doctor, date, time,
    status: "Booked", reason: "" });
  saveAppointments();
  message.textContent = "Appointment booked!";
  this.reset();
  showAppointments();
  document.getElementById("reminders").textContent = "";
});

document.getElementById("filter").addEventListener("change", showAppointments);

document.getElementById("remindersButton").addEventListener("click", () => {
  const box = document.getElementById("reminders");
  box.innerHTML = "";
  const booked = appointments.filter(a => a.status === "Booked");
  if (!booked.length) {
    box.textContent = "No active appointments to remind.";
    return;
  }
  booked.forEach(a => {
    const line = document.createElement("p");
    line.textContent = "Reminder preview: " + a.patient + " - " + a.date + " at " + a.time;
    box.appendChild(line);
  });
});

showAppointments();
