package com.jvfast.common.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;

/**
 * 启动客户端发送请求压缩,如果发送的要求带： Content-Encoding: gzip则会进行压缩
 */
public class ContentEncodingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String conentEncoding = request.getHeader("Content-Encoding");
        if (conentEncoding != null && ("gzip".equalsIgnoreCase(conentEncoding) || "deflate".equalsIgnoreCase(conentEncoding))) {
            filterChain.doFilter(new GZIPRequestWrapper(request), response);
        }
        filterChain.doFilter(request, response);
    }
}

@Slf4j
class GZIPRequestWrapper extends HttpServletRequestWrapper {

    protected HttpServletRequest request;

    public GZIPRequestWrapper(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ServletInputStream sis = request.getInputStream();
        InputStream is = null;
        String conentEncoding = request.getHeader("Content-Encoding");
        if ("gzip".equalsIgnoreCase(conentEncoding)) {
            is = new GZIPInputStream(sis);
        } else if ("deflate".equalsIgnoreCase(conentEncoding)) {
            is = new DeflaterInputStream(sis);
        } else {
            throw new UnsupportedEncodingException(conentEncoding + " is not supported.");
        }
        final InputStream compressInputStream = is;
        return new ServletInputStream() {
            ReadListener readListener;

            @Override
            public int read() throws IOException {
                int b = compressInputStream.read();
                if (b == -1 && readListener != null) {
                    readListener.onAllDataRead();
                }
                return b;
            }

            @Override
            public boolean isFinished() {
                try {
                    return compressInputStream.available() == 0;
                } catch (IOException e) {
                    log.error("error", e);
                    if (readListener != null) {
                        readListener.onError(e);
                    }
                    return false;
                }
            }

            @Override
            public boolean isReady() {
                try {
                    return compressInputStream.available() > 0;
                } catch (IOException e) {
                    log.error("error", e);
                    if (readListener != null) {
                        readListener.onError(e);
                    }
                    return false;
                }
            }

            @Override
            public void setReadListener(final ReadListener readListener) {
                this.readListener = readListener;
                sis.setReadListener(new ReadListener() {
                    @Override
                    public void onDataAvailable() throws IOException {
                        log.trace("onDataAvailable");
                        if (readListener != null) {
                            readListener.onDataAvailable();
                        }
                    }

                    @Override
                    public void onAllDataRead() throws IOException {
                        log.trace("onAllDataRead");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        log.error("onError", throwable);
                        if (readListener != null) {
                            readListener.onError(throwable);
                        }
                    }
                });
            }
        };
    }
}
