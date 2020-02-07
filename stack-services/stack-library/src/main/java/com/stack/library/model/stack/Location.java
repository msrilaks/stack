package com.stack.library.model.stack;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class Location {
    private double lat;
    private double lng;
}
