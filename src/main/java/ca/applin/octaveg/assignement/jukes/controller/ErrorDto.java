package ca.applin.octaveg.assignement.jukes.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {

    public static ErrorDto from(int status, String message, String path, String details) {
        return new ErrorDto(List.of(new ErrorItems(status, message, details, path)));
    }

    private List<ErrorItems> errors;

    @Data
    @AllArgsConstructor
    static class ErrorItems {

        private int status;
        private String message;
        private String details;
        private String path;
    }
}
