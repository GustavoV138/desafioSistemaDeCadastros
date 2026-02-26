import service.Inicio;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        File file = new File("formulario.txt");

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String linha;

        while ((linha = bufferedReader.readLine()) != null) {
            System.out.println(linha);
        }

        Inicio.exibirListaMenu();
        Inicio.escolhaUsuario();
    }
}
