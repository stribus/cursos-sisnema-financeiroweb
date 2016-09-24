package br.com.sisnema.financeiroweb.domain;

public enum UsuarioPermissao {
	

	ROLE_USUARIO("Permiss�o default de um usu�rio"),
	ROLE_ADMINISTRADOR("Permiss�o de administra��o"),
	ROLE_VIP("Permiss�o de usu�rios vips");
	
	private String descricao;

	private UsuarioPermissao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return descricao;
	}

}
