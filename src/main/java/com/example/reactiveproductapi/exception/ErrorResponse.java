package com.example.reactiveproductapi.exception;

import java.time.Instant;

/**
 * Represents a consistent API error response structure.
 */
public record ErrorResponse(String message, int status, Instant timestamp) {}
