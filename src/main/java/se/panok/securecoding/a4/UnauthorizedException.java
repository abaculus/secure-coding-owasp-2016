package se.panok.securecoding.a4;

public class UnauthorizedException extends RuntimeException {
	
	private static final long serialVersionUID = -7847289841906289949L;

	public UnauthorizedException() {
		super("Unauthorized action occured!");
	}
}
