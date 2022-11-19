package ir.alimalek.assignment.exception;

import org.springframework.http.HttpStatus;

public class ParsingDateFormatException extends HttpException{
    public ParsingDateFormatException() {
        super(HttpStatus.BAD_REQUEST, "Could not parse date, make sure the format is day-month-year");
    }
}
