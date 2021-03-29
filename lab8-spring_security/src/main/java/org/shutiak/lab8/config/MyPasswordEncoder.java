package org.shutiak.lab8.config;

import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(final CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }
}
