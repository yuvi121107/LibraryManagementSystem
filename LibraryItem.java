
package model;

import java.io.Serializable;

public abstract class LibraryItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String id;
    private final String title;
    private final String author;
    private boolean available;

    public LibraryItem(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public abstract String getItemType();

    @Override
    public String toString() {
        return getItemType()
                + " | ID: " + id
                + " | Title: " + title
                + " | Author: " + author
                + " | Available: " + available;
    }
}
