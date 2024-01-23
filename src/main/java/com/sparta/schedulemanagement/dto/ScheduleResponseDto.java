package com.sparta.schedulemanagement.dto;

import com.sparta.schedulemanagement.entity.ScheduleEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String manager;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ScheduleResponseDto(ScheduleEntity scheduleEntity) {
        this.id = scheduleEntity.getId();
        this.title = scheduleEntity.getTitle();
        this.contents = scheduleEntity.getContents();
        this.manager = scheduleEntity.getManager();
        this.createdAt = scheduleEntity.getCreatedAt();
        this.modifiedAt = scheduleEntity.getModifiedAt();
    }
}
