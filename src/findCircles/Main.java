package findCircles;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Main extends Application {

    public static final Scalar COLOR_WHITE = colorRGB(255, 255, 255);
    public static final Scalar COLOR_BLACK = colorRGB(0, 0, 0);

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage stage) throws Exception {
        VBox root = new VBox(15.0);
        root.setAlignment(Pos.CENTER);

        Button button = new Button("Выполнить");
        button.setOnAction(this::onClickButton);
        root.getChildren().add(button);

        Scene scene = new Scene(root, 400.0, 150.0);
        stage.setTitle("OpenCV " + Core.VERSION);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            Platform.exit();
        });
        stage.show();
    }

    private void onClickButton(ActionEvent e) {
        Mat img = Imgcodecs.imread("src\\resources\\circles2.jpg");

        Mat imgGray = new Mat();
        Imgproc.cvtColor(img, imgGray, Imgproc.COLOR_BGR2GRAY);
        CvUtilsFx.showImage(imgGray, "Original");

        Mat circles = new Mat();
        Imgproc.HoughCircles(imgGray, circles, Imgproc.HOUGH_GRADIENT, 2, imgGray.rows() / 4);
        Mat result = new Mat(img.size(), CvType.CV_8UC3, COLOR_WHITE);
        for (int i = 0, r = circles.rows(); i < r; i++) {
            for (int j = 0, c = circles.cols(); j < c; j++) {
                double[] circle = circles.get(i, j);
                Imgproc.circle(result, new Point(circle[0], circle[1]),
                        (int) circle[2], COLOR_BLACK);
            }
        }
        CvUtilsFx.showImage(result, "Result");
        img.release();
        imgGray.release();
        result.release();
    }

    public static Scalar colorRGB(double red, double green, double blue) {
        return new Scalar(blue, green, red);
    }

}
