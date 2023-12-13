package org.server.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderChart implements Serializable {

    @JsonFormat(pattern="yyyy-MM-dd", timezone = "UTC+8")
    private Date date;
    private int count;
}
