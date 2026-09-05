package apolices;

import java.util.ArrayList;
import java.util.List;

/**
 * RF04 — Apólice Viagem.
 * coberturaMedica e passaporteApresentado foram acrescentados em
 * relação ao diagrama: necessários para validar as exigências de
 * viagens internacionais.
 */
public class ViaApolice extends ApoliceAbs {

    private static final double COBERTURA_MEDICA_MINIMA = 30_000.00;

    private final int diasViagem;
    private final boolean destinoInternacional;
    private final double coberturaMedica;
    private final boolean passaporteApresentado;

    public ViaApolice(ClienteAbs cliente, int diasViagem, boolean destinoInternacional,
                       double coberturaMedica, boolean passaporteApresentado) {
        super(cliente);
        this.diasViagem = diasViagem;
        this.destinoInternacional = destinoInternacional;
        this.coberturaMedica = coberturaMedica;
        this.passaporteApresentado = passaporteApresentado;
    }

    @Override
    public double calcularPremio() {
        double premio = diasViagem * 15.00;
        if (destinoInternacional) {
            premio += 100.00;
        }
        return premio;
    }

    @Override
    public boolean validarCobertura() {
        if (destinoInternacional) {
            return coberturaMedica >= COBERTURA_MEDICA_MINIMA && passaporteApresentado;
        }
        return true;
    }

    @Override
    public List<String> listarDocumentos() {
        List<String> docs = new ArrayList<>(List.of("Itinerário de viagem"));
        if (destinoInternacional) {
            docs.add("Passaporte");
        }
        return docs;
    }
}
