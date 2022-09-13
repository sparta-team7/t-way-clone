package com.example.intermediate.controller;

import com.example.intermediate.dto.request.LoginRequestDto;
import com.example.intermediate.dto.request.MemberRequestDto;
import com.example.intermediate.dto.response.ResponseDto;
import com.example.intermediate.service.MemberService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class MemberController {

  private final MemberService memberService;

  @RequestMapping(value = "/api/member/signup", method = RequestMethod.POST)  //회원가입 api
  public ResponseDto<?> signup(@RequestBody @Valid MemberRequestDto requestDto) {
    return memberService.createMember(requestDto);
  }

  @RequestMapping(value = "/api/member/login", method = RequestMethod.POST) //로그인 api
  public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto requestDto,
      HttpServletResponse response
  ) {
    return memberService.login(requestDto, response);
  }

  @RequestMapping(value = "/api/member/logout", method = RequestMethod.POST)  //로그아웃 api
  public ResponseDto<?> logout(HttpServletRequest request) {
    return memberService.logout(request);
  }


  @RequestMapping(value = "/api/member/kakao/callback", method = RequestMethod.GET)  //로그아웃 api
  public ResponseDto<?> kakaoCallback(@RequestParam String code, HttpServletResponse response) throws IOException {
    System.out.println(code);
    return memberService.getKakaoAccessToken(code, response);
  }
}