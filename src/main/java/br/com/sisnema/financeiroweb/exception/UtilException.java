package br.com.sisnema.financeiroweb.exception;

public class UtilException extends Exception {

	private static final long serialVersionUID = -5950682013770666484L;

	public UtilException() {
		super();
	}

	public UtilException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public UtilException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public UtilException(String arg0) {
		super(arg0);
	}

	public UtilException(Throwable arg0) {
		super(arg0);
	}

	
	
}
