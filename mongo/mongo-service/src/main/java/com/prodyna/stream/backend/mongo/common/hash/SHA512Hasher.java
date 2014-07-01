package com.prodyna.stream.backend.mongo.common.hash;

import org.apache.commons.codec.digest.DigestUtils;

import javax.enterprise.inject.Alternative;

/**
 * alternative for Hasher that uses SHA512.
 */
@Alternative
public class SHA512Hasher implements Hasher {

    @Override
    public String hash(byte[] bytes) {
        return DigestUtils.sha512Hex(bytes);
    }
}
