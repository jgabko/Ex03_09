package apolices;

import java.util.ArrayList;
import java.util.List;

/**
 * RF03 — Apólice Vida.
 * atestadoMedicoApresentado foi acrescentado em relação ao diagrama:
 * necessário para validar a exigência de atestado médico quando o
 * capital segurado ultrapassa R$ 500.000,00.
 */
public class VidApolice extends ApoliceAbs {

    private static final double LIMITE_CAPITAL_SEM_ATESTADO = 500_000.00;

    private final int idade;
    private final double capitalSegurado;
    private final boolean fumante;
    private final boolean atestadoMedicoApresentado;

    public VidApolice(ClienteAbs cliente, int idade, double capitalSegurado,
                       boolean fumante, boolean atestadoMedicoApresentado) {
        super(cliente);
        this.idade = idade;
        this.capitalSegurado = capitalSegurado;
        this.fumante = fumante;
        this.atestadoMedicoApresentado = atestadoMedicoApresentado;
    }

    @Override
    public double calcularPremio() {
        double premio = (idade * 12) + (capitalSegurado * 0.002);
        if (fumante) {
            premio *= 1.5;
        }
        return premio;
    }

    @Override
    public boolean validarCobertura() {
        if (capitalSegurado > LIMITE_CAPITAL_SEM_ATESTADO) {
            return atestadoMedicoApresentado;
        }
        return true;
    }

    @Override
    public List<String> listarDocumentos() {
        List<String> docs = new ArrayList<>(List.of("Documento de identidade", "CPF"));
        if (capitalSegurado > LIMITE_CAPITAL_SEM_ATESTADO) {
            docs.add("Atestado médico");
        }
        return docs;
    }
}
