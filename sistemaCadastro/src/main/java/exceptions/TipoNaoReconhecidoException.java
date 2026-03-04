package exceptions;

public class TipoNaoReconhecidoException extends RuntimeException{
    public TipoNaoReconhecidoException() {
    }

    @Override
    public String getMessage() {
        return "Tipo de pet não reconhecido. Deve ser Cachorro ou Gato.";
    }
}
