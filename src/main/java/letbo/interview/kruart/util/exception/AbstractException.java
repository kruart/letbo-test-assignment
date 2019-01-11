package letbo.interview.kruart.util.exception;

import org.springframework.lang.NonNull;

public class AbstractException extends RuntimeException {
    public AbstractException(@NonNull String msg) {
        super(msg);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + getMessage();
    }
}
