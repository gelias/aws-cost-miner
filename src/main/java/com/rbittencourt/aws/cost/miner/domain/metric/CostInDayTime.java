package com.rbittencourt.aws.cost.miner.domain.metric;

import com.rbittencourt.aws.cost.miner.domain.billing.BillingInfos;
import com.rbittencourt.aws.cost.miner.domain.mask.Money;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

import static java.math.RoundingMode.HALF_EVEN;

@Order(6)
@Component
class CostInDayTime implements Metric {

    @Override
    public String description() {
        return "Daily Metrics";
    }

    @Override
    public MetricResult calculateMetric(BillingInfos billingInfos) {
        long daysQuantity = billingInfos.stream()
                .filter(b -> b.getUsageStartDate() != null)
                .map(b -> b.getUsageStartDate().toLocalDate())
                .distinct()
                .count();

        BillingInfos dayTimeBilling = billingInfos.betweenTimeRangeOfUsageStartDate(LocalTime.of(7, 0), LocalTime.of(19, 0));

        BigDecimal totalCost = dayTimeBilling.totalCost();
        BigDecimal average = totalCost.divide(new BigDecimal(daysQuantity), HALF_EVEN);

        MetricValue metricValue = new MetricValue("Cost mean by day period 07:00 to 19:00", average, new Money(average));

        return new MetricResult(description(), List.of(metricValue));
    }

}
