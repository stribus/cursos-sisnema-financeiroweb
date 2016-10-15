package br.com.sisnema.financeiroweb.domain;

public enum SituacaoCheque {

	BAIXADO("Baixado"),
	CANCELADO("Cancelado"),
	NAO_EMITIDO("N�o emitido");

	private String descricao;

	private SituacaoCheque(String descricao) {
		this.descricao = descricao;
	}	
	
	@Override
	public String toString() {
		return descricao;
	}
}
