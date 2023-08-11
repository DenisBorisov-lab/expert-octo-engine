package com.example.expertoctoengine.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "password")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Password {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "service_name", columnDefinition = "TEXT", nullable = false)
    private String service;

    @Column(name = "login", columnDefinition = "TEXT", nullable = false)
    private String login;

    @Column(name = "password", columnDefinition = "TEXT", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    @Override
    public String toString() {
        return "Password{" +
                "id=" + id +
                ", service='" + service + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
