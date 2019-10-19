package com.jalasoft.webservice.responses;

import com.jalasoft.webservice.entitities.BaseFile;

public class SuccessResponse extends Response {

    private BaseFile metadata;

    /**
     * Response Constructor
     *
     * @param name   Status in String Format.
     * @param status Status in Numeric Format.
     * @param detail Message String.
     */
    public SuccessResponse(String name, Integer status, String detail) {
        super(name, status, detail);
    }

    public BaseFile getMetadata() {
        return metadata;
    }

    public void setMetadata(BaseFile metadata) {
        this.metadata = metadata;
    }
}
