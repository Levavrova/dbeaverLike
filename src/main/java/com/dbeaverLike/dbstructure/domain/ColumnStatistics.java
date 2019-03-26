package com.dbeaverLike.dbstructure.domain;

import lombok.Data;

@Data
public class ColumnStatistics{
    
    Object minValue;
    Object maxValue;
    Object avgValue;
    Object medianValue;
}