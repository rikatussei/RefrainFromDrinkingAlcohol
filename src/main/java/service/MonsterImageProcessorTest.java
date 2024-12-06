// src/test/java/service/MonsterImageProcessorTest.java
package service;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MonsterImageProcessorTest {
	private MonsterImageProcessor processor;
	private byte[] testImageData;

	@BeforeEach
	void setUp() throws Exception {
		processor = new MonsterImageProcessor();

		// テスト用画像データの作成
		BufferedImage testImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(testImage, "PNG", baos);
		testImageData = baos.toByteArray();
	}

	@Test
	void testProcessImage() throws Exception {
		byte[] processedImage = processor.processImage(testImageData);

		// 処理された画像の検証
		assertNotNull(processedImage);
		BufferedImage result = ImageIO.read(new ByteArrayInputStream(processedImage));
		assertTrue(result.getWidth() <= 512);
		assertTrue(result.getHeight() <= 512);
	}

	@Test
	void testProcessSmallImage() throws Exception {
		// 小さい画像の作成
		BufferedImage smallImage = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(smallImage, "PNG", baos);

		byte[] processedImage = processor.processImage(baos.toByteArray());
		BufferedImage result = ImageIO.read(new ByteArrayInputStream(processedImage));

		// リサイズされていないことを確認
		assertEquals(300, result.getWidth());
		assertEquals(300, result.getHeight());
	}
}