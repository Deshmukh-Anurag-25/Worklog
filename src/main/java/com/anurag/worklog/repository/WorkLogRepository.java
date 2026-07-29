package com.anurag.worklog.repository;

import com.anurag.worklog.entity.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {
    List<WorkLog> findByUserId(Long userId);
    List<WorkLog> findByUserIdAndDate(Long userId, LocalDate date);
}
