package com.example.cours.endpoint.rest.controller.health;

import com.example.cours.endpoint.event.EventProducer;
import com.example.cours.endpoint.event.model.SendEmailRequested;
import com.example.cours.entity.Subscription;
import com.example.cours.mail.Email;
import com.example.cours.mail.Mailer;
import com.example.cours.repository.CourseRepository;
import com.example.cours.repository.JUserRepository;
import com.example.cours.repository.SubscriptionRepository;
import jakarta.mail.internet.InternetAddress;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/subscriptions")
@AllArgsConstructor
public class SubscriptionController {

  private final SubscriptionRepository subscriptionRepository;
  private final JUserRepository userRepository;
  private final CourseRepository courseRepository;
  private final Mailer mailer;
  private final EventProducer<SendEmailRequested> eventProducer;

  @PostMapping
  @SneakyThrows
  public ResponseEntity<Subscription> create(@RequestBody CreateSubscriptionRequest request) {
    var user =
        userRepository
            .findById(request.userId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    var course =
        courseRepository
            .findById(request.courseId())
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

    var subscription = new Subscription(null, user, course);
    subscription = subscriptionRepository.save(subscription);

    var recipient = new InternetAddress(user.getEmail());
    var subject = "Inscription confirmée : " + course.getTitle();
    var body =
        "Bonjour "
            + user.getFirstName()
            + ",\n\nVotre inscription au cours \""
            + course.getTitle()
            + "\" a bien été enregistrée !";

    mailer.accept(new Email(recipient, List.of(), List.of(), subject, body, List.of()));

    return ResponseEntity.status(HttpStatus.CREATED).body(subscription);
  }

  @GetMapping("/hello")
  @SneakyThrows
  public String helloWorld(@RequestParam String to) {
    var event = SendEmailRequested.builder().to(to).build();
    eventProducer.accept(List.of(event));
    return "... world!";
  }

  public record CreateSubscriptionRequest(UUID userId, UUID courseId) {}
}
