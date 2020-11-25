package com.thehuxley.memcached;

import java.io.IOException;
import java.util.Properties;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connector {
    static Logger logger = LoggerFactory.getLogger(Connector.class);
    private static Connector singleton = null;
    private MemcachedClient pool;

    private Connector() throws IOException {
        Properties properties = new Properties();
        pool = new MemcachedClient(AddrUtil.getAddresses("localhost:11211"));
    }

    public static MemcachedClient getConnection() {

        try {
        if (singleton == null) {
            singleton = new Connector();
        }
            return singleton.pool;
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error("Problemas na comunicacão com o memcached.",
                        e);
            }
        }
        return null;

    }

}


