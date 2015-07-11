package help.home.com.br.homehelp.webservices.rest.dto;

public class ClassificacaoDTO {

	private Long chamadoId;

	private String descricao;

	private String especialidade;

	private String prestador;

	public Long getChamadoId() {
		return chamadoId;
	}
	
	public void setChamadoId(Long chamadoId) {
		this.chamadoId = chamadoId;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	public String getPrestador() {
		return prestador;
	}

	public void setPrestador(String prestador) {
		this.prestador = prestador;
	}

}
