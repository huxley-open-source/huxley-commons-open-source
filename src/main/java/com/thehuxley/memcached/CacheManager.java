package com.thehuxley.memcached;

import com.thehuxley.memcached.data.ProblemDAO;

/**
 * Created by huxley on 27/10/14.
 */
public final class CacheManager {
    private CacheManager() {}

    public static void UpdateProblem(long id) {
        ProblemDAO problemDAO = new ProblemDAO();
        problemDAO.delete(id);
    }
}