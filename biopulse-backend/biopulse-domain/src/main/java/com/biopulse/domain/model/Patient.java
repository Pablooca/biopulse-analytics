package com.biopulse.domain.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.UUID;
import com.biopulse.domain.exception.InvalidPatientAgeException;

/**
 * Aggregate Root representing a Patient profile inside the clinical core.
 * Written in Pure Java 21 to guarantee zero compilation or annotation processing overhead.
 */
public final class Patient {

    private final UUID id;
    private final String patientCode;
    private final String firstName;
    private final String lastName;
    private final LocalDate dateOfBirth;
    private final String email;
    private final PatientStatus status;

    /**
     * Private constructor enforced by the Builder pattern.
     * Sanitizes data and executes domain invariants before instantiation.
     */
    private Patient(PatientBuilder builder) {
        this.id = builder.id;
        this.patientCode = builder.patientCode;
        this.firstName = builder.firstName != null ? builder.firstName.trim() : null;
        this.lastName = builder.lastName != null ? builder.lastName.trim() : null;
        this.dateOfBirth = builder.dateOfBirth;
        this.email = builder.email;
        this.status = builder.status;
        validateInvariants();
    }

    /**
     * Evaluates core medical and legal invariants for the Patient aggregate.
     */
    private void validateInvariants() {
        if (dateOfBirth != null && Period.between(dateOfBirth, LocalDate.now()).getYears() < 18) {
            throw new InvalidPatientAgeException(
                    "Patient must be at least 18 years old to be legally enrolled in the telemedicine trial."
            );
        }
        if (patientCode != null && !patientCode.matches("^BIO-[0-9]{6}$")) {
            throw new IllegalArgumentException(
                    "Alphanumeric clinical code format is invalid. Must strictly match pattern: BIO-XXXXXX"
            );
        }
    }

    /**
     * Evolves the Patient status to SUSPENDED, blocking incoming streaming adapters.
     *
     * @return A new immutable Patient instance with updated SUSPENDED status.
     * @throws IllegalStateException If the patient is already permanently discharged.
     */
    public Patient suspendTelemetry() {
        if (this.status == PatientStatus.DISCHARGED) {
            throw new IllegalStateException("Cannot suspend telemetry: Patient is already permanently discharged.");
        }
        if (this.status == PatientStatus.SUSPENDED) {
            return this;
        }
        return this.toBuilder()
                .status(PatientStatus.SUSPENDED)
                .build();
    }

    /**
     * Evolves the Patient status to DISCHARGED, finalizing their clinical timeline.
     *
     * @return A new immutable Patient instance with updated DISCHARGED status.
     */
    public Patient dischargeFromProgram() {
        if (this.status == PatientStatus.DISCHARGED) {
            return this;
        }
        return this.toBuilder()
                .status(PatientStatus.DISCHARGED)
                .build();
    }

    // --- PURE JAVA GETTERS ---
    public UUID getId() { return id; }
    public String getPatientCode() { return patientCode; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getEmail() { return email; }
    public PatientStatus getStatus() { return status; }

    // --- BUILDER PATTERN BRIDGE METHODS ---
    public static PatientBuilder builder() {
        return new PatientBuilder();
    }

    public PatientBuilder toBuilder() {
        return new PatientBuilder()
                .id(this.id)
                .patientCode(this.patientCode)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .dateOfBirth(this.dateOfBirth)
                .email(this.email)
                .status(this.status);
    }

    /**
     * Static inner class implementing a fluent API Builder pattern natively.
     */
    public static class PatientBuilder {
        private UUID id;
        private String patientCode;
        private String firstName;
        private String lastName;
        private LocalDate dateOfBirth;
        private String email;
        private PatientStatus status;

        public PatientBuilder id(UUID id) { this.id = id; return this; }
        public PatientBuilder patientCode(String patientCode) { this.patientCode = patientCode; return this; }
        public PatientBuilder firstName(String firstName) { this.firstName = firstName; return this; }
        public PatientBuilder lastName(String lastName) { this.lastName = lastName; return this; }
        public PatientBuilder dateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; return this; }
        public PatientBuilder email(String email) { this.email = email; return this; }
        public PatientBuilder status(PatientStatus status) { this.status = status; return this; }

        public Patient build() {
            return new Patient(this);
        }
    }

    // --- STANDARD OVERRIDES (EQUALS, HASHCODE, TOSTRING) ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id) &&
                Objects.equals(patientCode, patient.patientCode) &&
                Objects.equals(firstName, patient.firstName) &&
                Objects.equals(lastName, patient.lastName) &&
                Objects.equals(dateOfBirth, patient.dateOfBirth) &&
                Objects.equals(email, patient.email) &&
                status == patient.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patientCode, firstName, lastName, dateOfBirth, email, status);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", patientCode='" + patientCode + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                ", status=" + status +
                '}';
    }
}