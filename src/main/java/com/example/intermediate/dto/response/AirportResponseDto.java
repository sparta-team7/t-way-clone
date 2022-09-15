package com.example.intermediate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//Ticket 상세 조회용 DTO
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AirportResponseDto {
  private String endPoint;

  // 출발 공항 한글
  private String startPoint;
  // 출발 공항 영어코드
  private String startEng;
  // 항공기 편명
  private String flyNum;
  // 출발 시간
  private String startTime;
  // 여행 종료일
  private String endTime;
  // 소요시간
  private String takeTime;
  // 항공편 가격
  private int charge;
  private int chargeDc;
}
