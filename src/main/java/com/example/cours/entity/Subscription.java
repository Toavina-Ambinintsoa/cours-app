package com.example.cours.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Subscription {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID Id;

  @ManyToOne private User user;

  @ManyToOne private Course course;
}
