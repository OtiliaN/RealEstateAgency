package org.example.domain;

public enum PropertyType {
    APARTMENT("Apartment"),
    HOUSE("House"),
    LAND("Land"),
    COMMERCIAL("Commercial");

    private final String displayName;

    PropertyType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
