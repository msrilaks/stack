package com.stack.taskservice.advice;

import com.stack.library.model.error.ErrorCodes;
import com.stack.taskservice.exception.StackException;
import com.stack.taskservice.exception.TaskException;
import com.stack.library.model.error.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            ApplicationControllerAdvice.class.getName());

    @ExceptionHandler(StackException.class)
    public ResponseEntity<Error> stackException(final StackException e) {
        LOGGER.error(e.getApplicationError()
                      .getMessage(), e);
        return new ResponseEntity<Error>(e.getApplicationError(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskException.class)
    public ResponseEntity<Error> taskException(
            final TaskException e) {
        LOGGER.error(e.getApplicationError()
                      .getMessage(), e);
        return new ResponseEntity<Error>(e.getApplicationError(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> allException(final Exception e) {
        if (e.getCause() instanceof TaskException) {
            return taskException((TaskException) e.getCause());
        } else if (e.getCause() instanceof StackException) {
            return stackException((StackException) e.getCause());
        }
        LOGGER.error(ErrorCodes.APPLICATION_ERROR.getErrorMessage(), e);
        return new ResponseEntity<Error>(ErrorCodes.APPLICATION_ERROR.constructError(),
                                         HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
