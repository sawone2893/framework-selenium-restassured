package com.framework.core.api;

import static io.restassured.RestAssured.given;

import java.util.stream.Collectors;

import com.framework.utils.EnvConfig;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredClient implements ApiClient {

	private RequestSpecification build(RequestData data) {

        RequestSpecification req = given()
                .baseUri(EnvConfig.get("baseUrl"));

        if (data.getHeaders() != null)
            req.headers(data.getHeaders());

        if (data.getQueryParams() != null)
            req.queryParams(data.getQueryParams());

        if (data.getPathParams() != null)
            req.pathParams(data.getPathParams());

        if (data.getBody() != null)
            req.body(data.getBody());

        return req.log().all();
    }

    private ApiResponse execute(RequestSpecification req, String method, String endpoint) {

        long start = System.currentTimeMillis();

        Response res = switch (method) {
            case "GET" -> req.get(endpoint);
            case "POST" -> req.post(endpoint);
            case "PUT" -> req.put(endpoint);
            case "DELETE" -> req.delete(endpoint);
            case "PATCH" -> req.patch(endpoint);
            default -> throw new RuntimeException("Invalid method");
        };

        long end = System.currentTimeMillis();

        return new ApiResponse(
                res.getStatusCode(),
                res.getBody().asString(),
                res.getHeaders().asList().stream()
                        .collect(Collectors.toMap(h -> h.getName(), h -> h.getValue())),
                end - start
        );
    }

    public ApiResponse get(String endpoint, RequestData data) {
        return execute(build(data), "GET", endpoint);
    }

    public ApiResponse post(String endpoint, RequestData data) {
        return execute(build(data), "POST", endpoint);
    }

    public ApiResponse put(String endpoint, RequestData data) {
        return execute(build(data), "PUT", endpoint);
    }

    public ApiResponse delete(String endpoint, RequestData data) {
        return execute(build(data), "DELETE", endpoint);
    }

    public ApiResponse patch(String endpoint, RequestData data) {
        return execute(build(data), "PATCH", endpoint);
    }

}
