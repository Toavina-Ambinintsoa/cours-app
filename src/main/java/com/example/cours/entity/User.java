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
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID Id;

  private String firstName;
  private String lastName;
  private String userName;
  private String email;

  @OneToMany private Subscription subscription;
}
