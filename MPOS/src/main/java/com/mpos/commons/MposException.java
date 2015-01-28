package com.mpos.commons;

public class MposException extends RuntimeException {
	private static final long serialVersionUID = 2741768848973601742L;
	
	protected String errID;

	protected String msg = "";

	protected String str = "";

	public MposException() {
		super();
	}
	
	public MposException(Throwable ex) {
		super(ex);
		this.msg = ex.getMessage();
		this.str = "com.mpos.MposException:"
				+ "\n    nested exception:" + ex.toString();
	}

	public MposException(String ID, Throwable ex) {
		super(ex);
		this.errID = ID;
		this.msg = ex.getMessage();
		this.str = "com.mpos.MposException:" + ex.getMessage()
				+ "\n    nested exception:" + ex.toString();
	}

	public MposException(String ID, String message, Throwable ex) {
		super(ex);
		this.errID = ID;
		this.msg = message + " nested exception:" + ex.getMessage();
		this.str = "com.mpos.MposException:" + message
				+ "\n    nested exception:" + ex.toString();
	}

	public MposException(int ID, String message, Throwable ex) {
		super(ex);
		this.errID = String.valueOf(ID);
		this.msg = message + " nested exception:" + ex.getMessage();
		this.str = "com.mpos.MposException:" + message
				+ "\n    nested exception:" + ex.toString();
	}

	public MposException(String ID, String message) {
		this.errID = ID;
		this.msg = message;
		this.str = "com.mpos.MposException:" + message;
	}

	public MposException(int ID, String message) {
		this.errID = String.valueOf(ID);
		this.msg = message;
		this.str = "com.mpos.MposException:" + message;
	}

	public MposException(String ID) {
		this.errID = ID;
	}

	public MposException(int ID) {
		this.errID = String.valueOf(ID);
	}

	public String getErrorID() {
		return this.errID;
	}

	public String getMessage() {
		return this.msg;
	}
	
	public String toString() {
		return this.str;
	}

}
