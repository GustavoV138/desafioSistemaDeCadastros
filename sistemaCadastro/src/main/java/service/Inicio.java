package service;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Inicio {

    public static void exibirListaMenu() {
        System.out.println();
        System.out.println("----------Menu Inicial----------");
        System.out.println("1.Cadastrar um novo pet");
        System.out.println("2.Alterar os dados do pet cadastrado");
        System.out.println("3.Deletar um pet cadastrado");
        System.out.println("4.Listar todos os pets cadastrados");
        System.out.println("5.Listar pets por nome, idade ou raça");
        System.out.println("6.Sair");
    }

    public static void escolhaUsuario() {

        Scanner sc = new Scanner(System.in);
        boolean isTrue = true;
        int opcao = 0;

        while (isTrue) {

            System.out.print("\nDigite uma opção: ");

            try {
                opcao = sc.nextInt(); // aqui pode lançar um InputMismatchException, tem que tratar
            } catch (InputMismatchException e) {
                sc.next();
                System.out.println("\nVocê digitou um valor não aceito. (InputMismatchException)");
            }

            switch (opcao) {
//                case 1:
//                    cadastrarPet();
//                    break;
//
//                case 2:
//                    alterarDadosPet();
//                    break;
//
//                case 3:
//                    deletarPet();
//                    break;
//
//                case 4:
//                    listarTodosPets();
//                    break;
//
//                case 5:
//                    listarPetsPor();
//                    break;

                case 6:
                    isTrue = false;
                    System.out.println("\nAté mais!");
                    break;

                case 0:
                    break;

                default:
                    System.out.println("\nVocê não digitou uma opção válida!");
                    exibirListaMenu();
                    break;
            }

        }
    }
}
