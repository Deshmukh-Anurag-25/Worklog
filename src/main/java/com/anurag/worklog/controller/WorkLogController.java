package com.anurag.worklog.controller;

import com.anurag.worklog.dto.WorkLogRequest;
import com.anurag.worklog.dto.WorkLogResponse;
import com.anurag.worklog.service.WorkLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/worklogs")
@RequiredArgsConstructor
public class WorkLogController {

    private final WorkLogService workLogService;

    @GetMapping
    public ResponseEntity<List<WorkLogResponse>> getAll(@RequestParam Long userId){
        List<WorkLogResponse> logs = workLogService.getAllForUser(userId);
        return ResponseEntity.ok(logs);
    }

    @PostMapping
    public ResponseEntity<WorkLogResponse> create(
            @RequestBody @Valid WorkLogRequest request,
            @RequestParam Long userId
            ){
        WorkLogResponse response = workLogService.create(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/today")
    public ResponseEntity<List<WorkLogResponse>> getToday(@RequestParam Long userId){
        List<WorkLogResponse> logs = workLogService.getTodayForUser(userId);
        return ResponseEntity.ok(logs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkLogResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid WorkLogRequest request,
            @RequestParam Long userId
    ){
        WorkLogResponse response = workLogService.update(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam Long userId){
        workLogService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
}
