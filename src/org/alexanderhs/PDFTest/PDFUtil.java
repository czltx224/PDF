package org.alexanderhs.PDFTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class PDFUtil {
	/**
	 * ��ͼƬת��ΪPDF
	 * @param imagePath imageͼƬ·��
	 * @param pdfPath PDF�ļ�·��
	 * @throws IOException
	 */
	public static void imageToPDF(String imagePath,String pdfPath) throws IOException{
 		PDDocument doc = new PDDocument();
 		try{
            PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, doc);
            
            float w = pdImage.getWidth();
            float h = pdImage.getHeight();

            PDPage page = new PDPage(new PDRectangle (w,h));
            
            doc.addPage(page);
            
            PDPageContentStream contents = new PDPageContentStream(doc, page);
            
            contents.drawImage(pdImage, 0, 0);
            
            contents.close();
            doc.save(pdfPath);
        }finally{
            doc.close();
        }
 	}
 
 	/**
 	 * �ϲ�����PDF�ļ�
 	 * @param destination Ŀ���ĵ�����·��
 	 * @param sources ���ϲ�PDF·������
 	 * @throws IOException
 	 */
 	public static void mergePDFs(String destination,List<String> sources) throws IOException{
 		PDFMergerUtility pm=new PDFMergerUtility();
 		pm.setDestinationFileName(destination);
 		for (String source:sources){
 			pm.addSource(source);
 		}
 		pm.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());	
 	}
 	
 	/** 
 	 * �ϲ�����PDF�ļ� 
 	 * @param destination
 	 * @param source
 	 * @throws IOException
 	 */
 	public static void merge2PDF(String destination,String source) throws IOException{
 		PDFMergerUtility pm=new PDFMergerUtility();
 		
 		pm.addSource(destination);
 		pm.addSource(source);
 		
 		pm.setDestinationFileName(destination);
 		pm.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());	
 	}
 	
 	/**
 	 * ��PDF�����ͼƬ
 	 * @param pdfPath	PDF·��
 	 * @param imagePath	ͼƬ·��
 	 * @throws IOException
 	 */
 	public static void addImageToPDF(String pdfPath,String imagePath) throws IOException{
 		PDDocument doc = null;
 		try{
            doc = PDDocument.load(new File(pdfPath));
            
            PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, doc);
            
            float w = pdImage.getWidth();
            float h = pdImage.getHeight();

            PDPage page = new PDPage(new PDRectangle (w,h));
            
            doc.addPage(page);
            
            PDPageContentStream contents = new PDPageContentStream(doc, page);
            
            contents.drawImage(pdImage, 0, 0);
            
            contents.close();
            doc.save(pdfPath);

         }
        finally
        {
            if( doc != null )
            {
                doc.close();
            }
        }
 	}
 	
 	public static boolean isPDF(String filePath){
 		return filePath.endsWith(".pdf");
 	}
 	
 	public static boolean isImage(String filePath) {
 		return filePath.endsWith(".jpg");
 	}
 	
 	/**
 	 * �ϲ��ĵ�����PDF������ͼƬ��PDF�ļ���
 	 * @param destination	���ɵ�PDF�ļ�·��
 	 * @param sources	ͼƬ��PDF·������
 	 * @throws IOException
 	 */
 	public static void mergeToPDF(String destination,List<String> sources) throws IOException{
 		// ������pdf
        PDDocument doc = new PDDocument();
        doc.save(destination);
        doc.close();
 		// �����ͼƬ����ӵ�pdf�У������pdf��ִ�кϲ�����
        for(String source:sources){
        	if(isPDF(source)){
        		merge2PDF(destination,source);
        	}else if(isImage(source)){
        		addImageToPDF(destination,source);
        	}
        }
 	}
 	
 	
 	
 	public static void main(String[] args) throws IOException{
	    List<String> sources=new ArrayList<String>();
	    sources.add("F:\\1.20160125ҵ��ģ\\ҵ��ģpdf��\\��ѯҵ������ͼ��ʵ��.pdf");
	    sources.add("C:\\Users\\Administrator\\Desktop\\wallhaven-339672.jpg");
	    sources.add("F:\\1.20160125ҵ��ģ\\ҵ��ģpdf��\\����ֵ��Ǽ�ҵ������ͼ��ʵ��.pdf");
	    mergeToPDF("C:\\Users\\Administrator\\Desktop\\����.pdf",sources);
	}
}