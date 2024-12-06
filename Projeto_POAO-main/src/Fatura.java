import java.io.Serializable;
import java.util.ArrayList;

public class Fatura implements Serializable {
    private int numeroFatura;
    private Cliente cliente;
    private String data;
    private ArrayList<Produto> produtos;

    public Fatura(int numeroFatura, Cliente cliente, String data){
        this.numeroFatura = numeroFatura;
        this.cliente = cliente;
        this.data = data;
        this.produtos = new ArrayList<>();
    }

    public int getNumeroFatura(){ return numeroFatura; }

    public Cliente getCliente(){ return cliente; }

    public String getData(){ return data; }

    public ArrayList<Produto> getProdutos(){return produtos;}

    public void addProduto(Produto produto){
        produtos.add(produto);
    }

    public void setNumeroFatura(int novoNumeroFatura){this.numeroFatura= novoNumeroFatura;}

    public void setData(String novaData){ this.data= novaData;}

    public void setCliente(Cliente cliente){this.cliente= cliente;}

    // Calula a quantidade de produtos na lista de produtos
    public int calcularNumProdutos(){
        int quantidade = 0;
        for(Produto _ : produtos){
            quantidade += 1;
        }
        return quantidade;
    }

    // Calcula o valor total da lista sem taxa de imposto (IVA)
    public double calcularValorSemIVA(){
        double total = 0;
        for(Produto produto : produtos){
            total += produto.valorProdutosSemIVA();
        }
        return total;
    }

    // Calcula o valor total da lista com taxa de imposto (IVA)
    public double calcularValorComIVA(){
        double total = 0;
        Localizacao localizacao = cliente.getLocalizacao();
        for(Produto produto : produtos){
            total += produto.valorProdutosComIVA(localizacao);
        }
        return total;
    }

    public void listarProdutos(){
        System.out.println("----- Lista de produtos -----");
        Localizacao localizacao = cliente.getLocalizacao();
        for(Produto produto : produtos){
            produto.valoresProduto();
            System.out.println("Percentagem do IVA: " + produto.calcularIVA(localizacao) + "%");
            System.out.printf("Valor do IVA: %.2f \n", produto.valorDoIVA(localizacao));
            System.out.printf("Preço com IVA do produto: %.2f \n", produto.valorProdutoComIVA(localizacao));
        }
        System.out.printf("Valor total dos produtos da fatura sem IVA: %.2f \n", calcularValorSemIVA());
        double valorTotalIVA = calcularValorComIVA() - calcularValorSemIVA();
        System.out.printf("Valor total do IVA: %.2f \n", valorTotalIVA);
        System.out.printf("Valor total dos produtos da fatura com IVA: %.2f \n", calcularValorComIVA());
    }
}