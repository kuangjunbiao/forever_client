package com.gaoan.forever.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.gaoan.forever.utils.security.RSAUtils;

@Component
@ConfigurationProperties(prefix = "serverApiConfig")
public class ServerApiConfig {

	// prod
	private static String ip = "Cj5xaPa7p6t7elVMWuLNYXGKoI6cSUURwBwjbP5j/75SvC7g2QsA4146NOA0K2cRnJoZDakeHKOTzme64va0JyfbDmTjbjzkxRfQArCjgrbur7H3jimA3HJOvjyTO6MMizTrltq2oIr3NyYnBFahzJvzHavsXqSCf+wvPaal+5IB8ucYsal4uJQSIGPwFN0RTMJH4ufv45sdLmErs7VNbceaDeips39KYOP3l4+75ruBl6OIHXkGIGrYqsW6jAYwlglL03i9tEM/plbJ47qw55HS8DfrQpPS/MgOkcl3DWPg6imlaUW0DFOMbttTsAaFytPlcea9axUinisrpz9YKw==";
	private static String port = "m3Gb2S4DHhGKc64e/jtDIhYCoqS9BAMLaFHwkJt/GjAtzxkYGXCE/pVcWEYktOFSeh/tkfnZaidbVoFMhxIhjJdJuljjoGWFGVs8PkmXUWCgDaYaI/AZ11j8zmjQNapA53fzN8CU1gEiHCUnEvnSJOyU59tIqY1xe4gV+wEjU2YI9tYhS8fytvH9WP2Gyphdy8iXEXAfsCnr5bgd4eLnMNyBO/VkIlOpcxCA6BCAoa7+uvYKIEMSOZU5szEtivSZYJ6rArKgk2O7aGqiWkmj+yPoRUHvAVuoNiPRe28LHn2j2wk23YGpsnqxENI12Q8Yjw1YOqh8/8GWJqRZqo7XGw==";
	// private static String port =
	// "AgHgSn97PHWMA3qxv9lZQmYLGJllfpTsaHDUavmbvEzlr/yQxI9iWpBfv+5rYbKG4XB7DbTxsIb2Dy6qiVdJpo3hiSEeBZgBbobV6vuE+ZsUsc7GbyNaVHniUpBjcfDOLQZ62D976No+sX/L3AZUUvXxcMOgooF8THDy420WC1/Gxh5gW50fnEpk55Kasm//xlSuzGKeNQcs41lbogdQuI9coW6644TnUD6aB/5NwMP/C1pI2VgCBtqjiKEJLQkLuN0CRJctlE+TbxwchX04SqA/+4FayXSgyW9GV554TPkPUA0PRlzy0Ayhgn1aeT2yiiXzyxL1maivdAfiXaOlLg==";

	// local
	// private static String ip =
	// "SxwUBiygr15kGY1pNR/JxzcGo6oCMnG1qpPa8mJPDwcYkTvRuhgYGBml7++esFK0iFDsWQbqmJCUCYSa6gLsmmlYqLr4tEmUA8kBxQzAkZTroPtQDsFv4V3g+TFU9EVedSs0jrKcaK0abxHKTcmrWPK/KZu3ekYRFQXBTRaE3aUQqkliZZs8wAOR5RZBVQRW9Z8YBqaj3E17uiop77vWKyJnjLuoyhWXrfCtpLecRhFOlksrhE7HipyWoUfsFMUW+yp45B8B1XUaDZfbSNmCxu7Of0+tJcfDyKGq3ph9ALre12qA+tn/XUGxIkmFnx9pZiomNqmSYaz5OZxavEbjmQ==";
	// private static String port =
	// "YqxK5G3j0rAe3zyxtGHE2jP6gaPEKVKLOT7PK/F2QKZCH0w9lYx/gH1kJXYhLz/A3KkSi6VIQrVOrfJyYMjaR90GhXt/L7qhnaXlFk4kglK0wOgLpfT558UveTLJw0SfJxTnw1TuJDFD4Fe9l5ywz5XK+lQqcxzba/9CZBuYTSZNyjy/242Eile6ednuREMWImiZS4Zx+3qH8W1c2/QHDsq8fsak3Q3bxGtW4ZETpypeqz3tJteb3RqXHkgSCoBuHj90c2kHnwU/pRMwLOWSlRl+PlZlep9OgTEVKYjaho/5Jzlfrn53RCMuGrazVwU9O0hh6AZeHM9JgR9DvCfPGw==";

