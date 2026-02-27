package service;

import entities.Pet;
import entities.Sexo;
import entities.TipoPet;
import exceptions.*;
import repository.PetRepository;

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
    public Pet receberPet() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nome do pet: ");
        pet.setNome(sc.nextLine());

        System.out.print("Sobrenome do pet: ");
        pet.setSobrenome(sc.nextLine());

        System.out.print("Tipo do pet (Cachorro/Gato): ");
        String tipo = sc.nextLine().toLowerCase();
        if (tipo.equals("cachorro")) {
            pet.setTipo(TipoPet.CACHORRO);
        } else if (tipo.equals("gato")) {
            pet.setTipo(TipoPet.GATO);
        } else {

        }

        System.out.print("Sexo do pet: ");
        String sexo = sc.nextLine().toLowerCase();

        if (sexo.startsWith("m")){
            pet.setSexo(Sexo.MACHO);
        } else if (sexo.startsWith("f")) {
            pet.setSexo(Sexo.FEMEA);
        } else {
            throw new SexoIncompativelException();
        }

        System.out.print("Raça do pet: ");
        pet.setRaca(sc.nextLine());

        System.out.print("Rua que o pet foi encontrado: ");
        pet.setRua(sc.nextLine());

        System.out.print("Cidade que o pet foi encontrado: ");
        pet.setCidade(sc.nextLine());

        System.out.print("Número da casa que o pet foi encontrado: ");
        pet.setNumCasa(sc.nextLine());

        System.out.print("Idade do pet, ou aproximada: ");
        pet.setIdade(sc.nextDouble());

        System.out.print("Peso do pet, ou aproximado: ");
        pet.setPeso(sc.nextDouble());

        if (pet == null) {
            throw new PetNuloException();
        } else {
            return pet;
        }
    }

    public void cadastrarPet() {
        Pet petCadastro = new Pet();

        try {
            petCadastro = receberPet();
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Os campos: Idade e Peso devem ser preenchidos exclusivamente com valores numéricos, decimais ou reais.");
        }

        System.out.println(petCadastro);

        Pattern padraoAZ = Pattern.compile("[a-zA-Z]");

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

        if (petCadastro.getIdade() > 20 || petCadastro.getIdade() < 0) {
            throw new IdadeAlemDoLimiteException();
        }

//        ********** Regras: Raça ************

        Matcher matcherRaca = padraoAZ.matcher(petCadastro.getRaca());
        StringBuilder raca = new StringBuilder();

        while(matcherRaca.find()) {
            raca.append(matcherRaca.group());
        }

        petCadastro.setRaca(raca.toString());

        petRepository.cadastrarPet(petCadastro);

    }
}
