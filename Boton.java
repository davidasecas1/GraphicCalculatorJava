package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class Boton extends JButton{
	private Color bg=new Color(240,240,240);
	private Color hov=new Color(224,224,224);
	private Color active=new Color(204,229,255);
	private Color hovActive=new Color(153,204,255);
	private boolean activeB;
	public Boton(String t){
		this.setText(t);
		det();
	}
	public Boton(){
		det();
	}
	private void det(){
		this.setBackground(bg);
		this.setBorderPainted(false);
		this.setFont(new Font("Arial",Font.BOLD,50));
		activeB=false;
		class hover implements MouseListener{
			@Override
			public void mouseEntered(MouseEvent e) {
				JButton a=(JButton) e.getSource();
				if(!activeB){
					a.setBackground(hov);
				}else{
					a.setBackground(hovActive);
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				JButton a=(JButton) e.getSource();
				if(!activeB){
					a.setBackground(bg);
				}else{
					a.setBackground(active);
				}
			}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			@Override
			public void mouseClicked(MouseEvent arg0) {}
			
		}
		this.addMouseListener(new hover());
	}
	public void setTextSize(int size){
		this.setFont(new Font("Arial",Font.BOLD,size));
	}
	public void setActive(){
		this.setBackground(active);
		activeB=true;
	}
	public void setInactive(){
		this.setBackground(bg);
		activeB=false;
	}
}
