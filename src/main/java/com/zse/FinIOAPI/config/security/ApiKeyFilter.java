package com.zse.FinIOAPI.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import java.io.IOException;

@Component // ✅ Agora o Spring gerencia essa classe automaticamente
public class ApiKeyFilter extends GenericFilterBean {

    private final String publicApiKey;

    // ✅ O Spring injetará automaticamente o Environment
    public ApiKeyFilter(Environment env) {
        this.publicApiKey = env.getProperty("api.public-key");
    }
/*
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestUri = httpRequest.getRequestURI();

        System.out.println("🚀 Interceptando requisição para: " + requestUri); // ✅ Log para depuração

        // Aplica validação apenas no endpoint público específico
        if (requestUri.startsWith("/api/posmei/empresa/cadastro")) {
            String apiKey = httpRequest.getHeader("X-API-KEY");

            System.out.println("🔑 API Key recebida: " + apiKey); // ✅ Verificando se a chave está sendo enviada

            if (apiKey == null || !apiKey.equals(publicApiKey)) {
                System.out.println("❌ API Key inválida! Bloqueando acesso.");
                throw new ServletException("Acesso negado: API KEY inválida!");
            }
        }

        chain.doFilter(request, response);
    }
*/
    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestUri = httpRequest.getRequestURI();

      

        // Aplica validação apenas no endpoint específico
        if (requestUri.contains("/api/posmei/empresa/cadastro")) {
            String apiKey = httpRequest.getHeader("X-API-KEY");

            System.out.println("🔑 API Key recebida: " + apiKey);

            if (apiKey == null || !apiKey.equals(publicApiKey)) {
                System.out.println("❌ API Key inválida! Bloqueando acesso.");
                throw new ServletException("Acesso negado: API KEY inválida!");
            }
        }

        // ✅ Agora apenas requisições sem API Key serão bloqueadas
        chain.doFilter(request, response);
    }


}

