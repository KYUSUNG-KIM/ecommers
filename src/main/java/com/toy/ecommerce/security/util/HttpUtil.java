package com.toy.ecommerce.security.util;

import com.toy.ecommerce.global.constants.HttpConstants;
import com.toy.ecommerce.global.dto.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class HttpUtil {

    public <T> void sendResponse(HttpServletResponse response, HttpStatus status, String message, T data)
            throws IOException {

        response.setStatus(status.value());
        response.setContentType(HttpConstants.APPLICATION_JSON);

        CommonResponse result = new CommonResponse(data);

        if (! HttpStatus.OK.equals(status)) {
            result.setError(status, message);
        }

        PrintWriter w = response.getWriter();
        if (w != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            w.write(objectMapper.writeValueAsString(result));
            w.flush();
            w.close();
        }
    }
}