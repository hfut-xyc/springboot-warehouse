package org.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(1, message, data);
    }

    public static <T> Result<T> success(String message) {
        return new Result<>(1, message, null);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(0, message, null);
    }

}
