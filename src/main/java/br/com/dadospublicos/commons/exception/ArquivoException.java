package br.com.dadospublicos.commons.exception;

public class ArquivoException extends Exception {

    private ArquivoException(String mensagem) {
        super(mensagem);
    }

    private ArquivoException(String mensagem, Exception e) {
        super(mensagem, e);
    }

    public static class ArquivoCorrompidoException extends ArquivoException {
        public ArquivoCorrompidoException() {
            super(null);
        }

        public ArquivoCorrompidoException(String mensagem) {
            super(mensagem);
        }
    }

    public static class PrefixoInvalidoException extends ArquivoException {
        public PrefixoInvalidoException() {
            super(null);
        }

        public PrefixoInvalidoException(String mensagem) {
            super(mensagem);
        }

        public PrefixoInvalidoException(String mensagem, Exception e) {
            super(mensagem, e);
        }
    }
}
