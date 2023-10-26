package com.unext.capstone.promotionsandoffers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST, reason="Invalid Usage Details")
public class UsageInvalidException extends Exception {}

