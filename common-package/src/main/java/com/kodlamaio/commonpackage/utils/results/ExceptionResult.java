package com.kodlamaio.commonpackage.utils.results;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExceptionResult<T> {
    // public class ExceptionResult<T>
    // 22- 24 yok
    private LocalDateTime timestamp;
    private String type;
    private T message;

    public ExceptionResult(String type, T message) {
        timestamp = LocalDateTime.now();
        this.type = type;
        this.message = message;
    }
    
}