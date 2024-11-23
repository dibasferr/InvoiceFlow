public class ProdutoFarmacia extends Produto{
    private boolean hasPrescricao;
    private String medicoPrescritor;
    private CategoriaFarmacia categoria;

    public ProdutoFarmacia(String codigo, String nome, String descricao, int quantidade, double valorUnitario,
                           boolean hasPrescricao, String medicoPrescritor, CategoriaFarmacia categoria) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.hasPrescricao = hasPrescricao;
        this.medicoPrescritor = medicoPrescritor;
        this.categoria = categoria;
    }

    @Override
    public double calcularIVA(Localizacao localizacao) {
        int ivaBase = getTaxaBaseFarmacia(localizacao);

        // Se a categoria for "animais", aplica-se uma redução de 1%
        if (categoria == CategoriaFarmacia.ANIMAIS) {
            ivaBase -= 1;
        }

        return ivaBase;
    }

    private int getTaxaBaseFarmacia(Localizacao localizacao) {
        if (hasPrescricao) {
            switch (localizacao) {
                case PORTUGAL_CONTINENTAL:
                    return 6;
                case MADEIRA:
                    return 5;
                case ACORES:
                    return 4;
                default:
                    return 0;
            }
        } else {
            return 23; // Taxa normal para farmácia
        }
    }
}
