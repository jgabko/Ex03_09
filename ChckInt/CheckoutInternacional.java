import java.util.Locale;

// ===== Interfaces de produto (RF01-RF03) =====
interface DocumentoFiscal {
    String emitir(double valor);
}

interface ProcessadorPagamento {
    String processar(double valor);
}

interface EtiquetaEnvio {
    String gerar();
}

// ===== Abstract Factory =====
interface CheckoutFactory {
    DocumentoFiscal criarDocumentoFiscal();
    ProcessadorPagamento criarProcessadorPagamento();
    EtiquetaEnvio criarEtiquetaEnvio();
}

// ===== Brasil (RF01) =====
class NotaFiscalEletronica implements DocumentoFiscal {
    private final boolean interestadual;

    NotaFiscalEletronica(boolean interestadual) {
        this.interestadual = interestadual;
    }

    public String emitir(double valor) {
        String cfop = interestadual ? "6.102" : "5.102";
        double aliquota = interestadual ? 0.12 : 0.18;
        double icms = valor * aliquota;
        String chave = String.format(Locale.US, "%044d", (long) (valor * 1000));
        return String.format(Locale.US,
                "NF-e | CFOP %s | ICMS %.2f%% (R$ %.2f) | chave %s",
                cfop, aliquota * 100, icms, chave);
    }
}

class PagamentoPix implements ProcessadorPagamento {
    public String processar(double valor) {
        double desconto = valor * 0.05;
        return String.format(Locale.US,
                "Pix | valor pago R$ %.2f (desconto de 5%% = R$ %.2f)",
                valor - desconto, desconto);
    }
}

class PagamentoBoleto implements ProcessadorPagamento {
    public String processar(double valor) {
        return String.format(Locale.US,
                "Boleto | valor R$ %.2f | compensação em 3 dias úteis", valor);
    }
}

class EtiquetaCorreios implements EtiquetaEnvio {
    public String gerar() {
        return "Correios | CEP 80000-000";
    }
}

class BrasilFactory implements CheckoutFactory {
    private final boolean interestadual;
    private final boolean pix;

    BrasilFactory(boolean interestadual, boolean pix) {
        this.interestadual = interestadual;
        this.pix = pix;
    }

    public DocumentoFiscal criarDocumentoFiscal() {
        return new NotaFiscalEletronica(interestadual);
    }

    public ProcessadorPagamento criarProcessadorPagamento() {
        return pix ? new PagamentoPix() : new PagamentoBoleto();
    }

    public EtiquetaEnvio criarEtiquetaEnvio() {
        return new EtiquetaCorreios();
    }
}

// ===== Estados Unidos (RF02) =====
class SalesInvoice implements DocumentoFiscal {
    private final String estado;

    SalesInvoice(String estado) {
        this.estado = estado;
    }

    public String emitir(double valor) {
        double aliquota;
        switch (estado) {
            case "California": aliquota = 0.0725; break;
            case "Texas": aliquota = 0.0625; break;
            case "Oregon": aliquota = 0.0; break;
            default: aliquota = 0.0;
        }
        double tax = valor * aliquota;
        return String.format(Locale.US,
                "Sales invoice | estado %s | sales tax %.2f%% (US$ %.2f) | EIN 12-3456789",
                estado, aliquota * 100, tax);
    }
}

class PagamentoCartaoCredito implements ProcessadorPagamento {
    public String processar(double valor) {
        return String.format(Locale.US,
                "Cartão de crédito | valor US$ %.2f | AVS verificado", valor);
    }
}

class EtiquetaUSPS implements EtiquetaEnvio {
    public String gerar() {
        return "USPS | ZIP+4 90210-1234";
    }
}

class EstadosUnidosFactory implements CheckoutFactory {
    private final String estado;

    EstadosUnidosFactory(String estado) {
        this.estado = estado;
    }

    public DocumentoFiscal criarDocumentoFiscal() {
        return new SalesInvoice(estado);
    }

    public ProcessadorPagamento criarProcessadorPagamento() {
        return new PagamentoCartaoCredito();
    }

    public EtiquetaEnvio criarEtiquetaEnvio() {
        return new EtiquetaUSPS();
    }
}

// ===== Alemanha (RF03) =====
class VatInvoice implements DocumentoFiscal {
    private final boolean essencial;

    VatInvoice(boolean essencial) {
        this.essencial = essencial;
    }

    public String emitir(double valor) {
        double aliquota = essencial ? 0.07 : 0.19;
        double ust = valor * aliquota;
        return String.format(Locale.US,
                "VAT invoice | Umsatzsteuer %.0f%% (%.2f EUR) | VAT-ID DE123456789",
                aliquota * 100, ust);
    }
}

class PagamentoSepa implements ProcessadorPagamento {
    public String processar(double valor) {
        return String.format(Locale.US,
                "SEPA Direct Debit | valor %.2f EUR", valor);
    }
}

class EtiquetaDeutschePost implements EtiquetaEnvio {
    public String gerar() {
        return "Deutsche Post | PLZ 10115";
    }
}

class AlemanhaFactory implements CheckoutFactory {
    private final boolean essencial;

    AlemanhaFactory(boolean essencial) {
        this.essencial = essencial;
    }

    public DocumentoFiscal criarDocumentoFiscal() {
        return new VatInvoice(essencial);
    }

    public ProcessadorPagamento criarProcessadorPagamento() {
        return new PagamentoSepa();
    }

    public EtiquetaEnvio criarEtiquetaEnvio() {
        return new EtiquetaDeutschePost();
    }
}

// ===== Checkout (RNF01, RNF02, RNF03) =====
// Depende apenas das abstrações: sem "if/switch por país" aqui.
class Checkout {
    private final CheckoutFactory factory;

    Checkout(CheckoutFactory factory) {
        this.factory = factory;
    }

    String finalizarPedido(double valor) {
        DocumentoFiscal documento = factory.criarDocumentoFiscal();
        ProcessadorPagamento pagamento = factory.criarProcessadorPagamento();
        EtiquetaEnvio etiqueta = factory.criarEtiquetaEnvio();

        return "=== Relatório do pedido ===\n"
                + "Documento fiscal : " + documento.emitir(valor) + "\n"
                + "Pagamento        : " + pagamento.processar(valor) + "\n"
                + "Etiqueta de envio: " + etiqueta.gerar() + "\n";
    }
}

// ===== Main =====
public class CheckoutInternacional {
    public static void main(String[] args) {
        Checkout checkoutBrasil = new Checkout(new BrasilFactory(false, true));
        System.out.println("Pedido Brasil");
        System.out.println(checkoutBrasil.finalizarPedido(500.0));

        Checkout checkoutEua = new Checkout(new EstadosUnidosFactory("California"));
        System.out.println("Pedido Estados Unidos");
        System.out.println(checkoutEua.finalizarPedido(200.0));

        Checkout checkoutAlemanha = new Checkout(new AlemanhaFactory(false));
        System.out.println("Pedido Alemanha");
        System.out.println(checkoutAlemanha.finalizarPedido(150.0));
    }
}
