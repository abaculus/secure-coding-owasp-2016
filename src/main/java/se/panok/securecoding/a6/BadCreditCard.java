package se.panok.securecoding.a6;

/*
 * Strings are immutable. That means once you've created the string, if another
 * process can dump memory, there's no way (aside from reflection) you can get
 * rid of the data before garbage collection kicks in (which you cannot control).
 */
public class BadCreditCard {

	private String cardNumber;

	private String cvv;
}