	private static String protocol = "http://";

	// *******************************进货列表**********************************
	// 新增已有的进货订单
	private static String apiInsertPurchaseOrderByHave = "/api/purchase/insertPurchaseOrderByHave";
	// 新增未有的进货订单
	private static String apiInsertPurchaseOrderByUnHave = "/api/purchase/insertPurchaseOrderByUnHave";
	// 删除进货订单
	private static String apiDelPurchaseOrder = "/api/purchase/delPurchaseOrder";
	// 修改进货订单信息
	private static String apiUpdatePurchaseOrder = "/api/purchase/updatePurchaseOrder";
	// 获取进货订单列表
	private static String apiGetPurchaseOrderList = "/api/purchase/getPurchaseOrderList";
	// 获取订货订单详情
	private static String apiGetPurchaseOrderDetail = "/api/purchase/getPurchaseOrderDetail";

	// *******************************出货列表**********************************
	// 新增出货订单
	private static String apiInsertSalesOrder = "/api/sales/insertSalesOrder";
	// 删除出货订单记录
	private static String apiDelSalesOrder = "/api/sales/delSalesOrder";
	// 修改出货订单
	private static String apiUpdateSalesOrder = "/api/sales/updateSalesOrder";
	// 获取出货订单列表
	private static String apiGetSalesOrderList = "/api/sales/getSalesOrderList";
	// 获取出货订单详情
	private static String apiGetSalesOrderDetail = "/api/sales/getSalesOrderDetail";

	// *******************************库存列表**********************************
	// 获取库存列表
	private static String apiGetStockList = "/api/stock/getStockList";
	// 获取库存中的进货单名称
	private static String apiGetPurchaseNameList = "/api/stock/getPurchaseNameList";
	// 获取商品名称
	private static String apiGetGoodsList = "/api/stock/getGoodsList";
	// 获取商品颜色名称
	private static String apiGetGoodsColorList = "/api/stock/getGoodsColorList";
	// 获取商品尺寸名称
	private static String apiGetGoodsSizeList = "/api/stock/getGoodsSizeList";

	// *******************************用户列表**********************************
	// 用户注册
	private static String apiInsertUser = "/api/user/insertUser";
	// 获取用户信息
	private static String apiDelUserInfo = "/api/user/delUserInfo";
	// 修改用户信息
	private static String apiUpdateUserInfo = "/api/user/updateUserInfo";
	// 重置密码
	private static String apiResetPass = "/api/user/resetPass";
	// 获取用户列表
	private static String apiGetUserPageInfo = "/api/user/getUserPageInfo";
	// 获取用户列表
	private static String apiGetUserList = "/api/user/getUserList";
	// 获取用户详情
	private static String apiGetUserDetail = "/api/user/getUserDetail";
	// 登陆
	private static String apiLogin = "/api/usersale/login";
	// 退出
	private static String apiLoginout = "/api/usersale/loginout";
	// 忘记密码
	private static String apiForgetPass = "/api/user/forgetPass";

	// *******************************角色列表**********************************
	// 新增角色
	private static String apiInsertRole = "/api/role/insertRole";
	// 删除角色信息
	private static String apiDelRole = "/api/role/delRole";
	// 修改角色
	private static String apiUpdateRole = "/api/role/updateRole";
	// 获取角色分页信息
	private static String apiGetRolePage = "/api/role/getRolePage";
	// 获取角色列表
	private static String apiGetRoleList = "/api/role/getRoleList";
	// 获取角色详情
	private static String apiGetRoleDetail = "/api/role/getRoleDetail";

	// *******************************资源列表**********************************
	// 新增资源
	private static String apiInsertResource = "/api/resources/insertResourcesInfo";
	// 刪除資源信息
	private static String apiDelResource = "/api/resources/delResources";
	// 修改资源
	private static String apiUpdateResources = "/api/resources/updateResources";
	// 获取资源分页信息
	private static String apiGetResourcePage = "/api/resources/getResourcePage";
	// 获取资源列表
	private static String apiGetResourceList = "/api/resources/getResourceList";
	// 获取资源详情
	private static String apiGetResourceDetail = "/api/resources/getResourceDetail";

