package com.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private Integer code;
    private String message;
    private Object data;

    public static Result ok(String message, Object data) {
        return new Result(0, message, data);
    }

    public static Result ok(String message) {
        return new Result(0, message, null);
    }

    public static Result error(String message) {
        return new Result(-1, message, null);
    }

}
