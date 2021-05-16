package com.cookandroid.beer;

public class Post {
    private String documentId;
    private String title;
    private String contents;
    private String name;
    public Post(){

    }
    public Post(String documentId, String title, String contents,String name) {
        this.documentId = documentId;
        this.title = title;
        this.contents = contents;
        this.name =name;
    }
    public Post( String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Post{" +
                "documentId='" + documentId + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
