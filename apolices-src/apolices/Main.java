package apolices;

/**
 * Classe de teste: emite uma apólice de cada linha de produto através
 * de SistemaEmissao e imprime o resumo gerado para cada uma.
 */
public class Main {

    public static void main(String[] args) {
        SistemaEmissao sistema = new SistemaEmissao();

        // Registro das fábricas — cada uma já parametrizada com os
        // dados da cotação (ver comentário em AutoApoliceFactory).
        sistema.registrarFabrica("auto",
                new AutoApoliceFactory(60_000.00, 22, 1, 60_000.00));
        sistema.registrarFabrica("residencial",
                new ResApoliceFactory(400_000.00, true, true));
        sistema.registrarFabrica("vida",
                new VidApoliceFactory(40, 600_000.00, false, true));
        sistema.registrarFabrica("viagem",
                new ViaApoliceFactory(10, true, 35_000.00, true));

        Cliente joao = new Cliente("João da Silva", 22);
        Cliente maria = new Cliente("Maria Souza", 40);

        System.out.println(sistema.emitirApolice("auto", joao));
        System.out.println(sistema.emitirApolice("residencial", maria));
        System.out.println(sistema.emitirApolice("vida", maria));
        System.out.println(sistema.emitirApolice("viagem", joao));

        // Exemplo de rejeição: cobertura de terceiros abaixo do mínimo.
        sistema.registrarFabrica("auto_invalido",
                new AutoApoliceFactory(60_000.00, 30, 5, 10_000.00));
        try {
            sistema.emitirApolice("auto_invalido", joao);
        } catch (IllegalStateException e) {
            System.out.println("Rejeição esperada -> " + e.getMessage());
        }
    }
}
