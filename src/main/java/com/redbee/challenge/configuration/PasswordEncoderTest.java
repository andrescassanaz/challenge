package com.redbee.challenge.configuration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
public static void main(String[] args) {
	BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
	System.out.println(pe.encode("pass"));
	
}
}
