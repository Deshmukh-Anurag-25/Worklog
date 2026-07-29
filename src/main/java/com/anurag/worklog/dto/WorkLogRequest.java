package com.anurag.worklog.dto;


import com.anurag.worklog.entity.WorkLog;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkLogRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private LocalDate date;

    @NotNull
    @Positive
    private Double hoursWorked;

    private WorkLog.Mood mood;

    @Min(1) @Max(10)
    private Integer productivityRating;
}
