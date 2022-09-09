package com.example.intermediate.controller;


import com.example.intermediate.dto.response.ResponseDto;
import com.example.intermediate.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PassengerController {

  private final PassengerService passengerService;

  //입력 받은 id passenger 조회
  @RequestMapping(value = "/passenger/{id}", method = RequestMethod.GET)
  public ResponseDto<?> getPassenger(@PathVariable Long id) {
    return passengerService.getPassenger(id);
  }


  //입력 받은 id의 Ticket 전체 passenger 조회
  @RequestMapping(value = "/passengers/{id}", method = RequestMethod.GET)
  public ResponseDto<?> getAllPassenger(@PathVariable Long id) {
    return passengerService.getAllPassenger(id);
  }
}
