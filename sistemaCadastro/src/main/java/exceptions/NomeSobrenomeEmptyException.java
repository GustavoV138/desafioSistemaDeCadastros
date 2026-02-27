package exceptions;

public class NomeSobrenomeEmptyException extends RuntimeException{

    public NomeSobrenomeEmptyException() {
    }

    @Override
    public String getMessage() {
        return "Os campos: Nome e Sobrenome devem ser preenchidos.";
    }
}
