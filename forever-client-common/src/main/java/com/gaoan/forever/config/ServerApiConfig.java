package com.gaoan.forever.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "serverApiConfig")
public class ServerApiConfig {

	private String ip;

	private String port;

	private String protocol;

	private String apiLogin;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getHost() {
		return getProtocol() + "://" + getIp() + ":" + getPort();
	}

	public String getApiLogin() {
		return getHost() + apiLogin;
	}

	public void setApiLogin(String apiLogin) {
		this.apiLogin = apiLogin;
	}

}
