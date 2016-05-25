package se.panok.securecoding.a6;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

public class GoodcreditCard implements Cloneable, Serializable {

	private char[] cardNumber;
	private char[] cvv;

	public void wipe() {
		if (cardNumber != null) {
			Arrays.fill(cardNumber, 'x');
		}
		;
		cardNumber = null;
		if (cvv != null) {
			Arrays.fill(cvv, 'x');
		}
		cvv = null;
	}

	@Override
	protected void finalize() throws Throwable {
		wipe();
		super.finalize();
	}

	@Override
	protected final Object clone() throws CloneNotSupportedException {
		throw new java.lang.CloneNotSupportedException();
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		throw new IOException("Class cannot be deserialized");
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		throw new IOException("Class cannot be deserialized");
	}
}
