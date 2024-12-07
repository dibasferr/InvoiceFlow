package aplicacao;

import enums.Localizacao;
import java.io.Serializable;

public class Cliente implements Serializable {
    private String nome;
    private int contribuinte;
    private Localizacao localizacao;

    Cliente(String nome, int contribuinte, Localizacao localizacao){
        this.nome = nome;
        this.contribuinte = contribuinte;
        this.localizacao = localizacao;
    }

    String getNome(){ return nome; }

    int getContribuinte(){ return contribuinte; }

    Localizacao getLocalizacao(){ return localizacao; }

    void setNome(String novoNome){ this.nome=novoNome;}

    void setContribuinte(int contribuinte) { this.contribuinte = contribuinte;}

    void setLocalizacao(Localizacao localizacao){ this.localizacao = localizacao;}
}