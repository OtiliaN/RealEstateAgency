package org.example.domain;

import jakarta.persistence.*;

@jakarta.persistence.Entity
@Table(name = "properties")
public class Property extends Entity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "neighborhood", nullable = false)
    private String neighborhood;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_type", nullable = false)
    private PropertyType propertyType;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    // Constructors
    public Property() {}

    public Property(String city, String neighborhood, TransactionType transactionType,
                    PropertyType propertyType, Integer price, String description, Agent agent) {
        this.city = city;
        this.neighborhood = neighborhood;
        this.transactionType = transactionType;
        this.propertyType = propertyType;
        this.price = price;
        this.description = description;
        this.agent = agent;
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

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getNeighborhood() { return neighborhood; }
    public void setNeighborhood(String neighborhood) { this.neighborhood = neighborhood; }

    public TransactionType getTransactionType() { return transactionType; }
    public void setTransactionType(TransactionType transactionType) { this.transactionType = transactionType; }

    public PropertyType getPropertyType() { return propertyType; }
    public void setPropertyType(PropertyType propertyType) { this.propertyType = propertyType; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Agent getAgent() { return agent; }
    public void setAgent(Agent agent) { this.agent = agent; }


}
