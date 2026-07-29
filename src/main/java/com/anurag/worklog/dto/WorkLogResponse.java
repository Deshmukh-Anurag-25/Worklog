package com.anurag.worklog.dto;

import com.anurag.worklog.entity.WorkLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkLogResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDate date;
    private Double hoursWorked;
    private WorkLog.Mood mood;
    private Integer productivityRating;
    private Long userId;
}
