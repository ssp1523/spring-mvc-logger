/*
 * Copyright 2013 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.springmvclogger;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 日志打印类
 */
public abstract class Logger {


    protected abstract boolean shouldLog();

    /**
     * 打印输出
     */
    protected abstract void log(String configKey, String format, Object... args);

    /**
     * 请求日志
     * @param configKey 配置key
     * @param request 请求信息
     */
    protected void logRequest(String configKey, Request request) {
        if (!shouldLog()) {
            return;
        }

        log(configKey, "---> %s %s HTTP/1.1", request.getMethod(), request.getUrl());
        //请求头信息
        headersLog(configKey, request.getHeaders());

        int bodyLength = 0;
        byte[] body = request.getBody();
        if (body != null && body.length > 0) {
            bodyLength = body.length;
            String bodyText =
                    request.getCharset() != null ? new String(body, request.getCharset()) : null;
            log(configKey, ""); // 回车
            log(configKey, "%s", bodyText != null ? bodyText : "Binary data");
        }
        log(configKey, "---> END HTTP (%s-byte body)", bodyLength);
    }

    private void headersLog(String configKey, HttpHeaders headers) {
        for (String field : headers.keySet()) {
            List<String> headerList = headers.get(field);
            if (CollectionUtils.isEmpty(headerList)) {
                continue;
            }
            for (String value : headerList) {
                log(configKey, "%s: %s", field, value);
            }
        }
    }

    /**
     * 响应日志打印
     * @param configKey 配置key
     * @param response 响应对象
     * @param elapsedTime 响应时间
     */
    protected void logResponse(String configKey, Response response, long elapsedTime) {
        if (!shouldLog()) {
            return;
        }

        HttpStatus status = response.getStatus();
        log(configKey, "<--- HTTP/1.1 %s (%sms)", status, elapsedTime);

        headersLog(configKey, response.getHeaders());

        int bodyLength = 0;
        if (response.getBody() != null &&
                !(status == HttpStatus.NO_CONTENT || status == HttpStatus.RESET_CONTENT)) {
            log(configKey, ""); // 回车
            log(configKey, "%s", new String(response.getBody(), response.getCharset()));
            log(configKey, "<--- END HTTP (%s-byte body)", bodyLength);
        } else {
            log(configKey, "<--- END HTTP (%s-byte body)", bodyLength);
        }
    }


}
