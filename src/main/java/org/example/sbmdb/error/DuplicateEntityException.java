package org.example.sbmdb.error;

public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException(String entity) {
        super("Duplicate " + entity.toLowerCase());
    }
}
