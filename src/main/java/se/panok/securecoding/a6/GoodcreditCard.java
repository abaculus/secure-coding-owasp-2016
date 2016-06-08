package se.panok.securecoding.a6;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

/*
 * With an array, you can explicitly wipe the data after you're done with it.
 * You can overwrite the array with anything you like, and the password won't be
 * present anywhere in the system, even before garbage collection.
 * 
 * But(!) even using char[] only reduces the window of opportunity for an attacker.
 */
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

	/*private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		throw new IOException("Class cannot be deserialized");
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		throw new IOException("Class cannot be deserialized");
	}*/
}
