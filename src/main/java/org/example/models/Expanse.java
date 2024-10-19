package org.example.models;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Expanse {

    private String id;
    private String description;
    private BigDecimal amount;
    private Instant date;

    public Expanse() {
    }

    public Expanse(String id, String description, BigDecimal amount, Instant date) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expanse expanse = (Expanse) o;
        return Objects.equals(id, expanse.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
