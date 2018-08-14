package com.gaoan.forever.tablemodel;

import java.math.BigDecimal;
import java.util.List;

import com.gaoan.forever.model.PurchaseOrderInfoModel;
import com.gaoan.forever.model.TableModel;

public class PurchaseOrderTableModel extends TableModel<PurchaseOrderInfoModel> {

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
		return 9;
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
			return "进货价格";
		case 6:
			return "数量";
		case 7:
			return "总价";
		case 8:
			return "创建人";
		default:
			return "";
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		PurchaseOrderInfoModel model = data.get(rowIndex);
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
			return model.getCostPrice().setScale(2, BigDecimal.ROUND_UP);
		case 6:
			return model.getQuantity();
		case 7:
			return model.getTotal().setScale(2, BigDecimal.ROUND_UP);
		case 8:
			return model.getRealName();
		default:
			return "";
		}
	}

	public void setData(List<PurchaseOrderInfoModel> data) {
		this.data = data;
	}
}