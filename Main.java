package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Main {

	public static void main(String[] args) {
		Ventana v=new Ventana();
		v.setVisible(true);
	}

}



class Ventana extends JFrame{

	public Ventana(){
		setSize(400,550);
		setLocationRelativeTo(null);
		setTitle("Calculator");
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Contenido c=new Contenido();
		add(c);
	}
}

class Contenido extends JPanel{
	JLabel background;
	JButton salir,prueba;
	JPanel up;
	//PAD
	JPanel pad;
	JButton b1,b2,b3;
	JButton b4,b5,b6;
	JButton b7,b8,b9;
	JButton bpa,b0,bpc;
	//......
	JTextField inputField;
	String input;
	float[] nums;
	char[] op;
	int Ipos,id,numOp;
	int[] pos;
	Color bg;
	public Contenido(){
		setLayout(null);
		setLayouts();
		setInputs();
		setPad();
		revalidate();
	}
	
	private void setLayouts(){
		bg=new Color(102,178,255);
		//bg=Toolkit.getDefaultToolkit().getImage("D:\\Eclipse\\Calculator\\src\\images\\bg.png");
		setBackground(bg);
		//background=new JLabel(new ImageIcon(bg));
		//background.setBounds(0, 0, 400, 550);
		//background.setOpaque(false);
		salir();
		//add(background);
		
	}
	
	private void setInputs(){
		input="";
		inputField=new JTextField();
		inputField.setBounds(15, 15, 300, 40);
		prueba=new JButton("=");
		prueba.setBounds(15, 60, 50, 20);
		add(inputField);
		add(prueba);
		class listenInputField implements DocumentListener{
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				input=(String)inputField.getText();
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				input=(String)inputField.getText();
			}
		}
		inputField.getDocument().addDocumentListener(new listenInputField());
		class resolver implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				getPosyOp();
				float res=getResult();
				System.out.println(res);
			}
			
		}
		prueba.addActionListener(new resolver());
	}
	
	private void setPad(){
		pad=new JPanel();
		GridLayout p=new GridLayout(4,3);
		p.setHgap(20);
		p.setVgap(5);
		pad.setLayout(p);
		pad.setBackground(bg);
		pad.setBounds(15, 120,300,300);
		add(pad);
		b1=new JButton("1"); b2=new JButton("2"); b3=new JButton("3");
		b4=new JButton("4"); b5=new JButton("5"); b6=new JButton("6");
		b7=new JButton("7"); b8=new JButton("8"); b9=new JButton("9");
		bpa=new JButton("("); b0=new JButton("0"); bpc=new JButton(")");
		
		pad.add(b1); pad.add(b2); pad.add(b3);
		pad.add(b4); pad.add(b5); pad.add(b6);
		pad.add(b7); pad.add(b8); pad.add(b9);
		pad.add(bpa); pad.add(b0); pad.add(bpc);
		
		class BotonPad implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==b1) input+="1"; if(e.getSource()==b2) input+="2"; if(e.getSource()==b3) input+="3";
				if(e.getSource()==b4) input+="4"; if(e.getSource()==b5) input+="5"; if(e.getSource()==b6) input+="6";
				if(e.getSource()==b7) input+="7"; if(e.getSource()==b8) input+="8"; if(e.getSource()==b9) input+="9";
				if(e.getSource()==bpa) input+="("; if(e.getSource()==b0) input+="0"; if(e.getSource()==bpc) input+=")";

				inputField.setText(input);
			}
		}
		b1.addActionListener(new BotonPad()); b2.addActionListener(new BotonPad()); b3.addActionListener(new BotonPad());
		b4.addActionListener(new BotonPad()); b5.addActionListener(new BotonPad()); b6.addActionListener(new BotonPad());
		b7.addActionListener(new BotonPad()); b8.addActionListener(new BotonPad()); b9.addActionListener(new BotonPad());
		bpa.addActionListener(new BotonPad()); b0.addActionListener(new BotonPad()); bpc.addActionListener(new BotonPad());
	}
	
	private void getPosyOp(){
		nums=new float[100];
		op=new char[100];
		pos=new int[100];
		for(int a=0;a<pos.length;a++){pos[a]=0;}
		id=0;
		Ipos=0;
		char c;
		boolean b=false;
		numOp=0;
		for(int i=0;i<input.length();i++){
			c=input.charAt(i);
			if(c=='+'||c=='-'||c=='*'||c=='/'){ // ARRAY OF KNOWN OPS
				op[id]=c;
				pos[id]=i;
				b=true;
				numOp++;
				id++;
			}
		}
		id=0;
		if(numOp!=0){
			while(id<=numOp){
				if(id==0){
					nums[id]=toFloat(input.substring(0, pos[id]));
				}else if(id==numOp){
					nums[id]=toFloat(input.substring(pos[id-1]+1,input.length()));
				}else{
					nums[id]=toFloat(input.substring(pos[id-1]+1,pos[id]));
				}
				id++;
			}
		}else{
			nums[id]=toFloat(input.substring(0, input.length()));
		}
	}
	private float getResult(){
		float res=0;
		float sum=0;
		id=0;
		while(id<=numOp){// MAKE PRIORITY ON BRACKETS AND * /
			if(id==0){
				res=nums[id];
			}else{
				if(op[id-1]=='+'){
					res+=nums[id];
				}else if(op[id-1]=='-'){
					res-=nums[id];
				}else if(op[id-1]=='*'){
					res*=nums[id];
				}else if(op[id-1]=='/'){
					res/=nums[id];
				}
			}
			id++;
		}
		return res;
	}
	
	
	private void salir(){
		salir=new JButton("Exit");
		salir.setBounds(335, 5,60,30);
		add(salir);
		class exit implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		}
		salir.addActionListener(new exit());
	}
	
	public float toFloat(String str){
		float res;
		if(str!=null && str!=""){
			res=Float.parseFloat(str);
		}else{
			res=0;
		}
		return res;
	}
	public String toString(int n){
		String res=Integer.toString(n);
		return res;
	}
}