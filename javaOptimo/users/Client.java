package javaOptimo.users;

import java.util.Optional;

public record Client(
        int id,
        String name,
        String email,
        Optional<String> company,
        Optional<String> phone) {
}
