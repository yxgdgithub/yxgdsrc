package com.mj.mmanage.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;


/**
 * 上传下载模型类
 * 
 * @author knight
 * 
 */
public class UploadFile {
	
	private static Logger logger = Logger.getLogger(UploadFile.class);
	//定义一个数组，用于保存可上传的图片文件类型
    public static List<String> imgFileTypes = new ArrayList<String>();
  //定义一个数组，用于保存可上传的文件类型
    public static List<String> fileTypes = new ArrayList<String>();
    
    static{
    	imgFileTypes.add("jpg");
    	imgFileTypes.add("jpeg");
    	imgFileTypes.add("bmp");
    	imgFileTypes.add("gif");
    	imgFileTypes.add("png");
    	fileTypes.add("zip");
    	fileTypes.add("text");
    }
    
    /**
	 * 保存图片 
	 * @param imgFile
	 * @param fileTypes 限制图片类型
	 * @param path 保存图片路径
	 * @return
	 */
	public static File uploadFile(MultipartFile imgFile,List<String> fileTypes,String path) {  
		String fileName = imgFile.getOriginalFilename(); 
		File file = null;  
		if(!StringUtil.isEmpty(fileName)){
			//获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名  
			String ext = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());  
			Random ran = new Random();
			int r = ran.nextInt(1000);
			//新的文件名
			String newFileName =  "_"+r+"_"+System.currentTimeMillis() + "." + ext;
			//对扩展名进行小写转换  
			ext = ext.toLowerCase();  
			if(fileTypes.contains(ext)) {//如果扩展名属于允许上传的类型，则创建文件  
				try {  
					file = new File(path); 
					if(!file.exists()){
						file.mkdirs();
					}
					//imgFile.transferTo(file);//保存上传的文件  
					file = SaveFileFromInputStream(imgFile.getInputStream(),path,newFileName);
				} catch (IllegalStateException e) {  
					e.printStackTrace();  
				} catch (IOException e) {  
					e.printStackTrace();  
				}  
			}  
		}
		return file;  
	}  
	/**保存文件
     * @param stream
     * @param path
     * @param filename
     * @throws IOException
     */
	@SuppressWarnings("unused")
    public static File SaveFileFromInputStream(InputStream stream,String path,String filename) {      
        FileOutputStream fs = null;
        File file = null;
        try{
        	File ListFile = new File(path);
    		boolean fileTrue = ListFile.exists();
    		if (!fileTrue) {
    			ListFile.mkdirs();
    		}
        	fs = new FileOutputStream( path + "/"+ filename);
        	file = new File(path + "/"+ filename);
	        byte[] buffer =new byte[1024*1024];
			int bytesum = 0;
	        int byteread = 0; 
	        while ((byteread=stream.read(buffer))!=-1){
	           bytesum+=byteread;
	           fs.write(buffer,0,byteread);
	           fs.flush();
	        } 
        }catch(IOException e){
        	e.printStackTrace();
        }finally{
        	if(null != fs){
        		try {
					fs.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	if(null != stream){
        		try {
					stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
        return file;
    }    
	/**
	 * 删除文件
	 * @param file
	 * @return
	 */
	public static boolean deleteFile(String path){
		logger.info("path->"+ path);	
		File fileDir = new File(path);
		if(fileDir.isDirectory()){
			File[] files = fileDir.listFiles();
			for (File f:files) {
				f.delete();
			}
		}
		boolean delete = fileDir.delete();
		return delete;
	}
	
	// lhl 20180412 注释本方法
	 
//	/**
//	 * 把图片印刷到图片上
//	 * @param pressImg -- 水印文件
//	 * @param targetImg -- 目标文件
//	 * @param newImg -- 新文件路径
//	 * @param x --x坐标
//	 * @param y --y坐标
//	 */
//	public final static void pressImage(String pressImg, String targetImg, String newImg, int x, int y) {
//		try {
//			//目标文件
//			File _file = new File(targetImg);
//			Image src = ImageIO.read(_file);
//			int wideth = src.getWidth(null);
//			int height = src.getHeight(null);
//			BufferedImage image = new BufferedImage(wideth, height,BufferedImage.TYPE_INT_RGB);
//			Graphics g = image.createGraphics();
//			g.drawImage(src, 0, 0, wideth, height, null);
//			//水印文件
//			File _filebiao = new File(pressImg);
//			Image src_biao = ImageIO.read(_filebiao);
//			int wideth_biao = src_biao.getWidth(null);
//			int height_biao = src_biao.getHeight(null);
//			g.drawImage(src_biao, (wideth - wideth_biao-x),
//			y, wideth_biao, height_biao, null);
//			//水印文件结束
//			g.dispose();
//			FileOutputStream out = new FileOutputStream(newImg);
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//			encoder.encode(image);
//			out.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	/**
	 * 上传文件
	 * @param bpath 图片保存根路径
	 * @param replaced 图片流
	 * @param url 业务图片路径 
	 * @param name 图片名称
	 * @return
	 * @throws Exception
	
	public static FileVo saveBinary(String bpath, String replaced, String url,
			String name) throws Exception {

		if (null == replaced || null == url || "".equals(url)) {
			return null;
		}
		// 文件路径
		String add = bpath + url;
		// 如果目录不存在则创建目录
		File ListFile = new File(add);
		boolean fileTrue = ListFile.exists();
		if (!fileTrue) {
			ListFile.mkdirs();
		}
		// 取得后缀名
		String ext = name.substring(name.lastIndexOf('.') + 1, name.length());
		// 取得文件名称
		String topname = name.substring(0, name.lastIndexOf('.'));
		
		// 取得随机数
		Random ran = new Random();
		int r = ran.nextInt(1000);
		// 更改文件名，取得当前上传时间的毫秒数值
		Calendar calendar = Calendar.getInstance();
		// 生成的文件名
		String temp_name = r+"_"+ String.valueOf(calendar.getTimeInMillis());
		// 生成的文件全名
		String allName = temp_name + "." + ext;

		writeNew2Binary(replaced, add + allName);
		FileVo vo = new FileVo();
		vo.setAddress(add);
		vo.setAllName(url + allName);
		vo.setExt(ext);
		vo.setName(allName);
		vo.setTopName(topname);
		vo.setOldName(name);
		return vo;
	}
	 */
	public static void writeNew2Binary(String replaced, String path)
			throws NumberFormatException, IOException {
		FileOutputStream fout = new FileOutputStream(path);
		for (int i = 0; i < replaced.length(); i = i + 2) {
			fout.write(Integer.parseInt(replaced.substring(i, i + 2), 16));
		}
		fout.close();
	}
	
	/***
	 * 将大图分解成1240*1754 的图片
	 * 
	 * @param bpath
	 * @param replaced
	 * @param url
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static String toMorePic(String bpath, Integer pageCount)
			throws Exception {

		StringBuffer sb = new StringBuffer();

		if (!StringUtil.isEmpty(bpath)) {
			return sb.toString();
		}
        
		File mainListFile = new File(bpath);
		boolean mainfileTrue = mainListFile.exists();
		if (!mainfileTrue) {
			return "";
		}
		
		BufferedImage bi = ImageIO.read(new File(bpath));
		bi.getScaledInstance(bi.getWidth(), bi.getHeight(),Image.SCALE_DEFAULT);
		Integer ph = bi.getHeight() / pageCount; // 实际的高
		Integer rw = bi.getWidth(); // 实际的宽

		Image img;
		ImageFilter cropFilter;

		Image image = bi.getScaledInstance(bi.getWidth(), bi.getHeight(),
				Image.SCALE_DEFAULT);

		for (int y = 0; y < pageCount; y++) {
			// 四个参数分别为图像起点坐标和宽高
			// 即: CropImageFilter(int x,int y,int width,int height)
			cropFilter = new CropImageFilter(0, ph * y, rw, ph);
			img = Toolkit.getDefaultToolkit().createImage(
					new FilteredImageSource(image.getSource(), cropFilter));
			BufferedImage tag = new BufferedImage(rw, ph,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(img, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			// 输出为文件
			String npath = bpath.substring(0, bpath.lastIndexOf('.')) + "_" + y
					+ ".jpg";

			ImageIO.write(tag, "JPEG", new File(npath));
			sb.append(",").append(npath);
		}

		if (sb.length() > 0) {
			return sb.substring(1);
		} else {
			return "";
		}

	}
	
	public static String updateFileName(String fileName ){
		if( !StringUtil.isEmpty(fileName) ){
			int rand = (int) Math.round(Math.random() * 100);
			//文件后缀名 如：.jpg
			String extName = "";
			if (fileName.lastIndexOf(".") >= 0) {
				extName = fileName.substring(fileName.lastIndexOf("."));
			}
			fileName = fileName.substring(0, fileName.lastIndexOf(".") - 1);
			//文件名_hotelId_时间戳和三位随机数
			fileName +="_"+DateUtil.now(Constants.FULL_NO_FORMAT_STR) + rand+extName;
		}
		return fileName;
	}
}
