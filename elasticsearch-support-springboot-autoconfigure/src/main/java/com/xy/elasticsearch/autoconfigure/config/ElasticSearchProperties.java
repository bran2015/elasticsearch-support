package com.xy.elasticsearch.autoconfigure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author bo_z
 * @date 2019/9/19
 */
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticSearchProperties {

    private String hostname = "localhost";

    private Integer port = 9200;

    private String scheme = "http";

    private Integer maxRetryTimeoutMillis = 5 * 60 * 1000;

    private Integer collectionTimeout = 5 * 60 * 1000;

    private Integer socketTimeout = 5 * 60 * 1000;

    private Integer connectionRequestTimeout = 5 * 60 * 1000;

    private Secure secure = new Secure();

    public static class Secure {

        private String username;

        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public Secure getSecure() {
        return secure;
    }

    public void setSecure(Secure secure) {
        this.secure = secure;
    }

    public Integer getMaxRetryTimeoutMillis() {
        return maxRetryTimeoutMillis;
    }

    public void setMaxRetryTimeoutMillis(Integer maxRetryTimeoutMillis) {
        this.maxRetryTimeoutMillis = maxRetryTimeoutMillis;
    }

    public Integer getCollectionTimeout() {
        return collectionTimeout;
    }

    public void setCollectionTimeout(Integer collectionTimeout) {
        this.collectionTimeout = collectionTimeout;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public Integer getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }
}
