package br.com.fiap.cpdsa.app;

import br.com.fiap.cpdsa.abb.Abb;
import br.com.fiap.cpdsa.fila.Fila;
import br.com.fiap.cpdsa.models.Usuario;

import java.util.Comparator;
import java.util.Scanner;

public class App {

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Abb<Usuario> abbUsuario = new Abb<>(Comparator.comparing(Usuario::getCpf));
        Abb<Usuario> abbOfertas = new Abb<>(Comparator.comparing(Usuario::getTotalCompras));
        Fila<Usuario> fila = new Fila();

        int opcao;
        do {
            System.out.println("""
                    SELECIONE UMA OPÇÃO

                    1 - Inserir cliente;
                    2 - Ofertar produto;
                    3 - Entrar no submenu;
                    4 - Remover cliente do cadastro;
                    """);
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 0:
                    System.out.println("Encerrando operação...");
                    break;
                case 1:
                    inserirCliente(abbUsuario);
                    break;
                case 2:
                    abbOfertas = ofertaProduto(abbUsuario, abbOfertas);
                    fila = abbOfertas.generateQueue(abbOfertas.getRoot(), fila);
                    fila.display();
                    ContataCliente(fila);
                    abbOfertas.exibeOrdem(abbOfertas.getRoot());
                    fila.display();
                    abbOfertas.setRoot(null);
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);

    }


    public static void inserirCliente(Abb<Usuario> abbUsuario) {
        Scanner sc = new Scanner(System.in);
        Usuario novoUsuario = new Usuario();

        System.out.println("================ Cadastrando um novo cliente ================");

        Usuario a1 = new Usuario("Derick", "20", "33", 100, true);
        Usuario a2 = new Usuario("Nicolas", "10", "33", 550, true);
        Usuario a3 = new Usuario("Marcelo", "30", "33", 333, true);
        Usuario a4 = new Usuario("Edu", "12", "33", 120, true);
        Usuario a5 = new Usuario("Felipe", "80", "33", 990, true);


        abbUsuario.setRoot(abbUsuario.inserir(abbUsuario.getRoot(), a1));
        abbUsuario.setRoot(abbUsuario.inserir(abbUsuario.getRoot(), a2));
        abbUsuario.setRoot(abbUsuario.inserir(abbUsuario.getRoot(), a3));
        abbUsuario.setRoot(abbUsuario.inserir(abbUsuario.getRoot(), a4));
        abbUsuario.setRoot(abbUsuario.inserir(abbUsuario.getRoot(), a5));
        abbUsuario.exibeOrdem(abbUsuario.getRoot());
    }

    public static Abb<Usuario> ofertaProduto(Abb<Usuario> abbUsuario, Abb<Usuario> abbOfertas) {
        System.out.println("Valor minimo:");
        double minValue = input.nextDouble();
        abbOfertas =  abbUsuario.geraAbbElegivelAOferta(abbUsuario.getRoot(), minValue, abbOfertas);
        return abbOfertas;
    }

    public static void ContataCliente(Fila<Usuario> fila){

        while(fila.start != null){
            Usuario cliente = fila.dequeue().getDado();
            System.out.println("Olá cliente - " + cliente.getNome());

            System.out.println("Você está elegível para a oferta, deseja aceitá-la?");
            char result = input.next().toLowerCase().charAt(0);

            if(result == 's'){
                System.out.println("Sua oferta foi aceita!!");
                cliente.setAptoOferta(false);
                System.out.println("Você não está mais apto para ofertas!");
            } else if (result == 'n'){
                System.out.println("Saindo da tela");
            }

            System.out.println("-=====================");
            System.out.println("Avançando!");
        }

    }

}
