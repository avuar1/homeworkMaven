package com.avuar1.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class CarCategoryCreateEditDto {

    String category;
    Double dayPrice;
}
