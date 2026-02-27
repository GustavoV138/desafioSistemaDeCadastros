import entities.Pet;
import entities.Sexo;
import service.MenuService;

import java.time.LocalDateTime;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class Main {
    public static void main(String[] args){

        MenuService.exibirFormulario();
        MenuService.exibirListaMenu();
        MenuService.escolhaUsuario();

    }
}
