package com.example.intermediate.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequestDto {
  private String title;
  private String content;
  private String ticketStart;
  private String ticketEnd;
  private int days;
  private Long total;
}