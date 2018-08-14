package com.gaoan.forever;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import com.gaoan.forever.config.ServerApiConfig;
import com.gaoan.forever.window.LoginWindow;

@EnableAutoConfiguration
@SpringBootApplication
@ServletComponentScan
public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static ServerApiConfig serverApiConfig;

    @Autowired
    public static void setServerApiConfig(ServerApiConfig serverApiConfig) {
	App.serverApiConfig = serverApiConfig;
    }

    public static void main(String[] args) {
	logger.info("展示登录窗口.");
	// SpringApplication.run(App.class, args);
	LoginWindow.show();
	// Object obj = CallUtils.post(null,
	// "http://127.0.0.1:8066/api/sales/getSalesOrderList?page=1&pageSize=10",
	// "{'purchaseOrderName':'123'}", MessageEnum.SEARCH_MSG);

	// Object obj = CallUtils.post(null,
	// "http://127.0.0.1:8066/api/usersale/login",
	// JSON.toJSONString(new JSONObject()), MessageEnum.LOGIN_MSG);
    }

}
