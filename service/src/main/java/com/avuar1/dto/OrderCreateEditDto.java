package com.avuar1.dto;

import com.avuar1.entity.OrderStatus;
import java.time.LocalDateTime;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;

@Value
@FieldNameConstants
public class OrderCreateEditDto {

    Integer userId;
    Integer carId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime beginTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime endTime;
    OrderStatus status;
    String message;
}
