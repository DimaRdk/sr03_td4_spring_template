package fr.utc.sr03.chat.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a chat is not found
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ChatNotFoundException extends Exception{
    public ChatNotFoundException() {
    }
}