package net.codejava;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
	public static void main(String args[]) {
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		String rawPassword="4321";
		String encodedPassword=encoder.encode(rawPassword);
		
		System.out.println(encodedPassword);
	}

}
