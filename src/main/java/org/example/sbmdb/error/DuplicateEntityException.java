package org.example.sbmdb.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException(String entity) {
        super("Duplicate " + entity.toLowerCase());
    }
}