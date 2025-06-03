package org.example.domain;

public enum TransactionType {
    SALE("Sale"),
    RENT("Rent");

    private final String displayName;

    TransactionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
