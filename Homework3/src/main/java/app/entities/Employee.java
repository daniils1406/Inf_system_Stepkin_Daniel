package app.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Employee {
    int id;
    String firstName;
    String last_name;
    String birthday;
    String gender;
    String education;
}
