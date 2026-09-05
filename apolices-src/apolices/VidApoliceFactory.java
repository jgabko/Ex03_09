package apolices;

public class VidApoliceFactory extends ApoliceFactoryAbs {

    private final int idade;
    private final double capitalSegurado;
    private final boolean fumante;
    private final boolean atestadoMedicoApresentado;

    public VidApoliceFactory(int idade, double capitalSegurado, boolean fumante,
                              boolean atestadoMedicoApresentado) {
        this.idade = idade;
        this.capitalSegurado = capitalSegurado;
        this.fumante = fumante;
        this.atestadoMedicoApresentado = atestadoMedicoApresentado;
    }

    @Override
    public ApoliceAbs criarApolice(ClienteAbs cliente) {
        return new VidApolice(cliente, idade, capitalSegurado, fumante, atestadoMedicoApresentado);
    }

    @Override
    public String getPrefixo() {
        return "VID";
    }
}
