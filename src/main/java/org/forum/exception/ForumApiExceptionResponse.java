package org.forum.exception;

import org.forum.exception.ForumException.ErrorCode;
import java.util.Date;
import java.util.Objects;


public class ForumApiExceptionResponse {

    private int statusCode;
    private String status;
    private ErrorCode errorCode;
    private String message;
    private String path;
    private Date timestamp;

    public ForumApiExceptionResponse() {
        this.timestamp = new Date();
    }

    public ForumApiExceptionResponse(ForumApiException exception) {
        this();
        this.statusCode = exception.getStatus().value();
        this.status = exception.getStatus().toString();
        this.errorCode = exception.getErrorCode();
        this.message = exception.getMessage();
        this.path = exception.getPath();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ForumApiExceptionResponse{" +
                "statusCode=" + statusCode +
                ", status='" + status + '\'' +
                ", errorCode=" + errorCode +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ForumApiExceptionResponse)) return false;
        ForumApiExceptionResponse that = (ForumApiExceptionResponse) o;
        return statusCode == that.statusCode &&
                Objects.equals(status, that.status) &&
                errorCode == that.errorCode &&
                Objects.equals(message, that.message) &&
                Objects.equals(path, that.path) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, status, errorCode, message, path, timestamp);
    }
}
