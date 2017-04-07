package gui;

import interpret.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.*;
import javax.swing.*;
import javax.swing.event.*;

public class MainFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private static final Color ERROR_TEXT_COLOR = new Color(0xFFFF2020);
	private static final int ERROR_TEXTFIELD_WIDTH = 240;
	private static final int TEXTCOMPONENT_HEIGHT = 20;
	private static final int BUTTON_HEIGHT = 30;
	private static final Dimension MIN_FRAME_SIZE = new Dimension(800, 700);
	private static final Dimension DEF_FRAME_SIZE = new Dimension(960, 820);
	public static final String REFERENCE_PATTERN = "\\s*(#\\d+)\\s*(#\\d+)?\\s*";
	
	private static final String CLASS_NOT_FOUND_TEXT = "*クラスが存在しません";
	private static final String ARGUMENT_ERROR_TEXT = "*パラメータが不正です";
	private static final String FIELDVALUE_ERROR_TEXT = "*パラメータが不正です";
	private static final String NULL_ERROR_TEXT = "*インスタンスがnullです";
	private static final String CHANGE_VALUE_TOOLTIP_TEXT =
			"値を変更します. 表示される値と設定する時の値のフォーマットが違うため、表示される値をそのまま設定できない場合があります.";
	private static final String FIELDVALUE_TOOLTIP_TEXT = 
			"\"#番号\"で生成済みインスタンスを引数に指定できます(配列要素の場合は\"#インスタンス番号#配列要素番号\"で指定).";
	private static final String PARAMETER_TOOLTIP_TEXT = "カンマ区切りで複数の引数を指定できます. " + FIELDVALUE_TOOLTIP_TEXT;
	
	private long actionCount = 0;
	private boolean notPublicField = true;
	private boolean notPublicMethod = true;
	private Class<?> targetClass;
	private int targetArrayIndex;
	private Object targetObject;
	private ObjectManager objectManager = new ObjectManager();
	private ArrayList<Constructor<?>> constructorList;
	private ArrayList<Field> fieldList;
	private ArrayList<Method> methodList;
	
	private GroupBox groupBox_Class = new GroupBox();
	private JLabel label_Class = new JLabel();
	private JTextField textField_ClassName = new JTextField();
	private JLabel label_ClassNotFound = new JLabel();
	
	private GroupBox groupBox_Constructor = new GroupBox();
	private JList<String> list_Constructor = new JList<String>();
	private JScrollPane scrollPane_ConstructorList = new JScrollPane();
	private JButton button_ConstructorList = new JButton();
	private JLabel label_ConstructorParameter = new JLabel();
	private JTextField textField_ConstructorParameter = new JTextField();
	private JButton button_CreateObject = new JButton();
	private JLabel label_ConstructorError = new JLabel();
	
	private GroupBox groupBox_Array = new GroupBox();
	private JLabel label_ArrayLength = new JLabel();
	private JSpinner spinner_ArrayLength = new JSpinner();
	private JButton button_CreateNullArray = new JButton();
	private JLabel label_ArrayParameter = new JLabel();
	private JTextField textField_ArrayParameter = new JTextField();
	private JButton button_CreateInitArray = new JButton();
	private JLabel label_ArrayCreateError = new JLabel();
	
	private GroupBox groupBox_Objects = new GroupBox();
	private JList<String> list_Objects = new JList<String>();
	private JScrollPane scrollPane_List_Objects = new JScrollPane();
	private JLabel label_ObjectError = new JLabel();
	
	private GroupBox groupBox_ArrayObjects = new GroupBox();
	private JLabel label_ArrayName = new JLabel();
	private JTextField textField_ArrayName = new JTextField();
	private JButton button_GetArray = new JButton();
	private JList<String> list_ArrayObjects = new JList<String>();
	private JScrollPane scrollPane_List_ArrayObjects = new JScrollPane();
	private JLabel label_ArrayValue = new JLabel();
	private JTextField textField_ArrayValue = new JTextField();
	private JButton button_ChangeArrayValue = new JButton();
	private JLabel label_ArrayValueError = new JLabel();
	
	private GroupBox groupBox_ObjectData = new GroupBox();
	private JLabel label_ObjectName = new JLabel();
	private JTextField textField_ObjectName = new JTextField();
	private JButton button_GetObjectData = new JButton();
	private JButton button_GetArrayObjectData = new JButton();
	
	private GroupBox groupBox_Fields = new GroupBox();
	private JList<String> list_Fields = new JList<String>();
	private JScrollPane scrollPane_List_Fields = new JScrollPane();
	private JLabel label_FieldValue = new JLabel();
	private JTextField textField_FieldValue = new JTextField();
	private JButton button_ChangeFieldValue = new JButton();
	private JLabel label_FieldError = new JLabel();
	
	private GroupBox groupBox_Methods = new GroupBox();
	private JList<String> list_Methods = new JList<String>();
	private JScrollPane scrollPane_List_Methods = new JScrollPane();
	private JLabel label_MethodParameter = new JLabel();
	private JTextField textField_MethodParameter = new JTextField();
	private JButton button_InvokeMethod = new JButton();
	private JButton button_InvokeMethod_GetObj = new JButton();
	private JLabel label_MethodError = new JLabel();
	
	private GroupBox groupBox_Result = new GroupBox();
	private TextArea textArea_Result = new TextArea();
	
	public MainFrame()
	{
		super("Interpert");
		
		this.setLayout(null);
		this.setSize(DEF_FRAME_SIZE);
		this.setMinimumSize(MIN_FRAME_SIZE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent e)
			{
				locateComponent();
				revalidate();
			}
		});
		
		this.initializeComponent();
		this.locateComponent();
		
		// Drag&Drop用
		list_Objects.setDragEnabled(true);
		list_Objects.setTransferHandler(new ListTransferHandler());
		list_Objects.addMouseListener(new MouseListener_ListDragged());
		
		list_ArrayObjects.setDragEnabled(true);
		list_ArrayObjects.setTransferHandler(new ListTransferHandler());
		list_ArrayObjects.addMouseListener(new MouseListener_ListDragged());
		
		textField_ConstructorParameter.setTransferHandler(new TextFieldTransferHandler());
		textField_ArrayParameter.setTransferHandler(new TextFieldTransferHandler());
		textField_ArrayValue.setTransferHandler(new TextFieldTransferHandler());
		textField_FieldValue.setTransferHandler(new TextFieldTransferHandler());
		textField_MethodParameter.setTransferHandler(new TextFieldTransferHandler());
	}
	
	private void initializeComponent()
	{
		// 
		
		groupBox_Class.setTitle("インスタンス生成");
		this.add(groupBox_Class);
		
		label_Class.setText("クラス名");
		groupBox_Class.add(label_Class);
		
		groupBox_Class.add(textField_ClassName);

		label_ClassNotFound.setForeground(ERROR_TEXT_COLOR);
		groupBox_Class.add(label_ClassNotFound);
		
		
		groupBox_Constructor.setTitle("コンストラクタ実行");
		groupBox_Class.add(groupBox_Constructor);
		
		scrollPane_ConstructorList.getViewport().setView(list_Constructor);
		groupBox_Constructor.add(scrollPane_ConstructorList);
		
		button_ConstructorList.setText("一覧取得");
		button_ConstructorList.addActionListener(new ActionListener_ConstructorList());
		button_ConstructorList.setToolTipText("コンストラクタ一覧を取得します.");
		groupBox_Constructor.add(button_ConstructorList);

		label_ConstructorParameter.setText("引数");
		label_ConstructorParameter.setToolTipText(PARAMETER_TOOLTIP_TEXT);
		groupBox_Constructor.add(label_ConstructorParameter);

		groupBox_Constructor.add(textField_ConstructorParameter);

		button_CreateObject.setText("実行");
		button_CreateObject.addActionListener(new ActionListener_CreateObject());
		button_CreateObject.setToolTipText("選択されているコンストラクタを実行します.");
		groupBox_Constructor.add(button_CreateObject);

		label_ConstructorError.setForeground(ERROR_TEXT_COLOR);
		groupBox_Constructor.add(label_ConstructorError);
		
		
		groupBox_Array.setTitle("配列生成");
		groupBox_Class.add(groupBox_Array);
		
		label_ArrayLength.setText("長さ");
		groupBox_Array.add(label_ArrayLength);
		
		spinner_ArrayLength.setModel(new SpinnerNumberModel(1, 1, 100, 1));
		spinner_ArrayLength.setEditor(new JSpinner.NumberEditor(spinner_ArrayLength, "0"));
		groupBox_Array.add(spinner_ArrayLength);
		
		button_CreateNullArray.setText("空配列");
		button_CreateNullArray.addActionListener(new ActionListener_CreateNullArray());
		button_CreateNullArray.setToolTipText("指定した長さの要素がnullの配列を生成します.");
		groupBox_Array.add(button_CreateNullArray);

		label_ArrayParameter.setText("初期値");
		label_ArrayParameter.setToolTipText(PARAMETER_TOOLTIP_TEXT);
		groupBox_Array.add(label_ArrayParameter);

		groupBox_Array.add(textField_ArrayParameter);
		
		button_CreateInitArray.setText("設定済み配列");
		button_CreateInitArray.setToolTipText("指定した長さで要素が設定された配列を生成します.");
		button_CreateInitArray.addActionListener(new ActionListener_CreateInitArray());
		groupBox_Array.add(button_CreateInitArray);

		label_ArrayCreateError.setForeground(ERROR_TEXT_COLOR);
		groupBox_Array.add(label_ArrayCreateError);

		
		// 
		
		groupBox_Objects.setTitle("インスタンス一覧");
		this.add(groupBox_Objects);
		
		scrollPane_List_Objects.getViewport().setView(list_Objects);
		groupBox_Objects.add(scrollPane_List_Objects);
		

		// 
		
		groupBox_ArrayObjects.setTitle("配列要素一覧");
		this.add(groupBox_ArrayObjects);

		label_ArrayName.setText("対象");
		label_ArrayName.setToolTipText(FIELDVALUE_TOOLTIP_TEXT);
		groupBox_ArrayObjects.add(label_ArrayName);

		textField_ArrayName.setEditable(false);
		groupBox_ArrayObjects.add(textField_ArrayName);

		button_GetArray.setText("取得");
		button_GetArray.addActionListener(new ActionListener_GetArray());
		button_GetArray.setToolTipText("インスタンス一覧で選択されている配列の要素を取得します.");
		groupBox_ArrayObjects.add(button_GetArray);
		
		list_ArrayObjects.addListSelectionListener(new ListSelectionListener_ArrayObjects());
		scrollPane_List_ArrayObjects.getViewport().setView(list_ArrayObjects);
		groupBox_ArrayObjects.add(scrollPane_List_ArrayObjects);

		label_ArrayValue.setText("値");
		label_ArrayValue.setToolTipText(FIELDVALUE_TOOLTIP_TEXT);
		groupBox_ArrayObjects.add(label_ArrayValue);

		groupBox_ArrayObjects.add(textField_ArrayValue);

		button_ChangeArrayValue.setText("変更");
		button_ChangeArrayValue.addActionListener(new ActionListener_ChangeArrayObject());
		button_ChangeArrayValue.setToolTipText("選択されている配列の要素を変更します.");
		groupBox_ArrayObjects.add(button_ChangeArrayValue);

		label_ArrayValueError.setForeground(ERROR_TEXT_COLOR);
		groupBox_ArrayObjects.add(label_ArrayValueError);


		//

		groupBox_ObjectData.setTitle("インスタンス情報");
		this.add(groupBox_ObjectData);
		
		label_ObjectName.setText("対象インスタンス");
		groupBox_ObjectData.add(label_ObjectName);
		
		textField_ObjectName.setEditable(false);
		groupBox_ObjectData.add(textField_ObjectName);

		button_GetObjectData.setText("取得");
		button_GetObjectData.setToolTipText("インスタンス一覧で選択されているインスタンスの情報を取得します.");
		button_GetObjectData.addActionListener(new ActionListener_GetObject());
		groupBox_ObjectData.add(button_GetObjectData);

		button_GetArrayObjectData.setText("配列要素取得");
		button_GetArrayObjectData.setToolTipText("配列要素一覧で選択されているインスタンスの情報を取得します.");
		button_GetArrayObjectData.addActionListener(new ActionListener_GetArrayObject());
		groupBox_ObjectData.add(button_GetArrayObjectData);

		label_ObjectError.setForeground(ERROR_TEXT_COLOR);
		groupBox_ObjectData.add(label_ObjectError);

		
		groupBox_Fields.setTitle("フィールド");
		groupBox_ObjectData.add(groupBox_Fields);
		
		list_Fields.addListSelectionListener(new ListSelectionListener_Fields());
		scrollPane_List_Fields.getViewport().setView(list_Fields);
		groupBox_Fields.add(scrollPane_List_Fields);

		label_FieldValue.setText("値");
		label_FieldValue.setToolTipText(FIELDVALUE_TOOLTIP_TEXT);
		groupBox_Fields.add(label_FieldValue);

		groupBox_Fields.add(textField_FieldValue);

		button_ChangeFieldValue.setText("変更");
		button_ChangeFieldValue.addActionListener(new ActionListener_ChangeField());
		button_ChangeFieldValue.setToolTipText(CHANGE_VALUE_TOOLTIP_TEXT);
		groupBox_Fields.add(button_ChangeFieldValue);

		label_FieldError.setForeground(ERROR_TEXT_COLOR);
		groupBox_Fields.add(label_FieldError);
		

		groupBox_Methods.setTitle("メソッド");
		groupBox_ObjectData.add(groupBox_Methods);
		
		scrollPane_List_Methods.getViewport().setView(list_Methods);
		groupBox_Methods.add(scrollPane_List_Methods);

		label_MethodParameter.setText("引数");
		label_MethodParameter.setToolTipText(PARAMETER_TOOLTIP_TEXT);
		groupBox_Methods.add(label_MethodParameter);
		
		groupBox_Methods.add(textField_MethodParameter);

		button_InvokeMethod.setText("実行");
		button_InvokeMethod.addActionListener(new ActionListener_InvokeMethod());
		button_InvokeMethod.setToolTipText("選択されているメソッドを実行します.");
		groupBox_Methods.add(button_InvokeMethod);

		button_InvokeMethod_GetObj.setText("実行+取得");
		button_InvokeMethod_GetObj.addActionListener(new ActionListener_InvokeMethod());
		button_InvokeMethod_GetObj.setToolTipText("選択されているメソッドを実行して返り値をインスタンス一覧に追加します.");
		groupBox_Methods.add(button_InvokeMethod_GetObj);

		label_MethodError.setForeground(ERROR_TEXT_COLOR);
		groupBox_Methods.add(label_MethodError);
		
		
		//
		
		groupBox_Result.setTitle("実行結果");
		this.add(groupBox_Result);
		
		textArea_Result.setEditable(false);
		groupBox_Result.add(textArea_Result);
	}
	
	private void locateComponent()
	{
		groupBox_Class.setBounds(10, 10, this.getWidth() / 2 - 20, this.getHeight() - 300);
		label_Class.setBounds(25, 25, 55, TEXTCOMPONENT_HEIGHT);
		textField_ClassName.setBounds(90, 25, groupBox_Class.getWidth() - 120, TEXTCOMPONENT_HEIGHT);
		label_ClassNotFound.setBounds(90, 45, ERROR_TEXTFIELD_WIDTH, TEXTCOMPONENT_HEIGHT);
		
		groupBox_Constructor.setBounds(15, 70, groupBox_Class.getWidth() - 30, groupBox_Class.getHeight() - 205);
		scrollPane_ConstructorList.setBounds(15, 20, groupBox_Constructor.getWidth() - 30, groupBox_Constructor.getHeight() - 125);
		button_ConstructorList.setBounds(groupBox_Constructor.getWidth() - 130, groupBox_Constructor.getHeight() - 95, 115, BUTTON_HEIGHT);
		label_ConstructorParameter.setBounds(15, groupBox_Constructor.getHeight() - 50, 30, TEXTCOMPONENT_HEIGHT);
		textField_ConstructorParameter.setBounds(55, groupBox_Constructor.getHeight() - 50, groupBox_Constructor.getWidth() - 150, TEXTCOMPONENT_HEIGHT);
		button_CreateObject.setBounds(groupBox_Constructor.getWidth() - 85, groupBox_Constructor.getHeight() - 55, 70, BUTTON_HEIGHT);
		label_ConstructorError.setBounds(55, groupBox_Constructor.getHeight() - 30, ERROR_TEXTFIELD_WIDTH, TEXTCOMPONENT_HEIGHT);

		groupBox_Array.setBounds(15, groupBox_Class.getHeight() - 130, groupBox_Class.getWidth() - 30, 115);
		label_ArrayLength.setBounds(15, 25, 30, TEXTCOMPONENT_HEIGHT);
		spinner_ArrayLength.setBounds(65, 25, groupBox_Array.getWidth() - 220, TEXTCOMPONENT_HEIGHT);
		button_CreateNullArray.setBounds(groupBox_Array.getWidth() - 135, 20, 120, BUTTON_HEIGHT);
		label_ArrayParameter.setBounds(15, 65, 40, TEXTCOMPONENT_HEIGHT);
		textField_ArrayParameter.setBounds(65, 65, groupBox_Array.getWidth() - 210, TEXTCOMPONENT_HEIGHT);
		button_CreateInitArray.setBounds(groupBox_Array.getWidth() - 135, 60, 120, BUTTON_HEIGHT);
		label_ArrayCreateError.setBounds(65, 90, ERROR_TEXTFIELD_WIDTH, TEXTCOMPONENT_HEIGHT);

		
		groupBox_Result.setBounds(10, groupBox_Class.getHeight() + 20, this.getWidth() / 2 - 20, 130);
		textArea_Result.setBounds(15, 20, groupBox_Result.getWidth() - 30, groupBox_Result.getHeight() - 35);
		

		groupBox_Objects.setBounds(this.getWidth() / 2, 10, this.getWidth() / 4 - 35, this.getHeight() / 4 - 20);
		scrollPane_List_Objects.setBounds(15, 20, groupBox_Objects.getWidth() - 30, groupBox_Objects.getHeight() - 35);
		
		groupBox_ArrayObjects.setBounds(this.getWidth() * 3 / 4 - 25, 10, this.getWidth() / 4, this.getHeight() / 4 - 20);
		label_ArrayName.setBounds(15, 25, 30, TEXTCOMPONENT_HEIGHT);
		textField_ArrayName.setBounds(50, 25, groupBox_ArrayObjects.getWidth() - 135, TEXTCOMPONENT_HEIGHT);
		button_GetArray.setBounds(groupBox_ArrayObjects.getWidth() - 75, 20, 60, BUTTON_HEIGHT);
		scrollPane_List_ArrayObjects.setBounds(15, 60, groupBox_ArrayObjects.getWidth() - 30, groupBox_ArrayObjects.getHeight() - 125);
		label_ArrayValue.setBounds(15, groupBox_ArrayObjects.getHeight() - 50, 30, TEXTCOMPONENT_HEIGHT);
		textField_ArrayValue.setBounds(50, groupBox_ArrayObjects.getHeight() - 50, groupBox_ArrayObjects.getWidth() - 135, TEXTCOMPONENT_HEIGHT);
		button_ChangeArrayValue.setBounds(groupBox_ArrayObjects.getWidth() - 75, groupBox_ArrayObjects.getHeight() - 55, 60, BUTTON_HEIGHT);
		label_ArrayValueError.setBounds(15, groupBox_ArrayObjects.getHeight() - 25, groupBox_ArrayObjects.getWidth() - 30, TEXTCOMPONENT_HEIGHT);
		
		groupBox_ObjectData.setBounds(this.getWidth() / 2, groupBox_Objects.getHeight() + 20, this.getWidth() / 2 - 25, this.getHeight() * 3 / 4 - 45);
		label_ObjectName.setBounds(20, 25, 105, TEXTCOMPONENT_HEIGHT);
		textField_ObjectName.setBounds(135, 25, groupBox_ObjectData.getWidth() - 240, TEXTCOMPONENT_HEIGHT);
		button_GetObjectData.setBounds(groupBox_ObjectData.getWidth() - 90, 20, 70, BUTTON_HEIGHT);
		button_GetArrayObjectData.setBounds(groupBox_ObjectData.getWidth() - 140, 60, 120, BUTTON_HEIGHT);
		label_ObjectError.setBounds(15, 65, ERROR_TEXTFIELD_WIDTH, TEXTCOMPONENT_HEIGHT);
		
		groupBox_Fields.setBounds(15, 95, groupBox_ObjectData.getWidth() - 30, groupBox_ObjectData.getHeight() / 2 - 65);
		scrollPane_List_Fields.setBounds(15, 20, groupBox_Fields.getWidth() - 30, groupBox_Fields.getHeight() - 85);
		label_FieldValue.setBounds(15, groupBox_Fields.getHeight() - 50, 30, TEXTCOMPONENT_HEIGHT);
		textField_FieldValue.setBounds(55, groupBox_Fields.getHeight() - 50, groupBox_Fields.getWidth() - 150, TEXTCOMPONENT_HEIGHT);
		button_ChangeFieldValue.setBounds(groupBox_Fields.getWidth() - 85, groupBox_Fields.getHeight() - 55, 70, BUTTON_HEIGHT);
		label_FieldError.setBounds(55, groupBox_Fields.getHeight() - 30, ERROR_TEXTFIELD_WIDTH, TEXTCOMPONENT_HEIGHT);

		groupBox_Methods.setBounds(15, groupBox_Fields.getHeight() + 100, groupBox_ObjectData.getWidth() - 30, groupBox_ObjectData.getHeight() / 2 - 50);
		scrollPane_List_Methods.setBounds(15, 20, groupBox_Methods.getWidth() - 30, groupBox_Methods.getHeight() - 115);
		label_MethodParameter.setBounds(15, groupBox_Methods.getHeight() - 80, 30, TEXTCOMPONENT_HEIGHT);
		textField_MethodParameter.setBounds(55, groupBox_Methods.getHeight() - 80, groupBox_Methods.getWidth() - 150, TEXTCOMPONENT_HEIGHT);
		button_InvokeMethod.setBounds(groupBox_Methods.getWidth() - 85, groupBox_Methods.getHeight() - 85, 70, BUTTON_HEIGHT);
		button_InvokeMethod_GetObj.setBounds(groupBox_Methods.getWidth() - 115, groupBox_Methods.getHeight() - 45, 100, BUTTON_HEIGHT);
		label_MethodError.setBounds(15, groupBox_Methods.getHeight() - 40, ERROR_TEXTFIELD_WIDTH, TEXTCOMPONENT_HEIGHT);
	}
	
	private class ActionListener_ConstructorList implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				targetClass = Class.forName(textField_ClassName.getText());
				Constructor<?>[] constructors = ObjectManager.getAllConstructors(targetClass, notPublicMethod);
				constructorList = new ArrayList<Constructor<?>>(Arrays.asList(constructors));
				
				String[] strings = new String[constructors.length];
				for(int i = 0; i < constructors.length; i++)
				{
					strings[i] = Utilities.getConstructorString(constructors[i]);
				}
				list_Constructor.setListData(strings);

				label_ClassNotFound.setText("");
			}
			catch(ClassNotFoundException ex)
			{
				targetClass = null;
				constructorList = null;
				label_ClassNotFound.setText(CLASS_NOT_FOUND_TEXT);
				list_Constructor.setListData(new String[] { });
			}
		}
	}
	
	private class ActionListener_CreateObject implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				Constructor<?> constructor = constructorList.get(list_Constructor.getSelectedIndex());
				ReturnValue rtn = objectManager.createObject(
						constructor, stringToArgs(constructor.getParameterTypes(), textField_ConstructorParameter.getText()));
				
				list_Objects.setListData(getObjectListStrings());

				StringBuilder sb = new StringBuilder();
				sb.append("[").append(actionCount++).append("]").append(" コンストラクタ実行\n");
				
				if(rtn.isException())
				{
					sb.append("    返り値 : なし(インスタンス生成失敗)\n")
						.append("    例外   : ").append(rtn.getException().toString())
						.append("\n\n");
				}
				else
				{
					sb.append("    返り値 : なし(インスタンス生成成功)\n")
						.append("    例外   : なし\n\n");
				}

				textArea_Result.setText(sb.toString());
				label_ConstructorError.setText("");
			}
			catch(ArrayIndexOutOfBoundsException ex)
			{
				label_ConstructorError.setText("*コンストラクタが選択されていません");
			}
			catch(NullPointerException ex)
			{
				if(list_Constructor.getSelectedIndex() == -1)
					label_ConstructorError.setText("*コンストラクタが選択されていません");
				else
					label_ConstructorError.setText(ARGUMENT_ERROR_TEXT);
			}
			catch(IllegalArgumentException ex)
			{
				label_ConstructorError.setText(ARGUMENT_ERROR_TEXT);
			}
			catch(InstantiationException ex)
			{
				label_ConstructorError.setText("*コンストラクタ呼び出しに失敗しました");
			}
			catch (ReflectiveOperationException e1)	// 配列要素にnullがあり、組み込み型に変換できない場合
			{
				label_ConstructorError.setText("*配列にnullが含まれています");
			}
		}
	}
	
	private class ActionListener_CreateNullArray implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				int arrayLength = (Integer)spinner_ArrayLength.getModel().getValue();
				createArray(arrayLength);
				
				
			}
			catch(ClassNotFoundException ex)
			{
				label_ClassNotFound.setText(CLASS_NOT_FOUND_TEXT);
			}
		}
	}
	
	private class ActionListener_CreateInitArray implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				int arrayLength = (Integer)spinner_ArrayLength.getModel().getValue();
				Class<?>[] clsArray = new Class<?>[arrayLength];
				Class<?> cls = Class.forName(textField_ClassName.getText());

				for(int i = 0; i < clsArray.length; i++)
				{
					clsArray[i] = cls;
				}

				Object[] objects = stringToArgs(clsArray, textField_ArrayParameter.getText());
				
				createArray(arrayLength);				
				Object array = objectManager.getObjectAt(objectManager.getObjectCount() - 1);
				
				for(int i = 0; i < arrayLength; i++)
				{
					Array.set(array, i, objects[i]);
				}
			}
			catch(ClassNotFoundException ex)
			{
				label_ClassNotFound.setText(CLASS_NOT_FOUND_TEXT);
			}
			catch(IllegalArgumentException ex)	// 文字列->オブジェクト配列の変換に失敗
			{
				label_ArrayCreateError.setText(ARGUMENT_ERROR_TEXT);
			}
			catch (ReflectiveOperationException ex)	// 配列要素にnullがあり、組み込み型に変換できない場合
			{
				label_ArrayCreateError.setText("*配列にnullが含まれています");
			}
		}
	}

	private class ActionListener_GetArray implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// 配列要素のリストを更新
			targetArrayIndex = list_Objects.getSelectedIndex();
			refleshArrayObjectList(targetArrayIndex);
		}
	}
	
	private class ActionListener_GetObject implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			int index = list_Objects.getSelectedIndex();
			
			try
			{
				targetObject = objectManager.getObjectAt(index);
				targetObjectChanged();
				
				textField_ObjectName.setText(getObjectListStrings()[index]);
				label_ObjectError.setText("");
			}
			catch(IndexOutOfBoundsException ex)
			{
				targetObject = null;
				textField_ObjectName.setText("");
				
				list_Fields.setListData(new String[] { });
				fieldList = null;
				
				list_Methods.setListData(new String[] { });
				methodList = null;
				
				label_ObjectError.setText("*インスタンスが選択されていません");
			}
			catch(NullPointerException ex)	// 選択要素がnullの場合
			{
				targetObject = null;
				textField_ObjectName.setText("");
				
				list_Fields.setListData(new String[] { });
				fieldList = null;
				
				list_Methods.setListData(new String[] { });
				methodList = null;
				
				label_ObjectError.setText(NULL_ERROR_TEXT);
			}
		}
	}

	private class ActionListener_GetArrayObject implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			int index = list_ArrayObjects.getSelectedIndex();
			
			try
			{
				Object targetArray = objectManager.getObjectAt(targetArrayIndex);
				targetObject = Array.get(targetArray, index);
				targetObjectChanged();
				
				// 上のこの呼び出しだと配列要素のインスタンス形式が表示される
				//textField_ObjectName.setText(getArrayObjectListStrings_Object()[index]);
				textField_ObjectName.setText(getArrayObjectListStrings_Class()[index]);			
				label_ObjectError.setText("");
			}
			catch(IndexOutOfBoundsException
				| IllegalArgumentException | ReflectiveOperationException ex)
			{
				targetObject = null;
				textField_ObjectName.setText("");
				
				list_Fields.setListData(new String[] { });
				fieldList = null;
				
				list_Methods.setListData(new String[] { });
				methodList = null;
				
				label_ObjectError.setText("*配列要素が選択されていません");
			}
			catch(NullPointerException ex)	// 選択要素がnullの場合
			{
				targetObject = null;
				textField_ObjectName.setText("");
				
				list_Fields.setListData(new String[] { });
				fieldList = null;
				
				list_Methods.setListData(new String[] { });
				methodList = null;
				
				label_ObjectError.setText(NULL_ERROR_TEXT);
			}
		}
	}
	
	private class ActionListener_ChangeArrayObject implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			int index = list_ArrayObjects.getSelectedIndex();
			
			try
			{
				Object targetArray = objectManager.getObjectAt(targetArrayIndex);
				
				Array.set(targetArray, index,
					stringToArgs(new Class<?>[] { targetArray.getClass().getComponentType() }, textField_ArrayValue.getText())[0]);
				
				StringBuilder sb = new StringBuilder();
				sb.append("[").append(actionCount++).append("]").append(" 配列要素変更\n")
					.append("    配列 : ").append(targetArray.getClass().getSimpleName()).append("\n")
					.append("    Index : ").append(index)
					.append("\n\n");
				
				refleshArrayObjectList(targetArrayIndex);	// 配列要素のリストを更新
				
				textArea_Result.setText(sb.toString());
				label_ObjectError.setText("");
			}
			catch(NullPointerException ex)
			{
				label_ArrayValueError.setText("*配列が選択されていません");
			}
			catch(IndexOutOfBoundsException ex)	// 配列内要素が選択されていない場合
			{
				label_ArrayValueError.setText("*要素が選択されていません");
			}
			catch(IllegalArgumentException ex)
			{
				label_ArrayValueError.setText("*設定値が不正です");
			}
			catch (ReflectiveOperationException e1)	// 配列要素にnullがあり、組み込み型に変換できない場合
			{
				label_ArrayValueError.setText("*配列にnullが含まれています");
			}
		}
	}
	
	private class ActionListener_ChangeField implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				Field field = fieldList.get(list_Fields.getSelectedIndex());
				ObjectManager.changeFieldValue(targetObject, field,
					stringToArgs(new Class<?>[] { field.getType() }, textField_FieldValue.getText())[0]);
				
				StringBuilder sb = new StringBuilder();
				sb.append("[").append(actionCount++).append("]").append(" フィールド変更\n")
					.append("    フィールド : ").append(field.getName())
					.append("\n\n");
				
				textArea_Result.setText(sb.toString());
				label_FieldError.setText("");
				
				// 配列要素の内容を変更した場合に配列要素一覧を更新する
				// 2行目で配列要素でない場合に設定されるエラーメッセージを削除
				refleshArrayObjectList(targetArrayIndex);
				label_ArrayValueError.setText("");
			}
			catch(ArrayIndexOutOfBoundsException ex)
			{
				label_FieldError.setText(FIELDVALUE_ERROR_TEXT);
			}
			catch(NullPointerException ex)
			{
				if(list_Methods.getSelectedIndex() == -1)
					label_FieldError.setText("*フィールドが選択されていません");
				else
					label_FieldError.setText(FIELDVALUE_ERROR_TEXT);
			}
			catch(IllegalArgumentException ex)
			{
				label_FieldError.setText(FIELDVALUE_ERROR_TEXT);
			}
			catch(IllegalAccessException ex)
			{
				label_FieldError.setText("*変更不可能なフィールドです");
			}
			catch (ReflectiveOperationException e1)	// 配列要素にnullがあり、組み込み型に変換できない場合
			{
				label_FieldError.setText("*配列にnullが含まれています");
			}
		}
	}
	
	private class ActionListener_InvokeMethod implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				Method method = methodList.get(list_Methods.getSelectedIndex());
				ReturnValue rtn = ObjectManager.invokeMethod(targetObject, method,
					stringToArgs(method.getParameterTypes(), textField_MethodParameter.getText()));
				
				StringBuilder sb = new StringBuilder();
				sb.append("[").append(actionCount++).append("]").append(" メソッド実行\n").append("    返り値 : ");
				
				if(method.getReturnType() == void.class)
				{
					sb.append("(void)");
				}
				else if(rtn.isException())
				{
					sb.append("なし");
				}
				else
				{
					sb.append(Utilities.getObjectString(rtn.getReturnObject()))
						.append(" (")
						.append(rtn.getReturnObject().getClass().getSimpleName())
						.append(")");
				}
				
				sb.append("\n").append("    例外   : ")
					.append(rtn.isException() ? rtn.getException().toString() : "なし")
					.append("\n\n");

				textArea_Result.setText(sb.toString());
				label_MethodError.setText("");
				
				// インスタンス一覧に追加
				if(e.getSource() == button_InvokeMethod_GetObj)
				{
					if(rtn.getReturnObject() != null)
					{
						objectManager.addObject(rtn.getReturnObject());
						list_Objects.setListData(getObjectListStrings());
					}
					else
					{
						label_MethodError.setText("(返り値がありません)");
					}
				}

				// 配列要素の内容を変更した場合に配列要素一覧を更新する
				// 2行目で配列要素でない場合に設定されるエラーメッセージを削除
				refleshArrayObjectList(targetArrayIndex);
				label_ArrayValueError.setText("");
			}
			catch(ArrayIndexOutOfBoundsException ex)
			{
				label_MethodError.setText(ARGUMENT_ERROR_TEXT);
			}
			catch(NullPointerException ex)
			{
				if(list_Methods.getSelectedIndex() == -1)
					label_MethodError.setText("*メソッドが選択されていません");
				else
					label_MethodError.setText(ARGUMENT_ERROR_TEXT);
			}
			catch(IllegalArgumentException ex)
			{
				label_MethodError.setText(ARGUMENT_ERROR_TEXT);
			}
			catch (ReflectiveOperationException e1)	// 配列要素にnullがあり、組み込み型に変換できない場合
			{
				label_MethodError.setText("*配列にnullが含まれています");
			}
		}
	}
	
	private class ListSelectionListener_ArrayObjects implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			try
			{
				int index = list_ArrayObjects.getSelectedIndex();
				Object object = Array.get(objectManager.getObjectAt(targetArrayIndex), index);
				textField_ArrayValue.setText(Utilities.getObjectString(object));
				
				label_ArrayValueError.setText("");
			}
			catch(IndexOutOfBoundsException ex)
			{
				label_ArrayValueError.setText("");
				textField_ArrayValue.setText("");
			}
		}
	}
	
	private class ListSelectionListener_Fields implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			try
			{
				Field field = fieldList.get(list_Fields.getSelectedIndex());
				textField_FieldValue.setText(
						Utilities.getObjectString(ObjectManager.getFieldValue(targetObject, field)));
				
				label_FieldError.setText("");
			}
			catch(IndexOutOfBoundsException ex)
			{
				label_FieldError.setText("");
				textField_FieldValue.setText("");
			}
			catch(IllegalArgumentException ex)
			{
				throw new InternalError();
			}
		}
	}
	
	private class MouseListener_ListDragged extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent e)
		{
			try
			{
				JList<?> sender = (JList<?>)e.getSource();
				
				if((sender == list_Objects) || (sender == list_ArrayObjects))
				{
					TransferHandler handler = sender.getTransferHandler();
					handler.exportAsDrag(sender, e, TransferHandler.COPY);
				}
				else
				{
					throw new AssertionError();
				}
			}
			catch(ClassCastException ex)
			{
				throw new AssertionError();
			}
		}
	}
	
	private class ListTransferHandler extends TransferHandler
	{
		private static final long serialVersionUID = 1L;

		@Override
		protected Transferable createTransferable(JComponent component)
		{
			StringBuilder sb = new StringBuilder();
			
			if(component == list_Objects)
			{
				sb.append("#").append(list_Objects.getSelectedIndex());
			}
			else if(component == list_ArrayObjects)
			{
				sb.append("#").append(targetArrayIndex)
					.append("#").append(list_ArrayObjects.getSelectedIndex());
			}
			else
			{
				throw new AssertionError();
			}
			
			return new StringSelection(sb.toString());
		}
		
		@Override
		public int getSourceActions(JComponent c)
		{
	        return COPY;
	    }
	}
	
	private class TextFieldTransferHandler extends TransferHandler
	{
		private static final long serialVersionUID = 1L;

		@Override
		public boolean canImport(JComponent c, DataFlavor[] flavors)
		{
			for(DataFlavor flavor: flavors)
			{
				if(DataFlavor.stringFlavor.equals(flavor))
				{
					return true;
				}
			}
			
			return false;
		}
		
		@Override
		public boolean importData(JComponent c, Transferable t)
		{
			if(this.canImport(c, t.getTransferDataFlavors()))
			{
				try
				{
					JTextField targetComp = (JTextField)c;
					String word = (String)t.getTransferData(DataFlavor.stringFlavor);
					
					if(word.matches(REFERENCE_PATTERN))
					{
						String text = targetComp.getText();
						
						if((text.length() == 0) || (text.matches(".*,\\s*")))
						{
							text += word;
						}
						else
						{
							text += "," + word;
						}
						
						targetComp.setText(text);
					}
					
					return true;
				}
				catch(ClassCastException | UnsupportedFlavorException | IOException e)
				{
					return false;
				}
			}
			
			return false;
		}
	}
	
	/**
	 * 配列の要素リストの表示内容を更新します.
	 * @param selectedArrayIndex 選択されているインスタンス一覧のインデックス
	 */
	private void refleshArrayObjectList(int selectedArrayIndex)
	{
		try
		{
			list_ArrayObjects.setListData(getArrayObjectListStrings_Object());
			
			textField_ArrayName.setText(getObjectListStrings()[selectedArrayIndex]);
			label_ArrayValueError.setText("");
		}
		catch(IndexOutOfBoundsException
			| IllegalArgumentException | ReflectiveOperationException ex)
		{
			targetArrayIndex = -1;
			textField_ArrayName.setText("");
			
			list_ArrayObjects.setListData(new String[] { });
			
			label_ArrayValueError.setText("*配列が選択されていません");
		}
	}
	
	/**
	 * インスタンス一覧の文字列配列を取得します.
	 * @return インスタンス一覧の文字列配列
	 */
	private String[] getObjectListStrings()
	{
		String[] strings = new String[objectManager.getObjectCount()];
		
		for(int i = 0; i < strings.length; i++)
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append("#").append(i).append(" : ")
				.append(objectManager.getObjectAt(i).getClass().getSimpleName());
			
			strings[i] = sb.toString();
		}
		
		return strings;
	}

	/**
	 * 選択されているインスタンスの配列要素一覧の文字列配列を取得します.
	 * 文字列はインスタンスを基に取得します.
	 * @return 配列要素の文字列配列
	 * @throws ReflectiveOperationException 配列が選択されていない場合
	 */
	private String[] getArrayObjectListStrings_Object() throws ReflectiveOperationException
	{	
		try
		{
			Object targetArray = objectManager.getObjectAt(targetArrayIndex);
			String[] strings = new String[Array.getLength(targetArray)];
			
			for(int i = 0; i < strings.length; i++)
			{
				StringBuilder sb = new StringBuilder();
				
				sb.append("#").append(targetArrayIndex)
					.append("#").append(i).append(" : ")
					.append(Utilities.getObjectString(Array.get(targetArray, i)));
				
				strings[i] = sb.toString();
			}
			
			return strings;
		}
		catch(IllegalArgumentException | IndexOutOfBoundsException e)
		{
			throw new ReflectiveOperationException();
		}
	}

	/**
	 * 選択されているインスタンスの配列要素一覧の文字列配列を取得します.
	 * 文字列はクラスを基に取得します.
	 * @return 配列要素の文字列配列
	 * @throws ReflectiveOperationException 配列が選択されていない場合
	 */
	private String[] getArrayObjectListStrings_Class() throws ReflectiveOperationException
	{	
		try
		{
			Object targetArray = objectManager.getObjectAt(targetArrayIndex);
			String[] strings = new String[Array.getLength(targetArray)];
			
			for(int i = 0; i < strings.length; i++)
			{
				StringBuilder sb = new StringBuilder();
				Object obj = Array.get(targetArray, i);
				
				sb.append("#").append(targetArrayIndex)
					.append("#").append(i).append(" : ");
				
				if(obj == null)
				{
					sb.append("null");
				}
				else
				{
					sb.append(obj.getClass().getSimpleName());
				}
				
				strings[i] = sb.toString();
			}
			
			return strings;
		}
		catch(IllegalArgumentException | IndexOutOfBoundsException e)
		{
			throw new ReflectiveOperationException();
		}
	}
	
	/**
	 * 文字列を引数の配列に変換します.
	 * @param argTypes 引数の型の配列
	 * @param str 変換対象の文字列
	 * @return 文字列から変換した引数オブジェクトの配列
	 * @throws ReflectiveOperationException ラッパークラスの配列をプリミティブ型の配列に変換できない場合
	 * @throws IllegalArgumentException 文字列が引数に変換できない場合
	 */
	private Object[] stringToArgs(Class<?>[] argTypes, String str)
		throws ReflectiveOperationException, IllegalArgumentException
	{
		String[] words = str.split(",", -1);
		Object[] args = new Object[argTypes.length];
		
		// 引数が0の場合
		if(args.length == 0)
		{
			if((words.length == 1) && (words[0].matches("\\s*")))
			{
				return null;
			}
			else
			{
				throw new IllegalArgumentException();
			}
		}
		
		// 文字列内の要素数が引数の数と一致していない場合
		if(words.length != args.length)
		{
			throw new IllegalArgumentException();
		}
		
		for(int i = 0; i < args.length; i++)
		{
			args[i] = stringToObject(argTypes[i], words[i]);
		}

		return args;
	}
	
	/**
	 * 文字列をオブジェクトに変換します
	 * @param argType 変換するオブジェクトの型
	 * @param word 変換する文字列
	 * @return 文字列から変換したオブジェクト
	 * @throws ReflectiveOperationException ラッパークラスの配列にnullが含まれていて組み込み型に変換できない場合
	 * @throws IllegalArgumentException 文字列がObjectに変換できない場合
	 */
	private final Object stringToObject(Class<?> argType, String word)
		throws ReflectiveOperationException, IllegalArgumentException
	{
		if(word.length() == 0)
		{
			throw new IllegalArgumentException();
		}
		else if(word.equals("null"))
		{
			return null;
		}
		else
		{
			try
			{
				Pattern p = Pattern.compile(REFERENCE_PATTERN);
				Matcher m = p.matcher(word);
				
				if(m.matches())		// 生成済みインスタンスの場合
				{
					String[] subWords = word.split("#", 0);
					int index = Integer.parseInt(subWords[1]);
					Object argObject = null;
					
					if(subWords.length >= 3)	// 配列要素を対象としている場合
					{
						if(objectManager.getObjectAt(index).getClass().isArray())
						{
							int subIndex = Integer.parseInt(subWords[2]);
							argObject = Array.get(objectManager.getObjectAt(index), subIndex);
						}
						else
						{
							throw new IllegalArgumentException();
						}
					}
					else
					{
						argObject = objectManager.getObjectAt(index);
					}

					// 引数の型が配列かつ組み込み型の場合
					if(argType.isArray() && argType.getComponentType().isPrimitive())
					{
						// 引数用オブジェクトの配列の型を組み込み型に変換(型の一致は確認しない)
						try
						{
							argObject = Utilities.getPrimitiveArray(argType.getComponentType(), argObject);
						}
						catch(IllegalArgumentException e)
						{
							throw new IllegalArgumentException();
						}
						catch(NullPointerException e)	// ラッパークラス->組み込み型への変換に失敗した場合
						{
							throw new ReflectiveOperationException();
						}
					}
					
					return argObject;
				}
				else	// 組み込み型のラッパークラスの場合
				{
					return getWrapperObjectFromString(argType, word);
				}
			}
			catch(IndexOutOfBoundsException | IllegalArgumentException e)
			{
				throw new IllegalArgumentException();
			}
		}
	}
	
	/**
	 * 文字列をラッパークラスのオブジェクトに変換します.
	 * @param argType ラッパークラスの型
	 * @param word 文字列
	 * @return 文字列から変換したラッパークラスのオブジェクト
	 */
	private static Object getWrapperObjectFromString(Class<?> argType, String word)
	{
		try
		{
			if((argType == Boolean.class) || (argType == boolean.class))
			{
				return Boolean.parseBoolean(word.replaceAll("\\s+", ""));
			}
			else if((argType == Byte.class) || (argType == byte.class))
			{
				return Byte.parseByte(word.replaceAll("\\s+", ""));
			}
			else if((argType == Short.class) || (argType == short.class))
			{
				return Short.parseShort(word.replaceAll("\\s+", ""));
			}
			else if((argType == Integer.class) || (argType == int.class))
			{
				return Integer.parseInt(word.replaceAll("\\s+", ""));
			}
			else if((argType == Long.class) || (argType == long.class))
			{
				return Long.parseLong(word.replaceAll("\\s+", ""));
			}
			else if((argType == Float.class) || (argType == float.class))
			{
				return Float.parseFloat(word.replaceAll("\\s+", ""));
			}
			else if((argType == Double.class) || (argType == double.class))
			{
				return Double.parseDouble(word.replaceAll("\\s+", ""));
			}
			else if((argType == Character.class) || (argType == char.class))
			{
				if(word.matches("\\s*\'.\'\\s*"))
				{	
					return word.charAt(word.indexOf('\'') + 1);
				}
				else
				{
					throw new IllegalArgumentException();
				}
			}
			else if(argType == String.class)
			{
				if(word.matches("\\s*\".*\"\\s*"))
				{
					return (word.length() == 2)
						? "" : word.substring(word.indexOf('\"') + 1, word.lastIndexOf('\"'));
				}
				else
				{
					throw new IllegalArgumentException();
				}
			}
			else
			{
				throw new IllegalArgumentException();
			}
		}
		catch(NumberFormatException e)
		{
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * 対象のインスタンスを変更した場合に呼び出します.
	 *   ・フィールド一覧の更新
	 *   ・メソッド一覧の更新
	 */
	private void targetObjectChanged()
		throws NullPointerException
	{
		Class<?> cls = targetObject.getClass();
		Field[] fields = ObjectManager.getAllFields(cls, notPublicField);
		Method[] methods = ObjectManager.getAllMethods(cls, notPublicMethod);
		
		String[] fieldStrings = new String[fields.length];
		String[] methodStrings = new String[methods.length];

		for(int i = 0; i < fields.length; i++)
		{
			fieldStrings[i] = Utilities.getFieldString(fields[i]);
		}
		
		for(int i = 0; i < methods.length; i++)
		{
			methodStrings[i] = Utilities.getMethodString(methods[i]);
		}
		
		list_Fields.setListData(fieldStrings);
		fieldList = new ArrayList<Field>(Arrays.asList(fields));
		
		list_Methods.setListData(methodStrings);
		methodList = new ArrayList<Method>(Arrays.asList(methods));
	}
	
	/**
	 * TextFieldに入力されているクラスの配列をインスタンス一覧に追加します.
	 * @param arrayLength 配列の長さ
	 * @throws ClassNotFoundException TextFieldのクラスが存在しない場合
	 */
	private void createArray(int arrayLength) throws ClassNotFoundException
	{
		Class<?> cls = Class.forName(textField_ClassName.getText());
		objectManager.createArray(cls, arrayLength);
		
		list_Objects.setListData(getObjectListStrings());
		label_ClassNotFound.setText("");
		label_ArrayCreateError.setText("");
		
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(actionCount++).append("]").append(" 配列生成\n")
			.append("    配列 : ").append(cls.getSimpleName())
			.append("[").append(arrayLength).append("]")
			.append("\n");
		
		textArea_Result.setText(sb.toString());
	}
}