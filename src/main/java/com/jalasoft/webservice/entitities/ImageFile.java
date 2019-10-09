package com.jalasoft.webservice.entitities;

public class ImageFile extends BaseFile {
    private String targetType;
    private double rotate;
    private double blur;
    private int resize;
    private String borderColor;
    private int border;
    private boolean grayscale;
    private boolean transpose;
    private boolean transverse;

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public double getRotate() {
        return rotate;
    }

    public void setRotate(double rotate) {
        this.rotate = rotate;
    }

    public double getBlur() {
        return blur;
    }

    public void setBlur(double blur) {
        this.blur = blur;
    }

    public int getResize() {
        return resize;
    }

    public void setResize(int resize) {
        this.resize = resize;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public int getBorder() {
        return border;
    }

    public void setBorder(int border) {
        this.border = border;
    }

    public boolean isGrayscale() {
        return grayscale;
    }

    public void setGrayscale(boolean grayscale) {
        this.grayscale = grayscale;
    }

    public boolean isTranspose() {
        return transpose;
    }

    public void setTranspose(boolean transpose) {
        this.transpose = transpose;
    }

    public boolean isTransverse() {
        return transverse;
    }

    public void setTransverse(boolean transverse) {
        this.transverse = transverse;
    }
}
