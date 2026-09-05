package apolices;

/**
 * Fábrica concreta da linha Auto. Os dados específicos da contratação
 * (valor FIPE, idade do condutor etc.) entram pelo construtor porque a
 * assinatura do método fábrica, definida na superclasse, recebe apenas
 * o ClienteAbs — cada instância desta fábrica representa uma cotação.
 */
public class AutoApoliceFactory extends ApoliceFactoryAbs {

    private final double valorFipe;
    private final int idadeCondutor;
    private final int tempoHabilitacao;
    private final double coberturaTerceiros;

    public AutoApoliceFactory(double valorFipe, int idadeCondutor, int tempoHabilitacao,
                               double coberturaTerceiros) {
        this.valorFipe = valorFipe;
        this.idadeCondutor = idadeCondutor;
        this.tempoHabilitacao = tempoHabilitacao;
        this.coberturaTerceiros = coberturaTerceiros;
    }

    @Override
    public ApoliceAbs criarApolice(ClienteAbs cliente) {
        return new AutoApolice(cliente, valorFipe, idadeCondutor, tempoHabilitacao, coberturaTerceiros);
    }

    @Override
    public String getPrefixo() {
        return "AUTO";
    }
}
