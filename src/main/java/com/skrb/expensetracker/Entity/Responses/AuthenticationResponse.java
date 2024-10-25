package com.skrb.expensetracker.Entity.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse implements JsonResponse {
    private String token;
    private String username;
    private String msg;
}
