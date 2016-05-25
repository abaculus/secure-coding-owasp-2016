package se.panok.securecoding.a6;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class SomeService {

	public int tellMeSecret() {
		final int superSecret = 42; // stack
		final Integer secret = new Integer(24); // heap (pointer on stack -> pointing to heap)
		return new Random().nextBoolean() ? superSecret : secret;
	}
}
