package br.com.fiap.cpdsa.abb;

import java.util.Comparator;

public class Abb<T> {
    private class Arvore {
        Arvore esq, dir;
        T dado;
    }

    private Comparator<T> comparator;
    private Arvore root;

    public Abb(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public Arvore getRoot() {
        return root;
    }

    public void setRoot(Arvore root) {
        this.root = root;
    }



    public Arvore inserir(Arvore p, T dado) {
        if(p == null) {
            p = new Arvore();
            p.esq = null;
            p.dir = null;
            p.dado = dado;
        } else {
            if(comparator.compare(p.dado, dado) < 0) {
                p.dir = inserir(p.dir, dado);
            } else {
                p.esq = inserir(p.esq, dado);
            }
        }
        return p;
    }

    public boolean isPresente(Arvore p, T dado) {
        if(comparator.compare(p.dado, dado) == 0) {
            return true;
        } else {
            if(comparator.compare(p.dado, dado) < 0) {
                isPresente(p.dir, dado);
            } else {
                isPresente(p.esq, dado);
            }
        }
        return false;
    }

    public void exibeOrdem(Arvore p) {
        if(p != null) {
            exibeOrdem(p.esq);
            System.out.println(p.dado);
            exibeOrdem(p.dir);
        }
    }
}
