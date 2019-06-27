package br.ufrpe.bsi.mpoo.petspeed.infra.negocio;

public enum  Sintomas {

    Vomito("Vomito"),Diarreia("Diarreia"), Desidratacao("Desidratação"),Febre("Febre"),Letargia("Letargia"),
    OlhosVermelhos("Olhos Vermelhos"),Tosse("Tosse"),DorAbdominal("Dor Abdominal"),Paralisia("Paralisia"),ExcessoDeUrina("Excesso de Urina"),
    MandibulaCaida("Mandibula Caida"),OlhosAmarelados("Olhos Amarelados"),RespiracaoOfegante("Respiração Ofegante"),
    FadigaConstante("Fadiga Constante"),EspumaNaBoca("Espuma na boca"),TosseChiada("Tosse Chiada"),Indisposicao("Indisposição)"),
    PerdaDeApetite("Perda de Apetite"),OlhosAvermelhados("Olhos Avermelhados"),NarizEscorrendo("Nariz Escorrendo"),
    FaltaDeAr("Falta de Ar"),GengivasPalidas("Gengivas Pálidas"),Espirros("Espirros"),FezesComSangue("Fezes com Sangue"),
    DoresNoCorpo("Dores No Corpo"),Nauseas("Náusesas"),DificuldadeDeEngolir("Dificuldade de Engolir"),
    SedeExcessiva("Sede Excessiva"),AgressividadeIncomun("Agressividade Incomum"),DescoordenacaoMotora("Descoordenação Motora"),
    FerimentosDesconhecidos("Ferimentos Desconhecidos");

    public String descricao;

    Sintomas(String descricao){this.descricao = descricao;}

    public String getDescricao(){return descricao;}


    @Override
    public String toString() {
        return String.valueOf(this.name());
    }



}
