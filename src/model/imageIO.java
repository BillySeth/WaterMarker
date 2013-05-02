package model;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class imageIO {
	
	BufferedImage image;
	BufferedImage originalOverlay;
	BufferedImage newOverlay;
	BufferedImage combined;
	File fileName;
	private int overlayX;
	private int overlayY;
	private int overlaySize;
	private int maxOverlaySize;
	private Graphics2D g;
	private AlphaComposite alpha;
	
	public imageIO(){
		URL overlayURL = getClass().getResource("/overlay.png");
		
		overlayX = 0;
		overlayY = 0;

		try {
			originalOverlay = ImageIO.read(overlayURL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	    alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0F);
	    
		maxOverlaySize = originalOverlay.getHeight();
	}
	
	public void readFiles(){
			if(Main.DEBUG){
				System.out.println("Reading image files...");
			}

			try {
				image = ImageIO.read(fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			combined = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			g = (Graphics2D) combined.getGraphics();
			maxOverlaySize = Math.min(image.getHeight(), image.getWidth());
						
			changeOverlaySize(maxOverlaySize);
	}
	
	public void changeOverlaySize(int newSize){
		newOverlay = resizeOverlay(newSize);
	}
	
	public void changeOpacity(float newOpacity){
		alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, newOpacity);
		combineImages(overlayX, overlayY);
	}
	
	private BufferedImage resizeOverlay(int size){
		if(Main.DEBUG){
			System.out.println("Resize image... " + size);
		}
	    int imageWidth  = originalOverlay.getWidth();
	    int imageHeight = originalOverlay.getHeight();

	    double scaleX = (double)size/imageWidth;
	    double scaleY = (double)size/imageHeight;
	    AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
	    AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);
	    combineImages(overlayX, overlayY);

	    return bilinearScaleOp.filter(
	        originalOverlay,
	        new BufferedImage(size, size, originalOverlay.getType()));
	}
	
	public BufferedImage getCombined(){
		return combined;
	}
	
	public void combineImages(int x, int y){
		if(Main.DEBUG){
			System.out.println("Combining files...");
		}
			overlayX = x;
			overlayY = y;
			
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0F));
			g.drawImage(image, 0, 0, null);
			g.setComposite(alpha);
			g.drawImage(newOverlay, overlayX, overlayY, null);
	}
	
	public void saveImage(){
		if(Main.DEBUG){
			System.out.println("Saving file...");
		}
		try {
			ImageIO.write(combined, "PNG", new File(fileName.getParentFile(), "wm-"+fileName.getName()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public File getFileName() {
		return fileName;
	}

	public void setFileName(File fileName) {
		this.fileName = fileName;
	}

	public int getOverlaySize() {
		overlaySize = newOverlay.getHeight();
		return overlaySize;
	}

	public int getMaxOverlaySize() {
		return maxOverlaySize;
	}
}
