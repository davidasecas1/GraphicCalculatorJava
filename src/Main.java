/**
 * @author David Espejo Antiñolo
 */
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
	private static final long serialVersionUID = 1L;
	private final ImageIcon img = new ImageIcon("icono.png");
	public Ventana(){
		setSize(380,570);
		setLocationRelativeTo(null);
		setTitle("Calculator");		
		setIconImage(img.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Contenido c=new Contenido();
		add(c);
	}
}
class Contenido extends JPanel{
	private static final long serialVersionUID = 1L;
	private Boton Bresultado,del,delUnit;
	//PAD
	JPanel pad;
	private Boton b1,b2,b3,badd,bpow;
	private Boton b4,b5,b6,bdec,bdot;
	private Boton b7,b8,b9,bmult;
	private Boton bpa,b0,bpc,bdiv;
	//......
	//MEMORIAS
	private JPanel mem;
	private Boton bA,bB,bC,bD,bE;
	private Boton bSto;
	private static float A,B,C,D,E;
	private boolean Sto;
	//.......
	//CONSTANTES
	private JPanel constantes;
	private Boton bG, bMT,bRT,bUA,bh,bimpMag;
	private Boton bg0,bLuz,be,bme,bmp,bpi;
	private static float G,MT,RT,UA,h,impMag;
	private static float g0,c,e,me,mp,pi; 
	//.......
	private JTextField inputField;
	private String input;
	private Color bg;
	private Calculo calc;
	private final int tam=34;
	public Contenido(){
		setLayout(null);
		setLayouts();
		setInputs();
		setExtraButtons();
		setPad();
		setMem();
		setConst();
		revalidate();
		repaint();
	}
	private void getResult(){
		calc=new Calculo(input);
		float res=calc.toResult(calc.toPostFix());
		input=toStringF(res);
		inputField.setText(input);
	}
	private void setLayouts(){
		bg=new Color(240,240,240);
		setBackground(bg);
		setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
	}
	private void setInputs(){
		input="";
		inputField=new JTextField();
		inputField.setBounds(15, 15, 220, 40);
		add(inputField);
		class listenInputField implements DocumentListener{
			@Override
			public void changedUpdate(DocumentEvent arg0) {}
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
	}
	private void setExtraButtons(){
		//DELETE BUTTON
		del=new Boton("C",9);
		del.setBounds(285, 15, 50, 40);
		add(del);
		class Delete implements ActionListener{ //Class which acts when the button is clicked
			@Override
			public void actionPerformed(ActionEvent e) {
				input="";
				inputField.setText(input);
			}
		}
		del.addActionListener(new Delete());
		delUnit=new Boton(uniCode('\u00AB'),12);
		delUnit.setBounds(235, 15, 50, 40);
		add(delUnit);
		class DeleteUnit implements ActionListener{ //Class which acts when the button is clicked
			@Override
			public void actionPerformed(ActionEvent e) {
				if(input!="" && input!=null){ // ERROR
					input=input.substring(0, input.length()-1);
					inputField.setText(input);
				}
			}
		}
		delUnit.addActionListener(new DeleteUnit());
		//RESOLVE BUTTON =
		Bresultado=new Boton("=",tam);
		Bresultado.setBounds(295, 295, 70, 174);
		add(Bresultado);
		class resolver implements ActionListener{ //Class which acts when the button is clicked
			@Override
			public void actionPerformed(ActionEvent e) {
				getResult();
			}
		}
		Bresultado.addActionListener(new resolver());
	}
	private void setPad(){
		pad=new JPanel();
		pad.setLayout(new GridLayout(4,4));
		pad.setBackground(bg);
		pad.setBounds(15, 120,350,350);
		add(pad);
		b1=new Boton("1",tam); b2=new Boton("2",tam); b3=new Boton("3",tam); badd=new Boton("+",tam); bpow=new Boton("^",tam);
		b4=new Boton("4",tam); b5=new Boton("5",tam); b6=new Boton("6",tam); bdec=new Boton("-",tam); bdot=new Boton(".",tam);
		b7=new Boton("7",tam); b8=new Boton("8",tam); b9=new Boton("9",tam); bmult=new Boton("*",tam);
		bpa=new Boton("(",tam); b0=new Boton("0",tam); bpc=new Boton(")",tam); bdiv=new Boton("/",tam);
		int sh=15;
		pad.add(b1); pad.add(b2); pad.add(b3); pad.add(badd); pad.add(bpow);
		pad.add(b4); pad.add(b5);  pad.add(b6); pad.add(bdec); pad.add(bdot);
		pad.add(b7); pad.add(b8);  pad.add(b9); pad.add(bmult); pad.add(Box.createHorizontalStrut(sh));
		pad.add(bpa); pad.add(b0); pad.add(bpc); pad.add(bdiv); pad.add(Box.createHorizontalStrut(sh));
		
		class BotonPad implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton a=(JButton) e.getSource();
				input+=a.getText();
				inputField.setText(input);
			}
		}
		BotonPad boton=new BotonPad();
		b1.addActionListener(boton); b2.addActionListener(boton); b3.addActionListener(boton); badd.addActionListener(boton); bpow.addActionListener(boton);
		b4.addActionListener(boton); b5.addActionListener(boton); b6.addActionListener(boton); bdec.addActionListener(boton); bdot.addActionListener(boton);
		b7.addActionListener(boton); b8.addActionListener(boton); b9.addActionListener(boton); bmult.addActionListener(boton);
		bpa.addActionListener(boton); b0.addActionListener(boton); bpc.addActionListener(boton); bdiv.addActionListener(boton); 
	}	
	private void setMem(){
		mem=new JPanel();
		mem.setLayout(new GridLayout(1,5));
		mem.setBounds(15, 65, 350, 50);
		add(mem);
		setbSto();
		int tam=20;
		bA=new Boton("A",tam); bB=new Boton("B",tam); bC=new Boton("C",tam); bD=new Boton("D",tam); bE=new Boton("E",tam);
		mem.add(bA); mem.add(bB); mem.add(bC); mem.add(bD); mem.add(bE);
		class actMemorias implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				e.getSource();
				Boton a=(Boton)e.getSource();
				String s=a.getText();
				if(Sto){
					getResult();
					switch(s){
						case "A":
							A=toFloat(inputField.getText());
							break;
						case "B":
							B=toFloat(inputField.getText());
							break;
						case "C":
							C=toFloat(inputField.getText());
							break;
						case "D":
							D=toFloat(inputField.getText());
							break;
						case "E":
							E=toFloat(inputField.getText());
							break;
					}
					Sto=false;
					bSto.setInactive();
				}else{
					input+=s;
					inputField.setText(input);
				}
			}
		}
		bA.addActionListener(new actMemorias()); bB.addActionListener(new actMemorias()); bC.addActionListener(new actMemorias());
		bD.addActionListener(new actMemorias()); bE.addActionListener(new actMemorias());
		
	}
	private void setbSto(){
		A=0;B=0;C=0;D=0;E=0;
		Sto=false;
		bSto=new Boton("Sto",9);
		mem.add(bSto);
		class actBSto implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Sto){
					Sto=false;
					bSto.setInactive();
				}else{
					Sto=true;
					bSto.setActive();
				}
			}
		}
		bSto.addActionListener(new actBSto());
	}
	
	private void setConst(){
		G=exp(6.67384,-11);
		MT=exp(5.97,24);
		RT=exp(6370,3);
		UA=150000000;
		g0=(float) 9.81;
		c=exp(3,8);
		h=exp(6.62606957,-34); 
		impMag=exp(12.56370614359,-7); 
		e=exp(1.602176565,-19);
		me=exp(9.10938216,-31);
		mp=exp(1.672621637,-27);
		pi=(float)Math.PI;
		
		constantes=new JPanel();
		constantes.setLayout(new GridLayout(2,6));
		constantes.setBounds(15, 480, 350, 50);
		add(constantes);
		
		int tam=15;
		
		bG=new Boton("G",tam); bMT=new Boton("M",tam); bRT=new Boton("R",tam);
		bUA=new Boton("U",tam); bg0=new Boton("g",tam); bLuz=new Boton("c",tam);
		bh=new Boton("h",tam); bimpMag=new Boton(uniCode('\u03BC'),tam); be=new Boton("e",tam);
		bme=new Boton("q",tam); bmp=new Boton("p",tam); bpi=new Boton(uniCode('\u03C0'),tam);
		
		constantes.add(bG); constantes.add(bMT); constantes.add(bRT);
		constantes.add(bUA); constantes.add(bg0); constantes.add(bLuz);
		constantes.add(bh); constantes.add(bimpMag); constantes.add(be);
		constantes.add(bme); constantes.add(bmp); constantes.add(bpi);
		
		
		class constAct implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				Boton a=(Boton)e.getSource();
				input+=(String)a.getText();
				inputField.setText(input);
			}
		}
		ActionListener act=new constAct();
		bG.addActionListener(act); bMT.addActionListener(act); bRT.addActionListener(act);
		bUA.addActionListener(act); bg0.addActionListener(act); bLuz.addActionListener(act);
		bh.addActionListener(act); bimpMag.addActionListener(act); be.addActionListener(act);
		bme.addActionListener(act); bmp.addActionListener(act); bpi.addActionListener(act);
		
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
	public int toInt(String str){
		int res;
		res=Integer.parseInt(str);
		return res;
	}
	public String toString(int num){
		String res=Integer.toString(num);
		return res;
	}
	public String toStringF(float num){
		String res=Float.toString(num);
		return res;
	}
	public float exp(double norm,float e){
		float res=(float)(norm*Math.pow(10,e));
		return res;
	}
	private String uniCode(char codigo){
		return Character.toString (codigo);
	}
	private static String uniCodeS(char codigo){
		return Character.toString (codigo);
	}
	
	//Checkers
	public static float getLettersValue(String a){
		float res=0;
		
		switch(a){
			case "A":
				res=A;
				break;
			case "B":
				res=B;
				break;
			case "C":
				res=C;
				break;
			case "D":
				res=D;
				break;
			case "E":
				res=E;
				break;
				
			case "G":
				res=G;
				break;
			case "M":
				res=MT;
				break;
			case "R":
				res=RT;
				break;
			case "U":
				res=UA;
				break;
			case "g":
				res=g0;
				break;
				
			case "h":
				res=h;
				break;
			case "c":
				res=c;
				break;
			case "e":
				res=e;
				break;
			case "q":
				res=me;
				break;
			case "p":
				res=mp;
				break;
		}
		if(a.equals(uniCodeS('\u03BC'))){
			res=impMag;
		}
		if(a.equals(uniCodeS('\u03C0'))){
			res=pi;
		}
		return res;
	}
}
