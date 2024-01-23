package com.sparta.schedulemanagement.service;

import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.schedulemanagement.entity.ScheduleEntity;
import com.sparta.schedulemanagement.exception.InvalidPasswordException;
import com.sparta.schedulemanagement.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    //    @Autowired(생성자가 1개일때는 생략가능)
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        // RequestDto -> Entity
        ScheduleEntity schedule = new ScheduleEntity(requestDto);

        // DB 저장
        ScheduleEntity saveSchedule = scheduleRepository.save(schedule);

        // Entity -> ResponseDto
        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);

        return responseDto;
    }

    public ScheduleResponseDto getSchedule(Long id){
        return scheduleRepository.findById(id).map(ScheduleResponseDto::new).orElse(null);
    }

    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.findAllByOrderByCreatedAtDesc().stream().map(ScheduleResponseDto::new).toList();
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {

        // 해당 메모가 DB에 존재하는지 확인
        ScheduleEntity schedule = findSchedule(id);
        String password = requestDto.getPassword();
        try{
            if(checkPW(schedule,password)){
                // schedule 내용 수정
                schedule.update(requestDto);
            }
            return scheduleRepository.findById(id).map(ScheduleResponseDto::new).orElse(null);
        } catch (InvalidPasswordException e){
            return null;
        }
    }

    public String deleteSchedule(Long id, String password) {

        // 해당 메모가 DB에 존재하는지 확인
        ScheduleEntity schedule = findSchedule(id);

        try{
            if(checkPW(schedule,password)){
                // schedule 삭제
                scheduleRepository.delete(schedule);
            }
            return id + ". 삭제 성공";
        } catch (InvalidPasswordException e){
            return "비밀번호 불일치";
        }
    }

    private ScheduleEntity findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정은 존재하지 않습니다.")
        );
    }

    private boolean checkPW(ScheduleEntity scheduleEntity, String password) {
        if(scheduleEntity.getPassword().equals(password)){
            return true;
        } else{
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }
    }
}
