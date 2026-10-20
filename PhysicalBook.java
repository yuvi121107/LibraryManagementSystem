
package model;

public class PhysicalBook extends LibraryItem {

    private static final long serialVersionUID = 1L;

    private final String shelfLocation;

    public PhysicalBook(
            String id,
            String title,
            String author,
            String shelfLocation) {

        super(id, title, author);
        this.shelfLocation = shelfLocation;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    @Override
    public String getItemType() {
        return "Physical Book";
    }
}
