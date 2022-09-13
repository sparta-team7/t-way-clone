package com.example.intermediate.service;

import com.example.intermediate.dto.response.AirportResponseDto;
import com.example.intermediate.dto.response.ResponseDto;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.PassengerRepository;
import com.example.intermediate.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

  private final TokenProvider tokenProvider;
  private final TicketRepository ticketRepository;
  private final PassengerRepository passengerRepository;

  //ticket 생성 메서드
//  @Transactional
//  public ResponseDto<?> createTicket(HttpServletRequest request) {
//
//    //토큰 유효성 검사, 유효하면 해당 member 가져옴 아니면 null
//    Member member = isValipassengerAccess(request);
//    if (null == member) {
//      return ResponseDto.fail("MEMBER_NOT_FOUND",
//              "로그인이 필요합니다.");
//    }
//
//    //dto에 담긴 정보로 Ticket 생성
//    Ticket ticket = Ticket.builder().build();
//
//    ticketRepository.save(ticket);
//
//    return ResponseDto.success(ticket);
//  }

  // 입력받은  ticket 검색 정보가 담긴 dto 반환 메서드
  @Transactional(readOnly = true)
  public ResponseDto<?> SearchTicket(String ticketAirportIdRequestDto, String ticketPlandTimeRequestDto) throws IOException, ParseException, java.text.ParseException {
    /*URL*/
    String urlBuilder = "http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getFlightOpratInfoList" + "?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=fwYR5PK7M3FDvT8cwjvXBGHqc5ycplW8Zb9OE8RAb8ASE%2BxQ1qrd6jKlPoeNXxrMwCMX4F69yIEmcpZ071Rqwg%3D%3D" + /*Service Key*/
            "&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("1", StandardCharsets.UTF_8) + /*페이지번호*/
            "&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("10", StandardCharsets.UTF_8) + /*한 페이지 결과 수*/
            "&" + URLEncoder.encode("_type", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("json", StandardCharsets.UTF_8) + /*데이터 타입(xml, json)*/
            "&" + URLEncoder.encode("depAirportId", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(ticketAirportIdRequestDto, StandardCharsets.UTF_8) + /*출발공항ID*/
            "&" + URLEncoder.encode("arrAirportId", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("NAARKPC", StandardCharsets.UTF_8) + /*도착공항ID*/
            "&" + URLEncoder.encode("depPlandTime", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(ticketPlandTimeRequestDto, StandardCharsets.UTF_8) + /*출발일(YYYYMMDD)*/
            "&" + URLEncoder.encode("airlineId", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("AAR", StandardCharsets.UTF_8); /*항공사ID*/
    URL url = new URL(urlBuilder);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Content-type", "application/json");
    System.out.println("Response code: " + conn.getResponseCode());
    BufferedReader rd;
    if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
      rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    } else {
      rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
    }
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = rd.readLine()) != null) {
      sb.append(line);
    }
    System.out.println(sb);
    rd.close();
    conn.disconnect();
    List<AirportResponseDto> responseDtoList = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    JSONObject jsonObject = (JSONObject)jsonParser.parse(sb.toString());
    JSONObject response = (JSONObject)jsonObject.get("response");
    JSONObject body = (JSONObject)response.get("body");
    JSONObject items = (JSONObject)body.get("items");
    JSONArray airportList = (JSONArray) items.get("item");
    for (Object o : airportList) {
      JSONObject airport = (JSONObject) o;
      // startTime, endTime 설정
      String startTime = String.valueOf(airport.get("depPlandTime")) ;
      String endTime = String.valueOf(airport.get("arrPlandTime"));

      //airpost에서 가져온 데이터를 날짜 형식으로 바꿈
      SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
      Date startDate = format.parse(startTime);
      Date endDate = format.parse(endTime);
      //시간
      Long calHour = ((endDate.getTime() - startDate.getTime())/(1000*60*60));
      //분
      Long calMinutes = ((endDate.getTime() - startDate.getTime())/(1000*60)%60);

      //리스트에 추가
      responseDtoList.add(AirportResponseDto.builder()
              .endPoint(airport.get("arrAirportNm").toString())
              .endTime(String.valueOf(endTime))
              .startPoint(airport.get("depAirportNm").toString())
              .startTime(String.valueOf(startTime))
              .charge(Integer.parseInt(airport.get("economyCharge").toString()))
              .flyNum(airport.get("vihicleId").toString())
              .takeTime(calHour +"시간"+calMinutes+"분")

              .build());
    }
    return ResponseDto.success(responseDtoList);
  }




//  //ticket 삭제 메서드. ticket에 포함된 하위 요소들도 모두 같이 삭제된다.
//  @Transactional
//  public ResponseDto<?> deleteTicket(Long id, HttpServletRequest request) {
//
//    Member member = isValipassengerAccess(request);
//    if (null == member) {
//      return ResponseDto.fail("MEMBER_NOT_FOUND",
//              "로그인이 필요합니다.");
//    }
//
//    Ticket ticket = isPresentTicket(id);
//    if (null == ticket) {
//      return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
//    }
//
//    //ticket과 연결된 passenger 삭제
//    passengerRepository.deleteAllByTicket(ticket);
//    //ticket 삭제
//    ticketRepository.delete(ticket);
//
//    return ResponseDto.success("delete success");
//  }
//
//  //ticket 존재 여부 확인 메서드. 해당 id의 ticket이 존재하면 ticket을 반환하고 없으면 null
//  @Transactional(readOnly = true)
//  public Ticket isPresentTicket(Long id) {
//    Optional<Ticket> optionalTicket = ticketRepository.findById(id);
//    return optionalTicket.orElse(null);
//  }
//
//  //토큰 유효성 검사하는 메서드. 정상적인 접근(토큰이 들어있으며 유효함)이면 해당 member 반환, 아니면 null
//  public Member isValipassengerAccess(HttpServletRequest request){
//
//    //헤더에 Authorization, RefrehToken 값이 없거나 유효하지 않으면 null
//    if (null == request.getHeader("RefreshToken") ||
//            !tokenProvider.valipassengerToken(request.getHeader("RefreshToken")) ||
//            null == request.getHeader("Authorization")) {
//      return null;
//    }
//
//    // 유효성 검사 후 해당하는 member 반환
//    return tokenProvider.getMemberFromAuthentication();
//  }



}
