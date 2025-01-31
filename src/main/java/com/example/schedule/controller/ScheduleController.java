package com.example.schedule.controller;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
@Tag(name = "Schedule", description = "일정 API")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    @Operation(summary = "일정 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터로 일정 생성 시 실패"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 ID 일정 생성 시 실패")
    })
    public ResponseEntity<ScheduleResponseDto> saveSchedule(@RequestBody @Valid ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "일정 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터로 일정 목록 조회 시 실패")
    })
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(@RequestParam(required = false) Integer pageNo,
                                                                      @RequestParam(required = false) Integer size,
                                                                      @RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.findAllSchedules(dto, pageNo, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "일정 단일 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 일정 ID 조회 시 실패")
    })
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "일정 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터로 일정 수정 시 실패"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 일정 ID 수정 시 실패")
    })
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @RequestBody @Valid ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.updateSchedule(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "일정 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 일정 ID 삭제 시 실패")
    })
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto dto) {
        scheduleService.deleteSchedule(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
