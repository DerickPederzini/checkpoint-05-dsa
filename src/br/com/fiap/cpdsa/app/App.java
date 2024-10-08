package br.com.fiap.cpdsa.app;

import br.com.fiap.cpdsa.abb.Abb;
import br.com.fiap.cpdsa.fila.Fila;
import br.com.fiap.cpdsa.models.Usuario;

import java.util.*;

public class App {


    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

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
                    5 - Encerrar aplicação;

                    """);
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1:
                    inserirCliente(abbUsuario);
                    break;
                case 2:
                    System.out.println("Valor minimo:");
                    double minValue = input.nextDouble();
                    abbOfertas = ofertaProduto(abbUsuario, abbOfertas, minValue);
                    fila = abbOfertas.generateQueue(abbOfertas.getRoot(), fila);
                    ContataCliente(fila);
                    break;
                case 3:
                    exibirSubmenu(abbUsuario);
                    break;
                case 4:
                    removerCliente(abbUsuario);
                    break;
                case 5:
                    encerrar(abbOfertas);
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 5);

    }


    public static void inserirCliente(Abb<Usuario> abbUsuario) {
        Scanner sc = new Scanner(System.in);
        Usuario novoUsuario = new Usuario();

        System.out.println("================ Cadastrando um novo cliente ================");

        System.out.println("Insira o nome: ");
        novoUsuario.setNome(sc.nextLine());

        System.out.println("Insira o cpf: ");
        String cpf = sc.nextLine();
        while (cpf.length() != 2) {
            System.out.println("Insira um cpf com 2 dígitos: ");
            cpf = sc.nextLine();
        }
        novoUsuario.setCpf(cpf);

        System.out.println("Insira o whatsApp: ");
        novoUsuario.setWhatsapp(sc.nextLine());

        Random random = new Random();

        novoUsuario.setTotalCompras(random.nextDouble(1001));

        novoUsuario.setAptoOferta(true);

        abbUsuario.setRoot(abbUsuario.inserir(abbUsuario.getRoot(), novoUsuario));
    }

    public static void removerCliente(Abb<Usuario> abbUsuario) {
        Scanner sc = new Scanner(System.in);
        Usuario novoUsuario = new Usuario();

        System.out.println("================ Removendo um cliente ================");

        System.out.println("Insira o cpf: ");
        novoUsuario.setCpf(sc.nextLine());

        abbUsuario.setRoot(abbUsuario.remover(abbUsuario.getRoot(), novoUsuario));
    }

    public static void buscarClienteCpf(Abb<Usuario> abbUsuario) {
        Scanner sc = new Scanner(System.in);

        System.out.println("================ Buscando cliente por CPF ================");

        System.out.println("Insira o cpf: ");
        String cpf = sc.nextLine();
        while (cpf.length() != 2) {
            System.out.println("Insira um cpf com 2 dígitos: ");
            cpf = sc.nextLine();
        }
        Usuario user = abbUsuario.buscaCpf(abbUsuario.getRoot(), cpf);

        if (user == null) {
            System.out.println("Cliente não encontrado");
        } else {
            System.out.println(user);
        }
    }
    
    public static void buscarTodosClientes(Abb<Usuario> abbUsuario) {
        abbUsuario.exibeOrdem(abbUsuario.getRoot());
    }

    public static Abb<Usuario> ofertaProduto(Abb<Usuario> abbUsuario, Abb<Usuario> abbOfertas, Double minValue) {
        abbOfertas = abbUsuario.geraAbbElegivelAOferta(abbUsuario.getRoot(), minValue, abbOfertas);
        return abbOfertas;
    }

    public static void ContataCliente(Fila<Usuario> fila) {

        while (fila.start != null) {
            Usuario cliente = fila.dequeue().getDado();
            System.out.println("Olá cliente - " + cliente.getNome());

            System.out.println("Você está elegível para a oferta, deseja aceitá-la?");
            char result = input.next().toLowerCase().charAt(0);

            if (result == 's') {
                System.out.println("Sua oferta foi aceita!!");
                cliente.setAptoOferta(false);
                System.out.println("Você não está mais apto para ofertas!");
            } else if (result == 'n') {
                System.out.println("Saindo da tela");
            }

            System.out.println("-=====================");
            System.out.println("Avançando!");
        }

    }

    public static void exibirSubmenu(Abb<Usuario> abbUsuario) {
        int escolha;
        do {
            System.out.println("""
                    ================ SUBMENU ================
                                    
                    1 - Buscar cliente pelo cpf;
                    2 - Total de gastos de todos os clientes;
                    3 - Clientes com saldo acima do valor a ser inserido;
                    4 - Voltar ao menu principal;
                    """);
            escolha = input.nextInt();

            switch (escolha) {
                case 1:
                    //método que busca cliente por cpf
                    buscarClienteCpf(abbUsuario);
                    break;
                case 2:
                    //método irá somar o quanto todos clientes gastaram

                    break;
                case 3:
                    //método que irá mostrar clientes com saldo acima do valor inserido
                    buscarPorValorMinimo(abbUsuario);
                    break;
                case 4:
                    //ir ao switch anterior
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        } while (escolha != 4);
    }

    private static void buscarPorValorMinimo(Abb<Usuario> abbUsuario) {
        System.out.println("Valor minimo:");
        double minValue = input.nextDouble();
        int usuarios = abbUsuario.limitePorSaldo(abbUsuario.getRoot(), minValue);
        System.out.println("Quantidade de clientes com saldo acima de " + minValue + ": " + usuarios);
    }

    public static void encerrar(Abb<Usuario> abbOferta) {
        System.out.println("================ Clientes aptos que não aceitaram a oferta ================");
        System.out.println("");
        abbOferta.ofertaNaoAceita(abbOferta.getRoot());
        System.out.println("");
        System.out.println("Encerrando a aplicação...");
    }

}
