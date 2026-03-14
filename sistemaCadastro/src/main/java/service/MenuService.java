package service;

import repository.PetRepository;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuService {

    private static PetRepository petRepository = new PetRepository();
    private static PetService petService = new PetService();

    public MenuService() {
    }

    private static void exibirListaMenu() {

        System.out.println();
        System.out.println("----------Menu Inicial----------");
        System.out.println("1.Cadastrar um novo pet");
        System.out.println("2.Alterar os dados do pet cadastrado");
        System.out.println("3.Deletar um pet cadastrado");
        System.out.println("4.Listar todos os pets cadastrados");
        System.out.println("5.Listar por parâmetro. (Nome, raça, ...)");
        System.out.println("6.Sair");
    }

    public static void escolhaUsuario() {

        Scanner sc = new Scanner(System.in);
        boolean isTrue = true;
        int opcao = 0;

        exibirListaMenu();

        while (isTrue) {

            System.out.print("\nDigite uma opção: ");

            try {
                opcao = sc.nextInt();
            } catch (InputMismatchException e) {
                sc.next();
                System.out.println("\nVocê digitou um valor não aceito. (InputMismatchException)");
            }

            switch (opcao) {
                case 1:
                    petService.cadastrarPet();
                    exibirListaMenu();
                    break;

                case 2:
                    petService.alterarPet();
                    exibirListaMenu();
                    break;

                case 3:
                    petService.deletarPet();
                    exibirListaMenu();
                    break;

                case 4:
                    petService.listarTodosPets();
                    exibirListaMenu();
                    break;

                case 5:
                    try {
                        petService.lerCriterio();
                    } catch (InputMismatchException e) {
                        e.printStackTrace();
                    }
                    exibirListaMenu();
                    break;

                case 6:
                    isTrue = false;
                    System.out.println("\nAté mais!");
                    break;

                default:
                    System.out.println("\nVocê não digitou uma opção válida!");
                    exibirListaMenu();
                    break;
            }

        }
    }
}
