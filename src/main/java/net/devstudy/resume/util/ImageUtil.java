package net.devstudy.resume.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

public class ImageUtil
{
	public static String saveFile(String webappFolder, String destinationFolder, MultipartFile file)
	{
		try
		{
			String uuid = UUID.randomUUID().toString() + ".jpg";
			String smallUuid = uuid.replace(".jpg", "-sm.jpg");

			File tempFile = new File(webappFolder + destinationFolder, "temp");
			File destFile = new File(webappFolder + destinationFolder, uuid);
			File destFileSmall = new File(webappFolder + destinationFolder, smallUuid);

			if (!tempFile.getParentFile().exists())
				tempFile.getParentFile().mkdirs();
			file.transferTo(tempFile);

			Thumbnails.of(tempFile).size(400, 400).toFile(destFile);
			Thumbnails.of(tempFile).size(100, 100).toFile(destFileSmall);
			
			Path tempPath = Paths.get(tempFile.getAbsolutePath());
			Files.delete(tempPath);

			return destFile.toString().replace(webappFolder, "");
		} catch (Exception e)
		{
			return null;
		}
	}

	public static String getSmallPhotoPath(String uuid)
	{
		if (uuid == null)
			return null;
		else
			return uuid.replace(".jpg", "-sm.jpg");
	}

	public static void removeFile(String webappFolder, String oldImage)
	{
		if (oldImage != null && !oldImage.contains("blank-photo"))
		{
			try
			{
				Path destFile = Paths.get(webappFolder + oldImage);
				Files.delete(destFile);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}