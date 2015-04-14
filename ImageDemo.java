package test;

import java.io.IOException;
import javax.imageio.ImageIO;


import java.io.File;
import java.awt.image.BufferedImage;



public class ImageDemo {
public static void main(String[] args){
	  System.out.println("Comparing Images Started.....");
    BufferedImage img,img2;
    boolean flag=false;
    float error=0;
    try {
        img = ImageIO.read(new File("E:\\org.PNG"));
  	  	System.out.println("image 1 is read");
        img2 = ImageIO.read(new File("E:\\mod.PNG"));
    	System.out.println("image 2 is read");
    	if(!(img.getWidth()==img2.getWidth() && img.getHeight()==img2.getHeight()))
        	System.out.println("Images are different in size...");
    	else
    	{
    	int[][][] newimage = new int[img.getWidth()*2+20][img.getHeight()][3];
        int[][][] pixelData = new int[img.getWidth()][img.getHeight()][3];
  	  	System.out.println("image 1 is mapped to array");
        int[][][] pixelData2 = new int[img2.getWidth()][img2.getHeight()][3];
  	  	System.out.println("image 2 is mapped to array");
        int[] rgb;
        int[] rgb2;
        
        for(int y = 0; y < img.getHeight(); y++){
            for(int x = 0; x < img.getWidth(); x++){
                rgb = getPixelData(img, x , y);

                for(int k = 0; k < rgb.length; k++){
                    pixelData[x][y][k] = rgb[k];
                    newimage[x][y][k] = rgb[k];
                }

            }
        }
  	  	System.out.println("image 1 is analyzed");
        
        for(int y = 0; y < img2.getHeight(); y++){
            for(int x = 0; x < img2.getWidth(); x++){
                rgb2 = getPixelData(img2, x , y);

                for(int k = 0; k < rgb2.length; k++){
                	
                    pixelData2[x][y][k] = rgb2[k];
                    newimage[x+img.getWidth()+20][y][k] = rgb2[k];
                }

            }
        }
  	  	System.out.println("image 2 is analyzed");

        for(int y = 0; y < img2.getHeight(); y++){
            for(int x = 0; x < img2.getWidth(); x++){
            	
            	if(pixelData[x][y][0] != pixelData2[x][y][0] || pixelData[x][y][1] != pixelData2[x][y][1] || pixelData[x][y][2] != pixelData2[x][y][2])
            	{
            		newimage[x+img.getWidth()+20][y][0]=0;
            		newimage[x+img.getWidth()+20][y][1]=255;
            		newimage[x+img.getWidth()+20][y][2]=0;
        			flag=true;
        			error++;
        		}
            }
            
        }
        if(flag==false)
        {
    	 System.out.println("No differences found");
        }
        
        else{
       	 System.out.println("Images are different");
       	 error= (error/(img2.getWidth()*img2.getHeight()))*100;
       	 System.out.println(100-error);
        create(newimage,img2.getWidth()*2+20,img2.getHeight(),"");
        }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private static int[] getPixelData(BufferedImage img, int x, int y) {
int argb = img.getRGB(x, y);

int rgb[] = new int[] {
    (argb >> 16) & 0xff, //red
    (argb >>  8) & 0xff, //green
    (argb      ) & 0xff  //blue
};
return rgb;
}

public static void create(int[][][] newimage, int width, int height, String savedto){
	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 

	for (int y = 0; y < height; y++) {
	   for (int x = 0; x < width; x++) {
	      int rgb = newimage[x][y][0];
	      rgb = (rgb << 8) + newimage[x][y][1];
	      rgb = (rgb << 8) + newimage[x][y][2];
	      image.setRGB(x, y, rgb);
	   }
	}
	File outputfile = new File("E:\\Original-Highlited image.png");
    try {
		ImageIO.write(image, "png", outputfile);
	    System.out.println("Image comparison successful, File Created");
	} catch (IOException e) {
	    System.out.println("failed");
		e.printStackTrace();
	}
}
}
