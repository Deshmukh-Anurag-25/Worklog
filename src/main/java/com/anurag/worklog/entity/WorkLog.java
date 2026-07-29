package com.anurag.worklog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "work_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "hours_worked", nullable = false)
    private Double hoursWorked;

    @Enumerated(EnumType.STRING)
    private Mood mood;

    @Min(1)
    @Max(10)
    @Column(name = "productivity_rating")
    private Integer productivityRating;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public enum Mood{
        GREAT, GOOD, NEUTRAL, BAD, TERRIBLE
    }
}
