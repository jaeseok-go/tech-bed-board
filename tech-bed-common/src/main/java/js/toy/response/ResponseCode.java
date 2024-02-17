package js.toy.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 요청 결과에 대한 응답 내용을 담을 enum 클래스
 * @return
 *  - code: prefix로 TB, infix로 3자리 알파벳, postfix로 4자리 숫자를 사용한다. delimiter는 _를 사용한다.
 *  - message: 응답 내용을 표현할 메세지 문자열
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS("TB_SCS_0001", "요청이 정상적으로 처리되었습니다.");

    private String code;
    private String message;
}
