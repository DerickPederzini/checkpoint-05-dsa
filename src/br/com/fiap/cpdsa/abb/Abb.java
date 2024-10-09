package br.com.fiap.cpdsa.abb;


import br.com.fiap.cpdsa.fila.Fila;
import br.com.fiap.cpdsa.models.Usuario;
import java.util.Comparator;
import java.util.function.Function;

public class Abb<T> {
    private class Arvore<T>{
        T dado;
        Arvore esq, dir;
        //dessa forma, o dado, esquerda e direita, são alteraveis apenas dentro da ABB

    }

    private Comparator<T> comparator;
    private Arvore<T> root;

    public Abb(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public Arvore<T> getRoot() {
        return root;
    }

    public void setRoot(Arvore root) {
        this.root = root;
    }

    public Arvore<T> inserir(Arvore<T> p, T dado) {
        if(p == null) {
            p = new Arvore<T>();
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

    public boolean isPresente(Arvore<T> p, T dado) {
        if(p != null) {
            if (comparator.compare(p.dado, dado) == 0) {
                return true;
            } else {
                if (comparator.compare(p.dado, dado) < 0) {
                    return isPresente(p.dir, dado);
                } else {
                    return isPresente(p.esq, dado);
                }
            }
        }
        return false;
    }


    public Abb<Usuario> geraAbbElegivelAOferta(Arvore<Usuario> p, double minimal, Abb<Usuario> newAbb){
        //Prenche nova ABB baseada em se o cliente é apto a oferta E o valor minimo é atendido
        if(p != null) {
            Usuario u = p.dado;
            if ((u.getTotalCompras() >= minimal) && u.isAptoOferta()) {
                (u).setAptoOferta(true);
                newAbb.setRoot(newAbb.inserir(newAbb.getRoot(), u));
            }
            newAbb = geraAbbElegivelAOferta(p.esq, minimal, newAbb);
            newAbb = geraAbbElegivelAOferta(p.dir, minimal, newAbb);
        }
        return newAbb;
    }

    public Fila<T> generateQueue(Arvore<T> abb, Fila<T> fila){
        //GERA QUEUE de maneira decrescente
        //OU SEJA, do maior pro menor
        //Esse metodo precisa estar aqui na abb, não na main
        if(abb != null){
            generateQueue(abb.dir, fila);
            fila.enqueue(abb.dado);
            generateQueue(abb.esq, fila);
        }
        return fila;
    }

    public Usuario buscaCpf(Arvore<Usuario> p, String cpf) {
        if (p != null) {
            Usuario usuario = p.dado;
            if ((usuario.getCpf().equalsIgnoreCase(cpf))) {
                return usuario;
            }
            buscaCpf(p.dir, cpf);
            buscaCpf(p.esq, cpf);
        }
        return null;
    }

    public double somaGastos(Arvore<Usuario> p) {
        //SOMA GASTOS DE TODOS OS CLIENTES JUNTOS, não é gasto total de cada um
        if (p != null) {
            Usuario usuario = p.dado;
            double totalCompras = usuario.getTotalCompras();

            return somaGastos(p.esq) + somaGastos(p.dir) + totalCompras;
        }
        return 0;
    }

    public void exibeOrdem(Arvore<T> p) {
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

    public void naoParticipouOferta(Arvore<T> p, double minValue) {
        if(p != null) {
            naoParticipouOferta(p.esq, minValue);
            if (((Usuario) p.dado).getTotalCompras() < minValue) {
                System.out.println(p.dado);
            }
            naoParticipouOferta(p.dir, minValue);
        }
    }

}
