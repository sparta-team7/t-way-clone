package com.example.intermediate.repository;


import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
  List<Ticket> findAllByMemberOrderByTicketStartAsc(Member member);
}
