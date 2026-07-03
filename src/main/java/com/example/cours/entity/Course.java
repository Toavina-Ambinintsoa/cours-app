package com.example.cours.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Course {
  @GeneratedValue(strategy = GenerationType.UUID)
  @Id
  private UUID Id;

  private String title;
  private Instant startDate;
  private Instant endDate;

  @OneToMany(mappedBy = "course")
  private List<Subscription> subscriptions = new ArrayList<>();
}
