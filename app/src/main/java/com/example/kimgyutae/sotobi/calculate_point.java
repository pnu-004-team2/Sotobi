package com.example.kimgyutae.sotobi;

public class calculate_point {
    private int cur_point;
    private int result_point;

    public calculate_point(int p){
        cur_point = p;
    }

    public int rent_calculate_point(int point){
        result_point = cur_point - point;
        return result_point;
    }

    public int pickup_calculate_point(int point){
        result_point = cur_point + point;
        return result_point;
    }
    public int pickme_calculate_point(int point){
        result_point = cur_point - point;
        return result_point;
    }

}
