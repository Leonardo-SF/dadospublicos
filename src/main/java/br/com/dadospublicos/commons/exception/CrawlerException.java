package br.com.dadospublicos.commons.exception;

public class CrawlerException extends Exception {

    private CrawlerException(String mensagem) {
        super(mensagem);
    }

    public static class URLInvalidaException extends CrawlerException {
        public URLInvalidaException() {
            super(null);
        }

        public URLInvalidaException(String mensagem) {
            super(mensagem);
        }
    }

    public static class HtmlException extends CrawlerException {
        public HtmlException(String mensagem) {
            super(mensagem);
        }
    }

    public static class PaginaNaoEncontradaException extends CrawlerException {
        public PaginaNaoEncontradaException(String mensagem) {
            super(mensagem);
        }
    }

    public static class ErroInternoException extends CrawlerException {
        public ErroInternoException(String mensagem) {
            super(mensagem);
        }
    }

    public static class RequisicaoInvalidaException extends CrawlerException {
        public RequisicaoInvalidaException(String mensagem) {
            super(mensagem);
        }
    }

    public static class RecursoInvalidoException extends CrawlerException {
        public RecursoInvalidoException() {
            super(null);
        }

        public RecursoInvalidoException(String mensagem) {
            super(mensagem);
        }
    }

    public static class OperacaoNaoSuportadaException extends CrawlerException {
        public OperacaoNaoSuportadaException() {
            super(null);
        }

        public OperacaoNaoSuportadaException(String mensagem) {
            super(mensagem);
        }
    }
}
