package com.gaoan.forever.tablemodel;

import java.math.BigDecimal;
import java.util.List;

import com.gaoan.forever.model.SalesOrderInfoModel;
import com.gaoan.forever.model.TableModel;

public class SalesOrderTableModel extends TableModel<SalesOrderInfoModel> {

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
		return 12;
	}

	@Override
	public String getColumnName(int column) {
		// 根据实际情况返回列名
		switch (column) {
		case 0:
			return "销售日期";
		case 1:
			return "进货单名称";
		case 2:
			return "货品名称";
		case 3:
			return "颜色";
		case 4:
			return "尺寸";
		case 5:
			return "吊牌价";
		case 6:
			return "成交价";
		case 7:
			return "数量";
		case 8:
			return "成交总价";
		case 9:
			return "支付方式";
		case 10:
			return "备注";
		case 11:
			return "创建人";
		default:
			return "";
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		SalesOrderInfoModel model = data.get(rowIndex);
		int payType = 0;
		String payTypeDesc = "";
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
			return model.getTagPrice().setScale(2, BigDecimal.ROUND_UP);
		case 6:
			return model.getSellPrice().setScale(2, BigDecimal.ROUND_UP);
		case 7:
			return model.getQuantity();
		case 8:
			return model.getTotal().setScale(2, BigDecimal.ROUND_UP);
		case 9:
			if (model.getPayType() == null) {
				return "";
			}
			payType = model.getPayType().intValue();
			if (payType == 1) {
				payTypeDesc = "微信";
			} else if (payType == 2) {
				payTypeDesc = "支付宝";
			} else {
				payTypeDesc = "现金";
			}
			return payTypeDesc;
		case 10:
			return model.getRemark();
		case 11:

			return model.getRealName();
		default:
			return "";
		}
	}

	public void setData(List<SalesOrderInfoModel> data) {
		this.data = data;
	}

}