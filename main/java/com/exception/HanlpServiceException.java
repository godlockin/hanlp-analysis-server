package com.exception;

import com.common.constants.ResultEnum;
import lombok.Data;

@Data
public class HanlpServiceException extends Exception {

    private Integer code;

    public HanlpServiceException(ResultEnum result) {
        super(result.getMessage());
        this.code = result.getCode();
    }

    public HanlpServiceException(ResultEnum result, String message) {
        super(message);
        this.code = result.getCode();
    }

    public HanlpServiceException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}
