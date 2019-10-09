/*
 * Copyright (c) 2019 Jalasoft.
 *
 *  This software is the confidential and proprietary information of Jalasoft.
 *  ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Jalasoft.
 */

package com.jalasoft.webservice.entitities;

/**
 * ImageFile class.
 */
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

    /**
     * Get target type to convert.
     *
     * @return
     */
    public String getTargetType() {
        return targetType;
    }

    /**
     * Set target type
     *
     * @param targetType
     */
    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    /**
     * Get Rotate.
     *
     * @return
     */
    public double getRotate() {
        return rotate;
    }

    /**
     * Set Rotate.
     *
     * @param rotate
     */
    public void setRotate(double rotate) {
        this.rotate = rotate;
    }

    /**
     * Get Blur.
     *
     * @return
     */
    public double getBlur() {
        return blur;
    }

    /**
     * Set Blur.
     *
     * @param blur
     */
    public void setBlur(double blur) {
        this.blur = blur;
    }

    /**
     * Get Resize.
     *
     * @return
     */
    public int getResize() {
        return resize;
    }

    /**
     * Set Resize.
     *
     * @param resize
     */
    public void setResize(int resize) {
        this.resize = resize;
    }

    /**
     * Get Border Color.
     *
     * @return
     */
    public String getBorderColor() {
        return borderColor;
    }

    /**
     * Set Border Color.
     *
     * @param borderColor
     */
    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    /**
     * Get Border.
     *
     * @return
     */
    public int getBorder() {
        return border;
    }

    /**
     * Set Border.
     *
     * @param border
     */
    public void setBorder(int border) {
        this.border = border;
    }

    /**
     * Get the Grayscale.
     *
     * @return
     */
    public boolean isGrayscale() {
        return grayscale;
    }

    /**
     * Set the Grayscale.
     *
     * @param grayscale
     */
    public void setGrayscale(boolean grayscale) {
        this.grayscale = grayscale;
    }

    /**
     * Get the Transpose.
     *
     * @return
     */
    public boolean isTranspose() {
        return transpose;
    }

    /**
     * Set Transpose.
     *
     * @param transpose
     */
    public void setTranspose(boolean transpose) {
        this.transpose = transpose;
    }

    /**
     * Get Transverse.
     *
     * @return
     */
    public boolean isTransverse() {
        return transverse;
    }

    /**
     * Set Transverse.
     *
     * @param transverse
     */
    public void setTransverse(boolean transverse) {
        this.transverse = transverse;
    }

    @Override
    void Validate() {

    }
}
