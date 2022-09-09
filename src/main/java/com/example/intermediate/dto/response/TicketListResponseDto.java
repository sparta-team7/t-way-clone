package com.example.intermediate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//Ticket 목록 조회용 DTO
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TicketListResponseDto {
  private Long id;
  private String title;
  private String ticketStart;
  private String ticketEnd;
}