	// *******************************颜色列表**********************************
	// 新增颜色
	private static String apiInsertColor = "/api/color/insertColorInfo";
	// 删除颜色
	private static String apiDeleteColor = "/api/color/delColor";
	// 获取颜色分页信息
	private static String apiGetColorPage = "/api/color/getColorPage";
	// 获取颜色列表
	private static String apiGetColorList = "/api/color/getColorList";

	// *******************************尺寸列表**********************************
	// 新增颜色
	private static String apiInsertSize = "/api/size/insertSizeInfo";
	// 删除颜色
	private static String apiDeleteSize = "/api/size/delSize";
	// 获取颜色分页信息
	private static String apiGetSizePage = "/api/size/getSizePage";
	// 获取颜色列表
	private static String apiGetSizeList = "/api/size/getSizeList";

	// *******************************统计列表**********************************
	// 统计列表
	private static String apiGetStatisticsInfo = "/api/statistics/getStatisticsInfo";
	// 导出销售明细
	private static String apiExportSalesInfo = "/api/statistics/exportSalesList";
	// 导出支出明细
	private static String apiExportConsumeInfo = "/api/statistics/exportConsumeList";

	// *******************************消费列表**********************************
	// 消费列表
	private static String apiGetConsumePageInfo = "/api/consume/getConsumePageInfo";
	// 获取消费详情
	private static String apiGetConsumeDetail = "/api/consume/getConsumeDetail";
	// 新增消费信息
	private static String apiInsertConsumeInfo = "/api/consume/insertConsumeInfo";
	// 修改消费信息
	private static String apiUpdateConsumeInfo = "/api/consume/updateConsumeInfo";
	// 删除消费信息
	private static String apiDeleteConsumeInfo = "/api/consume/deleteConsumeInfo";

	public static String getIp() {
		return ip;
	}

	public static void setIp(String ip) {
		ServerApiConfig.ip = ip;
	}

	public static String getPort() {
		return port;
	}

	public static void setPort(String port) {
		ServerApiConfig.port = port;
	}

	public static String getProtocol() {
		return protocol;
	}

	public static void setProtocol(String protocol) {
		ServerApiConfig.protocol = protocol;
	}

	public static String getApiInsertPurchaseOrderByHave() {
		return getUrl(apiInsertPurchaseOrderByHave);
	}

	public static void setApiInsertPurchaseOrderByHave(String apiInsertPurchaseOrderByHave) {
		ServerApiConfig.apiInsertPurchaseOrderByHave = apiInsertPurchaseOrderByHave;
	}

	public static String getApiInsertPurchaseOrderByUnHave() {
		return getUrl(apiInsertPurchaseOrderByUnHave);
	}

	public static void setApiInsertPurchaseOrderByUnHave(String apiInsertPurchaseOrderByUnHave) {
		ServerApiConfig.apiInsertPurchaseOrderByUnHave = apiInsertPurchaseOrderByUnHave;
	}

	public static String getApiDelPurchaseOrder() {
		return getUrl(apiDelPurchaseOrder);
	}

	public static void setApiDelPurchaseOrder(String apiDelPurchaseOrder) {
		ServerApiConfig.apiDelPurchaseOrder = apiDelPurchaseOrder;
	}

	public static String getApiUpdatePurchaseOrder() {
		return getUrl(apiUpdatePurchaseOrder);
	}

	public static void setApiUpdatePurchaseOrder(String apiUpdatePurchaseOrder) {
		ServerApiConfig.apiUpdatePurchaseOrder = apiUpdatePurchaseOrder;
	}

	public static String getApiGetPurchaseOrderList() {
		return getUrl(apiGetPurchaseOrderList);
	}

	public static void setApiGetPurchaseOrderList(String apiGetPurchaseOrderList) {
		ServerApiConfig.apiGetPurchaseOrderList = apiGetPurchaseOrderList;
	}

	public static String getApiGetPurchaseOrderDetail() {
		return getUrl(apiGetPurchaseOrderDetail);
	}

