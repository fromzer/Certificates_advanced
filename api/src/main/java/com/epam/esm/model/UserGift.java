package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserGift {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
}
