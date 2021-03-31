package hr.tvz.susac.java.web.flight.util.exception;

public class UnauthorizedRequestException extends RuntimeException
{
    public UnauthorizedRequestException(String message) {
        super(message);
    }
}
