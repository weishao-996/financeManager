package com.demo.finance.pojo.qo;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class CompareQO {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endTime;
}
