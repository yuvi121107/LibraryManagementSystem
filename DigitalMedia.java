
package model;

public class DigitalMedia extends LibraryItem {

    private static final long serialVersionUID = 1L;

    private final String fileFormat;

    public DigitalMedia(
            String id,
            String title,
            String author,
            String fileFormat) {

        super(id, title, author);
        this.fileFormat = fileFormat;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    @Override
    public String getItemType() {
        return "Digital Media";
    }
}
