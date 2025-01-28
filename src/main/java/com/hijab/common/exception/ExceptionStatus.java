package com.hijab.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionStatus {
    DUPLICATED_USERNAME(409, "이미 사용중인 아이디입니다."),
    DUPLICATED_NICKNAME(408, "이미 사용중인 닉네임입니다."),
    DUPLICATED_EMAIL(407, "이미 사용중인 이메일입니다."),
    DUPLICATED_PHONENUMBER(406, "이미 사용중인 휴대폰번호입니다."),
    SIGNUP_WRONG_USERNAME(409, "최소 4자 이상, 10자 이하이며, 영문과 숫자만 입력하세요."),
    WRONG_EMAIL(404, "이메일을 잘못 입력 하였거나 등록되지 않은 이메일 입니다."),

    WRONG_PASSWORD(400, "잘못된 비밀번호 입니다."),
    AUTHENTICATION(500, "인증 실패"),
    WRONG_PROFILE(404, "프로필이 존재하지 않습니다."),

    AUTHORIZATION_ERROR(401, "권한이 없습니다."),
    DELETED_BOARD(404, "Not Found"),

    NOT_ENOUGH_POINT(400, "포인트가 부족합니다."),

    BAD_WORDS_FILTERED(400, "사용할 수 없는 단어가 확인되었습니다."),

    WRONG_DATE(400, "현재보다 과거의 날짜입니다. 현재 혹은 미래의 날짜로 다시 입력하세요."),

    /**
     * Personal Color Analysis
     */
    SERVER_ERROR_PERSONAL_COLOR(500, "퍼스널 컬러 분석 과정에서 문제가 발생했습니다.");

    private final int StatusCode;
    private final String message;
}
