package hr.tvz.susac.java.web.flight.util.exception;

public class InternalServerErrorException extends RuntimeException
{
    public InternalServerErrorException(String message) {
        super(message);
    }
}
