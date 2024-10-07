package br.com.fiap.cpdsa.app;

import br.com.fiap.cpdsa.abb.Abb;
import br.com.fiap.cpdsa.models.Usuario;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Env {
    public static void main(String[] args) {

        Comparator<Usuario> comparator = Comparator.comparing(Usuario::getCpf);

        List<Usuario> us = new ArrayList<>();

        Usuario u1 = new Usuario();
        u1.setNome("João");
        u1.setCpf("123");
        Usuario u2 = new Usuario();
        u2.setNome("Marcio");
        u2.setCpf("223");
        Usuario u3 = new Usuario();
        u3.setNome("Felipe");
        u3.setCpf("323");

        Abb<Usuario> abbUsuario = new Abb<>(comparator);

        abbUsuario.setRoot(abbUsuario.inserir(abbUsuario.getRoot(), u1));
        abbUsuario.setRoot(abbUsuario.inserir(abbUsuario.getRoot(), u2));
        abbUsuario.setRoot(abbUsuario.inserir(abbUsuario.getRoot(), u3));


//        abbUsuario.exibeOrdem(abbUsuario.getRoot());
        System.out.println(comparator.compare(u1, u1));

    }
}
