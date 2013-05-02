/*	 Copyright (C) 2013  Seth Phillips
 * 
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
