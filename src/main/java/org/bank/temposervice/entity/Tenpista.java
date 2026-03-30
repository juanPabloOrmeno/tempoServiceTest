package org.bank.temposervice.entity;

import jakarta.persistence.*;
import org.bank.temposervice.model.Transaction;

import java.util.List;

@Entity
@Table(name = "tenpistas",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_tenpista_name", columnNames = "name")
        }
)
public class Tenpista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "tenpista", fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}