package controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.Main;
import model.imageIO;

public class WatermarkGUI{
	private JFrame main;
	private imageIO imageHandler;
	private OptionsPanel optionsPanel;
	private JLabel picLabel;
	private Dimension dimension;

	public WatermarkGUI(){
		imageHandler = new imageIO();
		main = new JFrame("Watermarker");
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		optionsPanel = new OptionsPanel(imageHandler, this);
		picLabel = new JLabel();
		
		picLabel.addMouseListener(new MouseListener() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	        	imageHandler.combineImages(e.getX()-(imageHandler.getOverlaySize()/2), e.getY()-(imageHandler.getOverlaySize()/2));
	        	updateImageFrame();
	        }
	        
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
	    });
		
		picLabel.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent e) {
				imageHandler.combineImages(e.getX()-(imageHandler.getOverlaySize()/2), e.getY()-(imageHandler.getOverlaySize()/2));
	        	updateImageFrame();
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		dimension = Toolkit.getDefaultToolkit().getScreenSize();      
				
		main.add(optionsPanel, BorderLayout.PAGE_START);
		main.add(picLabel, BorderLayout.CENTER);
		main.pack();
		centerGUI();
		main.setVisible(true);
	}
	
	public void updateImageFrame(){
		if(Main.DEBUG){
			System.out.println("Update image...");
		}
		
		picLabel.setIcon(new ImageIcon(imageHandler.getCombined()));
		main.pack();
		main.repaint();
	}
	
	public void centerGUI(){
		main.setLocation((int) ((dimension.getWidth()/2)-(main.getWidth()/2)), (int)((dimension.getHeight()/2)-(main.getHeight())/2));
	}
	
	public JFrame getMain(){
		return main;
	}
}