	public static void setApiGetPurchaseOrderDetail(String apiGetPurchaseOrderDetail) {
		ServerApiConfig.apiGetPurchaseOrderDetail = apiGetPurchaseOrderDetail;
	}

	public static String getApiInsertSalesOrder() {
		return getUrl(apiInsertSalesOrder);
	}

	public static void setApiInsertSalesOrder(String apiInsertSalesOrder) {
		ServerApiConfig.apiInsertSalesOrder = apiInsertSalesOrder;
	}

	public static String getApiDelSalesOrder() {
		return getUrl(apiDelSalesOrder);
	}

	public static void setApiDelSalesOrder(String apiDelSalesOrder) {
		ServerApiConfig.apiDelSalesOrder = apiDelSalesOrder;
	}

	public static String getApiUpdateSalesOrder() {
		return getUrl(apiUpdateSalesOrder);
	}

	public static void setApiUpdateSalesOrder(String apiUpdateSalesOrder) {
		ServerApiConfig.apiUpdateSalesOrder = apiUpdateSalesOrder;
	}

	public static String getApiGetSalesOrderList() {
		return getUrl(apiGetSalesOrderList);
	}

	public static void setApiGetSalesOrderList(String apiGetSalesOrderList) {
		ServerApiConfig.apiGetSalesOrderList = apiGetSalesOrderList;
	}

	public static String getApiGetSalesOrderDetail() {
		return getUrl(apiGetSalesOrderDetail);
	}

	public static void setApiGetSalesOrderDetail(String apiGetSalesOrderDetail) {
		ServerApiConfig.apiGetSalesOrderDetail = apiGetSalesOrderDetail;
	}

	public static String getApiGetStockList() {
		return getUrl(apiGetStockList);
	}

	public static void setApiGetStockList(String apiGetStockList) {
		ServerApiConfig.apiGetStockList = apiGetStockList;
	}

	public static String getApiGetPurchaseNameList() {
		return getUrl(apiGetPurchaseNameList);
	}

	public static void setApiGetPurchaseNameList(String apiGetPurchaseNameList) {
		ServerApiConfig.apiGetPurchaseNameList = apiGetPurchaseNameList;
	}

	public static String getApiGetGoodsList() {
		return getUrl(apiGetGoodsList);
	}

	public static void setApiGetGoodsList(String apiGetGoodsList) {
		ServerApiConfig.apiGetGoodsList = apiGetGoodsList;
	}

	public static String getApiGetGoodsColorList() {
		return getUrl(apiGetGoodsColorList);
	}

	public static void setApiGetGoodsColorList(String apiGetGoodsColorList) {
		ServerApiConfig.apiGetGoodsColorList = apiGetGoodsColorList;
	}

	public static String getApiGetGoodsSizeList() {
		return getUrl(apiGetGoodsSizeList);
	}

	public static void setApiGetGoodsSizeList(String apiGetGoodsSizeList) {
		ServerApiConfig.apiGetGoodsSizeList = apiGetGoodsSizeList;
	}

	public static String getApiInsertUser() {
		return getUrl(apiInsertUser);
	}

	public static void setApiInsertUser(String apiInsertUser) {
		ServerApiConfig.apiInsertUser = apiInsertUser;
	}

	public static String getApiDelUserInfo() {
		return getUrl(apiDelUserInfo);
	}

	public static void setApiDelUserInfo(String apiDelUserInfo) {
		ServerApiConfig.apiDelUserInfo = apiDelUserInfo;
	}

	public static String getApiResetPass() {
		return getUrl(apiResetPass);
	}

	public static void setApiResetPass(String apiResetPass) {
		ServerApiConfig.apiResetPass = apiResetPass;
	}

	public static String getApiGetUserPageInfo() {
		return getUrl(apiGetUserPageInfo);
	}

	public static void setApiGetUserPageInfo(String apiGetUserPageInfo) {
		ServerApiConfig.apiGetUserPageInfo = apiGetUserPageInfo;
	}

	public static String getApiGetUserList() {
		return getUrl(apiGetUserList);
	}

	public static void setApiGetUserList(String apiGetUserList) {
		ServerApiConfig.apiGetUserList = apiGetUserList;
	}

