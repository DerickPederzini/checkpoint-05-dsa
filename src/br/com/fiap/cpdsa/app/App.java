package br.com.fiap.cpdsa.app;

import br.com.fiap.cpdsa.abb.Abb;
import br.com.fiap.cpdsa.models.Usuario;

import java.util.Comparator;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Abb<Usuario> abbUsuario = new Abb<>(Comparator.comparing(Usuario::getCpf));
        Abb<Usuario> abbOfertas = new Abb<>(Comparator.comparing(Usuario::getTotalCompras));

        int opcao = 0;
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

        } while (opcao == 0);

    }

    public void inserirCliente() {

    }
}
