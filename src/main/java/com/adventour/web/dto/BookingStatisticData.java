package com.adventour.web.dto;

import java.util.ArrayList;
import java.util.List;

public class BookingStatisticData {
    public List<Integer> bookingList;
    public List<Integer> cancelList;
    public List<String> timeList;

    public BookingStatisticData() {
        bookingList = new ArrayList<>();
        cancelList = new ArrayList<>();
        timeList = new ArrayList<>();
    }
}
