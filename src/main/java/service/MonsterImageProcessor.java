// src/main/java/service/MonsterImageProcessor.java
package service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonsterImageProcessor {
	private static final Logger logger = LoggerFactory.getLogger(MonsterImageProcessor.class);
	private static final int MAX_WIDTH = 512;
	private static final int MAX_HEIGHT = 512;

	public byte[] processImage(byte[] originalImage) throws IOException {
		try {
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(originalImage));

			// サイズチェックと必要に応じてリサイズ
			if (needsResize(image)) {
				image = resizeImage(image);
			}

			// 画像の最適化と変換
			return convertToOptimizedPng(image);
		} catch (IOException e) {
			logger.error("画像処理中にエラーが発生", e);
			throw e;
		}
	}

	private boolean needsResize(BufferedImage image) {
		return image.getWidth() > MAX_WIDTH || image.getHeight() > MAX_HEIGHT;
	}

	private BufferedImage resizeImage(BufferedImage original) {
		double scale = Math.min(
				(double) MAX_WIDTH / original.getWidth(),
				(double) MAX_HEIGHT / original.getHeight());

		int newWidth = (int) (original.getWidth() * scale);
		int newHeight = (int) (original.getHeight() * scale);

		BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		resized.createGraphics().drawImage(original, 0, 0, newWidth, newHeight, null);
		return resized;
	}

	private byte[] convertToOptimizedPng(BufferedImage image) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "PNG", outputStream);
		return outputStream.toByteArray();
	}
}