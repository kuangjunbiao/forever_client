package com.gaoan.forever.tablemodel;

import java.util.List;

import com.gaoan.forever.constant.ForeverConstant;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.model.ResourceInfoModel;
import com.gaoan.forever.model.TableModel;

public class ResourceTableModel extends TableModel<ResourceInfoModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4597895409030660817L;

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		// 根据实际情况返回列数
		return 4;
	}

	@Override
	public String getColumnName(int column) {
		// 根据实际情况返回列名
		switch (column) {
		case 0:
			return "编号";
		case 1:
			return "资源名";
		case 2:
			return "类型";
		case 3:
			return "创建时间";
		default:
			return "";
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ResourceInfoModel model = data.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return model.getId();
		case 1:
			return model.getResourcesName();
		case 2:
			String typeDesc = MessageInfoConstant.RESOURCE_TYPE_OPERATOR;
			if (ForeverConstant.RESOURCE_TYPE_MENU.equals(String.valueOf(model.getType()))) {
				typeDesc = MessageInfoConstant.RESOURCE_TYPE_MENU;
			}
			return typeDesc;
		case 3:
			return model.getCreateDate();
		default:
			return "";
		}
	}

	public void setData(List<ResourceInfoModel> data) {
		this.data = data;
	}

}