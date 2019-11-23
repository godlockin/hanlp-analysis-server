package com.common.constants;

public enum ResultEnum implements BaseEnum {

    SUCCESS(0, "成功")
    ,FAILURE(1, "失败")
    ,PARAMETER_CHECK(12, "参数校验失败")
    ,PARAMETER_CHECK_EMPTY(121, "参数为空")
    ,PARAMETER_CHECK_ORIGINAL_URL(122, "源地址为空")
    ;

    private Integer code;
    private String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
