package help.home.com.br.homehelp.webservices.rest.dto;

public class UsuarioEspecialidadeDTO {

	private Long id;

	private Long valorCobrado;

	private EspecialidadeDTO especialidade;

	private UsuarioDTO usuario;

	@Override
	public String toString() {
		return usuario.getNome();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getValorCobrado() {
		return valorCobrado;
	}

	public void setValorCobrado(Long valorCobrado) {
		this.valorCobrado = valorCobrado;
	}

	public EspecialidadeDTO getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(EspecialidadeDTO especialidade) {
		this.especialidade = especialidade;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

}
