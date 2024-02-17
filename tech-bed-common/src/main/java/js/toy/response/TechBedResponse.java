package js.toy.response;

import lombok.Getter;

@Getter
public class TechBedResponse {
    private boolean success;
    private String code;
    private String message;
    private Object data;

    private TechBedResponse() {}

    private TechBedResponse(boolean success, String code, String message, Object data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 실패 인스턴스 정적 팩토리 메서드
    public static TechBedResponse fail(TechBedResponse techBedResponse) {
        return new TechBedResponse(
                false,
                techBedResponse.getCode(),
                techBedResponse.getMessage(),
                null
        );
    }

    // 성공 인스턴스 정적 팩토리 메서드
    public static TechBedResponse success(Object data) {
        ResponseCode success = ResponseCode.SUCCESS;

        return new TechBedResponse(
                true,
                success.getCode(),
                success.getMessage(),
                data
        );
    }
}
