package br.ufrpe.bsi.mpoo.petspeed.infra.negocio;

public class FiltroBuscaMedicos {
    public enum Tipo {
        CIDADE("CIDADE"), ESTADO("UF"), NOME("Nome");
        private final String descricao;

        Tipo(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

        @Override
        public String toString() {
            return this.descricao;
        }
    }

    public enum Estados {
        PERNAMBUCO("PE"), PARAIBA("PB");
        private final String descricao;

        Estados(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

        @Override
        public String toString() {
            return this.descricao;
        }
    }

    public enum Cidades {
        RECIFE("Recife"), JOAO_PESSOA("João Pessoa");
        private final String descricao;

        Cidades(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

        @Override
        public String toString() {
            return this.descricao;
        }
    }
}
