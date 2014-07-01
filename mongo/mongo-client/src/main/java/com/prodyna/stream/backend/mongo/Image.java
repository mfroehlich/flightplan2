package com.prodyna.stream.backend.mongo;

import java.io.Serializable;

/**
 * Image entity.
 */
public class Image implements Serializable {
    private String id;
    private String filename;
    private String url;
    private long size;

    public Image() {
        super();
    }

    public Image(String id, String filename, String url, long size) {
        this.id = id;
        this.filename = filename;
        this.url = url;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (size != image.size) return false;
        if (filename != null ? !filename.equals(image.filename) : image.filename != null) return false;
        if (id != null ? !id.equals(image.id) : image.id != null) return false;
        if (url != null ? !url.equals(image.url) : image.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (filename != null ? filename.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (int) (size ^ (size >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id='" + id + '\'' +
                ", filename='" + filename + '\'' +
                ", url='" + url + '\'' +
                ", size=" + size +
                '}';
    }
}
