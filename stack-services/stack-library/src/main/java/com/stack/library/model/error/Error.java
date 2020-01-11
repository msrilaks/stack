package com.stack.library.model.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Error {
    private int    code;
    private String message;
    private String description;

    public Error appendMessage(String _message) {
        message = message + _message;
        return this;
    }
}
