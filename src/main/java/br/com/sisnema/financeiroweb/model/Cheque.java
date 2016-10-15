package br.com.sisnema.financeiroweb.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import br.com.sisnema.financeiroweb.domain.SituacaoCheque;

@Entity
public class Cheque extends BaseEntity {

	private static final long serialVersionUID = -4867826353065911909L;

	@EmbeddedId
	private ChequeId id;
	
	@Enumerated(EnumType.STRING)
	private SituacaoCheque situacao; 
	
	@ManyToOne
	@JoinColumn(name="cod_conta", insertable=false, updatable=false)
	private Conta conta;

	@OneToOne
	@JoinColumn(nullable=true)
	private Lancamento lancamento;
	
	
	public Cheque() {
	}


	public ChequeId getId() {
		return id;
	}


	public void setId(ChequeId id) {
		this.id = id;
	}


	public SituacaoCheque getSituacao() {
		return situacao;
	}


	public void setSituacao(SituacaoCheque situacao) {
		this.situacao = situacao;
	}


	public Conta getConta() {
		return conta;
	}


	public void setConta(Conta conta) {
		this.conta = conta;
	}


	public Lancamento getLancamento() {
		return lancamento;
	}


	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}


	@Override
	public String toString() {
		return "Cheque [id=" + id + ", situacao=" + situacao + ", conta=" + conta + ", lancamento=" + lancamento + "]";
	}

}
