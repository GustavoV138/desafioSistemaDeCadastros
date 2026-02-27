package exceptions;

public class PesoAlemDoLimiteException extends RuntimeException{
    public PesoAlemDoLimiteException() {
    }

    @Override
    public String getMessage() {
        return "O peso informado está fora dos limites. Min: 0.5 Kg, Max: 60 Kg";
    }
}