	public static String getApiGetUserDetail() {
		return getUrl(apiGetUserDetail);
	}

	public static void setApiGetUserDetail(String apiGetUserDetail) {
		ServerApiConfig.apiGetUserDetail = apiGetUserDetail;
	}

	public static String getApiLogin() {
		return getUrl(apiLogin);
	}

	public static void setApiLogin(String apiLogin) {
		ServerApiConfig.apiLogin = apiLogin;
	}

	public static String getApiLoginout() {
		return getUrl(apiLoginout);
	}

	public static void setApiLoginout(String apiLoginout) {
		ServerApiConfig.apiLoginout = apiLoginout;
	}

	public static String getApiForgetPass() {
		return getUrl(apiForgetPass);
	}

	public static void setApiForgetPass(String apiForgetPass) {
		ServerApiConfig.apiForgetPass = apiForgetPass;
	}

	public static String getApiInsertRole() {
		return getUrl(apiInsertRole);
	}

	public static void setApiInsertRole(String apiInsertRole) {
		ServerApiConfig.apiInsertRole = apiInsertRole;
	}

	public static String getApiDelRole() {
		return getUrl(apiDelRole);
	}

	public static void setApiDelRole(String apiDelRole) {
		ServerApiConfig.apiDelRole = apiDelRole;
	}

	public static String getApiUpdateRole() {
		return getUrl(apiUpdateRole);
	}

	public static void setApiUpdateRole(String apiUpdateRole) {
		ServerApiConfig.apiUpdateRole = apiUpdateRole;
	}

	public static String getApiGetRolePage() {
		return getUrl(apiGetRolePage);
	}

	public static void setApiGetRolePage(String apiGetRolePage) {
		ServerApiConfig.apiGetRolePage = apiGetRolePage;
	}

	public static String getApiGetRoleList() {
		return getUrl(apiGetRoleList);
	}

	public static void setApiGetRoleList(String apiGetRoleList) {
		ServerApiConfig.apiGetRoleList = apiGetRoleList;
	}

	public static String getApiGetRoleDetail() {
		return getUrl(apiGetRoleDetail);
	}

	public static void setApiGetRoleDetail(String apiGetRoleDetail) {
		ServerApiConfig.apiGetRoleDetail = apiGetRoleDetail;
	}

	public static String getApiInsertResource() {
		return getUrl(apiInsertResource);
	}

	public static void setApiInsertResource(String apiInsertResource) {
		ServerApiConfig.apiInsertResource = apiInsertResource;
	}

	public static String getApiDelResource() {
		return getUrl(apiDelResource);
	}

	public static void setApiDelResource(String apiDelResource) {
		ServerApiConfig.apiDelResource = apiDelResource;
	}

	public static String getApiUpdateResources() {
		return getUrl(apiUpdateResources);
	}

	public static void setApiUpdateResources(String apiUpdateResources) {
		ServerApiConfig.apiUpdateResources = apiUpdateResources;
	}

	public static String getApiGetResourcePage() {
		return getUrl(apiGetResourcePage);
	}

	public static void setApiGetResourcePage(String apiGetResourcePage) {
		ServerApiConfig.apiGetResourcePage = apiGetResourcePage;
	}

	public static String getApiGetResourceList() {
		return getUrl(apiGetResourceList);
	}

	public static void setApiGetResourceList(String apiGetResourceList) {
		ServerApiConfig.apiGetResourceList = apiGetResourceList;
	}

	public static String getApiGetResourceDetail() {
		return getUrl(apiGetResourceDetail);
	}

	public static void setApiGetResourceDetail(String apiGetResourceDetail) {
		ServerApiConfig.apiGetResourceDetail = apiGetResourceDetail;
	}

	public static String getApiUpdateUserInfo() {
		return getUrl(apiUpdateUserInfo);
	}

	public static void setApiUpdateUserInfo(String apiUpdateUserInfo) {
		ServerApiConfig.apiUpdateUserInfo = apiUpdateUserInfo;
	}

	public static String getApiGetColorPage() {
		return getUrl(apiGetColorPage);
	}

	public static void setApiGetColorPage(String apiGetColorPage) {
		ServerApiConfig.apiGetColorPage = apiGetColorPage;
	}

