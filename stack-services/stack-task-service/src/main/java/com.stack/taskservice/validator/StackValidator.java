package com.stack.taskservice.validator;

import com.stack.library.model.error.ErrorCodes;
import com.stack.library.model.exception.StackException;
import com.stack.taskservice.model.Stack;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StackValidator implements ConstraintValidator<StackConstraint, Stack> {
    @Override
    public void initialize(StackConstraint constraint) {
    }

    @Override
    public boolean isValid(Stack stack, ConstraintValidatorContext cxt) {
        if (stack.getUserId() == null || stack.getUserId()
                                              .isEmpty()) {
            throw new StackException(ErrorCodes.STACK_USER_ID_INVALID.constructError());
        }
        return true;
    }

}
