package com.anurag.worklog.service;

import com.anurag.worklog.dto.WorkLogRequest;
import com.anurag.worklog.dto.WorkLogResponse;
import com.anurag.worklog.entity.User;
import com.anurag.worklog.entity.WorkLog;
import com.anurag.worklog.exception.ResourceNotFoundException;
import com.anurag.worklog.exception.UnauthorizedActionException;
import com.anurag.worklog.repository.UserRepository;
import com.anurag.worklog.repository.WorkLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkLogService {

    private final WorkLogRepository workLogRepository;
    private final UserRepository userRepository;

    public WorkLogResponse create(WorkLogRequest request, Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        WorkLog workLog = new WorkLog();
        workLog.setTitle(request.getTitle());
        workLog.setDescription(request.getDescription());
        workLog.setDate(request.getDate());
        workLog.setHoursWorked(request.getHoursWorked());
        workLog.setMood(request.getMood());
        workLog.setProductivityRating(request.getProductivityRating());
        workLog.setUser(user);

        WorkLog saved = workLogRepository.save(workLog);
        return toResponse(saved);
    }

    public List<WorkLogResponse> getAllForUser(Long userId){
        return workLogRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<WorkLogResponse> getTodayForUser(Long userId){
        return workLogRepository.findByUserIdAndDate(userId, LocalDate.now())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public WorkLogResponse update(Long logId, WorkLogRequest request, Long userId){
        WorkLog workLog = workLogRepository.findById(logId)
                .orElseThrow(() -> new ResourceNotFoundException("Work log not found"));

        if(!workLog.getUser().getId().equals(userId)){
            throw new UnauthorizedActionException("Not authorized to perform this action");
        }

        workLog.setTitle(request.getTitle());
        workLog.setDescription(request.getDescription());
        workLog.setDate(request.getDate());
        workLog.setHoursWorked(request.getHoursWorked());
        workLog.setMood(request.getMood());
        workLog.setProductivityRating(request.getProductivityRating());

        WorkLog saved = workLogRepository.save(workLog);
        return toResponse(saved);
    }

    public void delete(Long logId, Long userId){
        WorkLog workLog = workLogRepository.findById(logId)
                .orElseThrow(() -> new ResourceNotFoundException("Work log not found"));

        if(!workLog.getUser().getId().equals(userId)){
            throw new UnauthorizedActionException("Not authorized to perform this action");
        }

        workLogRepository.delete(workLog);
    }

    private WorkLogResponse toResponse(WorkLog workLog) {
        WorkLogResponse response = new WorkLogResponse();
        response.setId(workLog.getId());
        response.setTitle(workLog.getTitle());
        response.setDescription(workLog.getDescription());
        response.setDate(workLog.getDate());
        response.setHoursWorked(workLog.getHoursWorked());
        response.setMood(workLog.getMood());
        response.setProductivityRating(workLog.getProductivityRating());
        response.setUserId(workLog.getUser().getId());
        return response;
    }
}
