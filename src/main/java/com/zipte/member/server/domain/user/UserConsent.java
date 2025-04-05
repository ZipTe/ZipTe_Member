package com.zipte.member.server.domain.user;

public class UserConsent {

    private boolean personalInfoRequired;  // 필수 개인정보 동의
    private boolean termsRequired;         // 필수 이용약관 동의

    private boolean dataSharingOptional;   // 선택 개인정보 제공 동의
    private boolean adsOptional;           // 선택 광고 수신 동의
    private boolean marketingEmailsOptional; // 선택 이메일 마케팅 동의
    private boolean marketingSMSOptional;  // 선택 문자 마케팅 동의

}
