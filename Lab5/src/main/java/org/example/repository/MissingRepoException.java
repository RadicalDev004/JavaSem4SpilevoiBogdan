package org.example.repository;

public class MissingRepoException extends Exception {
    public MissingRepoException(String message) {
        super(message);
    }
}
