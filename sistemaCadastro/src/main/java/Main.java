import entities.Pet;
import entities.Sexo;
import repository.PetRepository;
import service.MenuService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){

        MenuService.escolhaUsuario();

//        PetRepository pet = new PetRepository();
//        for(StringBuilder s: pet.buscarArquivos()){
//            System.out.println(s);
//        }

    }
}
