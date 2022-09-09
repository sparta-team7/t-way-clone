package com.example.intermediate.service;


import com.example.intermediate.dto.request.TicketRequestDto;
import com.example.intermediate.domain.Passenger;
import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.Ticket;
import com.example.intermediate.dto.response.*;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.PassengerRepository;
import com.example.intermediate.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {

  private final TokenProvider tokenProvider;
  private final TicketRepository ticketRepository;
  private final PassengerRepository passengerRepository;
  private final PassengerService passengerService;

  //ticket 생성 메서드
  @Transactional
  public ResponseDto<?> createTicket(TicketRequestDto requestDto, HttpServletRequest request) {

    //토큰 유효성 검사, 유효하면 해당 member 가져옴 아니면 null
    Member member = isValipassengerAccess(request);
    if (null == member) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
              "로그인이 필요합니다.");
    }

    //dto에 담긴 정보로 Ticket 생성
    Ticket ticket = Ticket.builder()
        .title(requestDto.getTitle())
        .content(requestDto.getContent())
        .days(requestDto.getDays())
        .ticketStart(requestDto.getTicketStart())
        .ticketEnd(requestDto.getTicketEnd())
        .total(0)
        .member(member)
        .build();

    ticketRepository.save(ticket);

    // ticket에 포함할 passenger 생성
    passengerService.createPassenger(ticket);
    return ResponseDto.success(
        TicketResponseDto.builder()
            .id(ticket.getId())
            .title(ticket.getTitle())
            .content(ticket.getContent())
            .ticketStart(ticket.getTicketStart())
            .ticketEnd(ticket.getTicketEnd())
            .days(ticket.getDays())
            .total(ticket.getTotal())
            .build()
    );
  }

  // 입력받은 id의 ticket 상세 정보가 담긴 dto 반환 메서드
  @Transactional(readOnly = true)
  public ResponseDto<?> getTicket(Long id, HttpServletRequest request) {

    Member member = isValipassengerAccess(request);
    if (null == member) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
              "로그인이 필요합니다.");
    }

    //해당 id를 가진 ticket이 있는지 확인. 있으면 해당 ticket을 받고 없으면 null
    Ticket ticket = isPresentTicket(id);
    if (null == ticket) {
      return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
    }

    // ticket과 연결된 passenger들의 List
    List<Passenger> passengerList = passengerRepository.findAllByTicket(ticket);
    List<PassengerResponseDto> passengerResponseDtoList = new ArrayList<>();

    //passenger 정보를 담은 dto 생성
    for (Passenger passenger : passengerList) {


      //costList와 passenger 정보를 담은 dto 생성 후 passengerList에 add
      passengerResponseDtoList.add(
              PassengerResponseDto.builder()
                      .id(passenger.getId())
                      .subTotal(passenger.getSubTotal())
                      .build()
      );
    }

    //ticket 정보를 담은 dto 반환
    return ResponseDto.success(
        TicketResponseDto.builder()
                .id(ticket.getId())
                .title(ticket.getTitle())
                .content(ticket.getContent())
                .ticketStart(ticket.getTicketStart())
                .ticketEnd(ticket.getTicketEnd())
                .days(ticket.getDays())
                .total(ticket.getTotal())
                .passengerList(passengerResponseDtoList)
                .build()
    );
  }

  //사용자가 작성한 모든 ticket의 목록 반환
  @Transactional(readOnly = true)
  public ResponseDto<?> getAllTicket(HttpServletRequest request) {

    Member member = isValipassengerAccess(request);
    if (null == member) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
              "로그인이 필요합니다.");
    }

    //목록에 표시할 간략한 정보만 담긴 Ticket dto 를 생성하여 반환
    //해당 member가 작성한 ticket의 리스트를 여행 시작을 기준으로 오름차순으로 정렬하여 반환한다.
    List<Ticket> ticketList = ticketRepository.findAllByMemberOrderByTicketStartAsc(member);
    List<TicketListResponseDto> dtoList = new ArrayList<>();

    for (Ticket ticket:ticketList) {
      dtoList.add(
              TicketListResponseDto.builder()
                      .id(ticket.getId())
                      .title(ticket.getTitle())
                      .ticketStart(ticket.getTicketStart())
                      .ticketEnd(ticket.getTicketEnd())
                      .build()
      );
    }
    return ResponseDto.success(dtoList);
  }

  //ticket 삭제 메서드. ticket에 포함된 하위 요소들도 모두 같이 삭제된다.
  @Transactional
  public ResponseDto<?> deleteTicket(Long id, HttpServletRequest request) {

    Member member = isValipassengerAccess(request);
    if (null == member) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
              "로그인이 필요합니다.");
    }

    Ticket ticket = isPresentTicket(id);
    if (null == ticket) {
      return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
    }

    //ticket과 연결된 passenger 삭제
    passengerRepository.deleteAllByTicket(ticket);
    //ticket 삭제
    ticketRepository.delete(ticket);

    return ResponseDto.success("delete success");
  }

  //ticket 존재 여부 확인 메서드. 해당 id의 ticket이 존재하면 ticket을 반환하고 없으면 null
  @Transactional(readOnly = true)
  public Ticket isPresentTicket(Long id) {
    Optional<Ticket> optionalTicket = ticketRepository.findById(id);
    return optionalTicket.orElse(null);
  }

  //토큰 유효성 검사하는 메서드. 정상적인 접근(토큰이 들어있으며 유효함)이면 해당 member 반환, 아니면 null
  public Member isValipassengerAccess(HttpServletRequest request){

    //헤더에 Authorization, RefrehToken 값이 없거나 유효하지 않으면 null
    if (null == request.getHeader("RefreshToken") ||
            !tokenProvider.valipassengerToken(request.getHeader("RefreshToken")) ||
            null == request.getHeader("Authorization")) {
      return null;
    }

    // 유효성 검사 후 해당하는 member 반환
    return tokenProvider.getMemberFromAuthentication();
  }

  //cost의 생성 및 삭제에 의한 비용 변경시 total 갱신하는 메서드
  public void uppassenger(int pay, Ticket ticket) {
    ticket.uppassenger(pay);
  }
}
