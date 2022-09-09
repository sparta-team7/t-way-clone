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
public class TicketResponseDto {
  private Long id;
  private String title;
  private String content;
  private String ticketStart;
  private String ticketEnd;
  private int days;
  private int total;
  private List<PassengerResponseDto> passengerList = new ArrayList<>();
}
