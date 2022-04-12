package org.forum.exception;


import org.springframework.http.HttpStatus;

import java.util.Objects;


public class ForumApiException extends ForumException {

    private HttpStatus status = HttpStatus.OK;

    private int statusCode = HttpStatus.OK.value();

    private String path;

    public ForumApiException(ErrorCode errorCode, HttpStatus status, String path) {
        super(errorCode);
        this.status = status;
        this.statusCode = status.value();
        this.path = path;
    }


    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ForumApiException{" +
                "status=" + status +
                ", statusCode=" + statusCode +
                ", path='" + path + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ForumApiException)) return false;
        ForumApiException that = (ForumApiException) o;
        return statusCode == that.statusCode &&
                status == that.status &&
                path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, statusCode, path);
    }
}
