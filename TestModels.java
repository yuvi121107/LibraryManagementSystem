
import model.DigitalMedia;
import model.LibraryItem;
import model.PhysicalBook;

public class TestModels {

    public static void main(String[] args) {

        LibraryItem book = new PhysicalBook(
                "B101",
                "Java Basics",
                "James",
                "Shelf A1"
        );

        LibraryItem media = new DigitalMedia(
                "D101",
                "Java Tutorial",
                "Open Learning",
                "MP4"
        );

        System.out.println(book);
        System.out.println(media);
    }
}
