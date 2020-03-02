package com.javafee.elibrary.core.uniform;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.border.TitledBorder;

import org.oxbow.swingbits.util.Strings;

import com.javafee.elibrary.core.common.BasePanel;

import lombok.Getter;
import lombok.Setter;

public class ImagePanel extends BasePanel {
	@Getter
	@Setter
	private Image image;
	@Getter
	@Setter
	private Image scaledImage;

	@Getter
	private boolean isTitledBorder;

	public ImagePanel(String tiledBorderText) {
		this.isTitledBorder = !Strings.isEmpty(tiledBorderText);
		if (isTitledBorder)
			setBorder(new TitledBorder(null, tiledBorderText, TitledBorder.LEADING,
					TitledBorder.TOP, null, null));
	}

	public void loadImage(String filename) throws IOException {
		image = ImageIO.read(new File(filename));
		setScaledImage();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (scaledImage != null)
			g.drawImage(scaledImage, !isTitledBorder ? 0 : 10, !isTitledBorder ? 0 : 20, this);
	}

	private void setScaledImage() {
		if (image != null) {
			int imageWidth = image.getWidth(this),
					imageHeight = image.getHeight(this);
			float iw = imageWidth,
					ih = imageHeight,
					pw = this.getWidth(), // panel width
					ph = this.getHeight(); // panel height
			if (pw < iw || ph < ih) {
				if ((pw / ph) > (iw / ih)) {
					iw = -1;
					ih = ph;
				} else {
					iw = pw;
					ih = -1;
				}
				// prevent errors if panel is 0 wide or high
				if (iw == 0)
					iw = -1;
				if (ih == 0)
					ih = -1;
				if (iw > 0 && isTitledBorder)
					iw -= 20;
				scaledImage = image.getScaledInstance(Float.valueOf(iw).intValue(), Float.valueOf(ih).intValue(),
						Image.SCALE_DEFAULT);
			} else
				scaledImage = image;
		}
	}
}
