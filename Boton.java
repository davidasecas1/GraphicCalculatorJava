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
		class hover implements MouseListener{
			@Override
			public void mouseEntered(MouseEvent e) {
				JButton a=(JButton) e.getSource();
				a.setBackground(hov);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				JButton a=(JButton) e.getSource();
				a.setBackground(bg);
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
}
