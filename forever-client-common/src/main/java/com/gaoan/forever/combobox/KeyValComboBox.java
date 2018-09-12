package com.gaoan.forever.combobox;

import java.awt.Component;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.gaoan.forever.model.KeyValBoxVo;

public class KeyValComboBox<E> extends JComboBox<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8421695878044293574L;

	public KeyValComboBox(Vector<E> values) {
		super(values);
		rendererData(); // 渲染数据
	}

	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	public void rendererData() {
		ListCellRenderer render = new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof KeyValBoxVo) {
					KeyValBoxVo po = (KeyValBoxVo) value;
					this.setText(po.getValue());
				}
				return this;
			}
		};
		this.setRenderer(render);
	}

	/**
	 * 修改Combox中的数据
	 * 
	 * @param values
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateData(Vector<E> values) {
		setModel(new DefaultComboBoxModel(values));
		rendererData();
	}

	/**
	 * 选中value与obj相同的选项
	 */
	@Override
	public void setSelectedItem(Object obj) {
		if (obj != null) {
			if (obj instanceof KeyValBoxVo) {
				super.setSelectedItem(obj);
			}
			if (obj instanceof String || obj instanceof Integer || obj instanceof Long) {
				for (int index = 0; index < getItemCount(); index++) {
					KeyValBoxVo po = (KeyValBoxVo) getItemAt(index);
					if (obj.toString().equals(po.getValue())) {
						super.setSelectedIndex(index);
					}
				}
			}
		} else {
			super.setSelectedItem(obj);
		}
	}

	/**
	 * 选中key与obj相同的选项
	 * 
	 * @param obj
	 */
	public void setSelectedKey(Object obj) {
		if (obj != null) {
			if (obj instanceof KeyValBoxVo) {
				super.setSelectedItem(obj);
			}
			if (obj instanceof String || obj instanceof Integer || obj instanceof Long) {
				for (int index = 0; index < getItemCount(); index++) {
					KeyValBoxVo po = (KeyValBoxVo) getItemAt(index);
					if (po.getKey().equals(obj.toString())) {
						super.setSelectedIndex(index);
					}
				}
			}
		} else {
			super.setSelectedItem(obj);
		}
	}

	// 获得选中项的键值
	public String getSelectedKey() {
		Object obj = getSelectedItem();
		if (obj instanceof KeyValBoxVo) {
			KeyValBoxVo po = (KeyValBoxVo) obj;
			return po.getKey();
		}
		return (obj != null) ? obj.toString() : null;
	}

	// 获得选中项的显示文本
	public String getSelectedValue() {
		Object obj = getSelectedItem();
		if (obj instanceof KeyValBoxVo) {
			KeyValBoxVo po = (KeyValBoxVo) obj;
			return po.getValue();
		}
		return (obj != null) ? obj.toString() : null;
	}
}