	public static String getApiGetColorList() {
		return getUrl(apiGetColorList);
	}

	public static void setApiGetColorList(String apiGetColorList) {
		ServerApiConfig.apiGetColorList = apiGetColorList;
	}

	public static String getApiInsertColor() {
		return getUrl(apiInsertColor);
	}

	public static void setApiInsertColor(String apiInsertColor) {
		ServerApiConfig.apiInsertColor = apiInsertColor;
	}

	public static String getApiDeleteColor() {
		return getUrl(apiDeleteColor);
	}

	public static void setApiDeleteColor(String apiDeleteColor) {
		ServerApiConfig.apiDeleteColor = apiDeleteColor;
	}

	public static String getApiInsertSize() {
		return getUrl(apiInsertSize);
	}

	public static void setApiInsertSize(String apiInsertSize) {
		ServerApiConfig.apiInsertSize = apiInsertSize;
	}

	public static String getApiDeleteSize() {
		return getUrl(apiDeleteSize);
	}

	public static void setApiDeleteSize(String apiDeleteSize) {
		ServerApiConfig.apiDeleteSize = apiDeleteSize;
	}

	public static String getApiGetSizePage() {
		return getUrl(apiGetSizePage);
	}

	public static void setApiGetSizePage(String apiGetSizePage) {
		ServerApiConfig.apiGetSizePage = apiGetSizePage;
	}

	public static String getApiGetSizeList() {
		return getUrl(apiGetSizeList);
	}

	public static void setApiGetSizeList(String apiGetSizeList) {
		ServerApiConfig.apiGetSizeList = apiGetSizeList;
	}

	public static String getApiGetStatisticsInfo() {
		return getUrl(apiGetStatisticsInfo);
	}

	public static void setApiGetStatisticsInfo(String apiGetStatisticsInfo) {
		ServerApiConfig.apiGetStatisticsInfo = apiGetStatisticsInfo;
	}

	public static String getApiExportSalesInfo() {
		return getUrl(apiExportSalesInfo);
	}

	public static void setApiExportSalesInfo(String apiExportSalesInfo) {
		ServerApiConfig.apiExportSalesInfo = apiExportSalesInfo;
	}

	public static String getApiGetConsumePageInfo() {
		return getUrl(apiGetConsumePageInfo);
	}

	public static void setApiGetConsumePageInfo(String apiGetConsumePageInfo) {
		ServerApiConfig.apiGetConsumePageInfo = apiGetConsumePageInfo;
	}

	public static String getApiGetConsumeDetail() {
		return getUrl(apiGetConsumeDetail);
	}

	public static void setApiGetConsumeDetail(String apiGetConsumeDetail) {
		ServerApiConfig.apiGetConsumeDetail = apiGetConsumeDetail;
	}

	public static String getApiInsertConsumeInfo() {
		return getUrl(apiInsertConsumeInfo);
	}

	public static void setApiInsertConsumeInfo(String apiInsertConsumeInfo) {
		ServerApiConfig.apiInsertConsumeInfo = apiInsertConsumeInfo;
	}

	public static String getApiUpdateConsumeInfo() {
		return getUrl(apiUpdateConsumeInfo);
	}

	public static void setApiUpdateConsumeInfo(String apiUpdateConsumeInfo) {
		ServerApiConfig.apiUpdateConsumeInfo = apiUpdateConsumeInfo;
	}

	public static String getApiDeleteConsumeInfo() {
		return getUrl(apiDeleteConsumeInfo);
	}

	public static void setApiDeleteConsumeInfo(String apiDeleteConsumeInfo) {
		ServerApiConfig.apiDeleteConsumeInfo = apiDeleteConsumeInfo;
	}

	public static String getApiExportConsumeInfo() {
		return getUrl(apiExportConsumeInfo);
	}

	public static void setApiExportConsumeInfo(String apiExportConsumeInfo) {
		ServerApiConfig.apiExportConsumeInfo = apiExportConsumeInfo;
	}

	public static String getUrl(String pathUrl) {
		StringBuffer sb = new StringBuffer();
		sb.append(protocol).append(RSAUtils.getData("ip", ip)).append(":").append(RSAUtils.getData("port", port))
				.append(pathUrl);
		return sb.toString();
	}

}
