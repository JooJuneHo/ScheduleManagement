package com.sparta.schedulemanagement.controller;

import com.sparta.schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.schedulemanagement.entity.ScheduleEntity;
import com.sparta.schedulemanagement.service.ScheduleService;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto){
        return scheduleService.createSchedule(requestDto);
    }

    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getAllSchedules(){
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/schedules/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id){
        return scheduleService.getSchedule(id);
    }

    @PutMapping("/schedules/{id}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.updateSchedule(id, requestDto);
    }

    @DeleteMapping("/schedules/{id}")
    public String deleteSchedule(@PathVariable Long id, @RequestParam String password) {
        return scheduleService.deleteSchedule(id, password);
    }
}
