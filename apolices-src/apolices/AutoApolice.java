package apolices;

import java.util.List;

/**
 * RF01 — Apólice Auto.
 * coberturaTerceiros foi acrescentado em relação ao diagrama: é o dado
 * necessário para aplicar a regra de validação de cobertura mínima
 * (R$ 50.000,00) exigida pela especificação.
 */
public class AutoApolice extends ApoliceAbs {

    private final double valorFipe;
    private final int idadeCondutor;
    private final int tempoHabilitacao;
    private final double coberturaTerceiros;

    public AutoApolice(ClienteAbs cliente, double valorFipe, int idadeCondutor,
                        int tempoHabilitacao, double coberturaTerceiros) {
        super(cliente);
        this.valorFipe = valorFipe;
        this.idadeCondutor = idadeCondutor;
        this.tempoHabilitacao = tempoHabilitacao;
        this.coberturaTerceiros = coberturaTerceiros;
    }

    @Override
    public double calcularPremio() {
        double premioAnual = valorFipe * 0.08;
        if (idadeCondutor < 25) {
            premioAnual *= 1.30;
        }
        if (tempoHabilitacao < 2) {
            premioAnual *= 1.20;
        }
        return premioAnual / 12.0;
    }

    @Override
    public boolean validarCobertura() {
        return coberturaTerceiros >= 50_000.00;
    }

    @Override
    public List<String> listarDocumentos() {
        return List.of("CNH", "CRLV", "Comprovante de residência");
    }
}
