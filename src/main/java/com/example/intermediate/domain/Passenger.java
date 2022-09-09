package com.example.intermediate.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 여행의 passenger.
// ex) 2박 3일의 여행 일정인 경우 1일차 passenger, 2일차 passenger, 3일차 passenger 총 3개의 passenger를 가지고 있다.
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Passenger  {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //해당 passenger를 포함하는 ticket
  @JoinColumn(name = "ticket_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Ticket ticket;

  //해당 passenger의 전체 cost 비용 합
  @Column(nullable = false)
  private int subTotal;

  //cost 삭제 및 생성에 의한 비용 변경시 subtotal 갱신
  public void uppassenger(int pay){
    this.subTotal += pay;
  }
}
