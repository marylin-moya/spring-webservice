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

import com.jalasoft.webservice.error_handler.ParamsInvalidException;
import com.jalasoft.webservice.model.Context;
import com.jalasoft.webservice.model.validate.FileNameValidation;
import com.jalasoft.webservice.model.validate.IValidateStrategy;
import com.jalasoft.webservice.model.validate.NullOrEmptyValidation;
import com.jalasoft.webservice.model.validate.TargetTypeValidation;

import java.util.ArrayList;
import java.util.List;

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

    public ImageFile() {
        borderColor = Color.black.toString();
        rotate = 0.0;
        blur = 0.0;
        resize = 0;
        border = 0;
        grayscale = false;
        transpose = false;
        transverse = false;
    }

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
        this.borderColor = borderColor.toLowerCase();
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
    public void validate() throws ParamsInvalidException {
        super.validate();
        validateDoubleField(this.rotate, "rotate");
        validateDoubleField(this.blur, "blur");
        validateIntegerField(this.resize, "resize");
        validateStringField(this.borderColor, "borderColor");
        validateIntegerField(this.border, "border");
        validateColor(this.borderColor, "borderColor");
        validateStringField(this.path, "path");
        validateStringField(this.targetType, "targetType");
        validateTargetType(this.targetType, "targetType");
    }

    /***
     * Validate a String field
     * @param field
     * @throws ParamsInvalidException
     */
    public void validateStringField(String field, String nameField) throws ParamsInvalidException {
        if (field == null) {
            throw new ParamsInvalidException(10, nameField);
        }
        if (field.isEmpty()) {
            throw new ParamsInvalidException(11, nameField);
        }
    }

    /***
     * Validate a Double field
     * @param field
     * @throws ParamsInvalidException
     */
    public void validateDoubleField(double field, String nameField) throws ParamsInvalidException {
        if (field < 0) {
            throw new ParamsInvalidException(12, nameField);
        }
    }

    /***
     * Validate a int field
     * @param field
     * @throws ParamsInvalidException
     */
    public void validateIntegerField(int field, String nameField) throws ParamsInvalidException {
        if (field < 0) {
            throw new ParamsInvalidException(12, nameField);
        }
    }

    /***
     * Validate a color
     * @param field
     * @throws ParamsInvalidException
     */
    public void validateColor(String field, String nameField) throws ParamsInvalidException {
        if (!IsColor(field)) {
            throw new ParamsInvalidException(13, nameField);
        }
    }

    public void validateTargetType(String field, String nameField) throws ParamsInvalidException {
        if (!isTargetTypeSupported(field)) {
            throw new ParamsInvalidException(15, nameField);
        }
    }

    /***
     * Verify if the parameter belongs a color value
     * @param color
     * @return
     */
    public boolean IsColor(String color) {
        for (Color colorObj : Color.values()) {
            if (colorObj.toString().equals(color))
                return true;
        }
        return false;
    }

    /***
     * Verify if targetType is support by tool
     * @param targetType
     * @return
     */
    public boolean isTargetTypeSupported(String targetType) {
        for (TypeSupported typeObj : TypeSupported.values()) {
            if (typeObj.toString().equals(targetType.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public void validateContextImg()throws ParamsInvalidException{
        List<IValidateStrategy> vals = new ArrayList<>();
        vals.add(new NullOrEmptyValidation(this.fileName));
        vals.add(new NullOrEmptyValidation(this.borderColor));
        vals.add(new NullOrEmptyValidation(this.targetType));
        vals.add(new NullOrEmptyValidation(this.fullFilePath));
        vals.add(new FileNameValidation(this.fileName));
        vals.add(new TargetTypeValidation(this.targetType));
        Context context = new Context(vals);
        context.validate();
    }

    private enum Color {
        red, yellow, blue, green, black;
    }

    private enum TypeSupported {
        PNG, JPG, BMP
    }
}
