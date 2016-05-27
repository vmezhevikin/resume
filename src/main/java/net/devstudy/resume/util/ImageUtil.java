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
	public static String saveFile(String dirWebapp, String destDir, MultipartFile file)
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

	public static String getSmallPhotoPath(String uuid)
	{
		if (uuid == null)
			return null;
		else
			return uuid.replace(".jpg", "-sm.jpg");
	}

	public static void removeFile(String dirWebapp, String oldImage)
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