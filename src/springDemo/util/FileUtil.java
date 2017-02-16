package springDemo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import springDemo.user.dto.ImageDto;
import springDemo.user.dto.UploadDto;
import springDemo.user.dto.UrlDto;

public enum FileUtil {
	image;
	
	public UploadDto upload(CommonsMultipartFile file, int type){
		UploadDto uploadDto = new UploadDto();
		ImageDto imageDto = new ImageDto();
		String path = "D:/java/upload/";
		String filePath = "";
		switch (type) {
		case 1:
			filePath += "img/";
			break;
		case 2:
			filePath += "video/";
			break;
		case 3:
			filePath += "document/";
			break;
		default:
			break;
		}
		
		File uploadFile = new File(path + filePath);
		
		if (!uploadFile.exists()) {
			uploadFile.mkdir();
		}
		
		if (!uploadFile.canWrite()) {
			uploadFile.setWritable(true);
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		filePath += dateFormat.format(Calendar.getInstance().getTime());
		
		File dateFile = new File(path + filePath);
		
		if (!dateFile.exists()) {
			dateFile.mkdir();
		}
		
		if (!dateFile.canWrite()) {
			dateFile.setWritable(true);
		}
		
		String originalFilename = file.getOriginalFilename();
		
		String fileName = originalFilename.substring(0, originalFilename.lastIndexOf(".") - 1);
		String typeName = originalFilename.substring(originalFilename.lastIndexOf("."), 
				originalFilename.length());
		
		dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		filePath += "/" + fileName ;
		filePath += "_" + dateFormat.format(Calendar.getInstance().getTime());
		filePath += "_" + getStringRandom(16);
		filePath += typeName;
		try {
			file.transferTo(new File(path + filePath));
			
			FileInputStream stream = new FileInputStream(path + filePath);
			FileChannel channel = stream.getChannel();
			imageDto.setCaption(fileName);
			imageDto.setSize(channel.size());
			imageDto.setType("image");
			imageDto.setWidth("auto");
			imageDto.setFileName(originalFilename);
			
			byte[] bFile = new byte[(int) channel.size()];  
			stream.read(bFile);
			String fileByteData = new String(bFile);
			
			imageDto.setPreviewAsData(fileByteData);	
			channel.close();
			stream.close();
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest paramHttpServletRequest = sra.getRequest();
		
		String httpPath = paramHttpServletRequest.getContextPath();
		String basePath = paramHttpServletRequest.getScheme() + "://"
				+ paramHttpServletRequest.getServerName() + ":" 
				+ paramHttpServletRequest.getServerPort()
				+ httpPath + "/static/";
		
		String[] initialPreview = { basePath + filePath};
		
		UrlDto extra = new UrlDto();
		extra.setDeleteUrl(filePath);
		imageDto.setExtra(extra);
		
		ImageDto[] initialPreviewConfig = {imageDto};
		uploadDto.setInitialPreview(initialPreview);
		uploadDto.setInitialPreviewConfig(initialPreviewConfig);
		return uploadDto;
	}
	
	public void delete(String filePath){
		String path = "D:/java/upload/";
		
		File dateFile = new File(path + filePath);

		if (dateFile.exists()) {
			dateFile.delete();
		}			
	}
	
    //生成随机数字和字母,  
    public String getStringRandom(int length) {  
          
        String val = "";  
        Random random = new Random();  
          
        //参数length，表示生成几位随机数  
        for(int i = 0; i < length; i++) {  
              
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
            //输出字母还是数字  
            if( "char".equalsIgnoreCase(charOrNum) ) {  
                //输出是大写字母还是小写字母  
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
                val += (char)(random.nextInt(26) + temp);  
            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
                val += String.valueOf(random.nextInt(10));  
            }  
        }  
        return val;  
    }  
}
