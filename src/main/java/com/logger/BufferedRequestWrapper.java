package com.logger;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.Arrays;

/**
 * 请求体缓存
 */
public class BufferedRequestWrapper extends HttpServletRequestWrapper {

    private byte[] bodyBuffer;

    public BufferedRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        InputStream in = request.getInputStream();
        int length = request.getContentLength();
        if (length < 1) {
            bodyBuffer = new byte[0];
            return;
        }
        bodyBuffer = new byte[length];
        //noinspection ResultOfMethodCallIgnored
        in.read(bodyBuffer);

    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bodyBuffer);
        return new ServletInputStream() {

            public int read() {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public byte[] toByteArray() {
        return bodyBuffer;
    }

}
