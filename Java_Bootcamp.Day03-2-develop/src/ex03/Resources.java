package ex03;public class Resources {    private final String url;    private final int fileNum;    private boolean isDownload;    public Resources(String url, boolean isDownload, int fileNum) {        this.url = url;        this.isDownload = isDownload;        this.fileNum = fileNum;    }    public String getUrl() {        return url;    }    public boolean isDownload() {        return isDownload;    }    public void setDownload(boolean download) {        isDownload = download;    }    public int getFileNum() {        return fileNum;    }}