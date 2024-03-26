package com.mountblue.blogapp.security;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WebUser {
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String username;
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String name;
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String email;
    @NotNull(message = "is required")
    @Size(min = 3, message = "is required")
    private String password;
}
