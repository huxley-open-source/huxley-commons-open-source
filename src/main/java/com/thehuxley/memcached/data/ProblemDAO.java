package com.thehuxley.memcached.data;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.spy.memcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Encoder;

import com.thehuxley.memcached.Connector;

public class ProblemDAO {
    static Logger logger = LoggerFactory.getLogger(ProblemDAO.class);
    public MemcachedClient connector = Connector.getConnection();

    public String generateSecurityKey(String key) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            logger.info("Não foi possível criar chave para '" + key + "',", e);
        }
        try {
            md.update(key.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.info("Encoding de chave não suportado,", e);
        }
        return new BASE64Encoder().encode(md.digest());
    }

    public void delete(long id) {
        connector.delete(generateSecurityKey("problem-id:" + id));
    }
    
}
