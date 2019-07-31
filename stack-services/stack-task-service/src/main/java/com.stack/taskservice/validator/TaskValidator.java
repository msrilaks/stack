package com.stack.taskservice.validator;

import com.stack.library.model.error.ErrorCodes;
import com.stack.taskservice.exception.TaskException;
import com.stack.taskservice.model.Task;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TaskValidator implements ConstraintValidator<TaskConstraint, Task> {
    @Override
    public void initialize(TaskConstraint constraint) {
    }

    @Override
    public boolean isValid(Task task, ConstraintValidatorContext cxt) {
        if (task.getUserId() == null || task.getUserId().isEmpty()) {
            throw new TaskException(ErrorCodes.TASK_USER_ID_INVALID.constructError());
        }
        if (task.getCreatedByUserId() == null || task.getCreatedByUserId().isEmpty()) {
            throw new TaskException(
                    ErrorCodes.TASK_CREATED_BY_USER_ID_INVALID.constructError());
        }
        return true;
    }

}
