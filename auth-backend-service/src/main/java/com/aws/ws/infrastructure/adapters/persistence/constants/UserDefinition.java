package com.aws.ws.infrastructure.adapters.persistence.constants;

public final class UserDefinition {
    // Constants for User entity attributes
    public static final String USER_ID = "userId";
    public static final String USER_FIRSTNAME = "userName";
    public static final String USER_LASTNAME = "userLastName";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_PASSWORD = "userPassword";
    public static final String USER_ROLE = "userRole";

    // Constants for Token entity attributes
    public static final String TOKEN_ID = "tokenId";
    public static final String TOKEN_USER_ID = "userId";
    public static final String TOKEN_JWT = "token";
    public static final String TOKEN_EXPIRATION_DATE = "expiresAt";
    public static final String TOKEN_CREATED_DATE = "issuedAt";
    public static final String TOKEN_ACTIVE = "active";

    private UserDefinition() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
