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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Main;
import model.imageIO;

public class OptionsPanel extends JPanel implements ActionListener, ChangeListener{

	private JButton openButton;
	private JFileChooser fileChooser;
	private imageIO imageHandler;
	private JButton saveButton;
	private JSlider sizeSlider;
	private WatermarkGUI mainGui;
	private JPanel sliderPanel;
	private JLabel sliderLabel;
	private JSlider opSlider;
	private JLabel opLabel;
	
	public OptionsPanel(imageIO imageHandler, WatermarkGUI mainGui){
		this.mainGui = mainGui;
		this.imageHandler = imageHandler;
		
		fileChooser = new JFileChooser();		
		openButton = new JButton("Select Image");
		openButton.addActionListener(this);
		saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		add(openButton);
		add(saveButton);
		
		sliderPanel = new JPanel();
		sliderPanel.setLayout(new GridLayout(2,2));	
		
		sliderLabel = new JLabel("Resize overlay", JLabel.CENTER);
		sliderPanel.add(sliderLabel);
		
		opLabel = new JLabel("Opacity", JLabel.CENTER);
		sliderPanel.add(opLabel);
		
		sizeSlider = new JSlider(JSlider.HORIZONTAL, 1, imageHandler.getMaxOverlaySize(), imageHandler.getMaxOverlaySize());
		sizeSlider.addChangeListener(this);
		sliderPanel.add(sizeSlider);
		
		opSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 100);
		opSlider.addChangeListener(this);
		sliderPanel.add(opSlider);
		
		add(sliderPanel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == openButton){
			int returnVal = fileChooser.showOpenDialog(mainGui.getMain());
			
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                imageHandler.setFileName(file);
                imageHandler.readFiles();
        		imageHandler.changeOverlaySize(imageHandler.getMaxOverlaySize());
        		imageHandler.combineImages(0, 0);
        		
        		sizeSlider.setMaximum(imageHandler.getMaxOverlaySize());
        		sizeSlider.setValue(imageHandler.getMaxOverlaySize());
                mainGui.updateImageFrame();
                mainGui.centerGUI();
            }
		}	
		
		if(e.getSource() == saveButton){
			imageHandler.saveImage();
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(Main.DEBUG){
			System.out.println("Slider changed...");
		}
	if(e.getSource() == sizeSlider){
		imageHandler.changeOverlaySize(sizeSlider.getValue());
		mainGui.updateImageFrame();
	}else if(e.getSource() == opSlider){
		imageHandler.changeOpacity((float) (opSlider.getValue()*.01));
		mainGui.updateImageFrame();
	}
	
	}
}
