public abstract class Produto {
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

    public double getValorUnitario(){ return valorUnitario;}

    // Metodo abstrato para calcular o IVA de acordo com o tipo de Produto
    public abstract double calcularIVA(Localizacao localizacao);

    public double valorProdutosSemIVA(){
        return valorUnitario * quantidade;
    }

    public double valorProdutosComIVA(Localizacao localizacao){ 
        return (valorUnitario + valorDoIVA(localizacao)) * quantidade;
    }

    public double valorDoIVA(Localizacao localizacao){
        double taxaIVA = calcularIVA(localizacao);
        return valorUnitario * taxaIVA;
    }
}
