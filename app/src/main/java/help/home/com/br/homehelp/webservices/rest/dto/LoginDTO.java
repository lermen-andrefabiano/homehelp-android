package help.home.com.br.homehelp.webservices.rest.dto;

public class LoginDTO {

	private Long id;

	private String nome;

	private String login;

	private String email;

	private String senha;

	private String confirmaSenha;

	private Boolean prestaServico;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public Boolean getPrestaServico() {
		return prestaServico;
	}

	public void setPrestaServico(Boolean prestaServico) {
		this.prestaServico = prestaServico;
	}

}
