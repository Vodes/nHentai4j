package pw.vodes.nhentai.connection;

public class Response {

	private String message;
	private int code;

	public Response(String message, int code) {
		this.message = message;
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
