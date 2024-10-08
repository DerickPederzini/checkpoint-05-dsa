package br.com.fiap.cpdsa.fila;

import br.com.fiap.cpdsa.models.Usuario;

public class Fila<T> {

    public class Node<T>{
        private T dado;
        private Node prox;

        public Node(){

        }

        public Node(T dado){
            this.dado = dado;
        }

        public T getDado() {
            return dado;
        }

        public void setDado(T dado) {
            this.dado = dado;
        }

        public Node getProx() {
            return prox;
        }

        public void setProx(Node prox) {
            this.prox = prox;
        }
    }

    public Node start;
    public Node end;

    public boolean isEmpty(){
        return start == null && end == null;
    }

    public void enqueue(T element){
        Node node = new Node(element);

        if(isEmpty()){
            start = node;
        }else{
            end.prox = node;
        }
        end = node;

    }

    public Node<T> dequeue() {
        Node node = start;
        start = (Node<T>) start.prox;
        if (start == null) {
            end = null;
        }
        return node;
    }

    public void display(){
        Node aux = start;

        while(aux != null){
            System.out.println("Fila AHA "+ aux.getDado());
            aux = aux.prox;
        }
    }
}
