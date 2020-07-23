package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String home() { return "home"; }

    @GetMapping("/users")
    public Iterable<User> list() {
        return this.repository.findAll();
    }

    @PostMapping("/users")
    public User create(@RequestBody User user) {
        return this.repository.save(user);
    }

    @GetMapping("/users/{id}")
    public User retrieve(@PathVariable long id) {
        return this.repository.findById(id).get();
    }

    @PatchMapping("/users/{id}")
    public User update(@PathVariable long id, @RequestBody User user) {
        User userToUpdate = this.repository.findById(id).get();

        userToUpdate.setEmail(user.getEmail());
        if (user.getPassword() != null) {
            userToUpdate.setPassword(user.getPassword());
        }

        return this.repository.save(userToUpdate);
    }

    @DeleteMapping("/users/{id}")
    public Map<String, Long> delete(@PathVariable long id) {
        this.repository.deleteById(id);
        Map<String, Long> results = new HashMap<>();
        results.put("count", this.repository.count());
        return results;
    }

    @PostMapping("/users/authenticate")
    public Map<String, Object> authenticate(@RequestBody User user) {
        Map<String, Object> results = new HashMap<>();
        Long id = this.repository.authenticate(user.getEmail(), user.getPassword());
        if (id != null) {
            user.setId(id);
            results.put("authenticated", true);
            results.put("user", user);
        } else {
            results.put("authenticated", false);
        }
        return results;
    }
}
