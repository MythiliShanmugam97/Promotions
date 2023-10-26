package com.unext.capstone.promotionsandoffers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.CONFLICT, reason="Promotion ID Not exists")
public class PromotionNotExistsException extends Exception {}

