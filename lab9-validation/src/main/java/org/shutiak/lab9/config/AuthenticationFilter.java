package org.shutiak.lab9.config;

import lombok.RequiredArgsConstructor;
import org.shutiak.lab9.service.JwtTokenGenerator;
import org.shutiak.lab9.utils.TokenUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SignatureException;

@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenGenerator jwtTokenGenerator;

    @Override
    protected void doFilterInternal(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, final FilterChain filterChain)
            throws ServletException, IOException {
        String token = TokenUtils.getTokenFromRequest(httpServletRequest);

        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        try {
            final String username = jwtTokenGenerator.getUsernameFromToken(token);
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }catch (RuntimeException e){
            httpServletResponse.setStatus(500);
            httpServletResponse.sendError(500, "wrong Authorization token, please clear cookies and relogin");
            return;
        }

    }

    private static String getRequestPath(final HttpServletRequest request) {
        return request.getRequestURI() + "?" + request.getQueryString();
    }
}

