package com.gaoan.forever.tablemodel;

import java.math.BigDecimal;
import java.util.List;

import com.gaoan.forever.model.StatisticsInfoModel;
import com.gaoan.forever.model.TableModel;

public class StatisticsInfoTableModel extends TableModel<StatisticsInfoModel> {

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
		return 7;
	}

	@Override
	public String getColumnName(int column) {
		// 根据实际情况返回列名
		switch (column) {
		case 0:
			return "时间";
		case 1:
			return "销售总数";
		case 2:
			return "销售总额";
		case 3:
			return "成本总额";
		case 4:
			return "销售利润总额";
		case 5:
			return "支出总额";
		case 6:
			return "利润总额";
		default:
			return "";
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		StatisticsInfoModel model = data.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return model.getDate();
		case 1:
			return model.getTotal();
		case 2:
			return model.getSellPriceTotal() == null ? "" : model.getSellPriceTotal().setScale(2, BigDecimal.ROUND_UP);
		case 3:
			return model.getCostPriceTotal() == null ? "" : model.getCostPriceTotal().setScale(2, BigDecimal.ROUND_UP);
		case 4:
			return model.getSalesProfitTotal() == null ? ""
					: model.getSalesProfitTotal().setScale(2, BigDecimal.ROUND_UP);
		case 5:
			return model.getConsumeTotal() == null ? "" : model.getConsumeTotal().setScale(2, BigDecimal.ROUND_UP);
		case 6:
			return model.getProfitTotal() == null ? "" : model.getProfitTotal().setScale(2, BigDecimal.ROUND_UP);
		default:
			return "";
		}
	}

	public void setData(List<StatisticsInfoModel> data) {
		this.data = data;
	}

}