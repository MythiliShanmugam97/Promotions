package com.unext.capstone.promotionsandoffers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason="No Promotions Available")
public class PromotionNotAvailableException extends Exception {

}
