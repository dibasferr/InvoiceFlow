import java.io.Serializable;

public abstract class Produto implements Serializable {
    private String codigo;
    private String nome;
    private String descricao;
    private int quantidade;
    private double valorUnitario;

    public Produto(String codigo, String nome, String descricao, int quantidade, double valorUnitario){
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public String getCodigo(){ return codigo;}
    public String getNome(){ return nome;}
    public String getDescricao(){ return descricao;}
    public int getQuantidade(){ return quantidade;}
    public double getValorUnitario(){ return valorUnitario;}

    // Metodo abstrato para calcular o IVA de acordo com o tipo de Produto
    public abstract double calcularIVA(Localizacao localizacao);

    public double valorProdutosSemIVA(){
        return valorUnitario * quantidade;
    }

    public double valorProdutosComIVA(Localizacao localizacao){
        return valorProdutoComIVA(localizacao) * quantidade;
    }

    public double valorProdutoComIVA(Localizacao localizacao){
        return valorUnitario + valorDoIVA(localizacao);
    }

    public double valorDoIVA(Localizacao localizacao){
        double taxaIVA = calcularIVA(localizacao);
        return valorUnitario * (taxaIVA / 100);
    }

    public void valoresProduto(){
        System.out.println("Codigo do produto: " + codigo);
        System.out.println("Nome do produto: " + nome);
        System.out.println("Descricao do produto: " + descricao);
        System.out.println("Quantidade do produto: " + quantidade);
        System.out.println("Valor unitario do produto (Preço sem IVA): " + valorUnitario);
        System.out.print("\n");
    }
}
