package br.com.sisnema.financeiroweb.exception;

public class LockException extends DAOException {

	private static final long serialVersionUID = -2292142001645773865L;

	public LockException() {
	}

	public LockException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public LockException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public LockException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public LockException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
