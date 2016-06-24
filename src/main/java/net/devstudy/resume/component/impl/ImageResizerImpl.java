package net.devstudy.resume.component.impl;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.coobird.thumbnailator.Thumbnails;
import net.devstudy.resume.component.ImageResizer;

@Component
public class ImageResizerImpl implements ImageResizer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageResizerImpl.class);

	@Override
	public void resize(File sourceFile, File destFile, int width, int height) throws IOException {
		LOGGER.debug("Resizing image {}", sourceFile.getAbsolutePath());
		Thumbnails.of(sourceFile).size(width, height).outputFormat("jpg").toFile(destFile);
	}
}