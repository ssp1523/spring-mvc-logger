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
import org.springframework.http.HttpMethod;

import java.nio.charset.Charset;


/**
 * An immutable request to an http server.
 */
@Builder
@Data
public final class Request {

    /**
     * 请求方法
     */
    private final HttpMethod method;

    /**
     * 请求url
     */
    private final String url;

    /**
     * 请求头
     */
    private final HttpHeaders headers;

    /**
     * 请求体
     */
    private final byte[] body;

    /**
     * 编码
     */
    private final Charset charset;


}
