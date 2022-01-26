package dev.vality.cds.client.identity.document.storage.exception;

public class CdsIDStorageException extends RuntimeException {

    public CdsIDStorageException() {
        super();
    }

    public CdsIDStorageException(String message) {
        super(message);
    }

    public CdsIDStorageException(Throwable cause) {
        super(cause);
    }

    public CdsIDStorageException(String message, Throwable cause) {
        super(message, cause);
    }

}
