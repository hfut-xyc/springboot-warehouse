package org.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderChart {

    @JsonFormat(pattern="yyyy-MM-dd", timezone = "UTC+8")
    private Date date;
    private int count;
}
