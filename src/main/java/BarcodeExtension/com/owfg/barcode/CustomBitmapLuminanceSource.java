package com.owfg.barcode; 

import net.rim.device.api.system.Bitmap;
import com.google.zxing.LuminanceSource;

public final class CustomBitmapLuminanceSource extends LuminanceSource {

    private final Bitmap bitmap;
    private int[] rgbData;

    public CustomBitmapLuminanceSource(Bitmap bitmap) {
        super(bitmap.getWidth(), bitmap.getHeight());
        this.bitmap = bitmap;
    }

    // Instead of multiplying by 306, 601, 117, we multiply by 256, 512, 256, so that
    // the multiplies can be implemented as shifts.
    //
    // Really, it's:
    //
    // return ((((pixel >> 16) & 0xFF) << 8) +
    //         (((pixel >>  8) & 0xFF) << 9) +
    //         (( pixel        & 0xFF) << 8)) >> 10;
    //
    // That is, we're replacing the coefficients in the original with powers of two,
    // which can be implemented as shifts, even though changing the coefficients slightly
    // alters the conversion. The difference is not significant for our purposes.
    public byte[] getRow(int y, byte[] row) {
        if (y < 0 || y >= getHeight()) {
            throw new IllegalArgumentException("Requested row is outside the image: " + y);
        }
        int width = getWidth();
        if (row == null || row.length < width) {
            row = new byte[width];
        }

        if (rgbData == null || rgbData.length < width) {
            rgbData = new int[width];
        }
        bitmap.getARGB(rgbData, 0, width, 0, y, width, 1);
        for (int x = 0; x < width; x++) {
            int pixel = rgbData[x];
            int luminance = (((pixel & 0x00FF0000) >> 16) +
                ((pixel & 0x0000FF00) >>  7) +
                (pixel & 0x000000FF       )) >> 2;
            row[x] = (byte) luminance;
        }
        return row;
    }

    public byte[] getMatrix() {
        int width = getWidth();
        int height = getHeight();
        int area = width * height;
        byte[] matrix = new byte[area];

        int[] argb = new int[area];
        bitmap.getARGB(argb, 0, width, 0, 0, width, height);
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                int pixel = argb[offset + x];
                int luminance = (((pixel & 0x00FF0000) >> 16) +
                    ((pixel & 0x0000FF00) >>  7) +
                    (pixel & 0x000000FF       )) >> 2;
                matrix[offset + x] = (byte) luminance;
            }
        }
        return matrix;
    }

}

