package com.gaoan.forever.tablemodel;

import java.util.List;

import com.gaoan.forever.model.StockInfoModel;
import com.gaoan.forever.model.TableModel;

public class StockTableModel extends TableModel<StockInfoModel> {

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
		return 6;
	}

	@Override
	public String getColumnName(int column) {
		// 根据实际情况返回列名
		switch (column) {
		case 0:
			return "进货日期";
		case 1:
			return "进货单名称";
		case 2:
			return "货品名称";
		case 3:
			return "颜色";
		case 4:
			return "尺寸";
		case 5:
			return "数量";
		default:
			return "";
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		StockInfoModel model = data.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return model.getCreateDate();
		case 1:
			return model.getPurchaseOrderName();
		case 2:
			return model.getGoodsName();
		case 3:
			return model.getColorName();
		case 4:
			return model.getSizeName();
		case 5:
			return model.getQuantity();
		default:
			return "";
		}
	}

	public void setData(List<StockInfoModel> data) {
		this.data = data;
	}

}