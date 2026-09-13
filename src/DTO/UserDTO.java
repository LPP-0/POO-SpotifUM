package DTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

public class UserDTO {
    private final String username;
    private final String name;
    private final String email;
    private final String address;
    private final String password;
    private final LocalDate birthday; // String para depois converter para Date
    private final int subscriptionPlan;

    // Construtor
    public UserDTO(String username, String name, String email, String address,
                   String password, LocalDate birthday, int subscriptionPlan) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.address = address;
        this.password = password;
        this.birthday = birthday;
        this.subscriptionPlan = subscriptionPlan;
    }

    // Getters
    public String getUsername() { return this.username; }
    public String getName() { return this.name; }
    public String getEmail() { return this.email; }
    public String getAddress() { return this.address; }
    public String getPassword() { return this.password; }
    public LocalDate getBirthday() { return this.birthday; }
    public int getSubscriptionPlan() { return this.subscriptionPlan; }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", username=" + username +
                ", password=" + password +
                '}';
    }
}
