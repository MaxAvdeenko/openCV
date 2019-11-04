package changeImage;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class ColorImageToGray {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {

        Mat m2 = Imgcodecs.imread("src\\resources\\test_image.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        Imgcodecs.imwrite("src\\resources\\test_image_rev.jpg", m2);
    }
}
