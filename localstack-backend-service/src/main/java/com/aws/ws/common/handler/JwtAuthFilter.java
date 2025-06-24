package com.aws.ws.common.handler;

//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//@Component
public class JwtAuthFilter {//implements WebFilter {

//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        return ReactiveSecurityContextHolder.getContext()
//                .flatMap(securityContext -> {
//                    AbstractAuthenticationToken authentication = (AbstractAuthenticationToken) securityContext.getAuthentication();
//
//                    if (authentication instanceof JwtAuthenticationToken jwtAuth) {
//                        Jwt jwt = jwtAuth.getToken();
//
//                        // Puedes extraer información personalizada del JWT
//                        String username = jwt.getClaimAsString("preferred_username");
//                        String email = jwt.getClaimAsString("email");
//
//                        // Aquí puedes loggear o rechazar si es necesario
//                        System.out.println("Usuario autenticado: " + username + " - " + email);
//                    }
//
//                    return chain.filter(exchange);
//                })
//                .switchIfEmpty(chain.filter(exchange)); // continuar si no hay contexto (por ejemplo, ruta pública)
//    }

//    public static HandlerFilterFunction<ServerResponse, ServerResponse> authorize(GlobalErrorHandler globalErrorHandler) {
//        return (request, next) -> {
//            String authHeader = request.headers().firstHeader(HttpHeaders.AUTHORIZATION);
//
//            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//                return globalErrorHandler.handleAuth("Unauthorized access");
//            }
//
//            String token = authHeader.substring(7); // Remove "Bearer "
//
//            if (!isValidToken(token)) {
//                return globalErrorHandler.handleAuth("Invalid or expired token");
//            }
//
//            return next.handle(request);
//        };
//    }
//
//    private static boolean isValidToken(String token) {
//        // Aquí puedes validar con tu lógica JWT real.
//        // Por simplicidad, aceptamos un token hardcodeado:
//        return token.equals("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJQTnJ6aDR3SDcxTF82elFsQWl0QlF4NjBsMG1seU5YQ1JXZFdpQmwwNmh3In0.eyJleHAiOjE3NDk3Njk5NDcsImlhdCI6MTc0OTc2OTY0NywianRpIjoiMzc4MGM2MDItNjMzNS00NDQzLWFhNmYtMTBjYTI2MmI0OGVlIiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay1xYS50cmFuc2VyLmRpZ2l0YWwvcmVhbG1zL3VzZXJzIiwiYXVkIjpbInJlYWxtLW1hbmFnZW1lbnQiLCJicm9rZXIiLCJhY2NvdW50Il0sInN1YiI6ImUxNzNjZWJkLWVjYzgtNDczZS04MWUyLTRjNjViZThhNDg1NyIsInR5cCI6IkJlYXJlciIsImF6cCI6InRyYW5zZXItdXNlcnMiLCJzaWQiOiJiOGIyYWU5Yi01ZTg4LTQ2MzAtOGRhMy00NmFhNDJmMmNkMDgiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbIioiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJkZWZhdWx0LXJvbGVzLXVzZXJzIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJ2aWV3LWlkZW50aXR5LXByb3ZpZGVycyIsInZpZXctcmVhbG0iLCJtYW5hZ2UtaWRlbnRpdHktcHJvdmlkZXJzIiwiaW1wZXJzb25hdGlvbiIsInJlYWxtLWFkbWluIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInF1ZXJ5LXJlYWxtcyIsInZpZXctYXV0aG9yaXphdGlvbiIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS11c2VycyIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIiwicXVlcnktZ3JvdXBzIl19LCJ0cmFuc2VyLXVzZXJzIjp7InJvbGVzIjpbImJ1c2luZXNzLXVwZGF0ZSIsInZlaGljbGVzLXVwZGF0ZSIsIm1lbnUtc2VydmljZXMtMDIiLCJsaXF1aWRhdGlvbnMtdXBkYXRlIiwibWVudS1zZXJ2aWNlcy0wMSIsIm1lbnUtc2VydmljZXMtMDMiLCJtZW51LWJ1c2luZXNzLTA0IiwibWVudS1idXNpbmVzcy0wMyIsIm1lbnUtcm91dGVzLTAxIiwiZHJpdmVycy11cGRhdGUiLCJyb3V0ZXMtdXBkYXRlIiwibWVudS12ZWhpY2xlcy0wMiIsIm1lbnUtdmVoaWNsZXMtMDEiLCJtZW51LWRyaXZlcnMtMDIiLCJtZW51LWRyaXZlcnMtMDMiLCJ1bWFfcHJvdGVjdGlvbiIsIm1lbnUtZHJpdmVycy0wMSIsImNvbXBsaWFuY2VzLXVwZGF0ZSIsImZ1ZWwtbWFuYWdlci11cGRhdGUiLCJiaWxsaW5ncy11cGRhdGUiLCJyZXNvdXJjZXMtdXBkYXRlIiwibWVudS1jbGllbnRzLTAxIiwibWVudS1jbGllbnRzLTAyIiwibWVudS1jbGllbnRzLTAzIiwic2VydmljZXMtdXBkYXRlIiwidG9sbHMtdXBkYXRlIiwiY2xpZW50cy11cGRhdGUiXX0sImJyb2tlciI6eyJyb2xlcyI6WyJyZWFkLXRva2VuIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJ2aWV3LWdyb3VwcyIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwibWFuYWdlLWNvbnNlbnQiLCJkZWxldGUtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6IlF1YWxpdHkgQXNzdXJhbmNlIiwicHJlZmVycmVkX3VzZXJuYW1lIjoicWEudGVzdCIsImdpdmVuX25hbWUiOiJRdWFsaXR5IiwiZmFtaWx5X25hbWUiOiJBc3N1cmFuY2UiLCJlbWFpbCI6InFhLnRlc3RAcHJhZ21hLmNvbS5jbyJ9.kIcBrI8p8LZjBb6kPiMkzbkxxmWNKftuPm1WB8GUXrkfxj_wABOZ4H1i_cNOgc776IB4kMVvDojG8qgZH7gE-Xe4ZNToBvVU4jDFwvonMpF4Gb-YOSi7i5twAyFrBSo0cmjduR5yuoF6zBL86dNKrNZungNhidLsg94qxOPG9LtYyCV7UjHVXF0nU6Eq3FmwZJ7EfVhH5c0halw0M6zB3rt8fTsOURO_dnT4WlUEk4_fTyzp1iGQxIBtIztziDhi8AhpODX8dQGUkLyhdgrO2456DHAHBQ1XnISbzqgszNXvqITTypSE3l6cyvZZaxK687QDN1_KzRZPck3IJlDBqQ");
//    }
}

