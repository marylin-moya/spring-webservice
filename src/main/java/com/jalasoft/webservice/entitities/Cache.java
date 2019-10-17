/*
 *
 *  Copyright (c) 2019 Jalasoft.
 *
 *  This software is the confidential and proprietary information of Jalasoft.
 *  ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into
 *  with Jalasoft.
 *
 */
package com.jalasoft.webservice.entitities;

import java.util.ArrayList;
import java.util.List;

/**
 * Cache class to save the tokens
 */
public class Cache {
    private static Cache cache;
    private static List<String> tokens;

    /**
     * Constructor
     */
    private Cache() {
        tokens = new ArrayList<>();
    }

    /**
     * Get Instance method
     *
     * @return
     */
    public static Cache getInstance() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }

    /**
     * Add a token
     *
     * @param token
     */
    public void add(String token) {
        tokens.add(token);
    }

    /**
     * Verify if the token is already in the cache.
     *
     * @param token
     * @return
     */
    public boolean isValid(String token) {
        return tokens.contains(token);
    }

}
