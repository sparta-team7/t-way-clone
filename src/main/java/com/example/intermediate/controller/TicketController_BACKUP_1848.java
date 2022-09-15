package com.example.intermediate.controller;

import com.example.intermediate.dto.request.TicketRequestDto;
import com.example.intermediate.dto.response.ResponseDto;
import com.example.intermediate.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TicketController {

    private final TicketService ticketService;

    //여행 상세 페이지 조회하기
    @RequestMapping(value = "/api/ticket", method = RequestMethod.GET)
    public ResponseDto<?> SearchTicket(@RequestParam(value = "depAirportId") String ticketStartRequestDto,
                                       @RequestParam(value = "depPlandTime") String ticketPlandTimeRequestDto,
                                       HttpServletRequest httpServletRequest
    ) throws IOException, ParseException, java.text.ParseException {


        //return ResponseDto.success(ticketPlandTimeRequestDto);
        return ticketService.SearchTicket(ticketStartRequestDto,ticketPlandTimeRequestDto,httpServletRequest);

    }
    //티켓 정보 입력
    @RequestMapping(value = "/api/auth/booking", method = RequestMethod.POST)
    public ResponseDto<?> creatTicket(@RequestBody TicketRequestDto requestDto,HttpServletRequest request) {
        return ticketService.createTicket(requestDto,request);
    }
    //나의 예약 조회하기
    @RequestMapping(value = "/api/auth/mybooking", method = RequestMethod.GET)
    public ResponseDto<?> getTicket (@RequestParam String bookingNum) {
        System.out.println(bookingNum);
        return ticketService.getTicket(bookingNum);
    }

    //여행 상세 페이지 조회하기



    }






  //여행 삭제하기
//  @RequestMapping(value = "/ticket/{id}", method = RequestMethod.DELETE)
//  public ResponseDto<?> deleteTicket(@PathVariable Long id,
//                                   HttpServletRequest request) {
//    return ticketService.deleteTicket(id, request);
//  }
