package com.adventour.web.dto;

import java.util.ArrayList;
import java.util.List;

public class RevenueByTourTypeData {
    public List<Integer> domesticRevenueList;
    public List<Integer> foreignRevenueList;
    public List<String> timeList;

    public RevenueByTourTypeData() {
        domesticRevenueList = new ArrayList<>();
        timeList = new ArrayList<>();
        foreignRevenueList = new ArrayList<>();
    }
}
