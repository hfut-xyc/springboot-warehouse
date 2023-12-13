package org.server.pojo.dto;

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

    public static Result ok(Object data, String message) {
        return new Result(0, message, data);
    }

    public static Result ok(String message) {
        return new Result(0, message, null);
    }

    public static Result error(String message) {
        return new Result(-1, message, null);
    }

}
