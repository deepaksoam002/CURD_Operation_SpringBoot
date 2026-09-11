package in.deepak.springBootCurdDemo.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ValidationExceptionResponseDto {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String path;
    private Map<String,String> errorFields;

    public ValidationExceptionResponseDto(LocalDateTime timestamp, int status, String error, String path, Map<String, String> errorFields) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
        this.errorFields = errorFields;

    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getErrorFields() {
        return errorFields;
    }

    public void setErrorFields(Map<String, String> errorFields) {
        this.errorFields = errorFields;
    }
}
