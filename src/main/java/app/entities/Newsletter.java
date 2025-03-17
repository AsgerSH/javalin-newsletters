package app.entities;

import java.time.LocalDate;

public class Newsletter {
    int id;
    String title;
    String published;
    String filename;
    String teasertext;
    String thumbnail_name;

    public Newsletter(int id, String title, String published, String filename, String teasertext, String thumbnail_name) {
        this.id = id;
        this.title = title;
        this.published = published;
        this.filename = filename;
        this.teasertext = teasertext;
        this.thumbnail_name = thumbnail_name;
    }

    public Newsletter(String title, String teaserText, String filename, String thumbnail_name, LocalDate publishedDate) {
        this.title = title;
        this.teasertext = teaserText;
        this.filename = filename;
        this.thumbnail_name = thumbnail_name;
        this.published = publishedDate.toString();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTeasertext() {
        return teasertext;
    }

    public void setTeasertext(String teasertext) {
        this.teasertext = teasertext;
    }

    public String getThumbnail_name() {
        return thumbnail_name;
    }

    public void setThumbnail_name(String thumbnail_name) {
        this.thumbnail_name = thumbnail_name;
    }

    @Override
    public String toString() {
        return "Newsletter{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", published=" + published +
                ", filename='" + filename + '\'' +
                ", teasertext='" + teasertext + '\'' +
                ", thumbnail_name='" + thumbnail_name + '\'' +
                '}';
    }
}
