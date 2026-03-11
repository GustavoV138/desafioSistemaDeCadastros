package repository;

import entities.Pet;
import exceptions.NenhumArquivoNaBaseDadosException;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PetRepository {

    private final File FORMULARIO = new File("formulario.txt");

    private final File PETS_DIRECTORY = new File("petsCadastrados");

    public PetRepository() {
    }

    public void criarFormulario() {
        try (FileWriter fileWriter = new FileWriter(FORMULARIO);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            FORMULARIO.createNewFile();

            bufferedWriter.write("1 - Qual o nome do pet?");
            bufferedWriter.newLine();
            bufferedWriter.write("2 - Qual o sobrenome do pet?");
            bufferedWriter.newLine();
            bufferedWriter.write("3 - Qual o tipo do pet (Cachorro/Gato)?");
            bufferedWriter.newLine();
            bufferedWriter.write("4 - Qual o sexo do pet?");
            bufferedWriter.newLine();
            bufferedWriter.write("5 - Qual a raça do pet?");
            bufferedWriter.newLine();
            bufferedWriter.write("6 - Em que rua o pet foi encontrado?");
            bufferedWriter.newLine();
            bufferedWriter.write("7 - Em que cidade o pet foi encontrado?");
            bufferedWriter.newLine();
            bufferedWriter.write("8 - Qual o número da casa?");
            bufferedWriter.newLine();
            bufferedWriter.write("9 - Qual a idade aproximada do pet?");
            bufferedWriter.newLine();
            bufferedWriter.write("10 - Qual o peso aproximado do pet?");
            bufferedWriter.newLine();

        } catch (IOException e) {
            System.out.println("Arquivo não pôde ser criado.");
            e.printStackTrace();
        }
    }

    public void deletarFormulario() {
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
        String nomeSobrenomeArquivo = (pet.getNome() + pet.getSobrenome()).toUpperCase();
        StringBuilder sbNomeSobrenome = new StringBuilder();

        Pattern padraoAZ = Pattern.compile("[a-zA-Z]");
        Matcher matcherNomeSobrenome = padraoAZ.matcher(nomeSobrenomeArquivo);

        while(matcherNomeSobrenome.find()){
            sbNomeSobrenome.append(matcherNomeSobrenome.group());
        }

        PETS_DIRECTORY.mkdir();

        File arquivoPet = new File(PETS_DIRECTORY, (dataFormatadaArquivo + "-" + sbNomeSobrenome + ".txt"));

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

            arquivoWriter.write(pet.getNome() + " " + pet.getSobrenome());
            arquivoWriter.newLine();
            arquivoWriter.write(pet.getTipo().TIPO);
            arquivoWriter.newLine();
            arquivoWriter.write(pet.getSexo().SEXO);
            arquivoWriter.newLine();
            arquivoWriter.write(pet.getRua() + "," + pet.getNumCasa() + "," + pet.getCidade());
            arquivoWriter.newLine();
            arquivoWriter.write(pet.getIdade() + " anos");
            arquivoWriter.newLine();
            arquivoWriter.write(pet.getPeso() + "kg");
            arquivoWriter.newLine();
            arquivoWriter.write(pet.getRaca());

        } catch (IOException e) {

        }

    }

    public ArrayList<StringBuilder> buscarArquivos() throws IOException {

        File file = new File(PETS_DIRECTORY.toString());

        File[] filesArray = file.listFiles();
        ArrayList<StringBuilder> listaStringBuilders = new ArrayList<>();

        if(filesArray == null){
            throw new NenhumArquivoNaBaseDadosException();
        }

        for (File f : filesArray) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
            String s;
            StringBuilder conteudoArquivos = new StringBuilder(); // Dentro de cada Stringbuilder vai ficar todos os dados de um arquivo


            while ((s = bufferedReader.readLine()) != null) {
                conteudoArquivos.append(s + ","); // Separando cada linha do arquivo por ','
            }

            bufferedReader.close();
            listaStringBuilders.add(conteudoArquivos); // Ao fim de cada iteração um novo StringBuilder é adicionado no ArrayList
        }

//        if(listaStringBuilders.isEmpty()) {
//
//        }

        return listaStringBuilders;
        // Posso usar o criterio passado, o nome pox exemplo como parametro na string do regex
    }

}
