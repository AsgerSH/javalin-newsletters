package app.entities;

import java.time.LocalDate;

public class Subscriber {
    String email;
    LocalDate localDate;

    public Subscriber(String email, LocalDate localDate) {
        this.email = email;
        this.localDate = localDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "email='" + email + '\'' +
                ", localDate=" + localDate +
                '}';
    }
}
