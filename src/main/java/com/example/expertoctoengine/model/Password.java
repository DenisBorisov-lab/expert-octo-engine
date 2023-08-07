package com.example.expertoctoengine.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


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
    @Column(name = "password", columnDefinition = "TEXT", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
