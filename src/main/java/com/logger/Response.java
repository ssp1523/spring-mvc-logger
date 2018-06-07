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
package com.logger;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.io.Closeable;
import java.nio.charset.Charset;

/**
 * 响应信息
 */
@Builder(toBuilder = true)
@Data
public final class Response {

    /**
     * 状态码
     */
    private final HttpStatus status;

    /**
     * 原因
     */
    private final String reason;

    /**
     * 响应头信息
     */
    private final HttpHeaders headers;

    /**
     * 响应体
     */
    private final byte[] body;

    /**
     * 请求对象
     */
    private final Charset charset;


}
