package ir.alimalek.assignment.exception;

import org.springframework.http.HttpStatus;

public class InvalidCSVInputFile extends HttpException {
    public InvalidCSVInputFile() {
        super(HttpStatus.BAD_REQUEST, "Invalid CSV file.");
    }
}
