package com.stack.library.model.stack;

import com.google.api.client.util.DateTime;
import lombok.*;

@ToString
@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StackEvent {
    DateTime start;
    DateTime  end;
    String location;
}
