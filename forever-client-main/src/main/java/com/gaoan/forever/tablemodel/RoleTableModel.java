package com.gaoan.forever.tablemodel;

import java.util.List;

import com.gaoan.forever.model.RoleInfoModel;
import com.gaoan.forever.model.TableModel;

public class RoleTableModel extends TableModel<RoleInfoModel> {

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
			return "角色名";
		case 2:
			return "创建时间";
		case 3:
			return "修改时间";
		default:
			return "";
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		RoleInfoModel model = data.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return model.getId();
		case 1:
			return model.getRoleName();
		case 2:
			return model.getCreateDate();
		case 3:
			return model.getUpdateDate();
		default:
			return "";
		}
	}

	public void setData(List<RoleInfoModel> data) {
		this.data = data;
	}

}