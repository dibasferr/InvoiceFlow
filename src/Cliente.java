public class Cliente {
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
}
