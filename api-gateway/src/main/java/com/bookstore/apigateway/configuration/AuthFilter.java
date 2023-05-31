package com.bookstore.apigateway.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.net.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    public static class Config {
        // empty class as I don't need any particular configuration
    }

    private AuthFilter() {
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getPath().toString();
            HttpMethod method = exchange.getRequest().getMethod();

            // Define the public API paths
            List<String> publicPaths = Arrays.asList(
                    "^/api/v1/articles/*$",
                    "^/api/v1/categories/*$",
                    "^/api/v1/product/*$",
                    "^/api/v1/productsAttrib/*$",
                    "^/api/v1/productsKeyword/*$",
                    "^/api/v1/productsPromotion/*$",
                    "^/api/v1/promotions/*$",
                    "^/api/v1/province/*$",
                    "^/api/v1/publisher/*$",
                    "^/api/v1/shipping/*$",
                    "^/api/v1/slides/*$",
                    "^/api/v1/statics/*$"
            );

            // Check if the request is for a public API
            boolean isPublicAPI = false;
            for (String publicPath : publicPaths) {
                if (path.matches(publicPath)) {
                    isPublicAPI = true;
                    break;
                }
            }

            if (!isPublicAPI) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing authorization information");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                String[] parts = authHeader.split(" ");

                if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                    throw new RuntimeException("Incorrect authorization structure");
                }

                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(parts[1]);
                String username = decodedJWT.getSubject();
                String role = decodedJWT.getClaim("role").asString();
                if(username == "" || username == null) {
                    throw new RuntimeException("Authorization error");
                }
                boolean isAuthorized = false;
                if (role.equals("user")) {
                    if (method == HttpMethod.GET) {
                        if (path.matches("^/api/v1/address(/.*)?$")
                                || path.matches("^/api/v1/bill(/.*)?$")
                                || path.matches("^/api/v1/billShipping(/.*)?$")
                                || path.matches("^/api/v1/comments(/.*)?$")
                                || path.matches("^/api/v1/contacts(/.*)?$")
                                || path.matches("^/api/v1/detailBill(/.*)?$")
                                || path.matches("^/api/v1/events(/.*)?$")
                                || path.matches("^/api/v1/userFavourite(/.*)?$")
                                || path.matches("^/api/v1/payments(/.*)?$")
                                || path.matches("^/api/v1/shoppingCart(/.*)?$")
                                || path.matches("^/api/v1/userPromotion(/.*)?$")) {
                            isAuthorized = true;
                        }
                    } else if (method == HttpMethod.POST) {
                        if (path.matches("^/api/v1/comments(/.*)?$")
                                || path.matches("^/api/v1/userFavourite(/.*)?$")
                                || path.matches("^/api/v1/payments(/.*)?$")
                                || path.matches("^/api/v1/shoppingCart(/.*)?$")) {
                            isAuthorized = true;
                        }
                    } else if (method == HttpMethod.PUT) {
                        if (path.matches("^/api/v1/comments(/.*)?$")
                                || path.matches("^/api/v1/shoppingCart(/.*)?$")) {
                            // Check if the user is the owner of the comment or cart
                            isAuthorized = true;
                        }
                    } else if (method == HttpMethod.DELETE) {
                        if (path.matches("^/api/v1/comments(/.*)?$")
                                || path.matches("^/api/v1/userFavourite(/.*)?$")
                                || path.matches("^/api/v1/shoppingCart(/.*)?$")) {
                            // Check if the user is the owner of the comment or favourite or cart
                            isAuthorized = true;
                        }
                    }
                }
                if (role.equals("admin")) {
                    isAuthorized = true;
                }
                if (!isAuthorized) {
                    throw new RuntimeException("Unauthorized access");
                }

                ServerHttpRequest request = exchange.getRequest().mutate().
                        header("X-auth-username", username).
                        build();
                return chain.filter(exchange.mutate().request(request).build());
            }
            return chain.filter(exchange);
        };
    }
}
