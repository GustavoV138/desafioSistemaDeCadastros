package service;

import entities.Pet;
import entities.Sexo;
import entities.TipoPet;
import exceptions.*;
import repository.PetRepository;

import java.io.*;
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
        String criterio1 = sc.nextLine().toLowerCase();
        String criterio2;

        if (!(criterio1.equals("cachorro") || criterio1.equals("gato"))) {
            System.out.println("\nTipo de pet inválido.");
            return;
        }


        System.out.println("\nDeseja adicionar mais algum critério?\n");
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
                System.out.println("\nVocê digitou um valor inválido. Pressione 'Enter' para tentar novamente.");
                new Scanner(System.in).nextLine();
                lerCriterio();
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
            System.out.println("Nenhum pet foi encontrado com o critério fornecido.");
            return;
        }

        System.out.println();
        int i = 1;

        for (StringBuilder sb : listaPets) {

            Scanner sc = new Scanner(sb.toString());
            sc.useDelimiter(",");

            while (sc.hasNext()) {
                System.out.println(i + " - " + sc.next() + " - " + sc.next() + " - " + sc.next() + " - " + sc.next() + "," + sc.next() + " -" + sc.next() + " - " + sc.next() + " - " + sc.next() + " - " + sc.next());
            }
            i++;
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

                if (matcher.find()) {
                    Matcher matcher2 = pattern2.matcher(sb.toString().toLowerCase());

                    if (matcher2.find()) { // Aqui verifica o segundo criterio dentro dos que batem no primeiro criterio
                        listaPets.add(sb);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (listaPets.isEmpty()) {
            System.out.println("Nenhum pet foi encontrado com o critério fornecido.");
            return;
        }

        System.out.println();
        int i = 1;

        for (StringBuilder sb : listaPets) {

            Scanner sc = new Scanner(sb.toString());
            sc.useDelimiter(",");

            while (sc.hasNext()) {
                System.out.println(i + " - " + sc.next() + " - " + sc.next() + " - " + sc.next() + " - " + sc.next() + "," + sc.next() + " -" + sc.next() + " - " + sc.next() + " - " + sc.next() + " - " + sc.next());
            }
            i++;
        }

        System.out.println("\nPressione 'Enter' para continuar.");
        new Scanner(System.in).nextLine();
    }

    public void listarTodosPets() {
        ArrayList<StringBuilder> listaPets = new ArrayList<>();

        try {
            listaPets.addAll(petRepository.buscarArquivos());

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (listaPets.isEmpty()) {
            System.out.println("\nNenhum pet foi encontrado na base de dados.");
            return;
        }

        System.out.println();
        int i = 1;

        for (StringBuilder sb : listaPets) {

            Scanner sc = new Scanner(sb.toString());
            sc.useDelimiter(",");


            while (sc.hasNext()) {
                System.out.println(i + " - " + sc.next() + " - " + sc.next() + " - " + sc.next() + " - " + sc.next() + "," + sc.next() + " -" + sc.next() + " - " + sc.next() + " - " + sc.next() + " - " + sc.next());
            }
            i++;
        }

        System.out.println("\nPressione 'Enter' para continuar.");
        new Scanner(System.in).nextLine();
    }

    public void lerPetAlteracao() {
        Scanner sc = new Scanner(System.in);
        ArrayList<StringBuilder> listaPetsAlterar = new ArrayList<>();

        System.out.print("\nTipo do pet (Cachorro/Gato): ");
        String criterio1 = sc.nextLine().toLowerCase();
        String criterio2;

        if (!(criterio1.equals("cachorro") || criterio1.equals("gato"))) {
            System.out.println("\nTipo de pet inválido.");
            return;
        }


        System.out.println("\nDeseja adicionar mais algum critério?\n");
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
                listaPetsAlterar.addAll(buscarPet(criterio1, criterio2));
                break;

            case 2:
                System.out.print("Digite o Sobrenome do pet: ");
                criterio2 = sc.nextLine();
                criterio2 = sc.nextLine();
                listaPetsAlterar.addAll(buscarPet(criterio1, criterio2));
                break;

            case 3:
                System.out.print("Digite a idade do pet: ");
                criterio2 = sc.nextLine();
                criterio2 = sc.nextLine();
                listaPetsAlterar.addAll(buscarPet(criterio1, criterio2));
                break;

            case 4:
                System.out.print("Digite o sexo do pet: ");
                criterio2 = sc.nextLine();
                criterio2 = sc.nextLine();
                listaPetsAlterar.addAll(buscarPet(criterio1, criterio2));
                break;

            case 5:
                System.out.print("Digite o peso do pet: ");
                criterio2 = sc.nextLine();
                criterio2 = sc.nextLine();
                listaPetsAlterar.addAll(buscarPet(criterio1, criterio2));
                break;

            case 6:
                System.out.print("Digite a raça do pet: ");
                criterio2 = sc.nextLine();
                criterio2 = sc.nextLine();
                listaPetsAlterar.addAll(buscarPet(criterio1, criterio2));
                break;

            case 7:
                System.out.print("Digite o endereço que o pet foi encontrado: ");
                criterio2 = sc.nextLine();
                criterio2 = sc.nextLine();
                listaPetsAlterar.addAll(buscarPet(criterio1, criterio2));
                break;

            case 8:
                listarPorCriterio(criterio1);
                listaPetsAlterar.addAll(buscarPet(criterio1));
                break;

            case 9:
                break;

            default:
                System.out.println("\nVocê digitou um valor inválido. Pressione 'Enter' para tentar novamente.");
                new Scanner(System.in).nextLine();
                lerPetAlteracao();
                break;
        }

        System.out.print("\nSelecione o pet para realizar a alteração: ");
        int numPet = sc.nextInt();

        StringBuilder sb = listaPetsAlterar.get(numPet - 1);

        Scanner scanner = new Scanner(sb.toString());
        scanner.useDelimiter(",");
        String nomeSobrenome = null, tipoPet = null, sexo = null, rua = null, numCasa = null, cidade = null, idade = null, peso = null, raca = null;

        while (scanner.hasNext()) {
            nomeSobrenome = scanner.next();
            tipoPet = scanner.next();
            sexo = scanner.next();
            rua = scanner.next();
            numCasa = scanner.next();
            cidade = scanner.next();
            idade = scanner.next();
            peso = scanner.next();
            raca = scanner.next();
        }

        Pattern pattern = Pattern.compile(nomeSobrenome.replaceAll("\\s+", "").toUpperCase());

        File f = new File("petsCadastrados");
        File[] arrayFiles = f.listFiles();

        StringBuilder arquivoEncontrado = new StringBuilder();

        for (File file : arrayFiles) {
            Matcher matcher = pattern.matcher(file.getName());

            if (matcher.find()) {
                arquivoEncontrado.append(file.getName());
            }
        }

        File arquivo = new File(f, arquivoEncontrado.toString());
        try (BufferedWriter alterarArquivo = new BufferedWriter(new FileWriter(arquivo))) {
            Scanner parametro = new Scanner(System.in);

            System.out.println("\nInforme o campo que deseja alterar:\n");
            System.out.println("1 - Nome");
            System.out.println("2 - Sobrenome");
            System.out.println("3 - Idade");
            System.out.println("4 - Peso");
            System.out.println("5 - Raça");
            System.out.println("6 - Endereço (Rua/Cidade/Núm. da casa)");
            System.out.println("7 - Voltar para o menu inicial");

            System.out.print("\nDigite sua escolha: ");
            int opcao = parametro.nextInt();


            ArrayList<String> nomeEsobrenome = new ArrayList<>();

            String nome = null, sobrenome = null;
            Scanner scan = new Scanner(nomeSobrenome);
            scan.useDelimiter(" ");

            switch (opcao) {

                case 1:
                    System.out.print("Novo nome: ");
                    criterio2 = sc.nextLine();
                    String novoNome = sc.nextLine();

                    while (scan.hasNext()) {
                        nomeEsobrenome.add(scan.next());
                    }

                    nome = nomeEsobrenome.get(0);
                    sobrenome = nomeEsobrenome.get(1);

                    alterarArquivo.write("");

                    alterarArquivo.write(novoNome + " " + sobrenome);
                    alterarArquivo.newLine();
                    alterarArquivo.write(tipoPet);
                    alterarArquivo.newLine();
                    alterarArquivo.write(sexo);
                    alterarArquivo.newLine();
                    alterarArquivo.write(rua + "," + numCasa + "," + cidade);
                    alterarArquivo.newLine();
                    alterarArquivo.write(idade);
                    alterarArquivo.newLine();
                    alterarArquivo.write(peso);
                    alterarArquivo.newLine();
                    alterarArquivo.write(raca);
                    alterarArquivo.newLine();
                    System.out.println("\nPet alterado com sucesso.");
                    break;

                case 2:
                    System.out.print("Novo sobrenome: ");
                    criterio2 = sc.nextLine();
                    String novoSobrenome = sc.nextLine();

                    while (scan.hasNext()) {
                        nomeEsobrenome.add(scan.next());
                    }

                    nome = nomeEsobrenome.get(0);
                    sobrenome = nomeEsobrenome.get(1);

                    alterarArquivo.write("");

                    alterarArquivo.write(nome + " " + novoSobrenome);
                    alterarArquivo.newLine();
                    alterarArquivo.write(tipoPet);
                    alterarArquivo.newLine();
                    alterarArquivo.write(sexo);
                    alterarArquivo.newLine();
                    alterarArquivo.write(rua + "," + numCasa + "," + cidade);
                    alterarArquivo.newLine();
                    alterarArquivo.write(idade);
                    alterarArquivo.newLine();
                    alterarArquivo.write(peso);
                    alterarArquivo.newLine();
                    alterarArquivo.write(raca);
                    alterarArquivo.newLine();
                    System.out.println("\nPet alterado com sucesso.");
                    break;

                case 3:
                    System.out.print("Nova idade: ");
                    criterio2 = sc.nextLine();
                    double novaIdade = sc.nextDouble();

                    alterarArquivo.write("");

                    alterarArquivo.write(nomeSobrenome);
                    alterarArquivo.newLine();
                    alterarArquivo.write(tipoPet);
                    alterarArquivo.newLine();
                    alterarArquivo.write(sexo);
                    alterarArquivo.newLine();
                    alterarArquivo.write(rua + "," + numCasa + "," + cidade);
                    alterarArquivo.newLine();
                    alterarArquivo.write(novaIdade + " anos");
                    alterarArquivo.newLine();
                    alterarArquivo.write(peso);
                    alterarArquivo.newLine();
                    alterarArquivo.write(raca);
                    alterarArquivo.newLine();
                    System.out.println("\nPet alterado com sucesso.");
                    break;

                case 4:
                    System.out.print("Novo peso: ");
                    criterio2 = sc.nextLine();
                    double novoPeso = sc.nextDouble();

                    alterarArquivo.write("");

                    alterarArquivo.write(nomeSobrenome);
                    alterarArquivo.newLine();
                    alterarArquivo.write(tipoPet);
                    alterarArquivo.newLine();
                    alterarArquivo.write(sexo);
                    alterarArquivo.newLine();
                    alterarArquivo.write(rua + "," + numCasa + "," + cidade);
                    alterarArquivo.newLine();
                    alterarArquivo.write(idade);
                    alterarArquivo.newLine();
                    alterarArquivo.write(novoPeso + "kg");
                    alterarArquivo.newLine();
                    alterarArquivo.write(raca);
                    alterarArquivo.newLine();
                    System.out.println("\nPet alterado com sucesso.");
                    break;

                case 5:
                    System.out.print("Nova raça: ");
                    criterio2 = sc.nextLine();
                    String novaRaca = sc.nextLine();

                    alterarArquivo.write("");

                    alterarArquivo.write(nomeSobrenome);
                    alterarArquivo.newLine();
                    alterarArquivo.write(tipoPet);
                    alterarArquivo.newLine();
                    alterarArquivo.write(sexo);
                    alterarArquivo.newLine();
                    alterarArquivo.write(rua + "," + numCasa + "," + cidade);
                    alterarArquivo.newLine();
                    alterarArquivo.write(idade);
                    alterarArquivo.newLine();
                    alterarArquivo.write(peso);
                    alterarArquivo.newLine();
                    alterarArquivo.write(novaRaca);
                    alterarArquivo.newLine();
                    System.out.println("\nPet alterado com sucesso.");
                    break;

                case 6:
                    System.out.println("1 - Rua");
                    System.out.println("2 - Número da casa");
                    System.out.println("3 - Cidade");
                    System.out.print("\nQual campo do endereço deseja alterar? ");
                    int outraOpcao = parametro.nextInt();

                    switch (outraOpcao) {
                        case 1:
                            System.out.print("\nNova rua: ");
                            String novaRua = sc.nextLine();

                            alterarArquivo.write("");

                            alterarArquivo.write(nomeSobrenome);
                            alterarArquivo.newLine();
                            alterarArquivo.write(tipoPet);
                            alterarArquivo.newLine();
                            alterarArquivo.write(sexo);
                            alterarArquivo.newLine();
                            alterarArquivo.write(novaRua + "," + numCasa + "," + cidade);
                            alterarArquivo.newLine();
                            alterarArquivo.write(idade);
                            alterarArquivo.newLine();
                            alterarArquivo.write(peso);
                            alterarArquivo.newLine();
                            alterarArquivo.write(raca);
                            alterarArquivo.newLine();
                            break;

                        case 2:
                            System.out.print("\nNovo número da casa: ");
                            int novoNum = sc.nextInt();

                            alterarArquivo.write("");

                            alterarArquivo.write(nomeSobrenome);
                            alterarArquivo.newLine();
                            alterarArquivo.write(tipoPet);
                            alterarArquivo.newLine();
                            alterarArquivo.write(sexo);
                            alterarArquivo.newLine();
                            alterarArquivo.write(rua + "," + novoNum + "," + cidade);
                            alterarArquivo.newLine();
                            alterarArquivo.write(idade);
                            alterarArquivo.newLine();
                            alterarArquivo.write(peso);
                            alterarArquivo.newLine();
                            alterarArquivo.write(raca);
                            alterarArquivo.newLine();
                            break;

                        case 3:
                            System.out.print("\nNova cidade: ");
                            String novaCidade = sc.nextLine();

                            alterarArquivo.write("");

                            alterarArquivo.write(nomeSobrenome);
                            alterarArquivo.newLine();
                            alterarArquivo.write(tipoPet);
                            alterarArquivo.newLine();
                            alterarArquivo.write(sexo);
                            alterarArquivo.newLine();
                            alterarArquivo.write(rua + "," + numCasa + ", " + novaCidade);
                            alterarArquivo.newLine();
                            alterarArquivo.write(idade);
                            alterarArquivo.newLine();
                            alterarArquivo.write(peso);
                            alterarArquivo.newLine();
                            alterarArquivo.write(raca);
                            alterarArquivo.newLine();
                            break;

                        default:
                            System.out.println("Você digitou um valor inválido. vai ter que tentar tudo denovo. :)");
                            break;
                    }

                    System.out.println("\nPet alterado com sucesso.");
                    break;

                case 7:
                    break;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<StringBuilder> buscarPet(String criterio1) {

        ArrayList<StringBuilder> listaPets = new ArrayList<>();
        Pattern pattern = Pattern.compile(criterio1.toLowerCase()); // Colocar o criterio passado em LowerCase

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
            System.out.println("Nenhum pet foi encontrado com o critério fornecido.");
            return null;
        }

        System.out.println();
        int i = 1;

        for (StringBuilder sb : listaPets) {

            Scanner sc = new Scanner(sb.toString());
            sc.useDelimiter(",");

            while (sc.hasNext()) {
                System.out.println(i + " - " + sc.next() + " - " + sc.next() + " - " + sc.next() + " - " + sc.next() + ", " + sc.next() + " - " + sc.next() + " - " + sc.next() + " - " + sc.next() + " - " + sc.next());
            }
            i++;
        }

        return listaPets;
    }

    public ArrayList<StringBuilder> buscarPet(String criterio1, String criterio2) {

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
            System.out.println("Nenhum pet foi encontrado com o critério fornecido.");
            return null;
        }

        System.out.println();
        int i = 1;

        for (StringBuilder sb : listaPets) {

            Scanner sc = new Scanner(sb.toString());
            sc.useDelimiter(",");

            while (sc.hasNext()) {
                System.out.println(i + " - " + sc.next() + " - " + sc.next() + " - " + sc.next() + " - " + sc.next() + ", " + sc.next() + " - " + sc.next() + " - " + sc.next() + " - " + sc.next() + " - " + sc.next());
            }
            i++;
        }

        return listaPets;
    }

    public void alterarPet() {
        System.out.println("\nInsira as informações do pet que deseja alterar.");
        lerPetAlteracao();
    }

    public void deletarPet() {
        Scanner sc = new Scanner(System.in);
        ArrayList<StringBuilder> listaPetsAlterar = new ArrayList<>();

        System.out.print("\nTipo do pet (Cachorro/Gato): ");
        String criterio1 = sc.nextLine().toLowerCase();
        String criterio2;

        if (!(criterio1.equals("cachorro") || criterio1.equals("gato"))) {
            System.out.println("\nTipo de pet inválido.");
            return;
        }


        System.out.println("\nDeseja adicionar mais algum critério?\n");
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
                listaPetsAlterar.addAll(buscarPet(criterio1, criterio2));
                break;

            case 2:
                System.out.print("Digite o Sobrenome do pet: ");
                criterio2 = sc.nextLine();
                criterio2 = sc.nextLine();
                listaPetsAlterar.addAll(buscarPet(criterio1, criterio2));
                break;

            case 3:
                System.out.print("Digite a idade do pet: ");
                criterio2 = sc.nextLine();
                criterio2 = sc.nextLine();
                listaPetsAlterar.addAll(buscarPet(criterio1, criterio2));
                break;

            case 4:
                System.out.print("Digite o sexo do pet: ");
                criterio2 = sc.nextLine();
                criterio2 = sc.nextLine();
                listaPetsAlterar.addAll(buscarPet(criterio1, criterio2));
                break;

            case 5:
                System.out.print("Digite o peso do pet: ");
                criterio2 = sc.nextLine();
                criterio2 = sc.nextLine();
                listaPetsAlterar.addAll(buscarPet(criterio1, criterio2));
                break;

            case 6:
                System.out.print("Digite a raça do pet: ");
                criterio2 = sc.nextLine();
                criterio2 = sc.nextLine();
                listaPetsAlterar.addAll(buscarPet(criterio1, criterio2));
                break;

            case 7:
                System.out.print("Digite o endereço que o pet foi encontrado: ");
                criterio2 = sc.nextLine();
                criterio2 = sc.nextLine();
                listaPetsAlterar.addAll(buscarPet(criterio1, criterio2));
                break;

            case 8:
                listarPorCriterio(criterio1);
                listaPetsAlterar.addAll(buscarPet(criterio1));
                break;

            case 9:
                break;

            default:
                System.out.println("\nVocê digitou um valor inválido. Pressione 'Enter' para tentar novamente.");
                new Scanner(System.in).nextLine();
                lerPetAlteracao();
                break;
        }

        System.out.print("\nSelecione o pet que deseja excluir: ");
        int numPet = sc.nextInt();

        StringBuilder sb = listaPetsAlterar.get(numPet - 1);

        Scanner scanner = new Scanner(sb.toString());
        scanner.useDelimiter(",");
        String nomeSobrenome = null, tipoPet = null, sexo = null, rua = null, numCasa = null, cidade = null, idade = null, peso = null, raca = null;

        while (scanner.hasNext()) {
            nomeSobrenome = scanner.next();
            tipoPet = scanner.next();
            sexo = scanner.next();
            rua = scanner.next();
            numCasa = scanner.next();
            cidade = scanner.next();
            idade = scanner.next();
            peso = scanner.next();
            raca = scanner.next();
        }

        Pattern pattern = Pattern.compile(nomeSobrenome.replaceAll("\\s+", "").toUpperCase());

        File f = new File("petsCadastrados");
        File[] arrayFiles = f.listFiles();

        StringBuilder arquivoEncontrado = new StringBuilder();

        for (File file : arrayFiles) {
            Matcher matcher = pattern.matcher(file.getName());

            if (matcher.find()) {
                arquivoEncontrado.append(file.getName());
            }
        }

        File arquivo = new File(f, arquivoEncontrado.toString());

        System.out.print("\nVocê tem certeza que deseja excluir o pet selecionado? (Sim/Não) :");
        sc.nextLine();
        String res = sc.nextLine().toLowerCase();

        if (res.startsWith("n")) {
            return;
        } else if (res.startsWith("s")) {
            if (arquivo.delete()) {
                System.out.println("\nPet deletado com sucesso!\n\n Pressione 'Enter' para continuar!");
                sc.nextLine();
            } else {
                System.out.println("\nOcorreu um erro durante a exclusão do Pet!\n\n Pressione 'Enter' para continuar!");
                sc.nextLine();
            }
        } else {
            System.out.println("\nVocê digitou um valor não aceito.");
            return;
        }
    }
}
