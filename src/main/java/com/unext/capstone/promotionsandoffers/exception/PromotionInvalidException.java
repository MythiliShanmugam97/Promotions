package com.unext.capstone.promotionsandoffers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.CONFLICT, reason="Promotion % or number of promo's are not valid")
public class PromotionInvalidException extends Exception {}

