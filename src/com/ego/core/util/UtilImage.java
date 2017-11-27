/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ego.core.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Administrator
 */
public class UtilImage {

    public void pressImage(File pressImg, File targetImg, int waterType, float alpha) {
        try {
            File img = targetImg;
            Image src = ImageIO.read(img);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            //水印文件   
            Image src_biao = ImageIO.read(pressImg);
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            //左上角水印
            if (waterType == 1) {
                g.drawImage(src_biao, 2, 2, wideth_biao, height_biao, null);
            }
            if (waterType == 2) {
                g.drawImage(src_biao, wideth - wideth_biao - 2, 2, wideth_biao, height_biao, null);
            }
            //右下角水印
            if (waterType == 3) {
                g.drawImage(src_biao, 2, height - height_biao - 2, wideth_biao, height_biao, null);
            }
            if (waterType == 4) {
                g.drawImage(src_biao, wideth - wideth_biao - 2, height - height_biao - 2, wideth_biao, height_biao, null);
            }
            //上中
            if (waterType == 6) {
                g.drawImage(src_biao, (wideth - wideth_biao) / 2, 2, wideth_biao, height_biao, null);
            }
            if (waterType == 7) {
                g.drawImage(src_biao, 2, (height - height_biao) / 2, wideth_biao, height_biao, null);
            }
            if (waterType == 8) {
                g.drawImage(src_biao, wideth - wideth_biao - 2, (height - height_biao) / 2, wideth_biao, height_biao, null);
            }
            if (waterType == 9) {
                g.drawImage(src_biao, (wideth - wideth_biao) / 2, height - height_biao - 2, wideth_biao, height_biao, null);
            }
            if (waterType == 5) {
                g.drawImage(src_biao, (wideth - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao, null);

            }
            //水印文件结束   
            g.dispose();
            ImageIO.write((BufferedImage) image, "jpg", img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressText(String pressText, File targetImg, String fontName, int fontStyle, Color color, int fontSize, int waterType, float alpha) {
        try {
            File img = targetImg;
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            int wideth_biao = (getLength(pressText) * fontSize);
            int height_biao = fontSize;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            //左上角水印
            if (waterType == 1) {
                g.drawString(pressText, 2, height_biao + 2);

                //g.drawImage(src_biao, 2, 2, wideth_biao, height_biao, null);   
            }
            if (waterType == 2) {
                g.drawString(pressText, width - wideth_biao - 2, height_biao + 2);
                //g.drawImage(src_biao, wideth-wideth_biao-2, 2, wideth_biao, height_biao, null);   
            }
            //右下角水印
            if (waterType == 3) {
                g.drawString(pressText, 2, height - height_biao + 2);
                //g.drawImage(src_biao, 2, height-height_biao-2, wideth_biao, height_biao, null);   
            }
            if (waterType == 4) {
                g.drawString(pressText, width - wideth_biao - 2, height - height_biao + 4);
                //g.drawImage(src_biao, wideth-wideth_biao-2, height-height_biao-2, wideth_biao, height_biao, null);   
            }
            //上中
            if (waterType == 6) {
                g.drawString(pressText, (width - wideth_biao) / 2, height_biao + 2);
                //g.drawImage(src_biao, (wideth - wideth_biao) / 2, 2, wideth_biao, height_biao, null);   
            }
            if (waterType == 7) {
                g.drawString(pressText, 2, (height - height_biao) / 2);
                //g.drawImage(src_biao, 2, (height - height_biao) / 2, wideth_biao, height_biao, null);   
            }
            if (waterType == 8) {
                g.drawString(pressText, (width - wideth_biao), (height - height_biao) / 2);
                //g.drawImage(src_biao, wideth-wideth_biao-2, (height - height_biao) / 2, wideth_biao, height_biao, null);   
            }
            if (waterType == 9) {
                g.drawString(pressText, (width - wideth_biao) / 2, height - height_biao - 2);
                //g.drawImage(src_biao, (wideth - wideth_biao) / 2, height-height_biao-2, wideth_biao, height_biao, null);   
            }
            if (waterType == 5) {
                g.drawString(pressText, (width - wideth_biao) / 2, (height - height_biao) / 2);
                // g.drawImage(src_biao, (wideth - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao, null);   

            }



            // g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);   
            g.dispose();
            ImageIO.write((BufferedImage) image, "jpg", img);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void resize(String filePath, int height, int width, boolean bb) {
        try {
            double ratio = 0.0; //缩放比例    
            File f = new File(filePath);
            BufferedImage bi = ImageIO.read(f);
            Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
            //计算比例   
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (new Integer(height)).doubleValue() / bi.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }
            if (bb) {
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null)) {
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
                } else {
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
                }
                g.dispose();
                itemp = image;
            }
            ImageIO.write((BufferedImage) itemp, "jpg", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (new String(text.charAt(i) + "").getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
