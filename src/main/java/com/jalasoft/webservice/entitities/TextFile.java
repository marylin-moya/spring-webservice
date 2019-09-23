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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/** TextFile : Class to manipulate information related to CSV files
 * Version : 1.0
 * Date: 9/19/2019
 */

public class TextFile extends BaseFile {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
