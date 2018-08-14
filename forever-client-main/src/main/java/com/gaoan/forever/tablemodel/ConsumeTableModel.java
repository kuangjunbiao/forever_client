package com.gaoan.forever.tablemodel;

import java.math.BigDecimal;
import java.util.List;

import com.gaoan.forever.model.ConsumeInfoModel;
import com.gaoan.forever.model.TableModel;

public class ConsumeTableModel extends TableModel<ConsumeInfoModel> {

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
		return 5;
	}

	@Override
	public String getColumnName(int column) {
		// 根据实际情况返回列名
		switch (column) {
		case 0:
			return "编号";
		case 1:
			return "消费日期";
		case 2:
			return "消费金额";
		case 3:
			return "备注";
		case 4:
			return "创建时间";
		default:
			return "";
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ConsumeInfoModel model = data.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return model.getId();
		case 1:
			return model.getDateStr();
		case 2:
			return model.getAmount().setScale(2, BigDecimal.ROUND_UP);
		case 3:
			return model.getRemark();
		case 4:
			return model.getCreateDate();
		default:
			return "";
		}
	}

	public void setData(List<ConsumeInfoModel> data) {
		this.data = data;
	}

}