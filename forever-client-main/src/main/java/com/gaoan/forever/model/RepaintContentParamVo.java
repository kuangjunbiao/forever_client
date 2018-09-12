package com.gaoan.forever.model;

import java.io.Serializable;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import com.gaoan.forever.combobox.KeyValComboBox;

/**
 * 重新加载数据的参数VO
 * 
 * @author Administrator
 *
 */
public class RepaintContentParamVo<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8474933862389943580L;

	private JFrame window;
	private JTextField idText;
	private JComboBox<String> purchaseOptions;
	private JComboBox<String> goodsOptions;
	private KeyValComboBox<KeyValBoxVo> roleNameComboBox;
	private KeyValComboBox<KeyValBoxVo> statusComboBox;
	private KeyValComboBox<KeyValBoxVo> typeComboBox;
	private KeyValComboBox<KeyValBoxVo> payTypeComboBox;
	private KeyValComboBox<KeyValBoxVo> sizeComboBox;
	private KeyValComboBox<KeyValBoxVo> colorComboBox;
	private KeyValComboBox<KeyValBoxVo> statisticsComboBox;
	private KeyValComboBox<KeyValBoxVo> userComboBox;
	private JTextField purchaseNameText;
	private JTextField goodsNameText;
	private JTextField userNameText;
	private JTextField roleNameText;
	private JTextField realNameText;
	private JTextField resourceNameText;
	private JTextField qtyText;
	private JTextField tagPriceText;
	private JTextField priceText;
	private JLabel discountLabel;
	private JTextField colorNameText;
	private JTextField sizeNameText;
	private JTextField sizeCodeText;
	private JTextField consumeAmountText;
	private JTextArea remarkArea;
	private JXDatePicker startDatePicker;
	private JXDatePicker endDatePicker;
	private JXDatePicker statisticsDatePicker;
	private JXDatePicker consumeDatePicker;

	private JButton confirmButton;
	private JButton cancelButton;
	private JButton searchBtn;
	private JButton insertBtn;
	private JButton insertHaveBtn;
	private JButton insertUnHaveBtn;
	private JButton updateBtn;
	private JButton deleteBtn;
	private JButton resetBtn;
	private JButton exportBtn;
	private JButton exportSalesBtn;
	private JButton exportConsumeBtn;

	private JTable table;
	private TableModel<T> tableModel;

	private Box mainBox;

	private List<Long> checkedResourceIdList;

	public JFrame getWindow() {
		return window;
	}

	public void setWindow(JFrame window) {
		this.window = window;
	}

	public JComboBox<String> getPurchaseOptions() {
		return purchaseOptions;
	}

	public void setPurchaseOptions(JComboBox<String> purchaseOptions) {
		this.purchaseOptions = purchaseOptions;
	}

	public JComboBox<String> getGoodsOptions() {
		return goodsOptions;
	}

	public void setGoodsOptions(JComboBox<String> goodsOptions) {
		this.goodsOptions = goodsOptions;
	}

	public JXDatePicker getStartDatePicker() {
		return startDatePicker;
	}

	public void setStartDatePicker(JXDatePicker startDatePicker) {
		this.startDatePicker = startDatePicker;
	}

	public JXDatePicker getEndDatePicker() {
		return endDatePicker;
	}

	public void setEndDatePicker(JXDatePicker endDatePicker) {
		this.endDatePicker = endDatePicker;
	}

	public Box getMainBox() {
		return mainBox;
	}

	public void setMainBox(Box mainBox) {
		this.mainBox = mainBox;
	}

	public JTextField getIdText() {
		return idText;
	}

	public void setIdText(JTextField idText) {
		this.idText = idText;
	}

	public JTextField getQtyText() {
		return qtyText;
	}

	public void setQtyText(JTextField qtyText) {
		this.qtyText = qtyText;
	}

	public JTextField getPriceText() {
		return priceText;
	}

	public void setPriceText(JTextField priceText) {
		this.priceText = priceText;
	}

	public JButton getConfirmButton() {
		return confirmButton;
	}

	public void setConfirmButton(JButton confirmButton) {
		this.confirmButton = confirmButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public JButton getSearchBtn() {
		return searchBtn;
	}

	public void setSearchBtn(JButton searchBtn) {
		this.searchBtn = searchBtn;
	}

	public JButton getInsertHaveBtn() {
		return insertHaveBtn;
	}

	public void setInsertHaveBtn(JButton insertHaveBtn) {
		this.insertHaveBtn = insertHaveBtn;
	}

	public JButton getInsertUnHaveBtn() {
		return insertUnHaveBtn;
	}

	public void setInsertUnHaveBtn(JButton insertUnHaveBtn) {
		this.insertUnHaveBtn = insertUnHaveBtn;
	}

	public JButton getUpdateBtn() {
		return updateBtn;
	}

	public void setUpdateBtn(JButton updateBtn) {
		this.updateBtn = updateBtn;
	}

	public JButton getDeleteBtn() {
		return deleteBtn;
	}

	public void setDeleteBtn(JButton deleteBtn) {
		this.deleteBtn = deleteBtn;
	}

	public JTable getTable() {
		return table;
	}

	public JButton getInsertBtn() {
		return insertBtn;
	}

	public void setInsertBtn(JButton insertBtn) {
		this.insertBtn = insertBtn;
	}

	public TableModel<T> getTableModel() {
		return tableModel;
	}

	public void setTableModel(TableModel<T> tableModel) {
		this.tableModel = tableModel;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JTextField getPurchaseNameText() {
		return purchaseNameText;
	}

	public void setPurchaseNameText(JTextField purchaseNameText) {
		this.purchaseNameText = purchaseNameText;
	}

	public JTextField getGoodsNameText() {
		return goodsNameText;
	}

	public void setGoodsNameText(JTextField goodsNameText) {
		this.goodsNameText = goodsNameText;
	}

	public JButton getResetBtn() {
		return resetBtn;
	}

	public void setResetBtn(JButton resetBtn) {
		this.resetBtn = resetBtn;
	}

	public KeyValComboBox<KeyValBoxVo> getRoleNameComboBox() {
		return roleNameComboBox;
	}

	public void setRoleNameComboBox(KeyValComboBox<KeyValBoxVo> roleNameComboBox) {
		this.roleNameComboBox = roleNameComboBox;
	}

	public KeyValComboBox<KeyValBoxVo> getStatusComboBox() {
		return statusComboBox;
	}

	public void setStatusComboBox(KeyValComboBox<KeyValBoxVo> statusComboBox) {
		this.statusComboBox = statusComboBox;
	}

	public JTextField getUserNameText() {
		return userNameText;
	}

	public void setUserNameText(JTextField userNameText) {
		this.userNameText = userNameText;
	}

	public JTextField getRealNameText() {
		return realNameText;
	}

	public void setRealNameText(JTextField realNameText) {
		this.realNameText = realNameText;
	}

	public JTextField getRoleNameText() {
		return roleNameText;
	}

	public void setRoleNameText(JTextField roleNameText) {
		this.roleNameText = roleNameText;
	}

	public List<Long> getCheckedResourceIdList() {
		return checkedResourceIdList;
	}

	public void setCheckedResourceIdList(List<Long> checkedResourceIdList) {
		this.checkedResourceIdList = checkedResourceIdList;
	}

	public KeyValComboBox<KeyValBoxVo> getTypeComboBox() {
		return typeComboBox;
	}

	public void setTypeComboBox(KeyValComboBox<KeyValBoxVo> typeComboBox) {
		this.typeComboBox = typeComboBox;
	}

	public JTextField getResourceNameText() {
		return resourceNameText;
	}

	public void setResourceNameText(JTextField resourceNameText) {
		this.resourceNameText = resourceNameText;
	}

	public KeyValComboBox<KeyValBoxVo> getPayTypeComboBox() {
		return payTypeComboBox;
	}

	public void setPayTypeComboBox(KeyValComboBox<KeyValBoxVo> payTypeComboBox) {
		this.payTypeComboBox = payTypeComboBox;
	}

	public JTextArea getRemarkArea() {
		return remarkArea;
	}

	public void setRemarkArea(JTextArea remarkArea) {
		this.remarkArea = remarkArea;
	}

	public JTextField getTagPriceText() {
		return tagPriceText;
	}

	public void setTagPriceText(JTextField tagPriceText) {
		this.tagPriceText = tagPriceText;
	}

	public JTextField getColorNameText() {
		return colorNameText;
	}

	public void setColorNameText(JTextField colorNameText) {
		this.colorNameText = colorNameText;
	}

	public JTextField getSizeNameText() {
		return sizeNameText;
	}

	public void setSizeNameText(JTextField sizeNameText) {
		this.sizeNameText = sizeNameText;
	}

	public JTextField getSizeCodeText() {
		return sizeCodeText;
	}

	public void setSizeCodeText(JTextField sizeCodeText) {
		this.sizeCodeText = sizeCodeText;
	}

	public KeyValComboBox<KeyValBoxVo> getSizeComboBox() {
		return sizeComboBox;
	}

	public void setSizeComboBox(KeyValComboBox<KeyValBoxVo> sizeComboBox) {
		this.sizeComboBox = sizeComboBox;
	}

	public KeyValComboBox<KeyValBoxVo> getColorComboBox() {
		return colorComboBox;
	}

	public void setColorComboBox(KeyValComboBox<KeyValBoxVo> colorComboBox) {
		this.colorComboBox = colorComboBox;
	}

	public KeyValComboBox<KeyValBoxVo> getStatisticsComboBox() {
		return statisticsComboBox;
	}

	public void setStatisticsComboBox(KeyValComboBox<KeyValBoxVo> statisticsComboBox) {
		this.statisticsComboBox = statisticsComboBox;
	}

	public JXDatePicker getStatisticsDatePicker() {
		return statisticsDatePicker;
	}

	public void setStatisticsDatePicker(JXDatePicker statisticsDatePicker) {
		this.statisticsDatePicker = statisticsDatePicker;
	}

	public JButton getExportBtn() {
		return exportBtn;
	}

	public void setExportBtn(JButton exportBtn) {
		this.exportBtn = exportBtn;
	}

	public KeyValComboBox<KeyValBoxVo> getUserComboBox() {
		return userComboBox;
	}

	public void setUserComboBox(KeyValComboBox<KeyValBoxVo> userComboBox) {
		this.userComboBox = userComboBox;
	}

	public JTextField getConsumeAmountText() {
		return consumeAmountText;
	}

	public void setConsumeAmountText(JTextField consumeAmountText) {
		this.consumeAmountText = consumeAmountText;
	}

	public JXDatePicker getConsumeDatePicker() {
		return consumeDatePicker;
	}

	public void setConsumeDatePicker(JXDatePicker consumeDatePicker) {
		this.consumeDatePicker = consumeDatePicker;
	}

	public JButton getExportSalesBtn() {
		return exportSalesBtn;
	}

	public void setExportSalesBtn(JButton exportSalesBtn) {
		this.exportSalesBtn = exportSalesBtn;
	}

	public JButton getExportConsumeBtn() {
		return exportConsumeBtn;
	}

	public void setExportConsumeBtn(JButton exportConsumeBtn) {
		this.exportConsumeBtn = exportConsumeBtn;
	}

	public JLabel getDiscountLabel() {
		return discountLabel;
	}

	public void setDiscountLabel(JLabel discountLabel) {
		this.discountLabel = discountLabel;
	}

}
