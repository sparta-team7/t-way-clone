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
@RestController("/api")
public class MemberController {

  private final MemberService memberService;
  //회원가입
  @PostMapping( "/api/member/signup")  //회원가입 api
  public ResponseDto<?> signup(@RequestBody @Valid MemberRequestDto requestDto) {
    return memberService.createMember(requestDto);
  }

  @PostMapping("/api/member/login") //로그인 api
  public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto requestDto,
      HttpServletResponse response
  ) {
    return memberService.login(requestDto, response);
  }

  @PostMapping( "/api/member/logout")  //로그아웃 api
  public ResponseDto<?> logout(HttpServletRequest request) {
    return memberService.logout(request);
  }


  @GetMapping( "/api/member/kakao/callback")  //로그아웃 api
  public ResponseDto<?> kakaoCallback(@RequestParam String code, HttpServletResponse response) throws IOException {
    System.out.println(code);
    return memberService.getKakaoAccessToken(code, response);
  }
}