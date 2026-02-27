package exceptions;

public class SexoIncompativelException extends RuntimeException{
    public SexoIncompativelException() {
    }

    @Override
    public String getMessage() {
        return "Valor informado incompátivel. Deve ser MACHO ou M, FEMEA ou F.";
    }
}
