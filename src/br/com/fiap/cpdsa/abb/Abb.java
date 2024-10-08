package br.com.fiap.cpdsa.abb;

import br.com.fiap.cpdsa.models.Usuario;

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
        if (p == null) {
            p = new Arvore();
            p.esq = null;
            p.dir = null;
            p.dado = dado;
        } else {
            if (comparator.compare(p.dado, dado) < 0) {
                p.dir = inserir(p.dir, dado);
            } else {
                p.esq = inserir(p.esq, dado);
            }
        }
        return p;
    }

    public Arvore remover(Arvore p, T dado) {
        if (p != null) {
            if (comparator.compare(p.dado, dado) == 0) {
                if (p.esq == null && p.dir == null)
                    return null;
                if (p.esq == null) {
                    return p.dir;
                } else {
                    if (p.dir == null) {
                        return p.esq;
                    } else {
                        Arvore aux, ref;
                        ref = p.dir;
                        aux = p.dir;
                        while (aux.esq != null)
                            aux = aux.esq;
                        aux.esq = p.esq;
                        return ref;
                    }
                }
            } else {
                if (comparator.compare(p.dado, dado) > 0)
                    p.esq = remover(p.esq, dado);
                else
                    p.dir = remover(p.dir, dado);
            }
        }
        return p;
    }

    public boolean isPresente(Arvore p, T dado) {
        if (comparator.compare(p.dado, dado) == 0) {
            return true;
        } else {
            if (comparator.compare(p.dado, dado) < 0) {
                isPresente(p.dir, dado);
            } else {
                isPresente(p.esq, dado);
            }
        }
        return false;
    }

    public Usuario buscaCpf(Arvore p, String cpf) {
        if (p != null) {
            if (((Usuario) p.dado).getCpf().equalsIgnoreCase(cpf)) {
                return (Usuario) p.dado;
            }
            buscaCpf(p.dir, cpf);
            buscaCpf(p.esq, cpf);
        }
        return null;
    }

    public void buscarClientes(Arvore p) {
        if (p != null) {
            buscarClientes(p.esq);
            System.out.println(p.dado);
            buscarClientes(p.dir);
        }
    }
}
