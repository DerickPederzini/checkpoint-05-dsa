package br.com.fiap.cpdsa.models;

public class Usuario implements Comparable<Usuario>{
    private String nome;
    private String cpf;
    private String whatsapp;
    private double totalCompras;
    private boolean aptoOferta;

    public Usuario(String nome, String cpf, String whatsapp, double totalCompras, boolean aptoOferta) {
        this.nome = nome;
        this.cpf = cpf;
        this.whatsapp = whatsapp;
        this.totalCompras = totalCompras;
        this.aptoOferta = aptoOferta;
    }

    public Usuario() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getwhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public double getTotalCompras() {
        return totalCompras;
    }

    public void setTotalCompras(double totalCompras) {
        this.totalCompras = totalCompras;
    }

    public boolean isAptoOferta() {
        return aptoOferta;
    }

    public void setAptoOferta(boolean aptoOferta) {
        this.aptoOferta = aptoOferta;
    }

    @Override
    public int compareTo(Usuario o) {
        if(this.cpf.compareTo(o.getCpf()) == 1) {
            return 1;
        }
        if(this.cpf.compareTo(o.getCpf()) == -1) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return nome + " - " + cpf + " - " + totalCompras + " - " + aptoOferta;
    }
}
