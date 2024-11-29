import java.util.ArrayList;

public class Empresa {
    private ArrayList<Fatura> faturas;
    private ArrayList<Cliente> clientes;

    public Empresa(){
        faturas = new ArrayList<>();
        clientes = new ArrayList<>();
    }

    // Adiciona um cliente na lista de clientes
    public void addListaCliente(Cliente cliente){
        clientes.add(cliente);
    }

    // Adiciona uma fatura na lista de faturas
    public void addListaFatura(Fatura fatura){
        faturas.add(fatura);
    }

    // Procura um cliente na lista pelo numero de contribuinte
    public Cliente procurarCliente(int contribuinte){
        for(Cliente cliente : clientes){
            if(cliente.getContribuinte() == contribuinte){
                return cliente;
            }
        }
        System.out.println("Cliente nao encontrado na lista!");
        return null;
    }

    // Procura uma fatura na lista pelo numero da fatura
    public Fatura procurarFatura(int numeroFatura){
        for(Fatura fatura : faturas){
            if(fatura.getNumeroFatura() == numeroFatura){
                return fatura;
            }
        }
        System.out.println("Fatura nao encontrada na lista!");
        return null;
    }

    // Lista as todas as informaçoes dos clientes da lista de clientes
    public void listarClientes(){
        System.out.println("----- Lista de clientes -----");
        for(Cliente cliente : clientes){
            System.out.println("nome: " + cliente.getNome());
            System.out.println("contribuinte: " + cliente.getContribuinte());
            System.out.println("localizacao: " + cliente.getLocalizacao());
            System.out.println("----------");
        }
    }

    // Lista as informacoes das faturas da lista de faturas
    public void listarFaturas(){
        System.out.println("----- Lista de faturas -----");
        for(Fatura fatura : faturas){
            System.out.println("numero da fatura: " + fatura.getNumeroFatura());
            System.out.println("data da fatura: " + fatura.getData());
            System.out.println("cliente: " + fatura.getCliente().getNome());
            System.out.println("localizacao do cliente: " + fatura.getCliente().getLocalizacao());
            System.out.println("quantidade de produtos: " + fatura.calcularNumProdutos());
            System.out.printf("valor total sem IVA: %.2f \n", fatura.calcularValorSemIVA());
            System.out.printf("valor total com IVA: %.2f \n", fatura.calcularValorComIVA());
            System.out.println("----------");
        }
    }

    // Visualizar as informacoes de uma determinada fatura
    public void visualizarFaturas(Fatura fatura){
        System.out.println("----- Visualizacao da fatura -----");
        System.out.println("numero da fatura: " + fatura.getNumeroFatura());
        System.out.println("nome: " + fatura.getCliente().getNome());
        System.out.println("contribuinte: " + fatura.getCliente().getContribuinte());
        System.out.println("localizacao: " + fatura.getCliente().getLocalizacao());
        fatura.listarProdutos();
        System.out.printf("valor total sem IVA: %.2f \n", fatura.calcularValorSemIVA());
        System.out.printf("valor total com IVA: %.2f \n", fatura.calcularValorComIVA());
    }

    public int quantidadeFaturas(){
        int quantidade = 0;
        for(Fatura _ : faturas){
            quantidade += 1;
        }
        return quantidade;
    }

    public int quantidadeProdutos(){
        int quantidade = 0;
        for(Fatura fatura : faturas){
            quantidade += fatura.calcularNumProdutos();
        }
        return quantidade;
    }

    public double valorTotalSemIVA(){
        double valor = 0;
        for(Fatura fatura : faturas){
            valor += fatura.calcularValorSemIVA();
        }
        return valor;
    }

    public double valorTotalComIVA(){
        double valor = 0;
        for(Fatura fatura : faturas){
            valor += fatura.calcularValorComIVA();
        }
        return valor;
    }

    public double valorTotalDoIVA(){
        return valorTotalComIVA() - valorTotalSemIVA();
    }
}
