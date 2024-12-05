package util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 * 画像サイズの最適化を行うユーティリティクラス
 */
public class ImageOptimizer {
	private static final int MAX_WIDTH = 256;
	private static final int MAX_HEIGHT = 256;
	private static final float COMPRESSION_QUALITY = 0.8f;

	/**
	 * 画像データを最適化
	 * @param imageData 元の画像データ
	 * @return 最適化された画像データ
	 */
	public static byte[] optimizeImage(byte[] imageData) {
		try {
			// 画像をBufferedImageに変換
			BufferedImage original = ImageIO.read(new ByteArrayInputStream(imageData));
			if (original == null) {
				throw new IllegalArgumentException("Invalid image data");
			}

			// サイズ調整が必要か確認
			if (original.getWidth() > MAX_WIDTH || original.getHeight() > MAX_HEIGHT) {
				original = resizeImage(original);
			}

			// 画像の圧縮
			return compressImage(original);
		} catch (Exception e) {
			throw new RuntimeException("画像の最適化に失敗しました", e);
		}
	}

	/**
	 * 画像のリサイズ
	 */
	private static BufferedImage resizeImage(BufferedImage original) {
		double scale = Math.min(
				(double) MAX_WIDTH / original.getWidth(),
				(double) MAX_HEIGHT / original.getHeight());

		int newWidth = (int) (original.getWidth() * scale);
		int newHeight = (int) (original.getHeight() * scale);

		BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = resized.createGraphics();

		try {
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(original, 0, 0, newWidth, newHeight, null);
			return resized;
		} finally {
			g.dispose();
		}
	}

	/**
	 * 画像の圧縮
	 */
	private static byte[] compressImage(BufferedImage image) throws Exception {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();

		try (ImageOutputStream ios = ImageIO.createImageOutputStream(output)) {
			writer.setOutput(ios);
			ImageWriteParam param = writer.getDefaultWriteParam();

			if (param.canWriteCompressed()) {
				param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				param.setCompressionQuality(COMPRESSION_QUALITY);
			}

			writer.write(null, new IIOImage(image, null, null), param);
			return output.toByteArray();
		} finally {
			writer.dispose();
		}
	}
}