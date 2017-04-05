package com.example.erielmarimon.driftwoodsoccer.models.net;

/**
 * Created by Eriel.Marimon on 4/3/17.
 */

public class Result<T> {

    public final T data;

    @SuppressWarnings("unused")
    public Result(T data) {
        this.data = data;
    }
}
