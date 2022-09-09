package com.example.intermediate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//Ticket 상세 조회용 DTO
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AirportResponseDto {
  private Long id;
  private String arrivalCity;
  private String startCity;
  private String domesticStartTime;
  private String domesticArrivalTime;
  private String time;
  private int domesticNum;
  private int charge;

  private List<PassengerResponseDto> passengerList = new ArrayList<>();
}
