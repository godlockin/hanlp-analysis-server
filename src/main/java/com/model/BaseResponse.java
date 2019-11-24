package com.model;

import com.common.constants.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class BaseResponse {

    private Integer code;
    private String message;
    private List<Item> items;

    public BaseResponse() {
        this(ResultEnum.SUCCESS);
    }

    public BaseResponse(ResultEnum rEnum) {
        this.code = rEnum.getCode();
        this.message = rEnum.getMessage();
        this.items = new ArrayList<>();
    }

    public BaseResponse setCode(Integer code) {
        this.code = code;
        return this;
    }

    public BaseResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public BaseResponse setItems(List<Item> items) {
        this.items = items;
        return this;
    }

    public BaseResponse setResult(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
        this.items = new ArrayList<>();
        return this;
    }
}
