package com.example.intermediate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PassengerResponseDto {

  //성별
  private String gender;

  //국적
  private String country;

  //이름
  private String name;

  //생일
  private String birth;

  private String email;

  private String number;


}
