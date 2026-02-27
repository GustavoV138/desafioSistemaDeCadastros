package exceptions;

public class IdadeAlemDoLimiteException extends RuntimeException{
    public IdadeAlemDoLimiteException() {
    }

    @Override
    public String getMessage() {
        return "A idade informada ultrapassa o limite máximo. (20 anos)";
    }
}
