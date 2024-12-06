import java.io.Serializable;

public class Cliente implements Serializable {
    private String nome;
    private int contribuinte;
    private Localizacao localizacao;

    public Cliente(String nome, int contribuinte, Localizacao localizacao){
        this.nome = nome;
        this.contribuinte = contribuinte;
        this.localizacao = localizacao;
    }

    public String getNome(){ return nome; }

    public int getContribuinte(){ return contribuinte; }

    public Localizacao getLocalizacao(){ return localizacao; }

    public void setNome(String novoNome){ this.nome=novoNome;}

    public void setContribuinte(int contribuinte) { this.contribuinte = contribuinte;}

    public void setLocalizacao(Localizacao localizacao){ this.localizacao = localizacao;}
}