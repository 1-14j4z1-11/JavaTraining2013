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
	
	private static final String CLASS_NOT_FOUND_TEXT = "*�N���X�����݂��܂���";
	private static final String ARGUMENT_ERROR_TEXT = "*�p�����[�^���s���ł�";
	private static final String FIELDVALUE_ERROR_TEXT = "*�p�����[�^���s���ł�";
	private static final String NULL_ERROR_TEXT = "*�C���X�^���X��null�ł�";
	private static final String CHANGE_VALUE_TOOLTIP_TEXT =
			"�l��ύX���܂�. �\�������l�Ɛݒ肷�鎞�̒l�̃t�H�[�}�b�g���Ⴄ���߁A�\�������l�����̂܂ܐݒ�ł��Ȃ��ꍇ������܂�.";
	private static final String FIELDVALUE_TOOLTIP_TEXT = 
			"\"#�ԍ�\"�Ő����ς݃C���X�^���X�������Ɏw��ł��܂�(�z��v�f�̏ꍇ��\"#�C���X�^���X�ԍ�#�z��v�f�ԍ�\"�Ŏw��).";
	private static final String PARAMETER_TOOLTIP_TEXT = "�J���}��؂�ŕ����̈������w��ł��܂�. " + FIELDVALUE_TOOLTIP_TEXT;
	
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
		
		// Drag&Drop�p
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
		
		groupBox_Class.setTitle("�C���X�^���X����");
		this.add(groupBox_Class);
		
		label_Class.setText("�N���X��");
		groupBox_Class.add(label_Class);
		
		groupBox_Class.add(textField_ClassName);

		label_ClassNotFound.setForeground(ERROR_TEXT_COLOR);
		groupBox_Class.add(label_ClassNotFound);
		
		
		groupBox_Constructor.setTitle("�R���X�g���N�^���s");
		groupBox_Class.add(groupBox_Constructor);
		
		scrollPane_ConstructorList.getViewport().setView(list_Constructor);
		groupBox_Constructor.add(scrollPane_ConstructorList);
		
		button_ConstructorList.setText("�ꗗ�擾");
		button_ConstructorList.addActionListener(new ActionListener_ConstructorList());
		button_ConstructorList.setToolTipText("�R���X�g���N�^�ꗗ���擾���܂�.");
		groupBox_Constructor.add(button_ConstructorList);

		label_ConstructorParameter.setText("����");
		label_ConstructorParameter.setToolTipText(PARAMETER_TOOLTIP_TEXT);
		groupBox_Constructor.add(label_ConstructorParameter);

		groupBox_Constructor.add(textField_ConstructorParameter);

		button_CreateObject.setText("���s");
		button_CreateObject.addActionListener(new ActionListener_CreateObject());
		button_CreateObject.setToolTipText("�I������Ă���R���X�g���N�^�����s���܂�.");
		groupBox_Constructor.add(button_CreateObject);

		label_ConstructorError.setForeground(ERROR_TEXT_COLOR);
		groupBox_Constructor.add(label_ConstructorError);
		
		
		groupBox_Array.setTitle("�z�񐶐�");
		groupBox_Class.add(groupBox_Array);
		
		label_ArrayLength.setText("����");
		groupBox_Array.add(label_ArrayLength);
		
		spinner_ArrayLength.setModel(new SpinnerNumberModel(1, 1, 100, 1));
		spinner_ArrayLength.setEditor(new JSpinner.NumberEditor(spinner_ArrayLength, "0"));
		groupBox_Array.add(spinner_ArrayLength);
		
		button_CreateNullArray.setText("��z��");
		button_CreateNullArray.addActionListener(new ActionListener_CreateNullArray());
		button_CreateNullArray.setToolTipText("�w�肵�������̗v�f��null�̔z��𐶐����܂�.");
		groupBox_Array.add(button_CreateNullArray);

		label_ArrayParameter.setText("�����l");
		label_ArrayParameter.setToolTipText(PARAMETER_TOOLTIP_TEXT);
		groupBox_Array.add(label_ArrayParameter);

		groupBox_Array.add(textField_ArrayParameter);
		
		button_CreateInitArray.setText("�ݒ�ςݔz��");
		button_CreateInitArray.setToolTipText("�w�肵�������ŗv�f���ݒ肳�ꂽ�z��𐶐����܂�.");
		button_CreateInitArray.addActionListener(new ActionListener_CreateInitArray());
		groupBox_Array.add(button_CreateInitArray);

		label_ArrayCreateError.setForeground(ERROR_TEXT_COLOR);
		groupBox_Array.add(label_ArrayCreateError);

		
		// 
		
		groupBox_Objects.setTitle("�C���X�^���X�ꗗ");
		this.add(groupBox_Objects);
		
		scrollPane_List_Objects.getViewport().setView(list_Objects);
		groupBox_Objects.add(scrollPane_List_Objects);
		

		// 
		
		groupBox_ArrayObjects.setTitle("�z��v�f�ꗗ");
		this.add(groupBox_ArrayObjects);

		label_ArrayName.setText("�Ώ�");
		label_ArrayName.setToolTipText(FIELDVALUE_TOOLTIP_TEXT);
		groupBox_ArrayObjects.add(label_ArrayName);

		textField_ArrayName.setEditable(false);
		groupBox_ArrayObjects.add(textField_ArrayName);

		button_GetArray.setText("�擾");
		button_GetArray.addActionListener(new ActionListener_GetArray());
		button_GetArray.setToolTipText("�C���X�^���X�ꗗ�őI������Ă���z��̗v�f���擾���܂�.");
		groupBox_ArrayObjects.add(button_GetArray);
		
		list_ArrayObjects.addListSelectionListener(new ListSelectionListener_ArrayObjects());
		scrollPane_List_ArrayObjects.getViewport().setView(list_ArrayObjects);
		groupBox_ArrayObjects.add(scrollPane_List_ArrayObjects);

		label_ArrayValue.setText("�l");
		label_ArrayValue.setToolTipText(FIELDVALUE_TOOLTIP_TEXT);
		groupBox_ArrayObjects.add(label_ArrayValue);

		groupBox_ArrayObjects.add(textField_ArrayValue);

		button_ChangeArrayValue.setText("�ύX");
		button_ChangeArrayValue.addActionListener(new ActionListener_ChangeArrayObject());
		button_ChangeArrayValue.setToolTipText("�I������Ă���z��̗v�f��ύX���܂�.");
		groupBox_ArrayObjects.add(button_ChangeArrayValue);

		label_ArrayValueError.setForeground(ERROR_TEXT_COLOR);
		groupBox_ArrayObjects.add(label_ArrayValueError);


		//

		groupBox_ObjectData.setTitle("�C���X�^���X���");
		this.add(groupBox_ObjectData);
		
		label_ObjectName.setText("�ΏۃC���X�^���X");
		groupBox_ObjectData.add(label_ObjectName);
		
		textField_ObjectName.setEditable(false);
		groupBox_ObjectData.add(textField_ObjectName);

		button_GetObjectData.setText("�擾");
		button_GetObjectData.setToolTipText("�C���X�^���X�ꗗ�őI������Ă���C���X�^���X�̏����擾���܂�.");
		button_GetObjectData.addActionListener(new ActionListener_GetObject());
		groupBox_ObjectData.add(button_GetObjectData);

		button_GetArrayObjectData.setText("�z��v�f�擾");
		button_GetArrayObjectData.setToolTipText("�z��v�f�ꗗ�őI������Ă���C���X�^���X�̏����擾���܂�.");
		button_GetArrayObjectData.addActionListener(new ActionListener_GetArrayObject());
		groupBox_ObjectData.add(button_GetArrayObjectData);

		label_ObjectError.setForeground(ERROR_TEXT_COLOR);
		groupBox_ObjectData.add(label_ObjectError);

		
		groupBox_Fields.setTitle("�t�B�[���h");
		groupBox_ObjectData.add(groupBox_Fields);
		
		list_Fields.addListSelectionListener(new ListSelectionListener_Fields());
		scrollPane_List_Fields.getViewport().setView(list_Fields);
		groupBox_Fields.add(scrollPane_List_Fields);

		label_FieldValue.setText("�l");
		label_FieldValue.setToolTipText(FIELDVALUE_TOOLTIP_TEXT);
		groupBox_Fields.add(label_FieldValue);

		groupBox_Fields.add(textField_FieldValue);

		button_ChangeFieldValue.setText("�ύX");
		button_ChangeFieldValue.addActionListener(new ActionListener_ChangeField());
		button_ChangeFieldValue.setToolTipText(CHANGE_VALUE_TOOLTIP_TEXT);
		groupBox_Fields.add(button_ChangeFieldValue);

		label_FieldError.setForeground(ERROR_TEXT_COLOR);
		groupBox_Fields.add(label_FieldError);
		

		groupBox_Methods.setTitle("���\�b�h");
		groupBox_ObjectData.add(groupBox_Methods);
		
		scrollPane_List_Methods.getViewport().setView(list_Methods);
		groupBox_Methods.add(scrollPane_List_Methods);

		label_MethodParameter.setText("����");
		label_MethodParameter.setToolTipText(PARAMETER_TOOLTIP_TEXT);
		groupBox_Methods.add(label_MethodParameter);
		
		groupBox_Methods.add(textField_MethodParameter);

		button_InvokeMethod.setText("���s");
		button_InvokeMethod.addActionListener(new ActionListener_InvokeMethod());
		button_InvokeMethod.setToolTipText("�I������Ă��郁�\�b�h�����s���܂�.");
		groupBox_Methods.add(button_InvokeMethod);

		button_InvokeMethod_GetObj.setText("���s+�擾");
		button_InvokeMethod_GetObj.addActionListener(new ActionListener_InvokeMethod());
		button_InvokeMethod_GetObj.setToolTipText("�I������Ă��郁�\�b�h�����s���ĕԂ�l���C���X�^���X�ꗗ�ɒǉ����܂�.");
		groupBox_Methods.add(button_InvokeMethod_GetObj);

		label_MethodError.setForeground(ERROR_TEXT_COLOR);
		groupBox_Methods.add(label_MethodError);
		
		
		//
		
		groupBox_Result.setTitle("���s����");
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
				sb.append("[").append(actionCount++).append("]").append(" �R���X�g���N�^���s\n");
				
				if(rtn.isException())
				{
					sb.append("    �Ԃ�l : �Ȃ�(�C���X�^���X�������s)\n")
						.append("    ��O   : ").append(rtn.getException().toString())
						.append("\n\n");
				}
				else
				{
					sb.append("    �Ԃ�l : �Ȃ�(�C���X�^���X��������)\n")
						.append("    ��O   : �Ȃ�\n\n");
				}

				textArea_Result.setText(sb.toString());
				label_ConstructorError.setText("");
			}
			catch(ArrayIndexOutOfBoundsException ex)
			{
				label_ConstructorError.setText("*�R���X�g���N�^���I������Ă��܂���");
			}
			catch(NullPointerException ex)
			{
				if(list_Constructor.getSelectedIndex() == -1)
					label_ConstructorError.setText("*�R���X�g���N�^���I������Ă��܂���");
				else
					label_ConstructorError.setText(ARGUMENT_ERROR_TEXT);
			}
			catch(IllegalArgumentException ex)
			{
				label_ConstructorError.setText(ARGUMENT_ERROR_TEXT);
			}
			catch(InstantiationException ex)
			{
				label_ConstructorError.setText("*�R���X�g���N�^�Ăяo���Ɏ��s���܂���");
			}
			catch (ReflectiveOperationException e1)	// �z��v�f��null������A�g�ݍ��݌^�ɕϊ��ł��Ȃ��ꍇ
			{
				label_ConstructorError.setText("*�z���null���܂܂�Ă��܂�");
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
			catch(IllegalArgumentException ex)	// ������->�I�u�W�F�N�g�z��̕ϊ��Ɏ��s
			{
				label_ArrayCreateError.setText(ARGUMENT_ERROR_TEXT);
			}
			catch (ReflectiveOperationException ex)	// �z��v�f��null������A�g�ݍ��݌^�ɕϊ��ł��Ȃ��ꍇ
			{
				label_ArrayCreateError.setText("*�z���null���܂܂�Ă��܂�");
			}
		}
	}

	private class ActionListener_GetArray implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// �z��v�f�̃��X�g���X�V
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
				
				label_ObjectError.setText("*�C���X�^���X���I������Ă��܂���");
			}
			catch(NullPointerException ex)	// �I��v�f��null�̏ꍇ
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
				
				// ��̂��̌Ăяo�����Ɣz��v�f�̃C���X�^���X�`�����\�������
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
				
				label_ObjectError.setText("*�z��v�f���I������Ă��܂���");
			}
			catch(NullPointerException ex)	// �I��v�f��null�̏ꍇ
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
				sb.append("[").append(actionCount++).append("]").append(" �z��v�f�ύX\n")
					.append("    �z�� : ").append(targetArray.getClass().getSimpleName()).append("\n")
					.append("    Index : ").append(index)
					.append("\n\n");
				
				refleshArrayObjectList(targetArrayIndex);	// �z��v�f�̃��X�g���X�V
				
				textArea_Result.setText(sb.toString());
				label_ObjectError.setText("");
			}
			catch(NullPointerException ex)
			{
				label_ArrayValueError.setText("*�z�񂪑I������Ă��܂���");
			}
			catch(IndexOutOfBoundsException ex)	// �z����v�f���I������Ă��Ȃ��ꍇ
			{
				label_ArrayValueError.setText("*�v�f���I������Ă��܂���");
			}
			catch(IllegalArgumentException ex)
			{
				label_ArrayValueError.setText("*�ݒ�l���s���ł�");
			}
			catch (ReflectiveOperationException e1)	// �z��v�f��null������A�g�ݍ��݌^�ɕϊ��ł��Ȃ��ꍇ
			{
				label_ArrayValueError.setText("*�z���null���܂܂�Ă��܂�");
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
				sb.append("[").append(actionCount++).append("]").append(" �t�B�[���h�ύX\n")
					.append("    �t�B�[���h : ").append(field.getName())
					.append("\n\n");
				
				textArea_Result.setText(sb.toString());
				label_FieldError.setText("");
				
				// �z��v�f�̓��e��ύX�����ꍇ�ɔz��v�f�ꗗ���X�V����
				// 2�s�ڂŔz��v�f�łȂ��ꍇ�ɐݒ肳���G���[���b�Z�[�W���폜
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
					label_FieldError.setText("*�t�B�[���h���I������Ă��܂���");
				else
					label_FieldError.setText(FIELDVALUE_ERROR_TEXT);
			}
			catch(IllegalArgumentException ex)
			{
				label_FieldError.setText(FIELDVALUE_ERROR_TEXT);
			}
			catch(IllegalAccessException ex)
			{
				label_FieldError.setText("*�ύX�s�\�ȃt�B�[���h�ł�");
			}
			catch (ReflectiveOperationException e1)	// �z��v�f��null������A�g�ݍ��݌^�ɕϊ��ł��Ȃ��ꍇ
			{
				label_FieldError.setText("*�z���null���܂܂�Ă��܂�");
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
				sb.append("[").append(actionCount++).append("]").append(" ���\�b�h���s\n").append("    �Ԃ�l : ");
				
				if(method.getReturnType() == void.class)
				{
					sb.append("(void)");
				}
				else if(rtn.isException())
				{
					sb.append("�Ȃ�");
				}
				else
				{
					sb.append(Utilities.getObjectString(rtn.getReturnObject()))
						.append(" (")
						.append(rtn.getReturnObject().getClass().getSimpleName())
						.append(")");
				}
				
				sb.append("\n").append("    ��O   : ")
					.append(rtn.isException() ? rtn.getException().toString() : "�Ȃ�")
					.append("\n\n");

				textArea_Result.setText(sb.toString());
				label_MethodError.setText("");
				
				// �C���X�^���X�ꗗ�ɒǉ�
				if(e.getSource() == button_InvokeMethod_GetObj)
				{
					if(rtn.getReturnObject() != null)
					{
						objectManager.addObject(rtn.getReturnObject());
						list_Objects.setListData(getObjectListStrings());
					}
					else
					{
						label_MethodError.setText("(�Ԃ�l������܂���)");
					}
				}

				// �z��v�f�̓��e��ύX�����ꍇ�ɔz��v�f�ꗗ���X�V����
				// 2�s�ڂŔz��v�f�łȂ��ꍇ�ɐݒ肳���G���[���b�Z�[�W���폜
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
					label_MethodError.setText("*���\�b�h���I������Ă��܂���");
				else
					label_MethodError.setText(ARGUMENT_ERROR_TEXT);
			}
			catch(IllegalArgumentException ex)
			{
				label_MethodError.setText(ARGUMENT_ERROR_TEXT);
			}
			catch (ReflectiveOperationException e1)	// �z��v�f��null������A�g�ݍ��݌^�ɕϊ��ł��Ȃ��ꍇ
			{
				label_MethodError.setText("*�z���null���܂܂�Ă��܂�");
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
	 * �z��̗v�f���X�g�̕\�����e���X�V���܂�.
	 * @param selectedArrayIndex �I������Ă���C���X�^���X�ꗗ�̃C���f�b�N�X
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
			
			label_ArrayValueError.setText("*�z�񂪑I������Ă��܂���");
		}
	}
	
	/**
	 * �C���X�^���X�ꗗ�̕�����z����擾���܂�.
	 * @return �C���X�^���X�ꗗ�̕�����z��
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
	 * �I������Ă���C���X�^���X�̔z��v�f�ꗗ�̕�����z����擾���܂�.
	 * ������̓C���X�^���X����Ɏ擾���܂�.
	 * @return �z��v�f�̕�����z��
	 * @throws ReflectiveOperationException �z�񂪑I������Ă��Ȃ��ꍇ
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
	 * �I������Ă���C���X�^���X�̔z��v�f�ꗗ�̕�����z����擾���܂�.
	 * ������̓N���X����Ɏ擾���܂�.
	 * @return �z��v�f�̕�����z��
	 * @throws ReflectiveOperationException �z�񂪑I������Ă��Ȃ��ꍇ
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
	 * ������������̔z��ɕϊ����܂�.
	 * @param argTypes �����̌^�̔z��
	 * @param str �ϊ��Ώۂ̕�����
	 * @return �����񂩂�ϊ����������I�u�W�F�N�g�̔z��
	 * @throws ReflectiveOperationException ���b�p�[�N���X�̔z����v���~�e�B�u�^�̔z��ɕϊ��ł��Ȃ��ꍇ
	 * @throws IllegalArgumentException �����񂪈����ɕϊ��ł��Ȃ��ꍇ
	 */
	private Object[] stringToArgs(Class<?>[] argTypes, String str)
		throws ReflectiveOperationException, IllegalArgumentException
	{
		String[] words = str.split(",", -1);
		Object[] args = new Object[argTypes.length];
		
		// ������0�̏ꍇ
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
		
		// ��������̗v�f���������̐��ƈ�v���Ă��Ȃ��ꍇ
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
	 * ��������I�u�W�F�N�g�ɕϊ����܂�
	 * @param argType �ϊ�����I�u�W�F�N�g�̌^
	 * @param word �ϊ����镶����
	 * @return �����񂩂�ϊ������I�u�W�F�N�g
	 * @throws ReflectiveOperationException ���b�p�[�N���X�̔z���null���܂܂�Ă��đg�ݍ��݌^�ɕϊ��ł��Ȃ��ꍇ
	 * @throws IllegalArgumentException ������Object�ɕϊ��ł��Ȃ��ꍇ
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
				
				if(m.matches())		// �����ς݃C���X�^���X�̏ꍇ
				{
					String[] subWords = word.split("#", 0);
					int index = Integer.parseInt(subWords[1]);
					Object argObject = null;
					
					if(subWords.length >= 3)	// �z��v�f��ΏۂƂ��Ă���ꍇ
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

					// �����̌^���z�񂩂g�ݍ��݌^�̏ꍇ
					if(argType.isArray() && argType.getComponentType().isPrimitive())
					{
						// �����p�I�u�W�F�N�g�̔z��̌^��g�ݍ��݌^�ɕϊ�(�^�̈�v�͊m�F���Ȃ�)
						try
						{
							argObject = Utilities.getPrimitiveArray(argType.getComponentType(), argObject);
						}
						catch(IllegalArgumentException e)
						{
							throw new IllegalArgumentException();
						}
						catch(NullPointerException e)	// ���b�p�[�N���X->�g�ݍ��݌^�ւ̕ϊ��Ɏ��s�����ꍇ
						{
							throw new ReflectiveOperationException();
						}
					}
					
					return argObject;
				}
				else	// �g�ݍ��݌^�̃��b�p�[�N���X�̏ꍇ
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
	 * ����������b�p�[�N���X�̃I�u�W�F�N�g�ɕϊ����܂�.
	 * @param argType ���b�p�[�N���X�̌^
	 * @param word ������
	 * @return �����񂩂�ϊ��������b�p�[�N���X�̃I�u�W�F�N�g
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
	 * �Ώۂ̃C���X�^���X��ύX�����ꍇ�ɌĂяo���܂�.
	 *   �E�t�B�[���h�ꗗ�̍X�V
	 *   �E���\�b�h�ꗗ�̍X�V
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
	 * TextField�ɓ��͂���Ă���N���X�̔z����C���X�^���X�ꗗ�ɒǉ����܂�.
	 * @param arrayLength �z��̒���
	 * @throws ClassNotFoundException TextField�̃N���X�����݂��Ȃ��ꍇ
	 */
	private void createArray(int arrayLength) throws ClassNotFoundException
	{
		Class<?> cls = Class.forName(textField_ClassName.getText());
		objectManager.createArray(cls, arrayLength);
		
		list_Objects.setListData(getObjectListStrings());
		label_ClassNotFound.setText("");
		label_ArrayCreateError.setText("");
		
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(actionCount++).append("]").append(" �z�񐶐�\n")
			.append("    �z�� : ").append(cls.getSimpleName())
			.append("[").append(arrayLength).append("]")
			.append("\n");
		
		textArea_Result.setText(sb.toString());
	}
}