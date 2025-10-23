package smbedition.common;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public class RequestSpecFactory {
    private static final ThreadLocal<RequestSpecification> SPEC = new ThreadLocal<>();

    public static void init() {
        // No base URL here - we'll use full URLs in ApiClient
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setContentType(JSON)
                .setRelaxedHTTPSValidation(); // For different environments

        SPEC.set(builder.build());
    }

    public static RequestSpecification get() {
        RequestSpecification spec = SPEC.get();
        if (spec == null) {
            throw new IllegalStateException("RequestSpecification not initialized. Call init() first.");
        }
        return spec;
    }

    public static void remove() {
        SPEC.remove();
    }
}