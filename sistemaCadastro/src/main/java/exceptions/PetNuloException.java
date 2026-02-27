package exceptions;

public class PetNuloException extends RuntimeException{

    public PetNuloException() {
        System.out.println(getMessage());
    }

    @Override
    public String getMessage() {
        return "Tentativa de registrar um Pet nulo.";
    }
}
