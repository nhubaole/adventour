package com.adventour.web.service.impl;

import com.adventour.web.service.StatisticService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {
    @Override
    public List<Integer> getRevenuePerMonth() {
        return Arrays.asList(44, 55, 57, 56, 61, 58, 63, 60, 66);
    }
}
