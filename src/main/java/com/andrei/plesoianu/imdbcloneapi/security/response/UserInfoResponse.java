package com.andrei.plesoianu.imdbcloneapi.security.response;

import com.andrei.plesoianu.imdbcloneapi.security.services.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private Long id;
    private String username;
    private List<String> roles;
    private String token;

    public static UserInfoResponse of(UserDetailsImpl userDetails) {
        List<String> userRoles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new UserInfoResponse(
                userDetails.getId(),
                userDetails.getUsername(),
                userRoles,
                ""
        );
    }
}
