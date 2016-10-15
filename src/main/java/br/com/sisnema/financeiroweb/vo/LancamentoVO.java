package br.com.sisnema.financeiroweb.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import br.com.sisnema.financeiroweb.model.Cheque;
/*visual object*/
public class LancamentoVO implements Serializable {

	private static final long serialVersionUID = 8280638345589223463L;
	private Integer codigo;
	private Date data;
	private String descricao;
	private BigDecimal valor;
	private float saldoNaData;
	private int fatorCategoria;
	private Integer cheque;
	
	public LancamentoVO() {
		super();
	}

	public LancamentoVO(Integer codigo, Date data, String descricao, BigDecimal valor, float saldoNaData,
			int fatorCategoria, Integer cheque) {
		super();
		this.codigo = codigo;
		this.data = data;
		this.descricao = descricao;
		this.valor = valor;
		this.saldoNaData = saldoNaData;
		this.fatorCategoria = fatorCategoria;
		this.cheque = cheque;
	}

	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public float getSaldoNaData() {
		return saldoNaData;
	}
	public void setSaldoNaData(float saldoNaData) {
		this.saldoNaData = saldoNaData;
	}
	public int getFatorCategoria() {
		return fatorCategoria;
	}
	public void setFatorCategoria(int fatorCategoria) {
		this.fatorCategoria = fatorCategoria;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cheque == null) ? 0 : cheque.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + fatorCategoria;
		result = prime * result + Float.floatToIntBits(saldoNaData);
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof LancamentoVO))
			return false;
		LancamentoVO other = (LancamentoVO) obj;
		if (cheque == null) {
			if (other.cheque != null)
				return false;
		} else if (!cheque.equals(other.cheque))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (fatorCategoria != other.fatorCategoria)
			return false;
		if (Float.floatToIntBits(saldoNaData) != Float.floatToIntBits(other.saldoNaData))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "LancamentoVO [codigo=" + codigo + ", data=" + data + ", descricao=" + descricao + ", valor=" + valor
				+ ", saldoNaData=" + saldoNaData + ", fatorCategoria=" + fatorCategoria + ", cheque=" + cheque + "]";
	}

	public enum Fields {
		CODIGO("codigo"),
		DESCRICAO("descricao"),
		VALOR("valor"),
		SALDO_NA_DATA("saldoNaData"),
		FATOR_CATEGORIA("fatorCategoria"),
		DATA("data"),
		CHEQUE("cheque")
		;
		
		private String property;

		private Fields(String property) {
			this.property = property;
		}
		
		@Override
		public String toString() {
			return property;
		}
	}

	public Integer getCheque() {
		return cheque;
	}
	public void setCheque(Integer cheque) {
		this.cheque = cheque;
	}

}
