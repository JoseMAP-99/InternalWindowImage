package com.fabiyjose.internalwindowsp8;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author José María y Fabián
 */
public class Canvas extends JPanel{
    
    private Mat actualImage = null;
    
    public BufferedImage getActualImage(){
        return (BufferedImage) HighGui.toBufferedImage(actualImage);
    }    
    
    @Override
    public void paintComponent(Graphics g){
        if(actualImage == null) return;
        super.paintComponent(g);
        g.drawImage(getActualImage(), 0, 0, null);
    }
        
    
    public void viewImage(Mat viewImage){
        this.actualImage = viewImage;
        repaint();
    }
        
    public Mat applyFilter(int umb, Mat actImage){
        this.actualImage = umbralizar(actImage, umb);
        repaint();
        return this.actualImage;
    }
        
    
    private Mat umbralizar(Mat imagen_original, Integer umbral) {
      
        // crear dos imágenes en niveles de gris con el mismo
        // tamaño que la original
        Mat imagenGris = new Mat(imagen_original.rows(),
                                 imagen_original.cols(), CvType.CV_8U);
        
        Mat imagenUmbralizada = new Mat(imagen_original.rows(),
                                        imagen_original.cols(), CvType.CV_8U);
        
        // convierte a niveles de grises la imagen original
        Imgproc.cvtColor(imagen_original, imagenGris, Imgproc.COLOR_BGR2GRAY);
        
        // umbraliza la imagen:
        // - píxeles con nivel de gris > umbral se ponen a 1
        // - píxeles con nivel de gris <= umbra se ponen a 0
        Imgproc.threshold(imagenGris, imagenUmbralizada, umbral, 255,
                          Imgproc.THRESH_BINARY);
        // se devuelve la imagen umbralizada
        
        return imagenUmbralizada;
    }
}
