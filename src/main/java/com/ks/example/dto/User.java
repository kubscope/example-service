package com.ks.example.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    Long id;
    String name;
    boolean active;
}
