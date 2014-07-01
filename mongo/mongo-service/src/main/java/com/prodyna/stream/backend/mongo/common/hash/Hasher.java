package com.prodyna.stream.backend.mongo.common.hash;

/**
 * Interface for hashing passwords one-way.
 */
public interface Hasher {

    public String hash(byte[] bytes);

}
