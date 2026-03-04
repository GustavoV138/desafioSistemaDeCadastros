package service;

import entities.Pet;
import entities.Sexo;
import entities.TipoPet;
import exceptions.*;
import repository.PetRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PetService {

    Pet pet = new Pet();
    PetRepository petRepository = new PetRepository();


    public PetService() {
    }

    public PetService(Pet pet, PetRepository petRepository) {
        this.pet = pet;
        this.petRepository = petRepository;
    }

    // Talvez a validaçao possa ser feita direto na entidade Pet
    // Possivel melhoria é deixar esse metodo somente para leitura dos dados, ele retorna um Pet, e outro metodo válida e manda pro repository
    public Pet lerPet() {
        Scanner sc = new Scanner(System.in);

        System.out.print("\n" + petRepository.lerFormulario().get(0) + " ");
        pet.setNome(sc.nextLine());

        System.out.print(petRepository.lerFormulario().get(1) + " ");
        pet.setSobrenome(sc.nextLine());

        System.out.print(petRepository.lerFormulario().get(2) + " ");
        String tipo = sc.nextLine().toLowerCase();
        if (tipo.equals("cachorro")) {
            pet.setTipo(TipoPet.CACHORRO);
        } else if (tipo.equals("gato")) {
            pet.setTipo(TipoPet.GATO);
        } else {
            throw new TipoNaoReconhecidoException();
        }

        System.out.print(petRepository.lerFormulario().get(3) + " ");
        String sexo = sc.nextLine().toLowerCase();

        if (sexo.startsWith("m")) {
            pet.setSexo(Sexo.MACHO);
        } else if (sexo.startsWith("f")) {
            pet.setSexo(Sexo.FEMEA);
        } else {
            throw new SexoIncompativelException();
        }

        System.out.print(petRepository.lerFormulario().get(4) + " ");
        pet.setRaca(sc.nextLine());

        System.out.print(petRepository.lerFormulario().get(5) + " ");
        pet.setRua(sc.nextLine());

        System.out.print(petRepository.lerFormulario().get(6) + " ");
        pet.setCidade(sc.nextLine());

        System.out.print(petRepository.lerFormulario().get(7) + " ");
        pet.setNumCasa(sc.nextLine());

        System.out.print(petRepository.lerFormulario().get(8) + " ");
        pet.setIdade(sc.nextDouble());

        System.out.print(petRepository.lerFormulario().get(9) + " ");
        pet.setPeso(sc.nextDouble());

        if (pet == null) {
            throw new PetNuloException();
        } else {
            return pet;
        }
    }

    public void cadastrarPet() {

        petRepository.criarFormulario();
        Pet petCadastro = new Pet();

        try {
            petCadastro = lerPet();
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Os campos: Idade e Peso devem ser preenchidos exclusivamente com valores numéricos, decimais ou reais.");
        }

        System.out.println(petCadastro);

        Pattern padraoAZ = Pattern.compile("[a-zA-Z\\s]");

//        ********** Regras: Nome e Sobrenome ************

        if (petCadastro.getNome().isEmpty() || petCadastro.getSobrenome().isEmpty()) {
            throw new NomeSobrenomeEmptyException();
        }

        Matcher matcherNome = padraoAZ.matcher(petCadastro.getNome());
        Matcher matcherSobrenome = padraoAZ.matcher(petCadastro.getSobrenome());

        StringBuilder nome = new StringBuilder();
        StringBuilder sobrenome = new StringBuilder();

        while (matcherNome.find()) {
            nome.append(matcherNome.group());
        }

        while (matcherSobrenome.find()) {
            sobrenome.append(matcherSobrenome.group());
        }

        petCadastro.setNome(nome.toString());
        petCadastro.setSobrenome(sobrenome.toString());

        System.out.println(petCadastro);

//        ********** Regras: Peso ************

        if (petCadastro.getPeso() < 0.5 || petCadastro.getPeso() > 60) {
            throw new PesoAlemDoLimiteException();
        }

//        ********** Regras: Idade ************

        if (petCadastro.getIdade() < 0 || petCadastro.getIdade() > 20) {
            throw new IdadeAlemDoLimiteException();
        }

//        ********** Regras: Raça ************

        Matcher matcherRaca = padraoAZ.matcher(petCadastro.getRaca());
        StringBuilder raca = new StringBuilder();

        while (matcherRaca.find()) {
            raca.append(matcherRaca.group());
        }

        petCadastro.setRaca(raca.toString());

        petRepository.cadastrarPet(petCadastro);

    }

    public void lerCriterio() throws RuntimeException {
        Scanner sc = new Scanner(System.in);

        System.out.print("\nTipo do pet (Cachorro/Gato): ");
        String criterio1 = sc.nextLine();
        String criterio2;

        System.out.println("\nEscolha se quer adicionar mais algum critério:");
        System.out.println("1 - Nome");
        System.out.println("2 - Sobrenome");
        System.out.println("3 - Idade");
        System.out.println("4 - Sexo");
        System.out.println("5 - Peso");
        System.out.println("6 - Raça");
        System.out.println("7 - Endereço (Rua/Cidade/Núm. da casa)");
        System.out.println("8 - Seguir sem critério adicional");
        System.out.println("9 - Voltar para o menu inicial");

        System.out.print("\nDigite sua escolha: ");
        int escolha = sc.nextInt();

        switch (escolha) {

            case 1:
                System.out.print("Digite o nome do pet: ");
                criterio2 = sc.nextLine();
                criterio2 = sc.nextLine();
                listarPorCriterio(criterio1, criterio2);
                break;

            case 2:
                System.out.print("Digite o Sobrenome do pet: ");
                criterio2 = sc.nextLine();
                criterio2 = sc.nextLine();
                listarPorCriterio(criterio1, criterio2);
                break;

            case 3:
                System.out.print("Digite a idade do pet: ");
                criterio2 = sc.nextLine();
                criterio2 = sc.nextLine();
                listarPorCriterio(criterio1, criterio2);
                break;

            case 4:
                System.out.print("Digite o sexo do pet: ");
                criterio2 = sc.nextLine();
                criterio2 = sc.nextLine();
                listarPorCriterio(criterio1, criterio2);
                break;

            case 5:
                System.out.print("Digite o peso do pet: ");
                criterio2 = sc.nextLine();
                criterio2 = sc.nextLine();
                listarPorCriterio(criterio1, criterio2);
                break;

            case 6:
                System.out.print("Digite a raça do pet: ");
                criterio2 = sc.nextLine();
                criterio2 = sc.nextLine();
                listarPorCriterio(criterio1, criterio2);
                break;

            case 7:
                System.out.print("Digite o endereço que o pet foi encontrado: ");
                criterio2 = sc.nextLine();
                criterio2 = sc.nextLine();
                listarPorCriterio(criterio1, criterio2);
                break;

            case 8:
                listarPorCriterio(criterio1);
                break;

            case 9:
                break;

            default:
                System.out.println("\nVocê digitou um valor inválido. Tente novamente.");
                break;
        }

    }

    public void listarPorCriterio(String criterio) {

//        O método pega o array do petRepository.buscarArquivo, lê cada item, compara com o critério, e se bater, adiciona em outra arrayList (listaPets)

        ArrayList<StringBuilder> listaPets = new ArrayList<>();
        Pattern pattern = Pattern.compile(criterio.toLowerCase()); // Colocar o criterio passado em LowerCase

        try {
            ArrayList<StringBuilder> listaTodosPets = new ArrayList<>(petRepository.buscarArquivos());

            for (StringBuilder sb : listaTodosPets) {
                Matcher matcher = pattern.matcher(sb.toString().toLowerCase()); // Transforma toda a linha em lowerCase antes de comparar com o pattern

                if (matcher.find()) {
                    listaPets.add(sb);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (listaPets.isEmpty()) {
            listaPets.add(new StringBuilder("Nenhum pet foi encontrado com o critério fornecido."));
        }

        System.out.println();
        for (StringBuilder sb : listaPets) {
            System.out.println(sb);
        }

        System.out.println("\nPressione 'Enter' para continuar.");
        new Scanner(System.in).nextLine();
    }

    public void listarPorCriterio(String criterio1, String criterio2) {

        ArrayList<StringBuilder> listaPets = new ArrayList<>();
        Pattern pattern = Pattern.compile(criterio1.toLowerCase()); // Colocar o criterio passado em LowerCase
        Pattern pattern2 = Pattern.compile(criterio2.toLowerCase());

        try {
            ArrayList<StringBuilder> listaTodosPets = new ArrayList<>(petRepository.buscarArquivos());

            for (StringBuilder sb : listaTodosPets) {
                Matcher matcher = pattern.matcher(sb.toString().toLowerCase()); // Transforma toda a linha em lowerCase antes de comparar com o pattern

                if (matcher.find()) { // Aqui verifica o segundo criterio dentro dos que batem no primeiro criterio
                    Matcher matcher2 = pattern2.matcher(sb.toString().toLowerCase());

                    if (matcher2.find()) {
                        listaPets.add(sb);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (listaPets.isEmpty()) {
            listaPets.add(new StringBuilder("Nenhum pet foi encontrado com o critério fornecido."));
        }

        System.out.println();
        for (StringBuilder sb : listaPets) {
            System.out.println(sb);
        }

        System.out.println("\nPressione 'Enter' para continuar.");
        new Scanner(System.in).nextLine();
    }
}
