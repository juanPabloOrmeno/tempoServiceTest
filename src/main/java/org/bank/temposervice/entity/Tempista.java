package org.bank.temposervice.entity;

import jakarta.persistence.*;
import org.bank.temposervice.model.Transaction;

import java.util.List;

@Entity
@Table(name = "tempistas",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_tempista_name", columnNames = "name")
        }
)
public class Tempista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "tempista", fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    // ===== Constructors =====

    public Tempista() {
    }

    public Tempista(String name) {
        this.name = name;
    }

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}