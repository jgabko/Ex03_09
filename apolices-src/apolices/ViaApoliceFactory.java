package apolices;

public class ViaApoliceFactory extends ApoliceFactoryAbs {

    private final int diasViagem;
    private final boolean destinoInternacional;
    private final double coberturaMedica;
    private final boolean passaporteApresentado;

    public ViaApoliceFactory(int diasViagem, boolean destinoInternacional, double coberturaMedica,
                              boolean passaporteApresentado) {
        this.diasViagem = diasViagem;
        this.destinoInternacional = destinoInternacional;
        this.coberturaMedica = coberturaMedica;
        this.passaporteApresentado = passaporteApresentado;
    }

    @Override
    public ApoliceAbs criarApolice(ClienteAbs cliente) {
        return new ViaApolice(cliente, diasViagem, destinoInternacional, coberturaMedica, passaporteApresentado);
    }

    @Override
    public String getPrefixo() {
        return "VIA";
    }
}
