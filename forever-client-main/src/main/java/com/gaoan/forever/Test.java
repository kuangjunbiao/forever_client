package com.gaoan.forever;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class Test {

    public static void main(String[] args) {
	JFrame jf = new JFrame("用户登录");
	jf.setSize(800, 500);
	jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	// 第 1 个 JPanel, 使用默认的浮动布局
	JPanel panel01 = new JPanel();
	panel01.add(new JLabel("用户名"));
	panel01.add(new JTextField(10));

	// 第 2 个 JPanel, 使用默认的浮动布局
	JPanel panel02 = new JPanel();
	panel02.add(new JLabel("密   码"));
	panel02.add(new JPasswordField(10));

	// 第 3 个 JPanel, 使用浮动布局, 并且容器内组件居中显示
	JPanel panel03 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	panel03.add(new JButton("登录"));
	panel03.add(new JButton("注册"));

	// 创建一个垂直盒子容器, 把上面 3 个 JPanel 串起来作为内容面板添加到窗口
	Box vBox = Box.createVerticalBox();
	vBox.add(panel01);
	vBox.add(panel02);
	vBox.add(panel03);

	jf.setContentPane(vBox);

	jf.pack();
	jf.setLocationRelativeTo(null);
	jf.setVisible(true);
    }

    /**
     * 列表展示
     * 
     * @param args
     */
    public static void main8(String[] args) {
	JFrame jf = new JFrame("Forever2进销存管理系统");
	jf.setSize(800, 500);
	jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	jf.setLocationRelativeTo(null);

	// 创建选项卡面板
	JTabbedPane tabbedPane = new JTabbedPane();

	// 创建第 1 个选项卡（选项卡只包含 标题）
	tabbedPane.addTab("入库列表", createListPane());

	// 创建第 2 个选项卡（选项卡包含 标题 和 图标）
	tabbedPane.addTab("出货管理", new ImageIcon("bb.jpg"), createListPane());

	// 创建第 3 个选项卡（选项卡包含 标题、图标 和 tip提示）
	tabbedPane.addTab("库存管理", new ImageIcon("bb.jpg"), createTextPanel("TAB 03"), "This is a tab.");

	tabbedPane.addTab("系统管理", new ImageIcon("bb.jpg"), createTextPanel("TAB 03"), "This is a tab.");

	// 添加选项卡选中状态改变的监听器
	tabbedPane.addChangeListener(new ChangeListener() {
	    public void stateChanged(ChangeEvent e) {
		System.out.println("当前选中的选项卡: " + tabbedPane.getSelectedIndex());
	    }
	});

	// 设置默认选中的选项卡
	tabbedPane.setSelectedIndex(0);

	jf.setContentPane(tabbedPane);
	jf.setVisible(true);

    }

    private static JComponent createListPane() {
	// 表头（列名）
	String[] columnNames = { "进货编号", "进货单名称", "物品名称", "进货价格", "数量", "总价", "创建人" };

	// 表格所有行数据
	Object[][] rowData = { { 1, "春一", "花裤子", 80, 1, 80, "徐颖" }, { 2, "春一", "花裤子", 80, 1, 80, "徐颖" },
		{ 3, "春一", "花裤子", 80, 1, 80, "徐颖" }, { 4, "春一", "花裤子", 80, 1, 80, "徐颖" },
		{ 5, "春一", "花裤子", 80, 1, 80, "徐颖" }, { 6, "春一", "花裤子", 80, 1, 80, "徐颖" },
		{ 7, "春一", "花裤子", 80, 1, 80, "徐颖" }, { 8, "春一", "花裤子", 80, 1, 80, "徐颖" },
		{ 9, "春一", "花裤子", 80, 1, 80, "徐颖" }, { 10, "春二", "花衣服", 80, 1, 80, "徐颖" },
		{ 11, "春二", "花衣服", 80, 1, 80, "徐颖" }, { 12, "春二", "花衣服", 80, 1, 80, "徐颖" },
		{ 13, "春二", "花衣服", 80, 1, 80, "徐颖" }, { 14, "春二", "花衣服", 80, 1, 80, "徐颖" },
		{ 15, "春二", "花衣服", 80, 1, 80, "徐颖" }, { 16, "春二", "花衣服", 80, 1, 80, "徐颖" },
		{ 17, "春二", "花衣服", 80, 1, 80, "徐颖" }, { 18, "春二", "花衣服", 80, 1, 80, "徐颖" },
		{ 19, "春二", "花衣服", 80, 1, 80, "徐颖" }, { 20, "春二", "花衣服", 80, 1, 80, "徐颖" } };

	// 创建一个表格，指定 表头 和 所有行数据
	JTable table = new JTable(rowData, columnNames);

	// 设置表格内容颜色
	table.setForeground(Color.BLACK); // 字体颜色
	table.setFont(new Font(null, Font.PLAIN, 14)); // 字体样式
	table.setSelectionForeground(Color.DARK_GRAY); // 选中后字体颜色
	table.setSelectionBackground(Color.LIGHT_GRAY); // 选中后字体背景
	table.setGridColor(Color.GRAY); // 网格颜色

	// 设置表头
	table.getTableHeader().setFont(new Font(null, Font.BOLD, 14)); // 设置表头名称字体样式
	table.getTableHeader().setForeground(Color.RED); // 设置表头名称字体颜色
	table.getTableHeader().setResizingAllowed(false); // 设置不允许手动改变列宽
	table.getTableHeader().setReorderingAllowed(false); // 设置不允许拖动重新排序各列

	// 设置行高
	table.setRowHeight(30);

	// 第一列列宽设置为40
	table.getColumnModel().getColumn(0).setPreferredWidth(40);

	// 设置滚动面板视口大小（超过该大小的行数据，需要拖动滚动条才能看到）
	table.setPreferredScrollableViewportSize(new Dimension(400, 300));

	// 把 表格 放到 滚动面板 中（表头将自动添加到滚动面板顶部）
	JScrollPane scrollPane = new JScrollPane(table);

	return scrollPane;
    }

    /**
     * 树形结构
     * 
     * @param args
     */
    public static void main6(String[] args) {
	JFrame jf = new JFrame("测试窗口");
	jf.setSize(300, 300);
	jf.setLocationRelativeTo(null);
	jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	JPanel panel = new JPanel(new BorderLayout());

	// 创建根节点
	DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("中国");

	// 创建二级节点
	DefaultMutableTreeNode gdNode = new DefaultMutableTreeNode("广东");
	DefaultMutableTreeNode fjNode = new DefaultMutableTreeNode("福建");
	DefaultMutableTreeNode shNode = new DefaultMutableTreeNode("上海");
	DefaultMutableTreeNode twNode = new DefaultMutableTreeNode("台湾");

	// 把二级节点作为子节点添加到根节点
	rootNode.add(gdNode);
	rootNode.add(fjNode);
	rootNode.add(shNode);
	rootNode.add(twNode);

	// 创建三级节点
	DefaultMutableTreeNode gzNode = new DefaultMutableTreeNode("广州");
	DefaultMutableTreeNode szNode = new DefaultMutableTreeNode("深圳");

	DefaultMutableTreeNode fzNode = new DefaultMutableTreeNode("福州");
	DefaultMutableTreeNode xmNode = new DefaultMutableTreeNode("厦门");

	DefaultMutableTreeNode tbNode = new DefaultMutableTreeNode("台北");
	DefaultMutableTreeNode gxNode = new DefaultMutableTreeNode("高雄");
	DefaultMutableTreeNode jlNode = new DefaultMutableTreeNode("基隆");

	// 把三级节点作为子节点添加到相应的二级节点
	gdNode.add(gzNode);
	gdNode.add(szNode);

	fjNode.add(fzNode);
	fjNode.add(xmNode);

	twNode.add(tbNode);
	twNode.add(gxNode);
	twNode.add(jlNode);

	// 使用根节点创建树组件
	JTree tree = new JTree(rootNode);

	// 设置树显示根节点句柄
	tree.setShowsRootHandles(true);

	// 设置树节点可编辑
	tree.setEditable(true);

	// 设置节点选中监听器
	tree.addTreeSelectionListener(new TreeSelectionListener() {
	    public void valueChanged(TreeSelectionEvent e) {
		System.out.println("当前被选中的节点: " + e.getPath());
	    }
	});

	// 创建滚动面板，包裹树（因为树节点展开后可能需要很大的空间来显示，所以需要用一个滚动面板来包裹）
	JScrollPane scrollPane = new JScrollPane(tree);

	// 添加滚动面板到那内容面板
	panel.add(scrollPane, BorderLayout.CENTER);

	// 设置窗口内容面板并显示
	jf.setContentPane(panel);
	jf.setVisible(true);
    }

    /**
     * 带滚动条的列表
     * 
     * @param args
     */
    public static void main5(String[] args) {
	JFrame jf = new JFrame("测试窗口");
	jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	// 创建内容面板
	// JPanel panel = new JPanel();

	// 表头（列名）
	String[] columnNames = { "序号", "姓名", "语文", "数学", "英语", "总分" };

	// 表格所有行数据
	Object[][] rowData = { { 1, "张三", 80, 80, 80, 240 }, { 2, "John", 70, 80, 90, 240 },
		{ 3, "Sue", 70, 70, 70, 210 }, { 4, "Jane", 80, 70, 60, 210 }, { 5, "Joe_05", 80, 70, 60, 210 },
		{ 6, "Joe_06", 80, 70, 60, 210 }, { 7, "Joe_07", 80, 70, 60, 210 }, { 8, "Joe_08", 80, 70, 60, 210 },
		{ 9, "Joe_09", 80, 70, 60, 210 }, { 10, "Joe_10", 80, 70, 60, 210 }, { 11, "Joe_11", 80, 70, 60, 210 },
		{ 12, "Joe_12", 80, 70, 60, 210 }, { 13, "Joe_13", 80, 70, 60, 210 }, { 14, "Joe_14", 80, 70, 60, 210 },
		{ 15, "Joe_15", 80, 70, 60, 210 }, { 16, "Joe_16", 80, 70, 60, 210 }, { 17, "Joe_17", 80, 70, 60, 210 },
		{ 18, "Joe_18", 80, 70, 60, 210 }, { 19, "Joe_19", 80, 70, 60, 210 },
		{ 20, "Joe_20", 80, 70, 60, 210 } };

	// 创建一个表格，指定 表头 和 所有行数据
	JTable table = new JTable(rowData, columnNames);

	// 设置表格内容颜色
	table.setForeground(Color.BLACK); // 字体颜色
	table.setFont(new Font(null, Font.PLAIN, 14)); // 字体样式
	table.setSelectionForeground(Color.DARK_GRAY); // 选中后字体颜色
	table.setSelectionBackground(Color.LIGHT_GRAY); // 选中后字体背景
	table.setGridColor(Color.GRAY); // 网格颜色

	// 设置表头
	table.getTableHeader().setFont(new Font(null, Font.BOLD, 14)); // 设置表头名称字体样式
	table.getTableHeader().setForeground(Color.RED); // 设置表头名称字体颜色
	table.getTableHeader().setResizingAllowed(false); // 设置不允许手动改变列宽
	table.getTableHeader().setReorderingAllowed(false); // 设置不允许拖动重新排序各列

	// 设置行高
	table.setRowHeight(30);

	// 第一列列宽设置为40
	table.getColumnModel().getColumn(0).setPreferredWidth(40);

	// 设置滚动面板视口大小（超过该大小的行数据，需要拖动滚动条才能看到）
	table.setPreferredScrollableViewportSize(new Dimension(400, 300));

	// 把 表格 放到 滚动面板 中（表头将自动添加到滚动面板顶部）
	JScrollPane scrollPane = new JScrollPane(table);

	// 添加 滚动面板 到 内容面板
	// panel.add(scrollPane);

	// 设置 内容面板 到 窗口
	jf.setContentPane(scrollPane);

	jf.pack();
	jf.setLocationRelativeTo(null);
	jf.setVisible(true);
    }

    /**
     * 简单列表
     * 
     * @param args
     */
    public static void main4(String[] args) {
	JFrame jf = new JFrame("测试窗口");
	jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	// 创建内容面板，使用边界布局
	JPanel panel = new JPanel(new BorderLayout());

	// 表头（列名）
	Object[] columnNames = { "姓名", "语文", "数学", "英语", "总分" };

	// 表格所有行数据
	Object[][] rowData = { { "张三", 80, 80, 80, 240 }, { "John", 70, 80, 90, 240 }, { "Sue", 70, 70, 70, 210 },
		{ "Jane", 80, 70, 60, 210 }, { "Joe", 80, 70, 60, 210 } };

	// 创建一个表格，指定 所有行数据 和 表头
	JTable table = new JTable(rowData, columnNames);

	// 把 表头 添加到容器顶部（使用普通的中间容器添加表格时，表头 和 内容 需要分开添加）
	panel.add(table.getTableHeader(), BorderLayout.NORTH);
	// 把 表格内容 添加到容器中心
	panel.add(table, BorderLayout.CENTER);

	jf.setContentPane(panel);
	jf.pack();
	jf.setLocationRelativeTo(null);
	jf.setVisible(true);
    }

    /**
     * 不同类型的对话框
     * 
     * @param args
     * @throws Exception
     */
    public static void main7(String[] args) throws Exception {
	JFrame jf = new JFrame("测试窗口");
	jf.setSize(400, 400);
	jf.setLocationRelativeTo(null);
	jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	/*
	 * 1. 消息对话框（信息消息）
	 */
	JButton btn01 = new JButton("showMessageDialog（信息消息）");
	btn01.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		// 消息对话框无返回, 仅做通知作用
		JOptionPane.showMessageDialog(jf, "Hello Information Message", "消息标题", JOptionPane.INFORMATION_MESSAGE);
	    }
	});

	/*
	 * 2. 消息对话框（警告消息）
	 */
	JButton btn02 = new JButton("showMessageDialog（警告消息）");
	btn02.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		// 消息对话框无返回, 仅做通知作用
		JOptionPane.showMessageDialog(jf, "Hello Warning Message", "消息标题", JOptionPane.WARNING_MESSAGE);
	    }
	});

	/*
	 * 3. 确认对话框
	 */
	JButton btn03 = new JButton("showConfirmDialog");
	btn03.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		/*
		 * 返回用户点击的选项, 值为下面三者之一: 是: JOptionPane.YES_OPTION 否:
		 * JOptionPane.NO_OPTION 取消: JOptionPane.CANCEL_OPTION 关闭:
		 * JOptionPane.CLOSED_OPTION
		 */
		int result = JOptionPane.showConfirmDialog(jf, "确认删除？", "提示", JOptionPane.YES_NO_CANCEL_OPTION);
		System.out.println("选择结果: " + result);
	    }
	});

	/*
	 * 4. 输入对话框（文本框输入）
	 */
	JButton btn04 = new JButton("showInputDialog（文本框输入）");
	btn04.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		// 显示输入对话框, 返回输入的内容
		String inputContent = JOptionPane.showInputDialog(jf, "输入你的名字:", "默认内容");
		System.out.println("输入的内容: " + inputContent);
	    }
	});

	/*
	 * 5. 输入对话框（下拉框选择）
	 */
	JButton btn05 = new JButton("showInputDialog（下拉框选择）");
	btn05.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		Object[] selectionValues = new Object[] { "香蕉", "雪梨", "苹果" };

		// 显示输入对话框, 返回选择的内容, 点击取消或关闭, 则返回null
		Object inputContent = JOptionPane.showInputDialog(jf, "选择一项: ", "标题", JOptionPane.PLAIN_MESSAGE, null,
			selectionValues, selectionValues[0]);
		System.out.println("输入的内容: " + inputContent);
	    }
	});

	/*
	 * 6. 选项对话框
	 */
	JButton btn06 = new JButton("showOptionDialog");
	btn06.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		// 选项按钮
		Object[] options = new Object[] { "香蕉", "雪梨", "苹果" };

		// 显示选项对话框, 返回选择的选项索引, 点击关闭按钮返回-1
		int optionSelected = JOptionPane.showOptionDialog(jf, "请点击一个按钮选择一项", "对话框标题",
			JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, options, // 如果传null,
												    // 则按钮为
												    // optionType
												    // 类型所表示的按钮（也就是确认对话框）
			options[0]);

		if (optionSelected >= 0) {
		    System.out.println("点击的按钮: " + options[optionSelected]);
		}
	    }
	});

	// 垂直排列按钮
	Box vBox = Box.createVerticalBox();
	vBox.add(btn01);
	vBox.add(btn02);
	vBox.add(btn03);
	vBox.add(btn04);
	vBox.add(btn05);
	vBox.add(btn06);

	JPanel panel = new JPanel();
	panel.add(vBox);

	jf.setContentPane(panel);
	jf.setVisible(true);
    }

    /**
     * Tab选项卡
     * 
     * @param args
     */
    public static void main2(String[] args) {
	JFrame jf = new JFrame("测试窗口");
	jf.setSize(300, 300);
	jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	jf.setLocationRelativeTo(null);

	// 创建选项卡面板
	JTabbedPane tabbedPane = new JTabbedPane();

	// 创建第 1 个选项卡（选项卡只包含 标题）
	tabbedPane.addTab("Tab01", createTextPanel("TAB 01"));

	// 创建第 2 个选项卡（选项卡包含 标题 和 图标）
	tabbedPane.addTab("Tab02", new ImageIcon("bb.jpg"), createTextPanel("TAB 02"));

	// 创建第 3 个选项卡（选项卡包含 标题、图标 和 tip提示）
	tabbedPane.addTab("Tab03", new ImageIcon("bb.jpg"), createTextPanel("TAB 03"), "This is a tab.");

	// 添加选项卡选中状态改变的监听器
	tabbedPane.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		System.out.println("当前选中的选项卡: " + tabbedPane.getSelectedIndex());
	    }
	});

	// 设置默认选中的选项卡
	tabbedPane.setSelectedIndex(1);

	jf.setContentPane(tabbedPane);
	jf.setVisible(true);
    }

    /**
     * 创建一个面板，面板中心显示一个标签，用于表示某个选项卡需要显示的内容
     */
    private static JComponent createTextPanel(String text) {
	// 创建面板, 使用一个 1 行 1 列的网格布局（为了让标签的宽高自动撑满面板）
	JPanel panel = new JPanel(new GridLayout(1, 1));

	// 创建标签
	JLabel label = new JLabel(text);
	label.setFont(new Font(null, Font.PLAIN, 50));
	label.setHorizontalAlignment(SwingConstants.CENTER);

	// 添加标签到面板
	panel.add(label);

	return panel;
    }

    /**
     * 父窗口弹出子窗口
     * 
     * @param args
     */
    public static void main1(String[] args) {
	JFrame jf = new JFrame("测试窗口");
	jf.setSize(400, 400);
	jf.setLocationRelativeTo(null);
	jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	JPanel panel = new JPanel();

	JButton btn = new JButton("Show New Window");
	btn.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		// 点击按钮, 显示新的一个窗口
		showNewWindow(jf);
	    }
	});
	panel.add(btn);

	jf.setContentPane(panel);
	jf.setVisible(true);
    }

    public static void showNewWindow(JFrame relativeWindow) {
	// 创建一个新窗口
	JFrame newJFrame = new JFrame("新的窗口");

	newJFrame.setSize(250, 250);

	// 把新窗口的位置设置到 relativeWindow 窗口的中心
	newJFrame.setLocationRelativeTo(relativeWindow);

	// 点击窗口关闭按钮, 执行销毁窗口操作（如果设置为 EXIT_ON_CLOSE, 则点击新窗口关闭按钮后, 整个进程将结束）
	newJFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	// 窗口设置为不可改变大小
	newJFrame.setResizable(false);

	JPanel panel = new JPanel(new GridLayout(1, 1));

	// 在新窗口中显示一个标签
	JLabel label = new JLabel("这是一个窗口");
	label.setFont(new Font(null, Font.PLAIN, 25));
	label.setHorizontalAlignment(SwingConstants.CENTER);
	label.setVerticalAlignment(SwingConstants.CENTER);
	panel.add(label);

	newJFrame.setContentPane(panel);
	newJFrame.setVisible(true);
    }
}
