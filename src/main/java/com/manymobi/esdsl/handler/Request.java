package com.manymobi.esdsl.handler;

import com.manymobi.esdsl.annotations.RequestMethod;

/**
 * @author 梁建军
 * 创建日期： 2021/4/20
 * 创建时间： 上午9:58
 * @version 1.0
 * @since 1.0
 */
public class Request {

    private final RequestMethod requestMethod;
    private final String url;
    private final String json;

    public Request(RequestMethod requestMethod, String url, String json) {
        this.requestMethod = requestMethod;
        this.url = url;
        this.json = json;
    }

    public Request(Build build) {
        this.requestMethod = build.requestMethod;
        this.url = build.url;
        this.json = build.json;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public String getUrl() {
        return url;
    }

    public String getJson() {
        return json;
    }

    public Build newBuild() {
        return new Build()
                .setRequestMethod(requestMethod)
                .setUrl(url)
                .setJson(json);
    }

    public static class Build {
        private RequestMethod requestMethod;
        private String url;
        private String json;

        public Build setRequestMethod(RequestMethod requestMethod) {
            this.requestMethod = requestMethod;
            return this;
        }

        public Build setUrl(String url) {
            this.url = url;
            return this;
        }

        public Build setJson(String json) {
            this.json = json;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}
