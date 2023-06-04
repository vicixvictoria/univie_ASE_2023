package com.ase.apigateway;

import com.ase.common.user.User;
import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

@Component
public class JWTAuthorizationFilter implements GatewayFilterFactory<Void> {

    private static final String HEADER = "Authorization";
    private static final String HEADER_BEARER = "Bearer";

    @Value("${config.user-token-endpoint}")
    private String userTokenEndpoint;

    /**
     * Extracts the JWT from the header. If the header does not contain the JWT token, an empty
     * Optional is returned, otherwise an Optional containing the token is returned
     *
     * @param exchange the exchange to which a request was made
     * @return the extracted token
     */
    private static Optional<String> extractTokenFromHeader(ServerWebExchange exchange) {
        Collection<String> headers = exchange.getRequest().getHeaders().get(HEADER);

        if (headers == null || headers.size() != 1) {
            return Optional.empty();
        }

        String header = headers.stream().findFirst().get();

        String[] headerContent = header.split(" ");

        if (headerContent.length != 2) {
            return Optional.empty();
        }

        if (!headerContent[0].equals(HEADER_BEARER)) {
            return Optional.empty();
        }

        return Optional.of(headerContent[1]);
    }


    @Override
    public GatewayFilter apply(Void config) {
        return (exchange, chain) -> {
            // get the JWT token from request
            Optional<String> potentialToken = extractTokenFromHeader(exchange);
            if (potentialToken.isEmpty()) {
                // if the request did not contain a JWT token, exit
                exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                return exchange.getResponse().setComplete();
            }
            String token = potentialToken.get();

            return WebClient.create()
                    // generate a post request
                    .post()
                    // to the endpoint which returns a User object, given a JWT
                    .uri(userTokenEndpoint)
                    // send the token as body to this request
                    .body(BodyInserters.fromValue(token))
                    // map the result to the User class
                    .exchangeToMono(clientResponse -> clientResponse.bodyToMono(User.class))
                    // process the result
                    .flatMap(user -> {
                        // if the user was empty, exit
                        if (user.id() == null) {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        }
                        // otherwise continue with the filter chain
                        return chain.filter(exchange);
                    });
        };
    }
}
