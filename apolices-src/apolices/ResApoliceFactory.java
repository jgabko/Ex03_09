package apolices;

public class ResApoliceFactory extends ApoliceFactoryAbs {

    private final double valorImovel;
    private final boolean altoPadrao;
    private final boolean documentoImovelApresentado;

    public ResApoliceFactory(double valorImovel, boolean altoPadrao, boolean documentoImovelApresentado) {
        this.valorImovel = valorImovel;
        this.altoPadrao = altoPadrao;
        this.documentoImovelApresentado = documentoImovelApresentado;
    }

    @Override
    public ApoliceAbs criarApolice(ClienteAbs cliente) {
        return new ResApolice(cliente, valorImovel, altoPadrao, documentoImovelApresentado);
    }

    @Override
    public String getPrefixo() {
        return "RES";
    }
}
