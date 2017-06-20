package main;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Main {

	public static void main(String[] args) {
		Ventana v=new Ventana();
		v.setVisible(true);
	}
	int a=0;
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
	Boton salir,Bresultado,del;
	JPanel up;
	//PAD
	JPanel pad;
	Boton b1,b2,b3,badd,bdot;
	Boton b4,b5,b6,bdec;
	Boton b7,b8,b9,bmult;
	Boton bpa,b0,bpc,bdiv;
	//......
	//MEMORIAS
	JPanel mem;
	Boton bA,bB,bC,bD,bE;
	Boton bSto;
	float A,B,C,D,E;
	boolean Sto;
	//.......
	//CONSTANTES
	JPanel constantes;
	Boton bG, bMT,bRT,bUA,bh,bimpMag;
	Boton bg0,bLuz,be,bme,bmp,bmn;
	private float G,MT,RT,UA,h,impMag;
	private float g0,c,e,me,mp,mn; 
	//.......
	JTextField inputField;
	String input;
	float[] nums;
	char[] op;
	int id,numOp;
	int[] pos;
	int numParantesis;
	int[] posP;
	boolean[] paran;
	Color bg;
	public Contenido(){
		setLayout(null);
		setLayouts();
		setInputs();
		setExtraButtons();
		setPad();
		setMem();
		setConst();
		revalidate();
	}
	private void setLayouts(){
		//bg=new Color(102,178,255);
		bg=new Color(240,240,240);
		setBackground(bg);
		setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
		salir();
	}
	private void setInputs(){
		input="";
		inputField=new JTextField();
		inputField.setBounds(15, 15, 250, 40);
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
		del=new Boton("C");
		del.setBounds(275, 15, 50, 40);
		add(del);
		del.setTextSize(20);
		class Delete implements ActionListener{ //Class which acts when the button is clicked
			@Override
			public void actionPerformed(ActionEvent e) {
				if(input!="" && input!=null){ // ERROR
					//input=input.substring(0, input.length()-1);
					inputField.setText("");
				}
			}
		}
		del.addActionListener(new Delete());
		//RESOLVE BUTTON =
		Bresultado=new Boton("=");
		Bresultado.setBounds(295, 207, 70, 262);
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
		b1=new Boton("1"); b2=new Boton("2"); b3=new Boton("3"); badd=new Boton("+"); bdot=new Boton(".");
		b4=new Boton("4"); b5=new Boton("5"); b6=new Boton("6"); bdec=new Boton("-");
		b7=new Boton("7"); b8=new Boton("8"); b9=new Boton("9"); bmult=new Boton("*");
		bpa=new Boton("("); b0=new Boton("0"); bpc=new Boton(")"); bdiv=new Boton("/");
		int sh=15;
		pad.add(b1); pad.add(b2); pad.add(b3); pad.add(badd); pad.add(bdot);
		pad.add(b4); pad.add(b5);  pad.add(b6); pad.add(bdec); pad.add(Box.createHorizontalStrut(sh));
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
		
		
		b1.addActionListener(new BotonPad()); b2.addActionListener(new BotonPad()); b3.addActionListener(new BotonPad()); badd.addActionListener(new BotonPad()); bdot.addActionListener(new BotonPad());
		b4.addActionListener(new BotonPad()); b5.addActionListener(new BotonPad()); b6.addActionListener(new BotonPad()); bdec.addActionListener(new BotonPad());
		b7.addActionListener(new BotonPad()); b8.addActionListener(new BotonPad()); b9.addActionListener(new BotonPad()); bmult.addActionListener(new BotonPad());
		bpa.addActionListener(new BotonPad()); b0.addActionListener(new BotonPad()); bpc.addActionListener(new BotonPad()); bdiv.addActionListener(new BotonPad());
	}
	
	private void getPosyOp(){
		int n=100;
		nums=new float[n];
		op=new char[n];
		pos=new int[n];
		numParantesis=0;
		posP=new int[n];
		paran=new boolean[n];
		for(int a=0;a<pos.length;a++){pos[a]=0;}
		id=0;
		char c;
		String a="";
		numOp=0;
		for(int i=0;i<input.length();i++){
			c=input.charAt(i);
			if(c=='+'||c=='-'||c=='*'||c=='/'){ 
				op[id]=c;
				pos[id]=i;
				numOp++;
				id++;
			}
			if(c=='('){
				posP[id]=i;
				paran[id]=true;
			}else if(c==')'){
				posP[id]=i;
				paran[id]=false;
			}
		}
		id=0;
		if(numOp!=0){
			while(id<=numOp){
				
				if(id==0){ //FIRST VALUE
					if(posP[id]==0){
						id++;
					}
					a=input.substring(id, pos[0]);
					System.out.println(a);
				}else if(id==numOp){ //LAST VALUE
					a=input.substring(pos[id-1]+1,input.length());
				}else if(posP[id-1]==(id-1)){ 
					a=input.substring(posP[id-1]+1,pos[id]);
				}else if(posP[id+1]==(id+1)){
					a=input.substring(pos[id-1]+1,posP[id]);
				}else{
					a=input.substring(pos[id-1]+1,pos[id]);	
				}
				if(checkLetters(a)){
					assignLetter(a,id);
				}else{
					nums[id]=toFloat(a);
				}
				id++;
			}
		}else{
			a=input.substring(0, input.length());
			if(checkLetters(a)){
				assignLetter(a,id);
			}else{
				nums[id]=toFloat(a);
			}
		}
	}
	private void getResult(){
		getPosyOp();
		float res=0;
		id=0;
		while(id<=numOp){// MAKE PRIORITY ON BRACKETS
			if(id==0){
				if(res==0)res=nums[id];
			}else{
				if(op[id-1]=='*' || op[id-1]=='/'){
					if(op[id-1]=='*'){
						res*=nums[id];
					}else if(op[id-1]=='/'){
						res/=nums[id];
					}
				}
			}
			id++;
		} // I divided this into two whiles because of the priority of * and /
		id=0;
		while(id<=numOp){
			if(id==0){
				if(res==0)res=nums[id];
			}else{
				if(op[id-1]=='+' || op[id-1]=='-'){
					if(op[id-1]=='+'){
						res+=nums[id];
					}else if(op[id-1]=='-'){
						res-=nums[id];
					}
				}
			}
			id++;
		}
		input=toStringF(res);
		inputField.setText(input);
	}
	
	private void setMem(){
		mem=new JPanel();
		mem.setLayout(new GridLayout(1,5));
		mem.setBounds(15, 65, 350, 50);
		add(mem);
		setbSto();
		int tam=25;
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
		bSto=new Boton("Sto");
		bSto.setTextSize(15); 
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
		mn=exp(1.674927211,-27);
		
		constantes=new JPanel();
		constantes.setLayout(new GridLayout(2,6));
		constantes.setBounds(15, 480, 350, 50);
		add(constantes);
		
		int tam=15;
		
		bG=new Boton("G",tam); bMT=new Boton("MT",tam); bRT=new Boton("RT",tam);
		bUA=new Boton("UA",tam); bg0=new Boton("g0",tam); bLuz=new Boton("c",tam);
		bh=new Boton("h",tam); bimpMag=new Boton("Mu",tam); be=new Boton("e",tam);
		bme=new Boton("me",tam); bmp=new Boton("mp",tam); bmn=new Boton("mn",tam);
		
		constantes.add(bG); constantes.add(bMT); constantes.add(bRT);
		constantes.add(bUA); constantes.add(bg0); constantes.add(bLuz);
		constantes.add(bh); constantes.add(bimpMag); constantes.add(be);
		constantes.add(bme); constantes.add(bmp); constantes.add(bmn);
		
		
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
		bme.addActionListener(act); bmp.addActionListener(act); bmn.addActionListener(act);
		
	}
	
	private void salir(){
		salir=new Boton("Exit");
		salir.setBounds(335, 5,60,30);
		salir.setTextSize(14);
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
	private String ascii(int codigo){
		return Character.toString ((char) codigo);
	}
	
	//Checkers
	private boolean checkLetters(String a){
		return (a.equals("A") || a.equals("B") || a.equals("C")|| a.equals("D")|| a.equals("E")
				||a.equals("G") ||a.equals("MT") ||a.equals("RT") ||a.equals("UA") ||a.equals("h") ||a.equals("Mu") ||a.equals("g0")
				||a.equals("c") ||a.equals("e") ||a.equals("me") ||a.equals("mp") ||a.equals("mn"));
	}
	private void assignLetter(String a,int id){
		switch(a){
			case "A":
				nums[id]=A;
				break;
			case "B":
				nums[id]=B;
				break;
			case "C":
				nums[id]=C;
				break;
			case "D":
				nums[id]=D;
				break;
			case "E":
				nums[id]=E;
				break;
				
			case "G":
				nums[id]=G;
				break;
			case "MT":
				nums[id]=MT;
				break;
			case "RT":
				nums[id]=RT;
				break;
			case "UA":
				nums[id]=UA;
				break;
			case "g0":
				nums[id]=g0;
				break;
				
			case "h":
				nums[id]=h;
				break;
			case "Mu":
				nums[id]=impMag;
				break;
			case "c":
				nums[id]=c;
				break;
			case "e":
				nums[id]=e;
				break;
			case "me":
				nums[id]=me;
				break;
			case "mp":
				nums[id]=mp;
				break;
			case "mn":
				nums[id]=mn;
				break;
		}
	}
	
	
}
