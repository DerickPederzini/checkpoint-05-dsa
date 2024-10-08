package br.com.fiap.cpdsa.abb;


import br.com.fiap.cpdsa.fila.Fila;
import br.com.fiap.cpdsa.models.Usuario;
import java.util.Comparator;

public class Abb<T> {
    private class Arvore<T> {
        Arvore esq, dir;
        T dado;
    }

    private Comparator<T> comparator;
    private Arvore<T> root;

    public Abb(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public Arvore getRoot() {
        return root;
    }

    public void setRoot(Arvore root) {
        this.root = root;
    }

    public Arvore inserir(Arvore<T> p, T dado) {
        if(p == null) {
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


    public Arvore remover(Arvore<T> p, T dado) {
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
                        Arvore<T> aux, ref;
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

    public boolean isPresente(Arvore<T> p, T dado) {
        if(comparator.compare(p.dado, dado) == 0) {
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


    public Usuario buscaCpf(Arvore<T> p, String cpf) {
        if (p != null) {
            if (((Usuario) p.dado).getCpf().equalsIgnoreCase(cpf)) {
                return (Usuario) p.dado;
            }
            buscaCpf(p.dir, cpf);
            buscaCpf(p.esq, cpf);
        }
        return null;
    }
  
    public Abb<Usuario> geraAbbElegivelAOferta(Arvore<Usuario> p, double minimal, Abb<Usuario> newAbb){
        if(p != null) {
            if (checaElegivelOferta(p.dado, minimal) && p.dado.isAptoOferta()) {
                p.dado.setAptoOferta(true);
                newAbb.setRoot(newAbb.inserir(newAbb.getRoot(), p.dado));
            }
            newAbb = geraAbbElegivelAOferta(p.esq, minimal, newAbb);
            newAbb = geraAbbElegivelAOferta(p.dir, minimal, newAbb);
            }
        return newAbb;
    }

    private boolean checaElegivelOferta(Usuario u, double minimal) {
        return u.getTotalCompras() >= minimal;
    }

    public Fila generateQueue(Arvore abb, Fila fila){
        if(abb != null){
            generateQueue(abb.dir, fila);
            fila.enqueue(abb.dado);
            generateQueue(abb.esq, fila);
        }
        return fila;
    }

    public void exibeOrdem(Arvore p) {
        if(p != null) {
            exibeOrdem(p.esq);
            System.out.println(p.dado);
            exibeOrdem(p.dir);
        }
    }

    public int limitePorSaldo(Arvore<Usuario> p, Double minValue) {

        if (p != null) {
            Usuario usuario = p.dado;
            double totalCompras = usuario.getTotalCompras();

            if(minValue <= totalCompras){
                return 1 + limitePorSaldo(p.esq, minValue) + limitePorSaldo(p.dir, minValue);
            }else{
                return limitePorSaldo(p.esq, minValue) + limitePorSaldo(p.dir, minValue);
            }
        }
        return 0;
    }

    public void ofertaNaoAceita(Arvore p) {
        if(p != null) {
            ofertaNaoAceita(p.esq);
            if (((Usuario) p.dado).isAptoOferta()) {
                System.out.println(p.dado);
            }
            ofertaNaoAceita(p.dir);
        }
    }

}
