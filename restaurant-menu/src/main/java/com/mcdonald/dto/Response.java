package com.mcdonald.dto;

public class Response<T> {


	public static enum STATUS {
		SUCCESS, ERROR
	}

	private STATUS status;
	private String message;

	private T body;

	public Response(STATUS status, String message) {
		this.status = status;
		this.message = message;
	}

	public Response(STATUS status, String message, T body) {
		this(status, message);
		this.body = body;
	}

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	public static <T> Response<T> error(String message) {
		return new Response<T>(STATUS.ERROR, message);
	}

	public static <T> Response<T> error(String message, T obj) {
		return new Response<>(STATUS.ERROR, message, obj);
	}

	public static <T> Response<T> success(String message) {
		return new Response<>(STATUS.SUCCESS, message);
	}

	public static <T> Response<T> success(String message, T obj) {
		return new Response<>(STATUS.SUCCESS, message, obj);
	}

	@Override
	public String toString() {
		return "Response [status=" + status + ", message=" + message + ", body=" + body + "]";
	}

}
