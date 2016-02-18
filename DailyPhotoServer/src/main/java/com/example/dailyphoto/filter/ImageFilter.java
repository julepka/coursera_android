package com.example.dailyphoto.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageFilter {

	public void grayScale(String filename) {
		BufferedImage image;
		int width;
		int height;
		try {
			File input = new File(filename);
			image = ImageIO.read(input);
			width = image.getWidth();
			height = image.getHeight();
			for(int i=0; i<height; i++) {
				for(int j=0; j<width; j++) {
					Color c = new Color(image.getRGB(j, i));
					int red = (int)(c.getRed() * 0.299);
					int green = (int)(c.getGreen() * 0.587);
					int blue = (int)(c.getBlue() *0.114);
					Color newColor = new Color(red+green+blue,
					red+green+blue,red+green+blue);
					image.setRGB(j,i,newColor.getRGB());
				}
			}
			File ouptut = new File(filename);
			ImageIO.write(image, "jpg", ouptut);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sepia(String filename) {
		BufferedImage inputFile = null;
		try {
			inputFile = ImageIO.read(new File(filename));
			int width = inputFile.getWidth();
			int height = inputFile.getHeight();
			BufferedImage outImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					{
						int pic = inputFile.getRGB(i, j);
						int in_r = ((pic >> 16) & 0xFF);
						int in_g = ((pic >> 8) & 0xFF);
						int in_b = (pic & 0xFF);
						float out_r = (float) (((in_r * .393) + (in_g * .769) + (in_b * .189)) / 256);
						if (out_r > 1)
							out_r = 1;
						float out_g = (float) (((in_r * .349) + (in_g * .686) + (in_b * .168)) / 256);
						if (out_g > 1)
							out_g = 1;
						float out_b = (float) (((in_r * .272) + (in_g * .534) + (in_b * .131)) / 256);
						if (out_b > 1)
							out_b = 1;
						Color color = new Color(out_r, out_g, out_b);
						int RGB = color.getRGB();
						outImage.setRGB(i, j, RGB);
					}
				}
			}
			File outputFile = new File(filename);
			ImageIO.write(outImage, "jpg", outputFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void invertColors(String filename) {
		BufferedImage inputFile = null;
        try {
            inputFile = ImageIO.read(new File(filename));
            for (int x = 0; x < inputFile.getWidth(); x++) {
            	for (int y = 0; y < inputFile.getHeight(); y++) {
            		int rgba = inputFile.getRGB(x, y);
            		Color col = new Color(rgba, true);
            		col = new Color(255 - col.getRed(),
                                255 - col.getGreen(),
                                255 - col.getBlue());
            		inputFile.setRGB(x, y, col.getRGB());
            	}
            }
            File outputFile = new File(filename);
            ImageIO.write(inputFile, "jpg", outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public void brighten(String filename) {
		BufferedImage inputFile = null;
        try {
            inputFile = ImageIO.read(new File(filename));
            RescaleOp op = new RescaleOp(1.2f, 0, null);
            inputFile = op.filter(inputFile, inputFile);
            File outputFile = new File(filename);
            ImageIO.write(inputFile, "jpg", outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public void darken(String filename) {
		BufferedImage inputFile = null;
        try {
            inputFile = ImageIO.read(new File(filename));
            RescaleOp op = new RescaleOp(0.5f, 0, null);
            inputFile = op.filter(inputFile, inputFile);
            File outputFile = new File(filename);
            ImageIO.write(inputFile, "jpg", outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public void blur(String filename) {
		double[][] filter = { { 0,0,1,0,0 },
							  { 0,1,1,1,0 },
							  { 1,1,1,1,1 },
							  { 0,1,1,1,0 },
							  { 0,0,1,0,0 } };
		matrixFilter(filename, filter, 5, 5, 1.0/13.0, 0);
	}
	
	public void sharpen(String filename) {
		double[][] filter = { { -1,-1,-1,-1,-1 },
				  			  { -1,2,2,2,-1 },
				  			  { -1,2,8,2,-1 },
				  			  { -1,2,2,2,-1 },
				  			  { -1,-1,-1,-1,-1 } };
		matrixFilter(filename, filter, 5, 5, 1.0/8.0, 0);
	}
	
	public void matrixFilter(String filename, double[][] filter, int filterWidth, int filterHeight, 
							double factor, double bias) {
		BufferedImage inputFile = null;
        try {
            inputFile = ImageIO.read(new File(filename));
            for (int x = 0; x < inputFile.getWidth(); x++) {
            	for (int y = 0; y < inputFile.getHeight(); y++) {
            		int red = 0, green = 0, blue = 0;
            		for(int filterX = 0; filterX < filterWidth; filterX++) 
            	        for(int filterY = 0; filterY < filterHeight; filterY++) 
            	        { 
            	            int imageX = (x - filterWidth / 2 + filterX + inputFile.getWidth()) % inputFile.getWidth(); 
            	            int imageY = (y - filterHeight / 2 + filterY + inputFile.getHeight()) % inputFile.getHeight(); 
            	            int rgba = inputFile.getRGB(imageX, imageY);
                    		Color color = new Color(rgba, true);
            	            red += color.getRed() * filter[filterX][filterY]; 
            	            green += color.getGreen() * filter[filterX][filterY]; 
            	            blue += color.getBlue() * filter[filterX][filterY]; 
            	        } 
            		red = (int) (red * factor + bias);
            		green = (int) (green * factor + bias);
            		blue = (int) (blue * factor + bias);
            		if(red > 255) red = 255;
            		if(green > 255) green = 255;
            		if(blue > 255) blue = 255;
            		if(red < 0) red = 0;
            		if(green < 0) green = 0;
            		if(blue < 0) blue = 0;
            		inputFile.setRGB(x, y, new Color(red, green, blue).getRGB());
            	}
            }
            File outputFile = new File(filename);
            ImageIO.write(inputFile, "jpg", outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}
