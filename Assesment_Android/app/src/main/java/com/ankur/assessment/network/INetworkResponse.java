package com.ankur.assessment.network;

/**
 * An interface for all network response.
 * Created by Ankur on 12/03/2016.
 */
public interface INetworkResponse {
    /**
     * Returns true; if response is from cache; false otherwise.
     *
     * @return true, if response is from cache; false otherwise.
     */
    boolean isCachedData();

    /**
     * Sets value for response's cached status.
     *
     * @param status response's caching status
     */
    void setCachedData(boolean status);
}