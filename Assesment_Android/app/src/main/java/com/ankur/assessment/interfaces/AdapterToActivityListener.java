package com.ankur.assessment.interfaces;

import com.ankur.assessment.model.Item;

/**
 * Created by Ankur on 12/03/2016.
 * <p>
 * interface for communication between fragment and activity
 */
public interface AdapterToActivityListener {

    void saveFavQuestionIntoDataBase(Item item);

    void deleteFavQuestionFromDataBase(Item item);
}
