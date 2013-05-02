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
	private JSlider slider;
	private WatermarkGUI mainGui;
	private JPanel sliderPanel;
	private JLabel sliderLabel;
	
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
		sliderPanel.setLayout(new GridLayout(2,0));		
		slider = new JSlider(JSlider.HORIZONTAL, 1, imageHandler.getMaxOverlaySize(), imageHandler.getMaxOverlaySize());
		slider.addChangeListener(this);
		sliderPanel.add(slider);
		add(sliderPanel);
		sliderLabel = new JLabel("Resize overlay", JLabel.CENTER);
		sliderPanel.add(sliderLabel);
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
        		
        		slider.setMaximum(imageHandler.getMaxOverlaySize());
        		slider.setValue(imageHandler.getMaxOverlaySize());
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
		imageHandler.changeOverlaySize(slider.getValue());
		mainGui.updateImageFrame();
	}
}
