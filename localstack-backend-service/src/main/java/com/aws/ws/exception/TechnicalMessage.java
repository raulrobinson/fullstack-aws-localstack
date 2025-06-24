package com.aws.ws.exception;

import lombok.Getter;

@Getter
public enum TechnicalMessage {
    INTERNAL_SERVER_ERROR("500", "Internal server error", ""),
    INTERNAL_ERROR_IN_ADAPTERS("503", "Internal error in adapters", ""),
    MINIMUM_OR_MAXIMUM_CAPACITY("400", "A capability must have 3 to 20 unique technologies.", ""),
    BAD_REQUEST("400", "Bad request", ""),
    NOT_FOUND("404", "Not found", ""),
    NO_CONTENT("204", "No content", ""),
    INVALID_REQUEST("400", "Request null or incomplete", ""),
    ALREADY_EXISTS("409", "Already exists", ""),
    NOT_ONLY_NUMBERS("400", "The field must contain only numbers", ""),
    NAME_CHARACTER_LIMIT("400", "Name must be between 3 and 50 characters", ""),
    DESCRIPTION_CHARACTER_LIMIT("400", "Description must be between 3 and 100 characters", ""),
    CATALOG_NOT_FOUND("404", "Catalog not found", ""),
    CATALOG_DELETION_FAILED("500", "Catalog deletion failed", ""),
    CATALOG_ALREADY_EXISTS("409", "Catalog already exists", ""),
    CATALOG_CREATION_FAILED("500", "Catalog creation failed", ""),
    UNKNOWN_ERROR("500", "Unknown error occurred", ""),
    CATALOG_CREATED("201", "Catalog created successfully", ""),
    INVALID_PARAMETERS("400", "Invalid parameters provided", ""),
    INTERNAL_ERROR("500", "An internal error occurred", ""),
    CATALOG_ERROR("500", "An error occurred while processing the technology", ""),
    DATABASE_ERROR("500", "Database error occurred", ""),
    X_MESSAGE_ID("X-Message-ID", "Unique identifier for the message", ""),
    UNAUTHORIZED("401", "Unauthorized access", ""),
    ADAPTER_ERROR("500", "Error in adapter processing", "");

    private final String code;
    private final String message;
    private final String parameter;

    TechnicalMessage(String code, String message, String parameter) {
        this.code = code;
        this.message = message;
        this.parameter = parameter;
    }
}
