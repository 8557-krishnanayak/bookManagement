package com.godigit.bookmybook.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    @Builder.Default
    private boolean success = false;

    @Builder.Default
    private String message = "Operation completed successfully";

    private Object result;
}
