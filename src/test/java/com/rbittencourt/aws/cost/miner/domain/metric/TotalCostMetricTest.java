package com.rbittencourt.aws.cost.miner.domain.metric;

import com.rbittencourt.aws.cost.miner.domain.billing.BillingInfo;
import com.rbittencourt.aws.cost.miner.domain.billing.BillingInfos;
import com.rbittencourt.aws.cost.miner.fixture.BillingInfoFixture;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.MockitoAnnotations.initMocks;

public class TotalCostMetricTest {

    @InjectMocks
    private TotalCostMetric metric;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void descriptionShouldBeNull() {
        assertNull(metric.description());
    }

    @Test
    public void shouldCalculateTotalCostMetric() {
        BillingInfo boxUsage1 = BillingInfoFixture.get().withUsageType("BoxUsage").withCost(100).build();
        BillingInfo boxUsage2 = BillingInfoFixture.get().withUsageType("BoxUsage").withCost(250).build();
        BillingInfo dataTransferIn1 = BillingInfoFixture.get().withUsageType("DataTransferIn").withCost(80).build();
        BillingInfo dataTransferIn2 = BillingInfoFixture.get().withUsageType("DataTransferIn").withCost(20).build();
        BillingInfo dataTransferOut1 = BillingInfoFixture.get().withUsageType("DataTransferOut").withCost(50).build();
        BillingInfo dataTransferOut2 = BillingInfoFixture.get().withUsageType("DataTransferOut").withCost(30).build();
        BillingInfos billingInfos = new BillingInfos(List.of(boxUsage1, boxUsage2, dataTransferIn1, dataTransferIn2, dataTransferOut1, dataTransferOut2));

        MetricResult metricResult = metric.calculateMetric(billingInfos);

        assertEquals("Total cost", metricResult.getMetricValues().get(0).getDescription());
        assertEquals(new BigDecimal(530), metricResult.getMetricValues().get(0).getValue());
        assertEquals("$530.00", metricResult.getMetricValues().get(0).getFormattedValue());
    }

}