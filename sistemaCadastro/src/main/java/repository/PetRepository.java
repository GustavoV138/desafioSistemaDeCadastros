package repository;

import entities.Pet;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PetRepository {

    private final File DIRECTORY = new File("BaseDados");
    private final File FORMULARIO = new File(DIRECTORY, "formulario.txt");

    private final File PETS_DIRECTORY = new File("petsCadastrados");

    public PetRepository() {
    }

    public void criarBaseDados() {
        DIRECTORY.mkdir();

        try {
            FORMULARIO.createNewFile();
        } catch (IOException e) {
            System.out.println("Arquivo não pôde ser criado.");
            e.printStackTrace();
        }
    }

    public void deletarBaseDados() {
        FORMULARIO.delete();
    }

    public ArrayList<String> lerFormulario() {
        try (FileReader fileReader = new FileReader(FORMULARIO);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String linha;
            ArrayList<String> textoFinal = new ArrayList<>();

            while ((linha = bufferedReader.readLine()) != null) {
                textoFinal.add(linha);
            }

            return textoFinal;

        } catch (IOException e) {
            System.out.println("Erro ao tentar acessar o formulário.");
            e.printStackTrace();
        }

        return null;
    }

    public void exibirFormulario() {

        for (String s : lerFormulario()) {
            System.out.println(s);
        }

    }

    public void cadastrarPet(Pet pet) {
        DateTimeFormatter padraoDataArquivos = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm");
        LocalDateTime horaAgora = LocalDateTime.now();

        String dataFormatadaArquivo = horaAgora.format(padraoDataArquivos);
        String nomeSobrenomeArquivo = (pet.getNome() + pet.getSobrenome()).trim();

        PETS_DIRECTORY.mkdir();

        File arquivoPet = new File(PETS_DIRECTORY, (dataFormatadaArquivo + "-" + nomeSobrenomeArquivo.toUpperCase()) + ".txt");

        try {
            if (arquivoPet.createNewFile()) {
                System.out.println("\nPet cadastrado com sucesso.");
            } else {
                System.out.println("Falha ao cadastrar Pet.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileWriter fw = new FileWriter(arquivoPet, true);
             BufferedWriter arquivoWriter = new BufferedWriter(fw)) {

            arquivoWriter.write("1 - " + pet.getNome() + " " + pet.getSobrenome());
            arquivoWriter.newLine();
            arquivoWriter.write("2 - " + pet.getTipo());
            arquivoWriter.newLine();
            arquivoWriter.write("3 - " + pet.getSexo());
            arquivoWriter.newLine();
            arquivoWriter.write("4 - " + pet.getRua() + ", " + pet.getNumCasa() + ", " + pet.getCidade());
            arquivoWriter.newLine();
            arquivoWriter.write("5 - " + pet.getIdade() + " anos");
            arquivoWriter.newLine();
            arquivoWriter.write("6 - " + pet.getPeso() + "kg");
            arquivoWriter.newLine();
            arquivoWriter.write("7 - " + pet.getRaca());

        } catch (IOException e) {

        }

    }
}
