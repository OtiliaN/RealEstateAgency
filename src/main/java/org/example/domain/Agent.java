package org.example.domain;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@jakarta.persistence.Entity
@Table(name = "agents")
public class Agent extends Entity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone")
    private String phone;


    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Property> properties = new HashSet<>();

    public Agent(String name, String username, String password, String phone) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

    public Agent() {

    }

    // Getters and Setters
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Set<Property> getProperties() { return properties; }
    public void setProperties(Set<Property> properties) { this.properties = properties; }
}
