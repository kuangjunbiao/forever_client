package com.gaoan.forever.tablemodel;

import java.util.List;

import com.gaoan.forever.model.SizeInfoModel;
import com.gaoan.forever.model.TableModel;

public class SizeTableModel extends TableModel<SizeInfoModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2784713523529477220L;

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
			return "颜色名";
		case 2:
			return "简称";
		case 3:
			return "创建时间";
		default:
			return "";
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		SizeInfoModel model = data.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return model.getId();
		case 1:
			return model.getSizeName();
		case 2:
			return model.getSizeCode();
		case 3:
			return model.getCreateDate();
		default:
			return "";
		}
	}

	public void setData(List<SizeInfoModel> data) {
		this.data = data;
	}

}