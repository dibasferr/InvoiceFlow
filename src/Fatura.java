import java.util.ArrayList;

public class Fatura {
    private int numeroFatura;
    private Cliente cliente;
    private String data;
    private ArrayList<Produto> produtos;

    public Fatura(int numeroFatura, Cliente cliente, String data, ArrayList<Produto> produtos){
        this.numeroFatura = numeroFatura;
        this.cliente = cliente;
        this.data = data;
        this.produtos = produtos;
    }

    public int getNumeroFatura(){ return numeroFatura; }

    public Cliente getCliente(){ return cliente; }

    public String getData(){ return data; }

    // Calula a quantidade de produtos na lista de produtos
    public int calcularNumProdutos(){
        int quantidade = 0;
        for(Produto produto : produtos){
            quantidade += 1;
        }
        return quantidade;
    }

    // Calcula o valor total da lista sem taxa de imposto (IVA)
    public double calcularValorSemIVA(){
        double total = 0;
        for(Produto produto : produtos){
            total += produto.getValorUnitario();
        }
        return total;
    }

    // Calcula o valor total da lista com taxa de imposto (IVA)
    public double calcularValorComIVA(){
        double total = 0;
        Localizacao localizacao = cliente.getLocalizacao();
        for(Produto produto : produtos){
            total += produto.calcularIVA(localizacao);
        }
        return total;
    }
}
