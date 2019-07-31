package com.stack.library.model.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Error {
    private int    code;
    private String message;
    private String description;
}
