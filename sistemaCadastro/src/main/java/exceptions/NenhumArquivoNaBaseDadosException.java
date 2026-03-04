package exceptions;

public class NenhumArquivoNaBaseDadosException extends RuntimeException{
    public NenhumArquivoNaBaseDadosException() {
    }

    @Override
    public String getMessage() {
        return "Não há nenhum arquivo para consultas na base de dados.";
    }
}
