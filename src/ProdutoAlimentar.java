import java.util.ArrayList;

public class ProdutoAlimentar extends Produto{
    private boolean isBiologico;
    private Taxa taxa;
    private ArrayList<Certificacao> certificacoes;
    private CategoriaAlimentar categoria;

    public ProdutoAlimentar(String codigo, String nome, String descricao, int quantidade, double valorUnitario,
                            boolean isBiologico, Taxa taxa, ArrayList<Certificacao> certificacoes, CategoriaAlimentar categoria){
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.isBiologico = isBiologico;
        this.taxa = taxa;
        this.certificacoes = certificacoes;
        this.categoria = categoria;
    }

    @Override
    public double calcularIVA(Localizacao localizacao){
            int ivaBase = 0;
            if(localizacao.equals(Localizacao.PORTUGAL_CONTINENTAL)){
                ivaBase = getTaxaBaseContinente();
            }
            else if(localizacao.equals(Localizacao.MADEIRA)){
                ivaBase = getTaxaBaseMadeira();
            }
            else if(localizacao.equals(Localizacao.ACORES)){
                ivaBase = getTaxaBaseAcores();
            }

            // Verificar as certificações (4 certificações = -1% no IVA)
            if (taxa.equals(Taxa.REDUZIDA) && certificacoes.size() == 4) {
                ivaBase -= 1;
            }

            if (taxa.equals(Taxa.INTERMEDIARIA) && categoria == CategoriaAlimentar.VINHOS) {
                ivaBase += 1;
            }

            // Desconto adicional se for biológico
            if (isBiologico) {
               ivaBase -= ivaBase * 0.1; // Desconto de 10% no IVA
            }

            // Calcular o IVA final
            return ivaBase;
    }

    public int getTaxaBaseContinente(){
        switch (taxa){
            case REDUZIDA:
                return 6;
            case INTERMEDIARIA:
                return 13;
            case NORMAL:
                return 23;
            default:
                return 0;
        }
    }

    public int getTaxaBaseMadeira(){
        switch (taxa){
            case REDUZIDA:
                return 5;
            case INTERMEDIARIA:
                return 12;
            case NORMAL:
                return 22;
            default:
                return 0;
        }
    }

    public int getTaxaBaseAcores(){
        switch (taxa){
            case REDUZIDA:
                return 4;
            case INTERMEDIARIA:
                return 9;
            case NORMAL:
                return 16;
            default:
                return 0;
        }
    }
}
