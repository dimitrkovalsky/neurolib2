package com.liberty.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:29
 */
@Entity
@Table(name = "libbook", schema = "neurolib")
public class LibbookEntity {
    private int bookId;
    private int fileSize;
    private Timestamp time;
    private String title;
    private String title1;
    private String lang;
    private String srcLang;
    private String fileType;
    private String encoding;
    private short year;
    private String deleted;
    private String ver;
    private String fileAuthor;
    private int n;
    private String keywords;
    private byte[] md5;
    private Timestamp modified;
    private String pmd5;
    private byte errorCode;
    private int pages;
    private int chars;

    @Id
    @Column(name = "BookId")
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Basic
    @Column(name = "FileSize")
    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    @Basic
    @Column(name = "Time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "Title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "Title1")
    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    @Basic
    @Column(name = "Lang")
    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Basic
    @Column(name = "SrcLang")
    public String getSrcLang() {
        return srcLang;
    }

    public void setSrcLang(String srcLang) {
        this.srcLang = srcLang;
    }

    @Basic
    @Column(name = "FileType")
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Basic
    @Column(name = "Encoding")
    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @Basic
    @Column(name = "Year")
    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    @Basic
    @Column(name = "Deleted")
    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "Ver")
    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    @Basic
    @Column(name = "FileAuthor")
    public String getFileAuthor() {
        return fileAuthor;
    }

    public void setFileAuthor(String fileAuthor) {
        this.fileAuthor = fileAuthor;
    }

    @Basic
    @Column(name = "N")
    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    @Basic
    @Column(name = "keywords")
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Basic
    @Column(name = "md5")
    public byte[] getMd5() {
        return md5;
    }

    public void setMd5(byte[] md5) {
        this.md5 = md5;
    }

    @Basic
    @Column(name = "Modified")
    public Timestamp getModified() {
        return modified;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }

    @Basic
    @Column(name = "pmd5")
    public String getPmd5() {
        return pmd5;
    }

    public void setPmd5(String pmd5) {
        this.pmd5 = pmd5;
    }

    @Basic
    @Column(name = "ErrorCode")
    public byte getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(byte errorCode) {
        this.errorCode = errorCode;
    }

    @Basic
    @Column(name = "Pages")
    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @Basic
    @Column(name = "Chars")
    public int getChars() {
        return chars;
    }

    public void setChars(int chars) {
        this.chars = chars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibbookEntity that = (LibbookEntity) o;

        if (bookId != that.bookId) return false;
        if (fileSize != that.fileSize) return false;
        if (year != that.year) return false;
        if (n != that.n) return false;
        if (errorCode != that.errorCode) return false;
        if (pages != that.pages) return false;
        if (chars != that.chars) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (title1 != null ? !title1.equals(that.title1) : that.title1 != null) return false;
        if (lang != null ? !lang.equals(that.lang) : that.lang != null) return false;
        if (srcLang != null ? !srcLang.equals(that.srcLang) : that.srcLang != null) return false;
        if (fileType != null ? !fileType.equals(that.fileType) : that.fileType != null) return false;
        if (encoding != null ? !encoding.equals(that.encoding) : that.encoding != null) return false;
        if (deleted != null ? !deleted.equals(that.deleted) : that.deleted != null) return false;
        if (ver != null ? !ver.equals(that.ver) : that.ver != null) return false;
        if (fileAuthor != null ? !fileAuthor.equals(that.fileAuthor) : that.fileAuthor != null) return false;
        if (keywords != null ? !keywords.equals(that.keywords) : that.keywords != null) return false;
        if (!Arrays.equals(md5, that.md5)) return false;
        if (modified != null ? !modified.equals(that.modified) : that.modified != null) return false;
        if (pmd5 != null ? !pmd5.equals(that.pmd5) : that.pmd5 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bookId;
        result = 31 * result + fileSize;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (title1 != null ? title1.hashCode() : 0);
        result = 31 * result + (lang != null ? lang.hashCode() : 0);
        result = 31 * result + (srcLang != null ? srcLang.hashCode() : 0);
        result = 31 * result + (fileType != null ? fileType.hashCode() : 0);
        result = 31 * result + (encoding != null ? encoding.hashCode() : 0);
        result = 31 * result + (int) year;
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        result = 31 * result + (ver != null ? ver.hashCode() : 0);
        result = 31 * result + (fileAuthor != null ? fileAuthor.hashCode() : 0);
        result = 31 * result + n;
        result = 31 * result + (keywords != null ? keywords.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(md5);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        result = 31 * result + (pmd5 != null ? pmd5.hashCode() : 0);
        result = 31 * result + (int) errorCode;
        result = 31 * result + pages;
        result = 31 * result + chars;
        return result;
    }
}
