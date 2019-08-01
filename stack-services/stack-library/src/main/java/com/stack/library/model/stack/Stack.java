package com.stack.library.model.stack;


import com.stack.library.validator.StackConstraint;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@StackConstraint
@Builder(toBuilder = true)
public class Stack {
    @Id
    private String            id;
    private String            userId;
    private Map<String, Task> tasks;
    @CreatedDate
    private Date              createdDate;
    @LastModifiedDate
    private Date              lastModifiedDate;

}