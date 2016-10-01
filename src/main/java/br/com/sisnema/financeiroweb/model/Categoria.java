package br.com.sisnema.financeiroweb.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
@Entity
public class Categoria extends BaseEntity {

	private static final long serialVersionUID = 7667180542514454031L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer codigo;
	
	@ManyToOne
	@JoinColumn(name="categoria_pai",nullable=true)
	private Categoria pai;
	
	@ManyToOne
	@JoinColumn(name="usuario")
	private Usuario usuario;
	
	@Column(nullable=false)
	private String descricao;
	
	@Column(nullable=false)
	private int fator;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
	@JoinColumn(name="categoria_pai",insertable=false, updatable=false)    
	@OrderBy(value="descricao asc")
	private List<Categoria> filhos;

	public Categoria() {
		
	}

	public Categoria(Integer codigo) {
		super();
		this.codigo = codigo;
	}

	public Categoria(Categoria pai, Usuario usuario, String descricao, int fator) {
		super();
		this.pai = pai;
		this.usuario = usuario;
		this.descricao = descricao;
		this.fator = fator;
	}

	public Categoria(Usuario usuario) {
		super();
		this.usuario = usuario;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Categoria getPai() {
		return pai;
	}

	public void setPai(Categoria pai) {
		this.pai = pai;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getFator() {
		return fator;
	}

	public void setFator(int fator) {
		this.fator = fator;
	}

	public List<Categoria> getFilhos() {
		return filhos;
	}

	public void setFilhos(List<Categoria> filhos) {
		this.filhos = filhos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Categoria))
			return false;
		Categoria other = (Categoria) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Categoria [codigo=" + codigo + ", pai=" + pai + ", usuario=" + usuario + ", descricao=" + descricao
				+ ", fator=" + fator + "]";
	}
	
	

}
