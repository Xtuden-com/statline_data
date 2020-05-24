package com.statline.api.databackend.utils;

import com.google.cloud.secretmanager.v1.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
    Verify that the header contains an api key from google secret manager
*/

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class VerifyAPIKey implements Filter {
    public static Set<String> getGoogleKeys() throws Exception{
        Set<String> keys;
        try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
            SecretVersionName secretVersionName = SecretVersionName.of(System.getenv("GOOGLE_CLOUD_PROJECT"),
                    "api_keys","latest");
            SecretVersion version = client.getSecretVersion(secretVersionName);
            AccessSecretVersionResponse response = client.accessSecretVersion(version.getName());
            String data = response.getPayload().getData().toStringUtf8();
            keys = new HashSet<>(Arrays.asList(data.split(",")));
        }
        return keys;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Set<String> googleApiKeys;
        try {
            googleApiKeys = getGoogleKeys();
        }
        catch (Exception e){
            throw new ServletException("Unable to check API Key");
        }
        String api_key = request.getHeader("api_key");
        if (api_key == null || !googleApiKeys.contains(api_key)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
