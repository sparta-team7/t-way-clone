package com.example.intermediate.Enum;

import lombok.Getter;

@Getter

 public enum NameEnum {
    NAARKSS("GMP","김포/서울"),
    NAARKTN("TAE","대구"),
    NAARKTU("CJJ","청주"),
    NAARKJY("RSU","여수");
    private final String airPortCode;
    private final String airPortName;

    NameEnum(String airPortCode, String airPortName) {
        this.airPortCode = airPortCode;
        this.airPortName = airPortName;
    }

    public String getName() { // 문자를 받아오는 함수
        return airPortName;
    }

    public String getAirCode(){
        return airPortCode;
    }
}
