package com.example.cours.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Course {
  @GeneratedValue(strategy = GenerationType.UUID) @Id private UUID Id;
  private String title;
  private Instant startDate;
  private Instant endDate;

  @OneToMany
  private Subscription subscriptions;
}
