package net.devstudy.resume.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class ImageUtil
{
	@Value("${webapp.folder}")
	private String dirWebapp1;
	
	@Value("${avatar.folder}")
	private String dirAvatar1;

	@Value("${certificate.folder}")
	private String dirCertificate1;
	
	private String dirWebapp = "/home/viktor/Dropbox/workspace/resume/src/main/webapp";
	
	private String dirAvatar = "/media/avatar";
	
	private String dirCertificate = "/media/certificate";

	private static ImageUtil instance;
	
	private ImageUtil(){}
	
	public static ImageUtil getInstance()
	{
		if (instance == null)
			instance = new ImageUtil();
		return instance;
	}
	
	public String saveFileToAvatars(MultipartFile file)
	{
		return saveFileTo(file, dirAvatar);
	}

	public String saveFileToCertificates(MultipartFile file)
	{
		return saveFileTo(file, dirCertificate);
	}

	private String saveFileTo(MultipartFile file, String destDir)
	{
		try
		{
			String uuid = UUID.randomUUID().toString() + ".jpg";
			String smallUuid = uuid.replace(".jpg", "-sm.jpg");

			File destFile = new File(dirWebapp + destDir, uuid);
			File destFileSmall = new File(dirWebapp + destDir, smallUuid);

			if (!destFile.getParentFile().exists())
				destFile.getParentFile().mkdirs();
			file.transferTo(destFile);

			Thumbnails.of(destFile).size(100, 100).toFile(destFileSmall);

			return destFile.toString().replace(dirWebapp, "");
		} catch (Exception e)
		{
			return null;
		}
	}

	public String getSmallPhotoPath(String uuid)
	{
		if (uuid == null)
			return null;
		else
			return uuid.replace(".jpg", "-sm.jpg");
	}

	public void removeFileFromImages(String oldImage)
	{
		if (oldImage != null && !oldImage.contains("blank-photo"))
		{
			try
			{
				Path destFile = Paths.get(dirWebapp + oldImage);
				Files.delete(destFile);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}