package zup.common.dto;

/**
 * Data Transfer Object utilizado para retorno de erros.
 */
public class ResponseErrorDto {

    private final int status;

    private final String mensagem;

    public ResponseErrorDto(int status, String mensagem) {
		this.status = status;
		this.mensagem = mensagem;
	}

	public int getStatus() {
		return status;
	}

	public String getMensagem() {
		return mensagem;
	}

}
