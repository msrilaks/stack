package com.stack.taskservice.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.stream.Stream;

@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            RequestResponseLoggingFilter.class.getName());

    private static ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper) {
            return (ContentCachingRequestWrapper) request;
        } else {
            return new ContentCachingRequestWrapper(request);
        }
    }

    private static ContentCachingResponseWrapper wrapResponse(
            HttpServletResponse response) {
        if (response instanceof ContentCachingResponseWrapper) {
            return (ContentCachingResponseWrapper) response;
        } else {
            return new ContentCachingResponseWrapper(response);
        }
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        doFilterWrapped(wrapRequest(request), wrapResponse(response), filterChain);
    }

    protected void doFilterWrapped(
            ContentCachingRequestWrapper request, ContentCachingResponseWrapper response,
            FilterChain filterChain) throws ServletException, IOException {
        beforeRequest(request, response);
        try {
            filterChain.doFilter(request, response);
        } finally {
            afterRequest(request, response);
            response.copyBodyToResponse();
        }
    }

    protected void beforeRequest(
            ContentCachingRequestWrapper request,
            ContentCachingResponseWrapper response) {
        if (logger.isInfoEnabled()) {
            StringBuffer msg = new StringBuffer();
            msg.append("Address:").append(request.getRemoteAddr()).append(", Method:")
               .append(request.getMethod()).append(",URI:").append(
                    request.getRequestURI());
            String queryString = request.getQueryString();
            msg.append(", Headers:{");
            Collections.list(request.getHeaderNames())
                       .forEach(headerName ->
                                        Collections.list(request.getHeaders(headerName))
                                                   .forEach(headerValue -> msg.append(
                                                           headerName).append(":")
                                                                              .append(headerValue)
                                                                              .append(",")));

            msg.append("}");
            msg.append(", Body:");
            byte[] content = request.getContentAsByteArray();
            if (content.length > 0) {
                try {
                    String contentString = new String(content,
                                                      request.getCharacterEncoding());
                    Stream.of(contentString.split("\r\n|\r|\n"))
                          .forEach(line -> msg.append(line));
                } catch (UnsupportedEncodingException e) {
                }
            }
            logger.info(msg.toString());
        }
    }

    protected void afterRequest(
            ContentCachingRequestWrapper request,
            ContentCachingResponseWrapper response) {
        StringBuffer msg = new StringBuffer();
        if (logger.isInfoEnabled()) {
            msg.append("address:").append(request.getRemoteAddr()).append(", response:")
               .append(response.getStatus());
            msg.append(", Headers:{");
            response.getHeaderNames().forEach(headerName ->
                                                      response.getHeaders(headerName)
                                                              .forEach(headerValue ->
                                                                               msg.append(
                                                                                       headerName)
                                                                                  .append(":")
                                                                                  .append(headerValue)));
            msg.append("}");
            byte[] content = response.getContentAsByteArray();
            if (content.length > 0) {
                msg.append(", Body:");
                try {
                    String contentString = new String(content,
                                                      request.getCharacterEncoding());
                    Stream.of(contentString.split("\r\n|\r|\n")).forEach(
                            line -> msg.append(line));
                } catch (UnsupportedEncodingException e) {

                }
            }
            logger.info(msg.toString());
        }
    }
}


