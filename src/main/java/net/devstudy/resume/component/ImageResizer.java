package net.devstudy.resume.component;

import java.io.File;
import java.io.IOException;

import javax.annotation.Nonnull;

public interface ImageResizer {
	void resize(@Nonnull File sourceFile, @Nonnull File destFile, int width, int height) throws IOException;
}